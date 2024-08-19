package com.astrapay.qris;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;


@ExtendWith(MockitoExtension.class)
public class MerchantCategoryCodesTest {


    @Test
    void testIso18245MerchantCategoryCode() {
        var result = MerchantCategoryCodes.Iso18245MerchantCategoryCode.valueOf(742);
        assertNotNull(result);
    }

    @Test
    void testGetDescriptions() {
        var result = MerchantCategoryCodes.Iso18245MerchantCategoryCode.valueOf(742).getDescription();
        assertNotNull(result);

    }

    @Test
    void testGetCodeAsString() {
        var result = MerchantCategoryCodes.Iso18245MerchantCategoryCode.valueOf(742).getCodeAsString();
        assertNotNull(result);

    }

    @Test
    void testToString() {
        var result = MerchantCategoryCodes.Iso18245MerchantCategoryCode.valueOf(742).toString();
        assertNotNull(result);
    }

    @Test
    void testGetTRBCClassification() {
        var result = MerchantCategoryCodes.Iso18245MerchantCategoryCode.valueOf(742).getTRBCClassification();
        assertNotNull(result);
    }

    @Test
    void testGetSchemeMCG() {
        var result = MerchantCategoryCodes.Iso18245MerchantCategoryCode.valueOf(742).getSchemeMCG();
        assertNotNull(result);
    }

    @Test
    void testGetSchemeTCC() {
        var result = MerchantCategoryCodes.Iso18245MerchantCategoryCode.valueOf(742).getSchemeTCC();
        assertNotNull(result);
    }

    @Test
    void testGetCategoryRange() {
        var result = MerchantCategoryCodes.Iso18245MerchantCategoryCode.valueOf(742).getCategoryRange();
        assertNotNull(result);
    }

    @Test
    void testGetHeadCategory() {
        var result = MerchantCategoryCodes.Iso18245MerchantCategoryCode.valueOf(742).getHeadCategory();
        assertNotNull(result);
    }
}
