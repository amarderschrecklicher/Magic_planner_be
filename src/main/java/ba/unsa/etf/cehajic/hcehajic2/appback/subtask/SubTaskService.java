package ba.unsa.etf.cehajic.hcehajic2.appback.subtask;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        List<SubTask> matching = new ArrayList<>();

        for (int i = 0; i < subs.size(); i++)
            if (subs.get(i).getTask().getId().equals(id)) {
                matching.add(subs.get(i));}

        return matching;
    }

    public SubTask AddNewSubTask(SubTask subTask) {
        subTaskRepository.save(subTask);
        return subTask;
    }

    public SubTask FinishSubTask(Long id,Boolean done) {
        SubTask subTask = subTaskRepository.getById(id);
        subTask.setDone(done);
        subTaskRepository.save(subTask);
        return subTask ;
    }

    public void deleteTask(Long id) {
        subTaskRepository.deleteById(id);
    }
}
