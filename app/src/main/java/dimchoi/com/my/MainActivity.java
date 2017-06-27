package dimchoi.com.my;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import dimchoi.com.my.data.MenuData;
import dimchoi.com.my.ultility.AsyncTaskLoadImage;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_WRITE_PERMISSION = 20;

    private List<MenuData> menuList = new ArrayList<>();

    private RecyclerView featuredRestaurantView;
    private RecyclerView nearbyRestaurantView;
    private RecyclerView topReviewRestaurantView;

    private FeaturedRestaurantAdapter featuredRestaurantAdapter;
    private NearbyRestaurantAdapter nearbyRestaurantAdapter;
    private TopReviewRestaurantAdapter topReviewRestaurantAdapter;

    private final int[] ID = {
            1, 2, 3, 4
    };

    private final String[] title = {
            "Ishin Japanese",
            "Mosaic",
            "Skillet",
            "Naughty Nuri's Life Centre"
    };

    private final String[] imageView = {
            "https://media-cdn.tripadvisor.com/media/photo-s/08/b2/6d/3a/ishin-japanese-dining.jpg",
            "https://media-cdn.tripadvisor.com/media/photo-s/02/e8/dc/99/mosaic.jpg",
            "https://media-cdn.tripadvisor.com/media/photo-s/08/14/8d/47/skillet-at-163.jpg",
            "https://media-cdn.tripadvisor.com/media/photo-s/0c/1d/c8/1e/a.jpg"
    };

    private int[] featuredID = {};
    private String[] featuredTitle = {};
    private String[] featuredImage = {};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (int i = 0; i < ID.length; i++) {
            MenuData menuData = new  MenuData(ID[i], imageView[i], title[i]);
            menuList.add(menuData);
        }

        featuredRestaurantView = (RecyclerView) findViewById(R.id.featured_restaurant_view);
        nearbyRestaurantView = (RecyclerView) findViewById(R.id.nearby_restaurant_view);
        topReviewRestaurantView = (RecyclerView) findViewById(R.id.top_review_restaurant_view);

        featuredRestaurantAdapter=new FeaturedRestaurantAdapter(menuList, getApplication());
        LinearLayoutManager featuredLayout = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        featuredRestaurantView.setLayoutManager(featuredLayout);

        nearbyRestaurantAdapter=new NearbyRestaurantAdapter(menuList, getApplication());
        topReviewRestaurantAdapter=new TopReviewRestaurantAdapter(menuList, getApplication());

        LinearLayoutManager nearbyLayout = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager topLayout = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);


        nearbyRestaurantView.setLayoutManager(nearbyLayout);
        nearbyRestaurantView.setAdapter(nearbyRestaurantAdapter);
        topReviewRestaurantView.setLayoutManager(topLayout);
        topReviewRestaurantView.setAdapter(topReviewRestaurantAdapter);

        new SendPostRequest().execute();
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

    class FeaturedRestaurantAdapter extends RecyclerView.Adapter<FeaturedRestaurantAdapter.MyViewHolder> {
        List<MenuData> menuList = Collections.emptyList();
        Context context;

        FeaturedRestaurantAdapter(List<MenuData> menuList, Context context) {
            this.menuList = menuList;
            this.context = context;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            ImageView imageView;
            TextView txtview;
            public MyViewHolder(View view) {
                super(view);
                imageView=(ImageView) view.findViewById(R.id.image_menu_small);
                txtview=(TextView) view.findViewById(R.id.menu_title_small);
            }
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_cardview_small, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {

            new AsyncTaskLoadImage(holder.imageView, null).execute(menuList.get(position).imageView);
            holder.txtview.setText(menuList.get(position).title);

            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override

                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, String.valueOf(menuList.get(position).restaurantID), Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public int getItemCount()
        {
            return menuList.size();
        }
    }

    class NearbyRestaurantAdapter extends RecyclerView.Adapter<NearbyRestaurantAdapter.MyViewHolder> {
        List<MenuData> menuList = Collections.emptyList();
        Context context;

        NearbyRestaurantAdapter(List<MenuData> menuList, Context context) {
            this.menuList = menuList;
            this.context = context;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            ImageView imageView;
            TextView txtview;
            public MyViewHolder(View view) {
                super(view);
                imageView=(ImageView) view.findViewById(R.id.image_menu_small);
                txtview=(TextView) view.findViewById(R.id.menu_title_small);
            }
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_cardview_small, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {

            new AsyncTaskLoadImage(holder.imageView, null).execute(menuList.get(position).imageView);
            holder.txtview.setText(menuList.get(position).title);

            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override

                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, String.valueOf(menuList.get(position).restaurantID), Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public int getItemCount()
        {
            return menuList.size();
        }
    }

    class TopReviewRestaurantAdapter extends RecyclerView.Adapter<TopReviewRestaurantAdapter.MyViewHolder> {
        List<MenuData> menuList = Collections.emptyList();
        Context context;

        TopReviewRestaurantAdapter(List<MenuData> menuList, Context context) {
            this.menuList = menuList;
            this.context = context;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            ImageView imageView;
            TextView txtview;
            public MyViewHolder(View view) {
                super(view);
                imageView=(ImageView) view.findViewById(R.id.image_menu_small);
                txtview=(TextView) view.findViewById(R.id.menu_title_small);
            }
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_cardview_small, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {

            new AsyncTaskLoadImage(holder.imageView, null).execute(menuList.get(position).imageView);
            holder.txtview.setText(menuList.get(position).title);

            holder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override

                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, String.valueOf(menuList.get(position).restaurantID), Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public int getItemCount()
        {
            return menuList.size();
        }
    }

    public class SendPostRequest extends AsyncTask<String, Void, String> {

        protected void onPreExecute(){}

        protected String doInBackground(String... arg0) {

            try {
                URL url = new URL("http://192.168.0.114/dimchoi/result.php"); // here is your URL path

                JSONObject postDataParams = new JSONObject();
                postDataParams.put("func", "getcomp");
                Log.e("params",postDataParams.toString());

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();

                int responseCode=conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in=new BufferedReader(new
                            InputStreamReader(
                            conn.getInputStream()));

                    StringBuffer sb = new StringBuffer("");
                    String line="";

                    while((line = in.readLine()) != null) {

                        sb.append(line);
                    }

                    in.close();
                    return sb.toString();

                }
                else {
                    return new String("false : "+responseCode);
                }
            }
            catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }

        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject data = new JSONObject(result);
                String dataString = data.getString("data");
                JSONArray logo = new JSONArray(dataString);

                menuList.clear();

                for (int i = 0; i < logo.length(); i++) {
                    JSONObject jsonObject = logo.getJSONObject(i);
                    Integer ID = Integer.parseInt(jsonObject.getString("i_res_id"));
                    String  name = jsonObject.getString("va_res_name");
                    String image = jsonObject.getString("va_company_logo");

                    MenuData menuData = new  MenuData(ID, image, name);
                    menuList.add(menuData);
                }



                Toast.makeText(getApplicationContext(), logo.toString(),
                        Toast.LENGTH_LONG).show();

                featuredRestaurantView.setAdapter(featuredRestaurantAdapter);
                featuredRestaurantAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while(itr.hasNext()){

            String key= itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        return result.toString();
    }
}
