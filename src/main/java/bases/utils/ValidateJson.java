package bases.utils;

import java.util.Map;
import java.util.Optional;

public class ValidateJson {
    private final Optional<Map<String, Object>> body;
    private final String errorMessage;

    private ValidateJson(Optional<Map<String, Object>> body, String errorMessage) {
        this.body = body;
        this.errorMessage = errorMessage;
    }

    public static ValidateJson ofBody(Map<String, Object> body) {
        return new ValidateJson(Optional.ofNullable(body), null);
    }

    public static ValidateJson ofMessage(String errorMessage) {
        return new ValidateJson(Optional.empty(), errorMessage);
    }

    public boolean isValid() {
        return body.isPresent();
    }

    public Optional<Map<String, Object>> getBody() {
        return body;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}
