package com.garyhu.conf;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.data.redis.core.index.IndexDefinition;

import java.util.Map;

/**
 * @author: garyhu
 * @since: 2018/11/22 0022
 * @decription: 实现了Condition接口，判断application.yml中是否配置了spring.cache.type,是否为Redis类型
 */
public class CacheCondition implements Condition {
    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        String type = conditionContext.getEnvironment().getProperty("spring.cache.type");
        if("Redis".equals(type)){
            return true;
        }
        return false;
    }
}
