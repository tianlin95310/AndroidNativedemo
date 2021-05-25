package com.tl.androidnativedemo.utils.ui;

public class DataSet {
    public double data1;
    public double data2;
    public double rate;

    // 在单柱图里会用到
    public String value;
    public String color;

    @Override
    public String toString()
    {
        return "DataSet{" +
                "data1=" + data1 +
                ", data2=" + data2 +
                ", rate=" + rate +
                '}';
    }
}