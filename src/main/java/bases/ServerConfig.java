package bases;

import static spark.Spark.*;

import java.util.Map;

import bases.config.EnvsAdapter;
import bases.routes.Routes;
import bases.utils.ErrorsShared;
import bases.utils.HttpStatus;

import static bases.utils.BaseController.*;

public class ServerConfig {
    private static final String ALLOWED_ORIGIN = EnvsAdapter.FRONTEND_URL;

    public static void start() {
        port(3000);
        enableCORS();
        Routes.getRoutes();
    }

    private static void enableCORS() {
        before((request, response) -> {
            String origin = request.headers("Origin");
            if (origin == null || !ALLOWED_ORIGIN.equals(origin))
                halt(HttpStatus.FORBIDDEN, jsonResponse(response, Map.of("error", ErrorsShared.ACCESS_NOT_ALLOWED)));

            response.header("Access-Control-Allow-Origin", ALLOWED_ORIGIN);
            response.header("Access-Control-Allow-Methods", "GET,PUT,POST,DELETE,OPTIONS");
            response.header("Access-Control-Allow-Headers", "Content-Type,Authorization,Accept");
        });
    }
}