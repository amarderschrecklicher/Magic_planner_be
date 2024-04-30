package ba.unsa.etf.cehajic.hcehajic2.appback.child;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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

    public List<Child> GetAllChildren() {
        return accountRepository.findAll();
    }

    public Child GetChildById(Long id) {
        return accountRepository.getById(id);
    }

    public Child GetAccountByCredentials(String accName) {
        List<Child> possible = GetAllChildren();
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

    public  Child CreateNewAccount(String name, String surname,Boolean male, LocalDate dateOfBirth, String qualities, String preferences, String special, Long managerId,String email,String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        Child account = new Child(name,surname,dateOfBirth,male,qualities,preferences,special,managerId,email,hashedPassword);
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

    public Child updatePassword(Long id, String password) {
        Child existingAcc = accountRepository.getById(id);
        if (existingAcc == null) {
            return null;
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        existingAcc.setPassword(hashedPassword);
        accountRepository.save(existingAcc);
        
        return existingAcc;
    }

    public boolean existsByEmail(String email) {
        return accountRepository.existsByEmail(email);
    }
}