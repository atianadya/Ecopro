package com.rocke.development.HmtlEcopro2015;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ShareActionProvider;

import com.rocke.development.HmtlEcopro2015.R;

/**
 * Created by Atia on 2015-01-11.
 */
public class ImagePreview extends Activity {

    private Button button;
    private ShareActionProvider mShareActionProvider;
    private Bitmap fBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new myView(this));

    }

    private class myView extends View {

        public myView(Context context) {
            super(context);
            // TODO Auto-generated constructor stub
        }

        @Override
        protected void onDraw(Canvas canvas) {
            // TODO Auto-generated constructor stub
            Intent intent = getIntent();
            byte[] bytes = intent.getByteArrayExtra("JPEG");
            Bitmap bmp = BitmapFactory.decodeByteArray(bytes,0,bytes.length);

            DisplayMetrics dpmetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(dpmetrics);
            int height = dpmetrics.heightPixels;
            int width = dpmetrics.widthPixels;
            Bitmap bim;

            bim = Bitmap.createScaledBitmap(bmp,width,width,true);

            int topCalc = Math.abs(height/2 - width/2)-40;

            canvas.drawARGB(255,11,11,11);
            canvas.drawBitmap(bim,0,topCalc,null);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        getMenuInflater().inflate(R.menu.menu_item_share,menu);

        MenuItem shareItem = menu.findItem(R.id.menu_item_share);
        if (shareItem != null) {
            mShareActionProvider = (ShareActionProvider)shareItem.getActionProvider();

        }

        setShareIntent();
        return true;
    }

    private void setShareIntent() {
        if (mShareActionProvider != null) {
            Intent intent = getIntent();
            byte[] bytes = intent.getByteArrayExtra("JPEG");
            Bitmap bmp = BitmapFactory.decodeByteArray(bytes,0,bytes.length);

            DisplayMetrics dpmetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(dpmetrics);
            int width = dpmetrics.widthPixels;
            Bitmap bim;

            bim = Bitmap.createScaledBitmap(bmp,width,width,true);
            fBitmap = bim;

            String pathofBmp = MediaStore.Images.Media.insertImage(getContentResolver(), fBitmap,"title", null);
            Uri bmpUri = Uri.parse(pathofBmp);
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
            shareIntent.setType("image/png");

            startActivity(Intent.createChooser(shareIntent, "Share image to..."));
            mShareActionProvider.setShareIntent(shareIntent);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
