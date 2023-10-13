package com.cydeo.service.impl;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.TaskDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.enums.Status;
import com.cydeo.service.ProjectService;
import com.cydeo.service.TaskService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProjectServiceImpl extends AbstractMapService<String, ProjectDTO> implements ProjectService {
    TaskService taskService;

    public ProjectServiceImpl(TaskService taskService){
        this.taskService = taskService;
    }
    @Override
    public ProjectDTO save(ProjectDTO object) {
        if(object.getProjectStatus() == null){
            object.setProjectStatus(Status.OPEN);
        }
        return super.save(object.getProjectCode(),object);
    }

    @Override
    public List<ProjectDTO> findAll() {
        return super.findAll();
    }


    @Override
    public ProjectDTO findById(String projectCode) {
        return super.findById(projectCode);
    }

    @Override
    public void deleteById(String projectCode) {
        super.deleteById(projectCode);
    }

    @Override
    public void update(ProjectDTO object) {
        ProjectDTO projectDTO = findById(object.getProjectCode());
        if(object.getProjectStatus() ==null){
            object.setProjectStatus(projectDTO.getProjectStatus());
        }
        super.update(object.getProjectCode(), object);
    }

    @Override
    public void complete(ProjectDTO projectDTO) {
        projectDTO.setProjectStatus(Status.COMPLETE);
        super.save(projectDTO.getProjectCode(),projectDTO);
    }

    @Override
    public List<ProjectDTO> getCountedListOfProjectDTO(UserDTO manager) {
        List<ProjectDTO> projectDTOList = findAll().stream()
                .filter(projectDTO -> projectDTO.getAssignedManager().equals(manager))
                .map(projectDTO -> {
                    List<TaskDTO> taskList = taskService.findTasksByManager(manager);
                    int completeTaskCounts = (int)taskList.stream().filter(taskDTO -> taskDTO.getProjectDTO().equals(projectDTO)
                            && taskDTO.getTaskStatus() == Status.COMPLETE).count();
                    int unfinishedTaskCounts = (int)taskList.stream().filter(taskDTO -> taskDTO.getProjectDTO().equals(projectDTO)
                            && taskDTO.getTaskStatus() != Status.COMPLETE  ).count();;
                    projectDTO.setCompleteTaskCounts(completeTaskCounts);
                    projectDTO.setUnfinishedTaskCounts(unfinishedTaskCounts);
                    return projectDTO;
                })
                .collect(Collectors.toList());
        return projectDTOList;
    }
}
