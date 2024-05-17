package com.bank.Bank.System.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "Объект для работы с поиском пользователей")
public class SearchByDTO {
    private LocalDateTime birthOfData;
    private String phone;
    private String fullName;
    private String email;
}
