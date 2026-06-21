package com.bank.model;

import com.bank.exception.InsufficientBalanceException;
import java.math.BigDecimal;

public class CurrentAccount extends Account {

    private final BigDecimal overdraftLimit;

    public CurrentAccount(String accountNumber,
                          BigDecimal initialBalance,
                          BigDecimal overdraftLimit) {

        super(accountNumber, initialBalance);

        if (overdraftLimit == null || overdraftLimit.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Overdraft limit cannot be negative");
        }

        this.overdraftLimit = overdraftLimit;
    }

    public BigDecimal getOverdraftLimit() {
        return overdraftLimit;
    }

    @Override
    public void withdraw(BigDecimal amount)
            throws InsufficientBalanceException {

        validateAmount(amount);

        BigDecimal projectedBalance = balance.subtract(amount);

        BigDecimal negativeLimit = overdraftLimit.negate();

        if (projectedBalance.compareTo(negativeLimit) < 0) {
            throw new InsufficientBalanceException(
                    "Overdraft limit exceeded"
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

    @Override
    public void sendTransfer(BigDecimal amount, String targetAccount)
            throws InsufficientBalanceException {

        validateAmount(amount);

        BigDecimal projectedBalance = balance.subtract(amount);
        BigDecimal negativeLimit = overdraftLimit.negate();

        if (projectedBalance.compareTo(negativeLimit) < 0) {
            throw new InsufficientBalanceException(
                    "Overdraft limit exceeded for transfer"
            );
        }

        debitBalance(amount);

        recordTransaction(
                new Transaction(
                        Transaction.Type.TRANSFER_OUT,
                        amount,
                        getAccountNumber(),
                        targetAccount
                )
        );
    }

}