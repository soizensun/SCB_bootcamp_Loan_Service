package com.digitalacamemy.loan.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Status implements Serializable {
    @JsonProperty("code")
    private String code;

    @JsonProperty("message")
    private String message;

    public Status() {}

    public Status(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
