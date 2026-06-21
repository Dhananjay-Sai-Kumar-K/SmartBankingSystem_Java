package com.bank.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Transaction {

    public enum Type {
        DEPOSIT,
        WITHDRAW,
        TRANSFER_IN,
        TRANSFER_OUT
    }

    private final String transactionId;
    private final Type type;
    private final BigDecimal amount;

    private final LocalDateTime timestamp;

    private final String sourceAccount;
    private final String targetAccount;

    public Transaction(Type type,
                       BigDecimal amount,
                       String sourceAccount,
                       String targetAccount) {

        this.transactionId = UUID.randomUUID().toString();
        this.type = type;
        this.amount = amount;
        this.timestamp = LocalDateTime.now();
        this.sourceAccount = sourceAccount;
        this.targetAccount = targetAccount;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public Type getType() {
        return type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getSourceAccount() {
        return sourceAccount;
    }

    public String getTargetAccount() {
        return targetAccount;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id='" + transactionId + '\'' +
                ", type=" + type +
                ", amount=" + amount +
                ", time=" + timestamp +
                ", from='" + sourceAccount + '\'' +
                ", to='" + targetAccount + '\'' +
                '}';
    }
}