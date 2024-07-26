package bases.routes;

import static spark.Spark.path;

public class Routes {
    public static void getRoutes() {
        path("/items", () -> ItemsRoutes.routes());
    }
}