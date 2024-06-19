package za.co.bank.platform.sns.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;
import za.co.bank.platform.common.SnsRequest;
import za.co.bank.platform.common.SnsResponse;
import za.co.bank.platform.common.Status;
import za.co.bank.platform.sns.configuration.ConfigProperties;

@Service
@EnableConfigurationProperties(ConfigProperties.class)
public class SnsClientServiceImpl implements SnsClientService{

    private static final Logger logger = LoggerFactory.getLogger(SnsClientServiceImpl.class);

    private SnsClient snsClient;
    private ConfigProperties properties;

    @Autowired
    public SnsClientServiceImpl(SnsClient snsClient, ConfigProperties properties){
        this.snsClient = snsClient;
        this.properties = properties;
    }
    @Override
    public SnsResponse publish(SnsRequest request) {

        String event = null;
        SnsResponse snsResponse = new SnsResponse();
        try{
            event = JsonUtils.toJson(request.getEvent());
        }catch (Exception ex){
            logger.error("Error converting event to json", ex);
            snsResponse.setStatus(Status.FAIL);
            snsResponse.setResponseDetails("Error converting event to json. " + ex.getMessage());
            return snsResponse;
        }

        PublishRequest publishRequest = PublishRequest.builder().message(event)
                .topicArn(request.getDestinationTopic()).build();
        try{
            if(!properties.isTestMode()){
                PublishResponse response =  snsClient.publish(publishRequest);
            }else{
                logger.info("SNS Running in test mode. No conncetion to AWS.");
            }
            snsResponse.setStatus(Status.SUCCESS);
        }catch(Exception ex){
            logger.error("Error sending message to aws sns", ex);
            snsResponse.setStatus(Status.FAIL);
            snsResponse.setResponseDetails("Error sending message to aws sns. " + ex.getMessage());
        }

        return snsResponse;
    }
}
