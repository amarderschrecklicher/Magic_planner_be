package ba.unsa.etf.cehajic.hcehajic2.appback.usersettings;
import ba.unsa.etf.cehajic.hcehajic2.appback.child.Child;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Random;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserSettings {
    @Id
    @SequenceGenerator(
            name = "user_settings_sequence_new",
            sequenceName = "user_settings_sequence_new",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_settings_sequence_new"
    )
    private Long id;
    @OneToOne
    @JoinColumn(name = "childId")
    private Child child;
    private String font;
    private int fontSize;
    private String colorOfPriorityTask;
    private String colorOfNormalTask;
    private String colorForSubtask;
    private String colorForFont;
    private String colorForBackground;
    private String colorForProgress;

    private String phoneLoginString;

    public UserSettings(Long accountId, String font, int fontSize, String colorOfPriorityTask, String colorOfNormalTask, String colorForSubtask, String colorForFont, String colorForBackground, String colorForProgress) {
        this.child = new Child();
        this.child.setId(accountId);
        this.font = font;
        this.fontSize = fontSize;
        this.colorOfPriorityTask = colorOfPriorityTask;
        this.colorOfNormalTask = colorOfNormalTask;
        this.colorForSubtask = colorForSubtask;
        this.colorForFont = colorForFont;
        this.colorForBackground = colorForBackground;
        this.colorForProgress = colorForProgress;
        this.phoneLoginString = accountId + generateRandomString(5) + accountId;
    }

    public static String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            sb.append(randomChar);
        }

        return sb.toString();
    }
}
