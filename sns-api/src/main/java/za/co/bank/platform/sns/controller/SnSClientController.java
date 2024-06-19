package za.co.bank.platform.sns.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import za.co.bank.platform.common.SnsRequest;
import za.co.bank.platform.common.SnsResponse;
import za.co.bank.platform.sns.service.SnsClientService;

@RestController
@RequestMapping("/bank/sns-client")
public class SnSClientController {

    @Autowired
    private SnsClientService snsClientService;

    @RequestMapping(value = "/publish", method = RequestMethod.POST)
    public SnsResponse publish(@RequestBody SnsRequest request){

        return snsClientService.publish(request);
    }
}
