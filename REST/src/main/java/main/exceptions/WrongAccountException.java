package main.exceptions;

public class WrongAccountException extends RuntimeException {

    public WrongAccountException(Long id) {
        super("The same account address for from and to exception. Account id: " + id);
    }
}
