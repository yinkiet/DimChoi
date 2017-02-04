package com.tjx.dimchoi;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.WriterException;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_WRITE_PERMISSION = 20;

    ImageView qrCodeImageview;
    EditText QRcode;
    TextView readQRText;
    Bitmap bitmap;
    public final static int WIDTH=500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        QRcode = (EditText)findViewById(R.id.editText);
        qrCodeImageview=(ImageView) findViewById(R.id.img_qr_code_image);
        Button generateQRButton = (Button)findViewById(R.id.button);
        Button readQRButton = (Button)findViewById(R.id.button2);
        readQRText = (TextView)findViewById(R.id.textView);

        generateQRButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readQRText.setText("");
                try {
                    GenerateQRCode generateQRCode = new GenerateQRCode(getApplicationContext());
                    //byte[] byteArray = QRcode.getText().toString().getBytes("UTF-16");
                    bitmap = generateQRCode.encodeAsBitmap(QRcode.getText().toString());
                    qrCodeImageview.setImageBitmap(bitmap);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }
        });

        readQRButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*Wait until layout design ready
                Intent intent = new Intent();
                intent.setClass(getApplicationContext(), ScanQRCode.class);
                startActivity(intent);*/

                //Temporary use this
                IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
                integrator.initiateScan();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_WRITE_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //To-do
                } else {
                    Toast.makeText(MainActivity.this, "Permission Denied!", Toast.LENGTH_SHORT).show();
                }
        }
    }

    //TO be remove
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (scanningResult != null) {
            String scanContent = "";
            String scanFormat = "";
            if (scanningResult.getContents() != null) {
                scanContent = scanningResult.getContents().toString();
                scanFormat = scanningResult.getFormatName().toString();
            }

            Toast.makeText(this,scanContent+"   type:"+scanFormat,Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText(this,"Nothing scanned",Toast.LENGTH_SHORT).show();
        }
    }
}