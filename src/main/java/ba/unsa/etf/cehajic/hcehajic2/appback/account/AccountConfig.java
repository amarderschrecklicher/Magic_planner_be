package ba.unsa.etf.cehajic.hcehajic2.appback.account;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class AccountConfig {
    @Bean
    CommandLineRunner commandLineRunnerAccount(AccountRepository accountRepository) {
        return args -> {
            Account u1 = new Account("Harun", "Cehajic", "sifra1", LocalDate.of(2000,7, 20));
            Account u2 = new Account("Rijad", "Burovic", "sifra2", LocalDate.of(2000,5, 25));
            accountRepository.save(u1);
            accountRepository.save(u2);
        };
    }
}





