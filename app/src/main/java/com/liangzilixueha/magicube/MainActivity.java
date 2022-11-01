package com.liangzilixueha.magicube;


import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.liangzilixueha.magicube.Solve.Search;
import com.liangzilixueha.magicube.databinding.ActivityMainBinding;
import com.permissionx.guolindev.PermissionX;

import java.io.File;
import java.security.Permission;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "csw";
    private static final int START_CAMERA = 1;
    private static final int START_CLIP = 2;
    private ActivityMainBinding binding;
    private List<ImageView> imageViews = new ArrayList<>();
    private List<TextView> textViews = new ArrayList<>(54);
    private ImageView 选中图片控件;
    private File photoFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
        for (int i = 0; i < imageViews.size(); i++) {
            int finalI = i;
            imageViews.get(i).setOnClickListener(view -> {
                选中图片控件 = imageViews.get(finalI);
                gotoCamera();
            });
        }
//        if (!Python.isStarted()) {
//            Python.start(new AndroidPlatform(this));
//        }
//        Python py = Python.getInstance();
        new Thread(() -> {
            //时间
            long start = System.currentTimeMillis();
            String path = "UBRLUFFUBLRUFRLLLRDBDRFDBBUDDBUDDLRFBFLDLBFFRFLRUBRDUU";
            String rel = Search.solution(path, 60, 5, false);
            //时间
            long end = System.currentTimeMillis();
            Log.d(TAG, "onCreate: " + rel);
            Log.d(TAG, "onCreate: " + (end - start) + "ms");
        }).start();
    }

    private void init() {
        imageViews.add(binding.uIv);
        imageViews.add(binding.lIv);
        imageViews.add(binding.fIv);
        imageViews.add(binding.rIv);
        imageViews.add(binding.bIv);
        imageViews.add(binding.dIv);

        textViews.add(binding.u1Tv);
        textViews.add(binding.u2Tv);
        textViews.add(binding.u3Tv);
        textViews.add(binding.u4Tv);
        textViews.add(binding.u5Tv);
        textViews.add(binding.u6Tv);
        textViews.add(binding.u7Tv);
        textViews.add(binding.u8Tv);
        textViews.add(binding.u9Tv);

        textViews.add(binding.l1Tv);
        textViews.add(binding.l2Tv);
        textViews.add(binding.l3Tv);
        textViews.add(binding.l4Tv);
        textViews.add(binding.l5Tv);
        textViews.add(binding.l6Tv);
        textViews.add(binding.l7Tv);
        textViews.add(binding.l8Tv);
        textViews.add(binding.l9Tv);

        textViews.add(binding.f1Tv);
        textViews.add(binding.f2Tv);
        textViews.add(binding.f3Tv);
        textViews.add(binding.f4Tv);
        textViews.add(binding.f5Tv);
        textViews.add(binding.f6Tv);
        textViews.add(binding.f7Tv);
        textViews.add(binding.f8Tv);
        textViews.add(binding.f9Tv);

        textViews.add(binding.r1Tv);
        textViews.add(binding.r2Tv);
        textViews.add(binding.r3Tv);
        textViews.add(binding.r4Tv);
        textViews.add(binding.r5Tv);
        textViews.add(binding.r6Tv);
        textViews.add(binding.r7Tv);
        textViews.add(binding.r8Tv);
        textViews.add(binding.r9Tv);

        textViews.add(binding.b1Tv);
        textViews.add(binding.b2Tv);
        textViews.add(binding.b3Tv);
        textViews.add(binding.b4Tv);
        textViews.add(binding.b5Tv);
        textViews.add(binding.b6Tv);
        textViews.add(binding.b7Tv);
        textViews.add(binding.b8Tv);
        textViews.add(binding.b9Tv);

        textViews.add(binding.d1Tv);
        textViews.add(binding.d2Tv);
        textViews.add(binding.d3Tv);
        textViews.add(binding.d4Tv);
        textViews.add(binding.d5Tv);
        textViews.add(binding.d6Tv);
        textViews.add(binding.d7Tv);
        textViews.add(binding.d8Tv);
        textViews.add(binding.d9Tv);
    }

    /*
     * 跳转到相机
     * 会保存拍摄下来的照片
     */
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
            /*
            拍照返回，跳转到裁剪界面
             */
            case START_CAMERA:
                gotoClipActivity(Uri.fromFile(photoFile));
                break;
            /*
            裁剪结束，显示图片
             */
            case START_CLIP:
                Uri uri = data.getData();
                if (uri == null) {
                    return;
                }
                String cropImagePath = getRealFilePathFromUri(getApplicationContext(), uri);
                Bitmap bitMap = BitmapFactory.decodeFile(cropImagePath);
                选中图片控件.setImageBitmap(bitMap);
                setColorText();
                break;
        }
    }

    private void setColorText() {
        Bitmap bitmap = ((BitmapDrawable) 选中图片控件.getDrawable()).getBitmap();
        int position = imageViews.indexOf(选中图片控件);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int p = 0;
        for (int i = 0; i < 9; i++) {
            switch (i) {
                case 0:
                    p = bitmap.getPixel(width / 6, height / 6);
                    break;
                case 1:
                    p = bitmap.getPixel(width / 2, height / 6);
                    break;
                case 2:
                    p = bitmap.getPixel(width * 5 / 6, height / 6);
                    break;
                case 3:
                    p = bitmap.getPixel(width / 6, height / 2);
                    break;
                case 4:
                    p = bitmap.getPixel(width / 2, height / 2);
                    break;
                case 5:
                    p = bitmap.getPixel(width * 5 / 6, height / 2);
                    break;
                case 6:
                    p = bitmap.getPixel(width / 6, height * 5 / 6);
                    break;
                case 7:
                    p = bitmap.getPixel(width / 2, height * 5 / 6);
                    break;
                case 8:
                    p = bitmap.getPixel(width * 5 / 6, height * 5 / 6);
                    break;
            }
            textViews.get(position * 9 + i).setText(new Pixel(Color.red(p), Color.green(p), Color.blue(p)).getColor());
        }
    }

    public static String getRealFilePathFromUri(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    public void gotoClipActivity(Uri uri) {
        if (uri == null) {
            return;
        }
        Intent intent = new Intent(this, ClipImage.class);
        intent.setData(uri);
        startActivityForResult(intent, START_CLIP);
    }
}