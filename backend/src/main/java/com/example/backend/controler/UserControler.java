package com.example.backend.controler;

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
        List<User> list = userService.selectAll();
        return Result.success(list);
    }

    @GetMapping("/getUserByName")
    public Result getUserByName(@RequestParam String name){
        User user = userService.getUserByName(name);
        return Result.success(user);
    }

    @GetMapping("/getPage")
    public Result getPage(@RequestParam(defaultValue = "1") int pageNum,
                          @RequestParam(defaultValue = "10") int pageSize){
        return Result.success(userService.getPage(pageNum,pageSize));
    }

    @PostMapping("/newUser")
    public Result newUser(@RequestBody User user){
        userService.newUser(user);
        return Result.success();
    }

    @PutMapping("/updateUser")
    public Result updateUser(@RequestBody User user){
        userService.updateUser(user);
        return Result.success();
    }

    @DeleteMapping("/deleteUser")
    public Result deleteUser(@RequestParam User user){
        userService.deleteUser(user);
        return Result.success();
    }

//    @PutMapping("/login")
//    public Result login(@RequestBody User user){
//
//    }
}
