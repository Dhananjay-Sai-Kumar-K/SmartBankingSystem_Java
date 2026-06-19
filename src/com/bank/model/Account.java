package com.bank.model;

import com.bank.exception.InsufficientBalanceException;
import java.math.BigDecimal;

public abstract class Account {

    private final String accountNumber;
    protected BigDecimal balance;

    public Account(String accountNumber, BigDecimal initialBalance) {

        if (accountNumber == null || accountNumber.isBlank()) {
            throw new IllegalArgumentException("Account number cannot be empty");
        }

        if (initialBalance == null || initialBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Initial balance cannot be negative");
        }

        this.accountNumber = accountNumber;
        this.balance = initialBalance;
    }

    // ---------------- Core Getters ----------------

    public String getAccountNumber() {
        return accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    // ---------------- Deposit ----------------

    public final void deposit(BigDecimal amount) {

        validateAmount(amount);

        balance = balance.add(amount);
    }

    // ---------------- Withdrawal (POLYMORPHIC) ----------------

    public abstract void withdraw(BigDecimal amount)
            throws InsufficientBalanceException;

    // ---------------- Protected Primitive Operation ----------------
    // ONLY modifies state safely — no business rules

    protected final void deductBalance(BigDecimal amount) {

        validateAmount(amount);

        if (balance.compareTo(amount) < 0) {
            throw new IllegalStateException("Insufficient internal balance state");
        }

        balance = balance.subtract(amount);
    }

    // ---------------- Shared Validation ----------------

    protected void validateAmount(BigDecimal amount) {

        if (amount == null) {
            throw new IllegalArgumentException("Amount cannot be null");
        }

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
    }

    // ---------------- Utility ----------------

    @Override
    public String toString() {
        return "Account{" +
                "accountNumber='" + accountNumber + '\'' +
                ", balance=" + balance +
                '}';
    }
}