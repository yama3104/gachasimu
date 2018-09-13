package com.satoshi.gachasimu;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class RegiImageActivity extends AppCompatActivity implements View.OnClickListener{

    EditText img_name_edit;
    Button btn1,btn2,btn3,btn4,btn5;
    ImageView iv1,iv2,iv3,iv4,iv5;
    CropImageView cropImageView;
    Bitmap[] bitmaps = new Bitmap[5];
    ArrayList<ArrayList<String>> imagePaths = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regi_image);

        img_name_edit = findViewById(R.id.img_name_editText);

        btn1 = findViewById(R.id.regi_btn1);
        btn2 = findViewById(R.id.regi_btn2);
        btn3 = findViewById(R.id.regi_btn3);
        btn4 = findViewById(R.id.regi_btn4);
        btn5 = findViewById(R.id.regi_btn5);
        Button btn_save = findViewById(R.id.save_btn);

        iv1 = findViewById(R.id.imageView10);
        iv2 = findViewById(R.id.imageView20);
        iv3 = findViewById(R.id.imageView30);
        iv4 = findViewById(R.id.imageView40);
        iv5 = findViewById(R.id.imageView50);
        cropImageView = findViewById(R.id.cropImageView);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);
        btn_save.setOnClickListener(this);
    }

    public void onClick(View v) {
        // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's file browser.
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");

        switch (v.getId()){
            case R.id.regi_btn1:
                startActivityForResult(intent,1001);
                break;
            case R.id.regi_btn2:
                startActivityForResult(intent,1002);
                break;
            case R.id.regi_btn3:
                startActivityForResult(intent,1003);
                break;
            case R.id.regi_btn4:
                startActivityForResult(intent,1004);
                break;
            case R.id.regi_btn5:
                startActivityForResult(intent,1005);
                break;
            case R.id.save_btn:
                try{
                    saveBitmap(bitmaps);
                } catch (IOException e){
                    e.printStackTrace();
                }

        }


    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("debug", ""+requestCode);
        if (resultCode == Activity.RESULT_OK){
            switch (requestCode){
                    //Log.d("debug", ""+requestCode);
                case 1001:
                    Intent intent = CropImage.activity(data.getData()).setAspectRatio(1,1).getIntent(this);
                    startActivityForResult(intent,2001);
                    break;
                case 1002:
                    intent = CropImage.activity(data.getData()).setAspectRatio(1,1).getIntent(this);
                    startActivityForResult(intent,2002);
                    break;
                case 1003:
                    intent = CropImage.activity(data.getData()).setAspectRatio(1,1).getIntent(this);
                    startActivityForResult(intent,2003);
                    break;
                case 1004:
                    intent = CropImage.activity(data.getData()).setAspectRatio(1,1).getIntent(this);
                    startActivityForResult(intent,2004);
                    break;
                case 1005:
                    intent = CropImage.activity(data.getData()).setAspectRatio(1,1).getIntent(this);
                    startActivityForResult(intent,2005);
                    break;

                case 2001:
                    btn1.setVisibility(View.GONE);
                    iv1.setVisibility(View.VISIBLE);
                    CropImage.ActivityResult result = CropImage.getActivityResult(data);
                    Uri croppedUri = result.getUri();
                    try {
                        ParcelFileDescriptor pfDescriptor = getContentResolver().openFileDescriptor(croppedUri, "r");
                        if(pfDescriptor != null) {
                            FileDescriptor fileDescriptor = pfDescriptor.getFileDescriptor();
                            Bitmap bmp = BitmapFactory.decodeFileDescriptor(fileDescriptor);
                            bitmaps[0] = bmp;
                            pfDescriptor.close();
                            iv1.setImageBitmap(bmp);
                        }
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                    break;
                case 2002:
                    btn2.setVisibility(View.GONE);
                    iv2.setVisibility(View.VISIBLE);
                    result = CropImage.getActivityResult(data);
                    croppedUri = result.getUri();
                    try {
                        ParcelFileDescriptor pfDescriptor = getContentResolver().openFileDescriptor(croppedUri, "r");
                        if(pfDescriptor != null) {
                            FileDescriptor fileDescriptor = pfDescriptor.getFileDescriptor();
                            Bitmap bmp = BitmapFactory.decodeFileDescriptor(fileDescriptor);
                            bitmaps[1] = bmp;
                            pfDescriptor.close();
                            iv2.setImageBitmap(bmp);
                        }
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                    break;
                case 2003:
                    btn3.setVisibility(View.GONE);
                    iv3.setVisibility(View.VISIBLE);
                    result = CropImage.getActivityResult(data);
                    croppedUri = result.getUri();
                    try {
                        ParcelFileDescriptor pfDescriptor = getContentResolver().openFileDescriptor(croppedUri, "r");
                        if(pfDescriptor != null) {
                            FileDescriptor fileDescriptor = pfDescriptor.getFileDescriptor();
                            Bitmap bmp = BitmapFactory.decodeFileDescriptor(fileDescriptor);
                            bitmaps[2] = bmp;
                            pfDescriptor.close();
                            iv3.setImageBitmap(bmp);
                        }
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                    break;
                case 2004:
                    btn4.setVisibility(View.GONE);
                    iv4.setVisibility(View.VISIBLE);
                    result = CropImage.getActivityResult(data);
                    croppedUri = result.getUri();
                    try {
                        ParcelFileDescriptor pfDescriptor = getContentResolver().openFileDescriptor(croppedUri, "r");
                        if(pfDescriptor != null) {
                            FileDescriptor fileDescriptor = pfDescriptor.getFileDescriptor();
                            Bitmap bmp = BitmapFactory.decodeFileDescriptor(fileDescriptor);
                            bitmaps[3] = bmp;
                            pfDescriptor.close();
                            iv4.setImageBitmap(bmp);
                        }
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                    break;
                case 2005:
                    btn5.setVisibility(View.GONE);
                    iv5.setVisibility(View.VISIBLE);
                    result = CropImage.getActivityResult(data);
                    croppedUri = result.getUri();
                    try {
                        ParcelFileDescriptor pfDescriptor = getContentResolver().openFileDescriptor(croppedUri, "r");
                        if(pfDescriptor != null) {
                            FileDescriptor fileDescriptor = pfDescriptor.getFileDescriptor();
                            Bitmap bmp = BitmapFactory.decodeFileDescriptor(fileDescriptor);
                            bitmaps[4] = bmp;
                            pfDescriptor.close();
                            iv5.setImageBitmap(bmp);
                        }
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                    break;


            }
        }


    }


    public void cropImage(Uri uri){
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("outputX", 5);
        intent.putExtra("outputY", 5);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 2001);
    }


    public void saveBitmap(Bitmap[] bitmapArray) throws IOException {
        for (int i = 0; i < 5; i++) {
            if (bitmapArray[i] == null) {
                Toast.makeText(this, "☆" + (i + 1) + "の画像を選んでください", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        ArrayList<String> path = new ArrayList<>();
        path.add(img_name_edit.getText().toString());
        for (int i = 0; i < 5; i++) {
            Date mDate = new Date();
            SimpleDateFormat fileNameDate = new SimpleDateFormat("yyyyMMdd_HHmmss_"+i);
            String fileName = fileNameDate.format(mDate) + ".jpg";
            final String SAVE_DIR = "/Pictures/GachaSimu/";
            String filePath =  Environment.getExternalStorageDirectory().getPath() + SAVE_DIR + fileName;
            Log.d("filePath", "" + filePath);

            File file = new File(filePath);

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1777);
            }

            try {
                if (!file.exists()) {
                    file.getParentFile().mkdir();
                    Log.d("mkdir", "directory made");
                }
            } catch (SecurityException e) {
                e.printStackTrace();
                throw e;
            }

            try {
                FileOutputStream out = new FileOutputStream(filePath);
                bitmapArray[i].compress(Bitmap.CompressFormat.JPEG, 100, out);
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw e;
            }

            path.add(filePath);

            // save index
            ContentValues values = new ContentValues();
            ContentResolver contentResolver = getContentResolver();
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
            values.put(MediaStore.Images.Media.TITLE, fileName);
            values.put("_data", filePath);
            contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        }
        imagePaths.add(path);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1777: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // パーミッションが必要な処理
                    ArrayList<String> path = new ArrayList<>();
                    path.add(img_name_edit.getText().toString());

                    for (int i = 0; i < 5; i++) {
                        Date mDate = new Date();
                        SimpleDateFormat fileNameDate = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.JAPAN);
                        String fileName = fileNameDate.format(mDate) + "_"+i+".jpg";
                        final String SAVE_DIR = "/Pictures/GachaSimu/";
                        String filePath = Environment.getExternalStorageDirectory().getPath() + SAVE_DIR + fileName;
                        Log.d("filePath", "" + filePath);

                        File file = new File(filePath);

                        try {
                            if (!file.exists()) {
                                file.getParentFile().mkdir();
                                Log.d("mkdir", "directory made");
                            }
                        } catch (SecurityException e) {
                            e.printStackTrace();
                            throw e;
                        }

                        try {
                            FileOutputStream out = new FileOutputStream(filePath);
                            bitmaps[i].compress(Bitmap.CompressFormat.JPEG, 100, out);
                            out.flush();
                            out.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        path.add(filePath);

                        // save index
                        ContentValues values = new ContentValues();
                        ContentResolver contentResolver = getContentResolver();
                        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
                        values.put(MediaStore.Images.Media.TITLE, fileName);
                        values.put("_data", filePath);
                        contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                    }
                    imagePaths.add(path);
                }
                break;
            }
        }
    }
}
