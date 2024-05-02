package ba.unsa.etf.cehajic.hcehajic2.appback.task;

import ba.unsa.etf.cehajic.hcehajic2.appback.subtask.SubTask;
import ba.unsa.etf.cehajic.hcehajic2.appback.subtask.SubTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TaskService {

    private final TaskRepository taskRepository;
    private final SubTaskRepository subTaskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, SubTaskRepository subTaskRepository) {
        this.taskRepository = taskRepository;
        this.subTaskRepository = subTaskRepository;
    }

    public List<Task> GetAllTasks() {
        return taskRepository.findAll();
    }

    public List<Task> GetTasksForAccount(Long id) {
        List<Task> tasks = GetAllTasks();
        List<Task> matching = new ArrayList<>();
        for (int i = 0; i < tasks.size(); i++)
            if (tasks.get(i).getChild().getId() == id)
                matching.add(tasks.get(i));

        return matching;
    }
    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    public List<Task> GetDoneTasksForAccount(Long id) {
        List<Task> tasks = GetAllTasks();
        List<Task> matching = new ArrayList<>();
        for (int i = 0; i < tasks.size(); i++)
            if (tasks.get(i).getChild().getId() == id && tasks.get(i).isDone())
                matching.add(tasks.get(i));

        return matching;
    }

    public List<Task> GetUndoneTasksForAccount(Long id) {
        List<Task> tasks = GetAllTasks();
        List<Task> matching = new ArrayList<>();
        for (int i = 0; i < tasks.size(); i++)
            if (tasks.get(i).getChild().getId() == id && !tasks.get(i).isDone())
                matching.add(tasks.get(i));

        return matching;
    }

    public Task AddNewTask(Task task) {
        task.setChild(task.getChild());
        taskRepository.save(task);
        return task;
    }


    public void deleteTask(Long id) {
        deleteSubtasksByTaskId(id);

        taskRepository.deleteById(id);
    }

    private void deleteSubtasksByTaskId(Long taskId) {
        List<SubTask> subTasks = subTaskRepository.findAll();
        for (SubTask subTask : subTasks) {
            if (subTask.getTask().getId() == taskId) {
                subTaskRepository.delete(subTask);
            }
        }
    }

    public Task FinishTask(Long id) {
        Task tOld = taskRepository.getById(id);
        tOld.setDone(true);
        taskRepository.save(tOld);
        return tOld;
    }
}
