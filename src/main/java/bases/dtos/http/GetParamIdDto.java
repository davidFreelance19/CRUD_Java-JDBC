package bases.dtos.http;

import java.util.Optional;

public class GetParamIdDto {
    private final Long param;

    private GetParamIdDto(Long param) {
        this.param = param;
    }

    public Long getParam() {
        return param;
    }

    public static class Result {
        private final String error;
        private final GetParamIdDto dto;

        private Result(String error, GetParamIdDto dto) {
            this.error = error;
            this.dto = dto;
        }

        public Optional<String> getError() {
            return Optional.ofNullable(error);
        }

        public Optional<GetParamIdDto> getParamDto() {
            return Optional.ofNullable(dto);
        }
    }

    public static Result getParam(String param) {
        try {
            Long paramObj = Long.parseLong(param);
            return new Result(null, new GetParamIdDto(paramObj));
        } catch (Exception e) {
            return new Result("Param is not valid", null);
        }
    }

}
