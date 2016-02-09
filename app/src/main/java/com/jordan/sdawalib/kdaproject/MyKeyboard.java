package com.jordan.sdawalib.kdaproject;

import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.media.AudioManager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputConnection;

import de.greenrobot.event.EventBus;

/**
 * Created by SamerGigaByte on 9/21/2015.
 */
public class MyKeyboard extends InputMethodService implements KeyboardView.OnKeyboardActionListener {

    private KeyboardView kv;
    private Keyboard keyboard;

    private boolean caps = false;
    MotionEvent motionEvent=null;
    Character lastChar =null;

    private EventBus bus = EventBus.getDefault();
    private boolean isFirstEvent=true;

    @Override
    public View onCreateInputView() {
        kv = (KeyboardView) getLayoutInflater().inflate(R.layout.keyboard, null);
        keyboard = new Keyboard(this, R.xml.qwerty);
        kv.setKeyboard(keyboard);
        kv.setOnKeyboardActionListener(this);
        kv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                motionEvent=event;
                lastChar=null;
                LogKeyWithMotionEvent();
                Log.wtf("eventTime",event.getEventTime()+"");
                return false;
            }
        });
        return kv;
    }

    private void playClick(int keyCode){
        AudioManager am = (AudioManager) getSystemService(AUDIO_SERVICE);
        switch (keyCode) {
            case 32:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_SPACEBAR);
                break;
            case 10:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_RETURN);
                break;
            case Keyboard.KEYCODE_DELETE:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_DELETE);
                break;
            default:
                am.playSoundEffect(AudioManager.FX_KEYPRESS_STANDARD);
        }
    }

    @Override
    public void onPress(int primaryCode) {

    }

    @Override
    public void onRelease(int primaryCode) {

    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        long start = System.currentTimeMillis();
        Log.wtf("onKeyTime",start+"");

        lastChar=(char) primaryCode;
        LogKeyWithMotionEvent();
        InputConnection ic = getCurrentInputConnection();
        playClick(primaryCode);
        switch (primaryCode){
            case Keyboard.KEYCODE_DELETE:
                ic.deleteSurroundingText(1, 0);
                break;
            case Keyboard.KEYCODE_SHIFT:
                caps = !caps;
                keyboard.setShifted(caps);
                kv.invalidateAllKeys();
                break;
            case Keyboard.KEYCODE_DONE:
                ic.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER));

                break;
            case -11:
                keyboard = new Keyboard(this, R.xml.symbols);
                caps=false;
                keyboard.setShifted(caps);
                kv.invalidateAllKeys();
                kv.setKeyboard(keyboard);
                break;
            case -12:

                keyboard = new Keyboard(this, R.xml.qwerty);
                caps=false;
                keyboard.setShifted(caps);
                kv.invalidateAllKeys();
                kv.setKeyboard(keyboard);
                break;
            default:
                char code = (char) primaryCode;
                if(Character.isLetter(code) && caps) {
                    code = Character.toUpperCase(code);
                }
                ic.commitText(String.valueOf(code), 1);
        }
    }

    @Override
    public void onText(CharSequence text) {

    }

    @Override
    public void swipeLeft() {

    }

    @Override
    public void swipeRight() {

    }

    @Override
    public void swipeDown() {

    }

    @Override
    public void swipeUp() {

    }
    private void LogKeyWithMotionEvent(){
    if (lastChar !=null && motionEvent!=null){
        Log.wtf("WTF", lastChar +" "+motionEvent.getX()+" "+motionEvent.getY());
        KeyboardTouchEvent event=new KeyboardTouchEvent(lastChar,motionEvent);
        bus.post(event);
        lastChar =null;
        motionEvent=null;
        isFirstEvent=true;
    }
        else if (lastChar==null && motionEvent!=null && isFirstEvent)
    {
        isFirstEvent=false;
        Log.wtf("WTF", lastChar +" "+motionEvent.getX()+" "+motionEvent.getY());
        KeyboardTouchEvent event=new KeyboardTouchEvent(lastChar,motionEvent);
        bus.post(event);
        lastChar =null;
        motionEvent=null;
    }
    }

}
