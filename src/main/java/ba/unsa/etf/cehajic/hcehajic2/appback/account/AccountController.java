package ba.unsa.etf.cehajic.hcehajic2.appback.account;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
@CrossOrigin
class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public List<Account> getAllAccounts() {
        return accountService.GetAllAccounts();
    }

    @GetMapping(path = "/{username}/{pass}")
    public Account getAccountByCredentials(@PathVariable("username") String accName,
                                           @PathVariable("pass") String pass) {
        return accountService.GetAccountByCredentials(accName, pass);
    }

    @GetMapping(path = "/{id}")
    public Account getAccountById(@PathVariable("id") Long id) {
        return accountService.GetAccountById(id);
    }

    @PostMapping(path = "/create")
    public ResponseEntity<Account> addNewAccount(@RequestBody AccountRequestDTO requestDTO) {
        System.out.println("Creating new User!");

        Account newAccount = accountService.CreateNewAccount(
                requestDTO.getName(),
                requestDTO.getSurname(),
                requestDTO.getEmail(),
                requestDTO.getKidName(),
                requestDTO.getKidMale(),
                requestDTO.getPassword(),
                requestDTO.getDateOfBirth()
        );

        return ResponseEntity.ok().body(newAccount);
    }

    @PutMapping(path = "/name/{id}")
    public ResponseEntity<Account> updateName(@PathVariable("id") Long id, @RequestBody String nameJson) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(nameJson);
            String name = jsonNode.get("name").asText();

            Account updatedUser = accountService.UpdateName(id, name);

            return ResponseEntity.ok(updatedUser);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping(path = "/surname/{id}")
    public ResponseEntity<Account> updateSurname(@PathVariable("id") Long id, @RequestBody String surnameJson) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(surnameJson);
            String surname = jsonNode.get("surname").asText();

            Account updatedUser = accountService.UpdateSurname(id, surname);

            return ResponseEntity.ok(updatedUser);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping(path = "/email/{id}")
    public ResponseEntity<Account> updateEmail(@PathVariable("id") Long id, @RequestBody String emailJson) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(emailJson);
            String surname = jsonNode.get("email").asText();

            Account updatedUser = accountService.UpdateEmail(id, surname);

            return ResponseEntity.ok(updatedUser);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PutMapping(path = "/kid/{id}")
    public ResponseEntity<Account> updateKidName(@PathVariable("id") Long id, @RequestBody String kidNameJson) {
        String kidName;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(kidNameJson);
            kidName = jsonNode.get("kidName").asText();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Account updatedUser = accountService.UpdateKidName(id, kidName);

        return ResponseEntity.ok(updatedUser);
    }

    @PutMapping(path = "/pass/{id}")
    public ResponseEntity<Account> updatePassword(@PathVariable("id") Long id, @RequestBody String passwordJson) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(passwordJson);
            String pass = jsonNode.get("password").asText();

            Account updatedUser = accountService.UpdatePassword(id, pass);

            return ResponseEntity.ok(updatedUser);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
