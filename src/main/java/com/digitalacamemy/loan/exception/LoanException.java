package com.digitalacamemy.loan.exception;

import com.digitalacamemy.loan.constants.LoanError;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class LoanException extends Exception {
    private LoanError loanError;
    private HttpStatus httpStatus = HttpStatus.OK;

    public LoanException(LoanError loanError, HttpStatus httpStatus) {
        this.loanError = loanError;
        this.httpStatus = httpStatus;
    }
}
