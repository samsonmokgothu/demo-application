package za.co.bank.account.service;

import za.co.bank.account.exception.SnsClientException;
import za.co.bank.platform.common.WithdrawalEvent;

public interface SnsApiClientService {

    public void publish(WithdrawalEvent event) throws SnsClientException;
}
