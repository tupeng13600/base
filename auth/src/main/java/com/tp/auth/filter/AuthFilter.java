package com.tp.auth.filter;

import com.google.gson.Gson;
import com.tp.auth.cache.AuthCache;
import com.tp.auth.cache.TokenThreadLocal;
import com.tp.auth.model.RespModel;
import com.tp.auth.token.Token;
import com.tp.auth.util.SecurityUtil;
import org.apache.catalina.connector.Response;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截器
 */
public class AuthFilter extends BasicHttpAuthenticationFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthFilter.class);

    protected static String TOKEN_NAME = "Access-Token";

    private static final String PHONE_IDX = "phone";

    private static final String VERIFY_CODE_IDX = "verifyCode";

    private static final String IMEI_IDX = "IMEI";

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        Token token = getByAccessToken(WebUtils.toHttp(servletRequest).getHeader(TOKEN_NAME));
        if(null == token) { //根据手机号码和验证码进行登录
            String phone = WebUtils.toHttp(servletRequest).getParameter(PHONE_IDX);
            String verifyCode = WebUtils.toHttp(servletRequest).getParameter(VERIFY_CODE_IDX);
            String imei = WebUtils.toHttp(servletRequest).getParameter(IMEI_IDX);
            if (StringUtils.isBlank(phone) || StringUtils.isBlank(verifyCode)) {
                return this.onAccessDenied(WebUtils.toHttp(servletResponse), "手机号码和验证码不可为空");
            } else {
                String accessToken = SecurityUtil.randomString();
                LOGGER.info("生成的token:{}", accessToken);
                try {
                    token = new Token(accessToken, imei, phone, verifyCode);
                    SecurityUtils.getSubject().login(token);
                } catch (Exception e) {
                    return this.onAccessDenied(WebUtils.toHttp(servletResponse), "验证码不正确");
                }
            }
        }
        LOGGER.info("缓存token:{}", token.getToken());
        TokenThreadLocal.put(token.getToken());
        return true;
    }

    private Token getByAccessToken(String accessToken) {
        LOGGER.info("获取到的TOKEN:{}", accessToken);
        return AuthCache.get(accessToken);
    }

    private Boolean onAccessDenied(HttpServletResponse response, String message) throws Exception {
        RespModel respModel = new RespModel(false);
        response.setStatus(Response.SC_UNAUTHORIZED);
        respModel.setErrorMessage(message);
        String json = new Gson().toJson(respModel);
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8"); //设置编码格式为UTF-8
        response.getWriter().write(json);
        return false;
    }
}
