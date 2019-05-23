package com.example.vlad.organiserapp;

import java.util.Date;


public class CustomEvent implements Event{

    private int id;
    private String title;
    private String description;
    private int isAlarmSet;
    // need to replace Date with Calendar
    private Date date;


    private CustomEvent(CustomEventBuilder customEventBuilder) {
        this.id = customEventBuilder.id;
        this.title = customEventBuilder.title;
        this.description = customEventBuilder.description;
        this.isAlarmSet = customEventBuilder.isAlarmSet;
        this.date = customEventBuilder.date;
    }

    @Override
    public int getId() {

        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int getIsAlarmSet() {
        return isAlarmSet;
    }

    @Override
    public void setIsAlarmSet(int isAlarmSet) {
        this.isAlarmSet = isAlarmSet;
    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean isNull() {
        return false;
    }

    @Override
    public String toString() {
        return "CustomEvent{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", isAlarmSet='" + isAlarmSet + '\'' +
                ", date=" + date +
                '}';
    }

    // Prototype Design Pattern
    @Override
    public CustomEvent clone() {
        return new CustomEvent
                .CustomEventBuilder()
                .setId(id)
                .setTitle(title)
                .setDescription(description)
                .setIsAlarmSet(isAlarmSet)
                .setDate(date)
                .build();
    }

    //Builder Design Pattern
    public static class CustomEventBuilder {
        private int id;
        private String title;
        private String description;
        private int isAlarmSet;
        private Date date;

        public CustomEventBuilder setId(int id) {
            this.id = id;
            return this;
        }

        public CustomEventBuilder setTitle(String title) {
            this.title = title;
            return this;
        }

        public CustomEventBuilder setDescription(String description) {
            this.description = description;
            return this;
        }

        public CustomEventBuilder setIsAlarmSet(int isAlarmSet) {
            this.isAlarmSet = isAlarmSet;
            return this;
        }

        public CustomEventBuilder setDate(Date date) {
            this.date = date;
            return this;
        }


        public CustomEvent build() {
            return new CustomEvent(this);
        }
    }


}
