package ba.unsa.etf.cehajic.hcehajic2.appback.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> GetAllTasks() {
        return taskRepository.findAll();
    }

    public List<Task> GetTasksForAccount(Long id) {
        List<Task> tasks = GetAllTasks();
        List<Task> matching = new ArrayList<>();
        for (int i = 0; i < tasks.size(); i++)
            if (tasks.get(i).getAccountId() == id)
                matching.add(tasks.get(i));

        return matching;
    }

    public List<Task> GetDoneTasksForAccount(Long id) {
        List<Task> tasks = GetAllTasks();
        List<Task> matching = new ArrayList<>();
        for (int i = 0; i < tasks.size(); i++)
            if (tasks.get(i).getAccountId() == id && tasks.get(i).isDone())
                matching.add(tasks.get(i));

        return matching;
    }

    public List<Task> GetUndoneTasksForAccount(Long id) {
        List<Task> tasks = GetAllTasks();
        List<Task> matching = new ArrayList<>();
        for (int i = 0; i < tasks.size(); i++)
            if (tasks.get(i).getAccountId() == id && !tasks.get(i).isDone())
                matching.add(tasks.get(i));

        return matching;
    }

    public Task AddNewTask(Task task) {
        taskRepository.save(task);
        return task;
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    public Task FinishTask(Long id) {
        Task tOld = taskRepository.getById(id);
        tOld.setDone(true);
        taskRepository.save(tOld);
        return tOld;
    }
}
