package com.spidey.openmusicapi.entity;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserDTO extends UserDO {

    @NotBlank(message = "密码不能为空")
    private String password;

}
