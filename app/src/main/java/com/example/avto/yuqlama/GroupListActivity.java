package com.example.avto.yuqlama;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.avto.yuqlama.Db.SqliteConfig;
import com.example.avto.yuqlama.Db.SqliteDbHelper;
import com.example.avto.yuqlama.Db.SqliteHelper;
import com.example.avto.yuqlama.Model.Data;
import com.example.avto.yuqlama.Model.Student;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class GroupListActivity extends AppCompatActivity {

    private static String groupName;
    private static ArrayList<Student>students;
    private static Data date;
    private boolean[]absents;
    private static int groupId;
    private boolean alreadyDoneGroup = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_list);

        groupId = getIntent().getExtras().getInt("groupId");
        groupName = getIntent().getExtras().getString("groupName");
        students = SqliteHelper.getStudentList(this, groupId);
        Collections.sort(students);
        date = new Data(new Date());

        if (date.getPair()==-1) {
            alreadyDoneGroup = true;
        }
        else if (SqliteHelper.isAlreadyDoneGroup(this, groupId, date.getPair())) {
            alreadyDoneGroup = true;
            Toast.makeText(GroupListActivity.this, groupName+"-guruh allaqachon yo'qlama qilingan", Toast.LENGTH_LONG).show();
        }
        initToolbar();


        String[]studentNames = new String[students.size()];
        for (int i = 0; i < studentNames.length; i++) {
            String number = (i+1)+"";
            if (i+1 <= 9)
                number = " "+(i+1);
            studentNames[i] = number+". "+students.get(i).getName();
        }
        LayoutInflater inflater = getLayoutInflater();
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.list_group);
        absents = new boolean[students.size()];
        ArrayList<Integer> absentStudents = SqliteHelper.absentsInGroup(this, groupId, date.getPair());
        System.out.println(absentStudents+"  *******************");
        for (int i = 0; i < absents.length; i++) {
            if (Collections.binarySearch(absentStudents, students.get(i).getId()) >= 0)
                absents[i] = true;
        }
        for (int i = 0; i < students.size(); i++) {
            View viewStudent = inflater.inflate(R.layout.item_student, null, false);
            TextView nameStudent = (TextView) viewStudent.findViewById(R.id.student_name);
            if (alreadyDoneGroup)
                nameStudent.setTextColor(Color.GRAY);
            nameStudent.setText((i+1)+". "+students.get(i).getName());
            final ImageView imageStudent = (ImageView) viewStudent.findViewById(R.id.student_absent);
            if (absents[i])
                imageStudent.setImageResource(R.mipmap.ic_no);
            else
                imageStudent.setImageResource(R.mipmap.ic_ok);
            final int finalI = i;
            viewStudent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    absents[finalI] = !absents[finalI];
                    if (absents[finalI])
                        imageStudent.setImageResource(R.mipmap.ic_no);
                    else
                        imageStudent.setImageResource(R.mipmap.ic_ok);
                }
            });
            if (alreadyDoneGroup)
                viewStudent.setEnabled(false);
            linearLayout.addView(viewStudent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_group_list, menu);
        return true;
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        Toast.makeText(GroupListActivity.this, "Saving", Toast.LENGTH_LONG).show();
//        return super.onOptionsItemSelected(item);
//    }

    private void initToolbar() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_group);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_action_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(GroupListActivity.this, MainActivity.class));
                finish();
            }
        });
        final Button saveButtom = new Button(getApplicationContext());
        saveButtom.setTextSize(26);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.rightMargin = 20;
        saveButtom.setLayoutParams(params);

        boolean save = false;
        if (!alreadyDoneGroup) {
            saveButtom.setText("SAQLASH");
            save = true;
            saveButtom.setBackgroundColor(Color.RED);
        }
        else {
            saveButtom.setText("Ortga");
            saveButtom.setBackgroundColor(Color.GREEN);
        }
//        saveButtom.setClickable(true);
//        TypedValue outValue = new TypedValue();
//        contetx.getTheme().resolveAttribute(android.R.attr.selectableItemBackground, outValue, true);

        final boolean finalSave = save;
        saveButtom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!alreadyDoneGroup) {
                    ArrayList<Integer> absentStudents = new ArrayList<Integer>();
                    for (int i = 0; i < students.size(); i++) {
                        if (absents[i]) {
                            absentStudents.add(students.get(i).getId());
                        }
                    }
                    SqliteHelper.doAbsentStudents(getApplicationContext(), absentStudents, groupId, date.getPair());
                    SqliteHelper.doDoneGroup(getApplicationContext(), groupId, date.getPair());
//
//
                }
                if (finalSave) {
                    setResult(RESULT_OK);
                }
                else
                    setResult(RESULT_CANCELED);
                finish();
            }
        });
        toolbar.addView(saveButtom, new Toolbar.LayoutParams(Gravity.RIGHT));

        String para = date.getPair()+"-para";
        if (date.getPair()==-1)
            para = "Hozir dars emas";
        getSupportActionBar().setTitle(groupName+" guruh. "+date.getDay()+"-"+date.getMonth()+". "+date.getWeek()+". "+date.getHours()+":"+date.getMinutes()+". "+para);
    }
}
