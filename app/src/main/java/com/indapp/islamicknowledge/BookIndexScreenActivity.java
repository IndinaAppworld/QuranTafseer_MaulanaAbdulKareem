package com.indapp.islamicknowledge;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.indapp.beans.DataBean;
import com.indapp.fonts.GujaratiTextView;
import com.indapp.fonts.UrduTextView;
import com.indapp.utils.Constants1;
import com.indapp.utils.DatabaseHandler;

import java.util.ArrayList;

public class BookIndexScreenActivity extends Activity  {



    ImageView imgBack,imgShare,imgShareNew;

    public ArrayList<DataBean> dataBeans=new ArrayList<>();
    LinearLayout contentLayout;
    String type="";
    Typeface typeface;

    public ArrayList<IndexBean>indexBeanArrayList=new ArrayList<>();
    IndexBean indexBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        // load title bar from Android layout
        TextView titleBar = (TextView) getWindow().findViewById(android.R.id.title);
        if (titleBar != null) {
            // set text color, YELLOW as sample
            titleBar.setTextColor(getResources().getColor(R.color.colorPrimary));
            // find parent view
            ViewParent parent = titleBar.getParent();
            if (parent != null && (parent instanceof View)) {
                // set background on parent, BRICK as sample
                View parentView = (View) parent;
                parentView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            }
        }
        setContentView(R.layout.activity_bookindex_screen);

        imgBack = (ImageView) findViewById(R.id.imgBack);


        contentLayout=(LinearLayout)findViewById(R.id.contentLayout);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        Constants1.initSharedPref(this);
        Constants1.LANGUAGE = Constants1.sp.getString("language", Constants1.GUJARATI);
        if (Constants1.LANGUAGE.equalsIgnoreCase(Constants1.GUJARATI)) {

            typeface = Typeface.createFromAsset(getAssets(),
                    "fonts/BHUJ UNICODE.ttf");
            ((GujaratiTextView) findViewById(R.id.txtMainTitleGujarati)).setVisibility(View.VISIBLE);
            if(type.equalsIgnoreCase("peshlafz"))
                ((GujaratiTextView) findViewById(R.id.txtMainTitleGujarati)).setText("પેશ લફઝ");
            else if(type.equalsIgnoreCase("aboutus")) {
                ((GujaratiTextView) findViewById(R.id.txtMainTitleGujarati)).setText("અમારા વિશે");


            }

        } else if (Constants1.LANGUAGE.equalsIgnoreCase(Constants1.URDU)) {
            typeface = Typeface.createFromAsset(getAssets(), "fonts/jameelnoorinastaleeq.ttf");

            ((UrduTextView) findViewById(R.id.txtMainTitleUrdu)).setVisibility(View.VISIBLE);
            if(type.equalsIgnoreCase("peshlafz"))
                ((UrduTextView) findViewById(R.id.txtMainTitleUrdu)).setText("پیش لفظ");
            else if(type.equalsIgnoreCase("aboutus"))
            {
                ((UrduTextView) findViewById(R.id.txtMainTitleUrdu)).setText("ہمارے بارے میں");


            }

        }


        if(Constants1.databaseHandler==null)
            Constants1.databaseHandler  =new DatabaseHandler(this);
        if(Constants1.sqLiteDatabase==null)
            Constants1.sqLiteDatabase=Constants1.databaseHandler.opendatabase(getApplicationContext());
        Cursor cursor = Constants1.databaseHandler.getData("SELECT ID,HEADING_GUJARATI from DUA where  HEADING_GUJARATI is not null", Constants1.sqLiteDatabase);
        while(cursor.moveToNext())
        {
            Log.v(Constants1.TAG,"TITLe---->"+cursor.getString(1));
            indexBean=new IndexBean();
            indexBean.setID(cursor.getString(0));
            indexBean.setTITLE(cursor.getString(1));
            indexBeanArrayList.add(indexBean);

        }
        updateList();


        findViewById(R.id.imgBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    int TOTAL_ROW=10;
    ImageView img[],imgArrow[];
    GujaratiTextView txtTitle[];
    View view[];
    public void updateList()
    {
        TOTAL_ROW=indexBeanArrayList.size();
        contentLayout.removeAllViews();
        view=new View[TOTAL_ROW];
        img=new ImageView[TOTAL_ROW];
        imgArrow=new ImageView[TOTAL_ROW];
        txtTitle=new GujaratiTextView[TOTAL_ROW];
        for(int i=0;i<TOTAL_ROW;i++)
        {

                view[i] = View.inflate(this, R.layout.inflate_duaindex_list_item, null);
                ((GujaratiTextView)view[i].findViewById(R.id.txtTitle)).setText(indexBeanArrayList.get(i).getTITLE());

//            else if(Constants1.CURRENT_TYPE==Constants1.TYPE_ASMAHUSNA)
//            {
//                view[i] = View.inflate(this, R.layout.inflate_list_item_arabic, null);
//                ((ArabicFont)view[i].findViewById(R.id.txtTitle)).setText(indexBeanArrayList.get(i).getTITLE());
//            }
//            else if(Constants1.LANGUAGE.equalsIgnoreCase(Constants1.URDU))
//            {
//                view[i] = View.inflate(this, R.layout.inflate_list_item_urdu, null);
//                ((NoriNastalicUrduFont)view[i].findViewById(R.id.txtTitle)).setText(indexBeanArrayList.get(i).getTITLE());
//
//            }
//            else if(Constants1.LANGUAGE.equalsIgnoreCase(Constants1.HINDI))
//            {
//                view[i] = View.inflate(this, R.layout.inflate_list_item_hindi, null);
//                ((HindiFont)view[i].findViewById(R.id.txtTitle)).setText(indexBeanArrayList.get(i).getTITLE());
//            }
//            img[i]=(ImageView)view[i].findViewById(R.id.img);
//            imgArrow[i]=(ImageView)view[i].findViewById(R.id.imgArrow);
//
//            if(Constants1.CURRENT_TYPE==Constants1.TYPE_QURNIC || Constants1.CURRENT_TYPE==Constants1.TYPE_OTHER
//                    || Constants1.CURRENT_TYPE==Constants1.TYPE_ASMAHUSNA)
//            {
//                if(Constants1.CURRENT_TYPE!=Constants1.TYPE_ASMAHUSNA && (Constants1.LANGUAGE.equalsIgnoreCase(Constants1.ENGLISH) || Constants1.LANGUAGE.equalsIgnoreCase(Constants1.ROMAN)  || Constants1.LANGUAGE.equalsIgnoreCase(Constants1.HINDI)))
//                    imgArrow[i].setImageResource(R.drawable.forwardimage);
//                else if(Constants1.CURRENT_TYPE==Constants1.TYPE_ASMAHUSNA|| Constants1.LANGUAGE.equalsIgnoreCase(Constants1.URDU))
//                {
//                    imgArrow[i].setImageResource(R.drawable.backwardimage);
//                }
//                changeButtonColor(imgArrow[i],"#43695B");
//            }

//            view[i].setOnClickListener(new ItemClickListener(i,subcontentLayout[i]));
            contentLayout.addView(view[i]);
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
}