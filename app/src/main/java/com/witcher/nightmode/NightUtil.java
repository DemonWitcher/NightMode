package com.witcher.nightmode;

import android.content.Context;
import android.content.SharedPreferences;

public class NightUtil {

    public static boolean isNightMode(Context context) {
        SharedPreferences sp = context.getSharedPreferences("test_night_mode", Context.MODE_PRIVATE);
        return sp.getBoolean("nightmode", false);
    }

    public static void setNightMode(Context context, boolean isNightMode) {
        SharedPreferences sp = context.getSharedPreferences("test_night_mode", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("nightmode", isNightMode);
        editor.commit();
    }

}
