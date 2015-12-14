package tw.idv.fly.imgsrc;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    ImageView myImageView;
    ListView lv_menu_item;

    public static String[] images_src = {
            "http://net.jcin.com.tw/p1.jpg",
            "http://net.jcin.com.tw/p2.jpg",
            "http://net.jcin.com.tw/p3.jpg"
    };

    DataDef[] dataDef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataDef = new DataDef[3];

        dataDef[0] = new DataDef();
        dataDef[0].img_url = "http://net.jcin.com.tw/p1.jpg";
        dataDef[0].price = 100;
        dataDef[0].intro = "牛肉麵";

        dataDef[1] = new DataDef();
        dataDef[1].img_url = "http://net.jcin.com.tw/p2.jpg";
        dataDef[1].price = 120;
        dataDef[1].intro = "肉絲麵";

        dataDef[2] = new DataDef();
        dataDef[2].img_url = "http://net.jcin.com.tw/p3.jpg";
        dataDef[2].price = 80;
        dataDef[2].intro = "湯麵";


        //String imageFileURL = "http://net.jcin.com.tw/p1.jpg";
        //myImageView = (ImageView) findViewById(R.id.imageView);

        //String url = "http://net.jcin.com.tw/p1.jpg";
        //Picasso.with(this).load(url).into(myImageView);

        BAdapter adapter = new BAdapter(MainActivity.this, R.layout.menu_item, dataDef);

        //new DownloadImageTask(myImageView).execute(imageFileURL); // 載入圖片


        lv_menu_item = (ListView) findViewById(R.id.listView);

        //int images[] = {R.drawable.p1, R.drawable.p2, R.drawable.p3};
        //String menu_name[] = {"炒飯", "炒麵", "牛肉麵"};

        //ArrayList<HashMap<String, Object>> items = new ArrayList<HashMap<String, Object>>();

        //for (int i = 0; i < images.length; i++) {
        //    HashMap<String, Object> menu_data = new HashMap<String, Object>();
        //    menu_data.put("pic", images[i]);
        //    menu_data.put("text", menu_name[i]);
        //    items.add(menu_data);
        //}


        //SimpleAdapter adapter = new SimpleAdapter(this, items,
        //        R.layout.menu_item,
        //        new String[]{"pic", "text"},
        //        new int[]{R.id.imageMenu, R.id.textMenuContext}
        //);
        lv_menu_item.setAdapter(adapter);


    }

    private class BAdapter extends ArrayAdapter {

        private Context act_context;
        private LayoutInflater inflater;
        private int resourceId;

        private String[] imageUrls;
        private DataDef[] dataDefs;

        public BAdapter(Context context, int resource, DataDef[] objects) {
            super(context, resource, objects);

            this.act_context = context;
            this.resourceId = resource;
            this.dataDefs = (DataDef[]) objects;

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ImageView img_res;
            TextView txt_intro;
            TextView txt_price;

            if (convertView == null) {

                LayoutInflater inflater = ((Activity) act_context).getLayoutInflater();
                convertView = inflater.inflate(this.resourceId, parent, false);


                //convertView = inflater.inflate(this.resourceId, parent, false);
                img_res = (ImageView) convertView.findViewById(R.id.imageMenu);
                txt_intro = (TextView) convertView.findViewById(R.id.textMenuContext);
                txt_price = (TextView) convertView.findViewById(R.id.textPrice);

            } else {
                img_res = (ImageView) convertView.findViewById(R.id.imageMenu);
                txt_intro = (TextView) convertView.findViewById(R.id.textMenuContext);
                txt_price = (TextView) convertView.findViewById(R.id.textPrice);
            }

            DataDef def = this.dataDefs[position];

            txt_intro.setText(def.intro);
            txt_price.setText(String.valueOf(def.price));
            Picasso.with(act_context).load(def.img_url).resize(150, 150).into(img_res);

            return convertView;
        }
    }


    //讀取網路圖片，型態為Bitmap
    private static Bitmap getBitmapFromURL(String imageUrl) {
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            //Bitmap bitmap = BitmapFactory.decodeStream(input);
            //return bitmap;
            return BitmapFactory.decodeStream(input);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                //InputStream in = new java.net.URL(urldisplay).openStream(); // 從網址上下載
                //mIcon11 = BitmapFactory.decodeStream(in);

                URL url = new URL(urldisplay);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                mIcon11 = BitmapFactory.decodeStream(input);


            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result); // 下載完成後載入結果
        }
    }

    private class DataDef {
        public DataDef() {

        }

        public String img_url;
        public String intro;
        public int price;

    }
}
