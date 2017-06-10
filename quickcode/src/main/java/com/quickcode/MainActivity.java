package com.quickcode;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

//http://blog.csdn.net/w1850461829/article/details/72911199  参考这篇博客
public class MainActivity extends AppCompatActivity {
    private Button button;
    private Button buttonImage;
    private Button buttonCreateImage;
    private EditText textView;
    private ImageView imageView;
    private static final int RESULT_OK = 0;
    private static final int REQUEST_IMAGE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ZXingLibrary.initDisplayOpinion(this);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.scan);
        buttonImage = (Button) findViewById(R.id.image);
        buttonCreateImage = (Button) findViewById(R.id.create_image);
        textView = (EditText) findViewById(R.id.tv);
        imageView = (ImageView) findViewById(R.id.imageView);
        setListener();
    }

    private void setListener() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MainActivity.this, CaptureActivity.class),RESULT_OK);
            }
        });
        buttonImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_IMAGE);
            }
        });
        buttonCreateImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textContent = "我是梁超杰";
                Bitmap mBitmap = CodeUtils.createImage(textContent, 400, 400, BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
                imageView.setImageBitmap(mBitmap);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_OK) {
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    textView.setText(result);
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    textView.setText("解析失败!");
                }
            }
        }
        if (requestCode == REQUEST_IMAGE) {
            if (data != null) {
                Uri uri = data.getData();
                ContentResolver cr = getContentResolver();
                try {
                    Bitmap mBitmap = MediaStore.Images.Media.getBitmap(cr, uri);//显得到bitmap图片

                    CodeUtils.analyzeBitmap(getPath(mBitmap), new CodeUtils.AnalyzeCallback() {
                        @Override
                        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
                            Toast.makeText(MainActivity.this, "解析结果:" + result, Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onAnalyzeFailed() {
                            Toast.makeText(MainActivity.this, "解析二维码失败", Toast.LENGTH_LONG).show();
                        }
                    });

                    if (mBitmap != null) {
                        mBitmap.recycle();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    //获取Bitmap的文件地址
    public String getPath(Bitmap bm) throws IOException {
        String path = Environment.getExternalStorageDirectory()+"/quickCode/";
        File dirFile = new File(path);
        File file = new File(path+"h.png");
        if(!dirFile.exists()){
            dirFile.mkdirs();
        }
        if(!file.exists()){
            file.createNewFile();
        }
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        bos.flush();
        bos.close();
        return path+"h.png";
    }
}
