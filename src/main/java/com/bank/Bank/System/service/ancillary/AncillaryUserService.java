package com.bank.Bank.System.service.ancillary;

import com.bank.Bank.System.dto.SearchByDTO;
import com.bank.Bank.System.dto.UserDTO;
import com.bank.Bank.System.entity.UserAccount;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;


public interface AncillaryUserService {
    public HttpStatus createNewAccount(UserDTO userDTO,  HttpServletResponse httpServletResponse);
    public UserAccount searchForUser(SearchByDTO searchByDTO);
}
