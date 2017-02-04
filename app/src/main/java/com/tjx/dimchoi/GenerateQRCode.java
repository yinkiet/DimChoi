package com.tjx.dimchoi;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v4.content.ContextCompat;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

/**
 * Created by y_kch on 4/2/2017.
 */

public class GenerateQRCode extends Application {

    public final static int WIDTH=500;
    private final Context context;
    final int version = Build.VERSION.SDK_INT;

    public GenerateQRCode(Context context) {
        this.context = context;
    }

    public Bitmap encodeAsBitmap(String str) throws WriterException {
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
        int black;
        int white;

        if (version >= 23) {
            black = ContextCompat.getColor(context, R.color.black);
            white = ContextCompat.getColor(context, R.color.white);
        } else {
            black = context.getResources().getColor(R.color.black);
            white = context.getResources().getColor(R.color.white);
        }

        int[] pixels = new int[w * h];
        for (int y = 0; y < h; y++) {
            int offset = y * w;
            for (int x = 0; x < w; x++) {

                pixels[offset + x] = result.get(x, y) ? black:white;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, 500, 0, 0, w, h);
        return bitmap;
    }
}
