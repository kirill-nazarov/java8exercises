package main.exceptions;

import java.math.BigDecimal;

public class NotEnoughFundsException extends RuntimeException {

    public NotEnoughFundsException(Long id, BigDecimal amount, BigDecimal balance) {
        super("Not enough funds exception. Account id: " + id + " Amount: " + amount + " Available balance:" + balance);
    }
}
