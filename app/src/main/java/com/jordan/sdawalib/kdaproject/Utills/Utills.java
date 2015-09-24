package com.jordan.sdawalib.kdaproject.Utills;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by SamerGigaByte on 24/09/2015.
 */
public class Utills {
    public static final String userNameKey="usernameKey";
    public static final String passwordKey="passwordKey";

    public static void savePreferencesForReasonCode(Context context,
                                             String key, String value) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }
    public static String getPreferences(Context context, String prefKey) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        return sharedPreferences.getString(prefKey, "");
    }
}
