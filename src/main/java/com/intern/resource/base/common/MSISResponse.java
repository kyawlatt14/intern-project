package com.intern.resource.base.common;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class MSISResponse implements Serializable {
    public String msg;
    public Object data;
    public Integer statusCode;
    private long timestamp;

    public static MSISResponse fail(String errorMsg) {
        return MSISResponse.builder()
                .statusCode(Constant.FAILURE_CODE)
                .msg(errorMsg)
                .data("*****")
                .timestamp(System.currentTimeMillis()/1000)
                .build();
    }
    public static MSISResponse success(String msg, Object data) {
        return MSISResponse.builder()
                .statusCode(Constant.SUCCESS_CODE)
                .msg(msg)
                .data(data)
                .timestamp(System.currentTimeMillis()/1000)
                .build();
    }
    public static MSISResponse fail(String errorMsg, Object data) {
        return MSISResponse.builder()
                .statusCode(Constant.FAILURE_CODE)
                .msg(errorMsg)
                .data(data.toString())
                .timestamp(System.currentTimeMillis()/1000)
                .build();
    }
}
