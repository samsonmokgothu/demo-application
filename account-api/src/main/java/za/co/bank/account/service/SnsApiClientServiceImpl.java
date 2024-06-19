package za.co.bank.account.service;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import za.co.bank.account.configuration.SnsApiConfigProperties;
import za.co.bank.account.exception.SnsClientException;
import za.co.bank.platform.common.SnsRequest;
import za.co.bank.platform.common.SnsResponse;
import za.co.bank.platform.common.WithdrawalEvent;

@Service
@EnableConfigurationProperties(SnsApiConfigProperties.class)
@Transactional(Transactional.TxType.NOT_SUPPORTED) // Suspends calling transaction and will resume after this returns
public class SnsApiClientServiceImpl implements SnsApiClientService{
    private static final Logger log = LoggerFactory.getLogger(SnsApiClientServiceImpl.class);

    private RestTemplate restTemplate;
    private SnsApiConfigProperties properties;

    @Autowired
    public SnsApiClientServiceImpl(@Qualifier("snsRetryableRestTemplate") RestTemplate restTemplate, SnsApiConfigProperties snsApiConfigProperties){
        this.restTemplate = restTemplate;
        this.properties = snsApiConfigProperties;
    }

    @Retryable
    @Override
    public void publish(WithdrawalEvent event) throws SnsClientException {
        try{
            SnsRequest request = new SnsRequest();
            request.setEvent(event);
            request.setDestinationTopic(properties.getTopicArn());
            ResponseEntity<SnsResponse> responseEntity =  restTemplate.postForEntity(properties.getUrl() + properties.getEndpoint() ,request, SnsResponse.class);
        }catch(Exception ex){
            log.error("Failed to call sns-client api. ", ex);
            throw new SnsClientException(ex);
        }
    }
}
