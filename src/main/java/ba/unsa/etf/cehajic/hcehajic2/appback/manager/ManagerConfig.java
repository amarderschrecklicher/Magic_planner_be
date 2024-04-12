package ba.unsa.etf.cehajic.hcehajic2.appback.manager;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class ManagerConfig {
    @Bean
    CommandLineRunner commandLineRunnerManager(ManagerRepository accountRepository) {
        return args -> {
            Manager u1 = new Manager("Harun", "Cehajic","harun.cehajic00@gmail.com", "sifra1", LocalDate.of(2000,7, 20));
            Manager u2 = new Manager("Rijad", "Burovic", "riki@gmail.com",  "sifra2", LocalDate.of(2000,5, 25));
            accountRepository.save(u1);
            accountRepository.save(u2);
        };
    }
}





