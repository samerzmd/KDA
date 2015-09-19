package com.jordan.sdawalib.kdaproject;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.andreabaccega.widget.FormEditText;
import com.jordan.sdawalib.kdaproject.Utills.EightCharValidator;

import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginActivity extends Activity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.userName)
    FormEditText userName;
    @Bind(R.id.btnCollect)
    Button btnCollect;
    @Bind(R.id.password)
    FormEditText password;
    @Bind(R.id.downTime)
    TextView downTime;
    @Bind(R.id.upTime)
    TextView upTime;
    @Bind(R.id.pressureTime)
    TextView pressureTime;
    long startClickTime = 0;
    @Bind(R.id.fingerLocation)
    TextView fingerLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        userName.addValidator(new EightCharValidator());
        password.addValidator(new EightCharValidator());
        btnCollect.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, final MotionEvent event) {
                Calendar c = Calendar.getInstance();
                int date = c.get(Calendar.DATE);

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        startClickTime = Calendar.getInstance().getTimeInMillis();
                        downTime.setText("Down Time:" + startClickTime);
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        long clickTime = Calendar.getInstance().getTimeInMillis();
                        long clickDuration = clickTime - startClickTime;
                        upTime.setText("UpTime:" + clickTime + " Down Up Time:" + clickDuration);
                    }
                }
                pressureTime.setText("pressure:" + event.getPressure() + "\nsize: " + event.getSize());
                fingerLocation.setText("Finger Location: X="+event.getX()+" Y="+event.getY());
                return false;
            }

        });
        btnCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean isUserNamePassed = userName.testValidity();
                boolean isPasswordPassed = password.testValidity();
                boolean isPassed = isUserNamePassed && isPasswordPassed;
                if (isPassed) {
                    Toast.makeText(LoginActivity.this, "Passed", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(LoginActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("username", userName.getText().toString());
        outState.putString("password", password.getText().toString());
        outState.putString("downTime", downTime.getText().toString());
        outState.putString("uptime", upTime.getText().toString());
        outState.putString("pressure", pressureTime.getText().toString());
        outState.putString("fingerLocation",fingerLocation.getText().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        userName.setText(savedInstanceState.getString("username"));
        password.setText(savedInstanceState.getString("password"));
        downTime.setText(savedInstanceState.getString("downTime"));
        upTime.setText(savedInstanceState.getString("uptime"));
        pressureTime.setText(savedInstanceState.getString("pressure"));
        fingerLocation.setText(savedInstanceState.getString("fingerLocation"));
    }
}
