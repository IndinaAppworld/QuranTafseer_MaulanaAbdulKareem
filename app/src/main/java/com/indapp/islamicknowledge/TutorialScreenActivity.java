package com.indapp.islamicknowledge;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewParent;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.indapp.fonts.GujaratiTextView;
import com.indapp.fonts.UrduTextView;
import com.indapp.fragements.TutorialFragement;
import com.indapp.utils.Constants1;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class TutorialScreenActivity  extends FragmentActivity {
    TextView txtTutotialText;
    TextView txtNumber,txtSkip;
    String strNumber="";

    RelativeLayout header,layout_footer;
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
        setContentView(R.layout.activity_tutorial_screen);
        Constants1.initSharedPref(this);
        Constants1.LANGUAGE = Constants1.sp.getString("language", Constants1.GUJARATI);

        header=(RelativeLayout)findViewById(R.id.header);
        layout_footer=(RelativeLayout)findViewById(R.id.layout_footer);
        txtTutotialText=(TextView)findViewById(R.id.txtTutotialText);
        txtNumber=(TextView)findViewById(R.id.txtNumber);
        txtSkip=(TextView)findViewById(R.id.txtSkip);
        Typeface face ;

        if (Constants1.LANGUAGE.equalsIgnoreCase(Constants1.GUJARATI)) {
            DESCRIPTION=Constants1.TUTORIL_DESCRIPTION_GUJARATI;
            ((GujaratiTextView) findViewById(R.id.txtMainTitleGujarati)).setVisibility(View.VISIBLE);
            txtSkip.setText("ખતમ કરો");
            face = Typeface.createFromAsset(getAssets(),
                    "fonts/BHUJ UNICODE.ttf");
        } else //if (Constants1.LANGUAGE.equalsIgnoreCase(Constants1.URDU))
             {
                 ((UrduTextView) findViewById(R.id.txtMainTitleUrdu)).setVisibility(View.VISIBLE);


                 DESCRIPTION=Constants1.TUTORIL_DESCRIPTION_URDU;
                 txtSkip.setText("منسوخ کریں");
                 face= Typeface.createFromAsset(getAssets(),
                         "fonts/jameelnoorinastaleeq.ttf");

        }

        txtTutotialText.setTypeface(face);;//, Typeface.BOLD);
        txtNumber.setTypeface(face);;// Typeface.BOLD);
        txtSkip.setTypeface(face);;//, Typeface.BOLD);

        List<Fragment> fragments = getFragments();
        ViewPager pager = (ViewPager) findViewById(R.id.activity_main_pager);
        MyPageAdapter  pageAdapter = new MyPageAdapter(getSupportFragmentManager(), fragments);
        pager.setAdapter(pageAdapter);
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }


            @Override
            public void onPageSelected(int position) {

                header.setVisibility(View.VISIBLE);
                layout_footer.setVisibility(View.VISIBLE);

                if(position<Constants1.TUTORIAL_IMAGES_GUJ.length) {
                    txtTutotialText.setText("" + DESCRIPTION[position]);
                    strNumber = (position + 1) + "/" + (Constants1.TUTORIAL_IMAGES.length+1);
                    if (Constants1.LANGUAGE.equalsIgnoreCase(Constants1.GUJARATI)) {
                        strNumber = strNumber.replaceAll("0", "૦").replaceAll("1", "૧")
                                .replaceAll("2", "૨").replaceAll("3", "૩").replaceAll("4", "૪")
                                .replaceAll("5", "૫").replaceAll("6", "૬").replaceAll("7", "૭").replaceAll("8", "૮").replaceAll("9", "૯");
                    } else if (Constants1.LANGUAGE.equalsIgnoreCase(Constants1.URDU)) {
                        strNumber = strNumber.replaceAll("0", "۰").replaceAll("1", "۱")
                                .replaceAll("2", "۲").replaceAll("3", "۳").replaceAll("4", "۴")
                                .replaceAll("5", "۵").replaceAll("6", "۶").replaceAll("7", "۷").replaceAll("8", "۸").replaceAll("9", "۹");
                    }
                    txtNumber.setText("" + strNumber);
                }
                else {
                    header.setVisibility(View.GONE);
                    layout_footer.setVisibility(View.GONE);
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        txtSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        txtTutotialText.setText(""+DESCRIPTION[0]);


    }
    public String DESCRIPTION[]=new String[Constants1.TUTORIAL_IMAGES.length];
    int TOTAL= Constants1.TUTORIAL_IMAGES_GUJ.length+1;
    private List<Fragment> getFragments()
    {
        List<Fragment> fList = new ArrayList<Fragment>();
        Vector<String> v=new Vector<String>();
        for(int i=0;i<TOTAL;i++)
            fList.add(TutorialFragement.newInstance(i));

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
}
