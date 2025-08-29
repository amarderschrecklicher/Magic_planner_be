package ba.unsa.etf.cehajic.hcehajic2.appback.token;

import ba.unsa.etf.cehajic.hcehajic2.appback.child.Child;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "token", uniqueConstraints = @UniqueConstraint(columnNames = "token"))
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

    @Column(name = "token", unique = true)
    private String token;

    @ManyToOne(optional = true)
    @JoinColumn(name = "childId") // Specify the name of the foreign key column
    private Child child;

    private String ModelId;

    public Token(String token,Long accountId, String modelId) {
        this.token = token;
        if(accountId!=null){
            this.child = new Child();
            this.child.setId(accountId);
        }
        this.ModelId = modelId;
    }

}
