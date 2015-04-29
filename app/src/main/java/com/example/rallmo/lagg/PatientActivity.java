package com.example.rallmo.lagg;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.android.glass.view.WindowUtils;
import com.google.android.glass.widget.CardBuilder;
import com.google.android.glass.widget.CardScrollView;

import java.lang.Object;
import com.google.android.glass.touchpad.Gesture;
import com.google.android.glass.touchpad.GestureDetector;
import java.util.List;
import java.util.ArrayList;


public class PatientActivity extends Activity {
    public static final String PATIENTSEARCH = "Search";
    private String appPlatform="PatientInfo";
    private CardScrollView mCardScroller;
    private List<CardBuilder> mCards;
    private GestureDetector mGestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("Tag", "Inne i onCreate");
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().requestFeature(WindowUtils.FEATURE_VOICE_COMMANDS);
        mCardScroller = new CardScrollView(this);
        mCards = new ArrayList<CardBuilder>();

        /*Get the patient info from mainacitivity*/
        if(getIntent().hasExtra(PATIENTSEARCH)){
            Log.d("Tag", "hasExtra");
            appPlatform = getIntent().getStringExtra(PATIENTSEARCH);
        }
        listContent(appPlatform);
        mCardScroller.setAdapter(new PatientAdapter(mCards));
        mCardScroller.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openOptionsMenu();
            }
        });
        mGestureDetector = createGestureDetector(this);
        setContentView(mCardScroller);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_patient, menu);
        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item){
        Log.d("Tag", "Inne i menuitem");
        if(featureId == WindowUtils.FEATURE_VOICE_COMMANDS||featureId== Window.FEATURE_OPTIONS_PANEL){


            switch (item.getItemId()){
                case R.id.patient_med:
                    Toast.makeText(getApplicationContext(),"Med", Toast.LENGTH_LONG).show();
                    break;
                case R.id.patient_history:
                    Toast.makeText(getApplicationContext(),"History", Toast.LENGTH_LONG).show();
                    break;
                case R.id.go_back:
                    break;
            }
            return true;
        }
        return super.onMenuItemSelected(featureId,item);
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
    private GestureDetector createGestureDetector(Context context){
        GestureDetector gestureDetector = new GestureDetector(context);
        gestureDetector.setBaseListener(new GestureDetector.BaseListener() {
            @Override
            public boolean onGesture(Gesture gesture) {
                if(gesture == Gesture.TAP){
                    openOptionsMenu();
                    return true;
                }
                return false;
            }
        });
        gestureDetector.setFingerListener(new GestureDetector.FingerListener() {
            @Override
            public void onFingerCountChanged(int previousCount, int currentCount) {

            }
        });

        gestureDetector.setScrollListener(new GestureDetector.ScrollListener() {
            @Override
            public boolean onScroll(float displacement, float delta, float velocity) {

                return true;
            }
        });

        return  gestureDetector;

    }
    private void listContent(String platform){
        for(int i=0; i<10; i++){
            CardBuilder card = new CardBuilder(this,CardBuilder.Layout.COLUMNS);
            card.setText(platform +"" + Integer.toString(i));
            card.setTimestamp(platform);
            card.addImage(R.drawable.ic_stop);
            mCards.add(card);
        }
        mCardScroller.setSelection(0);
    }
    @Override
    protected void onResume() {
        super.onResume();
        mCardScroller.activate();
    }

    @Override
    protected void onPause() {
        mCardScroller.deactivate();
        super.onPause();
    }
}

