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
    }
}
