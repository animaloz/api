package com.example.openapi.model.vo.in;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.util.Date;

/**
 * @Description: TODO
 * @Author gxy
 * @Date 2019/3/30
 */
@Data
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInVO{
    @NotBlank
    private String uuid;
    private String name;
    private Integer age;
    private String address;
    private Date birth;
}
