package main.controller;

import main.database.AccountRepository;
import main.entities.Account;
import main.entities.AccountDTO;
import main.exceptions.AccountNotFoundException;
import main.exceptions.NotEnoughFundsException;
import main.exceptions.WrongAccountException;
import main.exceptions.WrongAmountException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
class AccountController {

    private final AccountRepository repository;

    AccountController(AccountRepository repository) {
        this.repository = repository;
    }

    /***
     * Get accounts info
     */
    @GetMapping("/accounts")
    List<Account> all() {
        return repository.findAll();
    }

    /***
     * Get single account info
     */
    @GetMapping("/accounts/{id}")
    Account one(@PathVariable Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException(id));
    }

    /***
     * Deposit funds
     */
    @PutMapping("/deposit/{id}")
    Account deposit(@RequestBody Account accountData, @PathVariable Long id) {
        try {
            Account account = repository.getOne(id);
            BigDecimal balance = account.getBalance();
            if (accountData.getBalance().signum() != 1) {
                throw new WrongAmountException(id, accountData.getBalance());
            }
            BigDecimal newBalance = balance.add(accountData.getBalance());
            account.setBalance(newBalance);
            return repository.save(account);
        } catch (EntityNotFoundException exception) {
            throw new AccountNotFoundException(id);
        }
    }

    /***
     * Withdraw funds
     */
    @PutMapping("/withdraw/{id}")
    Account withdraw(@RequestBody Account accountData, @PathVariable Long id) {
        try {
            Account account = repository.getOne(id);
            BigDecimal balance = account.getBalance();
            if (accountData.getBalance().signum() != 1) {
                throw new WrongAmountException(id, accountData.getBalance());
            }
            if (balance.subtract(accountData.getBalance()).signum() == -1) {
                throw new NotEnoughFundsException(id, accountData.getBalance(), balance);
            }
            BigDecimal newBalance = balance.subtract(accountData.getBalance());
            account.setBalance(newBalance);
            return repository.save(account);
        } catch (EntityNotFoundException exception) {
            throw new AccountNotFoundException(id);
        }
    }

    /***
     * Transfer funds
     */
    @Transactional
    @PutMapping("/transfer/")
    List<Account> transfer(@RequestBody AccountDTO dto) {
        if (dto.getAmount().signum() != 1) {
            throw new WrongAmountException(dto.getAmount());
        }
        if (dto.getFromID() == dto.getToID()) {
            throw new WrongAccountException(dto.getFromID());
        }
        Account from = repository.findById(dto.getFromID())
                .orElseThrow(() -> new AccountNotFoundException(dto.getFromID()));

        Account to = repository.findById(dto.getToID())
                .orElseThrow(() -> new AccountNotFoundException(dto.getToID()));
        if (from.getBalance().subtract(dto.getAmount()).signum() == -1) {
            throw new NotEnoughFundsException(dto.getFromID(), dto.getAmount(), from.getBalance());
        }

        BigDecimal fromNewBalance = from.getBalance().subtract(dto.getAmount());
        BigDecimal toNewBalance = to.getBalance().add(dto.getAmount());
        from.setBalance(fromNewBalance);
        repository.save(from);
        to.setBalance(toNewBalance);
        repository.save(to);
        List<Account> accounts = new ArrayList<>();
        accounts.add(from);
        accounts.add(to);

        return accounts;
    }
}
