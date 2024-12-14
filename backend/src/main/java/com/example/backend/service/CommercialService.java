package com.example.backend.service;

import com.example.backend.entity.Commercial;
import com.example.backend.mapper.CommercialMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommercialService {
    @Resource
    private CommercialMapper CommercialMapper;

    public List<Commercial> selectAll(Commercial Commercial) {
        return CommercialMapper.selectAll(Commercial);
    }

    public List<Commercial> getRandomCommercials(int limit){
        return  CommercialMapper.getRandomCommercials(limit);
    }

    public Commercial getCommercialByUrl(String url){
        return CommercialMapper.getCommercialByUrl(url);
    }

    public Commercial getCommercialById(Integer id) {
        return CommercialMapper.getCommercialById(id);
    }

    public PageInfo<Commercial> getPage(Commercial Commercial,int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Commercial> Commercials = CommercialMapper.selectAll(Commercial);
        return PageInfo.of(Commercials);
    }

    public void newCommercial(Commercial Commercial) {
        CommercialMapper.newCommercial(Commercial);
    }

    public void updateCommercial(Commercial Commercial) {
        CommercialMapper.updateCommercial(Commercial);
    }

    public void deleteCommercial(List<Integer> ids) {
        for(Integer id : ids) {
            CommercialMapper.deleteCommercial(id);
        }
    }

    public void newUniqueCommercials(List<Commercial> commercials) {
        for(Commercial commercial : commercials) {
            Commercial c = CommercialMapper.getCommercialByUrl(commercial.getUrl());
            if(c != null) {
                commercial.setId(c.getId());
                CommercialMapper.updateCommercial(commercial);
            }
            else {
                CommercialMapper.newCommercial(commercial);
            }
        }
    }
}
