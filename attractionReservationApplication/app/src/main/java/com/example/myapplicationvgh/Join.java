package com.example.myapplicationvgh;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.myapplicationvgh.dto.LoginResponse;
import com.example.myapplicationvgh.dto.ManagerLoginResponse;
import com.example.myapplicationvgh.dto.ManagerSignupResponse;
import com.example.myapplicationvgh.dto.Response;
import com.google.gson.Gson;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

public class Join extends Activity {

    String RocalString ="";

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join);

        Button check, back;
        EditText Edit_name, Edit_id, Edit_pw, Edit_ride;
        RadioButton user, maneger;
        RadioGroup Group;


        LinearLayout ride_check;

        //회원가입 할때 라디오 버튼
        user= (RadioButton) findViewById(R.id.radio_user);
        maneger= (RadioButton) findViewById(R.id.radio_manager);
        Group = (RadioGroup) findViewById(R.id.check_user);

        //아래의 완료 버튼
        check = (Button) findViewById(R.id.join_check);
        //아래의 뒤로가기 버튼
        back = (Button) findViewById(R.id.join_back);
        //이름 적는 칸
        Edit_name = (EditText) findViewById(R.id.Edit_name);
        //아이디 적는 칸
        Edit_id = (EditText) findViewById(R.id.Edit_id);
        //비번 적는 칸
        Edit_pw = (EditText) findViewById(R.id.Edit_pw);

        Edit_ride = (EditText) findViewById(R.id.join_EditText_ride);

        ride_check = (LinearLayout) findViewById(R.id.join_Liner_ride);


        maneger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ride_check.setVisibility(view.VISIBLE);
            }
        });
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ride_check.setVisibility(view.GONE);
            }
        });

        //입력을 누르면
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                //Edit 텍스트를 받아오는 부분
                String id = Edit_id.getText().toString();
                String name = Edit_name.getText().toString();
                String pw = Edit_pw.getText().toString();
                String ride = Edit_ride.getText().toString();

                ManagerSignupResponse response;

                if(name.equals("")) //이름에 아무것도 안적은 경우
                {
                    AlertDialog.Builder dlg =new AlertDialog.Builder(Join.this);
                    dlg.setTitle("이름을 입력하지 않았습니다.");
                    dlg.setMessage("아이디를 다시 입력해 주십시오");
                    dlg.setPositiveButton("확인", null);
                    dlg.show();
                }
                else if(id.equals(""))  //아이디에 아무것도 안적은 경우
                {
                    AlertDialog.Builder dlg =new AlertDialog.Builder(Join.this);
                    dlg.setTitle("아이디를 입력하지 않았습니다.");
                    dlg.setMessage("아이디를 다시 입력해 주십시오");
                    dlg.setPositiveButton("확인", null);
                    dlg.show();
                }
                else if(pw.equals(""))  //비밀번호를 아무것도 안적은 경우
                {
                    AlertDialog.Builder dlg =new AlertDialog.Builder(Join.this);
                    dlg.setTitle("비밀번호를 입력하지 않았습니다.");
                    dlg.setMessage("비밀번호를 다시 입력해 주십시오");
                    dlg.setPositiveButton("확인", null);
                    dlg.show();
                }
                else{
                    //데이터를 보내주는 부분
                    if(!user.isChecked())
                    {
                        SendDataToServer_Join_menager task = new SendDataToServer_Join_menager(name, id, pw, ride);
                        task.execute();
                        try {
                            String result = task.get();
                            RocalString =result;
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Gson gson = new Gson();
                        response = gson.fromJson(RocalString, ManagerSignupResponse.class);
                    }
                    else{
                        SendDataToServer_Join task = new SendDataToServer_Join(name, id, pw);
                        task.execute();
                        try {
                            String result = task.get();
                            RocalString =result;
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Gson gson = new Gson();
                        response = gson.fromJson(RocalString, ManagerSignupResponse.class);
                    }

                    System.out.println("true or false" + response.isSuccess());
                    System.out.println("message" + response.getMessage());
                    if(response.isSuccess())
                    {

                        Intent intent = new Intent(getApplicationContext(), login.class);
                        Toast.makeText(getApplicationContext(), response.getMessage(), Toast.LENGTH_SHORT).show();
                        new Timer().schedule(new TimerTask() {
                            @Override
                            public void run() {
                                startActivity(intent);
                            }
                        }, 2000);
                        finish();
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), response.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    
                    
                }

            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();

                Intent intent = new Intent(getApplicationContext(), login.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
