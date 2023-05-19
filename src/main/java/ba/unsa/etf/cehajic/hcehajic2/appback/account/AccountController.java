package ba.unsa.etf.cehajic.hcehajic2.appback.account;

import org.springframework.beans.factory.annotation.Autowired;
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
}
