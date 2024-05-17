package com.bank.Bank.System.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Объект для работы с переводом средств со счета на счет")
public class MoneyTransferDTO {
    String loginOfUserToTransfer;
    float quantity;
}
