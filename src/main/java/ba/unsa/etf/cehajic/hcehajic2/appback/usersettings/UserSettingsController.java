package ba.unsa.etf.cehajic.hcehajic2.appback.usersettings;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/account/settings")
@CrossOrigin
public class UserSettingsController {
    private final UserSettingsService userSettingsService;

    @Autowired
    public UserSettingsController(UserSettingsService userSettingsService) {
        this.userSettingsService = userSettingsService;
    }

    @GetMapping
    public List<UserSettings> GetAllSettings() { return userSettingsService.GetAllUserSettings(); }

    @GetMapping(path="/{id}")
    public UserSettings GetUserSettings(@PathVariable("id") Long id) { return userSettingsService.GetUserSettingsForAccount(id); }

//    @PutMapping(path="/update")
//    public void Update() {
//
//        userSettingsService.UpdateAccountId(Long.valueOf(1), Long.valueOf(1));
//        userSettingsService.UpdateAccountId(Long.valueOf(2), Long.valueOf(2));
//    }

    @GetMapping(path="mobile/{id}")
    public String GetPhoneCode(@PathVariable Long id) {
        return userSettingsService.GetPhoneCode(id);
    }

    @PostMapping
    public ResponseEntity<UserSettings> CreateUserSettings(@RequestBody UserSettings userSettings) {
        UserSettings newUserSettings = userSettingsService.CreateUserSettings(userSettings);
        return ResponseEntity.ok().body(newUserSettings);
    }

    @PostMapping(path="/default/{id}")
    public ResponseEntity<UserSettings> CreateUserSettings(@PathVariable("id") Long id) {
        UserSettings newUserSettings = userSettingsService.CreateUserSettingsDefault(id);
        return ResponseEntity.ok().body(newUserSettings);
    }

    @PutMapping("/font/{id}")
    public ResponseEntity<UserSettings> updateUserFont(@PathVariable Long id, @RequestBody String string) {
        UserSettings updatedUser = userSettingsService.UpdateFont(id, string);
        return ResponseEntity.ok(updatedUser);
    }

    @PutMapping("/fontsize/{id}")
    public ResponseEntity<UserSettings> updateUserFontSize(@PathVariable Long id, @RequestBody int size) {
        UserSettings updatedUser = userSettingsService.UpdateFontSize(id, size);
        return ResponseEntity.ok(updatedUser);
    }

    @PutMapping("/priority/{id}")
    public ResponseEntity<UserSettings> updateUserPriority(@PathVariable Long id, @RequestBody String string) {
        UserSettings updatedUser = userSettingsService.UpdatePriorityTask(id, string);
        return ResponseEntity.ok(updatedUser);
    }

    @PutMapping("/normal/{id}")
    public ResponseEntity<UserSettings> updateUserNormal(@PathVariable Long id, @RequestBody String string) {
        UserSettings updatedUser = userSettingsService.UpdateNormalTask(id, string);
        return ResponseEntity.ok(updatedUser);
    }

    @PutMapping("/sub/{id}")
    public ResponseEntity<UserSettings> updateUserSub(@PathVariable Long id, @RequestBody String string) {
        UserSettings updatedUser = userSettingsService.UpdateSubTask(id, string);
        return ResponseEntity.ok(updatedUser);
    }

    @PutMapping("/header/{id}")
    public ResponseEntity<UserSettings> updateUserHeader(@PathVariable Long id, @RequestBody String string) {
        UserSettings updatedUser = userSettingsService.UpdateHeader(id, string);
        return ResponseEntity.ok(updatedUser);
    }

    @PutMapping("/background/{id}")
    public ResponseEntity<UserSettings> updateUserBackground(@PathVariable Long id, @RequestBody String string) {
        UserSettings updatedUser = userSettingsService.UpdateBackground(id, string);
        return ResponseEntity.ok(updatedUser);
    }
}
