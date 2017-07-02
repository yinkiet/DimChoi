package dimchoi.com.my.searchpage;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import dimchoi.com.my.Global;
import dimchoi.com.my.PlaceOrder;
import dimchoi.com.my.R;
import dimchoi.com.my.ultility.AsyncTaskLoadImage;

/**
 * Provides UI for the Detail page with Collapsing Toolbar.
 */
public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "position";
    public final String EXTRA_NAME = "name";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant_details);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Set Collapsing Toolbar layout to the screen
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        // Set title of Detail page
        // collapsingToolbar.setTitle(getString(R.string.item_title));

        Bundle extras = getIntent().getExtras();
        byte[] byteArray = extras.getByteArray("picture");
        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        String resName = extras.getString(EXTRA_NAME);


        Resources resources = getResources();

        collapsingToolbar.setTitle(resName);

        TextView placeDetail = (TextView) findViewById(R.id.place_detail);
        placeDetail.setText("detail");

        TextView placeLocation =  (TextView) findViewById(R.id.place_location);
        placeLocation.setText("location");

        ImageView placePicture = (ImageView) findViewById(R.id.image_view);
        placePicture.setImageBitmap(bmp);

        Button startOrder = (Button)findViewById(R.id.start_order_btn);

        startOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PlaceOrder.class);
                startActivity(intent);
            }
        });
    }
}
