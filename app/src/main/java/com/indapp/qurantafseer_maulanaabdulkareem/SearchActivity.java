package com.indapp.qurantafseer_maulanaabdulkareem;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.indapp.fonts.GujaratiEditText;
import com.indapp.fonts.GujaratiTextView;
import com.indapp.fonts.UrduEditText;
import com.indapp.fonts.UrduTextView;
import com.indapp.utils.Constants1;

import java.util.ArrayList;
import java.util.Locale;

public class SearchActivity extends Activity {

    EditText txtSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView titleBar = (TextView) getWindow().findViewById(android.R.id.title);
        if (titleBar != null) {
            // set text color, YELLOW as sample
            titleBar.setTextColor(Color.YELLOW);
            // find parent view
            ViewParent parent = titleBar.getParent();
            if (parent != null && (parent instanceof View)) {
                // set background on parent, BRICK as sample
                View parentView = (View) parent;
                parentView.setBackgroundColor(Color.rgb(0x88, 0x33, 0x33));
            }
        }
        Constants1.initSharedPref(this);
        Constants1.LANGUAGE = Constants1.sp.getString("language", Constants1.GUJARATI);
        setContentView(R.layout.activity_search);
        if (Constants1.LANGUAGE.equalsIgnoreCase(Constants1.GUJARATI)) {
            ((GujaratiTextView) findViewById(R.id.txtMainTitleGujarati)).setVisibility(View.VISIBLE);
            ((UrduEditText) findViewById(R.id.txtSearchUrdu)).setVisibility(View.GONE);
            txtSearch = (EditText) findViewById(R.id.txtSearchGujarati);
        } else if (Constants1.LANGUAGE.equalsIgnoreCase(Constants1.URDU)) {
            ((UrduTextView) findViewById(R.id.txtMainTitleUrdu)).setVisibility(View.VISIBLE);
            ((GujaratiEditText) findViewById(R.id.txtSearchGujarati)).setVisibility(View.GONE);
            txtSearch = (EditText) findViewById(R.id.txtSearchUrdu);
        }
        findViewById(R.id.imgBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        txtSearch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (txtSearch.getLeft() - txtSearch.getCompoundDrawables()[DRAWABLE_LEFT].getBounds().width())) {
                        // your action here
                        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
//                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.A);

                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ur");

                        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Need to speak");
                        try {
                            startActivityForResult(intent, 100);
                        } catch (ActivityNotFoundException a) {
                            Toast.makeText(getApplicationContext(),
                                    "Sorry your device not supported",
                                    Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    }
                }
                return false;
            }
        });

        InputMethodManager inputMethodManager = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        }

    }

    @Override
    public void onActivityResult(int req, int res, Intent result)
    {
        if(result!=null) {

            ArrayList<String> matches = result.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            txtSearch.setText(""+matches.get(0));
            InputMethodManager inputMethodManager = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (inputMethodManager != null) {
                inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            }

        }
    }
}