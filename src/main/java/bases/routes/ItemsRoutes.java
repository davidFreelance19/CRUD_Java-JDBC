package bases.routes;

import static spark.Spark.*;

import bases.controllers.ItemsController;
import bases.middlewares.HttpHeadersMiddleware;
import bases.models.Item;
import bases.repositories.ItemRepositoryImpl;
import bases.repositories.Repository;
import bases.services.ItemsService;
import bases.services.Service;

public class ItemsRoutes {

    public static void routes() {

        Repository<Item> repository = new ItemRepositoryImpl();
        Service<Item> service = new ItemsService(repository);
        ItemsController controller = new ItemsController(service);

        before("/*", HttpHeadersMiddleware::validateDateAndAuth);
        before("/*", HttpHeadersMiddleware::validateSignature);

        get("/", (req, res) -> controller.getItems(req, res));
        get("/:id", (req, res) -> controller.getItem(req, res));
        post("/create-item", (req, res) -> controller.createItem(req, res));
        put("/update-item/:id", (req, res) -> controller.updateItem(req, res));
        delete("/:id", (req, res) -> controller.deleteItem(req, res));
    }

}
