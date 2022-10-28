package com.liangzilixueha.magicube;


import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.liangzilixueha.magicube.databinding.ActivityMainBinding;
import com.permissionx.guolindev.PermissionX;

import java.io.File;
import java.security.Permission;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "csw";
    private static final int START_CAMERA = 1;
    private static final int START_CLIP = 2;
    private ActivityMainBinding binding;
    private File photoFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.lLay.setOnClickListener(v -> {
            gotoCamera();
        });
    }

    private void gotoCamera() {
        PermissionX.init(this)
                .permissions(Manifest.permission.WRITE_EXTERNAL_STORAGE
                        , Manifest.permission.READ_EXTERNAL_STORAGE
                        , Manifest.permission.CAMERA)
                .onForwardToSettings((scope, deniedList) -> {
                    scope.showForwardToSettingsDialog(deniedList, "请在设置中打开相机权限"
                            , "确定"
                            , "取消");
                })
                .request((allGranted, grantedList, deniedList) -> {
                    if (allGranted) {
                        photoFile = new File(getExternalCacheDir(), "photo.jpg");
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        /*
                        保存拍下来的照片
                         */
                        try {
                            if (photoFile.exists()) {
                                photoFile.delete();
                            }
                            photoFile.createNewFile();
                        } catch (Exception e) {
                            Log.e(TAG, "gotoCamera: " + e);
                        }
                        try {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                Uri contentUri = FileProvider.getUriForFile(this
                                        , "com.liangzilixueha.magicube.fileprovider"
                                        , photoFile);
                                intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
                            } else {
                                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                            }
                        } catch (Exception e) {
                            Log.e(TAG, "gotoCamera: " + e);
                        }
                        startActivityForResult(intent, START_CAMERA);
                    } else {
                        Toast.makeText(this, "未开启相机权限", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case START_CAMERA:
                break;
            case START_CLIP:
                break;
        }
    }
}