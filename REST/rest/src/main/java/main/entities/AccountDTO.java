package main.entities;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountDTO {

    private long fromID;
    private long toID;
    private BigDecimal amount;

}
