package ba.unsa.etf.cehajic.hcehajic2.appback.account;

import ba.unsa.etf.cehajic.hcehajic2.appback.task.Task;
import ba.unsa.etf.cehajic.hcehajic2.appback.usersettings.UserSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
@CrossOrigin
class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public List<Account> getAllAccounts() {
        return accountService.GetAllAccounts();
    }

    @GetMapping(path="/{username}/{pass}")
    public Account getAccountByCredentials(@PathVariable("username") String accName,
                                           @PathVariable("pass") String pass) {
        return accountService.GetAccountByCredentials(accName, pass);
    }

    @PostMapping(path="/create")
    public ResponseEntity<Account> addNewAccount(@RequestBody Account account) {
        Account exists = null;
        exists = accountService.GetAccountByCredentials(account.getName(), account.getPassword());
        if (exists == null)
            return ResponseEntity.badRequest().body(account);
        System.out.println("Creating new User!");
        Account newAccount = accountService.AddNewAccount(account);
        return ResponseEntity.ok().body(newAccount);
    }

    @PutMapping(path="/name/id")
    public ResponseEntity<Account> updateName(@PathVariable("id") Long id, @RequestBody String name) {
        Account updatedAccount = accountService.UpdateName(id, name);
        return ResponseEntity.ok().body(updatedAccount);
    }

    @PutMapping(path="/surname/id")
    public ResponseEntity<Account> updateSurname(@PathVariable("id") Long id, @RequestBody String surname) {
        Account updatedAccount = accountService.UpdateSurname(id, surname);
        return ResponseEntity.ok().body(updatedAccount);
    }

    @PutMapping(path="/pass/id")
    public ResponseEntity<Account> updatePassword(@PathVariable("id") Long id, @RequestBody String password) {
        Account updatedAccount = accountService.UpdatePassword(id, password);
        return ResponseEntity.ok().body(updatedAccount);
    }
}
