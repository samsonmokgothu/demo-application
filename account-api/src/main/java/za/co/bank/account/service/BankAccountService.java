package za.co.bank.account.service;

import za.co.bank.account.dto.WithdrawalRequest;
import za.co.bank.account.dto.WithdrawalResponse;
import za.co.bank.account.exception.AccountException;

public interface BankAccountService {

    public WithdrawalResponse transact(WithdrawalRequest request) throws AccountException;
    public void validateRequest(WithdrawalRequest request) throws AccountException;
}
