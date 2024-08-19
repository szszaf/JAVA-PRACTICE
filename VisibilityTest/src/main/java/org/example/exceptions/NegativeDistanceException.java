package org.example.exceptions;

public class NegativeDistanceException extends Exception{
    public NegativeDistanceException(){
        super("Negative distance");
    }

    public NegativeDistanceException(String message){
        super(message);
    }

    public NegativeDistanceException(String message, Throwable cause){
        super(message, cause);
    }
}
