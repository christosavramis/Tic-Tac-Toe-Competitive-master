package tttc.resource;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@Data
@RequiredArgsConstructor
public class APIWrapperResponse<T> {
    private final T data;
    private final Map<String, String> errors;

    public static <T> APIWrapperResponse<T> of(T data) {
        return new APIWrapperResponse<>(data, null);
    }

    public static <T> APIWrapperResponse<T> ofErrors(Map<String, String> errors) {
        return new APIWrapperResponse<>(null, errors);
    }
}
