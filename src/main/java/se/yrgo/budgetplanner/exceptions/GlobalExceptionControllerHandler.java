package se.yrgo.budgetplanner.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionControllerHandler extends ResponseEntityExceptionHandler {
    public static final String DEFAULT_ERROR_VIEW = "error";

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException e, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String error = "Malformed JSON request";
        return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, error, e));
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException e) {
        String error = "Could not retrieve resource";
        return buildResponseEntity(new ApiError(HttpStatus.NOT_FOUND, error, e));
    }

    @ExceptionHandler({UserNotFoundException.class, UserExistsException.class})
    public ResponseEntity<Object> RuntimeExceptionHandler(RuntimeException e) {
        String exceptionName = e.getClass().getSimpleName();
        boolean userExists = exceptionName.equals("UserExistsException");
        boolean userNotFound = exceptionName.equals("UserNotFoundException");
        String error = null;
        HttpStatus status = null;
        if (userExists) {
            error = "User exists";
            status = HttpStatus.CONFLICT;
        }
        else if (userNotFound) {
            error = "User couldn't be found";
            status = HttpStatus.NOT_FOUND;
        }
        return buildResponseEntity(new ApiError(status, error, e));
    }

    public Exception throwException(Exception e) {
        return throwException(e);
    }
}
