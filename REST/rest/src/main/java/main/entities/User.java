package main.entities;

import lombok.Data;

import javax.persistence.*;
import javax.persistence.Entity;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    public User(String name) {
        this.name = name;
    }

    @OneToOne(cascade = CascadeType.ALL)
    private Account account = new Account();
}

