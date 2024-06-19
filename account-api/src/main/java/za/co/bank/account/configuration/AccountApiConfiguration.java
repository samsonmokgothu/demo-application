package za.co.bank.account.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.client.RestTemplateCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableConfigurationProperties(SnsApiConfigProperties.class)
public class AccountApiConfiguration {

    private static final Logger log = LoggerFactory.getLogger(AccountApiConfiguration.class);
    @Autowired
    private SnsApiConfigProperties properties;

    @Bean(name = "snsRetryableRestTemplate")
    public RestTemplate snsRetryableRestTemplate(){
        return builder(retryRestTemplateCustomizer()).build();
    }

    private RestTemplateBuilder builder(RestTemplateCustomizer customizer){
        return new RestTemplateBuilder(customizer);
    }

    private RestTemplateCustomizer retryRestTemplateCustomizer() {
        return restTemplate -> restTemplate.getInterceptors().add((request, body, execution) -> {

            RetryTemplate retryTemplate = new RetryTemplate();
            retryTemplate.setRetryPolicy(new SimpleRetryPolicy(properties.getRetryCount()));
            try {
                return retryTemplate.execute(context -> {
                    log.info("Connecting to {}", properties.getUrl() + properties.getEndpoint());
                    return execution.execute(request, body); });
            } catch (Throwable throwable) {
                throw new RuntimeException(throwable);
            }
        });
    }
}
