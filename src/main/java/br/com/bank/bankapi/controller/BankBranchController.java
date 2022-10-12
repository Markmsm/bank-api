package br.com.bank.bankapi.controller;

import br.com.bank.bankapi.data.model.BankBranch;
import br.com.bank.bankapi.service.BankBranchService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bank/v1/branch")
public class BankBranchController {

    private BankBranchService service;

    public BankBranchController(BankBranchService service) {
        this.service = service;
    }

    @PostMapping
    void create(@RequestBody BankBranch bankBranch) {
        service.create(bankBranch);
    }
}
