package com.example.lecision.zoomeye;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class global{
    private static String access_token;
    public static String getAccess_token(){
        return access_token;
    }
    public static void setAccess_token(String a){
        global.access_token = a;
    }
}