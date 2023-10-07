package com.cydeo.service.impl;

import com.cydeo.dto.TaskDTO;
import com.cydeo.service.TaskService;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class TaskServiceImpl extends AbstractMapService<Long, TaskDTO> implements TaskService{
    @Override
    public TaskDTO save(TaskDTO object) {
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
        super.update(taskDTO.getId(), taskDTO);
    }
}
