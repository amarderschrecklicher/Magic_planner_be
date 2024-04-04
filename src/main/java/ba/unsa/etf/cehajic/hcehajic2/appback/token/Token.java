package ba.unsa.etf.cehajic.hcehajic2.appback.token;

import javax.persistence.*;

@Entity
@Table
public class Token {
     @Id
    @SequenceGenerator(
            name = "token_sequence",
            sequenceName = "token_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "token_sequence"
    )
    private Long id;
    private String Token;
    private Long accountId;
    private String ModelId;
    
    public Token(){};
    public Token(String token,Long accountId, String modelId) {
        this.Token = token;
        this.accountId = accountId;
        this.ModelId = modelId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        this.Token = token;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getModelId() {
        return ModelId;
    }

    public void setModelId(String modelId) {
        this.ModelId = modelId;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", Token='" + Token + '\'' +
                ", AccountId='" + accountId + '\'' +
                ", ModelId=" + ModelId + '\'' +
                '}';
    }

}
