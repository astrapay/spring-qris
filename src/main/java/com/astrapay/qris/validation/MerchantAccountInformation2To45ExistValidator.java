package com.astrapay.qris.validation;

import com.astrapay.qris.object.QrisDataObject;
import com.astrapay.qris.validation.constraints.MerchantAccountInformation2To45Exist;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Map;

@Builder
@NoArgsConstructor
public class MerchantAccountInformation2To45ExistValidator implements ConstraintValidator<MerchantAccountInformation2To45Exist, Map<Integer, QrisDataObject>> {

    @Override
    public boolean isValid(Map<Integer, QrisDataObject> value, ConstraintValidatorContext context) {
        if(value.get(51) == null){
            for(int i=2; i <=45; i++){
                if(value.containsKey(i)){
                    return true;
                }
            }
            return false;
        }
        return true;
    }
}