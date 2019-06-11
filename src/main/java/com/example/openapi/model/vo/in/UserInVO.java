package com.example.openapi.model.vo.in;

import com.example.openapi.model.enums.Sex;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel
public class UserInVO {
    @NotBlank
    private String uuid;
    private String name;
    private Integer age;
    private String address;
    private Date birth;
    @ApiModelProperty("性别")
    private Sex sex;
}
