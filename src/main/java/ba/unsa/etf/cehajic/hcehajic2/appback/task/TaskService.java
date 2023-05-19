package ba.unsa.etf.cehajic.hcehajic2.appback.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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
        tasks.stream().filter(task -> task.getId().equals(id));
        return tasks;
    }

    public Task AddNewTask(Task task) {
        taskRepository.save(task);
        return task;
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}
