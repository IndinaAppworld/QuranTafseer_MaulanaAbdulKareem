package com.indapp.islamicknowledge;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewParent;
import android.view.ViewStub;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.indapp.fonts.GujaratiTextView;
import com.indapp.fonts.UrduTextView;
import com.indapp.utils.Constants1;
import com.jaredrummler.android.colorpicker.ColorPickerDialog;
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener;

public class MenuScreenActivity extends FragmentActivity implements ColorPickerDialogListener {


    ImageView imgSetting;
    LinearLayout layout_general_setting;

    LinearLayout layout_setting;
    ImageView imgSettingClose;

    ImageView imgQuran,imgDua;
    ViewStub menu_stub;
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
        setContentView(R.layout.activity_menu_screen);
        menu_stub = (ViewStub) findViewById(R.id.layout_stub);

        imgQuran=(ImageView)findViewById(R.id.imgQuraan);
        imgDua=(ImageView)findViewById(R.id.imgDua);
        if(Constants1.LANGUAGE.equalsIgnoreCase(Constants1.GUJARATI))
        {
            imgQuran.setImageResource(R.drawable.menu_icon1_gujrati);
            imgDua.setImageResource(R.drawable.menu_icon2_gujrati);
//            menu_stub.setLayoutResource(R.layout.include_setting_menu_gujarati);
//            View inflated = menu_stub.inflate();
        }
        else if(Constants1.LANGUAGE.equalsIgnoreCase(Constants1.URDU))
        {
            imgQuran.setImageResource(R.drawable.menu_icon1_urdu);
            imgDua.setImageResource(R.drawable.menu_icon2_urdu);
//            menu_stub.setLayoutResource(R.layout.include_setting_menu_urdu);
//            View inflated = menu_stub.inflate();
        }


//        View inflated = menu_stub.inflate();

        imgQuran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(Intent.ACTION_VIEW);
                intent.setClass(MenuScreenActivity.this, QuranActivity.class);
                startActivity(intent);
            }
        });
        imgDua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent =new Intent(Intent.ACTION_VIEW);
                intent.setClass(MenuScreenActivity.this, DuaIndexScreenActivity.class);
                intent.putExtra("type_view",""+Constants1.TYPE_DUA);
                startActivity(intent);
            }
        });

        imgSetting=(ImageView)findViewById(R.id.imgSetting);

        layout_setting=(LinearLayout) findViewById(R.id.layout_setting);
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


        findViewById(R.id.imgInfo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setClass(MenuScreenActivity.this, InfoActivity.class);
                startActivityForResult(intent, 300);

            }
        });




        scroll_setting=(ScrollView)findViewById(R.id.scroll_setting);
        layout_general_setting=(LinearLayout)findViewById(R.id.include_general_setting);


        if(Constants1.LANGUAGE.equalsIgnoreCase(Constants1.URDU))
            layout_general_setting.addView(LayoutInflater.from(getApplicationContext()).inflate(R.layout.include_setting_menu_urdu, layout_setting, false));
        else if(Constants1.LANGUAGE.equalsIgnoreCase(Constants1.GUJARATI))
            layout_general_setting.addView(LayoutInflater.from(getApplicationContext()).inflate(R.layout.include_setting_menu_gujarati, layout_setting, false));



        imgSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSettingScreen(3);
            }
        });
        scroll_setting=(ScrollView)findViewById(R.id.scroll_setting);

        if(Constants1.sp.getBoolean("tutorial",false)==false)
        {
            Intent intent=new Intent(Intent.ACTION_VIEW);
            intent.setClass(MenuScreenActivity.this,TutorialScreenActivity.class);
            startActivity(intent);
            Constants1.editor.putBoolean("tutorial",true);
            Constants1.editor.commit();
        }

        findViewById(R.id.viewArabicColor).setBackgroundColor(Color.parseColor("#"+Constants1.sp.getString("perf_font_color_arabic", "000000")));
        findViewById(R.id.viewTranslationColor).setBackgroundColor(Color.parseColor("#"+Constants1.sp.getString("perf_font_color_urdu", "000000")));
        findViewById(R.id.viewLineColor).setBackgroundColor(Color.parseColor("#"+Constants1.sp.getString("perf_line_color", "000000")));
    }
    UrduTextView txtTitleSettingUrdu;
    GujaratiTextView txtTitleSettingGujarati;
    ScrollView scroll_setting;
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
                                layout_general_setting.addView(LayoutInflater.from(getApplicationContext()).inflate(R.layout.include_setting_menu_urdu, layout_setting, false));
                            else if(Constants1.LANGUAGE.equalsIgnoreCase(Constants1.GUJARATI))
                                layout_general_setting.addView(LayoutInflater.from(getApplicationContext()).inflate(R.layout.include_setting_menu_gujarati, layout_setting, false));

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
//        findViewById(R.id.viewArabicColor).setBackgroundColor(Color.parseColor("#"+Constants1.sp.getString("perf_font_color_arabic", "000000")));
//        findViewById(R.id.viewTranslationColor).setBackgroundColor(Color.parseColor("#"+Constants1.sp.getString("perf_font_color_urdu", "000000")));
//        findViewById(R.id.viewLineColor).setBackgroundColor(Color.parseColor("#"+Constants1.sp.getString("perf_line_color", "000000")));

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
        findViewById(R.id.layout_peshlafz).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setClass(MenuScreenActivity.this,PDFViewActivityScreen.class);
                intent.putExtra("type","peshlafz");
                startActivity(intent);
            }
        });
        findViewById(R.id.layout_aboutus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setClass(MenuScreenActivity.this,PDFViewActivityScreen.class);
                intent.putExtra("type","aboutus");
                startActivity(intent);
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

        findViewById(R.id.layout_tutorial).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(Intent.ACTION_VIEW);
                intent.setClass(MenuScreenActivity.this, TutorialScreenActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.layout_translationColor).setOnClickListener(new TextClickListener(0));
        findViewById(R.id.layout_arabicColor).setOnClickListener(new TextClickListener(1));
        findViewById(R.id.layout_lineColor).setOnClickListener(new TextClickListener(2));


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
            ColorPickerDialog.newBuilder().setDialogType(ColorPickerDialog.TYPE_CUSTOM).setColor(default_color).setDialogId(type).show(MenuScreenActivity.this);
        }
    }
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

    public void changeLanguage(){

        if(Constants1.LANGUAGE.equalsIgnoreCase(Constants1.URDU))
        {
            ((TextView)findViewById(R.id.txtLanguage_Urdu)).setBackgroundResource(R.drawable.rounded_bg_brown_sel);
            ((TextView)findViewById(R.id.txtLanguage_Urdu)).setTextColor(Color.parseColor("#ffffff"));

            ((TextView)findViewById(R.id.txtLanguage_Gujarati)).setBackgroundResource(R.drawable.rounded_bg_brown);
            ((TextView)findViewById(R.id.txtLanguage_Gujarati)).setTextColor(getResources().getColor(R.color.colorPrimaryDark));

            txtTitleSettingGujarati.setVisibility(View.GONE);
            txtTitleSettingUrdu.setVisibility(View.VISIBLE);

            imgQuran.setImageResource(R.drawable.menu_icon1_urdu);
            imgDua.setImageResource(R.drawable.menu_icon2_urdu);

        }
        else if(Constants1.LANGUAGE.equalsIgnoreCase(Constants1.GUJARATI))
        {
            ((TextView)findViewById(R.id.txtLanguage_Gujarati)).setBackgroundResource(R.drawable.rounded_bg_brown_sel);
            ((TextView)findViewById(R.id.txtLanguage_Gujarati)).setTextColor(Color.parseColor("#ffffff"));

            ((TextView)findViewById(R.id.txtLanguage_Urdu)).setBackgroundResource(R.drawable.rounded_bg_brown);
            ((TextView)findViewById(R.id.txtLanguage_Urdu)).setTextColor(getResources().getColor(R.color.colorPrimaryDark));

            txtTitleSettingGujarati.setVisibility(View.VISIBLE);
            txtTitleSettingUrdu.setVisibility(View.GONE);

            imgQuran.setImageResource(R.drawable.menu_icon1_gujrati);
            imgDua.setImageResource(R.drawable.menu_icon2_gujrati);

        }
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