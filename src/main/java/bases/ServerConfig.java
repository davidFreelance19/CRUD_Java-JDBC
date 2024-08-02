package bases;

import static spark.Spark.*;

import bases.routes.Routes;

public class ServerConfig {

    public static void start() {

        staticFiles.location("/public");

        port(3000);
        
        Routes.getRoutes();
    }

}