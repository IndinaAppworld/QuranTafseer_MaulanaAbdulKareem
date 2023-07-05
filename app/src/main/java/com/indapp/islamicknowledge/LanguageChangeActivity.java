package com.indapp.islamicknowledge;

import android.app.Activity;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.indapp.utils.Constants1;

public class LanguageChangeActivity extends Activity {

    TextView txtTitle;
    RadioButton radio1,radio2;
    TextView btnApply,btnCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(android.R.style.Theme_Dialog);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
        {
            setFinishOnTouchOutside(false);
        }
        else
        {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);
        }

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(R.layout.dialog_language);
        txtTitle=(TextView)findViewById(R.id.txtTitle);
        btnApply=(TextView)findViewById(R.id.btnApply);
        btnCancel=(TextView)findViewById(R.id.btnCancel);
        radio1=(RadioButton)findViewById(R.id.radio1);
        radio2=(RadioButton)findViewById(R.id.radio2);
        Constants1.initSharedPref(this);
        Constants1.LANGUAGE=Constants1.sp.getString("language",Constants1.URDU);
        Typeface face ;

        if (Constants1.LANGUAGE.equalsIgnoreCase(Constants1.GUJARATI)) {

            face = Typeface.createFromAsset(getAssets(),
                    "fonts/BLKCHCRY.ttf");
            //txtTitle.setText("ભાષા પસંદ કરો");

        } else //if (Constants1.LANGUAGE.equalsIgnoreCase(Constants1.URDU))
        {
            face= Typeface.createFromAsset(getAssets(),
                    "fonts/BLKCHCRY.ttf");
            //txtTitle.setText("زبان منتخب کریں");
        }
        txtTitle.setTypeface(face,Typeface.BOLD);
        radio1.setTypeface(face,Typeface.BOLD);
        radio2.setTypeface(face,Typeface.BOLD);
        btnApply.setTypeface(face,Typeface.BOLD);
        btnCancel.setTypeface(face,Typeface.BOLD);
        findViewById(R.id.btnApply).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(radio1.isChecked() || radio2.isChecked())
                {
                    if(radio1.isChecked())
                    Constants1.editor.putString("language",Constants1.URDU);
                    else if(radio2.isChecked())
                    Constants1.editor.putString("language",Constants1.GUJARATI);

                    Constants1.editor.putBoolean("languageDialog",true);

                    Constants1.editor.commit();
                    finish();

                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Select Language", Toast.LENGTH_SHORT).show();
                }
            }
        });
        findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constants1.editor.putBoolean("languageDialog",true);
                finish();
            }
        });

//        radio1.setText("Urdu");
//        radio2.setText("Gujarati");

    }
    @Override
    public void onBackPressed() {

    }
}
