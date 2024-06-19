package za.co.bank.account.dto;

import java.io.Serializable;

public class WithdrawalResponse implements Serializable{

    private BankAccountDTO bankAccountDetails;
    private Boolean success;
    private AccountError error;

    public WithdrawalResponse(){
    }

    public BankAccountDTO getBankAccountDetails() {
        return bankAccountDetails;
    }

    public void setBankAccountDetails(BankAccountDTO bankAccountDetails) {
        this.bankAccountDetails = bankAccountDetails;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public AccountError getError() {
        return error;
    }

    public void setError(AccountError error) {
        this.error = error;
    }
}
