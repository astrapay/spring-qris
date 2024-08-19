package com.astrapay.qris;

import com.astrapay.qris.cpm.QrCpmConfiguration;
import com.astrapay.qris.cpm.enums.TagIndicator;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class QrCpmConfigurationTest {

    @Test
    void testQrisCpmSubTagBean() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(QrCpmConfiguration.class);
        List<String> qrisCpmSubTag = (List<String>) context.getBean("qrisCpmSubTag");

        assertNotNull(qrisCpmSubTag);
        assertEquals(2, qrisCpmSubTag.size());
        assertEquals(TagIndicator.PAYLOAD_FORMAT_INDICATOR.getValue(), qrisCpmSubTag.get(0));
        assertEquals(TagIndicator.APPLICATION_TEMPLATE.getValue(), qrisCpmSubTag.get(1));

        context.close();
    }

    @Test
    void testApplicationTemplateSubTagBean() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(QrCpmConfiguration.class);
        List<String> applicationTemplateSubTag = (List<String>) context.getBean("applicationTemplateSubTag");

        assertNotNull(applicationTemplateSubTag);
        assertEquals(12, applicationTemplateSubTag.size());
        assertEquals(TagIndicator.ADF_NAME.getValue(), applicationTemplateSubTag.get(0));
        assertEquals(TagIndicator.APPLICATION_LABEL.getValue(), applicationTemplateSubTag.get(1));
        // Continue assertions for the remaining values

        context.close();
    }

    @Test
    void testApplicationSpecificTransparentTemplateSubTagBean() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(QrCpmConfiguration.class);
        List<String> applicationSpecificTransparentTemplateSubTag = (List<String>) context.getBean("applicationSpecificTransparentTemplateSubTag");

        assertNotNull(applicationSpecificTransparentTemplateSubTag);
        assertEquals(7, applicationSpecificTransparentTemplateSubTag.size());
        assertEquals(TagIndicator.ISSUER_QRIS_DATA.getValue(), applicationSpecificTransparentTemplateSubTag.get(0));
        assertEquals(TagIndicator.APPLICATION_CRYPTOGRAM.getValue(), applicationSpecificTransparentTemplateSubTag.get(1));
        // Continue assertions for the remaining values

        context.close();
    }
}
