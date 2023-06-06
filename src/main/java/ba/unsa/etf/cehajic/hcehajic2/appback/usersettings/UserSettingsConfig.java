package ba.unsa.etf.cehajic.hcehajic2.appback.usersettings;

import ba.unsa.etf.cehajic.hcehajic2.appback.task.Task;
import ba.unsa.etf.cehajic.hcehajic2.appback.task.TaskRepository;
import org.apache.catalina.User;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class UserSettingsConfig {
    @Bean
    CommandLineRunner commandLineRunnerUserSeetings(UserSettingsRepository repository) {
        return args -> {
            UserSettings t1 = new UserSettings(Long.valueOf(1));
            UserSettings t2 = new UserSettings(Long.valueOf(1));
            repository.save(t1);
            repository.save(t2);
        };
    }
}
