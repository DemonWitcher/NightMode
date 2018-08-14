package com.witcher.nightmode;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MeFragment extends Fragment{

    private Activity activity;
    private TextView tvMode;

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
        tvMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nightMode();
            }
        });
        return view;
    }

    private void nightMode(){
        boolean isNightMode = NightUtil.isNightMode(activity);
        NightUtil.setNightMode(activity,!isNightMode);
        //当前activityUI切换
    }

}
