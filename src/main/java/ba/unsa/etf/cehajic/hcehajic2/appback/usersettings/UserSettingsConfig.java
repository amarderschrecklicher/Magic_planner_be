package ba.unsa.etf.cehajic.hcehajic2.appback.usersettings;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserSettingsConfig {
    @Bean
    CommandLineRunner commandLineRunnerUserSettings(UserSettingsRepository repository) {
        return args -> {
            UserSettings t1 = new UserSettings(Long.valueOf(1));
            UserSettings t2 = new UserSettings(Long.valueOf(2));
            repository.save(t1);
            repository.save(t2);
        };
    }
}
