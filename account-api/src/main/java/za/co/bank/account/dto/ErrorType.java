package za.co.bank.account.dto;

public enum ErrorType {
    INSUFFICIENT_FUNDS_ERROR(4000, "Insufficient funds available"),
    ACCOUNT_NOT_FOUND (5000, "Account number not found"),
    INVALID_REQUEST_ERRROR (6000, "Invalid Request"),
    BANK_SERVICE_ERROR (3000, "Technical error occurred");

    private int errorCode;
    private String errorMessage;

    ErrorType(int code, String message){
        this.errorCode = code;
        this.errorMessage = message;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
