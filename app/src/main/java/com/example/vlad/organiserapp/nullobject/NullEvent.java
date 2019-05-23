package com.example.vlad.organiserapp.nullobject;

import java.util.Date;

public class NullEvent implements Event {

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public void setId(int id) {

    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public void setTitle(String title) {

    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    public void setDescription(String description) {

    }

    @Override
    public int getIsAlarmSet() {
        return 0;
    }

    @Override
    public void setIsAlarmSet(int isAlarmSet) {

    }

    @Override
    public Date getDate() {
        return null;
    }

    @Override
    public void setDate(Date date) {

    }

    @Override
    public boolean isNull() {
        return true;
    }
}
