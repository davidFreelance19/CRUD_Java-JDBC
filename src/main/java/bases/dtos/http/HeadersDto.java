package bases.dtos.http;

import java.util.Optional;

public class HeadersDto {
    private final String headerAuthentication;
    private final String headerDate;

    public HeadersDto(String headerAuthentication, String headerDate) {
        this.headerAuthentication = headerAuthentication;
        this.headerDate = headerDate;
    }

    public String getHeaderAuthentication() {
        return headerAuthentication;
    }

    public String getHeaderDate() {
        return headerDate;
    }

    public static class Result {
        private final String error;
        private final HeadersDto dto;

        private Result(String error, HeadersDto dto) {
            this.error = error;
            this.dto = dto;
        }

        public Optional<String> getError() {
            return Optional.ofNullable(error);
        }

        public Optional<HeadersDto> getDto() {
            return Optional.ofNullable(dto);
        }
    }

    public static Result validate(String auth, String date) {

        String authentication = auth;
        String dateNow = date; // Por defecto siempre vamos a estar enviando el date

        if (authentication == null) {
            return new Result("The x-authentication header is required", null);
        }

        // En dado caso de no enviar el date por defecto
        // Podemos agregar sus validaciones como un formato en especifico.

        return new Result(null, new HeadersDto(authentication, dateNow));
    }
}
