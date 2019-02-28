package com.garyhu.async;

import org.springframework.context.ApplicationEvent;

/**
 * @author: garyhu
 * @since: 2019/2/28 0028
 * @decription: 基础事件定义
 */
public class BaseEvent extends ApplicationEvent{

    private final static long serialVersionUID = 14223321651632534L;
    public BaseEvent(Object source) {
        super(source);
    }
}
