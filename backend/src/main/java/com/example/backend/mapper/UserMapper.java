package com.example.backend.mapper;

import com.example.backend.entity.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserMapper {
    List<User> selectAll();

    @Select("select * from user where name = #{name}")
    User getUserByName(String name);

    void newUser(User user);

    void updateUser(User user);

    void deleteUser(User user);
}
