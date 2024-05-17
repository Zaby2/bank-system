package com.bank.Bank.System.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserAccount {
    @Id
    @GeneratedValue
    private Long accountId;
    private Float balance;
    private String firstName;
    private String secondName;
    private String surName;
    private LocalDateTime birthDate;
    private String phoneNumbers ;
    private String email;
    private String login;
    private String password;
    private float maxPrBalance;
}
