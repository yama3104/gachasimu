package com.satoshi.gachasimu;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.math.BigDecimal;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class EditGachaActivity extends AppCompatActivity {

    double prob1, prob2, prob3, prob4, prob5;
    static int position;
    EditText name_edit, prob1_edit, prob2_edit, prob3_edit, prob4_edit, prob5_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_gacha);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("ガチャ編集");
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        position = intent.getIntExtra("position",-1);

        Realm realm = Realm.getDefaultInstance();
        RealmQuery<GachaModel> query = realm.where(GachaModel.class);
        RealmResults<GachaModel> results = query.findAll();

        String name = results.get(position).getName();
        double prob1 = results.get(position).getProb1();
        double prob2 = results.get(position).getProb2();
        double prob3 = results.get(position).getProb3();
        double prob4 = results.get(position).getProb4();
        double prob5 = results.get(position).getProb5();

        name_edit = findViewById(R.id.editText);
        prob1_edit = findViewById(R.id.editText1);
        prob2_edit = findViewById(R.id.editText2);
        prob3_edit = findViewById(R.id.editText3);
        prob4_edit = findViewById(R.id.editText4);
        prob5_edit = findViewById(R.id.editText5);

        name_edit.setText(name);
        prob1_edit.setText(String.valueOf(prob1));
        prob2_edit.setText(String.valueOf(prob2));
        prob3_edit.setText(String.valueOf(prob3));
        prob4_edit.setText(String.valueOf(prob4));
        prob5_edit.setText(String.valueOf(prob5));
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
                name_edit = findViewById(R.id.editText);
                prob1_edit = findViewById(R.id.editText1);
                prob2_edit = findViewById(R.id.editText2);
                prob3_edit = findViewById(R.id.editText3);
                prob4_edit = findViewById(R.id.editText4);
                prob5_edit = findViewById(R.id.editText5);

                String name = name_edit.getText().toString();
                String prob1_edtb = prob1_edit.getText().toString();
                String prob2_edtb = prob2_edit.getText().toString();
                String prob3_edtb = prob3_edit.getText().toString();
                String prob4_edtb = prob4_edit.getText().toString();
                String prob5_edtb = prob5_edit.getText().toString();

                if (prob1_edtb.length() != 0) prob1 = Double.parseDouble(prob1_edtb); else prob1 = 0;
                if (prob2_edtb.length() != 0) prob2 = Double.parseDouble(prob2_edtb); else prob2 = 0;
                if (prob3_edtb.length() != 0) prob3 = Double.parseDouble(prob3_edtb); else prob3 = 0;
                if (prob4_edtb.length() != 0) prob4 = Double.parseDouble(prob4_edtb); else prob4 = 0;
                if (prob5_edtb.length() != 0) prob5 = Double.parseDouble(prob5_edtb); else prob5 = 0;
                /*
                if(fixedRange_st.length() == 0) {
                    fixedRange = -1;
                } else if(fixedRange_st.equals("全範囲のどこか")){
                    fixedRange = 0;
                } else {
                    fixedRange = 1;
                }*/

                BigDecimal p1 = BigDecimal.valueOf(prob1);
                BigDecimal p2 = BigDecimal.valueOf(prob2);
                BigDecimal p3 = BigDecimal.valueOf(prob3);
                BigDecimal p4 = BigDecimal.valueOf(prob4);
                BigDecimal p5 = BigDecimal.valueOf(prob5);
                double sum = p1.add(p2).add(p3).add(p4).add(p5).doubleValue();

                final Realm realm = Realm.getDefaultInstance();
                RealmQuery<GachaModel> query = realm.where(GachaModel.class);
                final RealmResults<GachaModel> results = query.findAll();

                if (name.length() != 0 && sum == 100) {

                    realm.beginTransaction();
                    results.get(position).setName(name);
                    results.get(position).setProb1(prob1);
                    results.get(position).setProb2(prob2);
                    results.get(position).setProb3(prob3);
                    results.get(position).setProb4(prob4);
                    results.get(position).setProb5(prob5);
                    realm.commitTransaction();

                    this.finish();
                } else if (name.length() == 0) {
                    Toast.makeText(this, "名前を入力してください", Toast.LENGTH_SHORT).show();
                } else if (sum != 100) {
                    Toast.makeText(this, "確率の合計を100%にしてください", Toast.LENGTH_SHORT).show();
                }
        }
        return super.onOptionsItemSelected(item);
    }
}

