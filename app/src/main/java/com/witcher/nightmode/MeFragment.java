package com.witcher.nightmode;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MeFragment extends Fragment{

    private Activity activity;
    private TextView tvMode,tvMe,tvNightTitle;
    private RelativeLayout rlBg,rlNightBg;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (Activity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_me,container,false);
        tvMode = view.findViewById(R.id.tv_mode);
        tvMe = view.findViewById(R.id.tv_title);
        tvNightTitle = view.findViewById(R.id.tv_night_title);
        rlBg = view.findViewById(R.id.rl_bg);
        rlNightBg = view.findViewById(R.id.rl_night_bg);
        tvMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nightMode();
            }
        });
        if(NightUtil.isNightMode(activity)){
            tvMode.setText("关闭");
        }else{
            tvMode.setText("开启");
        }
        return view;
    }

    private void nightMode(){
        boolean isNightMode = NightUtil.isNightMode(activity);
        NightUtil.setNightMode(activity,!isNightMode);
        //当前activityUI切换
        if(isNightMode){
            activity.setTheme(R.style.DayTheme);
            tvMode.setText("开启");
        }else{
            activity.setTheme(R.style.NightTheme);
            tvMode.setText("关闭");
        }
//        activity.recreate();
        MainActivity mainActivity = (MainActivity) activity;
        mainActivity.notifyUI();
    }

    public void notifyUI() {
        TypedValue allBg = new TypedValue();
        TypedValue viewBg = new TypedValue();
        TypedValue tvColor = new TypedValue();

        Resources.Theme theme = activity.getTheme();
        theme.resolveAttribute(R.attr.tv_color,tvColor,true);
        theme.resolveAttribute(R.attr.all_bg_color,allBg,true);
        theme.resolveAttribute(R.attr.view_bg_color,viewBg,true);

        tvMode.setTextColor(getResources().getColor(tvColor.resourceId));
        tvMe.setTextColor(getResources().getColor(tvColor.resourceId));
        tvNightTitle.setTextColor(getResources().getColor(tvColor.resourceId));
        rlBg.setBackgroundResource(allBg.resourceId);
        rlNightBg.setBackgroundResource(viewBg.resourceId);
    }

}
