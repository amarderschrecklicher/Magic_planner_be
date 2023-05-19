package ba.unsa.etf.cehajic.hcehajic2.appback.account;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;

@Entity
@Table
public class Account {

    @Id
    @SequenceGenerator(
            name = "account_sequence",
            sequenceName = "account_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "account_sequence"
    )
    private Long id;
    private String Name;
    private String Surname;
    private String Password;
    @Transient
    private String Username;
    private LocalDate dateOfBirth;
    @Transient
    private int age;

    public Account(){};
    public Account(String name, String surname, String password, LocalDate dateOfBirth) {
        Name = name;
        Surname = surname;
        Password = password;
        this.dateOfBirth = dateOfBirth;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSurname() {
        return Surname;
    }

    public void setSurname(String surname) {
        Surname = surname;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getAge() {
        return Period.between(this.dateOfBirth, LocalDate.now()).getYears();
    }

    public String getUsername() {
        return (this.Name.toLowerCase()).charAt(0) + this.getSurname().toLowerCase() + this.id;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", Name='" + Name + '\'' +
                ", Surname='" + Surname + '\'' +
                ", Password='" + Password + '\'' +
                ", Username='" + Username + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", age=" + age +
                '}';
    }
}
