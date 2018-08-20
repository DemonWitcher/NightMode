package com.witcher.nightmode;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(NightUtil.isNightMode(this)){
            setTheme(R.style.NightTheme);
        }else{
            setTheme(R.style.DayTheme);
        }
        setContentView(R.layout.activity_detail);
    }
}
