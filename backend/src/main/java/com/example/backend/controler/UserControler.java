package com.example.backend.controler;

import com.example.backend.common.MyException;
import com.example.backend.common.Result;
import com.example.backend.entity.User;
import com.example.backend.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user") //请求前缀
public class UserControler {
    @Resource
    private UserService userService;


    @GetMapping("/getAllUsers")
    public Result getAllUsers(){
        List<User> list = userService.selectAll(null);
        return Result.success(list);
    }

    @GetMapping("/getUserByName")
    public Result getUserByName(@RequestParam String name){
        User user = userService.getUserByName(name);
        return Result.success(user);
    }

    @GetMapping("/getPage")
    public Result getPage(@RequestParam(defaultValue = "1") int pageNum,
                          @RequestParam(defaultValue = "10") int pageSize,
                          User user){
        return Result.success(userService.getPage(user,pageNum,pageSize));
    }

    @PostMapping("/newUser")
    public Result newUser(@RequestBody User user){
        String name=user.getName();
        String email=user.getEmail();
        if(name==null)return Result.error("用户名为空");
        if(email==null)return Result.error("邮箱为空");
        if(userService.getUserByName(name)!=null)return Result.error("用户已存在");
        if(userService.getUserByEmail(email)!=null)return Result.error("该邮箱已注册");
        if(user.getPassword().length()<6)return Result.error("密码长度必须大于等于6字符");
        if(user.getRole()==null)return Result.error("未选择身份");
        userService.newUser(user);
        return Result.success();
    }

    @PutMapping("/updateUser")
    public Result updateUser(@RequestBody User user){
        String name=user.getName();
        if(name==null)return Result.error("用户名为空");
        if(userService.getUserByName(name)==null)return Result.error("用户不存在");
        if(user.getPassword().length()<6)return Result.error("密码长度必须大于等于6字符");
        if(user.getRole()==null)return Result.error("未选择身份");
        if(user.getEmail()==null)return Result.error("邮箱为空");
        userService.updateUser(user);
        return Result.success();
    }


    @DeleteMapping("/deleteUser")
    public Result deleteUser(@RequestBody List<String> names){
        userService.deleteUser(names);
        return Result.success();
    }

    @PostMapping("/login")
    public Result login(@RequestBody User user){
        String name = user.getName();
        String password = user.getPassword();
        User u = userService.getUserByName(name);
        if(u == null || !u.getPassword().equals(password)) return Result.error("用户名或密码错误");
        return Result.success(u);
    }
}
