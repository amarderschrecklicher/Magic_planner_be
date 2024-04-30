package ba.unsa.etf.cehajic.hcehajic2.appback.child;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import ba.unsa.etf.cehajic.hcehajic2.appback.manager.Manager;
import ba.unsa.etf.cehajic.hcehajic2.appback.manager.ManagerService;
import ba.unsa.etf.cehajic.hcehajic2.appback.usersettings.UserSettingsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.UserRecord.CreateRequest;

import java.util.List;

@RestController
@RequestMapping("/api/v1/child")
@CrossOrigin
class ChildController {

    private final ChildService accountService;
    private final ManagerService managerService;
    private final UserSettingsService settingsService;

    @Autowired
    public ChildController(ChildService accountService, ManagerService managerService,UserSettingsService settingsService) {
        this.accountService = accountService;
        this.managerService = managerService;
        this.settingsService = settingsService;
    }

    @GetMapping
    public List<Child> getAllChildren() {
        return accountService.GetAllChildren();
    }

    @GetMapping(path = "/{id}")
    public Child getAccountById(@PathVariable("id") Long id) {
        Child c = accountService.GetChildById(id);
        return c;
    }

    @PostMapping(path = "/create")
    public ResponseEntity<?> addNewChild(@RequestBody ChildRequestDTO requestDTO) {
        System.out.println("Creating new User!");

        if (accountService.existsByEmail(requestDTO.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body("Email already exists");
        }

        Child newAccount = accountService.CreateNewAccount(
                requestDTO.getName(),
                requestDTO.getSurname(),
                requestDTO.getKidMale(),
                requestDTO.getDateOfBirth(),
                requestDTO.getQualities(),
                requestDTO.getPreferences(),
                requestDTO.getSpecial(),
                requestDTO.getManagerId(),
                requestDTO.getEmail(),
                requestDTO.getPassword()
        );

        Manager m = managerService.getManagerById(requestDTO.getManagerId());
        newAccount.setManager(m);

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
    public ResponseEntity<Child> updateName(@PathVariable("id") Long id, @RequestBody String nameJson) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(nameJson);
            String name = jsonNode.get("name").asText();

            Child updatedUser = accountService.UpdateName(id, name);

            return ResponseEntity.ok(updatedUser);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping(path = "/surname/{id}")
    public ResponseEntity<Child> updateSurname(@PathVariable("id") Long id, @RequestBody String surnameJson) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(surnameJson);
            String surname = jsonNode.get("surname").asText();

            Child updatedUser = accountService.UpdateSurname(id, surname);

            return ResponseEntity.ok(updatedUser);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping(path = "/email/{id}")
    public ResponseEntity<Child> updateEmail(@PathVariable("id") Long id, @RequestBody String emailJson) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(emailJson);
            String surname = jsonNode.get("email").asText();

            Child updatedUser = accountService.UpdateEmail(id, surname);

            return ResponseEntity.ok(updatedUser);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping(path = "/password/{id}")
    public ResponseEntity<Child> updatePassword(@PathVariable("id") Long id, @RequestBody String passJson) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(passJson);
            String pass = jsonNode.get("password").asText();

            Child updatedUser = accountService.updatePassword(id, pass);

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
}
