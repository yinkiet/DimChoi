package dimchoi.com.my.searchpage;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import dimchoi.com.my.Global;
import dimchoi.com.my.R;
import dimchoi.com.my.ultility.AsyncTaskLoadImage;

public class CardContentFragment extends Fragment {

    private final String TAG = "CardContentFragment";
    Context context;

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
        public TextView name;
        public ProgressBar progressBar;
        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.item_card, parent, false));

            progressBar = (ProgressBar) itemView.findViewById(R.id.imageProgressBar);
            picture = (ImageView) itemView.findViewById(R.id.card_image);
            name = (TextView) itemView.findViewById(R.id.card_title);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(getActivity(),
                            Pair.create(v,"picture")
                    ).toBundle();

                    Intent intent = new Intent(getActivity(), DetailActivity.class);
                    intent.putExtra(DetailActivity.EXTRA_POSITION, getAdapterPosition());
                    startActivity(intent, bundle);
                }
            });
        }
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

        //Global global = new Global();

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

            ((Global) context.getApplicationContext()).setLength(LENGTH);
            ((Global) context.getApplicationContext()).setPlace(mPlaces);
            ((Global) context.getApplicationContext()).setPlaceDesc(mPlaceDesc);
            ((Global) context.getApplicationContext()).setImageID(imageID);
//            global.setLength(LENGTH);
//            global.setPlace(mPlaces);
//            global.setPlaceDesc(mPlaceDesc);
//            global.setImageID(imageID);
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            //holder.picture.setImageDrawable(mPlacePictures[position % mPlacePictures.length]);
            holder.name.setText(mPlaces[position % mPlaces.length]);
            new AsyncTaskLoadImage(holder.picture, holder.progressBar).execute(imageID[position % imageID.length]);
        }

        @Override
        public int getItemCount() {
            return LENGTH;
        }
    }
}
