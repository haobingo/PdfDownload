package com.example.service;

import com.example.dao.MajorTableRepository;
import com.example.entity.MajorTable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class MajorTableService {
    @Resource
    private MajorTableRepository majorTableRepository;

    //添加与更新
    public void save(MajorTable majorTable){
        String now = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        majorTable.setCreateTime(now);
        majorTableRepository.save(majorTable);
    }

    public void delete(Integer id) {
        majorTableRepository.deleteById(id);
    }

    public MajorTable findById(Integer id){
        return majorTableRepository.findById(id).orElse(null);
    }

    public List<MajorTable> findAll(){
        return majorTableRepository.findAll();
    }

    public List<MajorTable> findByMajor(String major) {
        return majorTableRepository.findByMajor(major);
    }

    public List findMajors() {
        return majorTableRepository.findMajors();
    }
}
