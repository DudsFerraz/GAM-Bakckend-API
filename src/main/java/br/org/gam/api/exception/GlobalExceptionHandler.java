package br.org.gam.api.exception;

import br.org.gam.api.Entities.account.exception.AccountConflictException;
import br.org.gam.api.Entities.account.exception.AccountNotFoundException;
import br.org.gam.api.Entities.member.exception.MemberAccountConflictException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // =====================================================================================
    // == 400 BAD REQUEST - Input, validation, format errors...
    // =====================================================================================

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(@NonNull MethodArgumentNotValidException ex,
                                                                  @NonNull HttpHeaders headers,
                                                                  @NonNull HttpStatusCode status,
                                                                  @NonNull WebRequest request) {

        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> String.format("'%s': %s", fieldError.getField(), fieldError.getDefaultMessage()))
                .toList();

        String message = "Validation error: " + String.join(", ", errors);

        ApiErrorDTO errorDTO = new ApiErrorDTO(HttpStatus.BAD_REQUEST, message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiErrorDTO> illegalArgumentHandler(IllegalArgumentException e) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(InvalidPhoneNumberException.class)
    public ResponseEntity<ApiErrorDTO> invalidPhoneNumberHandler(InvalidPhoneNumberException e) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiErrorDTO> typeMismatchHandler(MethodArgumentTypeMismatchException e) {
        String message = String.format("The URL parameter '%s' received an invalid value type.", e.getName());
        return buildErrorResponse(HttpStatus.BAD_REQUEST, message);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiErrorDTO> dataIntegrityViolationHandler(DataIntegrityViolationException e) {
        log.warn("Data integrity violation detected.", e);
        // Generic message to avoid exposing DB schema details
        String message = "Data integrity error. The request may violate a database constraint.";
        return buildErrorResponse(HttpStatus.BAD_REQUEST, message);
    }

    // =====================================================================================
    // == 401 UNAUTHORIZED - Authentication errors (Missing credentials)
    // =====================================================================================

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiErrorDTO> authenticationHandler(AuthenticationException ignored) {
        return buildErrorResponse(HttpStatus.UNAUTHORIZED, "Authentication failed. Please check your credentials.");
    }

    // =====================================================================================
    // == 403 FORBIDDEN - Authorization errors (Missing permissions)
    // =====================================================================================

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiErrorDTO> accessDeniedHandler(AccessDeniedException ignored) {
        return buildErrorResponse(HttpStatus.FORBIDDEN, "Access denied. You do not have permission for this action.");
    }

    // =====================================================================================
    // == 404 NOT FOUND - Resource not found
    // =====================================================================================

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ApiErrorDTO> accountNotFoundHandler(AccountNotFoundException e) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, e.getMessage());
    }

//    @ExceptionHandler(MemberNotFoundException.class)
//    public ResponseEntity<ApiErrorDTO> memberNotFoundHandler(MemberNotFoundException e) {
//        return buildErrorResponse(HttpStatus.NOT_FOUND, e.getMessage());
//    }

    // =====================================================================================
    // == 409 CONFLICT - State conflict (e.g., duplicate resource)
    // =====================================================================================

    @ExceptionHandler(AccountConflictException.class)
    public ResponseEntity<ApiErrorDTO> accountConflictHandler(AccountConflictException e) {
        return buildErrorResponse(HttpStatus.CONFLICT, e.getMessage());
    }

    @ExceptionHandler(MemberAccountConflictException.class)
    public ResponseEntity<ApiErrorDTO> memberAccountConflictHandler(MemberAccountConflictException e) {
        return buildErrorResponse(HttpStatus.CONFLICT, e.getMessage());
    }

    // =====================================================================================
    // == 500 INTERNAL SERVER ERROR - Generic error
    // =====================================================================================

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorDTO> genericExceptionHandler(Exception e) {
        log.error("Generic unhandled error was captured by the handler: ", e);
        String message = "Unexpected internal server error.";
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }

    // =====================================================================================
    // == Helper Method
    // =====================================================================================

    private ResponseEntity<ApiErrorDTO> buildErrorResponse(HttpStatus status, String message) {
        return ResponseEntity
                .status(status)
                .body(new ApiErrorDTO(status, message));
    }
}