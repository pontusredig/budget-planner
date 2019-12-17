package se.yrgo.budgetplanner.exceptions;

public class UserExistsException extends RuntimeException{
    public UserExistsException(){
    super("You already have a user with that email and password!!!");
    }
    public UserExistsException(String message){
        super(message);
    }
}
