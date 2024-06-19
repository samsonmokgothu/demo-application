package za.co.bank.platform.common;

import java.io.Serializable;

public class SnsRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private String destinationTopic;
    private Object event;

    public String getDestinationTopic() {
        return destinationTopic;
    }

    public void setDestinationTopic(String destinationTopic) {
        this.destinationTopic = destinationTopic;
    }

    public Object getEvent() {
        return event;
    }

    public void setEvent(Object event) {
        this.event = event;
    }
}
