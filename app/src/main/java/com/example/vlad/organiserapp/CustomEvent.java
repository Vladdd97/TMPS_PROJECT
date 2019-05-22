package com.example.vlad.organiserapp;

import java.util.Date;


public class CustomEvent {

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

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIsAlarmSet() {
        return isAlarmSet;
    }

    public void setIsAlarmSet(int isAlarmSet) {
        this.isAlarmSet = isAlarmSet;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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




    //Builder Class
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
