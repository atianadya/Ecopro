package com.rocke.development.HmtlEcopro2015;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import com.rocke.development.HmtlEcopro2015.R;

/**
 * Created by Atia on 2014-12-28.
 */
public class DesaMitra extends Activity{

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_desa);
    }
}
