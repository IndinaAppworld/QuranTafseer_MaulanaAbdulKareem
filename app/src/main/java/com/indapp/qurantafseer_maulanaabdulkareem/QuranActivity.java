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
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import androidx.core.content.ContextCompat;
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
import com.indapp.fonts.CipherNormal;
import com.indapp.fonts.GujaratiTextView;
import com.indapp.fonts.UrduTextView;
import com.indapp.fragements.QuranFragement;
import com.indapp.smarttablayout.SmartTabLayout;
import com.indapp.utils.Constants1;
import com.indapp.utils.DatabaseHandler;
import com.jaredrummler.android.colorpicker.ColorPickerDialog;
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener;
import com.skydoves.androidribbon.RibbonInterface;
import com.skydoves.androidribbon.RibbonLayout;
import com.skydoves.androidribbon.RibbonView;

import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewParent;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
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
    LinearLayout layout_setting;
    ImageView imgSettingClose;
    LinearLayout layout_setting_surahparalist;
    ScrollView scroll_setting;
    boolean isChangedStat=false;

    RelativeLayout layout_splash,layout_quran;
    ImageView imgSetting;
    LinearLayout layout_general_setting;

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


        setContentView(R.layout.activity_main);
        layout_quran=(RelativeLayout)findViewById(R.id.layout_quran);
        imgSetting=(ImageView)findViewById(R.id.imgSetting);

        layout_setting=(LinearLayout) findViewById(R.id.layout_setting);
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
//        changeLanguage();

        imgSettingClose=(ImageView)findViewById(R.id.imgSettingClose);
        imgSettingClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSettingScreen(-1);
            }
        });

        layout_setting=(LinearLayout)findViewById(R.id.layout_setting);
        scroll_setting=(ScrollView)findViewById(R.id.scroll_setting);
        layout_setting_surahparalist=(LinearLayout)findViewById(R.id.layout_setting_surahparahlist);
        layout_general_setting=(LinearLayout)findViewById(R.id.include_general_setting);
        imgSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSettingScreen(3);
            }
        });

        changeLanguage();

        findViewById(R.id.viewArabicColor).setBackgroundColor(Color.parseColor("#"+Constants1.sp.getString("perf_font_color_arabic", "000000")));
        findViewById(R.id.viewTranslationColor).setBackgroundColor(Color.parseColor("#"+Constants1.sp.getString("perf_font_color_urdu", "000000")));
        findViewById(R.id.viewLineColor).setBackgroundColor(Color.parseColor("#"+Constants1.sp.getString("perf_line_color", "000000")));
         
    }

    EditText txtPageNo;
    UrduTextView txtTitleSettingUrdu;
    GujaratiTextView txtTitleSettingGujarati;
    public void openSettingScreen(int type)
    {

        RelativeLayout layout_setting_header=(RelativeLayout)findViewById(R.id.layout_setting_header);
        if(layout_setting.getVisibility()==View.INVISIBLE) {

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
            else if(type==3)
            {
                txtTitleSettingUrdu.setText("ترتیب");
                txtTitleSettingGujarati.setText("સેટિંગ");
            }

            if(type==0  || type==1)
            {
                resetSurahParaList(type);
            }
            else{
                resetGeneralSetting(type);
            }

            layout_setting.setVisibility(View.VISIBLE);
            TranslateAnimation animate = new TranslateAnimation(0, 0, layout_setting.getHeight(), 0);
            // duration of animation
            animate.setDuration(300);
            animate.setFillAfter(true);
            layout_setting.startAnimation(animate);
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

            layout_setting.setVisibility(View.INVISIBLE);
            layout_setting_surahparalist.setVisibility(View.INVISIBLE);
            scroll_setting.setVisibility(View.INVISIBLE);
            layout_general_setting.setVisibility(View.INVISIBLE);
            layout_setting_surahparalist.removeAllViews();

            TranslateAnimation animate = new TranslateAnimation(0, 0, 0, layout_setting.getHeight());

            // duration of animation
            animate.setDuration(300);
            animate.setFillAfter(true);
            layout_setting.startAnimation(animate);
            animate.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    layout_setting_header.setVisibility(View.INVISIBLE);

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // Do something after 5s = 5000ms

//                            InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
//                            //Find the currently focused view, so we can grab the correct window token from it.
//                            View view = getCurrentFocus();
//                            //If no view currently has focus, create a new one, just so we can grab a window token from it
//                            if (view == null) {
//                                view = new View(QuranActivity.this);
//                            }
//                            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);


                        }
                    }, 100);

                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }
    }
    public class PARAListData
    {
        public String _id;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getPARA_NAME() {
            return PARA_NAME;
        }

        public void setPARA_NAME(String PARA_NAME) {
            this.PARA_NAME = PARA_NAME;
        }

        public String getPARA_NAME_ENGLISH() {
            return PARA_NAME_ENGLISH;
        }

        public void setPARA_NAME_ENGLISH(String PARA_NAME_ENGLISH) {
            this.PARA_NAME_ENGLISH = PARA_NAME_ENGLISH;
        }

        public String getSTART_GROUP() {
            return START_GROUP;
        }

        public void setSTART_GROUP(String START_GROUP) {
            this.START_GROUP = START_GROUP;
        }

        public String PARA_NAME;
        public String PARA_NAME_ENGLISH;
        public String START_GROUP;

    }

    ArrayList<PARAListData> paraListData=new ArrayList<>();
    PARAListData tempParaListData;
    View view_list[];
    ArabicTextView txtArabic[];
    CipherNormal txtEnglish[];
    UrduTextView txtNumber[];
    ImageView imgArrow[];
    LinearLayout layout_sub_content[];
    int TOTAL_ROW=0;
    public void resetSurahParaList(int type)
    {
        layout_setting_surahparalist.removeAllViews();
        scroll_setting.setVisibility(View.VISIBLE);
        scroll_setting.scrollTo(0,0);
        layout_setting_surahparalist.setVisibility(View.VISIBLE);
        TOTAL_ROW=0;
        if (type == 0)
        {
            paraListData.clear();
            Cursor cursor = Constants1.databaseHandler.getData("SELECT ID,PARA_NAME,PARA_NAME_ENGLISH, START_GROUP_NO FROM QURAN_PARA", Constants1.sqLiteDatabase);
            while(cursor.moveToNext())
            {
                tempParaListData=new PARAListData();
                tempParaListData.set_id(cursor.getString(0));
                tempParaListData.setPARA_NAME(cursor.getString(1));
                tempParaListData.setPARA_NAME_ENGLISH(cursor.getString(2));
                tempParaListData.setSTART_GROUP(cursor.getString(3));
                paraListData.add(tempParaListData);
            }
            TOTAL_ROW=paraListData.size();
        }
        else if(type==1)
        {
            paraListData.clear();
            Cursor cursor = Constants1.databaseHandler.getData("SELECT ID,SURA_NAME,SURA_NAME_ENGLISH, START_GROUP_NO FROM QURAN_SURA", Constants1.sqLiteDatabase);
            while(cursor.moveToNext())
            {
                tempParaListData=new PARAListData();
                tempParaListData.set_id(cursor.getString(0));
                tempParaListData.setPARA_NAME(cursor.getString(1));
                tempParaListData.setPARA_NAME_ENGLISH(cursor.getString(2));
                tempParaListData.setSTART_GROUP(cursor.getString(3));
                paraListData.add(tempParaListData);
            }
            TOTAL_ROW=paraListData.size();
        }
        view_list=new View[TOTAL_ROW];
        txtArabic=new ArabicTextView[TOTAL_ROW];
        txtEnglish=new CipherNormal[TOTAL_ROW];
        imgArrow=new ImageView[TOTAL_ROW];
        txtNumber=new UrduTextView[TOTAL_ROW];
        layout_sub_content=new LinearLayout[TOTAL_ROW];
        for(int i=0;i<TOTAL_ROW;i++)
        {
            view_list[i]=View.inflate(this,R.layout.inflate_surah_parah_list,null);
            txtArabic[i]=(ArabicTextView)view_list[i].findViewById(R.id.txtArabic);
            txtEnglish[i]=(CipherNormal)view_list[i].findViewById(R.id.txtEnglish);
            txtNumber[i]=(UrduTextView) view_list[i].findViewById(R.id.txtNumber);
            layout_sub_content[i]=(LinearLayout) view_list[i].findViewById(R.id.layout_sub_content);
            txtArabic[i].setText(""+paraListData.get(i).PARA_NAME);
            txtEnglish[i].setText(""+paraListData.get(i).PARA_NAME_ENGLISH);
            txtNumber[i].setText(""+Constants1.URDU_NUMBERS[i]);
            imgArrow[i]=(ImageView)view_list[i].findViewById(R.id.imgArrow);

            view_list[i].setOnClickListener(new ItemClickListener(i,paraListData.get(i).getSTART_GROUP()));

            layout_setting_surahparalist.addView(view_list[i]);
        }

    }

    public void resetGeneralSetting(int type)
    {
        layout_setting_surahparalist.setVisibility(View.INVISIBLE);
        ((LinearLayout)findViewById(R.id.layout_language_list)).setVisibility(View.GONE);
        ((LinearLayout)findViewById(R.id.layout_color_list)).setVisibility(View.GONE);
        ((LinearLayout)findViewById(R.id.layout_enterpage)).setVisibility(View.GONE);
        scroll_setting.setVisibility(View.VISIBLE);
        scroll_setting.scrollTo(0,0);
        layout_general_setting.setVisibility(View.VISIBLE);

        findViewById(R.id.layout_language).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(((LinearLayout)findViewById(R.id.layout_language_list)).getVisibility()==View.GONE)
                {
                    ((LinearLayout)findViewById(R.id.layout_language_list)).setVisibility(View.VISIBLE);
                    ((LinearLayout)findViewById(R.id.layout_color_list)).setVisibility(View.GONE);
                    ((LinearLayout)findViewById(R.id.layout_enterpage)).setVisibility(View.GONE);
                }
                else {
                    ((LinearLayout)findViewById(R.id.layout_language_list)).setVisibility(View.GONE);
                }
            }
        });
        findViewById(R.id.txtLanguage_Urdu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!Constants1.LANGUAGE.equalsIgnoreCase(Constants1.URDU))
                {
                    Constants1.LANGUAGE=Constants1.URDU;
                    Constants1.editor.putString("language",Constants1.URDU);
                    Constants1.editor.commit();
                    changeLanguage();
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // Do something after 5s = 5000ms
                            setTabs();
                        }
                    }, 500);
                }
            }
        });
        findViewById(R.id.txtLanguage_Gujarati).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!Constants1.LANGUAGE.equalsIgnoreCase(Constants1.GUJARATI))
                {
                    Constants1.LANGUAGE=Constants1.GUJARATI;
                    Constants1.editor.putString("language",Constants1.GUJARATI);
                    Constants1.editor.commit();
                    changeLanguage();
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // Do something after 5s = 5000ms
                            setTabs();
                        }
                    }, 500);


                }
            }
        });
        changeLanguage();
        findViewById(R.id.layout_color).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(((LinearLayout)findViewById(R.id.layout_color_list)).getVisibility()==View.GONE)
                {
                    ((LinearLayout)findViewById(R.id.layout_color_list)).setVisibility(View.VISIBLE);

                    ((LinearLayout)findViewById(R.id.layout_language_list)).setVisibility(View.GONE);
                    ((LinearLayout)findViewById(R.id.layout_enterpage)).setVisibility(View.GONE);

                }
                else {
                    ((LinearLayout)findViewById(R.id.layout_color_list)).setVisibility(View.GONE);
                }
            }
        });

        findViewById(R.id.layout_gotopage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(((LinearLayout)findViewById(R.id.layout_enterpage)).getVisibility()==View.GONE)
                {
                    ((LinearLayout)findViewById(R.id.layout_enterpage)).setVisibility(View.VISIBLE);
                    ((LinearLayout)findViewById(R.id.layout_language_list)).setVisibility(View.GONE);
                    ((LinearLayout)findViewById(R.id.layout_color_list)).setVisibility(View.GONE);
                }
                else {
                    ((LinearLayout)findViewById(R.id.layout_enterpage)).setVisibility(View.GONE);
                }
            }
        });

        findViewById(R.id.layout_translationColor).setOnClickListener(new TextClickListener(0));
        findViewById(R.id.layout_arabicColor).setOnClickListener(new TextClickListener(1));
        findViewById(R.id.layout_lineColor).setOnClickListener(new TextClickListener(2));

        final EditText txtPageNo=(EditText)findViewById(R.id.txtPageNo);
        txtPageNo.setText("");
        txtPageNo.setOnEditorActionListener(new EditText.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER
                        || actionId == EditorInfo.IME_ACTION_DONE) {



                    // Do your action
                    return true;
                }
                return true;
            }
        });

        findViewById(R.id.btnGotoPage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(txtPageNo.getText().toString().trim().length()>0)
                {
                    if(Integer.parseInt(txtPageNo.getText().toString().trim())>TOTAL)
                    {
                        Toast.makeText(getApplicationContext(),"Page Should be less than "+TOTAL,Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        openSettingScreen(-1);
                        pager.setCurrentItem(TOTAL-(Integer.parseInt(txtPageNo.getText().toString().trim())));
                        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Please enter Page No.",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public void changeLanguage(){

        if(Constants1.LANGUAGE.equalsIgnoreCase(Constants1.URDU))
        {
            ((TextView)findViewById(R.id.txtLanguage_Urdu)).setBackgroundResource(R.drawable.rounded_bg_brown_sel);
            ((TextView)findViewById(R.id.txtLanguage_Urdu)).setTextColor(Color.parseColor("#ffffff"));

            ((TextView)findViewById(R.id.txtLanguage_Gujarati)).setBackgroundResource(R.drawable.rounded_bg_brown);
            ((TextView)findViewById(R.id.txtLanguage_Gujarati)).setTextColor(getResources().getColor(R.color.colorPrimaryDark));

            txtTitleSettingGujarati.setVisibility(View.GONE);
            txtTitleSettingUrdu.setVisibility(View.VISIBLE);
        }
        else if(Constants1.LANGUAGE.equalsIgnoreCase(Constants1.GUJARATI))
        {
            ((TextView)findViewById(R.id.txtLanguage_Gujarati)).setBackgroundResource(R.drawable.rounded_bg_brown_sel);
            ((TextView)findViewById(R.id.txtLanguage_Gujarati)).setTextColor(Color.parseColor("#ffffff"));

            ((TextView)findViewById(R.id.txtLanguage_Urdu)).setBackgroundResource(R.drawable.rounded_bg_brown);
            ((TextView)findViewById(R.id.txtLanguage_Urdu)).setTextColor(getResources().getColor(R.color.colorPrimaryDark));

            txtTitleSettingGujarati.setVisibility(View.VISIBLE);
            txtTitleSettingUrdu.setVisibility(View.GONE);
        }

    }
    public class ItemClickListener implements View.OnClickListener {
        int pos;
        String start_group_no;

        public ItemClickListener(int pos,String start_group_no) {
            this.pos = pos;
            this.start_group_no=start_group_no;
        }
        public void onClick(View view)
        {
            pager.setCurrentItem(TOTAL-(Integer.parseInt(start_group_no)));
            openSettingScreen(-1);
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
                if (!mainDire.exists())
                {
                    mainDire.mkdirs();
                    Log.v(Constants1.TAG, "directory created------------------------>");
                }
                else
                {
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
                try
                {
                    if (Constants1.databaseHandler == null)
                        Constants1.databaseHandler = new DatabaseHandler(this);
                    if (Constants1.sqLiteDatabase == null)
                        Constants1.sqLiteDatabase = Constants1.databaseHandler.opendatabase(getApplicationContext());
                }
                catch (Exception e)
                {
                    Log.e(Constants1.TAG,"Error in Opening Database----->"+e);
                }
                setTabs();
            }
            else
            {
                try {
                    if (Constants1.databaseHandler == null)
                        Constants1.databaseHandler = new DatabaseHandler(this);
                    if (Constants1.sqLiteDatabase == null)
                        Constants1.sqLiteDatabase = Constants1.databaseHandler.opendatabase(getApplicationContext());
                }
                catch (Exception e)
                {
                    Log.e(Constants1.TAG,"Error in Opening Database----->"+e);
                }
                setTabs();
            }
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),""+e,Toast.LENGTH_LONG).show();
        }
    }
    public class TextClickListener implements View.OnClickListener
    {
        int type;
        View view;
        public TextClickListener(int type)
        {
            this.type=type;
        }
        public void onClick(View view)
        {
            int default_color=0;
            if(type==0)
            {
                default_color=Color.parseColor("#"+Constants1.sp.getString("perf_font_color_urdu", "000000"));
            }
            else if(type==1)
            {
                default_color=Color.parseColor("#"+Constants1.sp.getString("perf_font_color_arabic", "000000"));
            }
            else if(type==2)
            {
                default_color=Color.parseColor("#"+Constants1.sp.getString("perf_line_color", "000000"));
            }
            else
            {
                default_color=Color.parseColor("#FF0000");
            }
            ColorPickerDialog.newBuilder().setDialogType(ColorPickerDialog.TYPE_CUSTOM).setColor(default_color).setDialogId(type).show(QuranActivity.this);
        }
    }
    public void setTabs()
    {
        List<Fragment> fragments = getFragments();
        pager = (ViewPager) findViewById(R.id.activity_main_pager);
        pageAdapter = new MyPageAdapter(getSupportFragmentManager(), fragments);
        pager.setAdapter(pageAdapter);
        ((SmartTabLayout) findViewById(R.id.viewpagertab_urdu)).setViewPager(pager);
        pager.setCurrentItem(TOTAL-Constants1.sp.getInt("last_read",0));
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                Cursor cursor = Constants1.databaseHandler.getData("SELECT QP.PARA_NAME, QS.SURA_NAME " +
                        "from QURAN_ARABIC QA, QURAN_PARA QP, QURAN_SURA QS, QURAN_TRANSLATION QT\n" +
                        "where QA.PARA_NO =  QP.ID and QA.GROUP_NO="+(TOTAL-(position))+" and QA.SURA_NO = QS.ID\n" +
                        "and QA.ID=QT.ID", Constants1.sqLiteDatabase);

                while(cursor.moveToNext())
                {
                    txtParaName.setText(""+cursor.getString(0));
                    txtSurahName.setText(""+cursor.getString(1));
                }

                Log.v(Constants1.TAG,"Last_Read------->"+(TOTAL-position));
                Constants1.editor.putInt("last_read",(TOTAL-position));
                Constants1.editor.commit();
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
    private List<Fragment> getFragments()
    {
        List<Fragment> fList = new ArrayList<Fragment>();
        Vector<String> v=new Vector<String>();
        for(int i=TOTAL;i>0;i--)
        fList.add(QuranFragement.newInstance(i,0));

        return fList;
    }
    class MyPageAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragments;
        public MyPageAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;}

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

            if(Constants1.LANGUAGE.equalsIgnoreCase(Constants1.GUJARATI))
               return ""+(""+(TOTAL-position)).replaceAll("0","૦").replaceAll("1","૧")
                       .replaceAll("2","૨").replaceAll("3","૩").replaceAll("4","૪")
                       .replaceAll("5","૫").replaceAll("6","૬").replaceAll("7","૭").replaceAll("8","૮").replaceAll("9","૯");
            else
            return Constants1.URDU_NUMBERS[TOTAL-position-1];
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
            if(layout_setting.getVisibility()!=View.VISIBLE) {
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
            if(layout_setting.getVisibility()!=View.VISIBLE) {
                Constants1.editor.putInt("perf_font_size", (int) n).commit();
                Log.v(Constants1.TAG, "----Font Size-------->" + n);
            }
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
        else if(dialogId==1)
            Constants1.editor.putString("perf_font_color_arabic",hex).commit();
        else if(dialogId==2)
            Constants1.editor.putString("perf_line_color",hex).commit();

        findViewById(R.id.viewArabicColor).setBackgroundColor(Color.parseColor("#"+Constants1.sp.getString("perf_font_color_arabic", "000000")));
        findViewById(R.id.viewTranslationColor).setBackgroundColor(Color.parseColor("#"+Constants1.sp.getString("perf_font_color_urdu", "000000")));
        findViewById(R.id.viewLineColor).setBackgroundColor(Color.parseColor("#"+Constants1.sp.getString("perf_line_color", "000000")));
    }

    @Override
    public void onDialogDismissed(int dialogId) {

    }
    @Override
    public void onBackPressed() {
        if(layout_setting.getVisibility()==View.VISIBLE)
        {
            openSettingScreen(-1);
        }
        else
        {
            finish();
        }
    }

}