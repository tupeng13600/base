package com.tp.api.controller.user.req;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

@Data
public class RegisterReq {

    @NotBlank
    @Length(min = 11, max = 11)
    private String phone;

    private String imei;

    @NotBlank
    @Length(min = 6, max = 6)
    private String verifyCode;

}
