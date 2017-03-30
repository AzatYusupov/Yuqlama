package com.example.avto.yuqlama;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EnterPass extends AppCompatActivity {

    private static EditText passwordText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_pass);
        passwordText = (EditText) findViewById(R.id.password_text);

        Button btnSend = (Button) findViewById(R.id.btn_send_pass);
        Button btnCancle = (Button)findViewById(R.id.btn_cancel);
        btnSend.setOnClickListener(ButtunClickListenet);
        btnCancle.setOnClickListener(ButtunClickListenet);
    }
    private View.OnClickListener ButtunClickListenet = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            setResult(RESULT_CANCELED);
            if (v.getId()==R.id.btn_cancel) {
                finish();
            }
            else {
                String password = passwordText.getText().toString();
                if (password != null) {
                    password = password.trim();
                    if (password.trim().length() != 0) {
                        Intent intent = new Intent();
                        intent.putExtra("password", password);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }

            }
        }
    };
}
