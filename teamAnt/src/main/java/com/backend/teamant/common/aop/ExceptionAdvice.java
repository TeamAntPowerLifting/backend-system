package com.backend.teamant.common.aop;

import com.backend.teamant.common.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice // global exception 처리
public class ExceptionAdvice {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse exceptionHandler(Exception e){
        return ErrorResponse.builder()
                .errorMessage(e.toString())
                .build();
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ErrorResponse badCredentialException(BadCredentialsException e) {
        return ErrorResponse.builder()
                .errorMessage("아이디 또는 비밀번호를 확인하세요.")
                .build();
    }

    @ExceptionHandler(IllegalStateException.class)
    // 사용자가 값을 입력했지만 개발자 코드에서 처리할 수 없을 때
    public ErrorResponse IllegalStateException(IllegalStateException e) {
        return ErrorResponse.builder()
                .errorMessage(e.getMessage())
                .build();
    }

}
