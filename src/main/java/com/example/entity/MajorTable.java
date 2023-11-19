package com.example.entity;

import javax.persistence.*;
import java.time.Year;

@Entity
@Table(name="major_table")
public class MajorTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String major;
    @Column(name="major_version")
    private String majorVersion;
    @Column(name="is_load")
    private int isLoad;


    @Column(name = "create_time")
    private String createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getMajorVersion() {
        return majorVersion;
    }

    public void setMajorVersion(String majorVersion) {
        this.majorVersion = majorVersion;
    }

    public int getIsLoad() {
        return isLoad;
    }

    public void setIsLoad(int isLoad) {
        this.isLoad = isLoad;
    }


    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
