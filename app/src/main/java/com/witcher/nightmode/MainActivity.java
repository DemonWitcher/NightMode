package com.witcher.nightmode;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView tv1,tv2,tv3;
    FrameLayout flContent;

    MainFragment mainFragment;
    ShopFragment shopFragment;
    MeFragment meFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initFg();
    }

    private void initFg() {
        mainFragment = new MainFragment();
        shopFragment = new ShopFragment();
        meFragment = new MeFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fl_content,mainFragment);
        fragmentTransaction.add(R.id.fl_content,shopFragment);
        fragmentTransaction.add(R.id.fl_content,meFragment);
        fragmentTransaction.hide(shopFragment);
        fragmentTransaction.hide(meFragment);
        fragmentTransaction.commit();
    }

    private void initView() {
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        tv3 = findViewById(R.id.tv3);
        flContent = findViewById(R.id.fl_content);
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);
    }

    private void switchFg(int index){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.hide(mainFragment);
        fragmentTransaction.hide(shopFragment);
        fragmentTransaction.hide(meFragment);
        if(index==0){
            fragmentTransaction.show(mainFragment);
        }else if(index == 1){
            fragmentTransaction.show(shopFragment);
        }else{
            fragmentTransaction.show(meFragment);
        }
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.tv1){
            switchFg(0);
        }else if(view.getId() == R.id.tv2){
            switchFg(1);
        }else if(view.getId() == R.id.tv3){
            switchFg(2);
        }
    }
}
