package com.astrapay.qris;

import com.astrapay.qris.mpm.QrisMapper;
import com.astrapay.qris.mpm.QrisModelAttributeMethodProcessor;
import com.astrapay.qris.mpm.QrisParser;
import com.astrapay.qris.mpm.object.QrisPayload;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

class QrisConfigurationTest {

    @Mock
    private ApplicationContext applicationContext;

    @InjectMocks
    private QrisConfiguration qrisConfiguration;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testQrisHttpMessageConverterBean() {
        QrisHttpMessageConverter<QrisPayload> qrisHttpMessageConverter = new QrisHttpMessageConverter<>();
        when(applicationContext.getBean("qrisHttpMessageConverter", QrisHttpMessageConverter.class)).thenReturn(qrisHttpMessageConverter);

        QrisHttpMessageConverter<?> bean = qrisConfiguration.qrisHttpMessageConverter();
        assertNotNull(bean);
        assertTrue(true);
    }

    @Test
    void testQrisModelAttributeMethodProcessorBean() {
        QrisModelAttributeMethodProcessor qrisModelAttributeMethodProcessor = new QrisModelAttributeMethodProcessor();
        when(applicationContext.getBean("qrisModelAttributeMethodProcessor", QrisModelAttributeMethodProcessor.class)).thenReturn(qrisModelAttributeMethodProcessor);

        QrisModelAttributeMethodProcessor bean = qrisConfiguration.qrisModelAttributeMethodProcessor();
        assertNotNull(bean);
        assertTrue(true);
    }

    @Test
    void testQrisMapperBean() {
        QrisMapper qrisMapper = new QrisMapper();
        when(applicationContext.getBean(QrisMapper.class)).thenReturn(qrisMapper);

        QrisMapper bean = qrisConfiguration.qrisMapper();
        assertNotNull(bean);
        assertTrue(true);
    }

    @Test
    void testQrisParserBean() {
        QrisParser qrisParser = new QrisParser();
        when(applicationContext.getBean(QrisParser.class)).thenReturn(qrisParser);

        QrisParser bean = qrisConfiguration.qrisParser();
        assertNotNull(bean);
        assertTrue(true);
    }
}
