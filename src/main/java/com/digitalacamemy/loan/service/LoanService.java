package com.digitalacamemy.loan.service;


import com.digitalacamemy.loan.constants.LoanError;
import com.digitalacamemy.loan.exception.LoanException;
import com.digitalacamemy.loan.model.LoanInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class LoanService {
    private static final Logger log = LogManager.getLogger(LoanService.class.getName());

    public LoanInfo getLoanInfoById(Long id) throws Exception{
        log.info("Get loan by customer id" + id);

        LoanInfo loneInfo = new LoanInfo();

        if(id.equals(1L)){
            loneInfo.setId(1L);
            loneInfo.setStatus("OK");
            loneInfo.setAccountPayable("102-222-222");
            loneInfo.setAccountReceivable("10-232-445-78");
            loneInfo.setAmount(3000);
        }
        else if (id.equals(2L)){
            log.info(id.toString());
            throw new LoanException(LoanError.GET_LOAN_INFO_FOUND, HttpStatus.BAD_REQUEST);
        }
        else{
            log.info(id.toString());
            throw new Exception("Test throw new exception");
        }

        return loneInfo;
    }
}
