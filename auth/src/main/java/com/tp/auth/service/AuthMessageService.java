package com.tp.auth.service;


import com.tp.auth.model.LoginSuccessModel;
import com.tp.auth.model.User;

import java.util.Set;

/**
 * Created by tupeng on 2017/7/17.
 */
public interface AuthMessageService {

    User getUser(String username);

    Set<String> getRole(String username);

    void saveUserMessage(LoginSuccessModel model);

}
