package com.satoshi.gachasimu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
//import android.support.v7.app.ActionBar;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.ActionBar;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class ResultActivity extends AppCompatActivity implements View.OnClickListener{

    int position, number, image_number;  // どのガチャか、何連か、どの画像を使うか
    static String name;
    static double prob1;
    static double prob2;
    static double prob3;
    static double prob4;
    static double prob5;
    static boolean fixed;

    TextView results_sum_tv;
    LinearLayout tRow1,tRow2;

    //ImageView imageView1,imageView2,imageView3,imageView4,imageView5,imageView6,imageView7,imageView8,imageView9,imageView10;
    //ImageView[] imageView = {imageView1,imageView2,imageView3,imageView4,imageView5,imageView6,imageView7,imageView8,imageView9,imageView10};

    ImageView[] imageView = new ImageView[10];
    ArrayList<ArrayList<String>> imagePaths;
    Bitmap[] resultbmps = new Bitmap[5];

    int result;
    int[] results_sum = new int[5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("結果");
        actionBar.setDisplayHomeAsUpEnabled(true);

        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        Button draw_btn = findViewById(R.id.draw_btn);
        draw_btn.setOnClickListener(this);

        imageView[0] = findViewById(R.id.imageView1);
        imageView[1] = findViewById(R.id.imageView2);
        imageView[2] = findViewById(R.id.imageView3);
        imageView[3] = findViewById(R.id.imageView4);
        imageView[4] = findViewById(R.id.imageView5);
        imageView[5] = findViewById(R.id.imageView6);
        imageView[6] = findViewById(R.id.imageView7);
        imageView[7] = findViewById(R.id.imageView8);
        imageView[8] = findViewById(R.id.imageView9);
        imageView[9] = findViewById(R.id.imageView10);


        Intent intent = getIntent();
        position = intent.getIntExtra("position",-1);
        //image_number = intent.getIntExtra("image_number", 0);
        number = intent.getIntExtra("number", 10);

        Realm realm = Realm.getDefaultInstance();
        RealmQuery<GachaModel> query = realm.where(GachaModel.class);
        RealmResults<GachaModel> results = query.findAll();

        name = results.get(position).getName();
        prob1 = results.get(position).getProb1();
        prob2 = results.get(position).getProb2();
        prob3 = results.get(position).getProb3();
        prob4 = results.get(position).getProb4();
        prob5 = results.get(position).getProb5();
        fixed = results.get(position).getFixed();

        // ガチャ画像を端末から読み込む
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        Gson gson = new Gson();
        imagePaths = gson.fromJson(pref.getString(getString(R.string.imgPaths_KEY),""), new TypeToken<ArrayList<ArrayList<String>>>(){}.getType());
        if(imagePaths == null) imagePaths = new ArrayList<>();
        int category = getIntent().getIntExtra("image_number", 0);
        if(category > 2){
            for(int i=0;i<5;i++) {
                InputStream input=null;
                try{
                    input = this.openFileInput(imagePaths.get(category-3).get(i+1));
                } catch (IOException e){
                    e.printStackTrace();
                }
                resultbmps[i] = BitmapFactory.decodeStream(input);
            }
        }


        drawGacha();
    }

    @Override
    public void onClick(View view) {
        drawGacha();
    }

    public int gacha(){
        double rand = Math.random()*100;

        if(0<rand && rand<prob1){
            return 1;
        } else if(prob1<rand && rand<=prob1+prob2){
            return 2;
        } else if(prob1+prob2<rand && rand<=prob1+prob2+prob3){
            return 3;
        } else if(prob1+prob2+prob3<rand && rand<=prob1+prob2+prob3+prob4){
            return 4;
        } else if(prob1+prob2+prob3+prob4<rand && rand<=prob1+prob2+prob3+prob4+prob5){
            return 5;
        } else return -1;
    }

    public void drawGacha(){

        for(int i=0; i<number; i++){
            result = gacha();
            if(i<10) setImage(imageView[i],result);
            results_sum[result-1]++;
        }

        results_sum_tv = findViewById(R.id.results_sum_tv);
        results_sum_tv.setText("☆1("+prob1+"%)    "+results_sum[0]+" 枚" +
                "\n☆2("+prob2+"%)    "+results_sum[1]+" 枚" +
                "\n☆3("+prob3+"%)    "+results_sum[2] +" 枚" +
                "\n☆4("+prob4+"%)    "+results_sum[3] +" 枚" +
                "\n☆5("+prob5+"%)    "+results_sum[4]+" 枚");

        if(number == 1) {
            tRow1 = findViewById(R.id.result_RL1);
            tRow2 = findViewById(R.id.result_RL2);
            tRow1.setVisibility(View.GONE);
            tRow2.setVisibility(View.GONE);
            for(int i=1; i<3; i++) imageView[i].setVisibility(View.GONE);
            imageView[9].setVisibility(View.INVISIBLE);


        }
    }

    //ガチャの結果に応じて画像をセットする
    public void setImage(ImageView iv, int i){
        //SharedPreferences data = getSharedPreferences("SettingSave", Context.MODE_PRIVATE);
        //int category = data.getInt("imgCategory",0);
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        Gson gson = new Gson();
        imagePaths = gson.fromJson(pref.getString(getString(R.string.imgPaths_KEY),""), new TypeToken<ArrayList<ArrayList<String>>>(){}.getType());
        if(imagePaths == null) imagePaths = new ArrayList<>();

        int category = getIntent().getIntExtra("image_number", 0);

        //Log.d("debug", ""+category+" "+imagePaths.size());

        if(category == 0) {
            if (i == 1) {
                iv.setImageResource(R.drawable.gakki1);
            } else if (i == 2) {
                iv.setImageResource(R.drawable.gakki2);
            } else if (i == 3) {
                iv.setImageResource(R.drawable.gakki3);
            } else if (i == 4) {
                iv.setImageResource(R.drawable.gakki4);
            } else if (i == 5) {
                iv.setImageResource(R.drawable.gakki5);
            } else {
                iv.setImageResource(R.drawable.error);
            }
        } else if(category == 1){
            if (i == 1) {
                iv.setImageResource(R.drawable.dessert1);
            } else if (i == 2) {
                iv.setImageResource(R.drawable.dessert2);
            } else if (i == 3) {
                iv.setImageResource(R.drawable.dessert3);
            } else if (i == 4) {
                iv.setImageResource(R.drawable.dessert4);
            } else if (i == 5) {
                iv.setImageResource(R.drawable.dessert5);
            } else {
                iv.setImageResource(R.drawable.error);
            }
        } else if(category == 2){
            if (i == 1) {
                iv.setImageResource(R.drawable.bunbougu1);
            } else if (i == 2) {
                iv.setImageResource(R.drawable.bunbougu2);
            } else if (i == 3) {
                iv.setImageResource(R.drawable.bunbougu3);
            } else if (i == 4) {
                iv.setImageResource(R.drawable.bunbougu4);
            } else if (i == 5) {
                iv.setImageResource(R.drawable.bunbougu5);
            } else {
                iv.setImageResource(R.drawable.error);
            }
        } else {
            //ユーザが登録した画像を使う
            if (i == 1) {
                iv.setImageBitmap(resultbmps[i-1]);
            } else if (i == 2) {
                iv.setImageBitmap(resultbmps[i-1]);
            } else if (i == 3) {
                iv.setImageBitmap(resultbmps[i-1]);
            } else if (i == 4) {
                iv.setImageBitmap(resultbmps[i-1]);
            } else if (i == 5) {
                iv.setImageBitmap(resultbmps[i-1]);
            } else {
                iv.setImageResource(R.drawable.error);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
