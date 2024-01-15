package com.rammdakk.recharge.base.theme;

import android.content.Context;
import android.content.res.Configuration;

import org.jetbrains.annotations.NotNull;

public class ThemeHelper {
    public static boolean isDarkTheme(@NotNull Context context) {
        int currentNightMode = context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        return currentNightMode == Configuration.UI_MODE_NIGHT_YES;
    }
}
