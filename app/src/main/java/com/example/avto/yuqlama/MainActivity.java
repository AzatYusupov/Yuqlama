package com.example.avto.yuqlama;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.avto.yuqlama.Db.SqliteHelper;
import com.example.avto.yuqlama.Model.Course;
import com.example.avto.yuqlama.Model.Data;
import com.example.avto.yuqlama.Model.Group;
import com.example.avto.yuqlama.Model.Student;

import org.json.JSONException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private static ArrayList<Course>cources;
    private static ArrayList<Group>groups;
    private static ArrayList<Student>students;
    private static Data date;
    private static int idImage;
    public static int requestCodeNbGroup = 100;
    public static int requestCodePassword = 200;
    public static int requestCodePasswordUpgrade = 201;
    private static String groupName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        date = new Data(new Date());

//        SqliteDbHelper sqliteDbHelper = new SqliteDbHelper(this);
//        SQLiteDatabase db = sqliteDbHelper.getWritableDatabase();
//
//        Cursor cursor = db.query(SqliteConfig.getTableDoneGrups(), null, null, null, null, null, null);
//        System.out.println("Absent students : +++++++++++++++");
//        if (cursor.moveToFirst()) {
//            int idColumn = cursor.getColumnIndex("id");
//            int studentIdColumn = cursor.getColumnIndex("group_id");
//            int paraColumn = cursor.getColumnIndex("para");
//
//            do {
//                int id = cursor.getInt(idColumn);
//                int studentId = cursor.getInt(studentIdColumn);
//                int para = cursor.getInt(paraColumn);
//                String data = cursor.getString(cursor.getColumnIndex("data"));
//                System.out.println(id+" "+studentId+" "+para+" "+data);
//            }while (cursor.moveToNext());
//        }
//        else
//            System.out.println("There is no absent students");
//        sqliteDbHelper.close();
        initToolbar();
//        initFab();
        initList();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==requestCodeNbGroup) {
            if (resultCode == RESULT_OK) {
                ImageView imageView = (ImageView) findViewById(idImage);
                imageView.setImageResource(R.mipmap.ic_ok);
                Toast.makeText(getApplicationContext(), groupName + "-guruh yo'qlamasi muvafaqiyatli saqlandi", Toast.LENGTH_LONG).show();
            }
        }
        if (requestCode==requestCodePassword && resultCode==RESULT_OK) {
            String password = data.getExtras().getString("password");
            Toast.makeText(MainActivity.this, password+"  1", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(MainActivity.this, SuccessActivity.class);
            try {
                int resultUpdate = SqliteHelper.updateData(password, this);
                if (resultUpdate==1) {
                    intent.putExtra("ok", true);
                    intent.putExtra("text", "Yo'qlamalar serverga muvafaqiyatli yuklandi!");
                }
                else if (resultUpdate==2) {
                    intent.putExtra("ok", false);
                    intent.putExtra("text", "Parol hato.");
                }
                else {
                    intent.putExtra("ok", false);
                    intent.putExtra("text", "Server bilan bog'lanish yo'q.");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            startActivity(intent);
        }
        if (requestCode==requestCodePasswordUpgrade && resultCode==RESULT_OK) {
            String password = data.getExtras().getString("password");
            Toast.makeText(MainActivity.this, password+"  2", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(MainActivity.this, SuccessActivity.class);
            try {
                int resultUpgrade = SqliteHelper.upgradeData(password, this);
                if (resultUpgrade==1) {
                    intent.putExtra("ok", true);
                    intent.putExtra("text", "Barcha kurslar, guruhlar va talabalar haqidai ma'lumotlar muvafaqiyatli yangilandi!");
                }
                else if (resultUpgrade==2) {
                    intent.putExtra("ok", false);
                    intent.putExtra("text", "Parol hato.");
                }
                else {
                    intent.putExtra("ok", false);
                    intent.putExtra("text", "Server bilan bog'lanish yo'q.");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            startActivity(intent);
        }
    }

    private  void initList() {
        
        cources = SqliteHelper.getCourseList(this);
        groups = SqliteHelper.getGroupList(this);
        System.out.println(groups.size()+"  ...............");

        LayoutInflater inflater = getLayoutInflater();
        LinearLayout linLayout = (LinearLayout)(findViewById(R.id.lin_layout));

        for (final Course course : cources) {
            course.setOpen(false);

            View viewCource = inflater.inflate(R.layout.item_course, linLayout, false);
            TextView textCource = (TextView)viewCource.findViewById(R.id.cource_name);
            textCource.setText(course.getName());
            linLayout.addView(viewCource);

            final LinearLayout linLayoutGroup = new LinearLayout(this);
            LinearLayout.LayoutParams leftMarginParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            leftMarginParams.leftMargin = 60;


            for (final Group group : groups) {
                if (group.getParentCourse() != course.getId()) {
                    continue;
                }
                group.setOpen(false);

                View viewGroup = inflater.inflate(R.layout.item_group, linLayoutGroup, false);

                TextView textGroup = (TextView) viewGroup.findViewById(R.id.group_name);
                ImageView imageGroup = (ImageView) viewGroup.findViewById(R.id.group_image);
                imageGroup.setId(group.getId());

                if (SqliteHelper.isAlreadyDoneGroup(this, group.getId(), date.getPair()))
                    imageGroup.setImageResource(R.mipmap.ic_ok);
                textGroup.setText(group.getName());
                linLayoutGroup.addView(viewGroup, leftMarginParams);

                viewGroup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        idImage = group.getId();
                        Intent intent = new Intent(MainActivity.this, GroupListActivity.class);
                        intent.putExtra("groupId", group.getId());
                        intent.putExtra("groupName", group.getName());
                        groupName = group.getName();
                        startActivityForResult(intent, requestCodeNbGroup);
                    }
                });
            }

            viewCource.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    course.click();
                    if (course.isOpen())
                        linLayoutGroup.setVisibility(View.VISIBLE);
                    else
                        linLayoutGroup.setVisibility(View.GONE);
                }
            });

            linLayoutGroup.setOrientation(LinearLayout.VERTICAL);
            linLayoutGroup.setVisibility(View.GONE);
            linLayout.addView(linLayoutGroup);
            System.out.println();
        }

    }
    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_launcher);
        String para = date.getPair()+"-para";
        if (date.getPair()==-1)
            para = "Hozir dars emas";
        getSupportActionBar().setTitle("TATU UF. Yo'qlama. "+date.getDay()+"-"+date.getMonth()+". "+date.getWeek()+". "+date.getHours()+":"+date.getMinutes()+". "+para);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_update) {
            Intent intentPass = new Intent(MainActivity.this, EnterPass.class);
            startActivityForResult(intentPass, requestCodePassword);
            return true;
        }
        if (id == R.id.action_upgrade) {
            Intent intentPass = new Intent(MainActivity.this, EnterPass.class);
            startActivityForResult(intentPass, requestCodePasswordUpgrade);
            return true;

        }
        return super.onOptionsItemSelected(item);
    }
}
