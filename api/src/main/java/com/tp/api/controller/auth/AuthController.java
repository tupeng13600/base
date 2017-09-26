package com.tp.api.controller.auth;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @GetMapping("/login")
    public Object login(){
        Map<String, Object> jsonObject = new HashMap<>();
        jsonObject.put("name", "涂鹏");
        return jsonObject;
    }
}
