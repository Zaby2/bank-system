package com.bank.Bank.System.service.ancillary;

import com.bank.Bank.System.JWTGenerator.JWTProvider;
import com.bank.Bank.System.dto.SearchByDTO;
import com.bank.Bank.System.dto.UserDTO;
import com.bank.Bank.System.entity.UserAccount;
import com.bank.Bank.System.repository.AccountRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Log4j
public class AncillaryUserServiceImpl implements AncillaryUserService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    JWTProvider jwtProvider;
    @Override
    public HttpStatus createNewAccount(UserDTO userDTO,  HttpServletResponse httpServletResponse) {
        if (isDataValid(userDTO)) {
            UserAccount userAccount = new UserAccount();
            userAccount.setBalance(userDTO.getBalance());
            userAccount.setLogin(userDTO.getLogin());
            userAccount.setPassword(userDTO.getPassword());
            userAccount.setEmail(userDTO.getEmail());
            userAccount.setPhoneNumbers(userDTO.getPhoneNumber());
            userAccount.setMaxPrBalance(userDTO.getBalance() * 207 / 100);
            accountRepository.save(userAccount);
            String accessToken = jwtProvider.generateAccessToken(userDTO);
            addTokenToCookie(accessToken, httpServletResponse);
            return HttpStatus.CREATED;
        }
        return HttpStatus.BAD_REQUEST;
    }

    @Override
    public UserAccount searchForUser(SearchByDTO searchByDTO) {
        if(searchByDTO.getPhone() != null) {
            return accountRepository.findUserAccountByPhoneNumbers(searchByDTO.getPhone());
        }
        if(searchByDTO.getBirthOfData() != null) {
            return accountRepository.findUserAccountByBirthDateAfter(searchByDTO.getBirthOfData());
        }
        if(searchByDTO.getFullName() != null) {
            return accountRepository.findByFullName(searchByDTO.getFullName()).get(0);
        }
        if(searchByDTO.getEmail() != null) {
            return accountRepository.findUserAccountByEmail(searchByDTO.getEmail());
        }
        return null;
    }

    private boolean isDataValid(UserDTO userDTO) {
        Iterable<UserAccount> userAccounts = accountRepository.findAll();
        for (UserAccount userAccount : userAccounts) {
            if(userAccount.getEmail().equals(userDTO.getEmail())
                    || userAccount.getLogin().equals(userDTO.getLogin())
                    || userAccount.getPhoneNumbers().
                    equals(userDTO.getPhoneNumber())) {
                return false;

            }
        }
        return true;
    }
    @SneakyThrows
    public void addTokenToCookie(@NonNull String token, HttpServletResponse httpServletResponse) {
        Cookie cookie = new Cookie("auth_token", token);
        cookie.setMaxAge(7*24*60*60);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        httpServletResponse.addCookie(cookie);
        httpServletResponse.setContentType("text/plain");
    }
    @Scheduled(fixedRate = 60000)
    public void balanceIncrease() {
        Iterable<UserAccount> userAccounts = accountRepository.findAll();
        for (UserAccount userAccount : userAccounts) {
            if(userAccount.getBalance() < userAccount.getMaxPrBalance()) {
                userAccount.setBalance((float) (userAccount.getBalance() + userAccount.getBalance()*0.05));
                accountRepository.save(userAccount);
            }
        }
    }

}
