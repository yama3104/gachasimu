package com.satoshi.gachasimu;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    final String[] items = {"楽器", "デザート", "文房具"};
    Button setting_img_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("設定");
        actionBar.setDisplayHomeAsUpEnabled(true);

        setting_img_btn = findViewById(R.id.setting_img_btn);
        setting_img_btn.setOnClickListener(this);
    }

    @Override
    protected void onResume(){
        super.onResume();

        setting_img_btn = findViewById(R.id.setting_img_btn);

        SharedPreferences data = getSharedPreferences("SettingSave", Context.MODE_PRIVATE);
        int category = data.getInt("imgCategory",0);
        setting_img_btn.setText(items[category]);


    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setting_img_btn:

                AlertDialog.Builder listDlg = new AlertDialog.Builder(this);
                listDlg.setItems(
                        items,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // リスト選択時の処理
                                // id は、選択されたアイテムのインデックス

                                SharedPreferences data = getSharedPreferences("SettingSave", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = data.edit();
                                editor.putInt("imgCategory", id);
                                editor.apply();
                                setting_img_btn.setText(items[id]);
                            }
                        });
                listDlg.create().show();
                break;
        }

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
