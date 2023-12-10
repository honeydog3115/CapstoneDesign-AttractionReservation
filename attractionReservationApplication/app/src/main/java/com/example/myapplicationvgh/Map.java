package com.example.myapplicationvgh;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.myapplicationvgh.dto.LoginResponse;
import com.example.myapplicationvgh.dto.Response;
import com.example.myapplicationvgh.dto.WaitingResponse;
import com.google.gson.Gson;

import java.util.concurrent.ExecutionException;

public class Map extends AppCompatActivity {

    String userId;
    //현재 뭘 타고있는지
    String ride;

    String LocalString;
    //dp값을 반환해주는 함수
    int change_dp(int dp)
    {
        //화면 밀도를 저장하는 변수
        float scale = getResources().getDisplayMetrics().density;
        int re = (int) (dp*scale+0.5f);
        return re;
    }





    FrameLayout map_FrameLayout;
    //리니어 레이아웃
    LinearLayout LL_ride;
    //맵의 위쪽에 있는 버튼들의 레이아웃
    LinearLayout top_menu;
    ImageView map;
    //버튼
    //맵의 놀이기구 버튼 및 놀이기구의 이름
    Button Horror_Maze, T_coaster, alpine_carnival, space_tour, shooting_ghost, magic_school, jubilee_carnival, Merry_go_round, sky_dancing, bumper_car, Magic_swing_dancing, Magic_Cookie_House,
            Peter_Pan, Horror_Maze_sign, T_coaster_sign, alpine_carnival_sign, space_tour_sign, shooting_ghost_sign, magic_school_sign, jubilee_carnival_sign, Merry_go_round_sign, sky_dancing_sign
            , bumper_car_sign, Magic_swing_dancing_sign, Magic_Cookie_House_sign, Peter_Pan_sign, View_in_table, View_in_name, show_QR, CheckWatingTime;
            ;
    //예약에 필요한 상자속의 버튼들
    Button LL_ride_waiting_person, LL_ride_waiting_time, LL_ride_reservation;

    //bool 타입 변수--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //맵에 놀이기구의 이름이 표기 되었는지 안되었는지 확인하기 위한 변수
    boolean check_show_name = false;
    //LL-ride가 표시 되어있는지 안되어 있는지 확인하는 변수
    boolean show_LL_ride;
    //bool 타입 변수--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    //int 타입 변수--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    //어떤
    String LL_ride_check;
    //int 타입 변수--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    //double 타입 변수--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    //double 타입 변수--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);


        //user id 받아오는 곳
        Intent intent = getIntent();
        if(intent != null)
        {
            String id = intent.getStringExtra("user_id");
            userId = id;
        }
        System.out.println("맵 에서 userId" + userId);

        //아이디 찾는곳
        /////////////////////////////////////////////////////////////////////////
        //버튼

        //놀이기구 버튼 밑 놀이기구 버튼의 이름 표
        Horror_Maze = (Button) findViewById(R.id.Horror_Maze);
        Horror_Maze_sign = (Button) findViewById(R.id.Horror_Maze_sign);
        T_coaster = (Button) findViewById(R.id.T_coaster);
        T_coaster_sign = (Button) findViewById(R.id.T_coaster_sign);
        alpine_carnival = (Button) findViewById(R.id.alpine_carnival);
        alpine_carnival_sign = (Button) findViewById(R.id.alpine_carnival_sign);
        space_tour = (Button) findViewById(R.id.space_tour);
        space_tour_sign = (Button) findViewById(R.id.space_tour_sign);
        shooting_ghost = (Button) findViewById(R.id.shooting_ghost);
        shooting_ghost_sign = (Button) findViewById(R.id.shooting_ghost_sign);
        magic_school = (Button) findViewById(R.id.magic_school);
        magic_school_sign = (Button) findViewById(R.id.magic_school_sign);
        jubilee_carnival = (Button) findViewById(R.id.jubilee_carnival);
        jubilee_carnival_sign = (Button) findViewById(R.id.jubilee_carnival_sign);
        Merry_go_round = (Button) findViewById(R.id.Merry_go_round);
        Merry_go_round_sign = (Button) findViewById(R.id.Merry_go_round_sign);
        sky_dancing = (Button) findViewById(R.id.sky_dancing);
        sky_dancing_sign = (Button) findViewById(R.id.sky_dancing_sign);
        bumper_car = (Button) findViewById(R.id.bumper_car);
        bumper_car_sign = (Button) findViewById(R.id.bumper_car_sign);
        Magic_swing_dancing = (Button) findViewById(R.id.Magic_swing_dancing);
        Magic_swing_dancing_sign = (Button) findViewById(R.id.Magic_swing_dancing_sign);
        Magic_Cookie_House = (Button) findViewById(R.id.Magic_Cookie_House);
        Magic_Cookie_House_sign = (Button) findViewById(R.id.Magic_Cookie_House_sign);
        Peter_Pan = (Button) findViewById(R.id.Peter_Pan);
        Peter_Pan_sign = (Button) findViewById(R.id.Peter_Pan_sign);
        space_tour_sign = (Button) findViewById(R.id.space_tour_sign);
        space_tour = (Button) findViewById(R.id.space_tour);
        // 윗쪽의 버튼들
        View_in_table = (Button) findViewById(R.id.View_in_table);
        View_in_name = (Button) findViewById(R.id.View_in_name);
        show_QR = (Button) findViewById(R.id.show_QR);
        CheckWatingTime = (Button) findViewById(R.id.map_Button_Checkwating);
        //예약에 필요한 상자에 필요한 버튼들
        LL_ride_waiting_person = (Button) findViewById(R.id.LL_ride_waiting_person); //대기 인원
        LL_ride_waiting_time = (Button) findViewById(R.id.LL_ride_waiting_time);    //기다리는 시간
        LL_ride_reservation = (Button) findViewById(R.id.LL_ride_reservation);  //예약
        /////////////////////////////////////////////////////////////////////////
        //레이아웃
        map_FrameLayout = (FrameLayout) findViewById(R.id.map_FrameLayout);
        top_menu = (LinearLayout) findViewById(R.id.top_menu);
        //예약하기에 필요한 박스
        LL_ride = (LinearLayout) findViewById(R.id.LL_ride);
        /////////////////////////////////////////////////////////////////////////
        //이미지 뷰
        map = (ImageView) findViewById(R.id.map);
        //아이디 찾는곳

        //현재 남은시간을 확인하는 리스너
        CheckWatingTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(getApplicationContext(), CheckTime.class);
                it.putExtra("user_id", userId);
                it.putExtra("ride", ride);
                startActivity(it);
                finish();
            }
        });

        //놀이기구 이름포기 를 클릭하면 놀이기구의 이름이 보였다 안보였다 하도록 하는 리스너
        View_in_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check_show_name == false)
                {
                    Horror_Maze_sign.setVisibility(View.VISIBLE);
                    T_coaster_sign.setVisibility(View.VISIBLE);
                    alpine_carnival_sign.setVisibility(View.VISIBLE);
                    space_tour_sign.setVisibility(View.VISIBLE);
                    shooting_ghost_sign.setVisibility(View.VISIBLE);
                    magic_school_sign.setVisibility(View.VISIBLE);
                    jubilee_carnival_sign.setVisibility(View.VISIBLE);
                    Merry_go_round_sign.setVisibility(View.VISIBLE);
                    sky_dancing_sign.setVisibility(View.VISIBLE);
                    bumper_car_sign.setVisibility(View.VISIBLE);
                    Magic_swing_dancing_sign.setVisibility(View.VISIBLE);
                    space_tour_sign.setVisibility(View.VISIBLE);
                    Peter_Pan_sign.setVisibility(View.VISIBLE);
                    check_show_name = true;
                }
                else
                {
                    Horror_Maze_sign.setVisibility(View.GONE);
                    T_coaster_sign.setVisibility(View.GONE);
                    alpine_carnival_sign.setVisibility(View.GONE);
                    space_tour_sign.setVisibility(View.GONE);
                    shooting_ghost_sign.setVisibility(View.GONE);
                    magic_school_sign.setVisibility(View.GONE);
                    jubilee_carnival_sign.setVisibility(View.GONE);
                    Merry_go_round_sign.setVisibility(View.GONE);
                    sky_dancing_sign.setVisibility(View.GONE);
                    bumper_car_sign.setVisibility(View.GONE);
                    Magic_swing_dancing_sign.setVisibility(View.GONE);
                    space_tour_sign.setVisibility(View.GONE);
                    Peter_Pan_sign.setVisibility(View.GONE);
                    check_show_name = false;
                }
            }

        });

        //호러메이즈 를 클릭하면 LL-ride가 나왔다 없어졌다 하는 버튼
        //LL_ride_check =1
        Horror_Maze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(show_LL_ride == false)
                {
                    FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) LL_ride.getLayoutParams();
                    params.leftMargin = change_dp(132);
                    params.topMargin = change_dp(115);
                    LL_ride.setLayoutParams(params);
                    LL_ride.setVisibility(View.VISIBLE);
                    LL_ride_check ="호러 메이즈";
                    show_LL_ride = true;

                    SendDataToServer_Map_button task = new SendDataToServer_Map_button("호러 메이즈", userId);
                    task.execute();
                    try {
                        String result = task.get();
                        LocalString =result;
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    WaitingResponse waitingResponse;
                    Gson gson = new Gson();
                    waitingResponse = gson.fromJson(LocalString, WaitingResponse.class);
                    LL_ride_waiting_person.setText(String.valueOf(waitingResponse.getWaitingTime()) + "분");
                    LL_ride_waiting_time.setText(String.valueOf(waitingResponse.getWaitingNumber()) + "명");

                }
                else
                {
                    LL_ride.setVisibility(View.GONE);
                    show_LL_ride = false;
                }
            }
        });
        //LL_ride_check =2
        T_coaster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(show_LL_ride == false)
                {
                    FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) LL_ride.getLayoutParams();
                    params.leftMargin = change_dp(195);
                    params.topMargin = change_dp(124);
                    LL_ride_check ="T익스프레스";
                    LL_ride.setLayoutParams(params);
                    LL_ride.setVisibility(View.VISIBLE);
                    show_LL_ride = true;

                    SendDataToServer_Map_button task = new SendDataToServer_Map_button("T익스프레스", userId);
                    task.execute();
                    try {
                        String result = task.get();
                        LocalString =result;
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    WaitingResponse waitingResponse;
                    Gson gson = new Gson();
                    waitingResponse = gson.fromJson(LocalString, WaitingResponse.class);
                    LL_ride_waiting_person.setText(String.valueOf(waitingResponse.getWaitingTime()) + "분");
                    LL_ride_waiting_time.setText(String.valueOf(waitingResponse.getWaitingNumber()) + "명");
                }
                else
                {
                    LL_ride.setVisibility(View.GONE);
                    show_LL_ride = false;
                }
            }
        });
        //LL_ride_check = 3
        alpine_carnival.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(show_LL_ride == false)
                {
                    FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) LL_ride.getLayoutParams();
                    params.leftMargin = change_dp(156);
                    params.topMargin = change_dp(144);
                    LL_ride_check ="알파인 카니발";
                    LL_ride.setLayoutParams(params);
                    LL_ride.setVisibility(View.VISIBLE);
                    show_LL_ride = true;

                    SendDataToServer_Map_button task = new SendDataToServer_Map_button("알파인 카니발", userId);
                    task.execute();
                    try {
                        String result = task.get();
                        LocalString =result;
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    WaitingResponse waitingResponse;
                    Gson gson = new Gson();
                    waitingResponse = gson.fromJson(LocalString, WaitingResponse.class);
                    LL_ride_waiting_person.setText(String.valueOf(waitingResponse.getWaitingTime()) + "분");
                    LL_ride_waiting_time.setText(String.valueOf(waitingResponse.getWaitingNumber()) + "명");
                }
                else
                {
                    LL_ride.setVisibility(View.GONE);
                    show_LL_ride = false;
                }
            }
        });
        //LL_ride_check = 4
        space_tour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(show_LL_ride == false)
                {
                    FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) LL_ride.getLayoutParams();
                    params.leftMargin = change_dp(269);
                    params.topMargin = change_dp(223);
                    LL_ride_check ="스페이스투어";
                    LL_ride.setLayoutParams(params);
                    LL_ride.setVisibility(View.VISIBLE);
                    show_LL_ride = true;

                    SendDataToServer_Map_button task = new SendDataToServer_Map_button("스페이스투어", userId);
                    task.execute();
                    try {
                        String result = task.get();
                        LocalString =result;
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    WaitingResponse waitingResponse;
                    Gson gson = new Gson();
                    waitingResponse = gson.fromJson(LocalString, WaitingResponse.class);
                    LL_ride_waiting_person.setText(String.valueOf(waitingResponse.getWaitingTime()) + "분");
                    LL_ride_waiting_time.setText(String.valueOf(waitingResponse.getWaitingNumber()) + "명");
                }
                else
                {
                    LL_ride.setVisibility(View.GONE);
                    show_LL_ride = false;
                }
            }
        });
        //LL_ride_check = 5
        shooting_ghost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(show_LL_ride == false)
                {
                    FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) LL_ride.getLayoutParams();
                    params.leftMargin = change_dp(290);
                    params.topMargin = change_dp(189);
                    LL_ride_check ="슈팅 고스트";
                    LL_ride.setLayoutParams(params);
                    LL_ride.setVisibility(View.VISIBLE);
                    show_LL_ride = true;

                    SendDataToServer_Map_button task = new SendDataToServer_Map_button("슈팅 고스트", userId);
                    task.execute();
                    try {
                        String result = task.get();
                        LocalString =result;
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    WaitingResponse waitingResponse;
                    Gson gson = new Gson();
                    waitingResponse = gson.fromJson(LocalString, WaitingResponse.class);
                    LL_ride_waiting_person.setText(String.valueOf(waitingResponse.getWaitingTime()) + "분");
                    LL_ride_waiting_time.setText(String.valueOf(waitingResponse.getWaitingNumber()) + "명");
                }
                else
                {
                    LL_ride.setVisibility(View.GONE);
                    show_LL_ride = false;
                }
            }
        });
        //LL_ride_check = 6
        magic_school.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(show_LL_ride == false)
                {
                    FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) LL_ride.getLayoutParams();
                    params.leftMargin = change_dp(296);
                    params.topMargin = change_dp(203);
                    LL_ride_check ="마법학교";
                    LL_ride.setLayoutParams(params);
                    LL_ride.setVisibility(View.VISIBLE);
                    show_LL_ride = true;

                    SendDataToServer_Map_button task = new SendDataToServer_Map_button("마법학교", userId);
                    task.execute();
                    try {
                        String result = task.get();
                        LocalString =result;
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    WaitingResponse waitingResponse;
                    Gson gson = new Gson();
                    waitingResponse = gson.fromJson(LocalString, WaitingResponse.class);
                    LL_ride_waiting_person.setText(String.valueOf(waitingResponse.getWaitingTime()) + "분");
                    LL_ride_waiting_time.setText(String.valueOf(waitingResponse.getWaitingNumber()) + "명");
                }
                else
                {
                    LL_ride.setVisibility(View.GONE);
                    show_LL_ride = false;
                }
            }
        });
        //LL_ride_check = 7
        jubilee_carnival.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(show_LL_ride == false)
                {
                    FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) LL_ride.getLayoutParams();
                    params.leftMargin = change_dp(461);
                    params.topMargin = change_dp(102);
                    LL_ride_check ="주빌리 카니발";
                    LL_ride.setLayoutParams(params);
                    LL_ride.setVisibility(View.VISIBLE);
                    show_LL_ride = true;

                    SendDataToServer_Map_button task = new SendDataToServer_Map_button("주빌리 카니발", userId);
                    task.execute();
                    try {
                        String result = task.get();
                        LocalString =result;
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    WaitingResponse waitingResponse;
                    Gson gson = new Gson();
                    waitingResponse = gson.fromJson(LocalString, WaitingResponse.class);
                    LL_ride_waiting_person.setText(String.valueOf(waitingResponse.getWaitingTime()) + "분");
                    LL_ride_waiting_time.setText(String.valueOf(waitingResponse.getWaitingNumber()) + "명");
                }
                else
                {
                    LL_ride.setVisibility(View.GONE);
                    show_LL_ride = false;
                }
            }
        });
        //LL_ride_check = 8
        Merry_go_round.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(show_LL_ride == false)
                {
                    FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) LL_ride.getLayoutParams();
                    params.leftMargin = change_dp(461);
                    params.topMargin = change_dp(74);
                    LL_ride_check ="회전 목마";
                    LL_ride.setLayoutParams(params);
                    LL_ride.setVisibility(View.VISIBLE);
                    show_LL_ride = true;

                    SendDataToServer_Map_button task = new SendDataToServer_Map_button("회전 목마", userId);
                    task.execute();
                    try {
                        String result = task.get();
                        LocalString =result;
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    WaitingResponse waitingResponse;
                    Gson gson = new Gson();
                    waitingResponse = gson.fromJson(LocalString, WaitingResponse.class);
                    LL_ride_waiting_person.setText(String.valueOf(waitingResponse.getWaitingTime()) + "분");
                    LL_ride_waiting_time.setText(String.valueOf(waitingResponse.getWaitingNumber()) + "명");
                }
                else
                {
                    LL_ride.setVisibility(View.GONE);
                    show_LL_ride = false;
                }
            }
        });
        //LL_ride_check = 9
        sky_dancing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(show_LL_ride == false)
                {
                    FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) LL_ride.getLayoutParams();
                    params.leftMargin = change_dp(592);
                    params.topMargin = change_dp(67);
                    LL_ride_check ="스카이 댄싱";
                    LL_ride.setLayoutParams(params);
                    LL_ride.setVisibility(View.VISIBLE);
                    show_LL_ride = true;

                    SendDataToServer_Map_button task = new SendDataToServer_Map_button("스카이 댄싱", userId);
                    task.execute();
                    try {
                        String result = task.get();
                        LocalString =result;
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    WaitingResponse waitingResponse;
                    Gson gson = new Gson();
                    waitingResponse = gson.fromJson(LocalString, WaitingResponse.class);
                    LL_ride_waiting_person.setText(String.valueOf(waitingResponse.getWaitingTime()) + "분");
                    LL_ride_waiting_time.setText(String.valueOf(waitingResponse.getWaitingNumber()) + "명");
                }
                else
                {
                    LL_ride.setVisibility(View.GONE);
                    show_LL_ride = false;
                }
            }
        });
        //LL_ride_check = 10
        bumper_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(show_LL_ride == false)
                {
                    FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) LL_ride.getLayoutParams();
                    params.leftMargin = change_dp(504);
                    params.topMargin = change_dp(104);
                    LL_ride_check ="범퍼카";
                    LL_ride.setLayoutParams(params);
                    LL_ride.setVisibility(View.VISIBLE);
                    show_LL_ride = true;

                    SendDataToServer_Map_button task = new SendDataToServer_Map_button("범퍼카", userId);
                    task.execute();
                    try {
                        String result = task.get();
                        LocalString =result;
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    WaitingResponse waitingResponse;
                    Gson gson = new Gson();
                    waitingResponse = gson.fromJson(LocalString, WaitingResponse.class);
                    LL_ride_waiting_person.setText(String.valueOf(waitingResponse.getWaitingTime()) + "분");
                    LL_ride_waiting_time.setText(String.valueOf(waitingResponse.getWaitingNumber()) + "명");
                }
                else
                {
                    LL_ride.setVisibility(View.GONE);
                    show_LL_ride = false;
                }
            }
        });
        //LL_ride_check = 11
        Magic_swing_dancing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(show_LL_ride == false)
                {
                    FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) LL_ride.getLayoutParams();
                    params.leftMargin = change_dp(528);
                    params.topMargin = change_dp(48);
                    LL_ride_check ="매직 스윙";
                    LL_ride.setLayoutParams(params);
                    LL_ride.setVisibility(View.VISIBLE);
                    show_LL_ride = true;

                    SendDataToServer_Map_button task = new SendDataToServer_Map_button("매직 스윙", userId);
                    task.execute();
                    try {
                        String result = task.get();
                        LocalString =result;
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    WaitingResponse waitingResponse;
                    Gson gson = new Gson();
                    waitingResponse = gson.fromJson(LocalString, WaitingResponse.class);
                    LL_ride_waiting_person.setText(String.valueOf(waitingResponse.getWaitingTime()) + "분");
                    LL_ride_waiting_time.setText(String.valueOf(waitingResponse.getWaitingNumber()) + "명");
                }
                else
                {
                    LL_ride.setVisibility(View.GONE);
                    show_LL_ride = false;
                }
            }
        });
        //LL_ride_check = 12
        Magic_Cookie_House.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(show_LL_ride == false)
                {
                    FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) LL_ride.getLayoutParams();
                    params.leftMargin = change_dp(547);
                    params.topMargin = change_dp(59);
                    LL_ride_check ="매직 쿠키 하우스";
                    LL_ride.setLayoutParams(params);
                    LL_ride.setVisibility(View.VISIBLE);
                    show_LL_ride = true;

                    SendDataToServer_Map_button task = new SendDataToServer_Map_button("매직 쿠키 하우스", userId);
                    task.execute();
                    try {
                        String result = task.get();
                        LocalString =result;
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    WaitingResponse waitingResponse;
                    Gson gson = new Gson();
                    waitingResponse = gson.fromJson(LocalString, WaitingResponse.class);
                    LL_ride_waiting_person.setText(String.valueOf(waitingResponse.getWaitingTime()) + "분");
                    LL_ride_waiting_time.setText(String.valueOf(waitingResponse.getWaitingNumber()) + "명");
                }
                else
                {
                    LL_ride.setVisibility(View.GONE);
                    show_LL_ride = false;
                }
            }
        });
        //LL_ride_check = 13
        Peter_Pan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(show_LL_ride == false)
                {
                    FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) LL_ride.getLayoutParams();
                    params.leftMargin = change_dp(556);
                    params.topMargin = change_dp(76);
                    LL_ride_check ="피터팬";
                    LL_ride.setLayoutParams(params);
                    LL_ride.setVisibility(View.VISIBLE);
                    show_LL_ride = true;

                    SendDataToServer_Map_button task = new SendDataToServer_Map_button("피터팬", userId);
                    task.execute();
                    try {
                        String result = task.get();
                        LocalString =result;
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    WaitingResponse waitingResponse;
                    Gson gson = new Gson();
                    waitingResponse = gson.fromJson(LocalString, WaitingResponse.class);
                    LL_ride_waiting_person.setText(String.valueOf(waitingResponse.getWaitingTime()) + "분");
                    LL_ride_waiting_time.setText(String.valueOf(waitingResponse.getWaitingNumber()) + "명");
                }
                else
                {
                    LL_ride.setVisibility(View.GONE);
                    show_LL_ride = false;
                }
            }
        });

        //QR코드로 이동 버튼 클릭시 동작
        show_QR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(getApplicationContext(), QR.class);
                it.putExtra("user_id", userId);
                startActivity(it);
                finish();
            }
        });

        View_in_table.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(getApplicationContext(), Table.class);

                it.putExtra("user_id", userId);
                startActivity(it);
                finish();
            }
        });



        //동그라미 누르면 나오는 예약하기
        LL_ride_reservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Response response;
                SendDataToServer_reservation_Map task = new SendDataToServer_reservation_Map(userId, LL_ride_check);
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
                response = gson.fromJson(LocalString, Response.class);

                if(response.isSuccess())
                {
                    Toast.makeText(getApplicationContext(), response.getMessage(), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), response.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}