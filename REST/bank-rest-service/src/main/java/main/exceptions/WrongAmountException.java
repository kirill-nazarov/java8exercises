package main.exceptions;

import java.math.BigDecimal;

public class WrongAmountException extends RuntimeException {

    public WrongAmountException(BigDecimal amount) {
        super("Wrong amount exception. " + "Amount: " + amount);
    }

    public WrongAmountException(Long id, BigDecimal amount) {
        super("Wrong amount exception. Account id: " + id + " Amount: " + amount);
    }
}



