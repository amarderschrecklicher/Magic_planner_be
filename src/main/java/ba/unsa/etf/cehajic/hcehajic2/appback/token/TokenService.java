package ba.unsa.etf.cehajic.hcehajic2.appback.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TokenService {

     private final TokenRepository tokenRepository;

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
            if (tokens.get(i).getAccountId() == id)
                matching.add(tokens.get(i));

        return matching;
    }

    public Token AddNewToken(String token, Long accountId, String modelId) {
        Token token1 = new Token(token,accountId,modelId);
        Token savedToken = tokenRepository.save(token1);
        return savedToken;
    }

    public void deleteToken(Long id) {
        tokenRepository.deleteById(id);
    }
    
    public Token UpdateToken(Long id, String newToken){
        java.util.Optional<Token> existingAcc = tokenRepository.findById(id);
        Token acc = existingAcc.orElse(null);
        if (acc == null) return null;
        acc.setToken(newToken);
        tokenRepository.save(acc);

        return acc;
    }
    
}
