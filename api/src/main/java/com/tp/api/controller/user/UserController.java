package com.tp.api.controller.user;

import com.tp.api.common.handler.BaseResponse;
import com.tp.api.controller.user.req.InitReq;
import com.tp.api.controller.user.req.RegisterReq;
import com.tp.api.controller.user.res.UserInfoRes;
import com.tp.api.enums.VerifyType;
import com.tp.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 校验手机号码是否被使用
     *
     * @param phone
     * @return
     */
    @GetMapping("/phone/{phone}/isUsed")
    public BaseResponse isUsed(@PathVariable String phone){
        return new BaseResponse(userService.isUsed(phone));
    }

    /**
     * 注册用户
     *
     * @param req
     * @return
     */
    @PostMapping("/registration")
    public BaseResponse register(@RequestBody @Valid RegisterReq req) {
        return new BaseResponse(userService.register(req.getPhone(), req.getVerifyCode(), req.getImei()));
    }

    /**
     * 初始化用户信息
     *
     * @param req
     * @return
     */
    @PutMapping("/init")
    public UserInfoRes init(@RequestBody @Valid InitReq req) {
        return userService.init(req);
    }

    /**
     * 获取手机验证码
     * @param verifyType
     * @param phone
     * @return
     */
    @GetMapping("/{verifyType}/{phone}/verify")
    public void getVerifyCode(@PathVariable VerifyType verifyType, @PathVariable String phone){
        userService.getVerifyCode(verifyType, phone);
    }

}
