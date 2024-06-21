package ba.unsa.etf.cehajic.hcehajic2.appback.task;

import nl.martijndwars.webpush.PushService;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;

@Configuration
public class PushServiceConfig {

    private final VapidConfig vapidConfig;

    public PushServiceConfig(VapidConfig vapidConfig) {
        this.vapidConfig = vapidConfig;
    }

    @Bean
    public PushService pushService() {
        Security.addProvider(new BouncyCastleProvider());
        
        try {
            return new PushService()
                    .setPublicKey(vapidConfig.getPublicKey())
                    .setPrivateKey(vapidConfig.getPrivateKey())
                    .setSubject("mailto:your-email@example.com");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }
}
