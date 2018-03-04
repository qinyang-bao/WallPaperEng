package com.edward.wallpaperengine;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.SharedPreferences;
import android.view.View;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import java.io.IOException;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSIONS_REQUEST_CAMERA = 454;
    static final String PERMISSION_CAMERA = Manifest.permission.CAMERA;
    private Context mContext;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    private GestureDetector myGestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;

        sharedPref = getApplicationContext().getSharedPreferences("mySharedPref", Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        AwesomeGesture gesture = new AwesomeGesture();
        myGestureDetector = new GestureDetector(MainActivity.this, gesture);

        checkSelfPermission();

    }

    class AwesomeGesture implements GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {

        @Override
        public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
            return false;
        }

        @Override
        public boolean onDoubleTap(MotionEvent motionEvent) {
            return false;
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent motionEvent) {
            WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());
            try {
                wallpaperManager.clear();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        public boolean onDown(MotionEvent motionEvent) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent motionEvent) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent motionEvent) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent motionEvent) {

        }

        @Override
        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
            return false;
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        myGestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public void onStart() {
        super.onStart();
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onResume() {
        super.onResume();
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onRestart() {
        super.onRestart();
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onPause() {
        Intent intent = new Intent(this, ShakeDetectService.class);
        startService(intent);
        super.onPause();
    }

    @Override
    public void onStop() {
        Intent intent = new Intent(this, ShakeDetectService.class);
        startService(intent);
        super.onStop();
    }

    @Override
    public void onDestroy() {
        Intent intent = new Intent(this, ShakeDetectService.class);
        startService(intent);
        super.onDestroy();
    }

    void checkSelfPermission() {
        if (ContextCompat.checkSelfPermission(mContext, PERMISSION_CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{PERMISSION_CAMERA},
                    PERMISSIONS_REQUEST_CAMERA);
        } else {

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSIONS_REQUEST_CAMERA: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                } else {

                }
                return;
            }
        }
    }

    public void onColorClick(View view) {

        WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());
        try {
            wallpaperManager.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ComponentName component = new ComponentName(getPackageName(), getPackageName() + ".CameraWallpaper");
        Intent intent = new Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
        intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT, component);
        startActivityForResult(intent, 4);
        finish();
    }

    public void onGrayClick(View view) {

        WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());
        try {
            wallpaperManager.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ComponentName component = new ComponentName(getPackageName(), getPackageName() + ".CameraWallpaperFront");
        Intent intent = new Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
        intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT, component);
        startActivityForResult(intent, 4);
        finish();
    }

    /*
    void startWallpaper() {

        WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());
        try {
            wallpaperManager.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ComponentName component = new ComponentName(getPackageName(), getPackageName() + ".CameraWallpaper");
        Intent intent = new Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
        intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT, component);
        startActivityForResult(intent, 4);
        finish();

    }
    */

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ((requestCode == 4) && (resultCode == RESULT_OK)) {
            Intent intent = new Intent(this, ShakeDetectService.class);
            startService(intent);
            setContentView(R.layout.activity_main);
            //finish();
        }
    }

}
