package com.astrapay.qris.mpm.object;

import com.astrapay.qris.mpm.validation.constraints.CRCCustom;

import java.util.Map;


public class QrisDataObjectTuntas extends QrisPayload{

    @Override
    @CRCCustom
    public Map<Integer, QrisDataObject> getQrisRoot() {
        return super.getQrisRoot();
    }

    public void setQrisRoot(Map<Integer, QrisDataObject> qrisRoot) {
        super.setQrisRoot(qrisRoot);
    }
}
