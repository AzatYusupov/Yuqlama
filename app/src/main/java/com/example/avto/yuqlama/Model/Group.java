package com.example.avto.yuqlama.Model;

/**
 * Created by Azat on 03.03.2017.
 */

public class Group {
    private String name;
    private int id;
    private int parentCourse;
    private boolean open;
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return this.id;
    }
    public void  click() {
        open = !open;
    }
    public boolean isOpen() {
        return this.open;
    }
    public void setOpen(boolean opened) {
        this.open = opened;
    }
    public void setParentCourse(int parentCourse) {
        this.parentCourse = parentCourse;
    }
    public int getParentCourse() {
        return this.parentCourse;
    }
    public Group(String name, int id, int parentCourse) {
        this.name = name;
        this.id = id;
        this.parentCourse = parentCourse;
    }
    public Group(){}
}
