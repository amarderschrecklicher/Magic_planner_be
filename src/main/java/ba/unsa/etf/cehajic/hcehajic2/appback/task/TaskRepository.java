package ba.unsa.etf.cehajic.hcehajic2.appback.task;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByChildId(Long childId);

    @Query(value = "SELECT * FROM Task t WHERE CURRENT_TIMESTAMP BETWEEN t.task_sent + INTERVAL '1 second' * (EXTRACT(EPOCH FROM (CAST(CONCAT(t.due_date, ' ', t.due_time) AS timestamp) - t.task_sent)) / 2) AND CAST(CONCAT(t.due_date, ' ', t.due_time) AS timestamp) AND t.noti_sent = FALSE;", nativeQuery=true)
    List<Task> findTasksWithHalfTimeLeft();

    @Query("SELECT t FROM Task t WHERE t.child.id = ?1 AND t.done = true")
    List<Task> findCompletedTasksByChildId(Long childId);
}
