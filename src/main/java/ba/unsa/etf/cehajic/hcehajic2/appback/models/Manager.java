package ba.unsa.etf.cehajic.hcehajic2.appback.models;

import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table
@JsonIgnoreProperties({"age","hibernateLazyInitializer"})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Manager {

    @Id
    @SequenceGenerator(
            name = "manager_sequence_new",
            sequenceName = "manager_sequence_new",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "manager_sequence_new"
    )
    private Long id;
    private String Name;
    private String Surname;
    private String Password;
    @Column(unique = true)
    private String email;
    private Boolean kidMale;
    @Transient
    private String Username;
    private LocalDate dateOfBirth;

    public Manager(String name, String surname, String email, String password, LocalDate dateOfBirth, boolean kidMale) {
        Name = name;
        Surname = surname;
        Password = password;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.kidMale = kidMale;
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
