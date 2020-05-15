package com.digitalacademy.loan.service;

import com.digitalacamemy.loan.exception.LoanException;
import com.digitalacamemy.loan.model.LoanInfo;
import com.digitalacamemy.loan.service.LoanService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.*;

public class LoanServiceTest {

    @InjectMocks
    LoanService loanService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        loanService = new LoanService();
    }

    // 3 test case
    @DisplayName("Test get loam bu id = 1 should return loan infor")
    @Test
    void testGetLoanInfoByIdEquals1() throws Exception {
        LoanInfo resq = loanService.getLoanInfoById(1L);
        assertEquals("1", resq.getId().toString());
        assertEquals("OK", resq.getStatus());
        assertEquals("102-222-222", resq.getAccountPayable());
        assertEquals("10-232-445-78", resq.getAccountReceivable());
        assertEquals(3000.00, resq.getAmount());
    }

    @DisplayName("Test get loam bu id = 1 should throw loan Exception")
    @Test
    void TestGetLoanInfoBbId2(){
        Long reqParam = 2L;
        LoanException thrown = assertThrows(LoanException.class,
                () -> loanService.getLoanInfoById(reqParam), "Expexted loadInfoById(resqParam) to throw, but it didn't");
                assertEquals(400, thrown.getHttpStatus().value());
                assertEquals("LOAN4002", thrown.getLoanError().getCode());
                assertEquals("Loan information not found", thrown.getLoanError().getMessage());

    }

    @DisplayName("Test get loam bu id != 1, 2 should throw Loan info")
    @Test
    void TestGetLoanInfoBbId3(){
        Long reqParam = 3L;
        Exception thrown = assertThrows(Exception.class,
                () -> loanService.getLoanInfoById(reqParam), "Expexted loadInfoById(resqParam) to throw, but it didn't");
        assertEquals("Test throw new exception", thrown.getMessage());
    }
}
