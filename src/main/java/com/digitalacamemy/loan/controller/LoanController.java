package com.digitalacamemy.loan.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
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

    @Autowired
    public LoanController() {

    }

    @GetMapping("/info/{id}")
    public HttpEntity getLoanInfoByCustomerId(@PathVariable Long id){
        log.info("Get loan by customer id" + id);
        System.out.println("Get loan by customer id" + id);

        return ResponseEntity.ok().build();
    }
}
