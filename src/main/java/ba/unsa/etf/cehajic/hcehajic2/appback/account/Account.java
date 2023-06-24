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
    private String email;
    private String kidName;
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

    public Account(String name, String surname, String email, String password, LocalDate dateOfBirth) {
        Name = name;
        Surname = surname;
        Password = password;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
    }

    public Account(String name, String surname, String email, String kidName, String password, LocalDate dateOfBirth) {
        Name = name;
        Surname = surname;
        Password = password;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.kidName = kidName;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getKidName() {
        return kidName;
    }

    public void setKidName(String kidName) {
        this.kidName = kidName;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", Name='" + Name + '\'' +
                ", Surname='" + Surname + '\'' +
                ", Email=" + email + '\'' +
                ", kidName=" + kidName + '\'' +
                ", Password='" + Password + '\'' +
                ", Username='" + Username + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", age=" + age +
                '}';
    }
}
