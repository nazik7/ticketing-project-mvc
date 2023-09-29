package com.cydeo.service.impl;

import com.cydeo.dto.UserDTO;
import com.cydeo.service.UserService;

import java.util.List;

public class UserServiceImpl extends AbstractMapService<UserDTO,String> implements UserService {
    @Override
    public UserDTO save(UserDTO userDTO) {
        return super.save(userDTO.getUserName(), userDTO);
    }

    @Override
    public List<UserDTO> findAll() {
        return super.findAll();
    }

    @Override
    public void deleteById(String id) {
        super.deleteById(id);
    }

    @Override
    public UserDTO findById(String id) {
        return findById(id);
    }
}
