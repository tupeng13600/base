package com.tp.api.controller.auth;


import com.tp.api.bean.User;
import com.tp.api.controller.user.res.UserInfoRes;
import com.tp.api.service.UserService;
import com.tp.auth.cache.TokenThreadLocal;
import com.tp.auth.util.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    /**
     * 登录并获取用户信息
     *
     * @return
     */
    @PostMapping("/login")
    public UserInfoRes login() {
        User user = userService.getByPhone(CurrentUser.phone());
        UserInfoRes infoRes = new UserInfoRes(user);
        infoRes.setAccessToken(TokenThreadLocal.get());
        return infoRes;
    }

    public static void main(String[] args) {

    }
}
