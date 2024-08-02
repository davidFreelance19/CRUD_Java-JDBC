package bases.controllers;

import spark.Request;
import spark.Response;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import bases.models.Item;
import bases.config.ExceptionHandler;
import bases.dtos.http.GetParamIdDto;
import bases.dtos.items.*;
import bases.repositories.*;
import bases.services.*;
import bases.utils.*;
import static bases.utils.BaseController.*;

public class ItemsController {

    private Service<Item> itemService;
    private Repository<Item> repository;

    public ItemsController(Service<Item> itemService) {
        this.repository = new ItemRepositoryImpl();
        this.itemService = new ItemsService(repository);
    }

    public String getItems(Request req, Response res) {
        try {
            List<Item> items = itemService.getAll();
            Map<String, Object> response = Map.of("items", items);
            
            return jsonResponse(res, response, HttpStatus.OK);
        } catch (ExceptionHandler e) {
            return jsonResponse(res, Map.of("error", e.getMessage()), e.getStatus());
        }
    }

    public String getItem(Request req, Response res) {
        GetParamIdDto.Result paramResult = GetParamIdDto.getParam(req.params("id"));

        if (paramResult.getError().isPresent())
            return jsonResponse(res, Map.of("error", paramResult.getError().get()), HttpStatus.BAD_REQUEST);

        try {
            Long id = (Long) paramResult.getParamDto().get().getParam();
            Optional<Item> item = itemService.getById(id);

            return jsonResponse(res, Map.of("item", item.get()), HttpStatus.OK);
        } catch (ExceptionHandler e) {
            return jsonResponse(res, Map.of("error", e.getMessage()), e.getStatus());
        }
    }

    public String createItem(Request req, Response res) {
        Optional<Map<String, Object>> body;
        ValidateJson resultJSON = validateSyntaxJson(req, res);

        if (!(resultJSON.isValid()))
            return resultJSON.getErrorMessage();

        body = Optional.of(resultJSON.getBody().get());

        CreateItemDto.Result resultCreateDto = CreateItemDto.createStatic(body);

        if (resultCreateDto.getError().isPresent())
            return jsonResponse(res, Map.of("error", resultCreateDto.getError().get()), HttpStatus.BAD_REQUEST);

        try {
            CreateItemDto dto = resultCreateDto.getDto().get();
            String message = itemService.save(dto);

            return jsonResponse(res, Map.of("message", message), HttpStatus.OK);
        } catch (ExceptionHandler e) {
            return jsonResponse(res, Map.of("error", e.getMessage()), e.getStatus());
        }
    }

    public String updateItem(Request req, Response res) {
        GetParamIdDto.Result paramResult = GetParamIdDto.getParam(req.params("id"));

        if (paramResult.getError().isPresent())
            return jsonResponse(res, Map.of("error", paramResult.getError().get()), HttpStatus.BAD_REQUEST);

        Long id = (Long) paramResult.getParamDto().get().getParam();

        Optional<Map<String, Object>> body;
        ValidateJson result = validateSyntaxJson(req, res);

        if (!(result.isValid()))
            return result.getErrorMessage();

        body = Optional.of(result.getBody().get());

        UpdateItemDto.Result resultDto = UpdateItemDto.updateStatic(id, body);

        if (resultDto.getError().isPresent()) {

            String key = resultDto.getError().get().containsKey("message") ? "message" : "error";
            String message = (String) resultDto.getError().get().get(key);
            Integer status = (Integer) resultDto.getError().get().get("status");

            return jsonResponse(res, Map.of(key, message), status);
        }

        try {
            UpdateItemDto dto = resultDto.getDto().get();
            String message = itemService.update(dto);

            return jsonResponse(res, Map.of("message", message), HttpStatus.OK);
        } catch (ExceptionHandler e) {
            return jsonResponse(res, Map.of("error", e.getMessage()), e.getStatus());
        }
    }

    public String deleteItem(Request req, Response res) {
        GetParamIdDto.Result result = GetParamIdDto.getParam(req.params("id"));

        if (result.getError().isPresent())
            return jsonResponse(res, Map.of("error", result.getError().get()), HttpStatus.BAD_REQUEST);

        try {
            Long id = (Long) result.getParamDto().get().getParam();
            String message = itemService.delete(id);

            return jsonResponse(res, Map.of("message", message), HttpStatus.OK);
        } catch (ExceptionHandler e) {
            return jsonResponse(res, Map.of("error", e.getMessage()), e.getStatus());
        }
    }

}
