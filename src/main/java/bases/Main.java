package bases;

import static spark.Spark.*;

import swagger.config.SwaggerServer;

public class Main {
    public static void main(String[] args) {

        ServerConfig.start();

        SwaggerServer.start();
       
    }
}