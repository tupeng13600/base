package com.tp.api.common.mybatis;

import com.tp.api.common.utils.ReflectUtil;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.Properties;

/**
 * Created by tupeng on 2017/7/22.
 * <p>
 * mybatis 拦截器，统一对sql进行处理
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
public class MybatisQueryInterceptors implements Interceptor {

    private static Logger logger = LoggerFactory.getLogger(MybatisQueryInterceptors.class);

    private static final String whereCondition = " WHERE deleted = FALSE";

    private static final String andCondition = " AND deleted = FALSE";

    private static final String groupCondition = "group by";
    private static final String orderCondition = "order by";
    private static final String limitCondition = "limit ";


    public static final String
            [] deletedMatches = new String[]{"wheredeleted=false", "anddeleted=false"};

    public static final String sqlSuffix = ";";

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        RoutingStatementHandler handler = (RoutingStatementHandler) invocation.getTarget();
        StatementHandler delegate = (StatementHandler) ReflectUtil.getProperty(handler, "delegate");
        BoundSql boundSql = delegate.getBoundSql();
        if (isSelect(boundSql.getSql())) {
            String sql = appendDeleted(replaceEndOfSql(boundSql.getSql()));
            ReflectUtil.setProperty(boundSql, "sql", sql);
            logger.debug("开始执行sql : {}", sql);
        }
        return invocation.proceed();
    }

    private Boolean isSelect(String sql) {
        String des = sql.trim().toLowerCase();
        return des.startsWith("select");
    }

    private String getSuffix(String sql) {
        String desSql = sql.toLowerCase();
        String suffix = "";
        if (containsGroup(desSql)) {
            suffix = desSql.substring(desSql.indexOf(groupCondition), desSql.length());
        } else if (containsOrder(desSql)) {
            suffix = desSql.substring(desSql.indexOf(orderCondition), desSql.length());
        } else if (containsLimit(sql)) {
            suffix = desSql.substring(desSql.indexOf(limitCondition), desSql.length());
        }
        return suffix;
    }

    private String getPrefix(String sql) {
        String prefix = replaceEndOfSql(sql).toLowerCase();
        if (containsGroup(sql)) {
            prefix = prefix.substring(0, prefix.indexOf(groupCondition));
        } else if (containsOrder(sql)) {
            prefix = prefix.substring(0, prefix.indexOf(orderCondition));
        } else if (containsLimit(sql)) {
            prefix = prefix.substring(0, prefix.indexOf(limitCondition));
        }
        return prefix;
    }

    private Boolean containsGroup(String sql) {
        String des = sql.toLowerCase();
        return des.contains(groupCondition);
    }

    private Boolean containsOrder(String sql) {
        String des = sql.toLowerCase();
        return des.contains(orderCondition);
    }

    private Boolean containsLimit(String sql) {
        String des = sql.toLowerCase();
        return des.contains(limitCondition);
    }

    private String appendDeleted(String sql) {
        String sqlPrefix = getPrefix(sql);
        String sqlSuffix = getSuffix(sql);
        if (!matches(sql)) {
            StringBuilder builder = new StringBuilder();
            return sql.toLowerCase().contains("where") ?
                    builder.append(sqlPrefix).append(andCondition).append(" ").append(sqlSuffix).toString()
                    : builder.append(sqlPrefix).append(whereCondition).append(" ").append(sqlSuffix).toString();
        }
        return appendEndOfSql(sqlPrefix + sqlSuffix);
    }

    private String replaceEndOfSql(String sql) {
        return sql.replaceAll(sqlSuffix, "");
    }

    private String appendEndOfSql(String sql) {
        return sql + sqlSuffix;
    }

    private Boolean matches(String sql) {
        String tempSql = sql.replaceAll(" ", "").toLowerCase();
        for (String match : deletedMatches) {
            if (tempSql.contains(match)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}
