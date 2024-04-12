package ba.unsa.etf.cehajic.hcehajic2.appback.child;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class ChildConfig {
    @Bean
    CommandLineRunner commandLineRunnerChild(ChildRepository accountRepository) {
        return args -> {
            Child u1 = new Child("Child1", "c1","c1@gmail.com",LocalDate.of(2000,7, 20),true, " "," "," ",Long.valueOf(1));
            Child u2 = new Child("Child2", "c2","c2@gmail.com",LocalDate.of(2000,5, 25),true, " "," "," ",Long.valueOf(1));
            accountRepository.save(u1);
            accountRepository.save(u2);
        };
    }
}





