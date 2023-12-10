


package com.example.myapplicationvgh;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;




public class SendDataToServerTask_maneger extends AsyncTask<Void, Void, String> {
    String reTrun;
    private String id;
    private String pw;
    private Context context;



    // 생성자를 통해 전송할 데이터를 받아옴
    public SendDataToServerTask_maneger(String id, String pw, Context context) {
        this.id = id;
        this.pw = pw;
        this.context = context;
    }

    @Override
    protected String doInBackground(Void... params) {

        try {



            System.out.println("Task시작");
            // 내가 연결하려는 URL생성
            URL url = new URL("http://118.217.128.115:8080/manager/login");
            System.out.println("url확인");
            // 컨넥트
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            System.out.println("컨넥트 확인");
            // HTTP요청을 설정하는 부분
            connection.setRequestMethod("POST");

            //포스트 일때만 사용
            //전송하고 받아올 데이터 형식을 JSON으로 설정
            connection.setRequestProperty("Content-Type", "application/json");
            //데이터를 담을 JSON오브젝트 생성
            JSONObject postData = new JSONObject();

            try{
                // 전송할 데이터 설정
                postData.put("id", id);
                postData.put("pw", pw);
                //postData.put("data",data);
            }catch(JSONException e){
                e.printStackTrace();
            }
            String jsonString = postData.toString();
            System.out.println("JS = " + jsonString);

            // 연결한 URL에 출력을 사용한다는 것 => 데이터 전송이 가능하게 하는것
            connection.setDoOutput(true);

            if(postData != null)
            {
                try (OutputStream os = connection.getOutputStream()) {
                    //서버에 출력하려는 데이터를 바이트로 바꿈
                    byte[] input = postData.toString().getBytes("utf-8");

                    System.out.println("INPUT = " + postData);
                    //서버로 데이터를 출력
                    os.write(input, 0, input.length);
                }
            }
            //포스트 일때만 사용

            // 서버에 잘 연결되었는지 확인하는 코드
            //200=OK
            int responseCode = connection.getResponseCode();
            System.out.println("연결 상태 : "+ responseCode);


            if (responseCode == HttpURLConnection.HTTP_OK) {
                System.out.println("OK_connectServer");
                // 서버 응답을 읽어올 수 있는 스트림 생성 및 처리
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                //System.out.println(in);
                String inputLine;
                StringBuilder response = new StringBuilder();
                //BufferedReader에서 읽어온것을 1줄씩 inputLine에 저장
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                    System.out.println("inputLine : " + response);
                }


                reTrun = response.toString();

                System.out.println("OK!");
                // 연결 닫기
                in.close();
            } else {
                System.out.println("Bad_request");
                // 서버 응답을 읽어올 수 있는 스트림 생성 및 처리

                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                //System.out.println(in);
                String inputLine;
                StringBuilder response = new StringBuilder();

                //BufferedReader에서 읽어온것을 1줄씩 inputLine에 저장
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                    System.out.println("inputLine : " + response);

                }

                reTrun = response.toString();
                System.out.println("OK!");
                in.close();
                System.out.println("close OK!");


                // 연결 닫기
                in.close();

            }

            // 연결 닫기
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("error");
        }


        return reTrun;
    }




}
