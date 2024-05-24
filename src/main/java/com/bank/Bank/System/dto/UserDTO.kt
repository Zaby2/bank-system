package com.bank.Bank.System.dto

import io.swagger.v3.oas.annotations.media.Schema
import lombok.Data

@Data
@Schema(description = "Объект для работы с пользователями")
class UserDTO {
    var login: String? = null
    var password: String? = null
    var balance: Float = 0f
    var phoneNumber: String? = null
    var email: String? = null
}
