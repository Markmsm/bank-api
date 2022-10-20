package br.com.bank.bankapi.data.model;

import java.util.ArrayList;
import java.util.Objects;
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

    public BankBranch(int id, String name, String address, ArrayList<Account> accounts) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.accounts = accounts;
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

    public void setAccounts(ArrayList<Account> accounts) {
        this.accounts = accounts;
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BankBranch that = (BankBranch) o;
        return id == that.id && name.equals(that.name) && Objects.equals(address, that.address) && Objects.equals(accounts, that.accounts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address, accounts);
    }
}
