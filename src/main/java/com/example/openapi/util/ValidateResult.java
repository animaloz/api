package com.example.openapi.util;

import lombok.Builder;
import lombok.Data;

import javax.validation.ConstraintViolation;
import java.util.Set;

/**
 * @Description: TODO
 * @Author gxy
 * @Date 2019/3/30
 */
@Data
@Builder
public class ValidateResult {
    private Boolean hasErrors;
    private String errorMsg;
    private Set<ConstraintViolation<Object>> errors;
}
