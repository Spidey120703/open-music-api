package com.spidey.openmusicapi.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@TableName("user")
public class ResetPasswordDTO {

    @NotBlank(message = "重置的用户名不能为空")
    private String username;

    @NotBlank(message = "重置的密码不能为空")
    private String password;

}
