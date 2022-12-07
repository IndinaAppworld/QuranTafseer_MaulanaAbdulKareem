package com.indapp.qurantafseer_maulanaabdulkareem;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Environment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager.widget.ViewPager;

import com.indapp.fonts.ArabicTextView;
import com.indapp.fonts.GujaratiTextView;
import com.indapp.fonts.UrduTextView;
import com.indapp.fragements.QuranFragement;
import com.indapp.qurantafseer_maulanaabdulkareem.databinding.ActivityMainBinding;
import com.indapp.smarttablayout.SmartTabLayout;
import com.indapp.utils.Constants1;
import com.indapp.utils.DatabaseHandler;
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener;

import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewParent;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class QuranActivity extends FragmentActivity implements ColorPickerDialogListener {


    private ViewPager pager;
    MyPageAdapter pageAdapter;

    public ArabicTextView txtParaName,txtSurahName;
    RelativeLayout layout_surahlist;
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
        Constants1.LANGUAGE=Constants1.sp.getString("language",Constants1.GUJARATI);
        Log.v(Constants1.TAG,"Language--->"+Constants1.LANGUAGE);

        if(Constants1.LANGUAGE.equalsIgnoreCase(Constants1.URDU))
        {
            Constants1.editor.putString("language",Constants1.GUJARATI);
            Constants1.editor.commit();
        }
        else
        {
            Constants1.editor.putString("language",Constants1.URDU);
            Constants1.editor.commit();
        }


        setContentView(R.layout.activity_main);
        layout_surahlist=(RelativeLayout) findViewById(R.id.layout_surahlist);
        mScaleDetector = new ScaleGestureDetector(this, new ScaleManager());

        copydatabase();

        txtParaName=(ArabicTextView)findViewById(R.id.txtParaName);
        txtSurahName=(ArabicTextView)findViewById(R.id.txtSurahName);
        txtParaName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                openSettingScreen(0);


            }
        });
        txtSurahName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                openSettingScreen(1);


            }
        });
        txtTitleSettingUrdu=(UrduTextView)findViewById(R.id.txtTitleSettingUrdu);
        txtTitleSettingGujarati=(GujaratiTextView) findViewById(R.id.txtTitleSettingGujarati);
        langaugeChange();
    }
    public void langaugeChange() {
        if (Constants1.LANGUAGE.equalsIgnoreCase(Constants1.URDU)) {
            txtTitleSettingGujarati.setVisibility(View.GONE);
            txtTitleSettingUrdu.setVisibility(View.VISIBLE);
        }
        else if (Constants1.LANGUAGE.equalsIgnoreCase(Constants1.GUJARATI))
        {
            txtTitleSettingGujarati.setVisibility(View.VISIBLE);
            txtTitleSettingUrdu.setVisibility(View.GONE);
        }

    }
    UrduTextView txtTitleSettingUrdu;
    GujaratiTextView txtTitleSettingGujarati;
    public void openSettingScreen(int type)
    {
        RelativeLayout layout_setting_header=(RelativeLayout)findViewById(R.id.layout_setting_header);
        if(layout_surahlist.getVisibility()==View.INVISIBLE) {

            if(type==0)
            {
                txtTitleSettingUrdu.setText("پاروں کی فہرست");
                txtTitleSettingGujarati.setText("પારાની સૂચિ");
            }
            else if(type==1)
            {
                txtTitleSettingUrdu.setText("سورتوں کی فہرست");
                txtTitleSettingGujarati.setText("સુરાઓની યાદી");
            }
            layout_surahlist.setVisibility(View.VISIBLE);
            TranslateAnimation animate = new TranslateAnimation(0, 0, layout_surahlist.getHeight(), 0);
            // duration of animation
            animate.setDuration(500);
            animate.setFillAfter(true);
            layout_surahlist.startAnimation(animate);
            animate.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    layout_setting_header.setVisibility(View.VISIBLE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }
        else
        {
            layout_surahlist.setVisibility(View.INVISIBLE);
            TranslateAnimation animate = new TranslateAnimation(0, 0, 0, layout_surahlist.getHeight());

            // duration of animation
            animate.setDuration(500);
            animate.setFillAfter(true);
            layout_surahlist.startAnimation(animate);
            animate.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    layout_setting_header.setVisibility(View.INVISIBLE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }
    }



    private void copydatabase() {
        Context context=getApplicationContext();
        try {
            String DB_NAME= DatabaseHandler.DB_NAME;

            if(new File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS) + "/MyFiles/"+DB_NAME).exists()==false)
            {

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

                try {
                    if (Constants1.databaseHandler == null)
                        Constants1.databaseHandler = new DatabaseHandler(this);
                    if (Constants1.sqLiteDatabase == null)
                        Constants1.sqLiteDatabase = Constants1.databaseHandler.opendatabase(getApplicationContext());
                }catch (Exception e)
                {
                    Log.e(Constants1.TAG,"Error in Opening Database----->"+e);
                }
                setTabs();
            }
            else
            {

                //Toast.makeText(getApplicationContext(),"Database Found",Toast.LENGTH_LONG).show();

                try {
                    if (Constants1.databaseHandler == null)
                        Constants1.databaseHandler = new DatabaseHandler(this);
                    if (Constants1.sqLiteDatabase == null)
                        Constants1.sqLiteDatabase = Constants1.databaseHandler.opendatabase(getApplicationContext());
                }catch (Exception e)
                {
                    Log.e(Constants1.TAG,"Error in Opening Database----->"+e);
                }
                setTabs();
//                isChangedStat = true;
//                showDisclaimer();
//                Toast.makeText(getApplicationContext(),"ALREADY AVAILABLE",Toast.LENGTH_SHORT).show();
            }
//            Toast.makeText(context,"CREATED",Toast.LENGTH_LONG).show();
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),""+e,Toast.LENGTH_LONG).show();
        }
    }
    public void setTabs()
    {
        List<Fragment> fragments = getFragments();
        pager = (ViewPager) findViewById(R.id.activity_main_pager);
        pageAdapter = new MyPageAdapter(getSupportFragmentManager(), fragments);
        pager.setAdapter(pageAdapter);
        ((SmartTabLayout) findViewById(R.id.viewpagertab_urdu)).setViewPager(pager);
        pager.setCurrentItem(TOTAL);
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


                Cursor cursor = Constants1.databaseHandler.getData("SELECT QP.PARA_NAME, QS.SURA_NAME " +
                        "from QURAN_ARABIC QA, QURAN_PARA QP, QURAN_SURA QS, QURAN_TRANSLATION QT\n" +
                        "where QA.PARA_NO =  QP.ID and QA.GROUP_NO="+(TOTAL-(position-1))+" and QA.SURA_NO = QS.ID\n" +
                        "and QA.ID=QT.ID", Constants1.sqLiteDatabase);
                while(cursor.moveToNext())
                {
                    txtParaName.setText(""+cursor.getString(0));
                    txtSurahName.setText(""+cursor.getString(1));
                }

            }
            @Override
            public void onPageSelected(int position) {

            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
    int TOTAL=Constants1.TOTAL_TABS;
    private List<Fragment> getFragments(){
        List<Fragment> fList = new ArrayList<Fragment>();
        Vector<String> v=new Vector<String>();
        for(int i=TOTAL;i>=0;i--)
        fList.add(QuranFragement.newInstance(i,0));

        return fList;
    }
    class MyPageAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragments;


        public MyPageAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }




        @Override
        public Fragment getItem(int position) {
            return this.fragments.get(position);
        }

        @Override
        public int getCount() {
            return this.fragments.size();
        }


        @Override
        public CharSequence getPageTitle(int position)
        {

            return Constants1.URDU_NUMBERS[TOTAL-position];
//            if(Constants1.LANGUAGE.equalsIgnoreCase(Constants1.URDU))
//            else if(Constants1.LANGUAGE.equalsIgnoreCase(Constants1.GUJARATI))
//                return Constants1.TAB_TIELS_ROMAN[position];
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
            n = n * scaleGestureDetector.getScaleFactor();
            Log.v(Constants1.TAG, "---onScale---");
            Constants1.editor.putInt("perf_font_size", (int) n).commit();
            return true;
        }
        public boolean onScaleBegin(ScaleGestureDetector scaleGestureDetector) {
            Log.v(Constants1.TAG, "---onScale Begin---");
            return super.onScaleBegin(scaleGestureDetector);
        }
        public void onScaleEnd(ScaleGestureDetector scaleGestureDetector) {
            Log.v(Constants1.TAG, "---onScale End---");
            super.onScaleEnd(scaleGestureDetector);
            Constants1.editor.putInt("perf_font_size", (int) n).commit();
            Log.v(Constants1.TAG,"----Font Size-------->"+n);
        }
    }
    /* access modifiers changed from: protected */
    public void onResume() {
        super.onResume();
        n = (float) Constants1.sp.getInt("perf_font_size", Constants1.DEFAULT_FONT);
    }
    float n;
    @Override
    public void onColorSelected(int dialogId, int color) {
        Constants1.initSharedPref(getApplicationContext());
        String hex = Integer.toHexString(color);
        if(dialogId==0)
            Constants1.editor.putString("perf_font_color_urdu",hex).commit();
        else
            Constants1.editor.putString("perf_font_color_arabic",hex).commit();
    }

    @Override
    public void onDialogDismissed(int dialogId) {

    }
}