package com.bank.Bank.System.swagger.config

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Contact
import io.swagger.v3.oas.annotations.info.Info

@OpenAPIDefinition(
    info = Info(
        title = "bank System Api",
        description = "bank System",
        version = "1.0.0",
        contact = Contact(name = "Shitov Bogdan", email = "work.acca@yandex.ru")
    )
)
class OpenApiConfig
