package com.bank.Bank.System.api.ancillary;

import com.bank.Bank.System.dto.SearchByDTO;
import com.bank.Bank.System.dto.UserDTO;
import com.bank.Bank.System.entity.UserAccount;
import com.bank.Bank.System.service.ancillary.AncillaryUserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

// jWT token need to be generated here and ADDED TO COOKIES

@RestController
@RequestMapping("/v1/ancillary")
public class AncillaryBankController {
    @Autowired
    AncillaryUserService ancillaryUserService;

    @PostMapping("/addUser")
    @Tag(name="Создание нового пользователя", description="Контроллер отвечает за создвние нового пользователя")
    public HttpStatus addNewUserAccount(@RequestBody UserDTO userDTO,  HttpServletResponse httpServletResponse) {
             return ancillaryUserService.createNewAccount(userDTO,httpServletResponse );
    }
    @GetMapping("/search")
    @Tag(name="Поиск пользователя", description="Поиск выполняется по одному из перечисленных в ТЗ полей")
    public UserAccount searchForUser(@RequestBody SearchByDTO search) {
        return ancillaryUserService.searchForUser(search);
    }
}
