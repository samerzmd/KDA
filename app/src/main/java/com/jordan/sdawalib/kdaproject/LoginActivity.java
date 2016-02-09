package com.jordan.sdawalib.kdaproject;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.andreabaccega.widget.FormEditText;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.jordan.sdawalib.kdaproject.Utills.EightCharValidator;
import com.jordan.sdawalib.kdaproject.Utills.SharedPreferencesManager;
import com.jordan.sdawalib.kdaproject.Utills.Utills;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import de.greenrobot.event.EventBus;

public class LoginActivity extends Activity {

    @Bind(R.id.toolbar)
    android.widget.Toolbar toolbar;
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
    @Bind(R.id.letterPressed)
    TextView letterPressed;
    @Bind(R.id.btnCreateExcelFile)
    Button btnCreateExcelFile;
    private EventBus bus = EventBus.getDefault();
    private UserModel userModel;
    int numOfExp=0;
    ArrayList<String>events=new ArrayList<String>();
    ArrayList<KDAMotionEvent>kdaMotionEvents=new ArrayList<KDAMotionEvent>();
    ArrayList<ArrayList<KDAMotionEvent>>arrayListsKdaMotionEvents=new ArrayList<ArrayList<KDAMotionEvent>>();
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        setActionBar(toolbar);
        bus.register(this);


        btnCollect.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, final MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_UP) {
                    KDAMotionEvent kdaMotionEvent = new KDAMotionEvent();
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN: {
                            kdaMotionEvent.action = "collect-Action Down";
                            kdaMotionEvent.actionTime = startClickTime = Calendar.getInstance().getTimeInMillis();
                            downTime.setText("Down Time:" + startClickTime);
                            break;
                        }
                        case MotionEvent.ACTION_UP: {
                            kdaMotionEvent.action = "collect-Action Up";
                            long clickTime = Calendar.getInstance().getTimeInMillis();
                            kdaMotionEvent.actionTime = clickTime;
                            long clickDuration = clickTime - startClickTime;
                            upTime.setText("UpTime:" + clickTime + " Down Up Time:" + clickDuration);
                        }
                    }

                    kdaMotionEvent.actionId = event.getAction();

                    kdaMotionEvent.username = userName.getText().toString();
                    kdaMotionEvent.password = password.getText().toString();
                    kdaMotionEvent.pressure = event.getPressure();
                    kdaMotionEvent.size = event.getSize();
                    kdaMotionEvent.fingerYpostion = event.getY();
                    kdaMotionEvent.fingerXpostion = event.getX();
                    kdaMotionEvents.add(kdaMotionEvent);
                    pressureTime.setText("pressure:" + event.getPressure() + "\nsize: " + event.getSize());
                    fingerLocation.setText("Finger Location: X=" + event.getX() + " Y=" + event.getY());
                    return false;
                }
                return false;
            }
        });
                btnCollect.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                boolean isUserNamePassed = userName.testValidity();
                boolean isPasswordPassed = password.testValidity();
                boolean isPasswordCorrect =true;
                boolean isUserNameCorrect = true;

                boolean isPassed = isUserNamePassed && isPasswordPassed && isPasswordCorrect && isUserNameCorrect;
                if (isPassed) {
                    Toast.makeText(LoginActivity.this, "Passed", Toast.LENGTH_SHORT).show();
                    String k="Action : collect"+ " username "+userName.getText().toString()+" password "+password.getText().toString()+" "+pressureTime.getText().toString()+" "+fingerLocation.getText().toString()+" "+downTime.getText().toString()+" "+upTime.getText().toString();

                    events.add(k);
                    k="";
                    int counter=1;
                    for (String s:events){
                        s="_____________\n"+counter+") "+s+"\n_____________\n";
                        k+=s;
                        counter++;
                    }
                    arrayListsKdaMotionEvents.add(kdaMotionEvents);

                    k="";
                    events=new ArrayList<String>();
                    new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Good Job!")
                            .setContentText("Your Info has been Saved Successfully! "+ ++numOfExp +" out of 3").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            userName.setText("");
                            password.setText("");
                            if (numOfExp>=3){
                                Gson gson = new GsonBuilder().create();
                                ArrayList one=new ArrayList();
                                for (ArrayList k:arrayListsKdaMotionEvents) {
                                    one.addAll(k);
                                }
                                JsonArray myKdaArray = gson.toJsonTree(one).getAsJsonArray();

                                String myEventsString=myKdaArray.toString();
                                Utills.writeToFile(LoginActivity.this,myEventsString);

                                numOfExp=0;
                                sweetAlertDialog.dismiss();
                            }
                            else {
                                sweetAlertDialog.dismiss();
                            }
                        }
                    })
                            .show();
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
            Intent o = new Intent(this, InfoSettingsActivity.class);
            startActivity(o);
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
        outState.putString("fingerLocation", fingerLocation.getText().toString());
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
        userModel= SharedPreferencesManager.getObject(LoginActivity.this,"usermodel",UserModel.class);
        if(!bus.isRegistered(this))
        bus.register(this);
        super.onResume();
    }

    public void onEvent(KeyboardTouchEvent event) {
        if (event.getMotionEvent().getAction()== MotionEvent.ACTION_DOWN||event.getMotionEvent().getAction()== MotionEvent.ACTION_UP) {
            String action = "";
            KDAMotionEvent motionEvent = new KDAMotionEvent();
            motionEvent.actionId = event.getMotionEvent().getAction();
            switch (event.getMotionEvent().getAction()) {
                case MotionEvent.ACTION_DOWN: {
                    motionEvent.action = action = "Keyboard Button Down";
                    motionEvent.actionTime = startClickTime = Calendar.getInstance().getTimeInMillis();
                    downTime.setText("Down Time:" + startClickTime);
                    break;
                }
                case MotionEvent.ACTION_UP: {
                    motionEvent.action = action = "Keyboard Button Up";
                    long clickTime = Calendar.getInstance().getTimeInMillis();
                    motionEvent.actionTime = clickTime;
                    long clickDuration = clickTime - startClickTime;
                    upTime.setText("UpTime:" + clickTime + "\n Down Up Time:" + clickDuration);
                }
                break;
            }
            motionEvent.pressure = event.getMotionEvent().getPressure();
            motionEvent.size = event.getMotionEvent().getSize();
            pressureTime.setText("pressure:" + event.getMotionEvent().getPressure() + "\nsize: " + event.getMotionEvent().getSize());
            motionEvent.keyboardCharClicked = event.charPressed != null ? event.charPressed + "" : "not considered yet";
            letterPressed.setText("letter pressed : " + event.charPressed != null ? event.charPressed + "" : "not considered yet");

            motionEvent.fingerXpostion=event.getMotionEvent().getX();
            motionEvent.fingerYpostion= event.getMotionEvent().getY();
            fingerLocation.setText("Finger Location: X=" + event.getMotionEvent().getX() + " Y=" + event.getMotionEvent().getY());
            motionEvent.username=userName.getText().toString();
            motionEvent.password=password.getText().toString();
            events.add("Action : " + action + " username " + userName.getText().toString() + " password " + password.getText().toString() + " " + pressureTime.getText().toString() + " " + fingerLocation.getText().toString() + " " + downTime.getText().toString() + " " + upTime.getText().toString());
            kdaMotionEvents.add(motionEvent);
        }
    }
}
