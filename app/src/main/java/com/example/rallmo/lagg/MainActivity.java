package com.example.rallmo.lagg;

import com.google.android.glass.media.Sounds;
import com.google.android.glass.view.WindowUtils;
import com.google.android.glass.widget.CardBuilder;
import com.google.android.glass.widget.CardScrollAdapter;
import com.google.android.glass.widget.CardScrollView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import com.google.android.glass.touchpad.Gesture;
import com.google.android.glass.touchpad.GestureDetector;



public class MainActivity extends Activity {

    /**
     * {@link CardScrollView} to use as the main content view.
     */
    private CardScrollView mCardScroller;

    private View mView;
    private GestureDetector mGestureDetector;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        mView = buildView();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().requestFeature(WindowUtils.FEATURE_VOICE_COMMANDS);
        mCardScroller = new CardScrollView(this);
        mCardScroller.setAdapter(new CardScrollAdapter() {
            @Override
            public int getCount() {
                return 1;
            }

            @Override
            public Object getItem(int position) {
                return mView;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                return mView;
            }

            @Override
            public int getPosition(Object item) {
                if (mView.equals(item)) {
                    return 0;
                }
                return AdapterView.INVALID_POSITION;
            }
        });
        // Handle the TAP event.
        mCardScroller.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openOptionsMenu();
            }
        });
        mGestureDetector = createGestureDetector(this);
        setContentView(mCardScroller);
    }
    public void patientMenu(String input){

    }
    public void mapViewMenu(){
        Intent resultsIntent = new Intent(this, StaticMapActivity.class);
        startActivity(resultsIntent);
    }
    public void notificationMenu(){

    }
    private GestureDetector createGestureDetector(Context context){
        GestureDetector gestureDetector = new GestureDetector(context);

        Log.d("TAG","GestureControll");
        gestureDetector.setBaseListener(new GestureDetector.BaseListener() {
            @Override
            public boolean onGesture(Gesture gesture) {
                if(gesture == Gesture.TAP){
                    Log.d("TAG","One tap");
                    openOptionsMenu();

                    return true;
                }else if(gesture == Gesture.TWO_TAP){
                    return true;
                }else if(gesture == Gesture.SWIPE_LEFT){
                    return true;
                }else if(gesture == Gesture.SWIPE_RIGHT){
                    return true;
                }else if(gesture == Gesture.SWIPE_DOWN){
                    finish();
                }
                return false;
            }
        });

        gestureDetector.setFingerListener(new GestureDetector.FingerListener(){
           @Override
        public void onFingerCountChanged(int previousCount, int currentCount){

           }
        });

        gestureDetector.setScrollListener(new GestureDetector.ScrollListener(){
            @Override
            public boolean onScroll(float displacement, float delta, float velocity){
                return true;
            }
        });

        return gestureDetector;
    }
    @Override
    public boolean onGenericMotionEvent(MotionEvent event){
        if(mGestureDetector !=null){
            return mGestureDetector.onMotionEvent(event);
        }
        return false;
    }
    @Override
    public boolean onCreatePanelMenu(int featureId, Menu menu){
        try{
            if(featureId == WindowUtils.FEATURE_VOICE_COMMANDS || featureId == Window.FEATURE_OPTIONS_PANEL){
                getMenuInflater().inflate(R.menu.main, menu);
                Log.d("TAG", "OkVoice commands");
                return true;
            }
        }catch (Exception e){
            Log.d("TAG","EXCEPTION:",e.fillInStackTrace());
        }
        return super.onCreatePanelMenu(featureId,menu);
    }
    @Override
     public boolean onMenuItemSelected(int featureId, MenuItem item){
        Log.d("TAG","OnMenuItemSelected");
        if(featureId==WindowUtils.FEATURE_VOICE_COMMANDS||featureId==Window.FEATURE_OPTIONS_PANEL){
            switch (item.getItemId()) {
                case R.id.notification_menu:
                    notificationMenu();
                    break;
                case R.id.map_viewer:
                    mapViewMenu();
                    break;
                case R.id.patient_info:
                    patientMenu("Name");
                    break;
            }
            return true;
        }
        return super.onMenuItemSelected(featureId,item);
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

    /**
     * Builds a Glass styled "Hello World!" view using the {@link CardBuilder} class.
     */
    private View buildView() {
        CardBuilder card = new CardBuilder(this, CardBuilder.Layout.TEXT);

        card.setText(R.string.app_name);
        card.addImage(R.drawable.ic_glass_logo);

        return card.getView();
    }

}
