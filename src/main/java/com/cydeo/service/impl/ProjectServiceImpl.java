package com.cydeo.service.impl;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.enums.Status;
import com.cydeo.service.ProjectService;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class ProjectServiceImpl extends AbstractMapService<String, ProjectDTO> implements ProjectService {
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
}
