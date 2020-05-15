package com.digitalacademy.loan.controller;


import com.digitalacamemy.loan.constants.LoanError;
import com.digitalacamemy.loan.controller.LoanController;
import com.digitalacamemy.loan.exception.LoanException;
import com.digitalacamemy.loan.model.LoanInfo;
import com.digitalacamemy.loan.service.LoanService;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = LoanControllerTest.class)
public class LoanControllerTest {

    @Mock
    LoanService loanService;

    @InjectMocks
    LoanController loanController;

    private MockMvc mvc;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        loanController = new LoanController(loanService);
        mvc = MockMvcBuilders.standaloneSetup(loanController).build();
    }

    @DisplayName("Test get loan info by customer id 1 should return loan information")
    @Test
    void TestGetLoanInfoById1() throws Exception {
        Long reqParam = 1L;

        LoanInfo loanInfo = new LoanInfo();
        loanInfo.setId(1L);
        loanInfo.setStatus("OK");
        loanInfo.setAccountPayable("102-222-222");
        loanInfo.setAccountReceivable("10-232-445-78");
        loanInfo.setAmount(3000.00);

        when(loanService.getLoanInfoById(reqParam)).thenReturn(loanInfo);

        MvcResult mvcResult = mvc.perform(get("/loan/info/" + reqParam))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn();

        JSONObject resq = new JSONObject(mvcResult.getResponse().getContentAsString());
        JSONObject status = new JSONObject(resq.getString("status"));
        JSONObject data = new JSONObject(resq.getString("data"));

        assertEquals("0", status.get("code").toString());
        assertEquals("success", status.get("message").toString());
        assertEquals(1, data.get("id"));
        assertEquals("102-222-222", data.get("account_payable"));
        assertEquals("10-232-445-78", data.get("account_receivable"));
        assertEquals(3000, data.get("amount"));

        verify(loanService, times(1)).getLoanInfoById(reqParam);
    }

    @DisplayName("Test get loan info by customer id 1 should return loan information")
    @Test
    void TestGetLoanInfoById2() throws Exception {
        Long reqParam = 2L;

        when(loanService.getLoanInfoById(reqParam)).thenThrow(
                new LoanException(LoanError.GET_LOAN_INFO_FOUND, HttpStatus.BAD_REQUEST)
        );

        MvcResult mvcResult = mvc.perform(get("/loan/info/" + reqParam))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn();

        JSONObject resq = new JSONObject(mvcResult.getResponse().getContentAsString());
        JSONObject status = new JSONObject(resq.getString("status"));

        assertEquals("LOAN4002", status.get("code").toString());
        assertEquals("Loan information not found", status.get("message").toString());
    }

    @DisplayName("Test get loan info by customer id 3 should throw exception")
    @Test
    void TestGetLoanInfoById3() throws Exception {
        Long reqParam = 3L;

        when(loanService.getLoanInfoById(reqParam)).thenThrow(
                new Exception("Test throw new exception")
        );

        MvcResult mvcResult = mvc.perform(get("/loan/info/" + reqParam))
                .andExpect(status().isInternalServerError())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn();

        JSONObject resq = new JSONObject(mvcResult.getResponse().getContentAsString());
        JSONObject status = new JSONObject(resq.getString("status"));

        assertEquals("LOAN4002", status.get("code").toString());
        assertEquals("Loan information not found", status.get("message").toString());
        assertEquals("Loan information not found", resq.get("data").toString());
    }
}
