package com.indapp.islamicknowledge;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
            ((GujaratiBoldTextView) findViewById(R.id.txtMainTitleGujarati)).setVisibility(View.VISIBLE);
            if(type.equalsIgnoreCase("peshlafz"))
                ((GujaratiBoldTextView) findViewById(R.id.txtMainTitleGujarati)).setText("પેશ લફઝ");
            else if(type.equalsIgnoreCase("aboutus")) {
                ((GujaratiBoldTextView) findViewById(R.id.txtMainTitleGujarati)).setText("અમારા વિશે");


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

        shimmerFrameLayout=(ShimmerFrameLayout)findViewById(R.id.shimmer_view_container);
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
    TextView txtTitle[];
    TextView txtNumber[];
    View view[];
    ImageView imgFav[];
    public void updateList()
    {
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

                view[i] = View.inflate(this, R.layout.inflate_duaindex_list_item, null);
                ((TextView)view[i].findViewById(R.id.txtTitle)).setText(indexBeanArrayList.get(i).getTITLE());
                 txtNumber[i]=(TextView)view[i].findViewById(R.id.txtNumber);
                 imgFav[i]=(ImageView) view[i].findViewById(R.id.imgFav);
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

            if(Constants1.sp.contains("DUA"+"_"+((i+1)))==true)
            {
                imgFav[i].setImageResource(R.drawable.favorite_dua_sel);
            }

            txtNumber[i].setText(Constants1.replaceNumbers(""+(i+1) +"."));
            view[i].setOnClickListener(new ItemClickListener(i));
            imgFav[i].setOnClickListener(new FavClickListener((i+1),imgFav[i]));
            contentLayout.addView(view[i]);
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
}