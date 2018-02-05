package com.example.avto.yuqlama.Model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by Azat on 03.03.2017.
 */

public class Group extends RealmObject{

    @Required
    @PrimaryKey
    int id;

    @Required
    String name;

    @Required
    int courseId;

    boolean open;

    public Group(int id, String name, int courseId) {
        this.name = name;
        this.id = id;
        this.courseId = courseId;
    }
    public Group(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }
}
