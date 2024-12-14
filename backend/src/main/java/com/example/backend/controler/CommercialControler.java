package com.example.backend.controler;

import com.example.backend.common.MyException;
import com.example.backend.common.Result;
import com.example.backend.entity.Commercial;
import com.example.backend.service.CommercialService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/commercial") //请求前缀
public class CommercialControler {
    @Resource
    private CommercialService CommercialService;


    @GetMapping("/getAllCommercials")
    public Result getAllCommercials(){
        List<Commercial> list = CommercialService.selectAll(null);
        return Result.success(list);
    }

    @GetMapping("/getCommercialById")
    public Result getCommercialById(@RequestParam Integer id){
        Commercial Commercial = CommercialService.getCommercialById(id);
        return Result.success(Commercial);
    }

    @GetMapping("/getPage")
    public Result getPage(@RequestParam(defaultValue = "1") int pageNum,
                          @RequestParam(defaultValue = "10") int pageSize,
                          Commercial Commercial){
        return Result.success(CommercialService.getPage(Commercial,pageNum,pageSize));
    }

    @GetMapping("/getRandomCommercials")
    public Result getRandomCommercials(@RequestParam(defaultValue = "10") int limit){
        return Result.success(CommercialService.getRandomCommercials(limit));
    }

    @PostMapping("/newUniqueCommercials")
    public Result newUniqueCommercials(@RequestBody List<Commercial>Commercials){
        CommercialService.newUniqueCommercials(Commercials);
        return Result.success();
    }

    @PostMapping("/newCommercial")
    public Result newCommercial(@RequestBody Commercial Commercial){
        CommercialService.newCommercial(Commercial);
        return Result.success();
    }

    @PutMapping("/updateCommercial")
    public Result updateCommercial(@RequestBody Commercial Commercial){
        CommercialService.updateCommercial(Commercial);
        return Result.success();
    }

    @DeleteMapping("/deleteCommercial")
    public Result deleteCommercial(@RequestBody List<Integer> ids){
        CommercialService.deleteCommercial(ids);
        return Result.success();
    }

//    @PutMapping("/login")
//    public Result login(@RequestBody Commercial Commercial){
//
//    }
}
