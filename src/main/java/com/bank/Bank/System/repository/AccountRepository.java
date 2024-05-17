package com.bank.Bank.System.repository;

import com.bank.Bank.System.entity.UserAccount;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;


public interface AccountRepository extends CrudRepository<UserAccount, Long> {
    public UserAccount findUserAccountByLogin(String login);
    public UserAccount findUserAccountByPhoneNumbers(String phone);
    public UserAccount findUserAccountByEmail(String email);

    public UserAccount findUserAccountByBirthDateAfter(LocalDateTime birthDate);
    @Query(value = "SELECT * FROM user_account WHERE " +
            "(:fullName IS NULL OR CONCAT(user_account.first_name, ' ', user_account.second_name, ' ', user_account.sur_name) LIKE CONCAT(:fullName, '%'))",
            nativeQuery = true)
    List<UserAccount> findByFullName(@Param("fullName") String fullName);
}

