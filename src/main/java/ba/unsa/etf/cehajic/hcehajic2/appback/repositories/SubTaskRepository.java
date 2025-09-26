package ba.unsa.etf.cehajic.hcehajic2.appback.repositories;

import ba.unsa.etf.cehajic.hcehajic2.appback.models.SubTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubTaskRepository extends JpaRepository<SubTask, Long> {

}
