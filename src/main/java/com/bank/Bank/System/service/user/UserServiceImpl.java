package com.bank.Bank.System.service.user;

import com.bank.Bank.System.JWTGenerator.JWTProvider;
import com.bank.Bank.System.dto.AdditionalUserInformationDTO;
import com.bank.Bank.System.dto.MoneyTransferDTO;
import com.bank.Bank.System.entity.UserAccount;
import com.bank.Bank.System.repository.AccountRepository;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.stream.StreamSupport;

@Service
@Log4j
public class UserServiceImpl implements UserService {

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    JWTProvider jwtProvider;
    @Override
    public HttpStatus addEmail(String email, String token) {
        if(isEmailValid(email)) {
            UserAccount userAccountByLogin = accountRepository.findUserAccountByLogin(jwtProvider.getUserNameFromToken(token));
            userAccountByLogin.setEmail(email);
            accountRepository.save(userAccountByLogin);
            return HttpStatus.ACCEPTED;
        }
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public HttpStatus addPhone(String phone, String token) {
        if(isPhoneValid(phone)) {
            UserAccount userAccountByLogin = accountRepository.findUserAccountByLogin(jwtProvider.getUserNameFromToken(token));
            userAccountByLogin.setPhoneNumbers(phone);
            accountRepository.save(userAccountByLogin);
            return HttpStatus.ACCEPTED;
        }
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public HttpStatus deleteEmail(String token) {
        UserAccount userAccountByLogin = accountRepository.findUserAccountByLogin(jwtProvider.getUserNameFromToken(token));
        if(userAccountByLogin.getEmail().isEmpty()) {
            return HttpStatus.BAD_REQUEST;
        }
        userAccountByLogin.setEmail(null);
        accountRepository.save(userAccountByLogin);
        return HttpStatus.ACCEPTED;
    }

    @Override
    public HttpStatus deletePhone(String token) {
        UserAccount userAccountByLogin = accountRepository.findUserAccountByLogin(jwtProvider.getUserNameFromToken(token));
        if(userAccountByLogin.getPhoneNumbers().isEmpty()) {
            return HttpStatus.BAD_REQUEST;
        }
        userAccountByLogin.setPhoneNumbers(null);
        accountRepository.save(userAccountByLogin);
        return HttpStatus.ACCEPTED;
    }

    @Override
    public HttpStatus addInfo(AdditionalUserInformationDTO additionalUserInformationDTO, String token) {
        UserAccount userAccountByLogin = accountRepository.findUserAccountByLogin(jwtProvider.getUserNameFromToken(token));
        userAccountByLogin.setFirstName(additionalUserInformationDTO.getFirstName());
        userAccountByLogin.setBirthDate(additionalUserInformationDTO.getBirthDate());
        userAccountByLogin.setSurName(additionalUserInformationDTO.getSurName());
        userAccountByLogin.setSecondName(additionalUserInformationDTO.getSecondName());
        accountRepository.save(userAccountByLogin);
        return HttpStatus.ACCEPTED;
    }

    @Override
   @Transactional
    public HttpStatus transferMoneyTo(MoneyTransferDTO moneyTransferDTO, String token) throws ChangeSetPersister.NotFoundException {
        UserAccount userAccountByLogin = accountRepository.findUserAccountByLogin(jwtProvider.getUserNameFromToken(token));
        if(userAccountByLogin.getBalance() - moneyTransferDTO.getQuantity() < 0) {
            throw new ChangeSetPersister.NotFoundException();
        }
        UserAccount userToTransfer = accountRepository.findUserAccountByLogin(moneyTransferDTO.getLoginOfUserToTransfer());
        if(userToTransfer == null) {

            return HttpStatus.BAD_REQUEST;
        }
        userAccountByLogin.setBalance(userAccountByLogin.getBalance() - moneyTransferDTO.getQuantity());
        userToTransfer.setBalance(userToTransfer.getBalance() + moneyTransferDTO.getQuantity());
        accountRepository.save(userToTransfer);
        accountRepository.save(userAccountByLogin);
        return HttpStatus.ACCEPTED;
    }

    private boolean isPhoneValid(String phone) {
        Iterable<UserAccount> userAccounts = accountRepository.findAll();
        for (UserAccount userAccount : userAccounts) {
            if(!userAccount.getPhoneNumbers().equals(phone)) {
                return false;

            }
        }
        return true;
    }

    private boolean isEmailValid(String email) {
        Iterable<UserAccount> userAccounts = accountRepository.findAll();
        for (UserAccount userAccount : userAccounts) {
            if(userAccount.getEmail().equals(email)) {
                return false;

            }
        }
        return true;
    }
}
