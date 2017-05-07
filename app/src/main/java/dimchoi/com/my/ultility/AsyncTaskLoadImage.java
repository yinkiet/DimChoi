package dimchoi.com.my.ultility;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class AsyncTaskLoadImage  extends AsyncTask<String, String, Bitmap> {

    private final static String TAG = "AsyncTaskLoadImage";
    private ImageView imageView;
    private ProgressBar progressBar;
    public AsyncTaskLoadImage(ImageView imageView, ProgressBar progressBar) {
        this.imageView = imageView;
        this.progressBar = progressBar;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        Bitmap bitmap = null;
        try {
            URL url = new URL(params[0]);
            bitmap = BitmapFactory.decodeStream((InputStream)url.getContent());
        } catch(IOException e) {
            Log.e(TAG, "Error: " + e.getMessage());
        }
        return bitmap;
    }

    @Override
    protected void onPreExecute() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
        progressBar.setVisibility(View.GONE);
    }
}