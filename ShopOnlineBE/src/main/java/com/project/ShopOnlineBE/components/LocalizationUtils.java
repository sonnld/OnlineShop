package com.project.ShopOnlineBE.components;

import com.project.ShopOnlineBE.utils.WebUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;

import java.util.Locale;
@RequiredArgsConstructor
@Component
public class LocalizationUtils {
    private final MessageSource messageSource;
    private final LocaleResolver localeResolver;
    //lấy thông báo theo key và params
    public String getLocalizedMessage(String messageKey, Object... params) {
        HttpServletRequest request = WebUtils.getCurrentRequest();
        Locale locale = localeResolver.resolveLocale(request);

        return messageSource.getMessage(messageKey, params, locale);
    }
}
