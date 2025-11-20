package ba.unsa.etf.cehajic.hcehajic2.appback.services;

import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

import ba.unsa.etf.cehajic.hcehajic2.appback.mappers.TaskMapper;
import ba.unsa.etf.cehajic.hcehajic2.appback.models.Task;
import ba.unsa.etf.cehajic.hcehajic2.appback.repositories.TaskRepository;
import ba.unsa.etf.cehajic.hcehajic2.appback.dtos.TaskRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ba.unsa.etf.cehajic.hcehajic2.appback.models.SubTask;
import ba.unsa.etf.cehajic.hcehajic2.appback.repositories.SubTaskRepository;

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

    public List<TaskRequestDTO> GetTasksForAccount(Long id) {
        return taskRepository.findByChildId(id)
                .stream()
                .map(TaskMapper::toDTO)
                .toList();
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElse(null);
    }

    public List<Task> GetDoneTasksForAccount(Long id) {
        return GetAllTasks().stream()
                .filter(task -> Objects.equals(task.getChild().getId(), id) && task.isDone())
                .sorted((a, b) -> {
                    if (a.getTaskEnd() == null && b.getTaskEnd() == null) return 0;
                    if (a.getTaskEnd() == null) return 1;
                    if (b.getTaskEnd() == null) return -1;
                    return b.getTaskEnd().compareTo(a.getTaskEnd());
                })
                .collect(Collectors.toList());
    }

    public List<Task> GetUndoneTasksForAccount(Long id) {
        return GetAllTasks().stream()
                .filter(task -> Objects.equals(task.getChild().getId(), id) && Objects.equals(task.getDueDate(), LocalDate.now()) && !task.isDone() )
                .sorted((a, b) -> {
                    return a.getTaskSent().compareTo(b.getTaskSent());
                })
                .collect(Collectors.toList());
    }

    public Task AddNewTask(Task task) {
        task.setTaskSent(LocalDateTime.now());
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
        Task task = taskRepository.findById(id).orElse(null);
        assert task != null;
        task.setTaskStart(ZonedDateTime.now(ZoneId.of("Europe/Sarajevo")).toLocalDateTime());
        taskRepository.save(task);
        return task;
    }

    public Task FinishTask(Long id) {
        Task task = taskRepository.findById(id).orElse(null);
        assert task != null;
        task.setDone(true);
        task.setTaskEnd(ZonedDateTime.now(ZoneId.of("Europe/Sarajevo")).toLocalDateTime());
        taskRepository.save(task);
        return task;
    }

    public Task NotificationSent(Long id) {
        Task task = taskRepository.findById(id).orElse(null);
        assert task != null;
        task.setNotiSent(true);
        taskRepository.save(task);
        return task;
    }

    public List<Long> calculateTasksInAndOutOfDeadline(Long childId) {
        List<Task> completedTasks = taskRepository.findCompletedTasksByChildId(childId);

        long tasksInDeadline = completedTasks.stream()
                .filter(task -> task.getTaskEnd() != null && task.getDueDate() != null &&
                        !task.getTaskEnd().toLocalDate().isAfter(task.getDueDate()))
                .count();

        long tasksOutOfDeadline = completedTasks.size() - tasksInDeadline;

        return List.of(tasksInDeadline, tasksOutOfDeadline);
    }

    public Map<LocalDate, Long> calculateCompletedTasksOverTime(Long childId) {
        List<Task> completedTasks = taskRepository.findCompletedTasksByChildId(childId);

        LocalDate today = LocalDate.now();
        Month currentMonth = today.getMonth();

        return completedTasks.stream()
                .filter(task -> task.getTaskEnd() != null && task.getTaskEnd().getMonth() == currentMonth)
                .collect(Collectors.groupingBy(
                        task -> task.getTaskEnd().toLocalDate(),
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
                if (task.getTaskStart() != null && task.getTaskEnd() != null) {
                    Map<String, Object> taskInfo = new HashMap<>();
                    taskInfo.put("date", task.getTaskEnd().toLocalDate());
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
        return Duration.between(task.getTaskStart(), task.getTaskEnd()).getSeconds();
    }

}
