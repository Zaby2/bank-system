package com.bank.Bank.System.api.user;


import com.bank.Bank.System.dto.AdditionalUserInformationDTO;
import com.bank.Bank.System.dto.MoneyTransferDTO;
import com.bank.Bank.System.service.user.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

// token validation need to be added


@RestController
@RequestMapping("/v1/user")
public class UserBankController {
    @Autowired
    UserService userService;

    @PatchMapping("/changeEmail")
    @Tag(name="Изменение Email пользователя")
    public HttpStatus changeEmail(@CookieValue(name="auth_token", defaultValue = "") String token,
                                  @RequestBody String email) {
        if(token.isEmpty()) {
            return HttpStatus.FORBIDDEN;
        }
            return userService.addEmail(email, token);
    }
    @PatchMapping("/changePhone")
    @Tag(name="Изменение номера телефона пользователя")
    public HttpStatus changePhone(@CookieValue(name="auth_token", defaultValue = "") String token,
                                  @RequestBody String phone) {
        if(token.isEmpty()) {
            return HttpStatus.FORBIDDEN;
        }
        return userService.addPhone(phone, token);
    }
    @DeleteMapping("/deleteEmail")
    @Tag(name="Удаление Email пользователя")
    public HttpStatus deleteEmail(@CookieValue(name="auth_token", defaultValue = "") String token) {
        if(token.isEmpty()) {
            return HttpStatus.FORBIDDEN;
        }
        return userService.deleteEmail(token);
    }
    @DeleteMapping("/deletePhone")
    @Tag(name="Удаление номера телефона пользователя")
    public HttpStatus deletePhone(@CookieValue(name="auth_token", defaultValue = "") String token) {
        if(token.isEmpty()) {
            return HttpStatus.FORBIDDEN;
        }
        return userService.deletePhone(token);
    }
    @PutMapping("/addInfo")
    @Tag(name="Добавление информации о пользователе", description = "Пользователь может добавить необходимую информацию о себе")
    public HttpStatus addUserInfo(@CookieValue(name="auth_token", defaultValue = "") String token,
                                  @RequestBody AdditionalUserInformationDTO additionalUserInformationDTO)  {
        return userService.addInfo(additionalUserInformationDTO, token);
    }
    @PutMapping("/transfer")
    @Tag(name="Перевод денег с одного счета на другой")
    public HttpStatus transferMoney(@CookieValue(name="auth_token", defaultValue = "") String token,
                                    @RequestBody MoneyTransferDTO moneyTransferDTO)  {
        return userService.transferMoneyTo(moneyTransferDTO, token);
    }


}
