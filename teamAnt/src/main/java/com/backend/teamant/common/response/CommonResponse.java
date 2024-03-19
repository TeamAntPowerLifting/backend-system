package com.backend.teamant.common.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommonResponse<T> {
    private static final String SUCCESS_STATUS = "SUCCESS";
    private static final String ERROR_STATUS = "ERROR";
    private static final String FAIL_STATUS = "FAIL";

    private String status;
    private T data;
    private String message;

    public static <T> CommonResponse<T> ok(T data) {
        return CommonResponse.<T>builder()
                .data(data)
                .status(SUCCESS_STATUS)
                .build();
    }

    public static <T> CommonResponse<T> error(String errorMessage) {
        return CommonResponse.<T>builder()
                .status(ERROR_STATUS)
                .message(errorMessage)
                .build();
    }

    public static <T> CommonResponse<T> ok() {
        return CommonResponse.<T>builder()
                .status(ERROR_STATUS)
                .build();
    }

}
