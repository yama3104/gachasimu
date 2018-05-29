package com.satoshi.gachasimu;

import io.realm.RealmObject;

/**
 * Created by satoshi on 2018/05/29.
 */

public class SettingModel extends RealmObject {

    private int img_category;

    public int getImg_category() {return img_category;}
    public void setImg_category(int img_category) {this.img_category = img_category;}
}
