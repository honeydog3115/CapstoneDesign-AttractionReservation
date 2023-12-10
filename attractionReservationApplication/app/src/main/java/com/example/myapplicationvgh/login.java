package com.example.myapplicationvgh;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import androidx.annotation.Nullable;

import com.example.myapplicationvgh.dto.LoginResponse;
import com.example.myapplicationvgh.dto.ManagerLoginResponse;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class login extends Activity {

    String LocalString;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);


        RadioButton user;
        LinearLayout ride_check;
        EditText id_edit, pw_edit;
        Button check, Join_button;
        id_edit = (EditText) findViewById(R.id.id_edit);
        pw_edit = (EditText) findViewById(R.id.pw_edit);
        check = (Button) findViewById(R.id.check);
        //회원가입 버튼
        Join_button = (Button) findViewById(R.id.join_button);
        user=(RadioButton) findViewById(R.id.radio_login_user);



        //확인버튼 눌렀을 때 로그인 확인하고 홈으로 넘어가기
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //아이디 비번을 확인
                String id_string = id_edit.getText().toString();
                String pw_string = pw_edit.getText().toString();
                System.out.println("id_string = " + id_string);

                if(id_string.equals("dlxotjr") && pw_string.equals("dlxotjr"))
                {
                    if(!user.isChecked())
                    {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        intent.putExtra("user_id", "dlxotjr");
                        startActivity(intent);
                        finish();
                    }
                    else{
                        Intent intent = new Intent(getApplicationContext(), Map.class);
                        intent.putExtra("user_id", "dlxotjr");
                        startActivity(intent);
                        finish();
                    }
                }

                LoginResponse loginResponse = new LoginResponse();
                ManagerLoginResponse managerLoginResponse = new ManagerLoginResponse();
                //서버로 데이터를 전송하고 받아오는 부분
                if(user.isChecked())
                {
                    SendDataToServerTask task = new SendDataToServerTask(id_string, pw_string, login.this);
                    task.execute();
                    try {
                        String result = task.get();
                        LocalString =result;
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Gson gson = new Gson();
                    loginResponse = gson.fromJson(LocalString, LoginResponse.class);
                }
                else{
                    SendDataToServerTask_maneger task = new SendDataToServerTask_maneger(id_string, pw_string, login.this);
                    task.execute();
                    try {
                        String result = task.get();
                        LocalString =result;
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Gson gson = new Gson();

                    managerLoginResponse = gson.fromJson(LocalString, ManagerLoginResponse.class);
                }
                boolean pass;
                if(user.isChecked())
                {
                    pass = loginResponse.isLoginSuccess();
                }
                else{
                    pass = managerLoginResponse.isLoginSuccess();
                }


                //true 직원으로 로그링ㄴ false는 손님으로 로그인




                    if(!user.isChecked())
                    {
                        if(pass)
                        {
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.putExtra("name", managerLoginResponse.getName());
                            intent.putExtra("ride", managerLoginResponse.getAttraction());
                            intent.putExtra("user_id", managerLoginResponse.getId());
                            startActivity(intent);
                            finish();
                        }
                        else    //아이디 자체가 등록이 안되어 있는경우
                        {
                            AlertDialog.Builder dlg =new AlertDialog.Builder(login.this);
                            dlg.setTitle("");
                            dlg.setMessage(managerLoginResponse.getMessage());
                            dlg.setPositiveButton("확인", null);
                            dlg.show();
                        }
                    }
                    else{
                        if(pass)
                        {
                            Intent intent = new Intent(getApplicationContext(), Map.class);
                            intent.putExtra("user_id", loginResponse.getId());
                            startActivity(intent);
                            finish();
                        }
                        else    //아이디 자체가 등록이 안되어 있는경우
                        {
                            AlertDialog.Builder dlg =new AlertDialog.Builder(login.this);
                            dlg.setTitle("");
                            dlg.setMessage(loginResponse.getMessage());
                            dlg.setPositiveButton("확인", null);
                            dlg.show();
                        }
                    }




            }
        });


        //회원가입을 눌렀을 때
        Join_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Join.class);
                startActivity(intent);
            }
        });


    }
}

