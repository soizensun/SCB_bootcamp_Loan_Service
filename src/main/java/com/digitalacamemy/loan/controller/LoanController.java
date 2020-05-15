package com.digitalacamemy.loan.controller;


import com.digitalacamemy.loan.constants.LoanError;
import com.digitalacamemy.loan.constants.Response;
import com.digitalacamemy.loan.exception.LoanException;
import com.digitalacamemy.loan.model.LoanInfo;
import com.digitalacamemy.loan.model.ResponseModel;
import com.digitalacamemy.loan.model.Status;
import com.digitalacamemy.loan.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;


@RestController
@RequestMapping(path = "/loan")
public class LoanController {

    private static final Logger log = LogManager.getLogger(LoanController.class.getName());
    private LoanService loanService;

    @Autowired
    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping("/info/{id}")
    public HttpEntity<ResponseModel> getLoanInfoByCustomerId(@PathVariable Long id) throws Exception {
        log.info("Get loan by customer id " + id);
        try{
            LoanInfo res = loanService.getLoanInfoById(id);
            Status status = new Status(Response.SUCCESS_CODE.getContent(), Response.SUCCESS.getContent());
            return ResponseEntity.ok(new ResponseModel(status, res));
        }
        catch (LoanException e){
            log.error("loan Exception by id " + id);
            LoanError loanError = e.getLoanError();
            return ResponseEntity.ok( new ResponseModel( new Status( loanError.getCode(), loanError.getMessage() ) ) );
        }
        catch (Exception e){
            log.error("Exception by id " + id);
            LoanError loanError = LoanError.GET_LOAN_INFO_FOUND;
            return new ResponseModel(
                    new Status(loanError.getCode(), loanError.getMessage() ), loanError.getMessage()
            ).build(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
