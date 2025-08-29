package ba.unsa.etf.cehajic.hcehajic2.appback.child;

import ba.unsa.etf.cehajic.hcehajic2.appback.manager.Manager;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties({"age","hibernateLazyInitializer","username"})
public class Child {

    @Id
    @SequenceGenerator(
            name = "child_sequence_new",
            sequenceName = "child_sequence_new",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "child_sequence_new"
    )
    private Long id;
    private String Name;
    private String Surname;
    private Boolean kidMale;
    private String qualities;
    private String preferences;
    private String special;
    @ManyToOne
    @JoinColumn(name = "managerId") // Specify the name of the foreign key column
    private Manager manager;
    private LocalDate dateOfBirth;
    @Column(unique = true)
    private String email;
    private String Password;

    public Child(String name, String surname, LocalDate dateOfBirth,boolean male, String qualities, String preferences, String special, Long managerId, String email ,String password) {
        Name = name;
        Surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.qualities = qualities;
        this.kidMale = male;
        this.preferences = preferences;
        this.special = special;
        this.Password = password;
        this.manager = new Manager();
        this.manager.setId(managerId);
    }


    public String getUsername() {
        return ((this.Name.toLowerCase()).charAt(0) + this.getSurname().toLowerCase() + this.id)
                .replace("č", "c")
                .replace("ć", "c")
                .replace("ž", "z")
                .replace("š", "s")
                .replace("đ", "d");
    }

}
