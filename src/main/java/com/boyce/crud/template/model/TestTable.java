package com.boyce.crud.template.model;

import com.boyce.crud.template.annotation.Column;
import com.boyce.crud.template.annotation.Table;

import java.util.Date;

/**
 * @author Boyce
 * @version V1.0
 * @date 2020/4/13 18:35
 */
@Table(value = "test1")
public class TestTable {
    private String id;
    private String name;
    @Column(value = "save_time")
    private Date saveTime;
    @Column(value = "is_ok")
    private int isOk;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getSaveTime() {
        return saveTime;
    }

    public void setSaveTime(Date saveTime) {
        this.saveTime = saveTime;
    }
}
