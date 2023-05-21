package ba.unsa.etf.cehajic.hcehajic2.appback.SubTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubTaskService {

    private final SubTaskRepository subTaskRepository;

    @Autowired
    public SubTaskService(SubTaskRepository subTaskRepository) {
        this.subTaskRepository = subTaskRepository;
    }

    public List<SubTask> GetAllSubTasks() {
        return subTaskRepository.findAll();
    }

    public List<SubTask> GetSubsForTask(Long id) {
        List<SubTask> subs = GetAllSubTasks();
        subs.stream().filter(sub -> sub.getId().equals(id));
        return subs;
    }

    public SubTask AddNewSubTask(SubTask subTask) {
        subTaskRepository.save(subTask);
        return subTask;
    }

    public void deleteTask(Long id) {
        subTaskRepository.deleteById(id);
    }
}
