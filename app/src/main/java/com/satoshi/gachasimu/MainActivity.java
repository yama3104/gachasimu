package com.satoshi.gachasimu;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, "ca-app-pub-6378485568392983~7425334460");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("ガチャリスト");

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(this);

        ListView listView = findViewById(R.id.listView1);
        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(this);

        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }


    @Override
    protected void onResume(){
        super.onResume();

        ListView listView = findViewById(R.id.listView1);
        Realm.init(this);
        Realm realm = Realm.getDefaultInstance();
        //GachaModel model = null;
        RealmQuery<GachaModel> query = realm.where(GachaModel.class);
        RealmResults<GachaModel> results = query.findAll();
        String[] gachaLists = new String[results.size()];

        for(int i=0; i<results.size(); i++){
            gachaLists[i] = results.get(i).getName();
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, gachaLists);
        listView.setAdapter(arrayAdapter);
    }

    public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this, AddGachaActivity.class);
        startActivity(intent);
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent1 = new Intent(MainActivity.this, DrawGachaActivity.class);
        intent1.putExtra("position", i);

        startActivity(intent1);
    }


    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {

        final Realm realm = Realm.getDefaultInstance();
        RealmQuery<GachaModel> query = realm.where(GachaModel.class);
        final RealmResults<GachaModel> results = query.findAll();

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("");
        alert.setMessage("このガチャを削除しますか？");
        alert.setPositiveButton("削除", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which) {
                //Yesボタンが押された時の処理

                realm.beginTransaction();
                results.deleteFromRealm(i);
                realm.commitTransaction();
                onResume();

                Toast.makeText(MainActivity.this, "削除しました", Toast.LENGTH_SHORT).show();
            }});
        alert.setNegativeButton("キャンセル", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which) {
                //Noボタンが押された時の処理
                //Toast.makeText(MainActivity.this, "No Clicked!", Toast.LENGTH_LONG).show();
            }});
        alert.show();

        return true;
    }
}

