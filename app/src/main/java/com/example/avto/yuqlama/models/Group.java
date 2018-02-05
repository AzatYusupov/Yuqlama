package com.example.avto.yuqlama.models;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by Azat on 03.03.2017.
 */

public class Group extends RealmObject{

    @PrimaryKey
    int id;

    @Required
    String name;

    int courseId;

    boolean open;

//    RealmList<Student> students;

    public Group(int id, String name, int courseId) {
        this.id = id;
        this.name = name;
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
