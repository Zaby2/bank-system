package com.bank.Bank.System.service.ancillary

import com.bank.Bank.System.JWTGenerator.JWTProvider
import com.bank.Bank.System.dto.SearchByDTO
import com.bank.Bank.System.dto.UserDTO
import com.bank.Bank.System.entity.UserAccount
import com.bank.Bank.System.repository.AccountRepository
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletResponse
import lombok.SneakyThrows
import lombok.extern.log4j.Log4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
@Log4j
class AncillaryUserServiceImpl : AncillaryUserService {
    @Autowired
    var accountRepository: AccountRepository? = null

    @Autowired
    var jwtProvider: JWTProvider? = null
    override fun createNewAccount(userDTO: UserDTO, httpServletResponse: HttpServletResponse): HttpStatus {
        if (isDataValid(userDTO)) {
            val userAccount = UserAccount()
            userAccount.balance = userDTO.balance
            userAccount.login = userDTO.login
            userAccount.password = userDTO.password
            userAccount.email = userDTO.email
            userAccount.phoneNumbers = userDTO.phoneNumber
            userAccount.maxPrBalance = userDTO.balance * 207 / 100
            accountRepository!!.save(userAccount)
            val accessToken = jwtProvider!!.generateAccessToken(userDTO)
            addTokenToCookie(accessToken, httpServletResponse)
            return HttpStatus.CREATED
        }
        return HttpStatus.BAD_REQUEST
    }

    override fun searchForUser(searchByDTO: SearchByDTO): UserAccount? {
        if (searchByDTO.phone != null) {
            return accountRepository!!.findUserAccountByPhoneNumbers(searchByDTO.phone)
        }
        if (searchByDTO.birthOfData != null) {
            return accountRepository!!.findUserAccountByBirthDateAfter(searchByDTO.birthOfData)
        }
        if (searchByDTO.fullName != null) {
            return accountRepository!!.findByFullName(searchByDTO.fullName)[0]
        }
        if (searchByDTO.email != null) {
            return accountRepository!!.findUserAccountByEmail(searchByDTO.email)
        }
        return null
    }

    private fun isDataValid(userDTO: UserDTO): Boolean {
        val userAccounts = accountRepository!!.findAll()
        for (userAccount in userAccounts) {
            if (userAccount.email == userDTO.email || userAccount.login == userDTO.login || userAccount.phoneNumbers == userDTO.phoneNumber) {
                return false
            }
        }
        return true
    }

    @SneakyThrows
    fun addTokenToCookie(token: String, httpServletResponse: HttpServletResponse) {
        val cookie = Cookie("auth_token", token)
        cookie.maxAge = 7 * 24 * 60 * 60
        cookie.path = "/"
        cookie.isHttpOnly = true
        httpServletResponse.addCookie(cookie)
        httpServletResponse.contentType = "text/plain"
    }

    @Scheduled(fixedRate = 60000)
    fun balanceIncrease() {
        val userAccounts = accountRepository!!.findAll()
        for (userAccount in userAccounts) {
            if (userAccount.balance < userAccount.maxPrBalance) {
                userAccount.balance = (userAccount.balance + userAccount.balance * 0.05).toFloat()
                accountRepository!!.save(userAccount)
            }
        }
    }
}
