package com.example.myappnew;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

public class RegisterActivity extends AppCompatActivity {

    private EditText userName;
    private EditText passwd;
    private EditText againPasswd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ImageView deleteUser = (ImageView) findViewById(R.id.username_delete_iv);
        ImageView deletePaswd = (ImageView) findViewById(R.id.passwd_delete_iv);
        ImageView againDeletePaswd = (ImageView) findViewById(R.id.again_passwd_delete_iv);
        userName = (EditText) findViewById(R.id.et_username);
        passwd = (EditText) findViewById(R.id.et_password);
        againPasswd = (EditText) findViewById(R.id.et_againpassword);
        Button forgetPasswd = (Button) findViewById(R.id.forget_password);
        Button login = (Button) findViewById(R.id.user_login);
        CheckBox agree = (CheckBox) findViewById(R.id.agree);
        Button duty = (Button) findViewById(R.id.duty);
        deleteUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName.setText("");
            }
        });
        deletePaswd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                passwd.setText("");
            }
        });
        againDeletePaswd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                againPasswd.setText("");
            }
        });

    }

    public void register(View view) {
        String name = userName.getText().toString();
        String passWord = passwd.getText().toString();
        String againPassword = againPasswd.getText().toString();
        if (name.equals("") || passWord.equals("") || againPassword.equals("")) {
            return;
        }
        BmobUser bu = new BmobUser();
        bu.setUsername(name);
        bu.setPassword(passWord);
        bu.setPassword(againPassword);
//        bu.setEmail("sendi@163.com");
//注意：不能用save方法进行注册
        bu.signUp(this, new SaveListener() {
            @Override
            public void onSuccess() {
                // TODO Auto-generated method stub
                Toast.makeText(RegisterActivity.this, "注册成功,立马进入登录页面", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
//                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
                startActivity(intent);
                finish();//销毁这个注册页，让登录页登录成功后去往主页面
            }
            @Override
            public void onFailure(int code, String msg) {
                // TODO Auto-generated method stub
                Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
