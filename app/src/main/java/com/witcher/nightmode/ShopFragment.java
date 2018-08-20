package com.witcher.nightmode;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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

public class ShopFragment extends Fragment{

    private Activity activity;
    private TextView tvTo,tvContent;
    private RelativeLayout rlBg;

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
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_shop,container,false);
        tvTo = view.findViewById(R.id.tv_to);
        tvContent = view.findViewById(R.id.tv_content);
        rlBg = view.findViewById(R.id.rl_bg);
        tvTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity,DetailActivity.class);
                activity.startActivity(intent);
            }
        });
        return view;
    }

    public void notifyUI(){
        TypedValue allBg = new TypedValue();
        TypedValue tvColor = new TypedValue();

        Resources.Theme theme = activity.getTheme();
        theme.resolveAttribute(R.attr.tv_color,tvColor,true);
        theme.resolveAttribute(R.attr.all_bg_color,allBg,true);

        tvTo.setTextColor(getResources().getColor(tvColor.resourceId));
        tvContent.setTextColor(getResources().getColor(tvColor.resourceId));
        rlBg.setBackgroundResource(allBg.resourceId);
    }

}
