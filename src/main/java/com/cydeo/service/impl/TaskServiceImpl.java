package com.cydeo.service.impl;

import com.cydeo.dto.TaskDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.enums.Status;
import com.cydeo.service.TaskService;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class TaskServiceImpl extends AbstractMapService<Long, TaskDTO> implements TaskService{
    @Override
    public TaskDTO save(TaskDTO object) {
        if(object.getId()==null){
            object.setId(UUID.randomUUID().getMostSignificantBits());
        }
        if(object.getTaskStatus()==null){
            object.setTaskStatus(Status.OPEN);
        }

        if (object.getAssignedDate() == null){
            object.setAssignedDate(LocalDate.now());
        }

        return super.save(object.getId(),object);
    }


    @Override
    public List<TaskDTO> findAll() {
        return super.findAll();
    }

    @Override
    public TaskDTO findById(Long id) {
        return super.findById(id);
    }


    @Override
    public void deleteById(Long id) {
        super.deleteById(id);
    }

    @Override
    public void update(TaskDTO taskDTO) {
        TaskDTO foundTask = findById(taskDTO.getId());
        taskDTO.setAssignedDate(foundTask.getAssignedDate());
        taskDTO.setTaskStatus(foundTask.getTaskStatus());
        super.update(taskDTO.getId(), taskDTO);
    }

    @Override
    public List<TaskDTO> findTasksByManager(UserDTO manager) {
        return super.findAll().stream()
                .filter(taskDTO -> taskDTO.getProjectDTO().getAssignedManager().equals(manager))
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> findAllUnfinishedTasks() {
        return super.findAll().stream()
                .filter(taskDTO -> !taskDTO.getTaskStatus().equals(Status.COMPLETE))
                .collect(Collectors.toList());
    }

    @Override
    public void updateStatus(TaskDTO task) {
        findById(task.getId()).setTaskStatus(task.getTaskStatus());
        update(task);
    }
}
