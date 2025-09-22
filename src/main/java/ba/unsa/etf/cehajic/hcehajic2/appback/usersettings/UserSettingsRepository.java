package ba.unsa.etf.cehajic.hcehajic2.appback.usersettings;

import ba.unsa.etf.cehajic.hcehajic2.appback.child.Child;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserSettingsRepository extends JpaRepository<UserSettings, Long>{

    Optional<UserSettings> findByPhoneLoginString(String phoneLoginString);
}