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
        if (bankBranch.getName().isBlank()) {
            throw new MalformedParametersException("No name provided.");
        }

        repository.create(bankBranch);
    }
}
