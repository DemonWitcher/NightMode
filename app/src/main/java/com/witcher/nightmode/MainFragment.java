package com.witcher.nightmode;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class MainFragment extends Fragment {

    private Activity activity;
    private RecyclerView rv;

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
        View view = inflater.inflate(R.layout.fg_main, container, false);
        rv = view.findViewById(R.id.rv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(linearLayoutManager);
        rv.setAdapter(new MyAdapter());
        return view;
    }

    public void notifyUI() {
        TypedValue tvColor = new TypedValue();
        TypedValue dividerBg = new TypedValue();
        TypedValue viewBg = new TypedValue();

        Resources.Theme theme = activity.getTheme();
        theme.resolveAttribute(R.attr.tv_color, tvColor, true);
        theme.resolveAttribute(R.attr.divider_color, dividerBg, true);
        theme.resolveAttribute(R.attr.view_bg_color, viewBg, true);

        int childCount = rv.getChildCount();
        for (int childIndex = 0; childIndex < childCount; childIndex++) {
            ViewGroup childView = (ViewGroup) rv.getChildAt(childIndex);
            RelativeLayout rlBg = childView.findViewById(R.id.rl_bg);
            TextView tvContent = childView.findViewById(R.id.tv_content);
            View divider = childView.findViewById(R.id.view_divider);
            rlBg.setBackgroundResource(viewBg.resourceId);
            divider.setBackgroundResource(dividerBg.resourceId);
            tvContent.setTextColor(getResources().getColor(tvColor.resourceId));
        }

        try {
            Field declaredField = RecyclerView.class.getDeclaredField("mRecycler");
            declaredField.setAccessible(true);
            Method declaredMethod = RecyclerView.Recycler.class.getDeclaredMethod("clear");
            declaredMethod.setAccessible(true);
            declaredMethod.invoke(declaredField.get(rv));
            RecyclerView.RecycledViewPool recycledViewPool = rv.getRecycledViewPool();
            recycledViewPool.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class MyAdapter extends RecyclerView.Adapter {

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new MyViewHolder(LayoutInflater.from(activity).inflate(R.layout.rv_item, viewGroup, false));
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
            TextView tv = viewHolder.itemView.findViewById(R.id.tv_content);
            tv.setText("" + i);
        }

        @Override
        public int getItemCount() {
            return 40;
        }
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }


}
