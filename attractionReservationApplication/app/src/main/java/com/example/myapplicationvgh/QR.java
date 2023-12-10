package com.example.myapplicationvgh;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class QR extends AppCompatActivity {
    String userId;


    TextView Rides_text, manager_text;

    //버튼
    Button back_home;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);





        Intent intent = getIntent();
        if(intent != null)
        {
            String op = intent.getStringExtra("user_id");
            userId = op;

        }

        ImageView imageView = findViewById(R.id.imageView);

        String textToEncode = userId;

        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(textToEncode, BarcodeFormat.QR_CODE, 200, 200);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            imageView.setImageBitmap(bitmap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //돌아가기 버튼
        back_home = (Button) findViewById(R.id.back_home);



        //버튼을 누른다면 홈 화면으로 돌아가도록 설정
        back_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Map.class);
                intent.putExtra("user_id", userId);
                startActivity(intent);
                finish();
            }
        });


    }
}