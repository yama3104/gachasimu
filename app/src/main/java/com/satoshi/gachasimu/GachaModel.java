package com.satoshi.gachasimu;

import io.realm.RealmObject;

/**
 * Created by satoshi on 2018/03/12.
 */

public class GachaModel extends RealmObject {

    private String name;
    private double prob1;
    private double prob2;
    private double prob3;
    private double prob4;
    private double prob5;
    private boolean fixed;
    private int fixedRange;
    private int least;


    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public double getProb1() {return prob1;}
    public void setProb1(double prob1) {this.prob1 = prob1;}

    public double getProb2() {return prob2;}
    public void setProb2(double prob2) {this.prob2 = prob2;}

    public double getProb3() {return prob3;}
    public void setProb3(double prob3) {this.prob3 = prob3;}

    public double getProb4() {return prob4;}
    public void setProb4(double prob4) {this.prob4 = prob4;}

    public double getProb5() {return prob5;}
    public void setProb5(double prob5) {this.prob5 = prob5;}

    public boolean getFixed() {return fixed;}
    public void setFixed(boolean fixed) {this.fixed = fixed;}

    public int getFixedRange() {return fixedRange;}
    public void setFixedRange(int fixedRange) {this.fixedRange = fixedRange;}

    public int getLeast() {return least;}
    public void setLeast(int least) {this.least = least;}
}
