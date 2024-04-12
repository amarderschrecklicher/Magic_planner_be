package ba.unsa.etf.cehajic.hcehajic2.appback.child;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/child")
@CrossOrigin
class ChildController {

    private final ChildService accountService;

    @Autowired
    public ChildController(ChildService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public List<Child> getAllAccounts() {
        return accountService.GetAllAccounts();
    }

    @GetMapping(path = "/{username}")
    public Child getAccountByCredentials(@PathVariable("username") String accName) {
        return accountService.GetAccountByCredentials(accName);
    }

    @GetMapping(path = "/{id}")
    public Child getAccountById(@PathVariable("id") Long id) {
        return accountService.GetAccountById(id);
    }

    @PostMapping(path = "/create")
    public ResponseEntity<Child> addNewAccount(@RequestBody ChildRequestDTO requestDTO) {
        System.out.println("Creating new User!");

        Child newAccount = accountService.CreateNewAccount(
                requestDTO.getName(),
                requestDTO.getSurname(),
                requestDTO.getEmail(),
                requestDTO.getKidMale(),
                requestDTO.getDateOfBirth(),
                requestDTO.getQualities(),
                requestDTO.getPreferences(),
                requestDTO.getSpecial(),
                requestDTO.getManagerId()
        );

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
