package ba.unsa.etf.cehajic.hcehajic2.appback.token;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long>{
    
}
