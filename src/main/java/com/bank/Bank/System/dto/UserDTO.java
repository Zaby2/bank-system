package com.bank.Bank.System.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Объект для работы с пользователями")
public class UserDTO {
     String login;
     String password;
     float balance;
     String phoneNumber;
     String email;
}
