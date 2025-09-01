package tttc.session;

import lombok.Data;
import tttc.exception.GenericGameException;

@Data
public abstract class SessionWrapper<T> {
    private T object;

    public T getObject() {
        if (object == null) {
            throw new GenericGameException("resource not found in session");
        }
        return object;
    }
}
