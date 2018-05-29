package com.satoshi.gachasimu;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {

    final String[] items = {"item1", "item2", "item3"};
    Button setting_img_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("設定");
        actionBar.setDisplayHomeAsUpEnabled(true);

        setting_img_btn = findViewById(R.id.setting_img_btn);
    }

    @Override
    protected void onResume(){
        super.onResume();

        setting_img_btn = findViewById(R.id.setting_img_btn);

        Realm realm = Realm.getDefaultInstance();
        RealmQuery<SettingModel> query = realm.where(SettingModel.class);
        RealmResults<SettingModel> results = query.findAll();

        int category = results.get(0).getImg_category();
        setting_img_btn.setText(""+category);
    }

    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.setting_img_btn:
                // リスト表示用のアラートダイアログ
                AlertDialog.Builder listDlg = new AlertDialog.Builder(this);
                listDlg.setTitle("タイトル");
                listDlg.setItems(
                        items,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // リスト選択時の処理
                                // which は、選択されたアイテムのインデックス

                                Realm realm = Realm.getDefaultInstance();
                                RealmQuery<SettingModel> query = realm.where(SettingModel.class);
                                RealmResults<SettingModel> results = query.findAll();
                                if(results.get(0).getImg_category() >0) {
                                    results.get(0).setImg_category(id);
                                    realm.commitTransaction();
                                } else {
                                    //Realm realm = Realm.getDefaultInstance();
                                    SettingModel model = null;

                                    realm.beginTransaction();
                                    model = realm.createObject(SettingModel.class);
                                    model.setImg_category(id);
                                    realm.commitTransaction();
                                }
                            }
                        });

                // 表示
                listDlg.create().show();
                break;
        }

    }
}
