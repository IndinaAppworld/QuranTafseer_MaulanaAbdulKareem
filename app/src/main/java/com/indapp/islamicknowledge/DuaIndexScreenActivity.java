package com.indapp.islamicknowledge;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.CpuUsageInfo;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewParent;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.indapp.beans.DataBean;
import com.indapp.fonts.GujaratiBoldTextView;
import com.indapp.fonts.GujaratiTextView;
import com.indapp.fonts.UrduTextView;
import com.indapp.utils.Constants1;
import com.indapp.utils.DatabaseHandler;

import java.util.ArrayList;

public class DuaIndexScreenActivity extends Activity  {



    ImageView imgBack,imgShare,imgShareNew;

    public ArrayList<DataBean> dataBeans=new ArrayList<>();
    LinearLayout contentLayout;
    String type="";
    Typeface typeface;

    public ArrayList<IndexBean>indexBeanArrayList=new ArrayList<>();
    IndexBean indexBean;
     ShimmerFrameLayout shimmerFrameLayout;


    ImageView imgSetting;
    LinearLayout layout_general_setting;
    RelativeLayout layout_quran;
    LinearLayout layout_setting;

    ImageView imgSettingClose;
    ScrollView scroll_setting;
    UrduTextView txtTitleSettingUrdu;
    GujaratiTextView txtTitleSettingGujarati;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        TextView titleBar = (TextView) getWindow().findViewById(android.R.id.title);
        if (titleBar != null) {
            // set text color, YELLOW as sample
            titleBar.setTextColor(Color.RED);
            // find parent view
            ViewParent parent = titleBar.getParent();
            if (parent != null && (parent instanceof View)) {
                // set background on parent, BRICK as sample
                View parentView = (View) parent;
                parentView.setBackgroundColor(Color.RED);
            }
        }
        getWindow().setStatusBarColor(getResources().getColor(R.color.duaprimarycolor));

        setContentView(R.layout.activity_bookindex_screen);

        imgBack = (ImageView) findViewById(R.id.imgBack);

        layout_quran=(RelativeLayout)findViewById(R.id.layout_quran);
        imgSetting=(ImageView)findViewById(R.id.imgSetting);

        layout_setting=(LinearLayout) findViewById(R.id.layout_setting);
        contentLayout=(LinearLayout)findViewById(R.id.contentLayout);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        Constants1.initSharedPref(this);
        Constants1.LANGUAGE = Constants1.sp.getString("language", Constants1.GUJARATI);



        shimmerFrameLayout=(ShimmerFrameLayout)findViewById(R.id.shimmer_view_container);

        updateList();


        findViewById(R.id.imgBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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
//        imgSetting.setVisibility(View.GONE);
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
//        findViewById(R.id.viewArabicColor).setBackgroundColor(Color.parseColor("#"+Constants1.sp.getString("perf_font_color_arabic", "000000")));
//        findViewById(R.id.viewTranslationColor).setBackgroundColor(Color.parseColor("#"+Constants1.sp.getString("perf_font_color_urdu", "000000")));
//        findViewById(R.id.viewLineColor).setBackgroundColor(Color.parseColor("#"+Constants1.sp.getString("perf_line_color", "000000")));

        findViewById(R.id.viewArabicColor).setBackgroundColor(Color.parseColor("#"+Constants1.sp.getString("perf_font_color_arabic", "000000")));
        findViewById(R.id.viewTranslationColor).setBackgroundColor(Color.parseColor("#"+Constants1.sp.getString("perf_font_color_urdu", "000000")));
        findViewById(R.id.viewLineColor).setBackgroundColor(Color.parseColor("#"+Constants1.sp.getString("perf_line_color", "000000")));



        ((LinearLayout)findViewById(R.id.layout_language_list)).setVisibility(View.GONE);
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
//        findViewById(R.id.layout_peshlafz).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent(Intent.ACTION_VIEW);
//                intent.setClass(DuaIndexScreenActivity.this,PDFViewActivityScreen.class);
//                intent.putExtra("type","peshlafz");
//                startActivity(intent);
//            }
//        });
//        findViewById(R.id.layout_aboutus).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent(Intent.ACTION_VIEW);
//                intent.setClass(DuaIndexScreenActivity.this,PDFViewActivityScreen.class);
//                intent.putExtra("type","aboutus");
//                startActivity(intent);
//            }
//        });
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

                            updateList();
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
                            updateList();
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
    int TOTAL_ROW=10;
    ImageView img[],imgArrow[];
    TextView txtTitle[];
    TextView txtNumber[];
    View view[];
    ImageView imgFav[];
    public void updateList()
    {

        ((UrduTextView) findViewById(R.id.txtMainTitleUrdu)).setVisibility(View.GONE);
        ((GujaratiBoldTextView) findViewById(R.id.txtMainTitleGujarati)).setVisibility(View.GONE);

        if (Constants1.LANGUAGE.equalsIgnoreCase(Constants1.GUJARATI)) {

            typeface = Typeface.createFromAsset(getAssets(),
                    "fonts/BHUJ UNICODE.ttf");
            ((GujaratiBoldTextView) findViewById(R.id.txtMainTitleGujarati)).setVisibility(View.VISIBLE);
            if(type.equalsIgnoreCase("peshlafz"))
                ((GujaratiBoldTextView) findViewById(R.id.txtMainTitleGujarati)).setText("પેશ લફઝ");
            else if(type.equalsIgnoreCase("aboutus")) {
                ((GujaratiBoldTextView) findViewById(R.id.txtMainTitleGujarati)).setText("અમારા વિશે");

                Constants1.setFontTypeFaceLanguage( ((TextView) findViewById(R.id.txtDaroodSharifOption)),getApplicationContext(),false);
            }
            ((TextView) findViewById(R.id.txtDaroodSharifOption)).setText("દુરૂદ\u200C શરીફ પઢવા માટે અહીંયા ક્લિક કરો");

        } else if (Constants1.LANGUAGE.equalsIgnoreCase(Constants1.URDU)) {
            typeface = Typeface.createFromAsset(getAssets(), "fonts/jameelnoorinastaleeq.ttf");

            ((UrduTextView) findViewById(R.id.txtMainTitleUrdu)).setVisibility(View.VISIBLE);
            if(type.equalsIgnoreCase("peshlafz"))
                ((UrduTextView) findViewById(R.id.txtMainTitleUrdu)).setText("پیش لفظ");
            else if(type.equalsIgnoreCase("aboutus"))
            {
                ((UrduTextView) findViewById(R.id.txtMainTitleUrdu)).setText("ہمارے بارے میں");
            }
            ((TextView) findViewById(R.id.txtDaroodSharifOption)).setText("درود شریف پڑھنے کے لیے یہاں کلک کریں");

        }
        Constants1.setFontTypeFaceLanguage( ((TextView) findViewById(R.id.txtDaroodSharifOption)),getApplicationContext(),false);


        indexBeanArrayList.clear();
        if(Constants1.databaseHandler==null)
            Constants1.databaseHandler  =new DatabaseHandler(this);
        if(Constants1.sqLiteDatabase==null)
            Constants1.sqLiteDatabase=Constants1.databaseHandler.opendatabase(getApplicationContext());

        Cursor cursor = Constants1.databaseHandler.getData("SELECT ID,HEADING_"+Constants1.LANGUAGE+" from DUA where  HEADING_"+Constants1.LANGUAGE+" is not null", Constants1.sqLiteDatabase);
        while(cursor.moveToNext())
        {
            Log.v(Constants1.TAG,"TITLe---->"+cursor.getString(1));
            indexBean=new IndexBean();
            indexBean.setID(cursor.getString(0));
            indexBean.setTITLE(cursor.getString(1));
            indexBeanArrayList.add(indexBean);
        }

        TOTAL_ROW=indexBeanArrayList.size();
        contentLayout.removeAllViews();
        view=new View[TOTAL_ROW];
        img=new ImageView[TOTAL_ROW];
        imgArrow=new ImageView[TOTAL_ROW];
        txtTitle=new TextView[TOTAL_ROW];
        txtNumber=new TextView[TOTAL_ROW];
        imgFav=new ImageView[TOTAL_ROW];
        for(int i=0;i<TOTAL_ROW;i++)
        {
            if(Constants1.LANGUAGE.equalsIgnoreCase(Constants1.URDU))
            {
                view[i] = View.inflate(this, R.layout.inflate_duaindex_list_item_urdu, null);

            }
            else if(Constants1.LANGUAGE.equalsIgnoreCase(Constants1.GUJARATI))
            {
                view[i] = View.inflate(this, R.layout.inflate_duaindex_list_item, null);
            }


            ((TextView)view[i].findViewById(R.id.txtTitle)).setText(indexBeanArrayList.get(i).getTITLE());
            txtNumber[i]=(TextView)view[i].findViewById(R.id.txtNumber);
            imgFav[i]=(ImageView) view[i].findViewById(R.id.imgFav);

            if(Constants1.sp.contains("DUA"+"_"+((i+1)))==true)
            {
                imgFav[i].setImageResource(R.drawable.favorite_dua_sel);
            }


            if(!Constants1.LANGUAGE.equalsIgnoreCase(Constants1.URDU))
            txtNumber[i].setText(Constants1.replaceNumbers(""+(i+1) +"."));
            else txtNumber[i].setText(Constants1.replaceNumbers("."+(i+1) +""));

            view[i].setOnClickListener(new ItemClickListener(i));
            imgFav[i].setOnClickListener(new FavClickListener((i+1),imgFav[i]));
            contentLayout.addView(view[i]);
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
    public class FavClickListener implements View.OnClickListener
    {
        int pageno;
        ImageView imgFav;
        public FavClickListener(int pageno,ImageView imgFav)
        {
            this.pageno=pageno;
            this.imgFav=imgFav;
        }
        public void onClick(View view)
        {
            Constants1.initSharedPref(DuaIndexScreenActivity.this);

            if(Constants1.sp.contains("DUA_"+pageno)==false)
            {
                Constants1.editor.putString("DUA_"+pageno,""+pageno);
                Constants1.editor.commit();

                imgFav.setImageResource(R.drawable.favorite_dua_sel);
                Toast.makeText(getApplicationContext(),"Added to Favourite",Toast.LENGTH_SHORT).show();
            }
            else
            {
                Constants1.editor.remove("DUA_"+pageno);
                Constants1.editor.commit();

                imgFav.setImageResource(R.drawable.favorite_dua_unesl);
                Toast.makeText(getApplicationContext(),"Removed from Favourite",Toast.LENGTH_SHORT).show();
            }
        }
    }
    public class ItemClickListener implements View.OnClickListener
    {
        int pos;
        public ItemClickListener(int pos)
        {
            this.pos=pos;
        }
        public void onClick(View view)
        {
            Intent intent=new Intent(Intent.ACTION_VIEW);
            intent.setClass(DuaIndexScreenActivity.this,DuaScreenActivity.class);
            intent.putExtra("pageno",""+pos);
            startActivity(intent);
        }
    }
    public class IndexBean
    {
        public String getTITLE() {
            return TITLE;
        }

        public void setTITLE(String TITLE) {
            this.TITLE = TITLE;
        }

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String TITLE,ID;
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