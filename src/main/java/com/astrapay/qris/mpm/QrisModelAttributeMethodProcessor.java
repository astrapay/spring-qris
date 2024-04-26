package com.astrapay.qris.mpm;

import com.astrapay.qris.mpm.QrisParser;
import com.astrapay.qris.mpm.object.QrisPayload;
import lombok.Setter;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ServletModelAttributeMethodProcessor;

import java.util.Objects;

/**
 * @author Arthur Purnama
 */
@Setter
public class QrisModelAttributeMethodProcessor extends ServletModelAttributeMethodProcessor {

    private QrisParser qrisParser;

    /**
     * constructor
     */
    public QrisModelAttributeMethodProcessor() {
        super(true);
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameter().getType().equals(QrisPayload.class);
    }

    @Override
    protected void bindRequestParameters(WebDataBinder binder, NativeWebRequest request) {
        super.bindRequestParameters(binder, request);
        qrisParser.parse((QrisPayload) Objects.requireNonNull(binder.getTarget()));
    }

}
