package com.tp.api.controller.user.req;

import com.tp.api.enums.SexType;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class InitReq {

    @NotBlank
    @Length(max = 15)
    private String nick;

    @NotBlank
    private String avatar;

    @NotNull
    private Date birthday;

    @NotNull
    private SexType sex;

    @Length(max = 80)
    private String address;

}
