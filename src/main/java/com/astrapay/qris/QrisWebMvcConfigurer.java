package com.astrapay.qris;

import com.astrapay.qris.object.QrisPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author Raymond Sugiarto
 * @since 2022-04-28
 */
@Component
public class QrisWebMvcConfigurer implements WebMvcConfigurer {

    @Autowired
    private QrisHttpMessageConverter<QrisPayload> qrisHttpMessageConverter;

    @Autowired
    private QrisParser qrisParser;

    @Autowired
    private QrisModelAttributeMethodProcessor qrisModelAttributeMethodProcessor;

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        qrisHttpMessageConverter.setQrisParser(qrisParser);
        converters.add(qrisHttpMessageConverter);
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        qrisModelAttributeMethodProcessor.setQrisParser(qrisParser);
        argumentResolvers.add(qrisModelAttributeMethodProcessor);
    }
}
