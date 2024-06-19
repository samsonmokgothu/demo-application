package za.co.bank.platform.sns.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("aws.sns")
public class ConfigProperties {

    private String region="af-south-1"; //default to ZA
    private boolean testMode = false;

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public boolean isTestMode() {
        return testMode;
    }

    public void setTestMode(boolean testMode) {
        this.testMode = testMode;
    }
}
