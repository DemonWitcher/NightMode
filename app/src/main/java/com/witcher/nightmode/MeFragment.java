package com.witcher.nightmode;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MeFragment extends Fragment {

    private Activity activity;
    private TextView tvMode, tvMe, tvNightTitle;
    private RelativeLayout rlBg, rlNightBg;

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
        View view = inflater.inflate(R.layout.fg_me, container, false);
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
        if (NightUtil.isNightMode(activity)) {
            tvMode.setText("关闭");
        } else {
            tvMode.setText("开启");
        }
        return view;
    }

    private void nightMode() {
        if (NightUtil.ANIM) {
            showAnimation();
        }
        boolean isNightMode = NightUtil.isNightMode(activity);
        NightUtil.setNightMode(activity, !isNightMode);
        //当前activityUI切换
        if (isNightMode) {
            activity.setTheme(R.style.DayTheme);
            tvMode.setText("开启");
        } else {
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
        theme.resolveAttribute(R.attr.tv_color, tvColor, true);
        theme.resolveAttribute(R.attr.all_bg_color, allBg, true);
        theme.resolveAttribute(R.attr.view_bg_color, viewBg, true);

        tvMode.setTextColor(getResources().getColor(tvColor.resourceId));
        tvMe.setTextColor(getResources().getColor(tvColor.resourceId));
        tvNightTitle.setTextColor(getResources().getColor(tvColor.resourceId));
        rlBg.setBackgroundResource(allBg.resourceId);
        rlNightBg.setBackgroundResource(viewBg.resourceId);
    }

    private Bitmap getCacheBitmapFromView(View view) {
        final boolean drawingCacheEnabled = true;
        view.setDrawingCacheEnabled(drawingCacheEnabled);
        view.buildDrawingCache(drawingCacheEnabled);
        final Bitmap drawingCache = view.getDrawingCache();
        Bitmap bitmap;
        if (drawingCache != null) {
            bitmap = Bitmap.createBitmap(drawingCache);
            view.setDrawingCacheEnabled(false);
        } else {
            bitmap = null;
        }
        return bitmap;
    }

    private void showAnimation() {
        final View decorView = activity.getWindow().getDecorView();
        Bitmap cacheBitmap = getCacheBitmapFromView(decorView);
        if (decorView instanceof ViewGroup && cacheBitmap != null) {
            final View view = new View(activity);
            view.setBackgroundDrawable(new BitmapDrawable(getResources(), cacheBitmap));
            ViewGroup.LayoutParams layoutParam = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            ((ViewGroup) decorView).addView(view, layoutParam);
            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(view, "alpha", 1f, 0f);
            objectAnimator.setDuration(300);
            objectAnimator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    ((ViewGroup) decorView).removeView(view);
                }
            });
            objectAnimator.start();
        }
    }
}
