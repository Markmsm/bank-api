package br.com.bank.bankapi.configuration;

import br.com.bank.bankapi.repository.BankBranchRepository;
import br.com.bank.bankapi.service.BankBranchService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BankConfiguration {

    @Bean
    BankBranchService bankBranchService(BankBranchRepository repository) {
        return new BankBranchService(repository);
    }

    @Bean
    BankBranchRepository bankBranchRepository() {
        return new BankBranchRepository();
    }
}
