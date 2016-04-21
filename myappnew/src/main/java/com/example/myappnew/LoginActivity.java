package com.example.myappnew;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.protocol.ProtocolActivity;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends AppCompatActivity {

    private EditText userName;
    private EditText passwd;
    private TextView loginName;
    public String name;
    private TextView userRegister;//注册

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ImageView deleteUser = (ImageView) findViewById(R.id.username_delete_iv);
        ImageView deletePaswd = (ImageView) findViewById(R.id.passwd_delete_iv);
        userName = (EditText) findViewById(R.id.et_username);
        passwd = (EditText) findViewById(R.id.et_password);
        userRegister = ((TextView) findViewById(R.id.register));
        Button forgetPasswd = (Button) findViewById(R.id.forget_password);
        Button login = (Button) findViewById(R.id.user_login);
        CheckBox agree = (CheckBox) findViewById(R.id.agree);
        Button duty = (Button) findViewById(R.id.duty);
        loginName = (TextView) findViewById(R.id.login);
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
        userRegister.setOnClickListener(new View.OnClickListener() {//注册
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                finish();
            }
        });
        duty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,ProtocolActivity.class));
            }
        });



    }

    public void login (View view){
        name = userName.getText().toString();
        String passWord = passwd.getText().toString();
        if (name.equals("") || passWord.equals("")) {
            return;
        }
        
        getSharedPreferences("userinfo", MODE_PRIVATE).edit().putString("name", name).commit();
        BmobUser bu2 = new BmobUser();
        bu2.setUsername(name);
        bu2.setPassword(passWord);
        bu2.login(this, new SaveListener() {
            @Override
            public void onSuccess() {
                // TODO Auto-generated method stub
                LoginActivity.this.finish();
                Toast.makeText(LoginActivity.this, name + "登录成功", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                intent.putExtra("name", name);
//                loginName.setText(name);
//                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
//                startActivity(intent);
//                setResult(2,intent);
//                finish();

            }
            @Override
            public void onFailure(int code, String msg) {
                // TODO Auto-generated method stub
                Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();

            }
        });
    }

}
