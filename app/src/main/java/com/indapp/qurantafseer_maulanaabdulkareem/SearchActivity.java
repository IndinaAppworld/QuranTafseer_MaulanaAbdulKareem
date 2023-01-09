package com.indapp.qurantafseer_maulanaabdulkareem;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewParent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.indapp.beans.PageBean;
import com.indapp.fonts.ArabicTextView;
import com.indapp.fonts.GujaratiEditText;
import com.indapp.fonts.GujaratiTextView;
import com.indapp.fonts.UrduEditText;
import com.indapp.fonts.UrduTextView;
import com.indapp.utils.Constants1;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class SearchActivity extends Activity {

    EditText txtSearch;

    LinearLayout contentLayout;
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
        contentLayout=(LinearLayout)findViewById(R.id.contentLayout);
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
                    if(Constants1.LANGUAGE.equalsIgnoreCase(Constants1.URDU)){
                        if (event.getRawX() <= (txtSearch.getLeft() + txtSearch.getCompoundDrawables()[DRAWABLE_LEFT].getBounds().width()))
                        {
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
                    else if(Constants1.LANGUAGE.equalsIgnoreCase(Constants1.GUJARATI))
                    {
                        if (event.getRawX() >= (txtSearch.getRight()- txtSearch.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width()))
                        {
                            // your action here
                            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
//                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.A);

                            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "gu");

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

                }
                return false;
            }
        });

        InputMethodManager inputMethodManager = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        }
//        txtSearch.setText("ઇબાદત");

        txtSearch.setOnEditorActionListener(new EditText.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER
                        || actionId == EditorInfo.IME_ACTION_DONE) {

                    if(txtSearch.getText().toString().length()>3)
                    {
                    contentLayout.removeAllViews();
                    ;
                    Cursor cursor = Constants1.databaseHandler.getData("SELECT QA.PARA_NO,QA.SURA_NO,QA.QURAN_AYAT_NO,QA.SURA_AYAT_NO, QA.AYAT,\n" +
                            "QP.PARA_NAME,\n" +
                            "QS.SURA_NAME,\n" +
                            "QT.TRANSLATION_" + Constants1.LANGUAGE + ",\n" +
                            "QT.TAFSEER_" + Constants1.LANGUAGE + ",\n" +
                            "QT.ID" + "\n" +
                            "from QURAN_ARABIC QA, QURAN_PARA QP, QURAN_SURA QS, QURAN_TRANSLATION QT\n" +
                            "where QA.PARA_NO =  QP.ID and (QT.TRANSLATION_" + Constants1.LANGUAGE + " like '%" + txtSearch.getText().toString().trim() + "%'  or QA.AYAT like '%" + txtSearch.getText().toString().trim() + "%') and QA.SURA_NO = QS.ID\n" +
                            "and QA.ID=QT.ID order by QT.ID", Constants1.sqLiteDatabase);
                    String PARA_NO, SURA_NO, QURAN_AYAT_NO, SURA_AYAT_NO, QURAN_AYAT, PARA_NAME, SURA_NAME, TRANSALATION, TAFSEER = "", ID = "";
                    ArrayList<PageBean> pageBeanArrayList = new ArrayList<>();
                    PageBean tempPageBean;
                    if (cursor != null) {
                        while (cursor.moveToNext()) {
                            Log.v(Constants1.TAG, "***********ID - " + "*****************");
                            tempPageBean = new PageBean();
                            PARA_NO = "" + cursor.getInt(0);
                            SURA_NO = "" + cursor.getInt(1);
                            QURAN_AYAT_NO = "" + cursor.getInt(2);
                            SURA_AYAT_NO = "" + cursor.getInt(3);
                            QURAN_AYAT = "" + cursor.getString(4);
                            PARA_NAME = "" + cursor.getString(5);
                            SURA_NAME = "" + cursor.getString(6);
                            TRANSALATION = "" + cursor.getString(7);
                            TAFSEER = "" + cursor.getString(8);
                            ID = "" + cursor.getString(9);

                            Log.v(Constants1.TAG, "PARA_NO: " + PARA_NO + "\nSURA_NO: " + SURA_NO + "\nPARA_NAME: " + PARA_NAME + "\nSURA_NAME:  " + SURA_NAME + "\nPARA_NAME:  " + PARA_NAME + "\nQURA_AYAT: " + QURAN_AYAT + "\nTRANSALATION: " + TRANSALATION);

                            tempPageBean.setPARA_NO(PARA_NO);
                            tempPageBean.setSURA_NO(SURA_NO);
                            tempPageBean.setQURAN_AYAT_NO(QURAN_AYAT_NO);
                            tempPageBean.setSURA_AYAT_NO(SURA_AYAT_NO);
                            tempPageBean.setQURA_AYAT(QURAN_AYAT);
                            tempPageBean.setPARA_NAME(PARA_NAME);
                            tempPageBean.setSURA_NAME(SURA_NAME);
                            tempPageBean.setTRANSALATION(TRANSALATION);
                            tempPageBean.setID(ID);
//                if(TAFSEER!=null && TAFSEER.trim().length()>0)
                            tempPageBean.setTAFSEER(TAFSEER);
//                else tempPageBean.setTAFSEER("");

                            pageBeanArrayList.add(tempPageBean);
                        }
                    }


                    int TOTAL_ROW = pageBeanArrayList.size();
                    final TextView txtTranslation[] = new TextView[TOTAL_ROW];
                    final ArabicTextView txtArabicTextView[] = new ArabicTextView[TOTAL_ROW];
                    final View view_row[] = new View[TOTAL_ROW];
                    final View viewLineHorizontal[] = new View[TOTAL_ROW];
                    final RelativeLayout layout_bookmarkContent[] = new RelativeLayout[TOTAL_ROW];
                    final ArabicTextView txtBookmarkSurahName[] = new ArabicTextView[TOTAL_ROW];
                    final ArabicTextView txtBookmarkParaName[] = new ArabicTextView[TOTAL_ROW];
                    String temp_translation = "";
                    String finalTasfeeer = "";
                    Log.v(Constants1.TAG, "Total Result-->" + TOTAL_ROW);
                    for (int i = 0; i < TOTAL_ROW; i++) {
                        Log.v(Constants1.TAG, "Search Counter-->" + i);
                        view_row[i] = View.inflate(SearchActivity.this, R.layout.inflate_search_result_item, null);

                        viewLineHorizontal[i] = view_row[i].findViewById(R.id.viewLineHorizontal);

                        layout_bookmarkContent[i] = view_row[i].findViewById(R.id.layout_bookmarkContent);
                        txtBookmarkSurahName[i] = view_row[i].findViewById(R.id.txtBookmarkSurahName);
                        txtBookmarkParaName[i] = view_row[i].findViewById(R.id.txtBookmarkParaName);

                        if (Constants1.LANGUAGE.equalsIgnoreCase(Constants1.URDU)) {
                            txtTranslation[i] = (TextView) view_row[i].findViewById(R.id.txtUrdu);

                        } else if (Constants1.LANGUAGE.equalsIgnoreCase(Constants1.GUJARATI)) {
                            txtTranslation[i] = (TextView) view_row[i].findViewById(R.id.txtGujarati);
                        }

                        txtArabicTextView[i] = (ArabicTextView) view_row[i].findViewById(R.id.txtArabic);

                        temp_translation = pageBeanArrayList.get(i).getTRANSALATION();

                        if (Constants1.LANGUAGE.equalsIgnoreCase(Constants1.GUJARATI)) {
                            temp_translation = temp_translation.replaceAll("0", "૦").replaceAll("1", "૧")
                                    .replaceAll("2", "૨").replaceAll("3", "૩").replaceAll("4", "૪")
                                    .replaceAll("5", "૫").replaceAll("6", "૬").replaceAll("7", "૭").replaceAll("8", "૮").replaceAll("9", "૯");
                        } else if (Constants1.LANGUAGE.equalsIgnoreCase(Constants1.URDU)) {
                            temp_translation = temp_translation.replaceAll("0", "۰").replaceAll("1", "۱")
                                    .replaceAll("2", "۲").replaceAll("3", "۳").replaceAll("4", "۴")
                                    .replaceAll("5", "۵").replaceAll("6", "۶").replaceAll("7", "۷").replaceAll("8", "۸").replaceAll("9", "۹");
                        }



                        txtBookmarkParaName[i].setText(pageBeanArrayList.get(i).getPARA_NAME());
                        txtBookmarkSurahName[i].setText(pageBeanArrayList.get(i).getSURA_NAME());

                        txtArabicTextView[i].setText(pageBeanArrayList.get(i).getQURAN_AYAT());
                        // view_row[i].setOnLongClickListener(new BookmarkActivity.RowLongClick(i, pageBeanArrayList.get(i)));

//                        if (Constants1.sp.contains("bookmark_" + pageBeanArrayList.get(i).getID()))
//                            view_row[i].setBackgroundColor(getResources().getColor(R.color.colorSurahParaBG));


                        txtTranslation[i].setText(temp_translation);

                        txtTranslation[i].setTextSize(2, (float) Constants1.sp.getInt("perf_font_size", Constants1.DEFAULT_FONT));
                        txtArabicTextView[i].setTextSize(2, (float) Constants1.sp.getInt("perf_font_size", Constants1.DEFAULT_FONT) * 1.3f);

                        txtTranslation[i].setTextColor(Color.parseColor("#" + Constants1.sp.getString("perf_font_color_urdu", "000000")));
                        txtArabicTextView[i].setTextColor(Color.parseColor("#" + Constants1.sp.getString("perf_font_color_arabic", "000000")));

                        txtBookmarkSurahName[i].setTextSize(2, (float) Constants1.sp.getInt("perf_font_size", Constants1.DEFAULT_FONT) * 1.3f);
                        txtBookmarkParaName[i].setTextSize(2, (float) Constants1.sp.getInt("perf_font_size", Constants1.DEFAULT_FONT) * 1.3f);

                        viewLineHorizontal[i].setBackgroundColor(Color.parseColor("#" + Constants1.sp.getString("perf_line_color", "000000")));



//                        Spannable translationSpan = new SpannableString(temp_translation);
//
//
//                        if(temp_translation.contains(txtSearch.getText().toString().trim()))
//                        translationSpan.setSpan(new BackgroundColorSpan(Color.YELLOW), 15, 30, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//
//                        txtTranslation[i].setText(translationSpan);

                        setHighLightedText(txtTranslation[i],txtSearch.getText().toString().trim());
                        setHighLightedText(txtArabicTextView[i],txtSearch.getText().toString().trim());
                        contentLayout.addView(view_row[i]);
                    }


                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    for (int i = 0; i < TOTAL_ROW; i++) {
                                        txtTranslation[i].setTextSize(2, (float) Constants1.sp.getInt("perf_font_size", Constants1.DEFAULT_FONT));
                                        txtArabicTextView[i].setTextSize(2, (float) Constants1.sp.getInt("perf_font_size", Constants1.DEFAULT_FONT) * 1.3f);

                                        txtTranslation[i].setTextColor(Color.parseColor("#" + Constants1.sp.getString("perf_font_color_urdu", "000000")));
                                        txtArabicTextView[i].setTextColor(Color.parseColor("#" + Constants1.sp.getString("perf_font_color_arabic", "000000")));

                                        txtBookmarkSurahName[i].setTextSize(2, (float) Constants1.sp.getInt("perf_font_size", Constants1.DEFAULT_FONT) * 1.3f);
                                        txtBookmarkParaName[i].setTextSize(2, (float) Constants1.sp.getInt("perf_font_size", Constants1.DEFAULT_FONT) * 1.3f);

                                        viewLineHorizontal[i].setBackgroundColor(Color.parseColor("#" + Constants1.sp.getString("perf_line_color", "000000")));

                                    }
//                            txtTafseer.setTextSize(2, (float) Constants1.sp.getInt("perf_font_size", Constants1.DEFAULT_FONT));
                                }

                                ;
                            });
                        }
                    }, 0, 100);

                    // Do your action
                    return false;

                }
                    else
                    {
                        Toast.makeText(SearchActivity.this, "Search Text should be 5 Character Atleast", Toast.LENGTH_SHORT).show();
                    }
            }

                return true;
            }
            });

        mScaleDetector = new ScaleGestureDetector(this, new ScaleManager());


    }

    public void setHighLightedText(TextView tv, String textToHighlight) {
        String tvt = tv.getText().toString();
        int ofe = tvt.indexOf(textToHighlight, 0);
        Spannable wordToSpan = new SpannableString(tv.getText());
        for (int ofs = 0; ofs < tvt.length() && ofe != -1; ofs = ofe + 1) {
            ofe = tvt.indexOf(textToHighlight, ofs);
            if (ofe == -1)
                break;
            else {
                // set color here
                wordToSpan.setSpan(new BackgroundColorSpan(0xFFFFFF00), ofe, ofe + textToHighlight.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                tv.setText(wordToSpan, TextView.BufferType.SPANNABLE);
            }
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
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        mScaleDetector.onTouchEvent(motionEvent);
        Log.v(Constants1.TAG,"Touch Event");
        return super.dispatchTouchEvent(motionEvent);
    }
    private ScaleGestureDetector mScaleDetector;
    private class ScaleManager extends ScaleGestureDetector.SimpleOnScaleGestureListener {

        /* renamed from: a  reason: collision with root package name */


        /* renamed from: b  reason: collision with root package name */

        private ScaleManager() {

        }
        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            //if(layout_setting.getVisibility()!=View.VISIBLE)
            {
                n = n * scaleGestureDetector.getScaleFactor();
                Log.v(Constants1.TAG, "---onScale---");
                Constants1.editor.putInt("perf_font_size", (int) n).commit();
            }
            return true;
        }
        public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
            Log.v(Constants1.TAG, "---onScale Begin---");
            return super.onScaleBegin(scaleGestureDetector);
        }
        public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {
            Log.v(Constants1.TAG, "---onScale End---");
            super.onScaleEnd(scaleGestureDetector);
            //if(layout_setting.getVisibility()!=View.VISIBLE)
            {
                Constants1.editor.putInt("perf_font_size", (int) n).commit();
                Log.v(Constants1.TAG, "----Font Size-------->" + n);
            }
        }
    }
    public void onResume() {
        super.onResume();
        n = (float) Constants1.sp.getInt("perf_font_size", Constants1.DEFAULT_FONT);
    }
    float n;
}