package bases.middlewares;

import spark.Request;
import spark.Response;
import static spark.Spark.*;

import java.util.Date;
import java.util.Map;

import bases.config.CriptoAdapter;
import bases.config.JwtAdapter;
import bases.dtos.http.HeadersDto;
import bases.utils.ErrorsShared;
import bases.utils.HttpStatus;
import static bases.utils.BaseController.*;

public class HttpHeadersMiddleware {

    private static final String HEADER_AUTH_REQUIRED = "The x-authentication header is required";
    private static final String HEADER_AUTH_INVALID = "Invalid x-authentication header";

    public static void validateDateAndAuth(Request req, Response res) {
        String dateNow = new Date().toString();
        String authentication = req.headers("x-authentication");
        HeadersDto.Result resultHeadersDto = HeadersDto.validate(authentication, dateNow);

        if (resultHeadersDto.getError().isPresent()) {
            String error = resultHeadersDto.getError().get();
            halt(HttpStatus.BAD_REQUEST, jsonResponse(res, Map.of("error", error)));
        }

        Map<String, String> headers = Map.of("authetication", authentication, "date", dateNow, "url", req.url());
        Map<String, Object> payload = encryptHeaders(res, headers);

        String token = JwtAdapter.generateToken(payload);

        req.attribute("token", token);
    }

    public static void validateSignature(Request req, Response res) throws Exception {
        String token = req.attribute("token");
        if (token == null)
            halt(HttpStatus.BAD_REQUEST, jsonResponse(res, Map.of("error", HEADER_AUTH_REQUIRED)));

        boolean tokenValid = JwtAdapter.validateToken(token);

        if (!tokenValid)
            halt(HttpStatus.UNAUTHORIZED, jsonResponse(res, Map.of("error", HEADER_AUTH_INVALID)));

        Map<String, Object> payload = JwtAdapter.getPayload(token);
        decryptHeaders(payload, res);
    }

    public static Map<String, Object> encryptHeaders(Response res, Map<String, String> headers) {

        try {
            String dateEncrypt = CriptoAdapter.encrypt(headers.get("date"), headers.get("authetication"));
            String urlEncrypt = CriptoAdapter.encrypt(headers.get("url"), headers.get("authetication"));

            return Map.of("date", dateEncrypt, "url", urlEncrypt);
        } catch (Exception e) {
            String response = jsonResponse(res, Map.of("error", ErrorsShared.INTERNAL_SERVER_ERROR));
            halt(HttpStatus.INTERNAL_SERVER_ERROR, response);

            return Map.of();
        }

    }

    public static void decryptHeaders(Map<String, Object> payload, Response res) {
        try {
            CriptoAdapter.decrypt(payload.get("date").toString());
            CriptoAdapter.decrypt(payload.get("url").toString());
        } catch (Exception e) {
            halt(HttpStatus.UNAUTHORIZED, jsonResponse(res, Map.of("error", HEADER_AUTH_INVALID)));
        }
    }
}