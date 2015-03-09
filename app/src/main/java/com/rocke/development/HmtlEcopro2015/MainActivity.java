package com.rocke.development.HmtlEcopro2015;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.app.Activity;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.Toast;
import android.view.MenuItem;

import com.rocke.development.HmtlEcopro2015.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Atia on 2014-12-23.
 */

@SuppressWarnings("deprecation")
public class MainActivity extends Activity {

        private Camera cameraObj;
        private CameraPreview showCamera;
        private ImageView pic;
        private static int cp_width;
        private FrameLayout preview;
        private Button shareButton;
        private ImageButton button_1, button_2, button_3, button_4;


    public static Camera isCameraAvailable() {
        Camera object = null;
        try {
            object = Camera.open();
        } catch (Exception e) {
            // failed to get camera
        }
        return object;

    }


    private PictureCallback mPicture = new PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

            File pictureFile = getOutputMediaFile();
            if (pictureFile == null) {
                return;
            }
            try {

                Matrix matrix = new Matrix();
                matrix.postRotate(90);

                FileOutputStream file_out = new FileOutputStream(pictureFile);
                Bitmap picture = BitmapFactory.decodeByteArray(data,0,data.length);
                picture = Bitmap.createBitmap(picture,0,0,picture.getHeight(),picture.getHeight(),matrix,true);

                /* overlaying output file */
                Bitmap bmpoverlay = Bitmap.createBitmap(picture.getWidth(), picture.getHeight(), picture.getConfig());
                Bitmap overlayOut = BitmapFactory.decodeResource(getResources(), R.drawable.overlay);
                overlayOut = Bitmap.createScaledBitmap(overlayOut,bmpoverlay.getWidth(),bmpoverlay.getHeight(),true);
                Canvas canvas = new Canvas(bmpoverlay);
                canvas.drawBitmap(picture, new Matrix(), null);
                canvas.drawBitmap(overlayOut, new Matrix(), null);

                bmpoverlay.compress(Bitmap.CompressFormat.JPEG, 85, file_out);

                file_out.write(data);
                file_out.close();
                Toast.makeText(getApplicationContext(), "Image saved to gallery", Toast.LENGTH_SHORT).show();
                MediaScannerConnection.scanFile(MainActivity.this, new String[]{pictureFile.getPath()}, new String[]{"image/jpeg"}, null);

                Intent intent = new Intent(MainActivity.this, ImagePreview.class);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bmpoverlay.compress(Bitmap.CompressFormat.JPEG, 85, stream);
                byte[] bytes = stream.toByteArray();
                intent.putExtra("JPEG",bytes);
                startActivity(intent);



                cameraObj.startPreview();
            } catch (FileNotFoundException e) {
                // file not found
            } catch (IOException e) {
                // IO exception
            } catch (IllegalArgumentException e) {
                Log.d("Camera","x,y, width, height values are outside of the dimensions of the source bitmap");
            }
        }
    };

    private static File getOutputMediaFile() {
        File storage = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Ecopro 2015");
        if (!storage.exists()) {
            if (!storage.mkdirs()) {
                Log.d("Ecopro", "failed to create directory");
                return null;
            }
        }

        // media file name
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        mediaFile = new File(storage.getPath() + File.separator + "IMG_" + timestamp + ".jpg");

        return mediaFile;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        addListenerButton();
        cameraObj = isCameraAvailable();
        showCamera = new CameraPreview(this, cameraObj);
        preview = (FrameLayout) findViewById(R.id.camera_preview);
        preview.addView(showCamera);
        preview.addView(new DrawOnTop(this),new WindowManager.LayoutParams(TableRow.LayoutParams.FILL_PARENT, WindowManager.LayoutParams.FILL_PARENT));
    }

    public void addListenerButton() {
        final Context context = this;
        button_1 = (ImageButton) findViewById(R.id.button_1);
        button_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (context, DesaMitra.class);
                startActivity(intent);
            }
        });
        button_2 = (ImageButton) findViewById(R.id.button_2);
        button_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (context, Lomba.class);
                startActivity(intent);
            }
        });
        button_3 = (ImageButton) findViewById(R.id.button_3);
        button_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Seminar1.class);
                MainActivity.this.onStop();
                startActivity(intent);
            }
        });
        button_4 = (ImageButton) findViewById(R.id.button_4);
        button_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (context, Exhibition.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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

    // 1:1 ratio camera preview
    @Override
    public void onResume() {
        super.onResume();
        findViewById(R.id.camera_preview).getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                View camera_preview = findViewById(R.id.camera_preview);
                ViewGroup.LayoutParams layout = camera_preview.getLayoutParams();
                layout.height = camera_preview.getWidth();

                camera_preview.setLayoutParams(layout);
                camera_preview.getViewTreeObserver().removeGlobalOnLayoutListener(this);

            }
        });
    }

    public void snapIt(View view) {
        cameraObj.takePicture(null, null, mPicture);
    }

    @Override
    public void onBackPressed() {
        this.finish();
        cameraObj.release();
    }
}