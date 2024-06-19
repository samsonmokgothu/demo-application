package za.co.bank.account.service;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import za.co.bank.account.dto.AccountError;
import za.co.bank.account.dto.BankAccountDTO;
import za.co.bank.account.dto.ErrorType;
import za.co.bank.account.dto.WithdrawalRequest;
import za.co.bank.account.dto.WithdrawalResponse;
import za.co.bank.account.exception.AccountException;
import za.co.bank.account.repository.BankAccount;
import za.co.bank.account.repository.BankAccountRepository;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@Transactional(Transactional.TxType.REQUIRED)
public class BankAccountServiceImpl implements BankAccountService{

    private static final Logger log = LoggerFactory.getLogger(BankAccountServiceImpl.class);

    private BankAccountRepository bankAccountRepository;

    @Autowired
    public BankAccountServiceImpl(BankAccountRepository bankAccountRepository){
        this.bankAccountRepository = bankAccountRepository;
    }

    @Override
    public WithdrawalResponse transact(WithdrawalRequest request) throws AccountException {
        Optional<BankAccount> bankAccountOptional = bankAccountRepository.findById(request.getAccountId());
        BankAccount bankAccount =  bankAccountOptional.isPresent() ? bankAccountOptional.get(): null;
        if(bankAccount == null){
            throw new AccountException("Account not found", ErrorType.ACCOUNT_NOT_FOUND);
        }

        WithdrawalResponse response = new WithdrawalResponse();
        if(bankAccount.getBalance().compareTo(request.getAmount()) > 0 ){
           log.info("Account {} has sufficient balance of R{} for withdrawal.", request.getAccountId(), bankAccount.getBalance());
           log.info("Withdrawing R{} amount from {} account.", request.getAmount(), request.getAccountId());

           bankAccount.setBalance(bankAccount.getBalance().subtract(request.getAmount()));
           bankAccountRepository.save(bankAccount);
           response.setSuccess(true);
           log.info("Withdrawal of R{} amount from {} account was successful. New balance is R{}", request.getAmount(), request.getAccountId(), bankAccount.getBalance());
        }else{
            log.info("Account {} has insufficient balance of R{} for withdrawal. Requested amount was R{}.", request.getAccountId(), bankAccount.getBalance(), request.getAmount());
            response.setSuccess(false);
            response.setError(new AccountError(ErrorType.INSUFFICIENT_FUNDS_ERROR.getErrorCode(),
                    ErrorType.INSUFFICIENT_FUNDS_ERROR.getErrorMessage()));
        }
        response.setBankAccountDetails(new BankAccountDTO());
        response.getBankAccountDetails().setAccountId(bankAccount.getAccountId());
        response.getBankAccountDetails().setBalance(bankAccount.getBalance());
        return response;
    }

    @Override
    public void validateRequest(WithdrawalRequest request) throws AccountException {

        if(request == null){
            throw new AccountException("Ivalid request", ErrorType.INVALID_REQUEST_ERRROR);
        }

        if(request.getAccountId() == null || request.getAccountId() <= 0 ){
            throw new AccountException("Provide valid account number.", ErrorType.INVALID_REQUEST_ERRROR);
        }

        if(request.getAmount() == null || request.getAmount().compareTo(BigDecimal.ZERO) < 1 ){
            throw new AccountException("Incorrect amount entered.", ErrorType.INVALID_REQUEST_ERRROR);
        }

    }

}
