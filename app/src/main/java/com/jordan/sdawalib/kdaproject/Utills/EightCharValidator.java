package com.jordan.sdawalib.kdaproject.Utills;

import android.widget.EditText;

import com.andreabaccega.formedittextvalidator.Validator;

/**
 * Created by SDawalib on 9/13/2015.
 */
public class EightCharValidator extends Validator {

    public EightCharValidator() {
        super("Enter 8 Characters");
    }

    @Override
    public boolean isValid(EditText editText) {
        if (editText.getText().toString().length()==8){return true;}
        return false;
    }
}
