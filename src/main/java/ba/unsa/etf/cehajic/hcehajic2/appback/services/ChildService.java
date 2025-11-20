package ba.unsa.etf.cehajic.hcehajic2.appback.services;

import ba.unsa.etf.cehajic.hcehajic2.appback.models.Child;
import ba.unsa.etf.cehajic.hcehajic2.appback.repositories.ChildRepository;
import ba.unsa.etf.cehajic.hcehajic2.appback.models.UserSettings;
import ba.unsa.etf.cehajic.hcehajic2.appback.repositories.UserSettingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ba.unsa.etf.cehajic.hcehajic2.appback.models.Manager;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class ChildService {

    private final ChildRepository accountRepository;
    private final UserSettingsRepository userSettingsRepository;

    @Autowired
    public ChildService(ChildRepository accountRepository, UserSettingsRepository userSettingsRepository) {
        this.accountRepository = accountRepository;
        this.userSettingsRepository = userSettingsRepository;
    }

    public List<Child> GetAllChildren() {
        return accountRepository.findAll();
    }

    public Child GetChildById(Long id) {
        return accountRepository.getById(id);
    }


    public  Child CreateNewAccount(String name, String surname,Boolean male, LocalDate dateOfBirth, String qualities, String preferences, String special, Long managerId,String email,String password) {

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        Child account = new Child(name,surname,dateOfBirth,male,qualities,preferences,special,managerId,email,hashedPassword);
        return accountRepository.save(account);

    }

    public Child updateEmployee(Long id, Child updatedUserData) {
        Child existingChild = accountRepository.getById(id);

        if (existingChild == null) return null;

        if (updatedUserData.getName() != null) {
            existingChild.setName(updatedUserData.getName());
        }
        if (updatedUserData.getSurname() != null) {
            existingChild.setSurname(updatedUserData.getSurname());
        }
        if (updatedUserData.getEmail() != null) {
            existingChild.setEmail(updatedUserData.getEmail());
        }
        if (updatedUserData.getDateOfBirth() != null) {
            existingChild.setDateOfBirth(updatedUserData.getDateOfBirth());
        }
        if (updatedUserData.getKidMale() != null) {
            existingChild.setKidMale(updatedUserData.getKidMale());
        }
        if (updatedUserData.getQualities() != null) {
            existingChild.setQualities(updatedUserData.getQualities());
        }
        if (updatedUserData.getPreferences() != null) {
            existingChild.setPreferences(updatedUserData.getPreferences());
        }
        if (updatedUserData.getSpecial() != null) {
            existingChild.setSpecial(updatedUserData.getSpecial());
        }

        accountRepository.save(existingChild);
        return existingChild;
    }


    public Child UpdateUser(Long id, String name,String surname,String email,String password,Manager manager) {
        Child existingAcc = accountRepository.getById(id);
        
        if(name!=null)
            existingAcc.setName(name);
        if(surname!=null)
            existingAcc.setSurname(surname);
        if(email!=null)
            existingAcc.setEmail(email);
        if(password!=null){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);
        existingAcc.setPassword(hashedPassword);
        }
        if(manager!=null)
            existingAcc.setManager(manager);

        accountRepository.save(existingAcc);
        return existingAcc;
    }


    public void deleteEmployee(Long id) {
        deleteUserSettingsByChildId(id);

        accountRepository.deleteById(id);
    }

    private void deleteUserSettingsByChildId(Long childId) {
        List<UserSettings> userSettings = userSettingsRepository.findAll();
        for (UserSettings userSetting : userSettings) {
            if (userSetting.getChild().getId() == childId) {
                userSettingsRepository.delete(userSetting);
            }
        }
    }

    public boolean existsByEmail(String email) {
        return accountRepository.existsByEmail(email);
    }

}