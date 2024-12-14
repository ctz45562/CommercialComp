package com.example.backend.mapper;

import com.example.backend.entity.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserMapper {
    List<User> selectAll(User user);

    @Select("select * from user where name = #{name}")
    User getUserByName(String name);

    @Select("select * from user where email = #{email}")
    User getUserByEmail(String email);

    void newUser(User user);

    void updateUser(User user);

    @Delete("delete from user where name = #{name}")
    void deleteUser(String name);
}
