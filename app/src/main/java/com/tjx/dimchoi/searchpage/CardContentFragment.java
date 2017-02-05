package com.tjx.dimchoi.searchpage;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.tjx.dimchoi.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Provides UI for the view with Cards.
 */
public class CardContentFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(
                R.layout.recycler_view, container, false);
        ContentAdapter adapter = new ContentAdapter(recyclerView.getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return recyclerView;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView picture;
        public String url;
        public TextView name;
        public TextView description;
        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_card, parent, false));

            Bitmap bitmap = DownloadImage(url);

            picture = (ImageView) itemView.findViewById(R.id.card_image);
            name = (TextView) itemView.findViewById(R.id.card_title);
            description = (TextView) itemView.findViewById(R.id.card_text);

            picture.setImageBitmap(bitmap);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra(DetailActivity.EXTRA_POSITION, getAdapterPosition());
                    context.startActivity(intent);
                }
            });

            // Adding Snackbar to Action Button inside card
            Button button = (Button)itemView.findViewById(R.id.action_button);
            button.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Snackbar.make(v, "Action is pressed",
                            Snackbar.LENGTH_LONG).show();
                }
            });

            ImageButton favoriteImageButton =
                    (ImageButton) itemView.findViewById(R.id.favorite_button);
            favoriteImageButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Snackbar.make(v, "Added to Favorite",
                            Snackbar.LENGTH_LONG).show();
                }
            });

            ImageButton shareImageButton = (ImageButton) itemView.findViewById(R.id.share_button);
            shareImageButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Snackbar.make(v, "Share article",
                            Snackbar.LENGTH_LONG).show();
                }
            });
        }
    }

    private InputStream OpenHttpConnection(String urlString) throws IOException {
        InputStream in = null;
        int response = -1;

        URL url = new URL(urlString);
        URLConnection conn = url.openConnection();

        if (!(conn instanceof HttpURLConnection))
            throw new IOException("Not an HTTP connection");

        try {
            HttpURLConnection httpConn = (HttpURLConnection) conn;
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            httpConn.connect();
            response = httpConn.getResponseCode();
            if (response == HttpURLConnection.HTTP_OK) {
                in = httpConn.getInputStream();
            }
        } catch (Exception ex) {
            throw new IOException("Error connecting");
        }
        return in;
    }

    private Bitmap DownloadImage(String URL) {
        Bitmap bitmap = null;
        InputStream in = null;
        try {
            in = OpenHttpConnection(URL);
            bitmap = BitmapFactory.decodeStream(in);
            in.close();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        return bitmap;
    }

    /**
     * Adapter to display recycler view.
     */
    public class ContentAdapter extends RecyclerView.Adapter<ViewHolder> {

        private final String[] mPlaces = {
                "Ishin Japanese",
                "Mosaic",
                "Skillet",
                "Naughty Nuri's Life Centre"
        };

        private final String[] mPlaceDesc = {
                "ISHIN is a Japanese restaurant serving genuine Japanese and Kaiseki style cuisines that are cooked to order. We prepare our meals freshly using ingredient imported from Japan's famous Tsukiji market with the finest ingredients of the season.",
                "Mosaic boasts a wide-ranging menu featuring both Oriental and Western specialties. Watch our chefs prepare them at cooking stations or order at your leisure from our a la carte menu. Elegant Peranakan designs and luxurious furnishings surround diners in local history, culture and sensibility.",
                "Following more than a year of research and development, Skillet has an exclusive new brioche roll! With a burger often lauded among the best in town, this tasty development is our next iteration of goodness!",
                "Terrace, garden and indoor seating for eating those wicked ribs and drinking those brutal martinis. Get a \"shake, shake, shake\" dance with any martini order. Funky and fun decor with naughty piggies everywhere. Meet new friends by sitting at communal tables or choose a smaller table for more intimate moments. Come for lunch, dinner or pre-club launch, our friendly staff will welcome you anytime."
        };

        //private final Drawable[] mPlacePictures;

        private final String[] imageID = {
                "https://media-cdn.tripadvisor.com/media/photo-s/08/b2/6d/3a/ishin-japanese-dining.jpg",
                "https://media-cdn.tripadvisor.com/media/photo-s/02/e8/dc/99/mosaic.jpg",
                "https://media-cdn.tripadvisor.com/media/photo-s/08/14/8d/47/skillet-at-163.jpg",
                "https://media-cdn.tripadvisor.com/media/photo-s/0c/1d/c8/1e/a.jpg"
        };

        private int LENGTH = mPlaces.length;

        Global global = new Global();

        public ContentAdapter(Context context) {
            Resources resources = context.getResources();
            //mPlaces = resources.getStringArray(R.array.places);
            //mPlaceDesc = resources.getStringArray(R.array.place_desc);
            /*TypedArray a = resources.obtainTypedArray(R.array.places_picture);
            mPlacePictures = new Drawable[a.length()];
            for (int i = 0; i < mPlacePictures.length; i++) {
                mPlacePictures[i] = a.getDrawable(i);
            }
            a.recycle();*/

            global.setLength(LENGTH);
            global.setPlace(mPlaces);
            global.setPlaceDesc(mPlaceDesc);
            global.setImageID(imageID);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            //holder.picture.setImageDrawable(mPlacePictures[position % mPlacePictures.length]);
            holder.name.setText(global.getPlace()[position % mPlaces.length]);
            holder.description.setText(global.getmPlaceDesc()[position % mPlaceDesc.length]);
            holder.url = global.getImageID()[position % imageID.length];
        }

        @Override
        public int getItemCount() {
            return global.getLength();
        }
    }
}
