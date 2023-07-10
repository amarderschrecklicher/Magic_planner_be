package ba.unsa.etf.cehajic.hcehajic2.appback.usersettings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserSettingsService {
    private final UserSettingsRepository userSettingsRepository;

    @Autowired
    public UserSettingsService(UserSettingsRepository userSettingsRepository) {
        this.userSettingsRepository = userSettingsRepository;
    }

    public UserSettings CreateUserSettingsDefault(Long id) {
        UserSettings userSettings = new UserSettings(id,
                "Palatino Linotype",
                22,
                "#FF6347",
                "#00BFFF",
                "#FF6347",
                "#141414",
                "#F5FFFA",
                "#00b200");
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
            if (settings.get(i).getAccountId() == id)
                matching = settings.get(i);

        return matching;
    }

    public UserSettings UpdateFont(Long id, String font) {
        System.out.println(font);
        UserSettings existingSettings = GetUserSettingsForAccount(id);
        if (existingSettings == null) return null;
        existingSettings.setFont(font);
        userSettingsRepository.save(existingSettings);
//        System.out.printf(String.valueOf(GetUserSettingsForAccount(id)));
        return existingSettings;
    }

    public UserSettings UpdateFontSize(Long id, int font) {
        UserSettings existingSettings = GetUserSettingsForAccount(id);
        if (existingSettings == null) return null;
        existingSettings.setFontSize(font);
        userSettingsRepository.save(existingSettings);
//        UserSettings e = GetUserSettingsForAccount(id);
//        System.out.printf(e.toString());
        return existingSettings;
    }

    public UserSettings UpdatePriorityTask(Long id, String rgb) {
        UserSettings existingSettings = GetUserSettingsForAccount(id);
        if (existingSettings == null) return null;
        existingSettings.setColorOfPriorityTask(rgb);
        userSettingsRepository.save(existingSettings);
        return existingSettings;
    }

    public UserSettings UpdateNormalTask(Long id, String rgb) {
        UserSettings existingSettings = GetUserSettingsForAccount(id);
        if (existingSettings == null) return null;
        existingSettings.setColorOfNormalTask(rgb);
        userSettingsRepository.save(existingSettings);
        return existingSettings;
    }

    public UserSettings UpdateSubTask(Long id, String rgb) {
        UserSettings existingSettings = GetUserSettingsForAccount(id);
        if (existingSettings == null) return null;
        existingSettings.setColorForSubtask(rgb);
        userSettingsRepository.save(existingSettings);
        return existingSettings;
    }

    public UserSettings UpdateFontColor(Long id, String rgb) {
        UserSettings existingSettings = GetUserSettingsForAccount(id);
        if (existingSettings == null) return null;
        existingSettings.setColorForFont(rgb);
        userSettingsRepository.save(existingSettings);
        return existingSettings;
    }

    public UserSettings UpdateBackground(Long id, String rgb) {
        UserSettings existingSettings = GetUserSettingsForAccount(id);
        if (existingSettings == null) return null;
        existingSettings.setColorForBackground(rgb);
        userSettingsRepository.save(existingSettings);
        return existingSettings;
    }

    public UserSettings UpdateProgress(Long id, String rgb) {
        UserSettings existingSettings = GetUserSettingsForAccount(id);
        if (existingSettings == null) return null;
        existingSettings.setColorForProgress(rgb);
        userSettingsRepository.save(existingSettings);
        return existingSettings;
    }

    public String GetPhoneCode(Long id) {
        UserSettings userSettings = GetUserSettingsForAccount(id);
        if (userSettings == null) return null;
        return userSettings.getPhoneLoginString();
    }

    public void UpdateAccountId(Long aid, Long sid) {
        userSettingsRepository.getById(sid).setAccountId(aid);
    }
}
