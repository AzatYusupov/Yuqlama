package com.example.avto.yuqlama.Db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.avto.yuqlama.Model.Student;

/**
 * Created by Azat on 10.03.2017.
 */

public class SqliteDbHelper extends SQLiteOpenHelper{
    public SqliteDbHelper(Context context) {
        super(context, SqliteConfig.getDbName(), null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+SqliteConfig.getTableCourse()+" (id integer primary key, name varchar(255));");
        db.execSQL("CREATE TABLE "+SqliteConfig.getTableGroup()+" (id integer primary key, name varchar(255), course_id integer);");
        db.execSQL("CREATE TABLE "+SqliteConfig.getTableStudent()+" (id integer primary key, name varchar(255), group_id integer);");

        db.execSQL("CREATE TABLE "+SqliteConfig.getTableAbsent()+" (id integer primary key autoincrement, student_id integer, group_id integer, data varchar(20), para integer);");
        db.execSQL("CREATE TABLE "+SqliteConfig.getTableDoneGrups()+" (id integer primary key autoincrement, group_id integer, data varchar(20), para integer);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
