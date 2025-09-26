package ba.unsa.etf.cehajic.hcehajic2.appback.dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ChildRequestDTO {
    private long id;
    private String name;
    private String surname;
    private Boolean kidMale;
    private LocalDate dateOfBirth;
    private String qualities;
    private String preferences;
    private String special;
    private Long managerId;
    private String email;
    private String password;
    private String jwtToken;
    private String login;
}
