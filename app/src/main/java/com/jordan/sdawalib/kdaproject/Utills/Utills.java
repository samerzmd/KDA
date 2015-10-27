package com.jordan.sdawalib.kdaproject.Utills;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Calendar;

/**
 * Created by SamerGigaByte on 24/09/2015.
 */
public class Utills {
    public static void writeToFile(Context context,String data) {
        File root = context.getExternalFilesDir(null);
        File gpxfile = new File(root, "samples "+ Calendar.getInstance().getTimeInMillis()+".txt");
        try {
        FileWriter writer = new FileWriter(gpxfile);
        writer.append(data);
        writer.flush();

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
