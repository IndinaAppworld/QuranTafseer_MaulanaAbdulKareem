package com.indapp.fragements;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;

import com.indapp.beans.PageBean;
import com.indapp.fonts.ArabicTextView;
import com.indapp.qurantafseer_maulanaabdulkareem.R;
import com.indapp.utils.Constants1;


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
    int GROUP_NO;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view1 = inflater.inflate(R.layout.quran_fragment, container, false);
        GROUP_NO=getArguments().getInt(PAGENO_ID);
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
                "QT.TRANSLATION_"+Constants1.LANGUAGE+",\n" +
                "QT.TAFSEER_"+Constants1.LANGUAGE+",\n" +
                "QT.ID"+"\n"+
                "from QURAN_ARABIC QA, QURAN_PARA QP, QURAN_SURA QS, QURAN_TRANSLATION QT\n" +
                "where QA.PARA_NO =  QP.ID and QA.GROUP_NO="+GROUP_NO+" and QA.SURA_NO = QS.ID\n" +
                "and QA.ID=QT.ID", Constants1.sqLiteDatabase);
        String PARA_NO,SURA_NO,QURAN_AYAT_NO,SURA_AYAT_NO,QURAN_AYAT,PARA_NAME,SURA_NAME,TRANSALATION,TAFSEER="",ID="";
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
                TAFSEER=""+cursor.getString(8);
                ID=""+cursor.getString(9);

                Log.v(Constants1.TAG,"PARA_NO: "+PARA_NO+"\nSURA_NO: "+SURA_NO+"\nPARA_NAME: "+PARA_NAME+"\nSURA_NAME:  "+SURA_NAME+"\nPARA_NAME:  "+PARA_NAME+"\nQURA_AYAT: "+QURAN_AYAT+"\nTRANSALATION: "+TRANSALATION);

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
        ScrollView scrollViewNested=(ScrollView)view1.findViewById(R.id.scrollViewNested);
        LinearLayout contentLayout=(LinearLayout)view1.findViewById(R.id.contentLayout);


        String tafseerLineSeperator="\n\n";

//        if(Constants1.LANGUAGE.equalsIgnoreCase(Constants1.GUJARATI))
//        txtTafseer=(TextView)view1.findViewById(R.id.txtTafseerGujarati);
//        else //if(Constants1.LANGUAGE.equalsIgnoreCase(Constants1.URDU))
//        {
//            txtTafseer=(TextView)view1.findViewById(R.id.txtTafseerUrdu);
//            tafseerLineSeperator="\n";
//        }

//        txtTafseer.setVisibility(View.VISIBLE);

        int TOTAL_ROW=pageBeanArrayList.size();
        final TextView txtTranslation[]=new TextView[TOTAL_ROW];
        final TextView txtTafseer[]=new TextView[TOTAL_ROW];
        final ArabicTextView txtArabicTextView[]=new ArabicTextView[TOTAL_ROW];
        final View view_row[]=new View[TOTAL_ROW];
        final View viewLineHorizontal[]=new View[TOTAL_ROW];
        String temp_translation="";
        String finalTasfeeer="";
        for(int i=0;i<TOTAL_ROW;i++)
        {
            view_row[i]=View.inflate(getActivity(),R.layout.inflate_quran_page,null);

            viewLineHorizontal[i]=view_row[i].findViewById(R.id.viewLineHorizontal);


            if(Constants1.LANGUAGE.equalsIgnoreCase(Constants1.URDU))
            {
                txtTranslation[i]=(TextView)view_row[i].findViewById(R.id.txtUrdu);
                txtTafseer[i]=(TextView)view_row[i].findViewById(R.id.txtTafseerUrdu);
            }
            else if(Constants1.LANGUAGE.equalsIgnoreCase(Constants1.GUJARATI))
            {
                txtTranslation[i]=(TextView)view_row[i].findViewById(R.id.txtGujarati);
                txtTafseer[i]=(TextView)view_row[i].findViewById(R.id.txtTafseerGujarati);
            }
            txtTafseer[i].setVisibility(View.VISIBLE);

            txtArabicTextView[i]=(ArabicTextView)view_row[i].findViewById(R.id.txtArabic);
            finalTasfeeer="";
            temp_translation=pageBeanArrayList.get(i).getTRANSALATION();
            if(pageBeanArrayList.get(i).getTAFSEER()!=null && pageBeanArrayList.get(i).getTAFSEER().trim().length()>0 &&
                    pageBeanArrayList.get(i).getTAFSEER().trim().equalsIgnoreCase("null")==false) {
//                if(finalTasfeeer.length()>0)
//                finalTasfeeer = finalTasfeeer+tafseerLineSeperator+ pageBeanArrayList.get(i).getTAFSEER();
//                else
                    finalTasfeeer = pageBeanArrayList.get(i).getTAFSEER();
            }

            if(Constants1.LANGUAGE.equalsIgnoreCase(Constants1.GUJARATI))
            {
                temp_translation=temp_translation.replaceAll("0","૦").replaceAll("1","૧")
                        .replaceAll("2","૨").replaceAll("3","૩").replaceAll("4","૪")
                        .replaceAll("5","૫").replaceAll("6","૬").replaceAll("7","૭").replaceAll("8","૮").replaceAll("9","૯");
            }
            else if(Constants1.LANGUAGE.equalsIgnoreCase(Constants1.URDU))
            {
                temp_translation=temp_translation.replaceAll("0","۰").replaceAll("1","۱")
                        .replaceAll("2","۲").replaceAll("3","۳").replaceAll("4","۴")
                        .replaceAll("5","۵").replaceAll("6","۶").replaceAll("7","۷").replaceAll("8","۸").replaceAll("9","۹");
            }


            if(finalTasfeeer.trim().length()>0) {
                if(Constants1.LANGUAGE.equalsIgnoreCase(Constants1.GUJARATI))
                {
                    finalTasfeeer = finalTasfeeer.replaceAll("0", "૦").replaceAll("1", "૧")
                            .replaceAll("2", "૨").replaceAll("3", "૩").replaceAll("4", "૪")
                            .replaceAll("5", "૫").replaceAll("6", "૬").replaceAll("7", "૭").replaceAll("8", "૮").replaceAll("9", "૯");
                }
                txtTafseer[i].setText(finalTasfeeer.trim());
            }
            else txtTafseer[i].setVisibility(View.GONE);

            txtTranslation[i].setText(temp_translation);

            txtArabicTextView[i].setText(pageBeanArrayList.get(i).getQURAN_AYAT());
            view_row[i].setOnLongClickListener(new RowLongClick(i,pageBeanArrayList.get(i)));

            if(Constants1.sp.contains("bookmark_"+pageBeanArrayList.get(i).getID()))
                view_row[i].setBackgroundColor(getResources().getColor(R.color.colorSurahParaBG));
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

                            viewLineHorizontal[i].setBackgroundColor(Color.parseColor("#"+Constants1.sp.getString("perf_line_color", "000000")));
                            txtTafseer[i].setTextSize(2, (float) Constants1.sp.getInt("perf_font_size", Constants1.DEFAULT_FONT));
                        }

                    };
                });
            }
        }, 0, 100);
        return  view1;
    }
    public class RowLongClick implements View.OnLongClickListener
    {
        int pos;
        PageBean pageBean;
        public RowLongClick(int pos,PageBean pageBean)
        {
            this.pos=pos;
            this.pageBean=pageBean;
        }
        public boolean onLongClick(View view)
        {
            Constants1.initSharedPref(getActivity());



            PopupMenu p = new PopupMenu(getActivity(), view);
            if(Constants1.LANGUAGE.equalsIgnoreCase(Constants1.GUJARATI))
            p.getMenuInflater().inflate(R.menu.pop_up_menu_gujarati, p.getMenu());
            else if(Constants1.LANGUAGE.equalsIgnoreCase(Constants1.URDU))
                p.getMenuInflater().inflate(R.menu.pop_up_menu_urdu, p.getMenu());
            p.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                public boolean onMenuItemClick(MenuItem item) {

                    if(item.getItemId()==R.id.addbookmark) {
                        Constants1.editor.putString("bookmark_" + pageBean.getID(), "" + pageBean.getID());
                        Constants1.editor.commit();
                        Toast.makeText(getActivity(),"Added to Bookmark",Toast.LENGTH_LONG).show();
                        view.setBackgroundColor(getResources().getColor(R.color.colorSurahParaBG));

                    }
                    else if(item.getItemId()==R.id.removeaddbookmark)
                    {
                        Constants1.editor.remove("bookmark_" + pageBean.getID());
                        Constants1.editor.commit();
                        Toast.makeText(getActivity(),"Removed from Bookmark",Toast.LENGTH_LONG).show();
                        view.setBackgroundColor(Color.WHITE);
                    }
                    else if(item.getItemId()==R.id.copyarabic)
                    {
                        actionOnText(0,pageBean,0);
                    }
                    else if(item.getItemId()==R.id.copytranslation)
                    {
                        actionOnText(1,pageBean,0);
                    }
                    else if(item.getItemId()==R.id.copyarabictranslation)
                    {
                        actionOnText(2,pageBean,0);
                    }
                    else if(item.getItemId()==R.id.sharearabic)
                    {
                        actionOnText(0,pageBean,1);
                    }
                    else if(item.getItemId()==R.id.sharetranslation)
                    {
                        actionOnText(1,pageBean,1);
                    }
                    else if(item.getItemId()==R.id.sharearabictranslation)
                    {
                        actionOnText(2,pageBean,1);
                    }
                    return true;

                }
            });
            if(Constants1.sp.contains("bookmark_"+pageBean.getID()))
                p.getMenu().removeItem(R.id.addbookmark);
            else p.getMenu().removeItem(R.id.removeaddbookmark);

            p.show();
            return true;
        }
    }
    public void openSubMenu()
    {

    }
    public void actionOnText(int actionOn,PageBean pageBean,int actionType)
    {
        String translation="";
        if(Constants1.LANGUAGE.equalsIgnoreCase(Constants1.GUJARATI)) {
            translation= pageBean.getTRANSALATION().replaceAll("0", "૦").replaceAll("1", "૧")
                    .replaceAll("2", "૨").replaceAll("3", "૩").replaceAll("4", "૪")
                    .replaceAll("5", "૫").replaceAll("6", "૬").replaceAll("7", "૭").replaceAll("8", "૮").replaceAll("9", "૯");
        }
        else if(Constants1.LANGUAGE.equalsIgnoreCase(Constants1.URDU))
        {
            translation=pageBean.getTRANSALATION().replaceAll("0","۰").replaceAll("1","۱")
                .replaceAll("2","۲").replaceAll("3","۳").replaceAll("4","۴")
                .replaceAll("5","۵").replaceAll("6","۶").replaceAll("7","۷").replaceAll("8","۸").replaceAll("9","۹");
        }

        String selectedText="";
        ClipboardManager clipboard = (ClipboardManager)getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        if(actionOn==0)selectedText=pageBean.getQURAN_AYAT();
        else if(actionOn==1)selectedText=translation;
        else if(actionOn==2)selectedText=pageBean.getQURAN_AYAT()+"\n\n"+translation;

        selectedText=selectedText+"\n\n["+pageBean.getPARA_NAME()+", "+pageBean.getSURA_NAME()+"]";



        if(actionType==0) {
            ClipData clip = ClipData.newPlainText("label", selectedText);
            if (clipboard == null || clip == null) return;
            clipboard.setPrimaryClip(clip);
            Toast.makeText(getActivity(), "Copied to Clipboard", Toast.LENGTH_LONG).show();
        }
        else
        {
            Intent intent = new Intent(android.content.Intent.ACTION_SEND);
            /*This will be the actual content you wish you share.*/
            String shareBody = selectedText;
            /*The type of the content is text, obviously.*/
            intent.setType("text/plain");
            /*Applying information Subject and Body.*/
//            intent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.share_subject));
            intent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            /*Fire!*/
            startActivity(Intent.createChooser(intent, "Share Via"));


        }
    }

}
