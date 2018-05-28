package com.satoshi.gachasimu;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import io.realm.Realm;

public class AddGachaActivity extends AppCompatActivity implements View.OnClickListener{

    double prob1,prob2,prob3,prob4,prob5;
    CheckBox chkbox;
    Spinner spinner, spinner1;
    private String spinnerItems[] = {"全範囲のどこか", "最後の一枠"};
    private String spinnerItems1[] = {"1", "2", "3", "4", "5"};
    int fixedRange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_gacha);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("ガチャ追加");
        actionBar.setDisplayHomeAsUpEnabled(true);

        /*
        chkbox = findViewById(R.id.checkBox);
        chkbox.setOnClickListener(this);


        spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner1 = findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spinnerItems1);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);
        */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public void onClick(View view) {}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()) {
            case android.R.id.home:
                finish();
                break;

            case R.id.menu_save:

                EditText name_edit = findViewById(R.id.editText);
                EditText prob1_edit = findViewById(R.id.editText1);
                EditText prob2_edit = findViewById(R.id.editText2);
                EditText prob3_edit = findViewById(R.id.editText3);
                EditText prob4_edit = findViewById(R.id.editText4);
                EditText prob5_edit = findViewById(R.id.editText5);
                chkbox = findViewById(R.id.checkBox);
                spinner = findViewById(R.id.spinner);
                spinner1 = findViewById(R.id.spinner1);

                String name = name_edit.getText().toString();
                String prob1_edtb = prob1_edit.getText().toString();
                String prob2_edtb = prob2_edit.getText().toString();
                String prob3_edtb = prob3_edit.getText().toString();
                String prob4_edtb = prob4_edit.getText().toString();
                String prob5_edtb = prob5_edit.getText().toString();
                //String fixedRange_st = spinner.getSelectedItem().toString();
                //int least = Integer.parseInt(spinner1.getSelectedItem().toString());

                if(prob1_edtb.length() != 0) prob1 = Double.parseDouble(prob1_edtb); else prob1 = 0;
                if(prob2_edtb.length() != 0) prob2 = Double.parseDouble(prob2_edtb); else prob2 = 0;
                if(prob3_edtb.length() != 0) prob3 = Double.parseDouble(prob3_edtb); else prob3 = 0;
                if(prob4_edtb.length() != 0) prob4 = Double.parseDouble(prob4_edtb); else prob4 = 0;
                if(prob5_edtb.length() != 0) prob5 = Double.parseDouble(prob5_edtb); else prob5 = 0;
                /*
                if(fixedRange_st.length() == 0) {
                    fixedRange = -1;
                } else if(fixedRange_st.equals("全範囲のどこか")){
                    fixedRange = 0;
                } else {
                    fixedRange = 1;
                }*/

                double sum = prob1+prob2+prob3+prob4+prob5;

                //Realm.init(this);
                Realm realm = Realm.getDefaultInstance();
                GachaModel model = null;

                if(name.length() != 0 && sum == 100) {
                    realm.beginTransaction();
                    model = realm.createObject(GachaModel.class);
                    model.setName(name);
                    model.setProb1(prob1);
                    model.setProb2(prob2);
                    model.setProb3(prob3);
                    model.setProb4(prob4);
                    model.setProb5(prob5);
                    //model.setFixed(chkbox.isChecked()); //trueだと確定枠がある
                    //model.setFixedRange(fixedRange);
                    //model.setLeast(least);
                    realm.commitTransaction();

                    this.finish();
                } else if(name.length() == 0){
                    Toast.makeText(this, "名前を入力してください", Toast.LENGTH_SHORT).show();
                } else if(sum != 100){
                    Toast.makeText(this, "確率の合計を100%にしてください", Toast.LENGTH_SHORT).show();
                }
        }
        return super.onOptionsItemSelected(item);
    }
}
