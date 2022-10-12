package br.com.bank.bankapi.repository;

import br.com.bank.bankapi.data.model.BankBranch;

import java.util.ArrayList;

public class BankBranchRepository {

    private static ArrayList<BankBranch> bankBranches = new ArrayList<>();

    public void create(BankBranch bankBranch) {
        bankBranches.add(bankBranch);
    }
}
