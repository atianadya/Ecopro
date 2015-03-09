package com.rocke.development.HmtlEcopro2015;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import com.rocke.development.HmtlEcopro2015.R;

/**
 * Created by Atia on 2014-12-27.
 */
public class DrawOnTop extends View {
    private Paint paint;
    private Bitmap bitmap;

    public DrawOnTop(Context context) {
        super(context);
    }

    private void drawBackground(Canvas canvas) {
        int height = getHeight();
        int width = getWidth();

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.overlay);

        Bitmap bitmapRes = Bitmap.createScaledBitmap(bitmap,width,height,true);
        canvas.drawBitmap(bitmapRes,0,0,null);
    }

    @Override
    protected void onDraw (Canvas canvas) {
        drawBackground(canvas);
    }
}
