package com.sample.test.country.api.country.api.exception.pojo;

import com.sample.test.country.api.country.api.util.Constant;
import lombok.Getter;



@Getter
public enum ExceptionCode {

    SBT001(Constant.ERROR_TYPE_SERVER),
    SBT002(Constant.ERROR_TYPE_INVALID_INPUT), //No resource found exception occurs
    SBT003(Constant.ERROR_TYPE_INVALID_INPUT), //Method Argument Not Valid Exception; // Employee details deleting failed
    SBT004(Constant.ERROR_TYPE_SERVER); //sb data fetching failed



    private final String errorType;

    ExceptionCode(String type) {
        this.errorType = type;
    }

    public String getType() {
        return errorType;
    }
}

