package com.backend.teamant.common.aop;

import com.backend.teamant.common.response.CommonResponse;
import com.backend.teamant.common.response.ErrorResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice
public class ResponseAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // Controller 작업이 끝난 response를 beforeBodyWrite로 보낼 것인지 판단
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        // response 되기 전에 custom을 하여 response를 전달할 수 있다.
        if(body instanceof ErrorResponse){
            return CommonResponse.error(((ErrorResponse) body).getErrorMessage());
        }
        return CommonResponse.ok(body);
    }
}
