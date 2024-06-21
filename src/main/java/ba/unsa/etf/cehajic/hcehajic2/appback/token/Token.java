package ba.unsa.etf.cehajic.hcehajic2.appback.token;

import javax.persistence.*;

import ba.unsa.etf.cehajic.hcehajic2.appback.child.Child;

@Entity
@Table
public class Token {
     @Id
    @SequenceGenerator(
            name = "token_sequence_new",
            sequenceName = "token_sequence_new",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "token_sequence_new"
    )
    private Long id;
    private String Token;
    @ManyToOne(optional = true)
    @JoinColumn(name = "childId") // Specify the name of the foreign key column
    private Child child;
    private String ModelId;
    
    public Token(){};
    public Token(String token,Long accountId, String modelId) {
        this.Token = token;
        if(accountId!=null){
        this.child = new Child();
        this.child.setId(accountId);
        }
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

    public Child getChild() {
        return child;
    }

    public void setChild(Child child) {
        this.child = child;
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
                ", ModelId=" + ModelId + '\'' +
                '}';
    }

}
