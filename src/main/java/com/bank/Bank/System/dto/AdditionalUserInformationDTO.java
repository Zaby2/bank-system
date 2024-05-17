package com.bank.Bank.System.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "Объект для добавления доп. информации о пользвателе")
public class AdditionalUserInformationDTO {
    private String firstName;
    private String secondName;
    private String surName;
    private LocalDateTime birthDate;
}
