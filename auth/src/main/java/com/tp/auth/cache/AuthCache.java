package com.tp.auth.cache;

import com.tp.auth.token.Token;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by tupeng on 2017/8/8.
 */
public abstract class AuthCache {

    private static Logger logger = LoggerFactory.getLogger(AuthCache.class);

    private static final Long expire = 30 * 60 * 1000L;

    private static final Map<String, Token> tokenResource = new ConcurrentHashMap<>();

    private static final Map<String, Long> tokenTime = new ConcurrentHashMap<>();

    public static Token get(String token) {
        if (expire(token)) {
            remove(token);
            return null;
        }
        tokenTime.put(token, System.currentTimeMillis());
        return tokenResource.get(token);
    }

    public static void put(String key, Token token) {
        if (StringUtils.isBlank(key)) {
            return;
        }
        tokenResource.put(key, token);
        tokenTime.put(key, System.currentTimeMillis());
    }

    private static Boolean expire(String token) {
        return null == tokenTime.get(token) || System.currentTimeMillis() > (tokenTime.get(token) + expire);
    }

    public static void remove(String token) {
        if (StringUtils.isBlank(token)) {
            return;
        }
        tokenResource.remove(token);
        tokenTime.remove(token);
    }

    public static void update() {
        Set<String> expireTokenList = new HashSet<>();
        for(Map.Entry<String, Token> entry : tokenResource.entrySet()) {
            String token = entry.getKey();
            if (expire(token)){
                expireTokenList.add(token);
            }
        }
        if(CollectionUtils.isNotEmpty(expireTokenList)) {
            logger.info("清除无效token:{}", expireTokenList);
            expireTokenList.forEach(expireToken -> {
                remove(expireToken);
            });
        }
    }

}
