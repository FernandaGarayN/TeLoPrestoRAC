package cl.duoc.telopresto.web.exception;

import lombok.Getter;

@Getter
public class ClientAlreadyExistsException extends RuntimeException {
    private final String value;
    private final String fieldName;

    public ClientAlreadyExistsException(String value, String fieldName) {
        super("Cliente con " + fieldName + "=" + value + " ya existe");
        this.value = value;
        this.fieldName = fieldName;
    }
}
