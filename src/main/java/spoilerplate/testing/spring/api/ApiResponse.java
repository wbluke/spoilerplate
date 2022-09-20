package spoilerplate.testing.spring.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

import static spoilerplate.testing.spring.api.ApiResponse.ApiResponseCode.*;

@Getter
@RequiredArgsConstructor
public class ApiResponse<T> {

    private final String code;
    private final String message;
    private final T data;

    public static <T> ApiResponse<T> of(ApiResponseCode apiResponseCode, T data) {
        return new ApiResponse<>(apiResponseCode.getHttpStatus().name(), apiResponseCode.getMessage(), data);
    }

    public static <T> ApiResponse<T> of(ApiResponseCode apiResponseCode, String message, T data) {
        return new ApiResponse<>(apiResponseCode.getHttpStatus().name(), message, data);
    }

    public static <T> ApiResponse<T> ok(T data) {
        return of(OK, data);
    }

    public static <T> ApiResponse<T> created(T data) {
        return of(CREATED, data);
    }

    public static <T> ApiResponse<T> badParameter(String message) {
        String errorMessage = StringUtils.hasText(message) ? message : BAD_PARAMETER.getMessage();
        return of(BAD_PARAMETER, errorMessage, null);
    }

    @Getter
    @RequiredArgsConstructor
    public enum ApiResponseCode {

        OK(HttpStatus.OK, "요청이 성공하였습니다."),
        CREATED(HttpStatus.CREATED, "요청이 성공하였습니다."),
        BAD_PARAMETER(HttpStatus.BAD_REQUEST, "파라미터가 형식에 맞지 않습니다."),
        UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다."),
        FORBIDDEN(HttpStatus.FORBIDDEN, "접근이 불가합니다."),
        ;

        private final HttpStatus httpStatus;
        private final String message;

    }

}
