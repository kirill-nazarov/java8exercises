package main.database;

import lombok.extern.slf4j.Slf4j;
import main.entities.Account;
import main.entities.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
@Slf4j
class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository) {
        return args -> {
            User user1 = new User("First User");
            Account account1 = new Account(new BigDecimal("1000"));
            user1.setAccount(account1);

            User user2 = new User("Second User");
            Account account2 = new Account(new BigDecimal("2000"));
            user2.setAccount(account2);

            log.info("Preloading " + userRepository.save(user1));
            log.info("Preloading " + userRepository.save(user2));

        };
    }
}
