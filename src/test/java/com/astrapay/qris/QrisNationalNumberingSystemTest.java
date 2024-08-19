package com.astrapay.qris;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class QrisNationalNumberingSystemTest {

    @Test
    void testPjsp() {
        var result = QrisNationalNumberingSystem.Pjsp.valueOf("SINARMAS");
        assertEquals(QrisNationalNumberingSystem.Pjsp.SINARMAS, result);
    }

    @Test
    void testPjspGetCode() {
        var result = QrisNationalNumberingSystem.Pjsp.valueOf("SINARMAS").getCode();
        assertEquals(QrisNationalNumberingSystem.Pjsp.SINARMAS.getCode(), result);
    }

    @Test
    void testPjspGetSwitching() {
        var result = QrisNationalNumberingSystem.Pjsp.valueOf("SINARMAS").getSwitching();
        assertNotNull(result);
    }

    @Test
    void testPjspWithCode() {
        var result = QrisNationalNumberingSystem.Pjsp.valueOf(93600002);
        assertEquals(QrisNationalNumberingSystem.Pjsp.BRI, result);
    }
}
