package com.jordan.sdawalib.kdaproject;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import com.andreabaccega.widget.FormEditText;
import com.jordan.sdawalib.kdaproject.Utills.SharedPreferencesManager;
import com.jordan.sdawalib.kdaproject.Utills.Utills;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import de.greenrobot.event.EventBus;

public class InfoSettingsActivity extends Activity {

    @Bind(R.id.toolbar)
    android.widget.Toolbar toolbar;
    @Bind(R.id.userName)
    FormEditText userName;
    @Bind(R.id.password)
    FormEditText password;
    @Bind(R.id.btnSave)
    Button btnSave;
    private EventBus bus = EventBus.getDefault();

    UserModel userModel;
    int numOfExp=0;
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_settings);
        ButterKnife.bind(this);
        bus.register(this);
        setActionBar(toolbar);
        userModel=new UserModel();

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new SweetAlertDialog(InfoSettingsActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Good Job!")
                        .setContentText("Your Info Saved Successfully! "+ ++numOfExp +" out of 5").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        if (numOfExp>=5){
                            SharedPreferencesManager.putObject(InfoSettingsActivity.this,"usermodel",userModel);
                            finish();
                        }
                        else {
                            sweetAlertDialog.dismiss();
                        }
                    }
                })
                        .show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_info_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    protected void onDestroy() {
        if(bus.isRegistered(this))
        bus.unregister(this);
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        if(bus.isRegistered(this))
        bus.unregister(this);
        super.onPause();
    }

    @Override
    protected void onResume() {
        if(!bus.isRegistered(this))
            bus.register(this);
        super.onResume();
    }
    public void onEvent(KeyboardTouchEvent event) {
        KDAEvent kdaEvent=new KDAEvent();
        kdaEvent.motionEvent=event.getMotionEvent();
        kdaEvent.aChar=event.getCharPressed()==null?null:event.getCharPressed();
        switch (kdaEvent.motionEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                kdaEvent.deatails="down on keyboard";
                break;
            case MotionEvent.ACTION_UP:
                kdaEvent.deatails="up on keyboard";
                break;
            default:
                kdaEvent.deatails="not down nor up ";
        }
        userModel.kdaEvents.add(kdaEvent);
    }
}
