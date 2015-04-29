package com.example.rallmo.lagg;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.InputStream;


public class StaticMapActivity extends Activity {
    private static final String TAG = StaticMapActivity.class.getSimpleName();

    private static final String STATIC_MAP_URL_TEMPLATE =
            "https://maps.googleapis.com/maps/api/staticmap"
                    + "?center=%.5f,%.5f"
                    + "&zoom=%d"
                    + "&sensor=true"
                    + "&size=640x360"
                    + "&scale=1"
                    + "&style=element:geometry%%7Cinvert_lightness:true"
                    + "&style=feature:landscape.natural.terrain%%7Celement:geometry%%7Cvisibility:on"
                    + "&style=feature:landscape%%7Celement:geometry.fill%%7Ccolor:0x303030"
                    + "&style=feature:poi%%7Celement:geometry.fill%%7Ccolor:0x404040"
                    + "&style=feature:poi.park%%7Celement:geometry.fill%%7Ccolor:0x0a330a"
                    + "&style=feature:water%%7Celement:geometry%%7Ccolor:0x00003a"
                    + "&style=feature:transit%%7Celement:geometry%%7Cvisibility:on%%7Ccolor:0x101010"
                    + "&style=feature:road%%7Celement:geometry.stroke%%7Cvisibility:on"
                    + "&style=feature:road.local%%7Celement:geometry.fill%%7Ccolor:0x606060"
                    + "&style=feature:road.arterial%%7Celement:geometry.fill%%7Ccolor:0x888888";

    /** Formats a Google static maps URL for the specified location and zoom level. */
    private static String makeStaticMapsUrl(double latitude, double longitude, int zoom) {
        return String.format(STATIC_MAP_URL_TEMPLATE, latitude, longitude, zoom);
    }

    private ImageView mMapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMapView = new ImageView(this);
        setContentView(mMapView);

        loadMap(37.8019, -122.4189, 18);
    }
    /** Load the map asynchronously and populate the ImageView when it's loaded. */
    private void loadMap(double latitude, double longitude, int zoom) {
        String url = makeStaticMapsUrl(latitude, longitude, zoom);
        new AsyncTask<String, Void, Bitmap>() {
            @Override
            protected Bitmap doInBackground(String... urls) {
                try {
                    HttpResponse response = new DefaultHttpClient().execute(new HttpGet(urls[0]));
                    InputStream is = response.getEntity().getContent();
                    return BitmapFactory.decodeStream(is);
                } catch (Exception e) {

                    Log.e(TAG, "Failed to load image", e);
                    return null;
                }
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                if (bitmap != null) {
                    mMapView.setImageBitmap(bitmap);
                }
            }
        }.execute(url);
    }


    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_static_map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}
