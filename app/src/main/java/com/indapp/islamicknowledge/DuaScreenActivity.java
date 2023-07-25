package com.indapp.islamicknowledge;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewParent;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.indapp.fonts.ArabicTextView;
import com.indapp.fonts.GujaratiBoldTextView;
import com.indapp.fonts.GujaratiTextView;
import com.indapp.fonts.UrduTextView;
import com.indapp.fragements.DuaFragement;
import com.indapp.utils.Constants1;
import com.indapp.utils.DatabaseHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class DuaScreenActivity extends FragmentActivity {

    LinearLayout layout_general_setting;

    RelativeLayout layout_quran;
    ImageView imgSetting;

    private ViewPager pager;
    MyPageAdapter pageAdapter;

    public ArabicTextView txtParaName,txtSurahName;
    LinearLayout layout_setting;
    ImageView imgSettingClose;
    LinearLayout layout_setting_surahparalist;
    ScrollView scroll_setting;
    boolean isChangedStat=false;
    CheckBox chkSettingTranslation, getChkSettingTafseer;

    ImageView imgListGridIcon;
    CheckBox chkTranslationTarjumal;
    TextView lblArabicOnlyText;
    TextView txtPageNo;
    UrduTextView txtPageNo_Urdu;


    UrduTextView txtTitleSettingUrdu;
    GujaratiTextView txtTitleSettingGujarati;
    String type_view="";
    RelativeLayout layout_header;
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
        getWindow().setStatusBarColor(getResources().getColor(R.color.duaprimarycolor));
        type_view=getIntent().getExtras().getString("type_view");
        if(type_view.equalsIgnoreCase(Constants1.TYPE_DAROOD))
        {getWindow().setStatusBarColor(getResources().getColor(R.color.daroodprimarycolor));
        }
        else {
            getWindow().setStatusBarColor(getResources().getColor(R.color.duaprimarycolor));
        }


        if(type_view.equalsIgnoreCase(Constants1.TYPE_DUA))TOTAL=104;
        else if(type_view.equalsIgnoreCase(Constants1.TYPE_DAROOD)) TOTAL=54;

        Constants1.initSharedPref(this);
        Constants1.LANGUAGE = Constants1.sp.getString("language", Constants1.GUJARATI);
        setContentView(R.layout.activity_dua_screen);

        layout_header=(RelativeLayout)findViewById(R.id.layout_header) ;
        ImageView  imgBack = (ImageView) findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        layout_quran=(RelativeLayout)findViewById(R.id.layout_quran);
        imgSetting=(ImageView)findViewById(R.id.imgSetting);

        layout_setting=(LinearLayout) findViewById(R.id.layout_setting);
        mScaleDetector = new ScaleGestureDetector(this, new ScaleManager());


        txtPageNo=(TextView) findViewById(R.id.txtPageNo);

//        copydatabase();

        txtParaName=(ArabicTextView)findViewById(R.id.txtParaName);
        txtSurahName=(ArabicTextView)findViewById(R.id.txtSurahName);
        txtParaName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


//                openSettingScreen(0);


            }
        });
        txtSurahName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


//                openSettingScreen(1);


            }
        });
        txtTitleSettingUrdu=(UrduTextView)findViewById(R.id.txtTitleSettingUrdu);
        txtTitleSettingGujarati=(GujaratiTextView) findViewById(R.id.txtTitleSettingGujarati);


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


        if(Constants1.LANGUAGE.equalsIgnoreCase(Constants1.URDU))
            layout_general_setting.addView(LayoutInflater.from(getApplicationContext()).inflate(R.layout.include_setting_urdu_dua, layout_setting, false));
        else if(Constants1.LANGUAGE.equalsIgnoreCase(Constants1.GUJARATI))
            layout_general_setting.addView(LayoutInflater.from(getApplicationContext()).inflate(R.layout.include_setting_gujarati_dua, layout_setting, false));


        imgSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSettingScreen(3);
            }
        });

        changeLanguage();

//        findViewById(R.id.viewArabicColor).setBackgroundColor(Color.parseColor("#"+Constants1.sp.getString("perf_font_color_arabic", "000000")));
//        findViewById(R.id.viewTranslationColor).setBackgroundColor(Color.parseColor("#"+Constants1.sp.getString("perf_font_color_urdu", "000000")));
//        findViewById(R.id.viewLineColor).setBackgroundColor(Color.parseColor("#"+Constants1.sp.getString("perf_line_color", "000000")));

        findViewById(R.id.imgSearch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setClass(DuaScreenActivity.this,SearchActivity.class);
                startActivity(intent);
            }
        });

//        chkTranslationTarjumal=(CheckBox) findViewById(R.id.chkTranslationTarjuma);
//        lblArabicOnlyText=(TextView)findViewById(R.id.lblArabicOnlyText);

//        imgListGridIcon=(ImageView)findViewById(R.id.imgListGridIcon);
//        if(Constants1.sp.getString("format","grid").equalsIgnoreCase("grid"))
//        {
//            imgListGridIcon.setImageResource(R.drawable.icon_grid);
//        }
//        else
//        {
//            imgListGridIcon.setImageResource(R.drawable.icon_list);
//        }
//        if(Constants1.sp.getBoolean("arabic",false))
//        {
//            imgListGridIcon.setVisibility(View.GONE);
//
//            chkTranslationTarjumal.setChecked(false);
//        }
//        else
//        {
//            chkTranslationTarjumal.setChecked(true);
//        }
//        imgListGridIcon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if(Constants1.sp.getString("format","grid").equalsIgnoreCase("grid"))
//                {
//
//                    Constants1.editor.putString("format","list");
//                    imgListGridIcon.setImageResource(R.drawable.icon_list);
//                }
//                else
//                {
//                    Constants1.editor.putString("format","grid");
//                    imgListGridIcon.setImageResource(R.drawable.icon_grid);
//                }
//                Constants1.editor.commit();
//
//
//            }
//        });



//        pageNo=(SwissNormalFont)findViewById(R.id.pgNo);
        List<Fragment> fragments = getFragments();
        pager = (ViewPager) findViewById(R.id.activity_main_pager);
        pageAdapter = new MyPageAdapter(getSupportFragmentManager(), fragments);
        pager.setAdapter(pageAdapter);
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                txtPageNo.setText(Constants1.replaceNumbers((position+1)+"/"+TOTAL));
            }

            @Override
            public void onPageSelected(int position) {
            }


            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        if(Constants1.databaseHandler==null)
            Constants1.databaseHandler  =new DatabaseHandler(this);
        if(Constants1.sqLiteDatabase==null)
            Constants1.sqLiteDatabase=Constants1.databaseHandler.opendatabase(getApplicationContext());

//        pageNo.setText("1/ "+TOTAL);


        Constants1.setFontTypeFaceLanguage(txtPageNo,getApplicationContext(),true);

        if(getIntent().getExtras().getString("pageno")!=null)
        {

            pager.setCurrentItem(Integer.parseInt(getIntent().getExtras().getString("pageno")));
        }

        if(type_view.equalsIgnoreCase(Constants1.TYPE_DAROOD))
        {
            layout_header.setBackgroundResource(R.drawable.background_daroodpatti);
        }
        else {
            layout_header.setBackgroundResource(R.drawable.background_header_dua);
        }
    }
    public void changeLanguage(){

        if(Constants1.LANGUAGE.equalsIgnoreCase(Constants1.URDU))
        {
            ((TextView)findViewById(R.id.txtLanguage_Urdu)).setBackgroundResource(R.drawable.rounded_bg_green_sel);
            ((TextView)findViewById(R.id.txtLanguage_Urdu)).setTextColor(Color.parseColor("#ffffff"));

            ((TextView)findViewById(R.id.txtLanguage_Gujarati)).setBackgroundResource(R.drawable.rounded_bg_green);
            ((TextView)findViewById(R.id.txtLanguage_Gujarati)).setTextColor(getResources().getColor(R.color.duaprimarycolor));

            txtTitleSettingGujarati.setVisibility(View.GONE);
            txtTitleSettingUrdu.setVisibility(View.VISIBLE);

//            imgQuran.setImageResource(R.drawable.menu_icon1_urdu);
//            imgDua.setImageResource(R.drawable.menu_icon2_urdu);

        }
        else if(Constants1.LANGUAGE.equalsIgnoreCase(Constants1.GUJARATI))
        {
            ((TextView)findViewById(R.id.txtLanguage_Gujarati)).setBackgroundResource(R.drawable.rounded_bg_green_sel);
            ((TextView)findViewById(R.id.txtLanguage_Gujarati)).setTextColor(Color.parseColor("#ffffff"));

            ((TextView)findViewById(R.id.txtLanguage_Urdu)).setBackgroundResource(R.drawable.rounded_bg_green);
            ((TextView)findViewById(R.id.txtLanguage_Urdu)).setTextColor(getResources().getColor(R.color.duaprimarycolor));

            txtTitleSettingGujarati.setVisibility(View.VISIBLE);
            txtTitleSettingUrdu.setVisibility(View.GONE);

//            imgQuran.setImageResource(R.drawable.menu_icon1_gujrati);
//            imgDua.setImageResource(R.drawable.menu_icon2_gujrati);

        }
    }



    public void openSettingScreen(int type)
    {

        RelativeLayout layout_setting_header=(RelativeLayout)findViewById(R.id.layout_setting_header);
        if(layout_setting.getVisibility()==View.INVISIBLE) {

            txtTitleSettingUrdu.setText("ترتیب");
            txtTitleSettingGujarati.setText("સેટિંગ");

            resetGeneralSetting(type);

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
            scroll_setting.setVisibility(View.INVISIBLE);
            layout_general_setting.setVisibility(View.INVISIBLE);

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

                            layout_general_setting.removeAllViews();
                            if(Constants1.LANGUAGE.equalsIgnoreCase(Constants1.URDU))
                                layout_general_setting.addView(LayoutInflater.from(getApplicationContext()).inflate(R.layout.include_setting_urdu_dua, layout_setting, false));
                            else if(Constants1.LANGUAGE.equalsIgnoreCase(Constants1.GUJARATI))
                                layout_general_setting.addView(LayoutInflater.from(getApplicationContext()).inflate(R.layout.include_setting_gujarati_dua, layout_setting, false));

                        }
                    }, 100);

                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }
    }
    public void resetGeneralSetting(int type)
    {
        findViewById(R.id.viewArabicColor).setBackgroundColor(Color.parseColor("#"+Constants1.sp.getString("perf_font_color_arabic", "000000")));
        findViewById(R.id.viewTranslationColor).setBackgroundColor(Color.parseColor("#"+Constants1.sp.getString("perf_font_color_urdu", "000000")));
        findViewById(R.id.viewLineColor).setBackgroundColor(Color.parseColor("#"+Constants1.sp.getString("perf_line_color", "000000")));



//        ((LinearLayout)findViewById(R.id.layout_language_list)).setVisibility(View.GONE);
        ((LinearLayout)findViewById(R.id.layout_color_list)).setVisibility(View.GONE);
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
//                            setTabs();

//                            updateList();
                        }
                    }, 500);

                }
            }
        });
        findViewById(R.id.layout_share).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, Constants1.share_data);
                startActivity(intent);
            }
        });
        findViewById(R.id.layout_rateapp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent rateIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.indapp.islamicknowledge"));
                startActivity(rateIntent);

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
//                            setTabs();
//                            updateList();
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

                }
                else {
                    ((LinearLayout)findViewById(R.id.layout_color_list)).setVisibility(View.GONE);
                }
            }
        });

//        findViewById(R.id.layout_tutorial).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent =new Intent(Intent.ACTION_VIEW);
//                intent.setClass(DuaIndexScreenActivity.this, TutorialScreenActivity.class);
//                startActivity(intent);
//            }
//        });

//        findViewById(R.id.layout_translationColor).setOnClickListener(new MenuScreenActivity.TextClickListener(0));
//        findViewById(R.id.layout_arabicColor).setOnClickListener(new MenuScreenActivity.TextClickListener(1));
//        findViewById(R.id.layout_lineColor).setOnClickListener(new MenuScreenActivity.TextClickListener(2));


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

            return "title";
        }
    }
    int TOTAL=104;
    private List<Fragment> getFragments(){
        List<Fragment> fList = new ArrayList<Fragment>();
        Vector<String> v=new Vector<String>();
        for(int i=0;i<TOTAL;i++)
        {
            fList.add(DuaFragement.newInstance(""+(i+1),type_view));
        }
        return fList;
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
    }
