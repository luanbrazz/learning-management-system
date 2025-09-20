package com.lbraz.lms.util;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class MessageUtil {

    private static MessageSource messageSource;

    private MessageUtil(MessageSource messageSource) {
        MessageUtil.messageSource = messageSource;
    }

    public static String get(String id) {
        return get(id, (Object[]) null);
    }

    public static String get(String id, Object... args) {
        if (messageSource == null) {
            return id;
        } else {
            try {
                return messageSource.getMessage(id, args, LocaleContextHolder.getLocale());
            } catch (Exception e) {
                return id;
            }
        }
    }
}