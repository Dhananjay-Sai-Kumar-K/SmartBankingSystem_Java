package com.bank.service;

import com.bank.exception.InsufficientBalanceException;
import com.bank.model.Account;

import java.math.BigDecimal;

public class BankService {

    public void transfer(Account source,
                         Account target,
                         BigDecimal amount)
            throws InsufficientBalanceException {

        if (source == null || target == null) {
            throw new IllegalArgumentException("Accounts cannot be null");
        }

        if (source == target) {
            throw new IllegalArgumentException("Cannot transfer to same account");
        }

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Invalid transfer amount");
        }

        source.sendTransfer(amount, target.getAccountNumber());
        target.receiveTransfer(amount, source.getAccountNumber());
    }
}