package ba.unsa.etf.cehajic.hcehajic2.appback.services;

import ba.unsa.etf.cehajic.hcehajic2.appback.models.Child;
import ba.unsa.etf.cehajic.hcehajic2.appback.models.Token;
import ba.unsa.etf.cehajic.hcehajic2.appback.repositories.TokenRepository;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.*;

@Service
@Transactional
public class TokenService {

    private final TokenRepository tokenRepository;
    private final UserSettingsService userSettingsService;

    @Value("${app.jwt.secret}")   // base64 string iz application.properties
    private String secret;


    private byte[] keyBytes() {
        return Base64.getDecoder().decode(secret);
    }

    private JWSHeader header() {
        return new JWSHeader.Builder(JWSAlgorithm.HS256)
                .type(JOSEObjectType.JWT)
                .build();
    }

    @Autowired
    public TokenService(TokenRepository tokenRepository, UserSettingsService userSettingsService) {
        this.tokenRepository = tokenRepository;
        this.userSettingsService = userSettingsService;
    }

    public List<Token> GetAllTokens() {
        return tokenRepository.findAll();
    }

    public Optional<List<Token>> GetTokensForAccount(Long id) {
        return tokenRepository.findAllByChildId(id);
    }

    public Token AddNewToken(String token, Long accountId, String modelId) {
        Token token1 = new Token(token,accountId,modelId);
        Token savedToken = tokenRepository.save(token1);
        return savedToken;
    }

    public void deleteToken(String token) {
        tokenRepository.deleteByToken(token);
    }
    
    public void updateNotificationToken(Long id, String newToken, String modelId) {

        Optional<Token> existingToken = tokenRepository.findByChildId(id);
        Token token = existingToken.orElse(null);

        if (token == null) {
            token = new Token();
            Child child = new Child();
            child.setId(id);
            token.setChild(child);
        }
        else if (!token.getToken().equals(newToken)) {return;}

        token.setToken(newToken);
        token.setModelId(modelId);
        tokenRepository.save(token);
    }

    public String generateJWTToken(Long id, String email, String phoneLoginString) throws JOSEException {

        List<String> roles = phoneLoginString.equals("no") ? List.of("MANAGER") : List.of("WORKER");
        var now = Instant.now();


        JWTClaimsSet.Builder builder = new JWTClaimsSet.Builder()
                .subject(String.valueOf(id))     // used as authentication.name
                .issueTime(Date.from(now))
                .claim("email", email)
                .claim("roles", roles);

        // Managers expire in 30 minutes; Workers have no exp claim (no time limit)
        if (phoneLoginString.equals("no")) {
            builder.expirationTime(Date.from(now.plus(Duration.ofMinutes(30))));
        }else {
            builder.expirationTime(Date.from(now.plus(Duration.ofDays(1))));
            builder.claim("phoneLoginString",  userSettingsService.updatePhoneLoginString(phoneLoginString));
        }

        SignedJWT jwt = new SignedJWT(header(), builder.build());
        jwt.sign(new MACSigner(keyBytes()));

        return jwt.serialize();
    }




}
