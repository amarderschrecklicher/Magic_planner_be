package ba.unsa.etf.cehajic.hcehajic2.appback.usersettings;

import ba.unsa.etf.cehajic.hcehajic2.appback.child.Child;
import ba.unsa.etf.cehajic.hcehajic2.appback.child.ChildMapper;
import ba.unsa.etf.cehajic.hcehajic2.appback.child.ChildRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static ba.unsa.etf.cehajic.hcehajic2.appback.usersettings.UserSettings.generateRandomString;

@Service
@Transactional
public class UserSettingsService {
    private final UserSettingsRepository userSettingsRepository;

    @Autowired
    public UserSettingsService(UserSettingsRepository userSettingsRepository) {
        this.userSettingsRepository = userSettingsRepository;
    }

    public UserSettings CreateUserSettingsDefault(Child child) {
        UserSettings userSettings = new UserSettings(child.getId(),
                "Palatino Linotype",
                22,
                "#FF6347",
                "#00BFFF",
                "#FF6347",
                "#141414",
                "#F5FFFA",
                "#00b200");
        userSettings.setChild(child);
        this.userSettingsRepository.save(userSettings);
        return userSettings;
    }

    public UserSettings CreateUserSettings(UserSettings userSettings) {
        this.userSettingsRepository.save(userSettings);
        return userSettings;
    }

    public List<UserSettings> GetAllUserSettings() {
        return userSettingsRepository.findAll();
    }

    public UserSettings GetUserSettingsForAccount(Long id) {
        List<UserSettings> settings = GetAllUserSettings();
        UserSettings matching = null;

        for (int i = 0; i < settings.size(); i++)
            if (settings.get(i).getChild().getId().equals(id))
                matching = settings.get(i);

        return matching;
    }

    public String GetPhoneCode(Long id) {
        UserSettings userSettings = GetUserSettingsForAccount(id);
        if (userSettings == null) return null;
        return userSettings.getPhoneLoginString();
    }

    public UserSettings updateUserSettings(UserSettings userSettings) {
        userSettingsRepository.save(userSettings);
        return userSettings;
    }

    public void UpdateAccountId(Long aid, Long sid) {
        userSettingsRepository.getById(sid).getChild().setId(sid);
    }

    public ChildRequestDTO getChildByPhoneLoginString(String phoneLoginString) {

        Optional<UserSettings> userSettings = userSettingsRepository.findByPhoneLoginString(phoneLoginString.trim());

        return ChildMapper.toDto(userSettings.get().getChild());
    }

    public String updatePhoneLoginString(String phoneLoginString){
        Optional<UserSettings> userSettings = userSettingsRepository.findByPhoneLoginString(phoneLoginString.trim());
        Long accountId = userSettings.get().getChild().getId();

        userSettings.get().setPhoneLoginString(generateRandomString(accountId ,5));

        return userSettingsRepository.save(userSettings.get()).getPhoneLoginString();

    }
}
