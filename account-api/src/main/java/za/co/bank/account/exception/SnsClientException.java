package za.co.bank.account.exception;

public class SnsClientException extends Exception{

    private static final long serialVersionUID = 1L;

    public SnsClientException(String message){
        super(message);
    }
    public SnsClientException(String message, Throwable t){
        super(message, t);
    }

    public SnsClientException(Throwable t){
        super(t);
    }
}
