package ba.unsa.etf.cehajic.hcehajic2.appback.task;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByChildId(Long childId);

    @Query("SELECT t FROM Task t WHERE t.dueDate BETWEEN :start AND :end")
    List<Task> findTasksEndingIn30Minutes(LocalDateTime start, LocalDateTime end);
}
