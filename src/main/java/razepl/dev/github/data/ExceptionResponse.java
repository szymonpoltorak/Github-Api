package razepl.dev.github.data;

import lombok.Builder;

@Builder
public record ExceptionResponse(int status, String message) {
}
