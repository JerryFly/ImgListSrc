package tw.idv.fly.imgsrc;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    ImageView myImageView;
    ListView lv_menu_item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //String imageFileURL = "http://net.jcin.com.tw/p1.jpg";
        //myImageView = (ImageView)findViewById(R.id.imageView);

        //new DownloadImageTask(myImageView).execute(imageFileURL); // 載入圖片

        lv_menu_item = (ListView) findViewById(R.id.listView);

        int images[] = {R.drawable.p1, R.drawable.p2, R.drawable.p3};
        String menu_name[] = {"炒飯", "炒麵", "牛肉麵"};

        ArrayList<HashMap<String, Object>> items = new ArrayList<HashMap<String, Object>>();

        for (int i = 0; i < images.length; i++) {
            HashMap<String, Object> menu_data = new HashMap<String, Object>();
            menu_data.put("pic", images[i]);
            menu_data.put("text", menu_name[i]);
            items.add(menu_data);
        }


        SimpleAdapter adapter = new SimpleAdapter(this, items,
                R.layout.menu_item,
                new String[]{"pic", "text"},
                new int[]{R.id.imageMenu, R.id.textMenuContext}
        );
        lv_menu_item.setAdapter(adapter);

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

}
