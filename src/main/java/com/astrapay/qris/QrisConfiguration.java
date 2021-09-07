package com.astrapay.qris;

import com.astrapay.qris.object.QrisPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * @author Arthur Purnama
 */
@Configuration
public class QrisConfiguration implements WebMvcConfigurer {

    @Autowired
    private QrisHttpMessageConverter<QrisPayload> qrisHttpMessageConverter;

    @Autowired
    private QrisModelAttributeMethodProcessor qrisModelAttributeMethodProcessor;

    @Autowired
    private QrisParser qrisParser;

    /**
     *
     * @return payload
     */
    @Bean
    public QrisHttpMessageConverter<QrisPayload> qrisHttpMessageConverter(){
        return new QrisHttpMessageConverter<>();
    }

    /**
     *
     * @return processor
     */
    @Bean
    public QrisModelAttributeMethodProcessor qrisModelAttributeMethodProcessor(){
        return new QrisModelAttributeMethodProcessor();
    }

    /**
     *
     * @return mapper
     */
    @Bean
    public QrisMapper qrisMapper(){
        return new QrisMapper();
    }

    /**
     *
     * @return parser
     */
    @Bean
    public QrisParser qrisParser(){
        return new QrisParser();
    }

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
