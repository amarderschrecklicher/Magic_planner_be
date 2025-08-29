package ba.unsa.etf.cehajic.hcehajic2.appback.token;

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

import ba.unsa.etf.cehajic.hcehajic2.appback.manager.Manager;

import java.time.Duration;
import java.time.Instant;
import java.util.*;

import static ch.qos.logback.classic.spi.ThrowableProxyVO.build;

@Service
@Transactional
public class TokenService {

    private final TokenRepository tokenRepository;
    @Value("${app.jwt.secret}")   // base64 string iz application.properties
    private String secret;

    @Autowired
    public TokenService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public List<Token> GetAllTokens() {
        return tokenRepository.findAll();
    }

    public List<Token> GetTokensForAccount(Long id) {
        List<Token> tokens = GetAllTokens();
        List<Token> matching = new ArrayList<>();
        for (int i = 0; i < tokens.size(); i++)
            if (tokens.get(i).getChild().getId() == id)
                matching.add(tokens.get(i));

        return matching;
    }

    public Token AddNewToken(String token, Long accountId, String modelId) {
        Token token1 = new Token(token,accountId,modelId);
        Token savedToken = tokenRepository.save(token1);
        return savedToken;
    }

    public void deleteToken(String token) {
        tokenRepository.deleteByToken(token);
    }
    
    public Token UpdateToken(Long id, String newToken){

        Optional<Token> existingToken = tokenRepository.findByChildId(id);
        Token token = existingToken.orElse(null);
        if (token == null) return null;
        token.setToken(newToken);
        tokenRepository.save(token);

        return token;
    }

    public String getManagerToken(Manager manager) {
        List<Token> tokens = GetAllTokens();
        Token matching = new Token("", null,manager.getId().toString());
        for (int i = 0; i < tokens.size();){
            if (tokens.get(i).getChild().getManager().getId() == manager.getId())
                matching.setToken(tokens.get(i).getToken());
                break;
        }

        if(matching.getToken()=="")
            return "no";   

        return matching.getToken();
    }

    public String generateJWTToken(Long id, String email, String name, boolean isManager) throws JOSEException {

        List<String> roles = isManager ? List.of("MANAGER") : List.of("WORKER");
        var now = Instant.now();


        JWTClaimsSet.Builder builder = new JWTClaimsSet.Builder()
                .subject(String.valueOf(id))     // used as authentication.name
                .issueTime(Date.from(now))
                .claim("email", email)
                .claim("name",  name)
                .claim("roles", roles);

        // Managers expire in 30 minutes; Workers have no exp claim (no time limit)
        if (isManager) {
            builder.expirationTime(Date.from(now.plus(Duration.ofMinutes(30))));
        }

        JWTClaimsSet claims = builder.build();

        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.HS256)
                .type(JOSEObjectType.JWT)
                .build();

        SignedJWT jwt = new SignedJWT(header, claims);

        byte[] keyBytes = Base64.getDecoder().decode(secret); // decode base64 secret
        jwt.sign(new MACSigner(keyBytes));

        return jwt.serialize();
    }
}
