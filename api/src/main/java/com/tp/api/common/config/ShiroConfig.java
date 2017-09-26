package com.tp.api.common.config;

import com.tp.api.common.auth.AuthService;
import com.tp.auth.AuthFilterFactoryBean;
import com.tp.auth.manager.SecurityManager;
import com.tp.auth.service.AuthMessageService;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.filter.DelegatingFilterProxy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tupeng on 2017/7/27.
 * 拦截器相关配置
 */
@Configuration
public class ShiroConfig {

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        filterRegistration.setFilter(new DelegatingFilterProxy("shiroFilter"));
        //该值缺省为false,表示生命周期由SpringApplicationContext管理,设置为true则表示由ServletContainer管理
        filterRegistration.addInitParameter("targetFilterLifecycle", "true");
        filterRegistration.setEnabled(true);
        filterRegistration.addUrlPatterns("/*");//可以自己灵活的定义很多，避免一些根本不需要被Shiro处理的请求被包含进来
        return filterRegistration;
    }

    @Bean("authService")
    public AuthService authService() {
        return new AuthService();
    }

    @Bean("securityManager")
    public SecurityManager securityManager(AuthMessageService authService) {
        return new SecurityManager(authService);
    }


    @Bean("shiroFilter")
    public AuthFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        return new AuthFilterFactoryBean(securityManager, getDefineFilterChain());
    }

    /**
     * Shiro生命周期处理器
     *
     * @return
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 开启shiro注解
     *
     * @return
     */
    @Bean
    @DependsOn({"lifecycleBeanPostProcessor"})
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * 在这里添加shiro的拦截路径
     * 例如：defineFilterChain.add("/user/info = anon");
     *
     * @return
     */
    private List<String> getDefineFilterChain() {
        List<String> defineFilterChain = new ArrayList<>();
        defineFilterChain.add("/swagger-resources/** = anon");
        defineFilterChain.add("/user = anon");
        defineFilterChain.add("/swagger-ui.html = anon");
        defineFilterChain.add("/webjars/** = anon");
        defineFilterChain.add("/v2/** = anon");
        defineFilterChain.add("/xcjy/** = anon");
        defineFilterChain.add("/assets/** = anon");
        defineFilterChain.add("/** = upcAuth");
        return defineFilterChain;
    }


}
