package za.co.bank.account.dto;

import java.io.Serializable;

public class AccountError implements Serializable {

    private static final long serialVersionUID = 1L;

    private int errorCode;
    private String errorMessage;

    public AccountError() {
    }
    public AccountError(int errorCode, String errorMessage){
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
