package za.co.bank.account.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class WithdrawalRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long accountId;
    private BigDecimal amount;

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
