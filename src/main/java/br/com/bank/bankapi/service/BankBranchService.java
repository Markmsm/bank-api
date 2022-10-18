package br.com.bank.bankapi.service;

import br.com.bank.bankapi.data.model.Account;
import br.com.bank.bankapi.data.model.BankBranch;
import br.com.bank.bankapi.repository.BankBranchRepository;

import java.lang.reflect.MalformedParametersException;
import java.util.NoSuchElementException;
import java.util.Optional;

public class BankBranchService {

    private BankBranchRepository repository;

    public BankBranchService(BankBranchRepository repository) {
        this.repository = repository;
    }

    public void create(BankBranch bankBranch) {
        String emptyNameMessage = "No name provided.";
        try {
            if (bankBranch.getName().isBlank()) throw new MalformedParametersException(emptyNameMessage);
            repository.create(bankBranch);
        } catch (NullPointerException ex) {
            throw new MalformedParametersException(emptyNameMessage);
        }
    }

    public BankBranch get(int bankBranchId) {
        Optional<BankBranch> bankBranchOPT = repository.get(bankBranchId);
        if (bankBranchOPT.isPresent()) {
            return bankBranchOPT.get();
        } else {
            throw new NoSuchElementException(String.format("Bank branch id = %s not found.", bankBranchId));
        }
    }

    public void update(BankBranch bankBranch) {
        delete(bankBranch.getId());
        create(bankBranch);
    }

    public void delete(int bankBranchId) {
        BankBranch bankBranchToDelete = get(bankBranchId);
        repository.delete(bankBranchToDelete);
    }

    public void createAccount(Account account) {
        BankBranch bankBranch = get(account.getBankBranchId());
        bankBranch.addAccount(account);
        repository.delete(bankBranch);
        create(bankBranch);
    }
}
