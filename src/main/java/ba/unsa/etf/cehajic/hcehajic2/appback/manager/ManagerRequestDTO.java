package ba.unsa.etf.cehajic.hcehajic2.appback.manager;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ManagerRequestDTO {
    Long id;
    private String name;
    private String surname;
    private String email;
    private Boolean kidMale;
    private String password;
    private String username;
    private LocalDate dateOfBirth;
    private String jwtToken;
}
