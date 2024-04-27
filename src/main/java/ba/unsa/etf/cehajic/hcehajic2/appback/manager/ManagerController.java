package ba.unsa.etf.cehajic.hcehajic2.appback.manager;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import ba.unsa.etf.cehajic.hcehajic2.appback.child.Child;
import ba.unsa.etf.cehajic.hcehajic2.appback.child.ChildService;
import ba.unsa.etf.cehajic.hcehajic2.appback.usersettings.UserSettingsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord.CreateRequest;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/manager")
@CrossOrigin
class ManagerController {

    private final ManagerService accountService;
    private final ChildService childService;
    private final UserSettingsService settingsService;

    @Autowired
    public ManagerController(ManagerService accountService,ChildService childService,UserSettingsService settingsService) {
        this.accountService = accountService;
        this.childService = childService;
        this.settingsService = settingsService;
    }

    @GetMapping
    public List<Manager> getAllAccounts() {
        return accountService.GetAllAccounts();
    }

    @GetMapping(path = "/children/{id}")
    public List<Child> getAllChildren(@PathVariable("id") Long id) {
        return childService.GetAllChildren().stream()
        .filter(child -> {
            Manager manager = child.getManager();
            return manager != null && manager.getId().equals(id);
        })
        .collect(Collectors.toList());
    }

    @GetMapping(path = "/{username}/{pass}")
    public Manager getAccountByCredentials(@PathVariable("username") String accName,
                                           @PathVariable("pass") String pass) {
        return accountService.GetAccountByCredentials(accName, pass);
    }

    @GetMapping(path = "/{id}")
    public Manager getManagerById(@PathVariable("id") Long id) {
        return accountService.getManagerById(id);
    }

    @PostMapping(path = "/create")
    public ResponseEntity<Manager> addNewAccount(@RequestBody ManagerRequestDTO requestDTO) {
        System.out.println("Creating new User!");

        Manager newAccount = accountService.CreateNewAccount(
                requestDTO.getName(),
                requestDTO.getSurname(),
                requestDTO.getEmail(),
                requestDTO.getKidMale(),
                requestDTO.getPassword(),
                requestDTO.getDateOfBirth()
        );

        CreateRequest request = new CreateRequest()
        .setEmail(newAccount.getEmail())
        .setPassword(newAccount.getPassword())
        .setDisplayName(requestDTO.getName()+ " "+ requestDTO.getSurname())
        .setEmailVerified(true);     

        try {
            FirebaseAuth.getInstance().createUser(request);
            settingsService.CreateUserSettingsDefault(newAccount.getId());

        } catch (FirebaseAuthException e) {
            e.printStackTrace();
        };   

        return ResponseEntity.ok().body(newAccount);
    }

    @PutMapping(path = "/name/{id}")
    public ResponseEntity<Manager> updateName(@PathVariable("id") Long id, @RequestBody String nameJson) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(nameJson);
            String name = jsonNode.get("name").asText();

            Manager updatedUser = accountService.UpdateName(id, name);

            return ResponseEntity.ok(updatedUser);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping(path = "/surname/{id}")
    public ResponseEntity<Manager> updateSurname(@PathVariable("id") Long id, @RequestBody String surnameJson) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(surnameJson);
            String surname = jsonNode.get("surname").asText();

            Manager updatedUser = accountService.UpdateSurname(id, surname);

            return ResponseEntity.ok(updatedUser);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping(path = "/email/{id}")
    public ResponseEntity<Manager> updateEmail(@PathVariable("id") Long id, @RequestBody String emailJson) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(emailJson);
            String surname = jsonNode.get("email").asText();

            Manager updatedUser = accountService.UpdateEmail(id, surname);

            return ResponseEntity.ok(updatedUser);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }


    @PutMapping(path = "/pass/{id}")
    public ResponseEntity<Manager> updatePassword(@PathVariable("id") Long id, @RequestBody String passwordJson) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(passwordJson);
            String pass = jsonNode.get("password").asText();

            Manager updatedUser = accountService.UpdatePassword(id, pass);

            return ResponseEntity.ok(updatedUser);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
    @DeleteMapping
    public ResponseEntity<String> deleteAllAccounts() {
        try {
            accountService.deleteAllAccounts();
            return ResponseEntity.ok("All accounts deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
             .body("An error occurred while deleting all accounts.");
        }
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Manager> updateUser(@PathVariable("id") Long id, @RequestBody Manager updatedUserData) {
        try {
            Manager updatedUser = accountService.updateUser(id, updatedUserData);

            if (updatedUser == null) {
                return ResponseEntity.notFound().build(); 
            }

            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Return 500 if an error occurs
        }
    }
}
