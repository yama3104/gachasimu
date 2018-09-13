package com.satoshi.gachasimu;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SettingImageActivity extends AppCompatActivity implements View.OnClickListener{

    String[][] items = new String[13][6];
    //ユーザが登録した画像のPathを格納する配列
    //{title, img1.path, img2.path, ... img5.path}の順に入れる
    final String[] itemsName = {"楽器", "デザート", "文房具"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_image);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(this);

        //for(int i=0; i<items.length; i++) items[i][0] = "a";
        for(int i=0; i<itemsName.length; i++) items[i][0] = itemsName[i];
    }

    @Override
    protected void onResume() {
        super.onResume();

        int index = 0;
        while(items[index][0] != null) index++;
        String[] itemsTitle = new String[index];
        for(int i=0; i < index; i++) itemsTitle[i] = items[i][0];

        ListView listView = findViewById(R.id.listView1);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, itemsTitle);
        listView.setAdapter(arrayAdapter);
    }

    public void onClick(View v) {
        Intent intent = new Intent(SettingImageActivity.this, RegiImageActivity.class);
        startActivity(intent);
    }
}
