package com.mezo.chaos.nyx.excel.mapper;

import com.mezo.chaos.nyx.excel.domain.Student;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface StudentMapper {
    /**
     * 查询全部
     * @return
     */
    @Select("select name,classes,score,time from student")
    List<Student> selectAll();

    /**
     * 插入
     * @param student
     */
    @Insert("insert into(name,classes,score,time) value (#{name},#{classes},#{score},#{time})")
    void insert(Student student);
}
