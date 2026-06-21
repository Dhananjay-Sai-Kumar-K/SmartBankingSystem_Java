package com.bank.model;

import com.bank.exception.InsufficientBalanceException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class Account {

    private final String accountNumber;
    protected BigDecimal balance;

    // 🆕 Transaction history added
    private final List<Transaction> transactions = new ArrayList<>();

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

    public String getAccountNumber() {
        return accountNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    // ---------------- TRANSACTION ACCESS ----------------

    public List<Transaction> getTransactions() {
        return Collections.unmodifiableList(transactions);
    }

    // ---------------- DEPOSIT ----------------

    public final void deposit(BigDecimal amount) {

        validateAmount(amount);

        balance = balance.add(amount);

        // 🆕 record transaction
        recordTransaction(
                new Transaction(
                        Transaction.Type.DEPOSIT,
                        amount,
                        null,
                        accountNumber
                )
        );
    }

    // ---------------- WITHDRAW (POLYMORPHIC) ----------------

    public abstract void withdraw(BigDecimal amount)
            throws InsufficientBalanceException;

    // ---------------- CORE BALANCE UPDATE ----------------

    protected final void deductBalance(BigDecimal amount) {

        validateAmount(amount);

        if (balance.compareTo(amount) < 0) {
            throw new IllegalStateException("Insufficient internal balance state");
        }

        balance = balance.subtract(amount);
    }

    // ---------------- TRANSACTION RECORDING ----------------

    protected final void recordTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    // ---------------- VALIDATION ----------------

    protected void validateAmount(BigDecimal amount) {

        if (amount == null) {
            throw new IllegalArgumentException("Amount cannot be null");
        }

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
    }


    protected final void creditBalance(BigDecimal amount) {
        validateAmount(amount);
        balance = balance.add(amount);
    }

    protected final void debitBalance(BigDecimal amount) {
        validateAmount(amount);
        balance = balance.subtract(amount);
    }

    public final void receiveTransfer(BigDecimal amount, String sourceAccount) {
        creditBalance(amount);
        recordTransaction(
                new Transaction(
                        Transaction.Type.TRANSFER_IN,
                        amount,
                        sourceAccount,
                        this.accountNumber
                )
        );
    }

    public abstract void sendTransfer(BigDecimal amount, String targetAccount)
            throws InsufficientBalanceException;


}