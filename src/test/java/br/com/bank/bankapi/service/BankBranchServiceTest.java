package br.com.bank.bankapi.service;

import br.com.bank.bankapi.data.model.BankBranch;
import br.com.bank.bankapi.repository.BankBranchRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.lang.reflect.MalformedParametersException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class BankBranchServiceTest {

    static private BankBranchService bankBranchService;
    static private BankBranchRepository mockedRepository;

    @BeforeAll
    static void setUp() {
        mockedRepository = mock(BankBranchRepository.class);
        bankBranchService = new BankBranchService(mockedRepository);
    }

    @Test
    void shouldCreateBankBranch() {
        // Given:
        BankBranch fakeBankBranch = createFakeBankBranch();

        // When:
        bankBranchService.create(fakeBankBranch);

        // Then:
        verify(mockedRepository).create(fakeBankBranch);
    }

    @Test
    void shouldCreateBranchIfNoAddress() {
        //Given:
        BankBranch fakeBankBranch = createFakeBankBranch();
        fakeBankBranch.setAddress("");

        //When:
        bankBranchService.create(fakeBankBranch);

        //Then:
        verify(mockedRepository).create(fakeBankBranch);
    }

    @Test
    void shouldNotCreateBranchIfNoName() {
        //Given:
        BankBranch fakeBankBranch = createFakeBankBranch();
        fakeBankBranch.setName("");

        //When:
        Throwable ex = assertThrows(MalformedParametersException.class, () -> {
            bankBranchService.create(fakeBankBranch);
        });

        //Then:
        assertThat(ex.getMessage(), is("No name provided."));
    }

    BankBranch createFakeBankBranch() {
        return new BankBranch("Branch testeeeera", "Address testeeeeraaa");
    }
}