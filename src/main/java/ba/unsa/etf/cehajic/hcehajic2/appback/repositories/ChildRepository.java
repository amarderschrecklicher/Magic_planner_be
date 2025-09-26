package ba.unsa.etf.cehajic.hcehajic2.appback.repositories;

import ba.unsa.etf.cehajic.hcehajic2.appback.models.Child;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChildRepository extends JpaRepository<Child, Long> {

    boolean existsByEmail(String email);
}