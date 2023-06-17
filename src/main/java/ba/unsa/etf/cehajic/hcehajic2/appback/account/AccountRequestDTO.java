package ba.unsa.etf.cehajic.hcehajic2.appback.account;

import java.time.LocalDate;

public class AccountRequestDTO {
    private String name;
    private String surname;
    private String password;
    private LocalDate dateOfBirth;

    public AccountRequestDTO() {
        // Default no-argument constructor
    }

    public AccountRequestDTO(String name, String surname, String password, LocalDate dateOfBirth) {
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
}
