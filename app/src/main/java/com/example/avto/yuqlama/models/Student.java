package com.example.avto.yuqlama.Model;

import android.support.annotation.NonNull;

/**
 * Created by Azat on 03.03.2017.
 */

public class Student implements Comparable<Student>{
    private String name;
    private int id;
    private int parentGroup;
    private boolean absent;
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }
    public boolean isAbsent() {
        return absent;
    }
    public void setAbsent(boolean absent) {
        this.absent = absent;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return this.id;
    }
    public void setParentGroup(int parentGroup) {
        this.parentGroup = parentGroup;
    }
    public int getParentGroup() {
        return this.parentGroup;
    }

    public Student(String name, int id, int parentGroup, boolean absent) {
        this.name = name;
        this.id = id;
        this.parentGroup = parentGroup;
        this.absent = absent;
    }
    public Student(){}

    @Override
    public int compareTo(@NonNull Student o) {
        return this.name.compareTo(o.name);
    }
}
