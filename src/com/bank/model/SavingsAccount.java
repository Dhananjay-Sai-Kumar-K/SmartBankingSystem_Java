package com.bank.model;

import com.bank.exception.InsufficientBalanceException;
import java.math.BigDecimal;

public class SavingsAccount extends Account {

    private final BigDecimal minimumBalance;

    public SavingsAccount(String accountNumber,
                          BigDecimal initialBalance,
                          BigDecimal minimumBalance) {

        super(accountNumber, initialBalance);

        if (minimumBalance == null || minimumBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Minimum balance cannot be negative");
        }

        this.minimumBalance = minimumBalance;
    }

    public BigDecimal getMinimumBalance() {
        return minimumBalance;
    }

    @Override
    public void withdraw(BigDecimal amount)
            throws InsufficientBalanceException {

        validateAmount(amount);

        BigDecimal projectedBalance = balance.subtract(amount);

        if (projectedBalance.compareTo(minimumBalance) < 0) {
            throw new InsufficientBalanceException(
                    "Withdrawal violates minimum balance requirement"
            );
        }

        deductBalance(amount);

        recordTransaction(
                new Transaction(
                        Transaction.Type.WITHDRAW,
                        amount,
                        getAccountNumber(),
                        null
                )
        );
    }