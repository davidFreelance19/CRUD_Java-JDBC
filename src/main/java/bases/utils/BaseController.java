package bases.utils;

import java.lang.reflect.Type;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import spark.Request;
import spark.Response;

public class BaseController {
    private static final Gson gson = new Gson();

    public static String jsonResponse(Response res, Object data, Integer status) {
        res.status(status);
        return jsonResponse(res, data);
    }

    public static String jsonResponse(Response res, Object data) {
        res.type("application/json");
        return gson.toJson(data);
    }

    public static ValidateJson validateSyntaxJson(Request req, Response res) {
        if (req.body().isEmpty()) {
            String message = jsonResponse(res, Map.of("error", ErrorsShared.BODY_EMPTY_ERROR), HttpStatus.BAD_REQUEST);
            return ValidateJson.ofMessage(message);
        }

        Type type = new TypeToken<Map<String, Object>>() {
        }.getType();

        try {
            Map<String, Object> body = gson.fromJson(req.body(), type);
            return ValidateJson.ofBody(body);
        } catch (JsonSyntaxException e) {
            String errorMessage = jsonResponse(res, Map.of("error", ErrorsShared.JSON_SYNTAX_ERROR),
                    HttpStatus.BAD_REQUEST);
            return ValidateJson.ofMessage(errorMessage);
        }
    }
}
