package bases.dtos.items;

import bases.dtos.UpdateDto;
import bases.utils.ErrorsItem;
import bases.utils.ErrorsShared;
import bases.utils.HttpStatus;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UpdateItemDto extends UpdateDto<Map<String, Object>, UpdateItemDto.Result> {

    private final Long id;
    private final String name;

    private UpdateItemDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Optional<Map<String, Object>> getValues() {

        Optional<Map<String, Object>> returnObj = Optional.empty();
        Map<String, Object> obj = new HashMap<String, Object>();

        if (name != null && !name.isEmpty()) {
            obj.put("name", name);
            returnObj = Optional.of(obj);
        }

        return returnObj;
    }

    public static class Result {
        private final Map<String, Object> error;
        private final UpdateItemDto dto;

        private Result(Map<String, Object> error, UpdateItemDto dto) {
            this.error = error;
            this.dto = dto;
        }

        public Optional<Map<String, Object>> getError() {
            return Optional.ofNullable(error);
        }

        public Optional<UpdateItemDto> getDto() {
            return Optional.ofNullable(dto);
        }
    }

    @Override
    public Result update(Long id, Optional<Map<String, Object>> props) {

        if (props.get().get("name") == null) {
            return createResult(ErrorsShared.NO_UPDATE, HttpStatus.OK);
        }

        if (!(props.get().get("name") instanceof String)) {
            return createResult(ErrorsItem.NAME_SINTAX_ERROR, HttpStatus.BAD_REQUEST);
        }

        String name = (String) props.get().get("name");

        if (name.trim().isEmpty()) {
            return createResult(ErrorsItem.NAME_EMPTY, HttpStatus.BAD_REQUEST);
        }

        return new Result(null, new UpdateItemDto(id, name));
    }

    public static Result updateStatic(Long id, Optional<Map<String, Object>> props) {
        return new UpdateItemDto(null, null).update(id, props);
    }

    private Result createResult(String message, int status) {
        String key = status == HttpStatus.OK ? "message" : "error";
        Map<String, Object> response = Map.of(key, message, "status", status);

        return new Result(response, null);
    }
}