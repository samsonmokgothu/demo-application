package za.co.bank.platform.sns.service;

import za.co.bank.platform.common.SnsRequest;
import za.co.bank.platform.common.SnsResponse;

public interface SnsClientService {

    public SnsResponse publish(SnsRequest request);
}
