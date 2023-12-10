package com.example.myapplicationvgh;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplicationvgh.dto.LoginResponse;
import com.example.myapplicationvgh.dto.ReservTableRespone;
import com.example.myapplicationvgh.dto.Response;
import com.example.myapplicationvgh.dto.WaitingResponse;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.util.Random;
import java.util.concurrent.ExecutionException;

public class Table extends AppCompatActivity {

    String user_id;
    String LocalString;

    private TextView rideNameTextView1, waitTimeTextView1;
    private TextView rideNameTextView2, waitTimeTextView2;
    private TextView rideNameTextView3, waitTimeTextView3;
    private TextView rideNameTextView5, waitTimeTextView5;
    private TextView rideNameTextView6, waitTimeTextView6;
    private TextView rideNameTextView7, waitTimeTextView7;
    private TextView rideNameTextView8, waitTimeTextView8;
    private TextView rideNameTextView9, waitTimeTextView9;
    private TextView rideNameTextView10, waitTimeTextView10;
    private TextView rideNameTextView11, waitTimeTextView11;
    private TextView rideNameTextView12, waitTimeTextView12;
    private TextView rideNameTextView13, waitTimeTextView13;
    private TextView rideNameTextView14, waitTimeTextView14;

    String wordValue;
    //int wordInt;
    //현재 예약중인지 확인하기 위한 값  true면 줄을 서고있는 중임
    boolean riding = false;
    int ride;
    int brfore_ride;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.table);

        //유저 id받아오는 부분
        Intent intent = getIntent();
        if(intent != null)
        {
            String op = intent.getStringExtra("user_id");
            System.out.println(op);

            user_id=op;
        }
        System.out.println(user_id);

        // 첫 번째 놀이기구
        rideNameTextView1 = findViewById(R.id.rideNameTextView1);
        Button reserveButton1 = findViewById(R.id.reserveButton1);
        Button cancelButton1 = findViewById(R.id.cancelButton1);

        // 두 번째 놀이기구
        rideNameTextView2 = findViewById(R.id.rideNameTextView2);
        Button reserveButton2 = findViewById(R.id.reserveButton2);
        Button cancelButton2 = findViewById(R.id.cancelButton2);

        // 세 번째 놀이기구
        rideNameTextView3 = findViewById(R.id.rideNameTextView3);
        Button reserveButton3 = findViewById(R.id.reserveButton3);
        Button cancelButton3 = findViewById(R.id.cancelButton3);



        // 네 번째 놀이기구
        rideNameTextView5 = findViewById(R.id.rideNameTextView5);
        Button reserveButton5 = findViewById(R.id.reserveButton5);
        Button cancelButton5 = findViewById(R.id.cancelButton5);

        // 네 번째 놀이기구
        rideNameTextView6 = findViewById(R.id.rideNameTextView6);
        Button reserveButton6 = findViewById(R.id.reserveButton6);
        Button cancelButton6 = findViewById(R.id.cancelButton6);

        // 네 번째 놀이기구
        rideNameTextView7 = findViewById(R.id.rideNameTextView7);
        Button reserveButton7 = findViewById(R.id.reserveButton7);
        Button cancelButton7 = findViewById(R.id.cancelButton7);

        // 네 번째 놀이기구
        rideNameTextView8 = findViewById(R.id.rideNameTextView8);
        Button reserveButton8 = findViewById(R.id.reserveButton8);
        Button cancelButton8 = findViewById(R.id.cancelButton8);

        // 네 번째 놀이기구
        rideNameTextView9 = findViewById(R.id.rideNameTextView9);
        Button reserveButton9 = findViewById(R.id.reserveButton9);
        Button cancelButton9 = findViewById(R.id.cancelButton9);

        // 네 번째 놀이기구
        rideNameTextView10 = findViewById(R.id.rideNameTextView10);
        Button reserveButton10 = findViewById(R.id.reserveButton10);
        Button cancelButton10 = findViewById(R.id.cancelButton10);

        // 네 번째 놀이기구
        rideNameTextView11 = findViewById(R.id.rideNameTextView11);
        Button reserveButton11 = findViewById(R.id.reserveButton11);
        Button cancelButton11 = findViewById(R.id.cancelButton11);

        // 네 번째 놀이기구
        rideNameTextView12 = findViewById(R.id.rideNameTextView12);
        Button reserveButton12 = findViewById(R.id.reserveButton12);
        Button cancelButton12 = findViewById(R.id.cancelButton12);

        // 네 번째 놀이기구
        rideNameTextView13 = findViewById(R.id.rideNameTextView13);
        Button reserveButton13 = findViewById(R.id.reserveButton13);
        Button cancelButton13 = findViewById(R.id.cancelButton13);

        SendDataToServer_Table task = new SendDataToServer_Table(user_id);
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
        ReservTableRespone waitingResponse[] = gson.fromJson(LocalString, ReservTableRespone[].class);

        for(int i=0;i<13;i++)
        {
            if(waitingResponse[i].isReservCheck() == true)
            {
                riding=true;
                ride=i+1;
                brfore_ride = i+1;
            }

        }


        //대기시간을 입력해주는 부분
        TextView TextName;
        for(int i=1;i<14;i++)
        {
            //대기시간
            int textViewId = getResources().getIdentifier("waitTimeTextView" + i, "id", getPackageName());
            TextName = findViewById(textViewId);
            TextName.setText(String.valueOf(waitingResponse[i-1].getWaitingTime()).toString());
        }
        //예약취소 버튼 글자 만들어 주는 부분(안봐도됨)
        for(int i=1;i<14;i++)
        {
            int textViewId = getResources().getIdentifier("cancelButton" + i, "id", getPackageName());
            TextName = findViewById(textViewId);
            TextName.setText("예약 취소");
        }



        //예약버튼 클릭 했을 때 예약되는 부분
        for(int i=1;i<14;i++)
        {
            final  int wordInt = i;
            int textViewId = getResources().getIdentifier("reserveButton" + wordInt, "id", getPackageName());
            TextName = findViewById(textViewId);
            System.out.println("ride = " + ride + "wordInt = " + wordInt);
            TextName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(riding)
                    {
                        System.out.println("ride = " + ride + "wordInt = " + wordInt);
                        Toast.makeText(getApplicationContext(), "예약이 완료되지 않았습니다. 다른 예약이 있는지 확인해 주세요", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        System.out.println("ride = " + ride + "wordInt = " + wordInt);
                        System.out.println("dsdsadasds");
                        if(brfore_ride != wordInt-1)
                        {
                            int textViewId = getResources().getIdentifier("checkText" + wordInt, "id", getPackageName());
                            TextView TextName = findViewById(textViewId);
                            TextName.setText("줄 서는 중....");
                            riding=true;
                            ride=wordInt;


                            //예약 했음을 알리기 위함///////////////////////////////////////////////////////////////////////////
                            int textViewID = getResources().getIdentifier("rideNameTextView" + wordInt, "id", getPackageName());
                            TextView textName = findViewById(textViewID);
                            String RideName = String.valueOf(textName.getText());
                            System.out.println("RideName = " + RideName);
                            SendDataToServer_reservation_Map task = new SendDataToServer_reservation_Map(user_id, RideName);
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
                            Response response = gson.fromJson(LocalString, Response.class);
                            Toast.makeText(getApplicationContext(), response.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(), "바로 전에 탔던 놀이기구 입니다. 다른 놀이기구를 이용한 후 이용이 가능합니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }

        for(int i=1;i<14;i++)
        {
            final  int wordInt = i;
            if(i==ride && riding == true)
            {
                int Textidcub = getResources().getIdentifier("checkText" + wordInt, "id", getPackageName());
                TextView Textcub = findViewById(Textidcub);
                Textcub.setText("줄 서는 중...");
            }
        }


        //취소버튼 클릭 했을 때 예약되는 부분
        for(int i=1;i<14;i++)
        {
            final  int wordInt = i;
            int textViewId = getResources().getIdentifier("cancelButton" + wordInt, "id", getPackageName());
            TextName = findViewById(textViewId);
            TextName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(riding&&ride==wordInt)
                    {
                        Toast.makeText(getApplicationContext(), "예약이 취소 되었습니다.", Toast.LENGTH_SHORT).show();

                        //예약을 취소했음을 알리기 위함
                        SendDataToServer_Table_cancellation task = new SendDataToServer_Table_cancellation(user_id);
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
                        Response response;
                        response = gson.fromJson(LocalString, Response.class);


                        riding =false;

                        int textViewId = getResources().getIdentifier("checkText" + wordInt, "id", getPackageName());
                        TextView TextName = findViewById(textViewId);
                        TextName.setText("");
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "대기중인 줄이 아닙니다.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        Button back = findViewById(R.id.backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Map.class);
                intent.putExtra("user_id", user_id);
                startActivity(intent);
                finish();
            }
        });

    }
}
