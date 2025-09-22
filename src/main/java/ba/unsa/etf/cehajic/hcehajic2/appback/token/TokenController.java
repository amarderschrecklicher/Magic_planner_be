package ba.unsa.etf.cehajic.hcehajic2.appback.token;

import java.time.Duration;
import java.time.Instant;
import java.util.*;

import ba.unsa.etf.cehajic.hcehajic2.appback.child.ChildRequestDTO;
import ba.unsa.etf.cehajic.hcehajic2.appback.usersettings.UserSettingsRepository;
import ba.unsa.etf.cehajic.hcehajic2.appback.usersettings.UserSettingsService;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import okhttp3.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


@RestController
@RequestMapping("/api/v1/token")
@CrossOrigin
public class TokenController {

     private final TokenService tokenService;
     private final UserSettingsService userSettingsService;

    @Autowired
    public TokenController(TokenService tokenService, UserSettingsService userSettingsService) {
        this.tokenService = tokenService;
        this.userSettingsService = userSettingsService;
    }

    @GetMapping
    public List<Token> getAllTasks() {
        return tokenService.GetAllTokens();
    }

    @GetMapping(path = "/{id}")
    public List<Token> getTokensForAccount(@PathVariable("id") Long id) {
        Optional<List<Token>> tokens = tokenService.GetTokensForAccount(id);

        return tokens.orElse(null);
    }

    @PostMapping(path = "/create")
    public ResponseEntity<Token> CreateNewToken(@RequestBody TokenRequestDTO requestDTO) {
                Token newToken = tokenService.AddNewToken(
                requestDTO.getNotificationToken(),
                requestDTO.getAccountId(),
                requestDTO.getModelId()
        );

        return ResponseEntity.ok().body(newToken);
    }


    @PostMapping("/mobile")
    public ResponseEntity<?> mobileTokens(@RequestBody TokenRequestDTO requestDTO) throws JOSEException {
        try {
            System.out.println(requestDTO);
            ChildRequestDTO child = userSettingsService.getChildByPhoneLoginString(requestDTO.getPhoneLoginString());

            String access = tokenService.generateJWTToken(child.getId(), child.getEmail(), requestDTO.getPhoneLoginString());
            child.setJwtToken(access);

            tokenService.updateNotificationToken(child.getId(), requestDTO.getNotificationToken(),requestDTO.getModelId());

            return ResponseEntity.ok(child);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Woker not found");
        }

    }


    @DeleteMapping
    public void deleteToken(@RequestBody String token) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(token);
            String tok = jsonNode.get("token").asText();

            tokenService.deleteToken(tok);   

            System.out.println("Delete called!");

        } catch (Exception e) {
        }
        
    }
    
}
