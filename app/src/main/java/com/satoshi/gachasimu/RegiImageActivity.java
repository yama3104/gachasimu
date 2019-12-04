package com.satoshi.gachasimu;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
//import android.support.v4.app.ActivityCompat;
//import android.support.v7.app.ActionBar;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.ActionBar;
import androidx.core.app.ActivityCompat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.theartofdev.edmodo.cropper.CropImage;

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
    CheckBox use_square_cb;
    Button btn1,btn2,btn3,btn4,btn5;
    ImageButton ib1,ib2,ib3,ib4,ib5;
    String imgSetName;
    Bitmap[] bitmaps = new Bitmap[5];
    ArrayList<ArrayList<String>> imagePaths;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regi_image);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setTitle("画像セット追加");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        img_name_edit = findViewById(R.id.img_name_editText);

        use_square_cb = findViewById(R.id.use_square_cb);
        use_square_cb.setOnClickListener(this);

        btn1 = findViewById(R.id.regi_btn1);
        btn2 = findViewById(R.id.regi_btn2);
        btn3 = findViewById(R.id.regi_btn3);
        btn4 = findViewById(R.id.regi_btn4);
        btn5 = findViewById(R.id.regi_btn5);

        ib1 = findViewById(R.id.imageView10);
        ib2 = findViewById(R.id.imageView20);
        ib3 = findViewById(R.id.imageView30);
        ib4 = findViewById(R.id.imageView40);
        ib5 = findViewById(R.id.imageView50);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
        btn5.setOnClickListener(this);

        ib1.setOnClickListener(this);
        ib2.setOnClickListener(this);
        ib3.setOnClickListener(this);
        ib4.setOnClickListener(this);
        ib5.setOnClickListener(this);
    }

    public void onClick(View v) {

        // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's file browser.
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");

        switch (v.getId()){
            case R.id.regi_btn1:
            case R.id.imageView10:
                startActivityForResult(intent,1001);
                break;
            case R.id.regi_btn2:
            case R.id.imageView20:
                startActivityForResult(intent,1002);
                break;
            case R.id.regi_btn3:
            case R.id.imageView30:
                startActivityForResult(intent,1003);
                break;
            case R.id.regi_btn4:
            case R.id.imageView40:
                startActivityForResult(intent,1004);
                break;
            case R.id.regi_btn5:
            case R.id.imageView50:
                startActivityForResult(intent,1005);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("debug", ""+requestCode);
        if (resultCode == Activity.RESULT_OK){
            Intent intent;
            //登録する画像が正方形かどうかでintentを変える
            if(use_square_cb.isChecked()){
                intent = CropImage.activity(data.getData()).setAspectRatio(1,1).getIntent(this);
            } else {
                intent = CropImage.activity(data.getData()).getIntent(this);
            }
            switch (requestCode){
                //Log.d("debug", ""+requestCode);
                //generate CropImage intent
                //setAspectRatio 無しでもレイアウトは崩れないが、ダサい
                case 1001:
                    startActivityForResult(intent,2001);
                    break;
                case 1002:
                    startActivityForResult(intent,2002);
                    break;
                case 1003:
                    startActivityForResult(intent,2003);
                    break;
                case 1004:
                    startActivityForResult(intent,2004);
                    break;
                case 1005:
                    startActivityForResult(intent,2005);
                    break;

                case 2001:
                    btn1.setVisibility(View.GONE);
                    ib1.setVisibility(View.VISIBLE);
                    CropImage.ActivityResult result = CropImage.getActivityResult(data);
                    Uri croppedUri = result.getUri();
                    try {
                        ParcelFileDescriptor pfDescriptor = getContentResolver().openFileDescriptor(croppedUri, "r");
                        if(pfDescriptor != null) {
                            FileDescriptor fileDescriptor = pfDescriptor.getFileDescriptor();
                            Bitmap bmp = BitmapFactory.decodeFileDescriptor(fileDescriptor);
                            bitmaps[0] = bmp;
                            pfDescriptor.close();
                            ib1.setImageBitmap(bmp);
                        }
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                    break;
                case 2002:
                    btn2.setVisibility(View.GONE);
                    ib2.setVisibility(View.VISIBLE);
                    result = CropImage.getActivityResult(data);
                    croppedUri = result.getUri();
                    try {
                        ParcelFileDescriptor pfDescriptor = getContentResolver().openFileDescriptor(croppedUri, "r");
                        if(pfDescriptor != null) {
                            FileDescriptor fileDescriptor = pfDescriptor.getFileDescriptor();
                            Bitmap bmp = BitmapFactory.decodeFileDescriptor(fileDescriptor);
                            bitmaps[1] = bmp;
                            pfDescriptor.close();
                            ib2.setImageBitmap(bmp);
                        }
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                    break;
                case 2003:
                    btn3.setVisibility(View.GONE);
                    ib3.setVisibility(View.VISIBLE);
                    result = CropImage.getActivityResult(data);
                    croppedUri = result.getUri();
                    try {
                        ParcelFileDescriptor pfDescriptor = getContentResolver().openFileDescriptor(croppedUri, "r");
                        if(pfDescriptor != null) {
                            FileDescriptor fileDescriptor = pfDescriptor.getFileDescriptor();
                            Bitmap bmp = BitmapFactory.decodeFileDescriptor(fileDescriptor);
                            bitmaps[2] = bmp;
                            pfDescriptor.close();
                            ib3.setImageBitmap(bmp);
                        }
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                    break;
                case 2004:
                    btn4.setVisibility(View.GONE);
                    ib4.setVisibility(View.VISIBLE);
                    result = CropImage.getActivityResult(data);
                    croppedUri = result.getUri();
                    try {
                        ParcelFileDescriptor pfDescriptor = getContentResolver().openFileDescriptor(croppedUri, "r");
                        if(pfDescriptor != null) {
                            FileDescriptor fileDescriptor = pfDescriptor.getFileDescriptor();
                            Bitmap bmp = BitmapFactory.decodeFileDescriptor(fileDescriptor);
                            bitmaps[3] = bmp;
                            pfDescriptor.close();
                            ib4.setImageBitmap(bmp);
                        }
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                    break;
                case 2005:
                    btn5.setVisibility(View.GONE);
                    ib5.setVisibility(View.VISIBLE);
                    result = CropImage.getActivityResult(data);
                    croppedUri = result.getUri();
                    try {
                        ParcelFileDescriptor pfDescriptor = getContentResolver().openFileDescriptor(croppedUri, "r");
                        if(pfDescriptor != null) {
                            FileDescriptor fileDescriptor = pfDescriptor.getFileDescriptor();
                            Bitmap bmp = BitmapFactory.decodeFileDescriptor(fileDescriptor);
                            bitmaps[4] = bmp;
                            pfDescriptor.close();
                            ib5.setImageBitmap(bmp);
                        }
                    } catch (IOException e){
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }

    public void saveBitmap(){
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        Gson gson = new Gson();
        imagePaths = gson.fromJson(pref.getString(getString(R.string.imgPaths_KEY),""), new TypeToken<ArrayList<ArrayList<String>>>(){}.getType());

        imgSetName = img_name_edit.getText().toString();
        for (int i = 0; i < 5; i++) {
            if (bitmaps[i] == null) {
                Toast.makeText(this, "☆" + (i + 1) + "の画像を選んでください" , Toast.LENGTH_SHORT).show();
                return;
            }
            if (imgSetName == null){
                Toast.makeText(this, "ガチャの名前を入力してください", Toast.LENGTH_SHORT).show();
                return;
            } else if(imgSetName.equals("楽器") || imgSetName.equals("デザート") || imgSetName.equals("文房具")){
                Toast.makeText(this, "この名前はすでに使われています", Toast.LENGTH_SHORT).show();
                return;
            } else if(nameExist(imgSetName, imagePaths)){
                Toast.makeText(this, "この名前はすでに使われています", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1777);
        } else {
            save();
            Log.d("nopermission", "nopermission");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1777: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // パーミッションが必要な処理
                    save();
                }
                break;
            }
        }
    }

    public void save(){
        ArrayList<String> path = new ArrayList<>();
        path.add(img_name_edit.getText().toString());

        for (int i = 0; i < 5; i++) {
            Date mDate = new Date();
            SimpleDateFormat fileNameDate = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.JAPAN);
            String fileName = fileNameDate.format(mDate) + "_"+i+".jpg";
            final String SAVE_DIR = "/Pictures/GachaSimu/";
            String filePath = Environment.getExternalStorageDirectory().getPath() + SAVE_DIR + fileName;
            String filePathNomedia = Environment.getExternalStorageDirectory().getPath() + SAVE_DIR + ".nomedia";
            Log.d("filePath", "" + filePath);

            //File file = new File(filePath);
            File fileNomedia = new File(filePathNomedia);

            try {
                /*
                if (!file.exists()) {
                    //boolean mkdir = file.getParentFile().mkdir();
                    boolean mkdir = file.createNewFile();
                    Log.d("mkdir", ""+mkdir);
                    Log.d("exists", ""+file.exists());
                }*/
                if (!fileNomedia.exists()) {
                    boolean mkdir_no = fileNomedia.createNewFile();
                    Log.d("mkdir_nomedia", ""+mkdir_no);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                //FileOutputStream out = new FileOutputStream(filePath);
                FileOutputStream out = this.openFileOutput(fileName, Context.MODE_PRIVATE);
                bitmaps[i].compress(Bitmap.CompressFormat.JPEG, 100, out);
                //out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            path.add(fileName);

            // save index
            ContentValues values = new ContentValues();
            ContentResolver contentResolver = getContentResolver();
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
            values.put(MediaStore.Images.Media.TITLE, fileName);
            values.put(MediaStore.Images.Media.DATA, filePath);
            values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());

            contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        }
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        Gson gson = new Gson();
        imagePaths = gson.fromJson(pref.getString(getString(R.string.imgPaths_KEY),""), new TypeToken<ArrayList<ArrayList<String>>>(){}.getType());
        if(imagePaths == null) imagePaths = new ArrayList<>();
        imagePaths.add(path);

        //sharedPreferenceに保存
        pref.edit().putString(getString(R.string.imgPaths_KEY), gson.toJson(imagePaths)).apply();

        finish();
    }

    //登録しようとする名前がすでに使用されているかどうか
    public boolean nameExist(String imgSetName, ArrayList<ArrayList<String>> imagePaths){
        if(imagePaths == null) return false;
        for(int i=0; i<imagePaths.size(); i++){
            if(imgSetName.equals(imagePaths.get(i).get(0))) return true;
        }
        return  false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;

            case R.id.menu_save:
                saveBitmap();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}