package com.indapp.islamicknowledge;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ScrollView;
import android.widget.TextView;

import com.indapp.fonts.GujaratiTextView;
import com.indapp.fonts.UrduTextView;
import com.indapp.utils.Constants1;

public class InfoActivity extends Activity {

    TextView txtAboutusUrdu;
    Typeface typeface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(android.R.style.Theme_Dialog);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_about_developer);

        findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        txtAboutusUrdu=(TextView)findViewById(R.id.txtDesc);
        Constants1.initSharedPref(this);
        Constants1.LANGUAGE = Constants1.sp.getString("language", Constants1.GUJARATI);
        if (Constants1.LANGUAGE.equalsIgnoreCase(Constants1.GUJARATI)) {

            typeface = Typeface.createFromAsset(getAssets(),
                    "fonts/BHUJ UNICODE.ttf");

                txtAboutusUrdu.setText("અગર આપકો ઇસ એપ સે ફાઇદા હો યા આપ કોઈ મશવરા દેના ચાહતે હોં તો નીચે દિયે ગએ ઈ-મેઈલ એડ્રેસ પર હમસે રાબતા કર સકતે હૈં, આપ કા દીની ભાઈ અફરોઝ ફત્તા (સુરત)" +
                        "");


        } else if (Constants1.LANGUAGE.equalsIgnoreCase(Constants1.URDU)) {
            typeface = Typeface.createFromAsset(getAssets(), "fonts/jameelnoorinastaleeq.ttf");


                txtAboutusUrdu.setText("" +
                        "اگر آپ کو اس ایپ سے فائدہ ہو یا آپ کوئی مشورہ دینا چاہتے ہو تو نیچے دیے گئے ای میل ایڈریس پر ہم سے رابطہ کر سکتے ہیں ، آپ کا دینی بھائی افروز فتہ (سورت)" +
                        "");


        }
        txtAboutusUrdu.setTypeface(typeface);
        findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

//        finish();
    }

    public void back(View v)
    {

        this.finish();

    }

    @Override
    public void onBackPressed()
    {
        back(null);
    }

}