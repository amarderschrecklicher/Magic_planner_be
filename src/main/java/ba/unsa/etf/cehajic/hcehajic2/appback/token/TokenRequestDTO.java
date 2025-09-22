package ba.unsa.etf.cehajic.hcehajic2.appback.token;

import lombok.Data;

@Data
public class TokenRequestDTO {
    private String notificationToken;
    private String jwtRefreshToken;
    private Long accountId;
    private String modelId;
    private String email;
    private String name;
    private String phoneLoginString;
}
