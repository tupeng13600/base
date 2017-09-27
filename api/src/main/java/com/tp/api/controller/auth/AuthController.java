package com.tp.api.controller.auth;


import com.tp.auth.cache.TokenThreadLocal;
import com.tp.auth.util.CurrentUser;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @PostMapping("/login")
    public Object login(){
        Map<String, Object> jsonObject = new HashMap<>();
        jsonObject.put("phone", CurrentUser.phone());
        jsonObject.put("access-token", TokenThreadLocal.get());
        return jsonObject;
    }
}
