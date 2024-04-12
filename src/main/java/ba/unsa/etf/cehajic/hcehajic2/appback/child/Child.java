package ba.unsa.etf.cehajic.hcehajic2.appback.child;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table
@JsonIgnoreProperties("hibernateLazyInitializer")
public class Child {

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
    private String email;
    private Boolean kidMale;   
    private String qualities;
    private String preferences;
    private String special;
    private Long managerId;
    @Transient
    private LocalDate dateOfBirth;

    public Child(){};
    public Child(String name, String surname,String email, LocalDate dateOfBirth,boolean male, String qualities, String preferences, String special, Long managerId) {
        Name = name;
        Surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.qualities = qualities;
        this.kidMale = male;
        this.preferences = preferences;
        this.special = special;
        this.managerId = managerId;
    }

    public Child(String name, String surname, String email, LocalDate dateOfBirth) {
        Name = name;
        Surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
    }

    public Child(String name, String surname, String email, LocalDate dateOfBirth, boolean kidMale) {
        Name = name;
        Surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.kidMale = kidMale;
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
        return ((this.Name.toLowerCase()).charAt(0) + this.getSurname().toLowerCase() + this.id)
                .replace("č", "c")
                .replace("ć", "c")
                .replace("ž", "z")
                .replace("š", "s")
                .replace("đ", "d");
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getKidMale() {
        return kidMale;
    }

    public void setKidMale(Boolean kidMale) {
        this.kidMale = kidMale;
    }

    public String getQualities() {
        return qualities;
    }

    public void setQualities(String kidName) {
        this.qualities = kidName;
    }
    public String getSpecial() {
        return special;
    }

    public void setSpecial(String kidName) {
        this.special = kidName;
    }
    public String getPreferences() {
        return preferences;
    }

    public void setPreferences(String kidName) {
        this.preferences = kidName;
    }
    public Long getManagerId() {
        return managerId;
    }

    public void setManagerId(Long kidName) {
        this.managerId = kidName;
    }
    

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", Name='" + Name + '\'' +
                ", Surname='" + Surname + '\'' +
                ", Email=" + email + '\'' +
                ", kidMale=" + kidMale + '\'' +
                ", dateOfBirth=" + dateOfBirth + '\'' +
                ", qualities=" + qualities + '\'' +
                ", preferences=" + preferences + '\'' +
                ", special=" + special + '\'' +
                ", managerId=" + managerId +
                '}';
    }
}
