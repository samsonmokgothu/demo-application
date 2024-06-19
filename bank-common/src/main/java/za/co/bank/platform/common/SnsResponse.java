package za.co.bank.platform.common;

import java.io.Serializable;

public class SnsResponse implements Serializable {
    private static final long serialVersionUID = 1L;

    private Status status;
    private String responseDetails;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getResponseDetails() {
        return responseDetails;
    }

    public void setResponseDetails(String responseDetails) {
        this.responseDetails = responseDetails;
    }
}
