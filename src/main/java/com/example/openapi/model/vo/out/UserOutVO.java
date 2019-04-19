package com.example.openapi.model.vo.out;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;

import java.util.Date;
import java.util.logging.Logger;

/**
 * @Description: TODO
 * @Author gxy
 * @Date 2019/3/30
 */
@Data
@Accessors(chain = true)
@Builder
public class UserOutVO {
    private String name;
    private Integer age;
    private String address;
    private Date birth;
}

