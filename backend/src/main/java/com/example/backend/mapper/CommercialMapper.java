package com.example.backend.mapper;

import com.example.backend.entity.Commercial;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CommercialMapper {
    List<Commercial> selectAll(Commercial commercial);

    @Select("select * from commercial where id = #{id}")
    Commercial getCommercialById(Integer id);

    void newCommercial(Commercial commercial);

    void updateCommercial(Commercial commercial);

    @Delete("delete from commercial where id = #{id}")
    void deleteCommercial(Integer id);

    @Select("select * from commercial where url = #{url}")
    Commercial getCommercialByUrl(String url);

    @Select("select * from commercial order by RAND() limit #{limit}")
    List<Commercial> getRandomCommercials(int limit);
}
