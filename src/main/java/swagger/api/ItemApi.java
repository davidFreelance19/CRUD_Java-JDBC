package swagger.api;


import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import swagger.models.Item;

@Tag(name = "Items", description = "CRUD items example")
@Path("/items")
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaJAXRSSpecServerCodegen", date = "2024-08-01T17:24:57.288399204Z[GMT]")
public class ItemApi {
    @GET
    @Path("//")
    @Produces({ "application/json"})
    @Operation(summary = "Get all items registered", description = "Returns all items registered in the database", operationId = "getItems")
    @ApiResponses(value = {
        @ApiResponse(
                responseCode = "200",
                description = "Items returned successfully",
                content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Item.class),
                            examples = @ExampleObject(
                                name = "Get items",
                                value = """
                                        {
                                           "items": [
                                                {
                                                  "id": 1,
                                                  "name": "Test item 1"
                                                },
                                                {
                                                  "id": 2,
                                                  "name": "Test item 2"
                                                }
                                           ]
                                        }
                                """
                            )
                )
        ),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public Response getItems(){
        return Response.ok().build();
    }

   @GET
    @Path("/{id}")
    @Produces({ "application/json" })
    @Operation(summary = "Get an item by ID", description = "Returns an item by ID", operationId = "getItemById")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Item.class),
                            examples = @ExampleObject(
                                    name = "Item with id 2",
                                    value = """
                                        {
                                          "item": {
                                                "id": 2,
                                                "name": "Item test"
                                          }
                                        }
                                    """
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Item not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
    })
    public Response getItem(
            @Parameter(description = "ID of item to return", required = true)
            @PathParam("id") String id
    ) {
        return Response.ok().build();
    }

    @Path("//")
    @POST
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @Operation(summary = "Register a new item", description = "Register a new item with the properties: name", operationId = "registerItem")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Item created successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Item.class),
                            examples = @ExampleObject(
                                    name = "Register item",
                                    value = "{\"message\":\"Item created successfully\"}"
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
    })
    public Response createItem(@FormParam(value = "name") String name) {
        return Response.ok().build();
    }

    @Path("/{id}")
    @PUT
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @Operation(summary = "Update an item", description = "Update the properties for example the name of item", operationId = "updateItem")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Item updated successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Item.class),
                            examples = @ExampleObject(
                                    name = "Register item",
                                    value = """
                                                {
                                                   "message": "Item updated successfully"
                                                }            
                                        """
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Item not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
    })
    public Response updateItem(
        @Parameter(description = "ID item to update", required = true)
        @PathParam("id") String id,
        @FormParam(value = "name") String name
    ) {
        return Response.ok().build();
    }

    @Path("/{id}")
    @DELETE
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @Operation(summary = "Delete an item by ID", description = "Delete an item by ID if the item exist in database", operationId = "deleteItem")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Item deleted successfully",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Item.class),
                            examples = @ExampleObject(
                                    name = "Delete item",
                                    value = """
                                                {
                                                   "message": "Item deleted successfully"
                                                }            
                                        """
                            )
                    )
            ),
            @ApiResponse(responseCode = "400", description = "Bad request"),
            @ApiResponse(responseCode = "404", description = "Item not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
    })
    public Response deleteItem(
        @Parameter(description = "The item id", required = true)
        @PathParam("id") String id
    ) {
        return Response.ok().build();
    }
}
