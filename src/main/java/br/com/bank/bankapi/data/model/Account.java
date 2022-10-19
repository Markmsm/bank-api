package br.com.bank.bankapi.data.model;

import java.util.ArrayList;
import java.util.Random;

public class Account {
    private int id;
    private ArrayList<Customer> accountOwners;
    private double balance;
    private int bankBranchId;

    public Account(int bankBranchId) {
        id = new Random().nextInt(10000) + 1;
        this.bankBranchId = bankBranchId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getBankBranchId() {
        return bankBranchId;
    }

    public void setBankBranchId(int bankBranchId) {
        this.bankBranchId = bankBranchId;
    }
}
