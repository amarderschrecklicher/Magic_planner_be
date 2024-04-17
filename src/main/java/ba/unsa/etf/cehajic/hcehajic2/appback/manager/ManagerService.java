package ba.unsa.etf.cehajic.hcehajic2.appback.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;


@Service
@Transactional
public class ManagerService {

    private final ManagerRepository accountRepository;

    @Autowired
    public ManagerService(ManagerRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Manager> GetAllAccounts() {
        return accountRepository.findAll();
    }

    public Manager getManagerById(Long id) {
        return accountRepository.getById(id);
    }

    public Manager GetAccountByCredentials(String accName, String pass) {
        List<Manager> possible = GetAllAccounts();
        Manager obj = null;
        for (Manager acc : possible) {
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

    public Manager AddNewAccount(Manager account) {
        accountRepository.save(account);
        return account;
    }

    public  Manager CreateNewAccount(String name, String surname, String password, LocalDate dateOfBirth) {
        Manager account = new Manager(name, surname, password, dateOfBirth);
        Manager savedAcc = accountRepository.save(account);
        return savedAcc;
    }

    public  Manager CreateNewAccount(String name, String surname, String email,String password, LocalDate dateOfBirth) {
        Manager account = new Manager(name, surname, email, password, dateOfBirth);
        Manager savedAcc = accountRepository.save(account);
        return savedAcc;
    }

    public  Manager CreateNewAccount(String name, String surname, String email, Boolean kidMale, String password, LocalDate dateOfBirth) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        Manager account = new Manager(name, surname, email,  hashedPassword, dateOfBirth, kidMale);
        Manager savedAcc = accountRepository.save(account);
        return savedAcc;
    }

    public Manager UpdateName(Long id, String name) {
        Manager existingAcc = accountRepository.getById(id);
        if (existingAcc == null) return null;
        existingAcc.setName(name);
        accountRepository.save(existingAcc);
        return existingAcc;
    }

    public Manager UpdateSurname(Long id, String surname) {
        Manager existingAcc = accountRepository.getById(id);
        if (existingAcc == null) return null;
        existingAcc.setSurname(surname);
        accountRepository.save(existingAcc);
        return existingAcc;
    }

    public Manager UpdateEmail(Long id, String email) {
        Manager existingAcc = accountRepository.getById(id);
        if (existingAcc == null) return null;
        existingAcc.setEmail(email);
        accountRepository.save(existingAcc);
        return existingAcc;
    }


    public Manager UpdatePassword(Long id, String password) {
        Manager existingAcc = accountRepository.getById(id);
        if (existingAcc == null) return null;
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        existingAcc.setPassword(hashedPassword);
        accountRepository.save(existingAcc);
        return existingAcc;
    }

    public void deleteAllAccounts() {
        accountRepository.deleteAll();
    }

    }
