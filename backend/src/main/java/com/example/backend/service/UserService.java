package com.example.backend.service;

import com.example.backend.common.MyException;
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

    public List<User> selectAll(User user) {
        return userMapper.selectAll(user);
    }


    public User getUserByName(String name) {
        return userMapper.getUserByName(name);
    }

    public User getUserByEmail(String email) {
        return userMapper.getUserByEmail(email);
    }

    public PageInfo<User> getPage(User user,int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<User> users = userMapper.selectAll(user);
        return PageInfo.of(users);
    }

    public void newUser(User user) {
        userMapper.newUser(user);
    }

    public void updateUser(User user) {
        userMapper.updateUser(user);
    }

    public void deleteUser(List<String> names) {
        for(String name : names) {
            userMapper.deleteUser(name);
        }
    }

}
