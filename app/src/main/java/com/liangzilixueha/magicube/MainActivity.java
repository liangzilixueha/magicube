package com.liangzilixueha.magicube;


import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.liangzilixueha.magicube.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "csw";
    private static final int REQUEST_CODE_CAMERA = 1;
    private ActivityMainBinding binding;

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CAMERA) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            //bitmap = addPoint(bitmap);
            binding.preview.setImageBitmap(bitmap);
            colorIdentify(bitmap, width * height / 2);
        }
    }

    private Bitmap addPoint(Bitmap bitmap) {
        /*在正中间添加白点*/
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] pixels = new int[width * height];
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
        int point = 0xffffffff;
        int pointX = width / 2;
        int pointY = height / 2;
        pixels[pointY * width + pointX] = point;
        Bitmap newBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        newBitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return newBitmap;
    }

    private int[] colorIdentify(Bitmap bitmap, int position) {
        /*识别bitmap最中心的颜色*/
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] pixels = new int[width * height];
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);
        int center = pixels[position / 2];
        int red = (center & 0x00ff0000) >> 16;
        int green = (center & 0x0000ff00) >> 8;
        int blue = (center & 0x000000ff);
        return new int[]{red, green, blue};
        /*识别bitmap最中心的颜色*/

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnTake.setOnClickListener(v -> {
            takePhoto();
        });
    }

    private void takePhoto() {
        try {
            /*
            打开相机，保存拍摄图片到相册
             */
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, REQUEST_CODE_CAMERA);
        } catch (Exception e) {
            Log.d(TAG, e.toString());
        }
    }
}