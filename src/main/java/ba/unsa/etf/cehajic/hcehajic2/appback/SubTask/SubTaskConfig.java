package ba.unsa.etf.cehajic.hcehajic2.appback.SubTask;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class SubTaskConfig {

    @Bean
    CommandLineRunner commandLineRunnerTask(SubTaskRepository repository) {
        return args -> {
            SubTask s1 = new SubTask("SubDescription1", Long.valueOf(1), Long.valueOf(1));
            SubTask s2 = new SubTask("SubDescription2", Long.valueOf(1), Long.valueOf(1));
            repository.saveAll(
                    List.of(s1, s2)
            );
        };
    }
}
