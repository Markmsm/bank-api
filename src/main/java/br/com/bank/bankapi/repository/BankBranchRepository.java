package br.com.bank.bankapi.repository;

import br.com.bank.bankapi.data.model.BankBranch;

import java.util.ArrayList;
import java.util.Optional;

public class BankBranchRepository {

    private static ArrayList<BankBranch> bankBranches = new ArrayList<>();

    public void create(BankBranch bankBranch) {
        bankBranches.add(bankBranch);
    }

    public Optional<BankBranch> get(int bankBranchId) {
        return bankBranches
                .stream()
                .filter(b -> b.getId() == bankBranchId)
                .findAny();
    }

    public void delete(BankBranch bankBranch) {
        bankBranches.remove(bankBranch);
    }
}
