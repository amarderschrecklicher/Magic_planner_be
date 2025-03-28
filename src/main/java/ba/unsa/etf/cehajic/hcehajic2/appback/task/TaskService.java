package ba.unsa.etf.cehajic.hcehajic2.appback.task;

import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ba.unsa.etf.cehajic.hcehajic2.appback.subtask.SubTask;
import ba.unsa.etf.cehajic.hcehajic2.appback.subtask.SubTaskRepository;

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

        return taskRepository.findByChildId(id);
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
        task.setSent(LocalDateTime.now());
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
            if (Objects.equals(subTask.getTask().getId(), taskId)) {
                subTaskRepository.delete(subTask);
            }
        }
    }

    public Task StartTask(Long id){
        Task task = taskRepository.getById(id);
        task.setStart(ZonedDateTime.now(ZoneId.of("Europe/Sarajevo")).toLocalDateTime());
        taskRepository.save(task);
        return task;
    }

    public Task FinishTask(Long id) {
        Task task = taskRepository.getById(id);
        task.setDone(true);
        task.setEnd(ZonedDateTime.now(ZoneId.of("Europe/Sarajevo")).toLocalDateTime());
        taskRepository.save(task);
        return task;
    }

    public Task NotificationSent(Long id) {
        Task task = taskRepository.getById(id);
        task.setNotiSent(true);
        taskRepository.save(task);
        return task;
    }

    public List<Long> calculateTasksInAndOutOfDeadline(Long childId) {
        List<Task> completedTasks = taskRepository.findCompletedTasksByChildId(childId);

        long tasksInDeadline = completedTasks.stream()
                .filter(task -> task.getEnd() != null && task.getDueDate() != null &&
                        !task.getEnd().toLocalDate().isAfter(task.getDueDate()))
                .count();

        long tasksOutOfDeadline = completedTasks.size() - tasksInDeadline;

        return List.of(tasksInDeadline, tasksOutOfDeadline);
    }

    public Map<LocalDate, Long> calculateCompletedTasksOverTime(Long childId) {
        List<Task> completedTasks = taskRepository.findCompletedTasksByChildId(childId);

        LocalDate today = LocalDate.now();
        Month currentMonth = today.getMonth();

        return completedTasks.stream()
                .filter(task -> task.getEnd() != null && task.getEnd().getMonth() == currentMonth)
                .collect(Collectors.groupingBy(
                        task -> task.getEnd().toLocalDate(),
                        Collectors.counting()
                ));
    }

    public List<Map<String, Object>> getTaskSummary(Long childId) {
        List<Task> tasks = taskRepository.findByChildId(childId);

        Map<String, List<Task>> tasksGroupedByName = tasks.stream()
                .collect(Collectors.groupingBy(Task::getTaskName));

        List<Map<String, Object>> result = new ArrayList<>();

        for (String taskName : tasksGroupedByName.keySet()) {
            List<Task> group = tasksGroupedByName.get(taskName);

            Map<String, Object> entry = new HashMap<>();
            entry.put("taskName", taskName);

            List<Map<String, Object>> taskDetails = new ArrayList<>();

            for (Task task : group) {
                if (task.getStart() != null && task.getEnd() != null) {
                    Map<String, Object> taskInfo = new HashMap<>();
                    taskInfo.put("date", task.getEnd().toLocalDate());
                    taskInfo.put("executionTime", calculateExecutionTime(task));
                    taskDetails.add(taskInfo);
                }
            }

            entry.put("taskDetails", taskDetails);

            result.add(entry);
        }

        return result;
    }

    private long calculateExecutionTime(Task task) {
        return Duration.between(task.getStart(), task.getEnd()).getSeconds();
    }

}
