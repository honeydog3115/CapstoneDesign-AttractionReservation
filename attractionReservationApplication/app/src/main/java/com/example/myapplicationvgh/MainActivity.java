package com.example.myapplicationvgh;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplicationvgh.dto.Response;
import com.example.myapplicationvgh.dto.WaitingResponse;
import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.w3c.dom.Text;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {


    String userId;
    String name;
    String ride;
    Button QR;

    String LocalString ="";

    TextView Rides_text, manager_text, pass_text;




    //QR스캔 결과를 처리
    //resultCode는 성공 했는지 실패 했는지, data는 데이터
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        pass_text = (TextView) findViewById(R.id.pass);
        //스캔 결과를 처리
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                // 취소된 경우
            } else {
                // 스캔한 것을 문자열로 받아옴
                String scannedData = result.getContents();
                //QR찍고 동작

                SendDataToServer_Manager_userid task = new SendDataToServer_Manager_userid(scannedData, userId);
                task.execute();
                try {
                    String Result = task.get();
                    LocalString =Result;
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Response response;
                Gson gson = new Gson();
                response = gson.fromJson(LocalString, Response.class);

                pass_text.setText(response.getMessage());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Rides_text = (TextView) findViewById(R.id.Rides_text);
        manager_text = (TextView) findViewById(R.id.manager_text);
        pass_text = (TextView) findViewById(R.id.pass);
        Intent intent = getIntent();
        if(intent != null)
        {
            String op = intent.getStringExtra("user_id");
            userId = op;
            op = intent.getStringExtra("name");
            name = op;
            op = intent.getStringExtra("ride");
            ride= op;
            System.out.println("userId = " + userId +", name = " + name + ", ride = " + ride);
        }

        QR = (Button) findViewById(R.id.QR);

        
        //QR코드 클릭시
        QR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new IntentIntegrator(MainActivity.this).initiateScan();
            }
        });


        //담당자 이름 변경
        manager_text.setText(name);
        Rides_text.setText(ride);
        pass_text.setText(LocalString);
        


    }
}