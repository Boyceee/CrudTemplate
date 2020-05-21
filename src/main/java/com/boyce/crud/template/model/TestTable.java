package com.boyce.crud.template.model;

import com.boyce.crud.template.annotation.Column;
import com.boyce.crud.template.annotation.Id;
import com.boyce.crud.template.annotation.Table;

import java.util.Date;

/**
 * @author Boyce
 * @version V1.0
 * @date 2020/4/13 18:35
 */
@Table(value = "test1")
public class TestTable {
    @Id
    @Column(value = "id")
    private String testId;
    private String name;
    @Column(value = "save_time")
    private Date saveTime;
    @Column(value = "is_ok")
    private int isOk;

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

    public int getIsOk() {
        return isOk;
    }

    public void setIsOk(int isOk) {
        this.isOk = isOk;
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
