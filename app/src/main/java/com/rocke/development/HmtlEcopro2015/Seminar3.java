package com.rocke.development.HmtlEcopro2015;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

import com.rocke.development.HmtlEcopro2015.R;

/**
 * Created by Atia on 2015-01-09.
 */
public class Seminar3 extends Activity {

    private ImageButton nextButton;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seminar3);
    }
}
