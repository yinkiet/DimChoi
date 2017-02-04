package com.tjx.dimchoi;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.datamatrix.DataMatrixReader;
import com.google.zxing.qrcode.QRCodeReader;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity {
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
                readQRText.setText(readQRCode(bitmap));

            }
        });
    }

    // this is method call from on create and return bitmap image of QRCode.
    Bitmap encodeAsBitmap(String str) throws WriterException {
        BitMatrix result;
        try {
            result = new MultiFormatWriter().encode(str,
                    BarcodeFormat.QR_CODE, WIDTH, WIDTH, null);
        } catch (IllegalArgumentException iae) {
            // Unsupported format
            return null;
        }
        int w = result.getWidth();
        int h = result.getHeight();
        int[] pixels = new int[w * h];
        for (int y = 0; y < h; y++) {
            int offset = y * w;
            for (int x = 0; x < w; x++) {
                pixels[offset + x] = result.get(x, y) ? getResources().getColor(R.color.black):getResources().getColor(R.color.white);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, 500, 0, 0, w, h);
        return bitmap;
    }

    public String readQRCode(Bitmap bitmap) {
        Result result = null;

        if (bitmap == null)
            return null;

        int[] intArray = new int[bitmap.getWidth()*bitmap.getHeight()];
        LuminanceSource source = new RGBLuminanceSource(bitmap.getWidth(), bitmap.getHeight(),intArray);

        BinaryBitmap bitMap = new BinaryBitmap(new HybridBinarizer(source));
        Reader reader = new DataMatrixReader();

        try {
            result = reader.decode(bitMap);
            return result.getText();
        } catch (NotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ChecksumException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (FormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }
}