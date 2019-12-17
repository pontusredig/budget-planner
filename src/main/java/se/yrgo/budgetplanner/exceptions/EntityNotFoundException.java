package se.yrgo.budgetplanner.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends RuntimeException {


    public EntityNotFoundException() {
        super("Resource not found.");
    }

    public EntityNotFoundException(String message) {
        super(message);
    }

}
