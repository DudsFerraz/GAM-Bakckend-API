package br.org.gam.api.Entities.account.exception;

import br.org.gam.api.exception.ApiErrorDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@ControllerAdvice
public class AccountExceptionHandler extends ResponseEntityExceptionHandler {
    private ResponseEntity<ApiErrorDTO> buildErrorResponse(HttpStatus status, String message) {
        return ResponseEntity
                .status(status)
                .body(new ApiErrorDTO(status, message));
    }

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ApiErrorDTO> accountNotFoundHandler(AccountNotFoundException e) {

        return buildErrorResponse(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiErrorDTO> handleIllegalArgument(IllegalArgumentException e) {

        return buildErrorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }


    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiErrorDTO> handleTypeMismatch(MethodArgumentTypeMismatchException e) {

        String message = String.format("O parâmetro de URL '%s' recebeu um valor de tipo inválido.", e.getName());
        return buildErrorResponse(HttpStatus.BAD_REQUEST, message);
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {

        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> String.format("'%s': %s", fieldError.getField(), fieldError.getDefaultMessage()))
                .toList();

        String message = "Erro de validação: " + String.join(", ", errors);

        ApiErrorDTO errorDTO = new ApiErrorDTO(HttpStatus.BAD_REQUEST, message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDTO);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorDTO> handleGenericException(Exception e) {
        e.printStackTrace();

        String message = "Ocorreu um erro interno inesperado no servidor.";

        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }
}