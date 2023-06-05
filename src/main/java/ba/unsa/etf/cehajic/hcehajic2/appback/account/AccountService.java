package ba.unsa.etf.cehajic.hcehajic2.appback.account;

import ba.unsa.etf.cehajic.hcehajic2.appback.usersettings.UserSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Account> GetAllAccounts() {
        return accountRepository.findAll();
    }

    public Account GetAccountByCredentials(String accName, String pass) {
        List<Account> possible = GetAllAccounts();
        Account obj = null;
        for (Account acc : possible) {
            if (acc.getPassword().equals(pass)
                && acc.getUsername().equals(accName)) {
                obj = acc;
                break;
            }
        }
        if (obj == null) System.out.println("User doesn't exist!");
        return obj;
    }

    public Account AddNewAccount(Account account) {
        accountRepository.save(account);
        return account;
    }

    public Account UpdateName(Long id, String name) {
        Account existingAcc = accountRepository.getById(id);
        if (existingAcc == null) return null;
        existingAcc.setName(name);
        return existingAcc;
    }

    public Account UpdateSurname(Long id, String surname) {
        Account existingAcc = accountRepository.getById(id);
        if (existingAcc == null) return null;
        existingAcc.setSurname(surname);
        return existingAcc;
    }

    public Account UpdatePassword(Long id, String password) {
        Account existingAcc = accountRepository.getById(id);
        if (existingAcc == null) return null;
        existingAcc.setPassword(password);
        return existingAcc;
    }
}