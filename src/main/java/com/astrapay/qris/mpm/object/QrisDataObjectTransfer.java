package com.astrapay.qris.mpm.object;

import com.astrapay.qris.mpm.validation.constraints.CRCCustom;
import com.astrapay.qris.mpm.validation.constraints.PayloadFormatIndicatorFirstPosition;

import java.util.Map;

public class QrisDataObjectTransfer extends QrisPayload{

    @Override
    @PayloadFormatIndicatorFirstPosition
    public Map<Integer, QrisDataObject> getQrisRoot() {
        return super.getQrisRoot();
    }

    public void setQrisRoot(Map<Integer, QrisDataObject> qrisRoot) {
        super.setQrisRoot(qrisRoot);
    }
}
