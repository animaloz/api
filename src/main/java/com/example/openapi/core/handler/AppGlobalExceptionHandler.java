package com.example.openapi.core.handler;

import com.example.openapi.model.result.Result;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author sunny
 * @date: 2018-06-08 13:51
 * @des: 统一异常处理
 */
@ControllerAdvice
public class AppGlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public Result validExceptionHandle(MethodArgumentNotValidException exception) {
        BindingResult result = exception.getBindingResult();
        final List<FieldError> fieldErrors = result.getFieldErrors();
        StringBuilder builder = new StringBuilder();
        for (FieldError error : fieldErrors) {
            builder.append(error.getDefaultMessage() + ";");
        }
        return Result.builder().msg(builder.toString()).success(false).errCode(9000).build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result validExceptionHandle(Exception ex) {
        return Result.builder().msg(ex.getMessage()).success(false).errCode(500).build();
    }
}
