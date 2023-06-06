package ba.unsa.etf.cehajic.hcehajic2.appback.usersettings;
import javax.persistence.*;
import java.util.Random;

@Entity
@Table
public class UserSettings {
    @Id
    @SequenceGenerator(
            name = "user-settings_sequence",
            sequenceName = "user-settings_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user-settings_sequence"
    )
    private Long id;
    private Long accountId;
    private String font;
    private int fontSize;
    private String colorOfPriorityTask;
    private String colorOfNormalTask;
    private String colorForSubtask;
    private String colorForHeader;
    private String colorForBackground;

    private String phoneLoginString;

    public UserSettings(Long accountId, String font, int fontSize, String colorOfPriorityTask, String colorOfNormalTask, String colorForSubtask, String colorForHeader, String colorForBackground) {
        this.accountId = accountId;
        this.font = font;
        this.fontSize = fontSize;
        this.colorOfPriorityTask = colorOfPriorityTask;
        this.colorOfNormalTask = colorOfNormalTask;
        this.colorForSubtask = colorForSubtask;
        this.colorForHeader = colorForHeader;
        this.colorForBackground = colorForBackground;
        this.phoneLoginString = accountId + generateRandomString(5) + accountId;
    }

    public UserSettings() {

    }

    public UserSettings(Long id) {
        this.accountId = accountId;
        this.font = "Arial";
        this.fontSize = 12;
        this.colorOfPriorityTask = "#000000";
        this.colorOfNormalTask = "#000000";
        this.colorForSubtask = "#000000";
        this.colorForHeader = "#000000";
        this.colorForBackground = "#000000";
        this.phoneLoginString = accountId + generateRandomString(5) + accountId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public String getColorOfPriorityTask() {
        return colorOfPriorityTask;
    }

    public void setColorOfPriorityTask(String colorOfPriorityTask) {
        this.colorOfPriorityTask = colorOfPriorityTask;
    }

    public String getColorOfNormalTask() {
        return colorOfNormalTask;
    }

    public void setColorOfNormalTask(String colorOfNormalTask) {
        this.colorOfNormalTask = colorOfNormalTask;
    }

    public String getColorForSubtask() {
        return colorForSubtask;
    }

    public void setColorForSubtask(String colorForSubtask) {
        this.colorForSubtask = colorForSubtask;
    }

    public String getColorForHeader() {
        return colorForHeader;
    }

    public void setColorForHeader(String colorForHeader) {
        this.colorForHeader = colorForHeader;
    }

    public String getColorForBackground() {
        return colorForBackground;
    }

    public void setColorForBackground(String colorForBackground) {
        this.colorForBackground = colorForBackground;
    }

    public String getPhoneLoginString() {
        return phoneLoginString;
    }

    public void setPhoneLoginString(String phoneLoginString) {
        this.phoneLoginString = phoneLoginString;
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

    @Override
    public String toString() {
        return "UserSettings{" +
                "accountId=" + accountId +
                ", font='" + font + '\'' +
                ", colorOfPriorityTask='" + colorOfPriorityTask + '\'' +
                ", colorOfNormalTask='" + colorOfNormalTask + '\'' +
                ", colorForSubtask='" + colorForSubtask + '\'' +
                ", colorForHeader='" + colorForHeader + '\'' +
                ", colorForBackground='" + colorForBackground + '\'' +
                '}';
    }
}
