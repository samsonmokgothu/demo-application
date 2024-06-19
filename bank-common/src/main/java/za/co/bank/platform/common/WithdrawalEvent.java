package za.co.bank.platform.common;

import java.math.BigDecimal;

public class WithdrawalEvent {
    private BigDecimal balance;
    private Long accountId;
    private Status status;

    public WithdrawalEvent(BigDecimal balance, Long accountId, Status status){
        this.balance = balance;
        this.accountId = accountId;
        this.status = status;
    }

    public WithdrawalEvent() {
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
