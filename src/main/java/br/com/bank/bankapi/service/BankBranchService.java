package br.com.bank.bankapi.service;

import br.com.bank.bankapi.data.model.Account;
import br.com.bank.bankapi.data.model.BankBranch;
import br.com.bank.bankapi.data.model.Customer;
import br.com.bank.bankapi.repository.BankBranchRepository;

import javax.naming.directory.InvalidAttributesException;
import java.lang.reflect.MalformedParametersException;
import java.util.ArrayList;
import java.util.List;
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
        BankBranch newBankBranch = cloneBankBranch(bankBranch);
        delete(bankBranch.getId());
        create(newBankBranch);
    }

    public void delete(int bankBranchId) {
        BankBranch bankBranchToDelete = get(bankBranchId);
        repository.delete(bankBranchToDelete);
    }

    public void createAccount(int bankBranchId, List<Customer> customers) throws InvalidAttributesException {
        if (customers == null || customers.isEmpty())
            throw new InvalidAttributesException(String.format(
                    "Account for bank branch id = %s could not be created without customer.",
                    bankBranchId
            ));

        BankBranch oldBankBranch = get(bankBranchId);
        BankBranch newBankBranch = cloneBankBranch(oldBankBranch);
        Account newAccount = new Account(bankBranchId, customers);
        newBankBranch.addAccount(newAccount);
        update(newBankBranch);
    }

    public Account getAccount(int accountId, int bankBranchId) {
        BankBranch bankBranch = get(bankBranchId);
        Optional<Account> accountOPT = bankBranch.getAccounts()
                .stream()
                .filter(a -> a.getId() == accountId)
                .findAny();

        if (accountOPT.isPresent()) {
            return accountOPT.get();
        } else {
            throw new NoSuchElementException(String.format("Account id = %s in bank branch id = %s not found.", accountId, bankBranchId));
        }
    }

    private BankBranch cloneBankBranch(BankBranch bankBranch) {
        return new BankBranch(bankBranch.getId(),
                bankBranch.getName(),
                bankBranch.getAddress(),
                new ArrayList<>(bankBranch.getAccounts()));
    }
}
