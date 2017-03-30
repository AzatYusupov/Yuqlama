package com.example.avto.yuqlama.Db;

/**
 * Created by Azat on 11.03.2017.
 */

public class SqliteConfig {
    private static String dbName = "yuqlama";
    private static String tableCourse = "cources";
    private static String tableGroup = "groups";
    private static String tableStudent = "students";

    private static String tableAbsent = "absents";
    private static String tableDoneGrups = "doneGpoups";

    public static String getDbName() {
        return dbName;
    }
    public static String getTableCourse() {
        return tableCourse;
    }
    public static String getTableGroup() {
        return tableGroup;
    }
    public static String getTableStudent() {
        return tableStudent;
    }

    public static String getTableAbsent() {
        return tableAbsent;
    }
    public static String getTableDoneGrups() {
        return tableDoneGrups;
    }


}
