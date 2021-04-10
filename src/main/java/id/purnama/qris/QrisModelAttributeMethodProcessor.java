package id.purnama.qris;

import id.purnama.qris.object.QrisPayload;
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
