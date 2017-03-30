package com.example.avto.yuqlama.Server;

/**
 * Created by Azat on 19.03.2017.
 */

public class Config {
    private static String urlServer = "http://192.168.137.1:8085/";
    private static String pathGroupList = "group/list/";
    private static String pathStudentList = "student/list/";
    private static String pathCouurseList = "course/list/";
    private static String pathNbSend = "nb/send/";
    private static String pathNbGroupSend = "nbGroup/send/";
    public static String getUrlGroupList() {
        return urlServer + pathGroupList;
    }
    public static String getUrlStudentList() {
        return urlServer + pathStudentList;
    }
    public static String getUrlCourseList() {
        return urlServer + pathCouurseList;
    }
    public static String getUrlNbSend() {
        return urlServer + pathNbSend;
    }
    public static String getUrlNbGroupSend() {
        return urlServer + pathNbGroupSend;
    }
}
