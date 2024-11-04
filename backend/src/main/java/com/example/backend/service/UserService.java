package com.example.backend.service;

import com.example.backend.entity.User;
import com.example.backend.mapper.UserMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Resource
    private UserMapper userMapper;

    public List<User> selectAll() {
        return userMapper.selectAll();
    }


    public User getUserByName(String name) {
        return userMapper.getUserByName(name);
    }

    public PageInfo<User> getPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> users = userMapper.selectAll();
        return PageInfo.of(users);
    }

    public void newUser(User user) {
        userMapper.newUser(user);
    }

    public void updateUser(User user) {
        userMapper.updateUser(user);
    }

    public void deleteUser(User user) {
        userMapper.deleteUser(user);
    }

    public void login(User user) {
        String name = user.getName();
        String password = user.getPassword();
        User u = getUserByName(name);

    }
}
