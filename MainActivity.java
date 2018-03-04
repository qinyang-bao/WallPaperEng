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

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSIONS_REQUEST_CAMERA = 454;
    static final String PERMISSION_CAMERA = Manifest.permission.CAMERA;
    private Context mContext;
    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;

        sharedPref = getApplicationContext().getSharedPreferences("mySharedPref", Context.MODE_PRIVATE);
        editor = sharedPref.edit();

        checkSelfPermission();

    }

    public void onStart() {
        startWallpaper();
        super.onStart();
    }

    public void onResume() {
        startWallpaper();
        super.onResume();
    }

    public void onRestart() {
        startWallpaper();
        super.onRestart();
    }

    public void onPause() {
        Intent intent = new Intent(this, ShakeDetectService.class);
        startService(intent);
        super.onPause();
    }

    public void onStop() {
        Intent intent = new Intent(this, ShakeDetectService.class);
        startService(intent);
        super.onStop();
    }

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
            startWallpaper();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSIONS_REQUEST_CAMERA: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startWallpaper();

                } else {

                }
                return;
            }
        }
    }

    public void onStartClick(View view) {
        editor.putBoolean("isGrayscale", true);
    }

    public void onEndClick(View view) {
        editor.putBoolean("isGrayscale")
    }

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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ((requestCode == 4) && (resultCode == RESULT_OK)) {
            Intent intent = new Intent(this, ShakeDetectService.class);
            startService(intent);
            finish();
        }
    }

}
