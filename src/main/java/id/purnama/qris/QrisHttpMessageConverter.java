package id.purnama.qris;

import id.purnama.qris.object.QrisPayload;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.StreamUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

/**
 * @author Arthur Purnama
 * @param <T> QRIS Pay Load
 */
@AllArgsConstructor
public class QrisHttpMessageConverter<T extends QrisPayload> extends AbstractHttpMessageConverter<T> {

    private QrisParser qrisParser;

    protected QrisHttpMessageConverter() {
        super(QrisMediaType.APPLICATION_QRIS);
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return clazz.equals(QrisPayload.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected T readInternal(Class<? extends T> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        String text = new BufferedReader(new InputStreamReader(inputMessage.getBody(), StandardCharsets.UTF_8)).lines().collect(Collectors.joining("\n"));
        return (T) qrisParser.parse(text);
    }

    @Override
    protected void writeInternal(T t, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        OutputStream outputStream = StreamUtils.nonClosing(outputMessage.getBody());
        outputStream.write(t.getPayload().getBytes(StandardCharsets.UTF_8));
    }
}
