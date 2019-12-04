package com.satoshi.gachasimu;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
//import android.support.v7.app.ActionBar;
//import android.support.v7.app.AlertDialog;
//import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.ActionBar;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class SettingActivity extends AppCompatActivity
        implements View.OnClickListener, AdapterView.OnItemLongClickListener {

    final String[] items = {"Musical instrument", "Dessert", "Stationery"};
    Button setting_img_btn;

    ArrayList<ArrayList<String>> imagePaths;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("画像セットリスト");
        actionBar.setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = findViewById(R.id.setting_fab);
        fab.setOnClickListener(this);

        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        //setting_img_btn = findViewById(R.id.setting_img_btn);
        //setting_img_btn.setOnClickListener(this);
    }

    @Override
    protected void onResume(){
        super.onResume();

        //setting_img_btn = findViewById(R.id.setting_img_btn);
        /*
        SharedPreferences data = getSharedPreferences("SettingSave", Context.MODE_PRIVATE);
        int category = data.getInt("imgCategory",0);
        setting_img_btn.setText(items[category]);*/

        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        Gson gson = new Gson();
        imagePaths = gson.fromJson(pref.getString(getString(R.string.imgPaths_KEY),""), new TypeToken<ArrayList<ArrayList<String>>>(){}.getType());
        if(imagePaths == null) imagePaths = new ArrayList<>();

        String[] imgListItems = new String[imagePaths.size()+3];
        imgListItems[0] = "楽器";
        imgListItems[1] = "デザート";
        imgListItems[2] = "文房具";
        for(int i=0; i<imagePaths.size(); i++) imgListItems[i+3] = imagePaths.get(i).get(0);

        ListView listView = findViewById(R.id.image_listView);
        listView.setOnItemLongClickListener(this);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, imgListItems);
        listView.setAdapter(arrayAdapter);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setting_fab:
                Intent intent = new Intent(SettingActivity.this, RegiImageActivity.class);
                startActivity(intent);
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("");
        alert.setMessage("このガチャを削除しますか？");
        alert.setPositiveButton("削除", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which) {
                //Yesボタンが押された時の処理
                if(i<3) {
                    Toast.makeText(SettingActivity.this, "この画像セットは削除できません", Toast.LENGTH_SHORT).show();
                    return;
                }

                SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
                Gson gson = new Gson();
                imagePaths = gson.fromJson(pref.getString(getString(R.string.imgPaths_KEY),""), new TypeToken<ArrayList<ArrayList<String>>>(){}.getType());
                if(imagePaths == null) imagePaths = new ArrayList<>();
                Log.d("arraySize", ""+imagePaths.size());
                Log.d("arrayRowSize", ""+imagePaths.get(i-3).size());
                Log.d("fileDelete_i", ""+i);

                //file delete
                boolean r=false;
                for(int j=1; j<6; j++) {
                    r = deleteFile(imagePaths.get(i-3).get(j));
                    /*
                    File file = new File(imagePaths.get(i-3).get(j));
                    Log.d("filePath", ""+imagePaths.get(i-3).get(j));
                    if(!file.exists()) {
                        Log.d("fileDelete", "file not exists!!!");
                        //imagePaths.remove(i-3);
                        //break;
                    }
                    r = file.delete();*/
                    Log.d("fileDelete_j", ""+j);
                }
                imagePaths.remove(i-3);
                Log.d("fileDelete_r", ""+r);
                pref.edit().putString(getString(R.string.imgPaths_KEY), gson.toJson(imagePaths)).apply();
                onResume();
                if(r) Toast.makeText(SettingActivity.this, "削除しました", Toast.LENGTH_SHORT).show();
            }});
        alert.setNegativeButton("キャンセル", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which) {
                //Noボタンが押された時の処理
            }});
        alert.show();

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
