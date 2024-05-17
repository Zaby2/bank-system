package com.bank.Bank.System.JWTGenerator;

import com.bank.Bank.System.dto.UserDTO;
import lombok.NonNull;

public interface JWTProvider {
    public String generateAccessToken(@NonNull UserDTO userDTO);
    public String getUserNameFromToken(String token);
    public String generateSecretKey();
}
