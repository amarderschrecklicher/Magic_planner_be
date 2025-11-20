package ba.unsa.etf.cehajic.hcehajic2.appback.controllers;

import java.util.*;

import ba.unsa.etf.cehajic.hcehajic2.appback.dtos.ChildRequestDTO;
import ba.unsa.etf.cehajic.hcehajic2.appback.models.Token;
import ba.unsa.etf.cehajic.hcehajic2.appback.dtos.TokenRequestDTO;
import ba.unsa.etf.cehajic.hcehajic2.appback.services.TokenService;
import ba.unsa.etf.cehajic.hcehajic2.appback.services.UserSettingsService;
import com.nimbusds.jose.JOSEException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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


    // koristi se
    @PostMapping(path = "/create")
    public ResponseEntity<Token> CreateNewToken(@RequestBody TokenRequestDTO requestDTO) {
                Token newToken = tokenService.AddNewToken(
                requestDTO.getNotificationToken(),
                requestDTO.getAccountId(),
                requestDTO.getModelId()
        );

        return ResponseEntity.ok().body(newToken);
    }


    // koristi se
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

    // koristi se
    @DeleteMapping
    public ResponseEntity<?> deleteToken(@RequestBody TokenRequestDTO token) {
        try {
            tokenService.deleteToken(token.getNotificationToken());

            return ResponseEntity.ok("Delete successful!");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Token not found");
        }
        
    }
    
}
