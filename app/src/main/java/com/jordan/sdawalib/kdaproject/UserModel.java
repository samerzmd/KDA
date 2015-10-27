package com.jordan.sdawalib.kdaproject;

import java.util.ArrayList;

/**
 * Created by SamerGigaByte on 25/09/2015.
 */
public class UserModel {
    public UserModel(){
        kdaEvents=new ArrayList<>();
    }
    String username;
    String password;
    ArrayList<KDAEvent> kdaEvents;
}
