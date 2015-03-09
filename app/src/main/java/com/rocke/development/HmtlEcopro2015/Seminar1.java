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
 * Created by Atia on 2015-01-08.
 */
public class Seminar1 extends Activity {

    private ImageButton nextButton;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seminar1);
        addListenerButton();
    }

    public void addListenerButton() {
        final Context context = this;
        nextButton = (ImageButton) findViewById(R.id.button_next);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Seminar2.class);
                startActivity(intent);
            }
        });
    }
}
