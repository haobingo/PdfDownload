package com.example.dao;

import com.example.entity.MajorTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MajorTableRepository extends JpaRepository<MajorTable,Integer> {

    //利用原生的 SQL 实现姓名的模糊查询
    @Query(value=" SELECT * FROM major_table WHERE major_table.major LIKE %:major% ",nativeQuery=true)
    List<MajorTable> findByMajor(@Param("major") String major);

    @Query(value="SELECT DISTINCT major FROM major_table",nativeQuery = true)
    List findMajors();
}
