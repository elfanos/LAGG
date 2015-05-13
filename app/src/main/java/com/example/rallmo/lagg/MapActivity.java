package com.example.rallmo.lagg;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;

public class MapActivity extends Activity {
    GoogleMap map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        createMapView();
        addMarker();

       /* map =((MapFragment)getFragmentManager().findFragmentById(R.id.mapView)).getMap();

        LatLng loc = new LatLng(-33.867,151.206);

        map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLng(loc,13));
        map.addMarker(new MarkerOptions().title("Call from").snippet("Location navigate to").position(loc));*/
    }


    public void createMapView(){

        try{
            if(map == null){

                map = ((MapFragment)getFragmentManager().findFragmentById(R.id.mapView)).getMap();

                if(map == null){
                    Toast.makeText(getApplicationContext(), "Error creating map", Toast.LENGTH_SHORT).show();

                }
            }

        }catch(NullPointerException e){
            Log.e("MapApp", e.toString());
        }


    }

    public void addMarker(){

        if(map != null){
            map.addMarker(new MarkerOptions().position(new LatLng(0,0)).title("Emergency").draggable(true));


        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_map, menu);
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
    }
}
