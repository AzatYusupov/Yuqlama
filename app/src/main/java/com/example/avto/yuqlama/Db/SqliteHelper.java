package com.example.avto.yuqlama.Db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Surface;
import android.view.View;
import android.widget.Toast;

import com.example.avto.yuqlama.Model.Course;
import com.example.avto.yuqlama.Model.Data;
import com.example.avto.yuqlama.Model.Group;
import com.example.avto.yuqlama.Model.Student;
import com.example.avto.yuqlama.Server.Config;
import com.example.avto.yuqlama.Server.HttpHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.logging.Handler;

/**
 * Created by Azat on 11.03.2017.
 */

public class SqliteHelper {

    public static boolean isAlreadyDoneGroup(Context context, int groupId, int para) {
        SqliteDbHelper dbHelper = new SqliteDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selection = "group_id=? AND para=?";
        String[]selectionArgs = {groupId+"", para+""};
        Cursor c = db.query(SqliteConfig.getTableDoneGrups(), null, selection, selectionArgs, null, null, null);
        boolean exist = c.moveToFirst();
        db.close();
        dbHelper.close();
        return exist;
    }
    public static void doAbsentStudents(Context context, ArrayList<Integer>absentStudents, int groupId, int para) {
        SqliteDbHelper helper = new SqliteDbHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        for (int studentId : absentStudents) {
            ContentValues cv = new ContentValues();
            cv.put("student_id", studentId);
            cv.put("group_id", groupId);
            cv.put("para", para);
            cv.put("data", getStringDateToDatabase());
            db.insert(SqliteConfig.getTableAbsent(), null, cv);
        }
        db.close();
        helper.close();
    }
    public static void doDoneGroup(Context context, int groupId, int para) {
        SqliteDbHelper helper = new SqliteDbHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("group_id", groupId);
        cv.put("para", para);
        cv.put("data", getStringDateToDatabase());
        db.insert(SqliteConfig.getTableDoneGrups(), null, cv);
        db.close();
        helper.close();
    }
    public static ArrayList<Integer> absentsInGroup(Context context, int groupId, int para) {
        ArrayList<Integer> result = new ArrayList<>();
        SqliteDbHelper helper = new SqliteDbHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        String selection = "group_id=? AND para=? AND data=?";
        String[]selectionArgs = {groupId+"", para+"", getStringDateToDatabase()};
        String[]columns = {"student_id"};
        Cursor c = db.query(SqliteConfig.getTableAbsent(), columns, selection, selectionArgs, null, null, null);
        if (c.moveToFirst()) {
            do {
                result.add(c.getInt(c.getColumnIndex("student_id")));
            }while (c.moveToNext());
        }
        Collections.sort(result);
        db.close();
        helper.close();
        return result;
    }
    public static String getStringDateToDatabase() {
        Date date = new Date();
        String res = date.getYear()+"-";
        if (date.getMonth()+1 < 10)
            res += "0";
        res += (date.getMonth()+1)+"-";
        if (date.getDate() < 10)
            res += "0";
        res += date.getDate();
        return res;
    }

    public static int upgradeData(String password, Context context) throws JSONException, IOException {
        int updateResult = updateData(password, context);
        if (updateResult != 1)
            return updateResult;
        SqliteDbHelper helper = new SqliteDbHelper(context);
        SQLiteDatabase db = helper.getWritableDatabase();

        db.delete(SqliteConfig.getTableCourse(), null, null);
        db.delete(SqliteConfig.getTableGroup(), null, null);
        db.delete(SqliteConfig.getTableStudent(), null, null);
        db.delete(SqliteConfig.getTableAbsent(), null, null);
        db.delete(SqliteConfig.getTableDoneGrups(), null, null);


        HttpHandler handler = new HttpHandler();
        String jSonResult;


        ArrayList<Course>cources = new ArrayList<>();
        jSonResult = handler.doGetQuery(Config.getUrlCourseList());
        System.out.println(Config.getUrlCourseList()+"  ++++++++++++++++++");

        if (jSonResult==null) {
            System.out.println("NUUUUUUUUUUUUUUUUUUUUUUUUL");
            return 0;
        }
        JSONArray arrayCourse = new JSONArray(jSonResult);
        for (int i = 0; i < arrayCourse.length(); i++) {
            Course course = new Course();
            JSONObject obj = arrayCourse.getJSONObject(i);
            course.setId(obj.getInt("id"));
            course.setName(obj.getString("name"));
            cources.add(course);
        }

        ArrayList<Group>groups = new ArrayList<>();
        jSonResult = handler.doGetQuery(Config.getUrlGroupList());
        if (jSonResult==null)
            return 0;
        JSONArray arrayGroup = new JSONArray(jSonResult);
        for (int i = 0; i < arrayGroup.length(); i++) {
            JSONObject obj = arrayGroup.getJSONObject(i);
            Group group = new Group();
            group.setId(obj.getInt("id"));
            group.setName(obj.getString("name"));
            group.setParentCourse(obj.getInt("parentCourse"));
            groups.add(group);
        }


        ArrayList<Student>students = new ArrayList<>();
        jSonResult = handler.doGetQuery(Config.getUrlStudentList());
        if (jSonResult==null)
            return 0;
        JSONArray arrayStudent = new JSONArray(jSonResult);
        for (int i = 0; i < arrayStudent.length(); i++) {
            JSONObject obj = arrayStudent.getJSONObject(i);
            Student student = new Student();
            student.setId(obj.getInt("id"));
            student.setName(obj.getString("name"));
            student.setParentGroup(obj.getInt("parentGroup"));
            student.setAbsent(false);
            students.add(student);
        }

        for (Course course : cources) {
            ContentValues cv = new ContentValues();
            cv.put("id", course.getId());
            cv.put("name", course.getName());
            db.insert(SqliteConfig.getTableCourse(), null, cv);
        }

        for (Group group : groups) {
            ContentValues cv = new ContentValues();
            cv.put("id", group.getId());
            cv.put("name", group.getName());
            cv.put("course_id", group.getParentCourse());
            db.insert(SqliteConfig.getTableGroup(), null, cv);
        }

        for (Student student : students) {
            ContentValues cv = new ContentValues();
            cv.put("id", student.getId());
            cv.put("name", student.getName());
            cv.put("group_id", student.getParentGroup());
            db.insert(SqliteConfig.getTableStudent(), null, cv);
        }
        db.close();
        helper.close();
        return 1;
    }
    public static int updateData(String password, Context context) throws JSONException, IOException {

        HttpHandler handler = new HttpHandler();

        SqliteDbHelper helper = new SqliteDbHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.query(SqliteConfig.getTableAbsent(), null, null, null, null, null, null);
        System.out.println("NB lar:");
        JSONArray jsonArray = new JSONArray();
        int i = 0;
        if (c.moveToFirst()) {
            do{
                int studentId = c.getInt(c.getColumnIndex("student_id"));
                int groupId = c.getInt(c.getColumnIndex("group_id"));
                String data = c.getString(c.getColumnIndex("data"));
                int para = c.getInt(c.getColumnIndex("para"));
//                System.out.println(studentId+" "+groupId+" "+data+" "+para);
                JSONObject obj = new JSONObject();
                obj.put("studentId", studentId);
                obj.put("groupId", groupId);
                obj.put("data", data);
                obj.put("para", para);
                jsonArray.put(obj);
            }while (c.moveToNext());
        }
        c.close();
        int resultSendTServer = handler.postNBQuery(password, jsonArray);
        if (resultSendTServer!=1) {
            return resultSendTServer;
        }
        System.out.println("++++++++++++++++++");
        c = db.query(SqliteConfig.getTableDoneGrups(), null, null, null, null, null, null);
        System.out.println("Yo'qlama qilingan guruhlar: ");
        jsonArray = new JSONArray();
        if (c.moveToFirst()) {
            do {
                int groupId = c.getInt(c.getColumnIndex("group_id"));
                String data = c.getString(c.getColumnIndex("data"));
                int para = c.getInt(c.getColumnIndex("para"));
                JSONObject obj = new JSONObject();
                obj.put("groupId", groupId);
                obj.put("data", data);
                obj.put("para", para);
                jsonArray.put(obj);
            }while (c.moveToNext());
        }
        int res = handler.postNBGroupQuery(password, jsonArray);
        if (res != 1)
            return res;
        db.delete(SqliteConfig.getTableAbsent(), null, null);
        db.delete(SqliteConfig.getTableDoneGrups(), null, null);
        db.close();
        helper.close();
        return res;
    }
    public static ArrayList<Course> getCourseList(Context context) {
        ArrayList<Course> courses = new ArrayList<>();
        SqliteDbHelper helper = new SqliteDbHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor c = db.query(SqliteConfig.getTableCourse(), null, null, null, null, null, null);
        if (c.moveToFirst()) {
            do {
                int id = c.getInt(c.getColumnIndex("id"));
                String name = c.getString(c.getColumnIndex("name"));
                courses.add(new Course(name, id));
            }while (c.moveToNext());
        }
        c.close();
        db.close();
        helper.close();
        return courses;
    }
    public static ArrayList<Group> getGroupList(Context context) {
        ArrayList<Group> groups = new ArrayList<>();
        SqliteDbHelper helper = new SqliteDbHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.query(SqliteConfig.getTableGroup(), null, null, null, null, null, null);
        if (c.moveToFirst()) {
            do {
                int id = c.getInt(c.getColumnIndex("id"));
                String name = c.getString(c.getColumnIndex("name"));
                int courseId = c.getInt(c.getColumnIndex("course_id"));
                groups.add(new Group(name, id, courseId));
            }while (c.moveToNext());
        }

        db.close();
        helper.close();
        return groups;
    }
    public static ArrayList<Student> getStudentList(Context context, int groupId) {
        ArrayList<Student> students = new ArrayList<>();
        SqliteDbHelper helper = new SqliteDbHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();
        String selection = "group_id=?";
        String[]selectionArgs = {groupId+""};
        Cursor c = db.query(SqliteConfig.getTableStudent(), null, selection, selectionArgs, null, null, null);
        if (c.moveToFirst()) {
            do {
                int id = c.getInt(c.getColumnIndex("id"));
                String name = c.getString(c.getColumnIndex("name"));
                students.add(new Student(name, id, groupId, false));
            }while (c.moveToNext());
        }
        db.close();
        helper.close();
        return students;
    }
}
