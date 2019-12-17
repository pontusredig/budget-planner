package se.yrgo.budgetplanner.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException() {
        super("No user with that email exists in the database!!!");
    }

    public UserNotFoundException(String message) {
        super(message);
    }
}
