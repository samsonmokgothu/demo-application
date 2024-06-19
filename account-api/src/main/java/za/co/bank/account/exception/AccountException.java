package za.co.bank.account.exception;

import za.co.bank.account.dto.ErrorType;

public class AccountException extends Exception{

    private ErrorType errorType;

    public AccountException(String message, ErrorType errorType){
        super(message);
        this.errorType = errorType;
    }

    public AccountException(String message, Throwable throwable, ErrorType errorType){
        super(message, throwable);
        this.errorType = errorType;
    }

    public AccountException(Throwable throwable, ErrorType errorType){
        super(throwable);
        this.errorType = errorType;
    }

    public ErrorType getErrorType() {
        return errorType;
    }
}
