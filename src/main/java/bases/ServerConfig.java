package bases;

import static spark.Spark.port;

import bases.routes.Routes;

public class ServerConfig {

    public static void start() {
        port(3000);
        Routes.getRoutes();
    }
}
