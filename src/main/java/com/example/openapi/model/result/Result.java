package com.example.openapi.model.result;

import lombok.Builder;
import lombok.Data;

/**
 * @Description: TODO
 * @Author gxy
 * @Date 2019/3/30
 */
@Data
@Builder
public class Result<T> {
    private String msg;
    private boolean success;
    private T data;
    private Integer errCode;
}
