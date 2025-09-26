package ba.unsa.etf.cehajic.hcehajic2.appback.subtask;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubTaskRepository extends JpaRepository<SubTask, Long> {

}
