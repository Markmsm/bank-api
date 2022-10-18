package br.com.bank.bankapi.data.model;

import java.util.ArrayList;
import java.util.Random;

public class BankBranch {
    private int id;
    private String name;
    private String address;
    private ArrayList<Account> accounts;

    //Todo: temporário, assim que conectar no banco deve ser modificado
    public BankBranch(String name, String address) {
        id = new Random().nextInt(10000) + 1;
        this.name = name;
        this.address = address;
        accounts = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }
}
