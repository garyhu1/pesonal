package com.garyhu.async;

import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author: garyhu
 * @since: 2019/2/28 0028
 * @decription: 事件发布器
 */
@Component
public class EventPublisher {

    @Autowired
    ApplicationContext context;

    public void publishEvent(BaseEvent event){

    }
}
