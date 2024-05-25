package com.bank.Bank.System.service.user;

import com.bank.Bank.System.dto.AdditionalUserInformationDTO;
import com.bank.Bank.System.dto.MoneyTransferDTO;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;

public interface UserService {
    public HttpStatus addEmail(String email, String token);
    public HttpStatus addPhone(String phone, String token);
    public HttpStatus deleteEmail( String token);
    public HttpStatus deletePhone( String token);
    public HttpStatus addInfo(AdditionalUserInformationDTO additionalUserInformationDTO, String token);

    public HttpStatus transferMoneyTo(MoneyTransferDTO moneyTransferDTO, String token) throws ChangeSetPersister.NotFoundException;

}
