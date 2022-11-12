package com.liangzilixueha.magicube;

import static com.liangzilixueha.magicube.MainActivity.getRealFilePathFromUri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.gzuliyujiang.colorpicker.ColorGradientView;
import com.github.gzuliyujiang.colorpicker.ColorPicker;
import com.github.gzuliyujiang.colorpicker.OnColorPickedListener;
import com.liangzilixueha.magicube.databinding.ActivityColorCalibrationBinding;
import com.permissionx.guolindev.PermissionX;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ColorCalibration extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "csw";
    private static final int START_CAMERA = 1;
    private ActivityColorCalibrationBinding binding;
    private List<TextView> imageViews = new ArrayList<>();
    private TextView 被选中的颜色;
    private SharedPreferences sp;
    private ColorPicker colorPicker;
    private File photoFile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_calibration);
        binding = ActivityColorCalibrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //初始化
        sp = getSharedPreferences(FILE.COLOR_CALIBRATION, MODE_PRIVATE);
        binding.takePhoto.setOnClickListener(view -> {
            gotoCamera();
        });
        imageViews.add(binding.red);
        imageViews.add(binding.green);
        imageViews.add(binding.blue);
        imageViews.add(binding.yellow);
        imageViews.add(binding.white);
        imageViews.add(binding.orange);
        被选中的颜色 = binding.red;
        colorPicker = new ColorPicker(this);
        colorPicker.setOnColorPickListener(pickedColor -> {
            //pickedColor转为rgb
            RGB rgb = new RGB(pickedColor);
            被选中的颜色.setBackgroundColor(pickedColor);
            保存颜色(被选中的颜色.getId(), pickedColor);
        });
        for (int i = 0; i < imageViews.size(); i++) {
            int anInt;
            int rgb;
            switch (imageViews.get(i).getId()) {
                case R.id.red:
                    anInt = sp.getInt(FILE.RED, Color.RED);
                    rgb = Color.rgb(Color.red(anInt), Color.green(anInt), Color.blue(anInt));
                    imageViews.get(i).setBackgroundColor(rgb);
                    break;
                case R.id.green:
                    anInt = sp.getInt(FILE.GREEN, Color.GREEN);
                    rgb = Color.rgb(Color.red(anInt), Color.green(anInt), Color.blue(anInt));
                    imageViews.get(i).setBackgroundColor(rgb);
                    break;
                case R.id.blue:
                    anInt = sp.getInt(FILE.BLUE, Color.BLUE);
                    rgb = Color.rgb(Color.red(anInt), Color.green(anInt), Color.blue(anInt));
                    imageViews.get(i).setBackgroundColor(rgb);
                    break;
                case R.id.yellow:
                    anInt = sp.getInt(FILE.YELLOW, Color.YELLOW);
                    rgb = Color.rgb(Color.red(anInt), Color.green(anInt), Color.blue(anInt));
                    imageViews.get(i).setBackgroundColor(rgb);
                    break;
                case R.id.white:
                    anInt = sp.getInt(FILE.WHITE, Color.WHITE);
                    rgb = Color.rgb(Color.red(anInt), Color.green(anInt), Color.blue(anInt));
                    imageViews.get(i).setBackgroundColor(rgb);
                    break;
                case R.id.orange:
                    anInt = sp.getInt(FILE.ORANGE, Color.rgb(255, 165, 0));
                    rgb = Color.rgb(Color.red(anInt), Color.green(anInt), Color.blue(anInt));
                    imageViews.get(i).setBackgroundColor(rgb);
                    break;
            }
            imageViews.get(i).setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        被选中的颜色 = (TextView) view;
        colorPicker.show();
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
        if (requestCode == START_CAMERA && resultCode == RESULT_OK) {
            try {
                Intent intent = new Intent(this, ClipImage.class);
                intent.setData(Uri.fromFile(photoFile));
                startActivityForResult(intent, 2);
            } catch (Exception e) {
                Log.e(TAG, "onActivityResult: " + e);
            }
        } else if (requestCode == 2 && resultCode == RESULT_OK) {
            try {
                Uri uri = data.getData();
                if (uri == null) {
                    return;
                }
                String cropImagePath = getRealFilePathFromUri(getApplicationContext(), uri);
                Bitmap bitMap = BitmapFactory.decodeFile(cropImagePath);
                binding.iv.setImageBitmap(bitMap);
                获取图片颜色(bitMap);
            } catch (Exception e) {
                Log.e(TAG, "onActivityResult: " + e);
            }
        }
    }

    private void 获取图片颜色(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int p = 0;
        List<RGB> list = new ArrayList<>();
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
            list.add(new RGB(p));
        }
        RGB 平均RGB = new RGB(0, 0, 0);
        for (int i = 0; i < list.size(); i++) {
            平均RGB.Red += list.get(i).Red;
            平均RGB.Green += list.get(i).Green;
            平均RGB.Blue += list.get(i).Blue;
        }
        平均RGB.Red /= list.size();
        平均RGB.Green /= list.size();
        平均RGB.Blue /= list.size();
        Log.e(TAG, "获取图片颜色: " + 平均RGB.toString());
        被选中的颜色.setBackgroundColor(Color.rgb(平均RGB.Red, 平均RGB.Green, 平均RGB.Blue));
        保存颜色(被选中的颜色.getId(), 平均RGB.toInt());
    }

    public void 保存颜色(int id, int pickedColor) {
        Log.e(TAG, "保存颜色: " + new RGB(pickedColor).toString());
        switch (id) {
            case R.id.red:
                sp.edit().putInt(FILE.RED, pickedColor).apply();
                break;
            case R.id.green:
                sp.edit().putInt(FILE.GREEN, pickedColor).apply();
                break;
            case R.id.blue:
                sp.edit().putInt(FILE.BLUE, pickedColor).apply();
                break;
            case R.id.yellow:
                sp.edit().putInt(FILE.YELLOW, pickedColor).apply();
                break;
            case R.id.white:
                sp.edit().putInt(FILE.WHITE, pickedColor).apply();
                break;
            case R.id.orange:
                sp.edit().putInt(FILE.ORANGE, pickedColor).apply();
                break;
        }
    }
}