package com.witcher.nightmode;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    TextView tv1, tv2, tv3;
    FrameLayout flContent;
    LinearLayout llBottom;

    MainFragment mainFragment;
    ShopFragment shopFragment;
    MeFragment meFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (NightUtil.isNightMode(this)) {
            setTheme(R.style.NightTheme);
        } else {
            setTheme(R.style.DayTheme);
        }
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
        fragmentTransaction.add(R.id.fl_content, mainFragment);
        fragmentTransaction.add(R.id.fl_content, shopFragment);
        fragmentTransaction.add(R.id.fl_content, meFragment);
        fragmentTransaction.hide(shopFragment);
        fragmentTransaction.hide(meFragment);
        fragmentTransaction.commit();
    }

    private void initView() {
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        tv3 = findViewById(R.id.tv3);
        llBottom = findViewById(R.id.ll_bottom);
        flContent = findViewById(R.id.fl_content);
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);
    }

    private void switchFg(int index) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.hide(mainFragment);
        fragmentTransaction.hide(shopFragment);
        fragmentTransaction.hide(meFragment);
        if (index == 0) {
            fragmentTransaction.show(mainFragment);
        } else if (index == 1) {
            fragmentTransaction.show(shopFragment);
        } else {
            fragmentTransaction.show(meFragment);
        }
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tv1) {
            switchFg(0);
        } else if (view.getId() == R.id.tv2) {
            switchFg(1);
        } else if (view.getId() == R.id.tv3) {
            switchFg(2);
        }
    }

    public void notifyUI() {
        mainFragment.notifyUI();
        shopFragment.notifyUI();
        meFragment.notifyUI();
        notifyUIMyself();
    }

    private void notifyUIMyself() {
        TypedValue allBg = new TypedValue();
        TypedValue tvColor = new TypedValue();

        Resources.Theme theme = getTheme();
        theme.resolveAttribute(R.attr.tv_color, tvColor, true);
        theme.resolveAttribute(R.attr.all_bg_color, allBg, true);

        tv1.setTextColor(getResources().getColor(tvColor.resourceId));
        tv2.setTextColor(getResources().getColor(tvColor.resourceId));
        tv3.setTextColor(getResources().getColor(tvColor.resourceId));
        llBottom.setBackgroundResource(allBg.resourceId);
    }

}
