package com.example.openapi.model.vo.in;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * @author lx
 * @version $Id: UserIn.java, v 0.1 2019-04-12 11:33:55 lx Exp $$
 */
class UserIn {

    /**
     *
     */
    @Getter
    @Setter
    private String uuid;

    /**
     *
     */
    @Getter
    @Setter
    private String name;

    /**
     *
     */
    @Getter
    @Setter
    private Integer age;

    /**
     *
     */
    @Getter
    @Setter
    private String address;

    /**
     *
     */
    @Getter
    @Setter
    private Date birth;


}
