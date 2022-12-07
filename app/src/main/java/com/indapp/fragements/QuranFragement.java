package com.indapp.fragements;

import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import com.indapp.beans.PageBean;
import com.indapp.fonts.ArabicTextView;
import com.indapp.fonts.UrduTextView;
import com.indapp.qurantafseer_maulanaabdulkareem.R;
import com.indapp.utils.Constants1;
import com.jaredrummler.android.colorpicker.ColorPickerDialog;
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener;


import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class QuranFragement extends Fragment {


    public static final String PAGENO_ID = "PAGENO_ID";
    public static final String EXTRA_TYPE = "TYPE";
    public static final QuranFragement newInstance(int PAGENO, int TYPE)
    {
        QuranFragement f = new QuranFragement();
        Bundle bdl = new Bundle(2);
        bdl.putInt(PAGENO_ID, PAGENO);
        bdl.putInt(EXTRA_TYPE, TYPE);
        f.setArguments(bdl);
        return f;
    }


    Runnable r6;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view1 = inflater.inflate(R.layout.quran_fragment, container, false);
        int GROUP_NO=getArguments().getInt(PAGENO_ID)+1;
        Log.v(Constants1.TAG, "**************GROUP_NO******************" + GROUP_NO);

//        SELECT QA.PARA_NO,QA.SURA_NO,QA.QURAN_AYAT_NO,QA.SURA_AYAT_NO, QA.AYAT,
//                QP.ID as PARA_NO, QP.PARA_NAME,
//                QS.ID as SURA_NO, QS.SURA_NAME,
//                QT.TRANSLATION_URDU
//        from QURAN_ARABIC QA, QURAN_PARA QP, QURAN_SURA QS, QURAN_TRANSLATION QT
//        where QA.PARA_NO =  QP.ID and QA.GROUP_NO=1276 and QA.SURA_NO = QS.ID
//        and QA.ID=QT.ID
        Cursor cursor = Constants1.databaseHandler.getData("SELECT QA.PARA_NO,QA.SURA_NO,QA.QURAN_AYAT_NO,QA.SURA_AYAT_NO, QA.AYAT,\n" +
                "QP.PARA_NAME,\n" +
                "QS.SURA_NAME,\n" +
                "QT.TRANSLATION_"+Constants1.LANGUAGE+"\n" +
                "from QURAN_ARABIC QA, QURAN_PARA QP, QURAN_SURA QS, QURAN_TRANSLATION QT\n" +
                "where QA.PARA_NO =  QP.ID and QA.GROUP_NO="+GROUP_NO+" and QA.SURA_NO = QS.ID\n" +
                "and QA.ID=QT.ID", Constants1.sqLiteDatabase);
        String PARA_NO,SURA_NO,QURAN_AYAT_NO,SURA_AYAT_NO,QURAN_AYAT,PARA_NAME,SURA_NAME,TRANSALATION;
        ArrayList<PageBean> pageBeanArrayList=new ArrayList<>();
        PageBean tempPageBean;
        if(cursor!=null) {
            while (cursor.moveToNext()) {
                Log.v(Constants1.TAG,"***********GROUP NO - "+GROUP_NO+"*****************");
                tempPageBean=new PageBean();
                PARA_NO=""+cursor.getInt(0);
                SURA_NO=""+cursor.getInt(1);
                QURAN_AYAT_NO=""+cursor.getInt(2);
                SURA_AYAT_NO=""+cursor.getInt(3);
                QURAN_AYAT=""+cursor.getString(4);
                PARA_NAME=""+cursor.getString(5);
                SURA_NAME=""+cursor.getString(6);
                TRANSALATION=""+cursor.getString(7);

                Log.v(Constants1.TAG,"PARA_NO: "+PARA_NO+"\nSURA_NO: "+SURA_NO+"\nPARA_NAME: "+PARA_NAME+"\nSURA_NAME:  "+SURA_NAME+"\nPARA_NAME:  "+PARA_NAME+"\nQURA_AYAT: "+QURAN_AYAT+"\nTRANSALATION: "+TRANSALATION);

                tempPageBean.setPARA_NO(PARA_NO);
                tempPageBean.setSURA_NO(SURA_NO);
                tempPageBean.setQURAN_AYAT_NO(QURAN_AYAT_NO);
                tempPageBean.setSURA_AYAT_NO(SURA_AYAT_NO);
                tempPageBean.setQURA_AYAT(QURAN_AYAT);
                tempPageBean.setPARA_NAME(PARA_NAME);
                tempPageBean.setSURA_NAME(SURA_NAME);
                tempPageBean.setTRANSALATION(TRANSALATION);

                pageBeanArrayList.add(tempPageBean);
            }
        }
        ScrollView scrollViewNested=(ScrollView)view1.findViewById(R.id.scrollViewNested);
        LinearLayout contentLayout=(LinearLayout)view1.findViewById(R.id.contentLayout);

        int TOTAL_ROW=pageBeanArrayList.size();
        final TextView txtTranslation[]=new TextView[TOTAL_ROW];
        final ArabicTextView txtArabicTextView[]=new ArabicTextView[TOTAL_ROW];
        final View view_row[]=new View[TOTAL_ROW];
        String temp_translation="";
        for(int i=0;i<TOTAL_ROW;i++)
        {
            view_row[i]=View.inflate(getActivity(),R.layout.inflate_quran_page,null);

            if(Constants1.LANGUAGE.equalsIgnoreCase(Constants1.URDU))
            txtTranslation[i]=(TextView)view_row[i].findViewById(R.id.txtUrdu);
            else if(Constants1.LANGUAGE.equalsIgnoreCase(Constants1.GUJARATI))
            txtTranslation[i]=(TextView)view_row[i].findViewById(R.id.txtGujarati);

            txtArabicTextView[i]=(ArabicTextView)view_row[i].findViewById(R.id.txtArabic);

            temp_translation=pageBeanArrayList.get(i).getTRANSALATION();
            if(Constants1.LANGUAGE.equalsIgnoreCase(Constants1.GUJARATI))
            {
                temp_translation=temp_translation.replaceAll("0","૦").replaceAll("1","૧")
                        .replaceAll("2","૨").replaceAll("3","૩").replaceAll("4","૪")
                        .replaceAll("5","૫").replaceAll("6","૬").replaceAll("7","૭").replaceAll("8","૮").replaceAll("9","૯");
            }
            txtTranslation[i].setText(temp_translation);

            txtArabicTextView[i].setText(pageBeanArrayList.get(i).getQURAN_AYAT());

            txtTranslation[i].setOnClickListener(new TextClickListener(0));
            txtArabicTextView[i].setOnClickListener(new TextClickListener(1));

            contentLayout.addView(view_row[i]);

            Log.v(Constants1.TAG,"----------"+i);
        }
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for(int i=0;i<TOTAL_ROW;i++)
                        {
                            txtTranslation[i].setTextSize(2, (float) Constants1.sp.getInt("perf_font_size", Constants1.DEFAULT_FONT));
                            txtArabicTextView[i].setTextSize(2, (float) Constants1.sp.getInt("perf_font_size", Constants1.DEFAULT_FONT)* 1.3f);

                            txtTranslation[i].setTextColor(Color.parseColor("#"+Constants1.sp.getString("perf_font_color_urdu", "000000")));
                            txtArabicTextView[i].setTextColor(Color.parseColor("#"+Constants1.sp.getString("perf_font_color_arabic", "000000")));

                        }
                    };
                });
            }
        }, 0, 100);
        return  view1;
    }
    public class TextClickListener implements View.OnClickListener
    {
        int type;
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
            else
            {
                default_color=Color.parseColor("#FF0000");
            }
            ColorPickerDialog.newBuilder().setDialogType(ColorPickerDialog.TYPE_CUSTOM).setColor(default_color).setDialogId(type).show(getActivity());
        }
    }

}
