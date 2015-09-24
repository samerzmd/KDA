package com.jordan.sdawalib.kdaproject;

import android.view.MotionEvent;

/**
 * Created by SamerGigaByte on 9/21/2015.
 */
public class KeyboardTouchEvent {
    Character charPressed;
    MotionEvent motionEvent;

    public KeyboardTouchEvent(Character charPressed, MotionEvent motionEvent) {
        this.charPressed = charPressed;
        this.motionEvent = motionEvent;
    }
    public Character getCharPressed() {
        return charPressed;
    }
    public MotionEvent getMotionEvent() {
        return motionEvent;
    }
}
