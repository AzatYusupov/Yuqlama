package com.example.avto.yuqlama;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class SuccessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        boolean ok = getIntent().getExtras().getBoolean("ok");
        String text = getIntent().getExtras().getString("text");

        TextView textSuccess = (TextView) findViewById(R.id.text_success);
        textSuccess.setText(text);
        if (ok)
            textSuccess.setTextColor(Color.GREEN);
        else
            textSuccess.setTextColor(Color.RED);
        ((Button)findViewById(R.id.btn_success)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SuccessActivity.this, MainActivity.class));
                finish();
            }
        });
    }
}
