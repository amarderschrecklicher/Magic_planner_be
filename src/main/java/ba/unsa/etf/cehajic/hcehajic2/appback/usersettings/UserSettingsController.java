package ba.unsa.etf.cehajic.hcehajic2.appback.usersettings;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<UserSettings> updateUserFont(@PathVariable Long id, @RequestBody String fontJson) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(fontJson);
            String font = jsonNode.get("font").asText();

            UserSettings updatedUser = userSettingsService.UpdateFont(id, font);
//            System.out.println(updatedUser);
            if (updatedUser != null) {
                return ResponseEntity.ok(updatedUser);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/fontsize/{id}")
    public ResponseEntity<UserSettings> updateUserFontSize(@PathVariable Long id,  @RequestBody String sizeJson) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(sizeJson);
            int size = jsonNode.get("size").asInt();

            UserSettings updatedUser = userSettingsService.UpdateFontSize(id, size);
//            System.out.println(updatedUser);
            if (updatedUser != null) {
                return ResponseEntity.ok(updatedUser);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/priority/{id}")
    public ResponseEntity<UserSettings> updateUserPriority(@PathVariable Long id, @RequestBody String priorityJson) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(priorityJson);
            String priority = jsonNode.get("priority").asText();

            UserSettings updatedUser = userSettingsService.UpdatePriorityTask(id, priority);
            if (updatedUser != null) {
                return ResponseEntity.ok(updatedUser);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/normal/{id}")
    public ResponseEntity<UserSettings> updateUserNormal(@PathVariable Long id, @RequestBody String normalJson) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(normalJson);
            String normal = jsonNode.get("normal").asText();

            UserSettings updatedUser = userSettingsService.UpdateNormalTask(id, normal);
            if (updatedUser != null) {
                return ResponseEntity.ok(updatedUser);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/sub/{id}")
    public ResponseEntity<UserSettings> updateUserSub(@PathVariable Long id, @RequestBody String subJson) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(subJson);
            String sub = jsonNode.get("sub").asText();

            UserSettings updatedUser = userSettingsService.UpdateSubTask(id, sub);
            if (updatedUser != null) {
                return ResponseEntity.ok(updatedUser);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/fcolor/{id}")
    public ResponseEntity<UserSettings> updateUserHeader(@PathVariable Long id, @RequestBody String fontColorJson) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(fontColorJson);
            String fontColor = jsonNode.get("fcolor").asText();

            UserSettings updatedUser = userSettingsService.UpdateFontColor(id, fontColor);
            if (updatedUser != null) {
                return ResponseEntity.ok(updatedUser);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping("/background/{id}")
    public ResponseEntity<UserSettings> updateUserBackground(@PathVariable Long id, @RequestBody String bgJson) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(bgJson);
            String background = jsonNode.get("background").asText();

            UserSettings updatedUser = userSettingsService.UpdateBackground(id, background);
            if (updatedUser != null) {
                return ResponseEntity.ok(updatedUser);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    @PutMapping("/progress/{id}")
    public ResponseEntity<UserSettings> updateUserProgress(@PathVariable Long id, @RequestBody String progressJson) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(progressJson);
            String progress = jsonNode.get("progress").asText();

            UserSettings updatedUser = userSettingsService.UpdateProgress(id, progress);
            if (updatedUser != null) {
                return ResponseEntity.ok(updatedUser);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
