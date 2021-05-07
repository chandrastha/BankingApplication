package com.projectzero.eval;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.projectzero.dao.AccountDAOImpl;
import com.projectzero.model.Account;
import com.projectzero.service.AccountService;

public class ProjectZeroServiceTest {

	
	@Mock
	private AccountDAOImpl fakeAccountDaoImpl;
	private AccountService fakeAccountService;
	private Account fakeAccount;
	
	@SuppressWarnings("deprecation")
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		fakeAccountService = new AccountService(fakeAccountDaoImpl);
		fakeAccount = new Account(15,3500,"active", "customer");
		
		when(fakeAccountDaoImpl.withdraw(4000, 15)).thenReturn(false);
		when(fakeAccountDaoImpl.withdraw(-2000, 15)).thenReturn(false);
		when(fakeAccountDaoImpl.withdraw(1000, 10)).thenReturn(false);
		
		when(fakeAccountDaoImpl.deposit(1000, 15)).thenReturn(true);
		when(fakeAccountDaoImpl.deposit(-1000, 15)).thenReturn(false);
		when(fakeAccountDaoImpl.deposit(1000, 10)).thenReturn(false);
	}
	
	
	@Test
	public void WithdrawNotAvailableBalanceFailure() {
		assertEquals(false,fakeAccountService.withdraw(4000,15),"Lower balance withdraw failure");
		
	}
	
	
	@Test
	public void withdrawNegativeAmountFailure() {
		assertEquals(false,fakeAccountService.withdraw(-2000, 15),"Negative amount withdraw failure");
	}
	
	@Test
	public void withdrawWithWrongAccountNumber() {
		assertEquals(false,fakeAccountService.withdraw(1000, 10),"Wrong Account number Failure");
	}
	
	@Test
	public void depositPositiveAmountSuccess() {
		assertEquals(true,fakeAccountService.deposit(1000, 15)," Positive amount Deposit successful");
	}
	
	@Test
	public void depositNegativAmountFailure() {
		assertEquals(false,fakeAccountService.deposit(-1000, 15)," Negative amount Deposit unsuccessful");
	}
	
	@Test
	public void ddepositWrongAccountFailure() {
		assertEquals(false,fakeAccountService.deposit(1000, 10)," Wrong account number failure");
	}
}
