package br.com.bank.bankapi.service;

import br.com.bank.bankapi.data.model.Account;
import br.com.bank.bankapi.data.model.BankBranch;
import br.com.bank.bankapi.data.model.Customer;
import br.com.bank.bankapi.repository.BankBranchRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.naming.directory.InvalidAttributesException;
import java.lang.reflect.MalformedParametersException;
import java.util.ArrayList;
import java.util.List;
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

    @BeforeEach
    void setUp() {
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
    void createShouldThrowExceptionIfBankBranchWithEmptyName() {
        //Given:
        BankBranch fakeBankBranch = createFakeBankBranch();
        fakeBankBranch.setName("");

        //When:
        Throwable ex = assertThrows(MalformedParametersException.class,
                () -> bankBranchService.create(fakeBankBranch));

        //Then:
        assertThat(ex.getMessage(), is("No name provided."));
    }

    @Test
    void createShouldThrowExceptionIfBankBranchWithNullName() {
        //Given:
        BankBranch fakeBankBranch = createFakeBankBranch();
        fakeBankBranch.setName(null);

        //When:
        Throwable ex = assertThrows(MalformedParametersException.class,
                () -> bankBranchService.create(fakeBankBranch));

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
        given(mockedRepository.get(expectedBankBranch.getId()))
                .willReturn(Optional.of(expectedBankBranch));

        //When:
        BankBranch bankBranch = bankBranchService.get(expectedBankBranch.getId());

        //Then:
        assertThat(bankBranch.getId(), is(expectedBankBranch.getId()));
        assertThat(bankBranch.getName(), is(expectedBankBranch.getName()));
        assertThat(bankBranch.getAddress(), is(expectedBankBranch.getAddress()));
    }

    @Test
    void getShouldThrowExceptionIfBankBranchDontExist() {
        //Given:
        int fakeBankBranchId = createFakeBankBranch().getId();

        //When:
        Throwable ex = assertThrows(NoSuchElementException.class,
                () -> bankBranchService.get(fakeBankBranchId));

        //Then:
        assertThat(ex.getMessage(), is(String.format("Bank branch id = %s not found.", fakeBankBranchId)));
    }

    @Test
    void updateShouldUpdateBankBranch() {
        //Given:
        BankBranch oldBankBranch = createFakeBankBranch();
        BankBranch newBankBranch = createFakeBankBranch();
        int oldBankBranchId = oldBankBranch.getId();
        newBankBranch.setId(oldBankBranchId);
        newBankBranch.setName("Testeeera");
        given(mockedRepository.get(oldBankBranchId))
                .willReturn(Optional.of(oldBankBranch));

        //When:
        bankBranchService.update(newBankBranch);

        //Then:
        verify(mockedRepository).delete(oldBankBranch);
        verify(mockedRepository).create(newBankBranch);
    }

    @Test
    void updateShouldThrowExceptionIfBankBranchDontExist() {
        //Given:
        int oldBankBranchId = createFakeBankBranch().getId();
        BankBranch newBankBranch = createFakeBankBranch();
        newBankBranch.setId(oldBankBranchId);

        //When:
        Throwable ex = assertThrows(NoSuchElementException.class,
                () -> bankBranchService.update(newBankBranch));

        //Then:
        assertThat(ex.getMessage(), is(String.format("Bank branch id = %s not found.", newBankBranch.getId())));
    }

    @Test
    void deleteShouldDeleteBankBranch() {
        //Given:
        BankBranch bankBranchToDelete = createFakeBankBranch();
        given(mockedRepository.get(bankBranchToDelete.getId()))
                .willReturn(Optional.of(bankBranchToDelete));

        //When:
        bankBranchService.delete(bankBranchToDelete.getId());

        //Then:
        verify(mockedRepository).delete(bankBranchToDelete);
    }

    @Test
    void deleteShouldThrowExceptionIfBankBranchDontExist() {
        //Given:
        BankBranch bankBranchToDelete = createFakeBankBranch();

        //When:
        Throwable ex = assertThrows(NoSuchElementException.class,
                () -> bankBranchService.delete(bankBranchToDelete.getId()));

        //Then:
        assertThat(ex.getMessage(), is(String.format("Bank branch id = %s not found.", bankBranchToDelete.getId())));
    }

    @Test
    void createAccountShouldCreateAccount() throws InvalidAttributesException {
        //Given:
        List<Customer> customers = createFakeCustomers();
        BankBranch fakeBankBranchWithoutAccount = createFakeBankBranch();
//        BankBranch expectedBankBranchWithAccount = createFakeBankBranch();
//        expectedBankBranchWithAccount.setId(fakeBankBranchWithoutAccount.getId());
        given(mockedRepository
                .get(fakeBankBranchWithoutAccount.getId()))
                .willReturn(Optional.of(fakeBankBranchWithoutAccount));

        //When:
        bankBranchService.createAccount(fakeBankBranchWithoutAccount.getId(), customers);

        //Then:
        verify(mockedRepository).delete(fakeBankBranchWithoutAccount);
        //Todo COMO VERIFICAR SE O MÉTODO CREATE FOI CHAMADO COM A AGÊNCIA CONTENDO CONTA??????
    }

    @Test
    void createAccountShouldThrowExceptionIfBankBranchDontExist() {
        //Given:
        List<Customer> customers = createFakeCustomers();
        int fakeBankBranchId = createFakeBankBranch().getId();

        //When:
        Throwable ex = assertThrows(NoSuchElementException.class,
                () -> bankBranchService.createAccount(fakeBankBranchId, customers));

        //Then:
        assertThat(ex.getMessage(), is(String.format("Bank branch id = %s not found.", fakeBankBranchId)));
    }

    @Test
    void createAccountShouldThrowExceptionIfEmptyCustomers() {
        //Given:
        List<Customer> customers = createFakeCustomers();
        int fakeBankBranchId = createFakeBankBranch().getId();
        customers.clear();

        //When:
        Throwable ex = assertThrows(InvalidAttributesException.class,
                () -> bankBranchService.createAccount(fakeBankBranchId, customers));

        //Then:
        assertThat(ex.getMessage(),
                is(String.format("Account for bank branch id = %s could not be created without customer.",
                        fakeBankBranchId)));
    }

    @Test
    void createAccountShouldThrowExceptionIfNullCustomers() {
        //Given:
        int fakeBankBranchId = createFakeBankBranch().getId();

        //When:
        Throwable ex = assertThrows(InvalidAttributesException.class,
                () -> bankBranchService.createAccount(fakeBankBranchId, null));

        //Then:
        assertThat(ex.getMessage(),
                is(String.format("Account for bank branch id = %s could not be created without customer.",
                        fakeBankBranchId)));
    }

    @Test
    void getAccountShouldReturnAccount() {
        //Given:
        Account fakeAccount = createFakeAccount();
        BankBranch fakeBankBranch = createFakeBankBranch();
        fakeBankBranch.setId(fakeAccount.getBankBranchId());
        fakeBankBranch.addAccount(fakeAccount);
        given(mockedRepository.get(fakeAccount.getBankBranchId()))
                .willReturn(Optional.of(fakeBankBranch));

        //When:
        bankBranchService.getAccount(fakeAccount.getId(), fakeAccount.getBankBranchId());

        //Then:
        verify(mockedRepository).get(fakeAccount.getBankBranchId());
    }

    @Test
    void getAccountShouldThrowExceptionIfAccountDontExist() {
        //Given:
        Account fakeAccount = createFakeAccount();
        BankBranch fakeBankBranch = createFakeBankBranch();
        fakeBankBranch.setId(fakeAccount.getBankBranchId());
        given(mockedRepository.get(fakeAccount.getBankBranchId()))
                .willReturn(Optional.of(fakeBankBranch));

        //When:
        Throwable ex = assertThrows(NoSuchElementException.class,
                () -> bankBranchService.getAccount(fakeAccount.getId(), fakeAccount.getBankBranchId()));

        //Then:
        assertThat(
                ex.getMessage(),
                is(String.format(
                        "Account id = %s in bank branch id = %s not found.",
                        fakeAccount.getId(),
                        fakeAccount.getBankBranchId()
                ))
        );
    }

    BankBranch createFakeBankBranch() {
        return new BankBranch("Branch testeeeera", "Address testeeeeraaa");
    }

    private List<Customer> createFakeCustomers() {
        List<Customer> customers = new ArrayList<>();

        Customer johnDoe = new Customer("12345678912",
                "John Doe",
                "John Doe Address",
                "5551999999999",
                "john@email.com");

        customers.add(johnDoe);
        return customers;
    }

    Account createFakeAccount() {
        return new Account(123456, createFakeCustomers());
    }
}