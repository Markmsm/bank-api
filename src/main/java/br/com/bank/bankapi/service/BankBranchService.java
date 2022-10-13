package br.com.bank.bankapi.service;

import br.com.bank.bankapi.data.model.BankBranch;
import br.com.bank.bankapi.repository.BankBranchRepository;

import java.lang.reflect.MalformedParametersException;

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
}
