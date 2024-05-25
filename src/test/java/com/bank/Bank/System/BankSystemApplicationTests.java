package com.bank.Bank.System;

import com.bank.Bank.System.JWTGenerator.JWTProvider;
import com.bank.Bank.System.dto.MoneyTransferDTO;
import com.bank.Bank.System.entity.UserAccount;
import com.bank.Bank.System.repository.AccountRepository;
import com.bank.Bank.System.service.user.UserService;
import com.bank.Bank.System.service.user.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
class BankSystemApplicationTests {

		@Mock
		private AccountRepository accountRepository;

		@Mock
		private JWTProvider jwtProvider;

		@InjectMocks
		private UserServiceImpl moneyTransferService;

		private UserAccount userAccountByLogin;
		private UserAccount userToTransfer;

		@BeforeEach
		public void setUp() {
			userAccountByLogin = new UserAccount();
			userAccountByLogin.setLogin("user1");
			userAccountByLogin.setBalance(1000F);

			userToTransfer = new UserAccount();
			userToTransfer.setLogin("user2");
			userToTransfer.setBalance(500F);
		}

		@Test
		public void testTransferMoneyTo_Success() throws ChangeSetPersister.NotFoundException {
			MoneyTransferDTO dto = new MoneyTransferDTO();
			dto.setLoginOfUserToTransfer("user2");
			dto.setQuantity(100);

			when(jwtProvider.getUserNameFromToken(anyString())).thenReturn("user1");
			when(accountRepository.findUserAccountByLogin("user1")).thenReturn(userAccountByLogin);
			when(accountRepository.findUserAccountByLogin("user2")).thenReturn(userToTransfer);

			HttpStatus status = moneyTransferService.transferMoneyTo(dto, "token");

			assertEquals(HttpStatus.ACCEPTED, status);
			assertEquals(900, userAccountByLogin.getBalance());
			assertEquals(600, userToTransfer.getBalance());

			verify(accountRepository).save(userAccountByLogin);
			verify(accountRepository).save(userToTransfer);
		}

		@Test
		public void testTransferMoneyTo_InsufficientFunds() throws ChangeSetPersister.NotFoundException {
			MoneyTransferDTO dto = new MoneyTransferDTO();
			dto.setLoginOfUserToTransfer("user2");
			dto.setQuantity(1100);

			when(jwtProvider.getUserNameFromToken(anyString())).thenReturn("user1");
			when(accountRepository.findUserAccountByLogin("user1")).thenReturn(userAccountByLogin);

			HttpStatus status = moneyTransferService.transferMoneyTo(dto, "token");

			assertEquals(HttpStatus.FORBIDDEN, status);
			verify(accountRepository, never()).save(any(UserAccount.class));
		}

		@Test
		public void testTransferMoneyTo_UserNotFound() throws ChangeSetPersister.NotFoundException {
			MoneyTransferDTO dto = new MoneyTransferDTO();
			dto.setLoginOfUserToTransfer("user3");
			dto.setQuantity(100);

			when(jwtProvider.getUserNameFromToken(anyString())).thenReturn("user1");
			when(accountRepository.findUserAccountByLogin("user1")).thenReturn(userAccountByLogin);
			when(accountRepository.findUserAccountByLogin("user3")).thenReturn(null);

			HttpStatus status = moneyTransferService.transferMoneyTo(dto, "token");

			assertEquals(HttpStatus.BAD_REQUEST, status);
			verify(accountRepository, never()).save(any(UserAccount.class));
		}
}
