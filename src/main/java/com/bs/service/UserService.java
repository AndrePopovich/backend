package com.bs.service;

import com.bs.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    public User addUser(User user);
    public User updateUser(User user);
    public User getUserById(Long id);
    public List<User> getUsers();
    public void deleteUser(Long id);
}
