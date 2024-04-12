package ba.unsa.etf.cehajic.hcehajic2.appback.token;


import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;



public class TokenConfig {

     @Bean
    CommandLineRunner commandLineRunnerTask(TokenRepository repository) {
        return args -> {
            Token t1 = new Token("sdasdasdad",Long.valueOf(243), "ABC");
            Token t2 = new Token("sdasdasdaJKd",Long.valueOf(243), "ABC");
            repository.save(t1);
            repository.save(t2);
        };
    }
    
}
