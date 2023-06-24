package ba.unsa.etf.cehajic.hcehajic2.appback.account;

import ba.unsa.etf.cehajic.hcehajic2.appback.usersettings.UserSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
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
             && (acc.getUsername().equals(accName)
                || acc.getEmail().equals(accName))) {
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

    public  Account CreateNewAccount(String name, String surname, String password, LocalDate dateOfBirth) {
        Account account = new Account(name, surname, password, dateOfBirth);
        Account savedAcc = accountRepository.save(account);
        return savedAcc;
    }

    public  Account CreateNewAccount(String name, String surname, String email,String password, LocalDate dateOfBirth) {
        Account account = new Account(name, surname, email, password, dateOfBirth);
        Account savedAcc = accountRepository.save(account);
        return savedAcc;
    }

    public  Account CreateNewAccount(String name, String surname, String email, String kidName,String password, LocalDate dateOfBirth) {
        Account account = new Account(name, surname, email, kidName, password, dateOfBirth);
        Account savedAcc = accountRepository.save(account);
        return savedAcc;
    }

    public  Account CreateNewAccount(String name, String surname, String email, String kidName, Boolean kidMale, String password, LocalDate dateOfBirth) {
        Account account = new Account(name, surname, email, kidName, password, dateOfBirth, kidMale);
        Account savedAcc = accountRepository.save(account);
        return savedAcc;
    }

    public Account UpdateName(Long id, String name) {
        Account existingAcc = accountRepository.getById(id);
        if (existingAcc == null) return null;
        existingAcc.setName(name);
        accountRepository.save(existingAcc);
        return existingAcc;
    }

    public Account UpdateSurname(Long id, String surname) {
        Account existingAcc = accountRepository.getById(id);
        if (existingAcc == null) return null;
        existingAcc.setSurname(surname);
        accountRepository.save(existingAcc);
        return existingAcc;
    }

    public Account UpdateEmail(Long id, String email) {
        Account existingAcc = accountRepository.getById(id);
        if (existingAcc == null) return null;
        existingAcc.setEmail(email);
        accountRepository.save(existingAcc);
        return existingAcc;
    }

    public Account UpdateKidName(Long id, String kidName) {
        Account existingAcc = accountRepository.getById(id);
        if (existingAcc == null) return null;
        existingAcc.setKidName(kidName);
        accountRepository.save(existingAcc);
        return existingAcc;
    }

    public Account UpdatePassword(Long id, String password) {
        Account existingAcc = accountRepository.getById(id);
        if (existingAcc == null) return null;
        existingAcc.setPassword(password);
        accountRepository.save(existingAcc);
        return existingAcc;
    }
}