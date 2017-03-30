package com.example.avto.yuqlama.Model;

/**
 * Created by Azat on 03.03.2017.
 */

public class Course {
    private String name;
    private int id;
    private boolean open;
    public Course(String name, int id) {
        this.id = id;
        this.name = name;
    }
    public Course(){}
    public void setOpen(boolean opened) {
        this.open = opened;
    }
    public boolean isOpen() {
        return this.open;
    }
    public void click() {
        open = !open;
    }
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
}
