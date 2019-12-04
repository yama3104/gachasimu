package com.satoshi.gachasimu;

import android.content.Intent;
//import android.support.v7.app.ActionBar;
//import android.support.v7.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.ActionBar;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class DrawGachaActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {

    Intent intent;
    static int position;
    double prob1, prob2, prob3, prob4, prob5;
    static int n, spinner_number, image_number;
    TextView name_tv, prob1_tv, prob2_tv, prob3_tv, prob4_tv, prob5_tv, calc_tv1, calc_tv2, calc_result_tv;
    EditText custom_number, calc_number;
    Spinner spinner, img_spinner;
    private String spinnerItems[] = {"☆1", "☆2", "☆3", "☆4", "☆5",};

    ArrayList<ArrayList<String>> imagePaths;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw_gacha);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("ガチャ情報");
        actionBar.setDisplayHomeAsUpEnabled(true);

        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        name_tv = findViewById(R.id.textView);
        prob1_tv = findViewById(R.id.textView1);
        prob2_tv = findViewById(R.id.textView2);
        prob3_tv = findViewById(R.id.textView3);
        prob4_tv = findViewById(R.id.textView4);
        prob5_tv = findViewById(R.id.textView5);
        calc_tv1 = findViewById(R.id.calc_tv1);
        calc_tv2 = findViewById(R.id.calc_tv2);
        calc_result_tv = findViewById(R.id.calc_result_tv);
        calc_number = findViewById(R.id.calc_editText);
        Button draw1_btn = findViewById(R.id.draw1_btn);
        Button draw10_btn = findViewById(R.id.draw10_btn);
        Button draw100_btn = findViewById(R.id.draw100_btn);
        calc_number.addTextChangedListener(this);
        draw1_btn.setOnClickListener(this);
        draw10_btn.setOnClickListener(this);
        draw100_btn.setOnClickListener(this);

        spinner = findViewById(R.id.star_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinner_number = i+1;


                calc_result_tv.setText(calcProb(n, spinner_number)+"%");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // ガチャで使う画像セットを選択
        img_spinner = findViewById(R.id.image_spinner);

        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        Gson gson = new Gson();
        imagePaths = gson.fromJson(pref.getString(getString(R.string.imgPaths_KEY),""), new TypeToken<ArrayList<ArrayList<String>>>(){}.getType());
        if(imagePaths == null) imagePaths = new ArrayList<>();
        String[] imgSpinnerItems = new String[imagePaths.size()+3];
        imgSpinnerItems[0] = "楽器";
        imgSpinnerItems[1] = "デザート";
        imgSpinnerItems[2] = "文房具";
        for(int i=0; i<imagePaths.size(); i++) imgSpinnerItems[i+3] = imagePaths.get(i).get(0);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, imgSpinnerItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        img_spinner.setAdapter(adapter);
        img_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                image_number = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView){}
        });
    }

    @Override
    protected void onResume(){
        super.onResume();

        Intent intent = getIntent();
        position = intent.getIntExtra("position",-1);

        Realm realm = Realm.getDefaultInstance();
        RealmQuery<GachaModel> query = realm.where(GachaModel.class);
        RealmResults<GachaModel> results = query.findAll();


        String name = results.get(position).getName();
        prob1 = results.get(position).getProb1();
        prob2 = results.get(position).getProb2();
        prob3 = results.get(position).getProb3();
        prob4 = results.get(position).getProb4();
        prob5 = results.get(position).getProb5();
        boolean fixed = results.get(position).getFixed();
        name_tv.setText(name);
        prob1_tv.setText("☆1    "+prob1+"%");
        prob2_tv.setText("☆2    "+prob2+"%");
        prob3_tv.setText("☆3    "+prob3+"%");
        prob4_tv.setText("☆4    "+prob4+"%");
        prob5_tv.setText("☆5    "+prob5+"%");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_edit, menu);
        return true;
    }

    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.draw1_btn:
                intent = new Intent(DrawGachaActivity.this, ResultActivity.class);
                intent.putExtra("position", position);
                intent.putExtra("image_number", image_number);
                intent.putExtra("number", 1); //1回ってことを次のAvtivityに渡す
                startActivity(intent);
                break;

            case R.id.draw10_btn:
                intent = new Intent(DrawGachaActivity.this, ResultActivity.class);
                intent.putExtra("position", position);
                intent.putExtra("image_number", image_number);
                intent.putExtra("number", 10); //10連ってことを次のAvtivityに渡す
                startActivity(intent);
                break;

            case R.id.draw100_btn:
                custom_number = findViewById(R.id.editText6);

                if (custom_number.getText().toString().length() < 2) {
                    Toast.makeText(this, "10以上にしてください", Toast.LENGTH_SHORT).show();
                    break;
                } else {

                    int n = Integer.parseInt(custom_number.getText().toString());

                    intent = new Intent(DrawGachaActivity.this, ResultActivity.class);
                    intent.putExtra("position", position);
                    intent.putExtra("image_number", image_number);
                    intent.putExtra("number", n); //n連ってことを次のAvtivityに渡す
                    startActivity(intent);
                    break;
                }
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        String str = s.toString();
        if(str.length() == 0) n=0; else n = Integer.parseInt(str);

        calc_result_tv.setText(calcProb(n, spinner_number)+"%");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case android.R.id.home:
                finish();
                break;

            case R.id.menu_edit:
                intent = new Intent(DrawGachaActivity.this, EditGachaActivity.class);
                intent.putExtra("position", position);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //少なくとも一枚引く確率を求める
    public String calcProb(int n, double rarity){
        double least1_prob;

        if(n==0) return "";

        if(rarity == 1){
            least1_prob = 1-Math.pow(1-(prob1/100), n);
        } else if(rarity == 2){
            least1_prob = 1-Math.pow(1-(prob2/100), n);
        } else if(rarity == 3){
            least1_prob = 1-Math.pow(1-(prob3/100), n);
        } else if(rarity == 4){
            least1_prob = 1-Math.pow(1-(prob4/100), n);
        } else {
            least1_prob = 1-Math.pow(1-(prob5/100), n);
        }


        String s = String.format("%.5s", least1_prob*100);
        return s;


        //return least1_prob*100;

    }
}
