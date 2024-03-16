package com.backend.teamant.common.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorResponse {
    private String errorMessage;
}
