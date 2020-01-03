package com.example.myapplication.Model;

public class Events {
    public String date, dateevent, description, location, time, timeevent, image, evid, name;

    public Events() {

    }

    public Events(String date, String dateevent, String description, String location, String time, String timeevent, String image, String evid, String name) {
        this.date = date;
        this.dateevent = dateevent;
        this.description = description;
        this.location = location;
        this.time = time;
        this.timeevent = timeevent;
        this.image = image;
        this.evid = evid;
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDateevent() {
        return dateevent;
    }

    public void setDateevent(String dateevent) {
        this.dateevent = dateevent;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTimeevent() {
        return timeevent;
    }

    public void setTimeevent(String timeevent) {
        this.timeevent = timeevent;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEvid() {
        return evid;
    }

    public void setEvid(String evid) {
        this.evid = evid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
