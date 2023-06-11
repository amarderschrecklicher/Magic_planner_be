package ba.unsa.etf.cehajic.hcehajic2.appback.usersettings;

import org.hibernate.tool.schema.internal.exec.ScriptTargetOutputToFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserSettingsService {
    private final UserSettingsRepository userSettingsRepository;

    @Autowired
    public UserSettingsService(UserSettingsRepository userSettingsRepository) {
        this.userSettingsRepository = userSettingsRepository;
    }

    public UserSettings CreateUserSettingsDefault(Long id) {
        UserSettings userSettings = new UserSettings(id, "Arial", 12, "#cccc00", "#ccffff", "#ffffff", "#ffffff", "#3399ff");
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
        return existingSettings;
    }

    public UserSettings UpdateFontSize(Long id, int font) {
        UserSettings existingSettings = GetUserSettingsForAccount(id);
        if (existingSettings == null) return null;
        existingSettings.setFontSize(font);
        return existingSettings;
    }

    public UserSettings UpdatePriorityTask(Long id, String rgb) {
        UserSettings existingSettings = GetUserSettingsForAccount(id);
        if (existingSettings == null) return null;
        existingSettings.setColorOfPriorityTask(rgb);
        return existingSettings;
    }

    public UserSettings UpdateNormalTask(Long id, String rgb) {
        UserSettings existingSettings = GetUserSettingsForAccount(id);
        if (existingSettings == null) return null;
        existingSettings.setColorOfNormalTask(rgb);
        return existingSettings;
    }

    public UserSettings UpdateSubTask(Long id, String rgb) {
        UserSettings existingSettings = GetUserSettingsForAccount(id);
        if (existingSettings == null) return null;
        existingSettings.setColorForSubtask(rgb);
        return existingSettings;
    }

    public UserSettings UpdateHeader(Long id, String rgb) {
        UserSettings existingSettings = GetUserSettingsForAccount(id);
        if (existingSettings == null) return null;
        existingSettings.setColorForHeader(rgb);
        return existingSettings;
    }

    public UserSettings UpdateBackground(Long id, String rgb) {
        UserSettings existingSettings = GetUserSettingsForAccount(id);
        if (existingSettings == null) return null;
        existingSettings.setColorForBackground(rgb);
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
