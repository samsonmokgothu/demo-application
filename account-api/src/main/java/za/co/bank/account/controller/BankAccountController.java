package za.co.bank.account.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import za.co.bank.account.dto.AccountError;
import za.co.bank.account.dto.WithdrawalRequest;
import za.co.bank.account.dto.WithdrawalResponse;
import za.co.bank.account.exception.AccountException;
import za.co.bank.account.service.BankAccountService;
import za.co.bank.account.service.SnsApiClientService;
import za.co.bank.platform.common.Status;
import za.co.bank.platform.common.WithdrawalEvent;

import java.math.BigDecimal;

@RestController
@RequestMapping("/bank/account")
public class BankAccountController {

    private static final Logger log = LoggerFactory.getLogger(BankAccountController.class);

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private SnsApiClientService snsApiClientService;


    @RequestMapping(value = "/withdraw", method = RequestMethod.POST)
    public @ResponseBody WithdrawalResponse withdraw(@RequestBody WithdrawalRequest request){

        //First do withdrawal -- this will run in transactions
        WithdrawalResponse response;
        try {
            bankAccountService.validateRequest(request);
            response = bankAccountService.transact(request);
        } catch (AccountException e) {
            response = new WithdrawalResponse();
            response.setSuccess(false);
            response.setError(new AccountError(e.getErrorType().getErrorCode(), e.getErrorType().getErrorMessage() + ". " + e.getMessage()));
        }

        //Then publish event, this will not run in transaction
        try{
            WithdrawalEvent event = new WithdrawalEvent();
            event.setAccountId(request.getAccountId());
            event.setStatus(response.getSuccess() ?  Status.SUCCESS : Status.FAIL);
            event.setBalance(response.getBankAccountDetails() != null ? response.getBankAccountDetails().getBalance(): BigDecimal.valueOf(0.0));
            snsApiClientService.publish(event);
        } catch (Exception e) {
            log.warn("Failed to send message to sns", e);
        }

        return response;
    }
}
