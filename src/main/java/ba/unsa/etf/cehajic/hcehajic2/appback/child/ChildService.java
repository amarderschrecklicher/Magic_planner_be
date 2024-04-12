package ba.unsa.etf.cehajic.hcehajic2.appback.child;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class ChildService {

    private final ChildRepository accountRepository;

    @Autowired
    public ChildService(ChildRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Child> GetAllAccounts() {
        return accountRepository.findAll();
    }

    public Child GetAccountById(Long id) {
        return accountRepository.getById(id);
    }

    public Child GetAccountByCredentials(String accName) {
        List<Child> possible = GetAllAccounts();
        Child obj = null;
        for (Child acc : possible) {
            if (
             (acc.getUsername().equals(accName)
                || acc.getEmail().equals(accName))) {
                obj = acc;
                break;
            }
        }
        if (obj == null) System.out.println("User doesn't exist!");
        return obj;
    }

    public Child AddNewAccount(Child account) {
        accountRepository.save(account);
        return account;
    }

    public  Child CreateNewAccount(String name, String surname, String email,Boolean male, LocalDate dateOfBirth, String qualities, String preferences, String special, Long managerId) {
        Child account = new Child(name,surname,email,dateOfBirth,male,qualities,preferences,special,managerId);
        Child savedAcc = accountRepository.save(account);
        return savedAcc;
    }


    public Child UpdateName(Long id, String name) {
        Child existingAcc = accountRepository.getById(id);
        if (existingAcc == null) return null;
        existingAcc.setName(name);
        accountRepository.save(existingAcc);
        return existingAcc;
    }

    public Child UpdateSurname(Long id, String surname) {
        Child existingAcc = accountRepository.getById(id);
        if (existingAcc == null) return null;
        existingAcc.setSurname(surname);
        accountRepository.save(existingAcc);
        return existingAcc;
    }

    public Child UpdateEmail(Long id, String email) {
        Child existingAcc = accountRepository.getById(id);
        if (existingAcc == null) return null;
        existingAcc.setEmail(email);
        accountRepository.save(existingAcc);
        return existingAcc;
    }


    public void deleteAllAccounts() {
        accountRepository.deleteAll();
    }
}