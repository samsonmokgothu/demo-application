package za.co.bank.account.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("sns.client.api")
public class SnsApiConfigProperties {

    private String url;
    private String topicArn;
    private String endpoint;
    private int retryCount = 3;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTopicArn() {
        return topicArn;
    }

    public void setTopicArn(String topicArn) {
        this.topicArn = topicArn;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }
}
