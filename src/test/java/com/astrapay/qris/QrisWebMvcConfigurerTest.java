package com.astrapay.qris;

import com.astrapay.qris.mpm.QrisModelAttributeMethodProcessor;
import com.astrapay.qris.mpm.QrisParser;
import com.astrapay.qris.mpm.object.QrisPayload;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class QrisWebMvcConfigurerTest {

    @Mock
    private QrisHttpMessageConverter<QrisPayload> qrisHttpMessageConverter;

    @Mock
    private QrisParser qrisParser;

    @Mock
    private QrisModelAttributeMethodProcessor qrisModelAttributeMethodProcessor;

    @InjectMocks
    private QrisWebMvcConfigurer qrisWebMvcConfigurer;

    @Test
    void testConfigureMessageConverters() {
        List<HttpMessageConverter<?>> converters = new ArrayList<>();
        qrisWebMvcConfigurer.configureMessageConverters(converters);

        verify(qrisHttpMessageConverter).setQrisParser(qrisParser);
        assertTrue(converters.contains(qrisHttpMessageConverter));
    }

    @Test
    void testAddArgumentResolvers() {
        List<HandlerMethodArgumentResolver> argumentResolvers = new ArrayList<>();
        qrisWebMvcConfigurer.addArgumentResolvers(argumentResolvers);

        verify(qrisModelAttributeMethodProcessor).setQrisParser(qrisParser);
        assertTrue(argumentResolvers.contains(qrisModelAttributeMethodProcessor));
    }
}
