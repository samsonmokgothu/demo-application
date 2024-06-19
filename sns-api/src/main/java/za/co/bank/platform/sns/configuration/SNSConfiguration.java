package za.co.bank.platform.sns.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;

@Configuration
@EnableConfigurationProperties(ConfigProperties.class)
public class SNSConfiguration {

    @Autowired
    private ConfigProperties configProperties;

    @Bean
    public SnsClient snsClient(){
        return SnsClient.builder().region(Region.of(configProperties.getRegion())).build();
    }
}