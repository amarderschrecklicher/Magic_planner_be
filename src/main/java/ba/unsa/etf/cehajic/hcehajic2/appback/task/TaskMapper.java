package ba.unsa.etf.cehajic.hcehajic2.appback.task;

import ba.unsa.etf.cehajic.hcehajic2.appback.child.Child;

public class TaskMapper {

    public static TaskRequestDTO toDTO(Task task) {
        if (task == null) return null;

        TaskRequestDTO dto = new TaskRequestDTO();
        dto.setId(task.getId());
        dto.setTaskName(task.getTaskName());
        dto.setDescription(task.getDescription());
        dto.setDueDate(task.getDueDate());
        dto.setDueTime(task.getDueTime());
        dto.setChildId(task.getChild() != null ? task.getChild().getId() : null);
        dto.setPriority(task.isPriority());
        dto.setDone(task.isDone());
        dto.setNotiSent(task.isNotiSent());
        dto.setTaskSent(task.getTaskSent());
        dto.setTaskStart(task.getTaskStart());
        dto.setTaskEnd(task.getTaskEnd());
        return dto;
    }

    public static Task toEntity(TaskRequestDTO dto, Child child) {
        if (dto == null) return null;

        Task task = new Task();
        task.setId(dto.getId());
        task.setTaskName(dto.getTaskName());
        task.setDescription(dto.getDescription());
        task.setDueDate(dto.getDueDate());
        task.setDueTime(dto.getDueTime());
        task.setChild(child);
        task.setPriority(dto.isPriority());
        task.setDone(dto.isDone());
        task.setNotiSent(dto.isNotiSent());
        task.setTaskSent(dto.getTaskSent());
        task.setTaskStart(dto.getTaskStart());
        task.setTaskEnd(dto.getTaskEnd());
        return task;
    }
}
