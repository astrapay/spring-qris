package com.astrapay.qris;

import com.astrapay.qris.object.QrisPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
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
public class QrisConfiguration {

    /**
     *
     * @return payload
     */
    @Bean("qrisHttpMessageConverter")
    public QrisHttpMessageConverter<QrisPayload> qrisHttpMessageConverter(){
        return new QrisHttpMessageConverter<>();
    }

    /**
     *
     * @return processor
     */
    @Bean("qrisModelAttributeMethodProcessor")
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

}
