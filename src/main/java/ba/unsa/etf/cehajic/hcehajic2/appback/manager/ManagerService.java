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

    public ManagerRequestDTO GetAccountByCredentials(String accName, String pass) {
        List<Manager> possible = GetAllAccounts();
        Manager obj = null;
        for (Manager acc : possible) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            if (acc.getPassword() != null && encoder.matches(pass, acc.getPassword())
                    && (acc.getUsername().equals(accName) || acc.getEmail().equals(accName))) {
                obj = acc;
                break;
            }
        }
        if (obj == null) {
            System.out.println("User doesn't exist!");
        }
        return ManagerMapper.toRequestDto(obj);
    }

    public  ManagerRequestDTO CreateNewAccount(String name, String surname, String email, Boolean kidMale, String password, LocalDate dateOfBirth) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        Manager account = new Manager(name, surname, email,  hashedPassword, dateOfBirth, kidMale);
        account.setUsername(account.getUsername());
        accountRepository.save(account);
        return ManagerMapper.toRequestDto(account);
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

    public Manager updateUser(Long id, Manager updatedUserData) {
        Manager existingAcc = accountRepository.getById(id);

        if (existingAcc == null) return null;

        if (updatedUserData.getName() != null) {
            existingAcc.setName(updatedUserData.getName());
        }
        if (updatedUserData.getSurname() != null) {
            existingAcc.setSurname(updatedUserData.getSurname());
        }
        if (updatedUserData.getEmail() != null) {
            existingAcc.setEmail(updatedUserData.getEmail());
        }
        if (updatedUserData.getDateOfBirth() != null) {
            existingAcc.setDateOfBirth(updatedUserData.getDateOfBirth());
        }
        if (updatedUserData.getKidMale() != null) {
            existingAcc.setKidMale(updatedUserData.getKidMale());
        }

        accountRepository.save(existingAcc);
        return existingAcc;
    }

    public void deleteAllAccounts() {
        accountRepository.deleteAll();
    }

    public void deleteManagerById(Long id) {
        accountRepository.deleteById(id);
    }

    public boolean existsByEmail(String email) {
        return accountRepository.existsByEmail(email);
    }

    }
