package com.garyhu.validate.code.sms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created on 2018/1/10.
 *
 * @author zlf
 * @since 1.0
 */
public class DefaultSmsCodeSender implements SmsCodeSender {

    private final static Logger log = LoggerFactory.getLogger(DefaultSmsCodeSender.class);

    @Override
    public void send(String mobile, String code) {
        log.warn("请配置真实的短信验证码发送器(SmsCodeSender)");
        log.info("向手机" + mobile + "发送短信验证码" + code);
    }
}
