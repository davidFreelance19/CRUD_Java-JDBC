package bases.dtos.items;

import bases.dtos.CreateDto;
import bases.utils.ErrorsItem;

import java.util.Map;
import java.util.Optional;

public class CreateItemDto extends CreateDto<Map<String, Object>, CreateItemDto.Result> {
    private final String name;

    private CreateItemDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static class Result {
        private final String error;
        private final CreateItemDto dto;

        private Result(String error, CreateItemDto dto) {
            this.error = error;
            this.dto = dto;
        }

        public Optional<String> getError() {
            return Optional.ofNullable(error);
        }

        public Optional<CreateItemDto> getDto() {
            return Optional.ofNullable(dto);
        }
    }

    @Override
    public Result create(Optional<Map<String, Object>> props) {

        Object nameObj = props.get().get("name");
        if (nameObj == null) {
            return new Result(ErrorsItem.NAME_REQUIRED, null);
        }

        if (!(nameObj instanceof String)) {
            return new Result(ErrorsItem.NAME_SINTAX_ERROR, null);
        }

        String name = nameObj.toString().trim();
        if (name.isEmpty()) {
            return new Result(ErrorsItem.NAME_EMPTY, null);
        }

        // Aquí puedes agregar más validaciones si es necesario
        // Por ejemplo, validar la longitud máxima del nombre

        return new Result(null, new CreateItemDto(name));
    }

    public static Result createStatic(Optional<Map<String, Object>> props) {
        return new CreateItemDto("").create(props);
    }
}