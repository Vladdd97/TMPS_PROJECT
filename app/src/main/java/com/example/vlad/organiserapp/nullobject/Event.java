package com.example.vlad.organiserapp.nullobject;

import java.util.Date;

public interface Event {

    public int getId();
    public void setId(int id);

    public String getTitle();

    public void setTitle(String title);

    public String getDescription();

    public void setDescription(String description);

    public int getIsAlarmSet();

    public void setIsAlarmSet(int isAlarmSet);

    public Date getDate();

    public void setDate(Date date);

    public boolean isNull();
}
