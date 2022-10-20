package br.com.bank.bankapi.data.model;

public class Customer {
    private String cpf;
    private String name;
    private String address;
    private String phone;
    private String email;

    public Customer(String cpf, String name, String address, String phone, String email) {
        this.cpf = cpf;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.email = email;
    }
}
