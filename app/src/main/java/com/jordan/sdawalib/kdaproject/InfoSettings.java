package com.jordan.sdawalib.kdaproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.andreabaccega.widget.FormEditText;
import com.jordan.sdawalib.kdaproject.Utills.Utills;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class InfoSettings extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.userName)
    FormEditText userName;
    @Bind(R.id.password)
    FormEditText password;
    @Bind(R.id.btnSave)
    Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_settings);
        ButterKnife.bind(this);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utills.savePreferencesForReasonCode(InfoSettings.this,Utills.userNameKey,userName.getText().toString());
                Utills.savePreferencesForReasonCode(InfoSettings.this,Utills.passwordKey,password.getText().toString());
                new SweetAlertDialog(InfoSettings.this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Good Job!")
                        .setContentText("Your Info Saved Successfully!")
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
}
