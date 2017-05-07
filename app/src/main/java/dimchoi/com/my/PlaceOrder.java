package dimchoi.com.my;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.zxing.WriterException;

/**
 * Created by y_kch on 4/2/2017.
 */

public class PlaceOrder extends AppCompatActivity {

    Bitmap bitmap;
    Button generateQRCode;
    ImageView qrCodeImageview;
    ListView listView;
    private String QRCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_page);

        generateQRCode = (Button)findViewById(R.id.generate_qr_code_btn);
        qrCodeImageview = (ImageView)findViewById(R.id.qr_code_image);
        listView = (ListView)findViewById(R.id.order_list_view) ;

        generateQRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QRCode = "testing";

                try {
                    GenerateQRCode generateQRCode = new GenerateQRCode(getApplicationContext());
                    //byte[] byteArray = QRcode.getText().toString().getBytes("UTF-16");
                    bitmap = generateQRCode.encodeAsBitmap(QRCode);
                    qrCodeImageview.setImageBitmap(bitmap);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
