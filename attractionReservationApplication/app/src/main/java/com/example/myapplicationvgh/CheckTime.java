package com.example.myapplicationvgh;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.myapplicationvgh.dto.LoginResponse;
import com.example.myapplicationvgh.dto.WaitingResponse;
import com.google.gson.Gson;

import java.util.concurrent.ExecutionException;

public class CheckTime extends Activity {

    String WorldString;
    String id;

    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.checktime);

        TextView time, count, error;
        Button checktime_Button_Back;
        checktime_Button_Back = (Button) findViewById(R.id.checktime_Button_Back);
        time =(TextView) findViewById(R.id.checktime_TextView_showTime);
        count =(TextView) findViewById(R.id.checktime_TextView_showCount);
        error = (TextView) findViewById(R.id.checktime_TextView_error);

        Intent intent = getIntent();
        if(intent != null)
        {
            String op = intent.getStringExtra("user_id");
            id=op;
        }


        SendDataToServer_CheckTime task = new SendDataToServer_CheckTime(id);
        task.execute();
        try {
            String result = task.get();
            WorldString = result;
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();
        WaitingResponse waitingresponse = gson.fromJson(WorldString, WaitingResponse.class);
        if(waitingresponse.getWaitingTime() == -1)
        {
            error.setText("현재 대기중인 놀이기구가 없습니다.");
        }
        else
        {
            time.setText(String.valueOf(waitingresponse.getWaitingTime()));
            count.setText(String.valueOf(waitingresponse.getWaitingNumber()));
        }


        checktime_Button_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Map.class);
                intent.putExtra("user_id", id);
                startActivity(intent);
                finish();
            }
        });
    }
}
