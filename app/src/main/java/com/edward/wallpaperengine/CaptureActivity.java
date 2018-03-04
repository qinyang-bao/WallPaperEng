package com.edward.wallpaperengine;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import android.view.View;

public class CaptureActivity extends AppCompatActivity {

    Camera camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture);

        camera = Camera.open();
    }

    private File getOutputMediaFile() throws IOException{

        System.out.println("T");
        String imageFileName = "JPEG_" + "food" + "_";
        File mediaStorageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File mediaFile = File.createTempFile(imageFileName, ".jpg", mediaStorageDir);
        System.out.println("A");

        System.out.println(mediaFile.getAbsolutePath());

        return mediaFile;
    }

    private PictureCallback mPicture = new PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            File pictureFile = null;
            System.out.println("S");
            try {
                pictureFile = getOutputMediaFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (pictureFile == null){
                return;
            }

            try {
                FileOutputStream fos = new FileOutputStream(pictureFile);
                fos.write(data);
                fos.close();
                System.out.println("B");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    public void onButtonClick(View view) {

        System.out.println("D");
        camera.takePicture(null, null, mPicture);
        System.out.println("C");

    }

}
