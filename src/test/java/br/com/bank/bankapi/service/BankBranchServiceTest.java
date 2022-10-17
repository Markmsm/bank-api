package br.com.bank.bankapi.service;

import br.com.bank.bankapi.data.model.BankBranch;
import br.com.bank.bankapi.repository.BankBranchRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.lang.reflect.MalformedParametersException;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
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
    void createShouldCreateBankBranch() {
        // Given:
        BankBranch fakeBankBranch = createFakeBankBranch();

        // When:
        bankBranchService.create(fakeBankBranch);

        // Then:
        verify(mockedRepository).create(fakeBankBranch);
    }

    @Test
    void createShouldCreateBankBranchIfEmptyAddress() {
        //Given:
        BankBranch fakeBankBranch = createFakeBankBranch();
        fakeBankBranch.setAddress("");

        //When:
        bankBranchService.create(fakeBankBranch);

        //Then:
        verify(mockedRepository).create(fakeBankBranch);
    }

    @Test
    void createShouldCreateBankBranchIfNullAddress() {
        //Given:
        BankBranch fakeBankBranch = createFakeBankBranch();
        fakeBankBranch.setAddress(null);

        //When:
        bankBranchService.create(fakeBankBranch);

        //Then:
        verify(mockedRepository).create(fakeBankBranch);
    }

    @Test
    void createShouldNotCreateBankBranchIfEmptyName() {
        //Given:
        BankBranch fakeBankBranch = createFakeBankBranch();
        fakeBankBranch.setName("");

        //When:
        Throwable ex = assertThrows(MalformedParametersException.class, () -> bankBranchService.create(fakeBankBranch));

        //Then:
        assertThat(ex.getMessage(), is("No name provided."));
    }

    @Test
    void createShouldNotCreateBankBranchIfNullName() {
        //Given:
        BankBranch fakeBankBranch = createFakeBankBranch();
        fakeBankBranch.setName(null);

        //When:
        Throwable ex = assertThrows(MalformedParametersException.class, () -> bankBranchService.create(fakeBankBranch));

        //Then:
        assertThat(ex.getMessage(), is("No name provided."));
    }

    //Todo: revisar este teste quando aplicação tiver conexão com o banco,
    // pois neste momento não está testando lógica nenhuma,
    // o mock está retornando o próprio objeto que o teste usa, ou seja,
    // só valida se ele retorna o que o repository retornou pra ele (fiz mais pela experiencia mesmo)
    @Test
    void getShouldReturnBankBranchById() {
        //Given:
        BankBranch expectedBankBranch = createFakeBankBranch();
        given(mockedRepository.get(expectedBankBranch.getId())).willReturn(Optional.of(expectedBankBranch));

        //When:
        BankBranch bankBranch = bankBranchService.get(expectedBankBranch.getId());

        //Then:
        assertThat(bankBranch.getId(), is(expectedBankBranch.getId()));
        assertThat(bankBranch.getName(), is(expectedBankBranch.getName()));
        assertThat(bankBranch.getAddress(), is(expectedBankBranch.getAddress()));
    }

    @Test
    void getShouldThrowExceptionIfNoBankBranch() {
        //Given:
        int fakeBankBranchId = createFakeBankBranch().getId();

        //When:
        Throwable ex = assertThrows(NoSuchElementException.class, () -> bankBranchService.get(fakeBankBranchId));

        //Then:
        assertThat(ex.getMessage(), is(String.format("Bank branch id = %s not found.", fakeBankBranchId)));
    }

    @Test
    void deleteShouldDeleteBankBranch() {
        //Given:
        BankBranch bankBranchToDelete = createFakeBankBranch();
        given(mockedRepository.get(bankBranchToDelete.getId())).willReturn(Optional.of(bankBranchToDelete));

        //When:
        bankBranchService.delete(bankBranchToDelete.getId());

        //Then:
        verify(mockedRepository).delete(bankBranchToDelete);
    }

    @Test
    void deleteShouldThrowExceptionIfNoBankBranch() {
        //Given:
        BankBranch bankBranchToDelete = createFakeBankBranch();

        //When:
        Throwable ex = assertThrows(NoSuchElementException.class, () -> bankBranchService.delete(bankBranchToDelete.getId()));

        //Then:
        assertThat(ex.getMessage(), is(String.format("Bank branch id = %s not found.", bankBranchToDelete.getId())));
    }

    BankBranch createFakeBankBranch() {
        return new BankBranch("Branch testeeeera", "Address testeeeeraaa");
    }
}