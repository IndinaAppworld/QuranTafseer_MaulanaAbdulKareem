package com.indapp.islamicknowledge;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.indapp.utils.Constants1;
import com.indapp.utils.DatabaseHandler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class SplashActivity extends Activity {

    boolean isChangedStat=false;
    ImageView imgSplashIcon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);
        // load title bar from Android layout
        TextView titleBar = (TextView)getWindow().findViewById(android.R.id.title);
        if (titleBar != null) {
            // set text color, YELLOW as sample
            titleBar.setTextColor(Color.YELLOW);
            // find parent view
            ViewParent parent = titleBar.getParent();
            if (parent != null && (parent instanceof View)) {
                // set background on parent, BRICK as sample
                View parentView = (View)parent;
                parentView.setBackgroundColor(Color.rgb(0x88, 0x33, 0x33));
            }
        }

        Handler mHandler = new Handler();
        mHandler.postDelayed(
                new Runnable() {

                    @Override
                    public void run()
                    {
                        //start your activity here
                        if (isChangedStat == false)
                        {
                            copydatabase();

                        }

                    }

                }, 3000L);



    }
    private void copydatabase() {
        Context context=getApplicationContext();

        try {


            String DB_NAME= DatabaseHandler.DB_NAME;

            if(new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS) + "/MyFiles/"+DB_NAME).exists()==false) {

                Toast.makeText(getApplicationContext(),"NEW CREATED",Toast.LENGTH_SHORT).show();
                File mainDire = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS + "/MyFiles/");
                if (!mainDire.exists()) {
                    mainDire.mkdirs();
                    Log.v(Constants1.TAG, "directory created------------------------>");
                } else {
                    Log.v(Constants1.TAG, "directory exist------------------------>");
                }
                File subDire = new File(mainDire.getAbsolutePath(), DB_NAME);
                subDire.createNewFile();
                Log.v(Constants1.TAG, "SubDir------>" + subDire.getPath());
                if (subDire.isDirectory()) {
                    Log.v(Constants1.TAG, "Directory Created----------->");
                } else {
                    Log.v(Constants1.TAG, "Directory Not Created------->");
                }
//
//        Open your local db as the input stream
                InputStream myinput = context.getAssets().open(DB_NAME);
                // Path to the just created empty db

                //Open the empty db as the output stream
                FileOutputStream myoutput = new FileOutputStream(subDire);

                // transfer byte to inputfile to outputfile
                byte[] buffer = new byte[1024];
                int length;
                while ((length = myinput.read(buffer)) > 0) {
                    myoutput.write(buffer, 0, length);
                }
                //Close the streams
                myoutput.flush();
                myoutput.close();
                myinput.close();
                Log.v(Constants1.TAG, "COMPLETED--------->");

                isChangedStat = true;
                showDisclaimer();
            }
            else
            {
                isChangedStat = true;
                showDisclaimer();
            }
        }
        catch (Exception e)
        {
            Toast.makeText(context,""+e,Toast.LENGTH_LONG).show();
        }
    }

    public void showDisclaimer()
    {
        Constants1.initSharedPref(this);
        if(Constants1.sp.getBoolean("languageDialog",false)==true)
        {
            finish();
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setClass(SplashActivity.this, MenuScreenActivity.class);
            startActivity(intent);
        }
        else {

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setClass(SplashActivity.this, LanguageChangeActivity.class);
            startActivityForResult(intent,100);
        }
    }
    public void onActivityResult(int req,int res,Intent data)
    {
        finish();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setClass(SplashActivity.this, MenuScreenActivity.class);
        startActivity(intent);
      }

}
