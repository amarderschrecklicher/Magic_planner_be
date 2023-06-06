package ba.unsa.etf.cehajic.hcehajic2.appback.task;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class TaskConfig {

    @Bean
    CommandLineRunner commandLineRunnerTask(TaskRepository repository) {
        return args -> {
            Task t1 = new Task("Task1", "Description1", LocalDate.of(2023,7, 20), "12:44", Long.valueOf(1), false);
            Task t2 = new Task("Task2", "Description2", LocalDate.of(2023,7, 25), "09:11", Long.valueOf(1), true);
            repository.save(t1);
            repository.save(t2);
        };
    }
}
