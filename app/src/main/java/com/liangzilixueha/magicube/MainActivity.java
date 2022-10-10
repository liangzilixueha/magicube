package com.liangzilixueha.magicube;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.liangzilixueha.magicube.databinding.ActivityMainBinding;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private Camera mCamera;//相机


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        SurfaceView preview = binding.preview;
        //设置preview为相机预览画面
        preview.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                //打开相机
                mCamera = Camera.open();
                try {
                    //设置预览画面,旋转90度
                    mCamera.setPreviewDisplay(holder);
                    mCamera.setDisplayOrientation(90);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                //开始预览
                mCamera.startPreview();
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                //停止预览
                mCamera.stopPreview();
                //释放相机
                mCamera.release();
            }
        });
    }
}