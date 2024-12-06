package ba.unsa.etf.cehajic.hcehajic2.appback.task;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByChildId(Long childId);

    //@Query("SELECT t FROM Task t WHERE CURRENT_TIMESTAMP BETWEEN TIMESTAMPADD(SECOND, TIMESTAMPDIFF(SECOND, t.createdDate, CONCAT(t.dueDate, 'T', t.dueTime)) / 2, t.createdDate) AND CONCAT(t.dueDate, 'T', t.dueTime)")
    //List<Task> findTasksWithHalfTimeLeft();
    

}
