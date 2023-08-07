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
import com.indapp.islamicknowledge.R;
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

        PageBean tempPageBean=new PageBean();
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

        String SINGLE_PAGE_DATA="";
        if(cursor!=null) {
            while (cursor.moveToNext()) {
                Log.v(Constants1.TAG,"***********GROUP NO - "+GROUP_NO+"*****************");

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



                if(Constants1.sp.getBoolean("arabic",false)) {

                    if (QURAN_AYAT_NO.equalsIgnoreCase("-1") || QURAN_AYAT_NO.equalsIgnoreCase("0")
                            || SURA_AYAT_NO.equalsIgnoreCase("-1") || SURA_AYAT_NO.equalsIgnoreCase("0"))
                    {

                        if(SINGLE_PAGE_DATA.trim().length()>0) {
                            tempPageBean.setQURAN_AYAT(SINGLE_PAGE_DATA);
                            pageBeanArrayList.add(tempPageBean);
                            tempPageBean=new PageBean();
                            SINGLE_PAGE_DATA = "";
                        }
                        tempPageBean=new PageBean();
                        tempPageBean.setQURAN_AYAT(QURAN_AYAT);
                        tempPageBean.setSURA_AYAT_NO(SURA_AYAT_NO);
                        tempPageBean.setQURAN_AYAT_NO(QURAN_AYAT_NO);
                        pageBeanArrayList.add(tempPageBean);
                    }
                    else
                    {
                        SINGLE_PAGE_DATA=SINGLE_PAGE_DATA+QURAN_AYAT;
                        tempPageBean=new PageBean();

                    }
                }
                else {
                    tempPageBean=new PageBean();
                    tempPageBean.setPARA_NO(PARA_NO);
                    tempPageBean.setSURA_NO(SURA_NO);
                    tempPageBean.setQURAN_AYAT_NO(QURAN_AYAT_NO);
                    tempPageBean.setSURA_AYAT_NO(SURA_AYAT_NO);
                    tempPageBean.setQURA_AYAT(QURAN_AYAT);
                    tempPageBean.setPARA_NAME(PARA_NAME);
                    tempPageBean.setSURA_NAME(SURA_NAME);
                    tempPageBean.setTRANSALATION(TRANSALATION);
                    tempPageBean.setID(ID);

                    tempPageBean.setTAFSEER(TAFSEER);

                    pageBeanArrayList.add(tempPageBean);
                }
            }
        }

        if(Constants1.sp.getBoolean("arabic",false)) {

            if(SINGLE_PAGE_DATA.trim().length()>0) {
                tempPageBean=new PageBean();
                tempPageBean.setQURAN_AYAT(SINGLE_PAGE_DATA);
                pageBeanArrayList.add(tempPageBean);
                SINGLE_PAGE_DATA = "";
            }

        }
        ScrollView scrollViewNested=(ScrollView)view1.findViewById(R.id.scrollViewNested);
        LinearLayout contentLayout=(LinearLayout)view1.findViewById(R.id.contentLayout);


        String tafseerLineSeperator="\n\n";

         int TOTAL_ROW=0;
        TOTAL_ROW = pageBeanArrayList.size();
        final TextView txtTranslation[] = new TextView[TOTAL_ROW];
        final TextView txtTranslationList[] = new TextView[TOTAL_ROW];
        final TextView txtTafseer[] = new TextView[TOTAL_ROW];
        final ArabicTextView txtArabicTextView[] = new ArabicTextView[TOTAL_ROW];
        final ArabicTextView txtArabicList[] = new ArabicTextView[TOTAL_ROW];

        final View view_row[] = new View[TOTAL_ROW];
        final View viewLineHorizontal[] = new View[TOTAL_ROW];
        final View viewLineVertical[] = new View[TOTAL_ROW];

        final LinearLayout layout_grid[] = new LinearLayout[TOTAL_ROW];
        final LinearLayout layout_list[] = new LinearLayout[TOTAL_ROW];
        String temp_translation = "";
        String finalTasfeeer = "";



        View quran_fullview[] = new View[TOTAL_ROW];
        final ArabicTextView txtArabicFullText[]=new ArabicTextView[TOTAL_ROW];
        if(Constants1.sp.getBoolean("arabic",false)==false)
        {


            for (int i = 0; i < TOTAL_ROW; i++) {
                view_row[i] = View.inflate(getActivity(), R.layout.inflate_quran_page, null);

                viewLineHorizontal[i] = view_row[i].findViewById(R.id.viewLineHorizontal);
                viewLineVertical[i] = view_row[i].findViewById(R.id.viewLineVertical);
                layout_grid[i] = (LinearLayout) view_row[i].findViewById(R.id.layout_grid);
                layout_list[i] = (LinearLayout) view_row[i].findViewById(R.id.layout_list);

                if (Constants1.LANGUAGE.equalsIgnoreCase(Constants1.URDU)) {
                    txtTranslation[i] = (TextView) view_row[i].findViewById(R.id.txtUrdu);
                    txtTranslationList[i] = (TextView) view_row[i].findViewById(R.id.txtUrduList);
                    txtTafseer[i] = (TextView) view_row[i].findViewById(R.id.txtTafseerUrdu);
                } else if (Constants1.LANGUAGE.equalsIgnoreCase(Constants1.GUJARATI)) {
                    txtTranslation[i] = (TextView) view_row[i].findViewById(R.id.txtGujarati);
                    txtTranslationList[i] = (TextView) view_row[i].findViewById(R.id.txtGujaratiList);
                    txtTafseer[i] = (TextView) view_row[i].findViewById(R.id.txtTafseerGujarati);
                }
                txtTafseer[i].setVisibility(View.VISIBLE);

                txtArabicTextView[i] = (ArabicTextView) view_row[i].findViewById(R.id.txtArabic);
                txtArabicList[i] = (ArabicTextView) view_row[i].findViewById(R.id.txtArabicList);
                finalTasfeeer = "";
                temp_translation = pageBeanArrayList.get(i).getTRANSALATION();
                if (pageBeanArrayList.get(i).getTAFSEER() != null && pageBeanArrayList.get(i).getTAFSEER().trim().length() > 0 &&
                        pageBeanArrayList.get(i).getTAFSEER().trim().equalsIgnoreCase("null") == false) {
                    finalTasfeeer = pageBeanArrayList.get(i).getTAFSEER();
                }

                if (Constants1.LANGUAGE.equalsIgnoreCase(Constants1.GUJARATI)) {
                    temp_translation = temp_translation.replaceAll("0", "૦").replaceAll("1", "૧")
                            .replaceAll("2", "૨").replaceAll("3", "૩").replaceAll("4", "૪")
                            .replaceAll("5", "૫").replaceAll("6", "૬").replaceAll("7", "૭").replaceAll("8", "૮").replaceAll("9", "૯");
                } else if (Constants1.LANGUAGE.equalsIgnoreCase(Constants1.URDU)) {
                    temp_translation = temp_translation.replaceAll("0", "۰").replaceAll("1", "۱")
                            .replaceAll("2", "۲").replaceAll("3", "۳").replaceAll("4", "۴")
                            .replaceAll("5", "۵").replaceAll("6", "۶").replaceAll("7", "۷").replaceAll("8", "۸").replaceAll("9", "۹");
                }

                if (finalTasfeeer.trim().length() > 0) {
                    if (Constants1.LANGUAGE.equalsIgnoreCase(Constants1.GUJARATI)) {
                        finalTasfeeer = finalTasfeeer.replaceAll("0", "૦").replaceAll("1", "૧")
                                .replaceAll("2", "૨").replaceAll("3", "૩").replaceAll("4", "૪")
                                .replaceAll("5", "૫").replaceAll("6", "૬").replaceAll("7", "૭").replaceAll("8", "૮").replaceAll("9", "૯");
                    }
                    txtTafseer[i].setText(finalTasfeeer.trim());
                    viewLineHorizontal[i].setVisibility(View.GONE);
                } else txtTafseer[i].setVisibility(View.GONE);



                txtTranslation[i].setText(temp_translation);
                txtTranslationList[i].setText(temp_translation);
//                        +"\n\n<style @font-face {src: url('file:///android_asset/fonts/extrafonttesting/Noori Nastaliq.ttf')}></style><font color='red'>"+Constants1.UNICODE_RAZIALLAHU_ANHA+"---"+Constants1.UNICODE_ALAIHASSALAM+"</font>"));
//                "* ઔર જબ હમને કહા ફરિશ્તોં સે કે સજદા કરો આદમ \uFDE4 કો તો સબને સજદા કિયા મગર ઇબ્લીસ (શૈતાન) ને ઇસ હુકમ કો કુબૂલ ન કિયા ઔર અપની બળાઈ બતાને લગા ઔર બગાવત કરને વાલોં મેં હો ગયા. (34)");//

                txtArabicTextView[i].setText(pageBeanArrayList.get(i).getQURAN_AYAT());
                txtArabicList[i].setText(pageBeanArrayList.get(i).getQURAN_AYAT());
                view_row[i].setOnLongClickListener(new RowLongClick(i, pageBeanArrayList.get(i), txtArabicTextView[i]));

                if (Constants1.sp.contains("bookmark_" + pageBeanArrayList.get(i).getID()))
                    view_row[i].setBackgroundColor(getResources().getColor(R.color.colorSurahParaBG));

                txtTranslation[i].setTextColor(Color.parseColor("#ffffff"));
                txtArabicTextView[i].setTextColor(Color.parseColor("#ffffff"));

                txtTranslationList[i].setTextColor(Color.parseColor("#ffffff"));
                txtArabicList[i].setTextColor(Color.parseColor("#ffffff"));

                if (pageBeanArrayList.get(i).getQURAN_AYAT_NO() != null && pageBeanArrayList.get(i).getQURAN_AYAT_NO().equalsIgnoreCase("-1")) {
                    view_row[i].setBackgroundColor(Color.parseColor("#c99136"));
                } else if (pageBeanArrayList.get(i).getQURAN_AYAT_NO() != null && pageBeanArrayList.get(i).getSURA_AYAT_NO().equalsIgnoreCase("0")) {
                    view_row[i].setBackgroundColor(Color.parseColor("#b78431"));
                }
                contentLayout.addView(view_row[i]);
                Log.v(Constants1.TAG, "----------" + i);
            }
        }
        else
        {

            for(int i=0;i<TOTAL_ROW;i++) {
                quran_fullview[i] = View.inflate(getActivity(), R.layout.inflate_quran_page_arabiconly, null);
                txtArabicFullText[i]=(ArabicTextView)quran_fullview[i].findViewById(R.id.txtArabicFullText);
                txtArabicFullText[i].setText(pageBeanArrayList.get(i).getQURAN_AYAT());
                Log.v(Constants1.TAG,"--->"+pageBeanArrayList.get(i).getSURA_AYAT_NO()+"---"+pageBeanArrayList.get(i).getSURA_AYAT_NO());
                if (pageBeanArrayList.get(i).getQURAN_AYAT_NO() != null && pageBeanArrayList.get(i).getQURAN_AYAT_NO().equalsIgnoreCase("-1")) {
                    quran_fullview[i].setBackgroundColor(Color.parseColor("#c99136"));
                } else if (pageBeanArrayList.get(i).getSURA_AYAT_NO() != null && pageBeanArrayList.get(i).getSURA_AYAT_NO().equalsIgnoreCase("0")) {
                    quran_fullview[i].setBackgroundColor(Color.parseColor("#b78431"));
                }
                contentLayout.addView(quran_fullview[i]);
            }

        }
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {

                try {

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {


                                if (Constants1.sp.getBoolean("arabic", false) == false) {
                                    for (int i = 0; i < pageBeanArrayList.size(); i++) {
                                        txtTranslation[i].setTextSize(2, (float) Constants1.sp.getInt("perf_font_size", Constants1.DEFAULT_FONT));
                                        txtArabicTextView[i].setTextSize(2, (float) Constants1.sp.getInt("perf_font_size", Constants1.DEFAULT_FONT) * 1.3f);

                                        txtTranslationList[i].setTextSize(2, (float) Constants1.sp.getInt("perf_font_size", Constants1.DEFAULT_FONT));
                                        txtArabicList[i].setTextSize(2, (float) Constants1.sp.getInt("perf_font_size", Constants1.DEFAULT_FONT) * 1.3f);

                                        if (pageBeanArrayList.get(i).getQURAN_AYAT_NO() != null && pageBeanArrayList.get(i).getQURAN_AYAT_NO().equalsIgnoreCase("-1") == false
                                                && pageBeanArrayList.get(i).getQURAN_AYAT_NO() != null && pageBeanArrayList.get(i).getSURA_AYAT_NO().equalsIgnoreCase("0") == false) {
                                            txtTranslation[i].setTextColor(Color.parseColor("#" + Constants1.sp.getString("perf_font_color_urdu", "000000")));
                                            txtArabicTextView[i].setTextColor(Color.parseColor("#" + Constants1.sp.getString("perf_font_color_arabic", "000000")));

                                            txtTranslationList[i].setTextColor(Color.parseColor("#" + Constants1.sp.getString("perf_font_color_urdu", "000000")));
                                            txtArabicList[i].setTextColor(Color.parseColor("#" + Constants1.sp.getString("perf_font_color_arabic", "000000")));
                                        }

                                        viewLineHorizontal[i].setBackgroundColor(Color.parseColor("#" + Constants1.sp.getString("perf_line_color", "000000")));
                                        viewLineVertical[i].setBackgroundColor(Color.parseColor("#" + Constants1.sp.getString("perf_line_color", "000000")));

                                        txtTafseer[i].setTextSize(2, (float) Constants1.sp.getInt("perf_font_size", Constants1.DEFAULT_FONT));

                                        if (Constants1.sp.getString("format", "list").equalsIgnoreCase("grid")) {
                                            layout_grid[i].setVisibility(View.VISIBLE);
                                            layout_list[i].setVisibility(View.GONE);
                                        } else {
                                            layout_grid[i].setVisibility(View.GONE);
                                            layout_list[i].setVisibility(View.VISIBLE);
                                        }
                                    }
                                } else {

                                    for (int i = 0; i < pageBeanArrayList.size(); i++) {
                                        txtArabicFullText[i].setTextSize(2, (float) Constants1.sp.getInt("perf_font_size", Constants1.DEFAULT_FONT) * 1.3f);
                                        if (pageBeanArrayList.get(i).getQURAN_AYAT_NO().equalsIgnoreCase("-1") == false
                                                && pageBeanArrayList.get(i).getSURA_AYAT_NO().equalsIgnoreCase("0") == false) {
                                            txtArabicFullText[i].setTextColor(Color.parseColor("#" + Constants1.sp.getString("perf_font_color_arabic", "000000")));
                                        }
                                    }
                                }
                            } catch (Exception e) {
                            }
                        }

                        ;
                    });
                }
                catch (Exception e)
                {}
            }
        }, 0, 100);
        return  view1;
    }
    PopupMenu p ;
    public class RowLongClick implements View.OnLongClickListener
    {
        int pos;
        PageBean pageBean;
        View clickView;
        public RowLongClick(int pos,PageBean pageBean,View clickView)
        {
            this.pos=pos;
            this.pageBean=pageBean;
            this.clickView=clickView;
        }
        public boolean onLongClick(View view)
        {
            Constants1.initSharedPref(getActivity());




            if(p==null) {
                p = new PopupMenu(getActivity(), clickView);
                p.setOnDismissListener(new PopupMenu.OnDismissListener() {
                    @Override
                    public void onDismiss(PopupMenu menu) {
                        p=null;
                    }
                });
                if (Constants1.LANGUAGE.equalsIgnoreCase(Constants1.GUJARATI))
                    p.getMenuInflater().inflate(R.menu.pop_up_menu_gujarati, p.getMenu());
                else if (Constants1.LANGUAGE.equalsIgnoreCase(Constants1.URDU))
                    p.getMenuInflater().inflate(R.menu.pop_up_menu_urdu, p.getMenu());
                p.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {

                        if (item.getItemId() == R.id.addbookmark) {
                            Constants1.editor.putString("bookmark_" + pageBean.getID(), "" + pageBean.getID());
                            Constants1.editor.commit();
                            Toast.makeText(getActivity(), "Added to Bookmark", Toast.LENGTH_LONG).show();
                            view.setBackgroundColor(getResources().getColor(R.color.colorSurahParaBG));
                        } else if (item.getItemId() == R.id.removeaddbookmark) {
                            Constants1.editor.remove("bookmark_" + pageBean.getID());
                            Constants1.editor.commit();
                            Toast.makeText(getActivity(), "Removed from Bookmark", Toast.LENGTH_LONG).show();
                            view.setBackgroundColor(Color.WHITE);
                        }
//                        else if (item.getItemId() == R.id.copyarabic) {
//                            actionOnText(0, pageBean, 0);
//                        } else if (item.getItemId() == R.id.copytranslation) {
//                            actionOnText(1, pageBean, 0);
//                        } else if (item.getItemId() == R.id.copyarabictranslation) {
//                            actionOnText(2, pageBean, 0);
//                        }
//                        else if (item.getItemId() == R.id.sharearabic) {
//                            actionOnText(0, pageBean, 1);
//                        }
//                        else if (item.getItemId() == R.id.sharetranslation) {
//                            actionOnText(1, pageBean, 1);
//                        }
                        else if (item.getItemId() == R.id.sharearabictranslation) {
                            actionOnText(2, pageBean, 1);
                        }
                        else if (item.getItemId() == R.id.sharearabictranslationtafseer) {
                            actionOnText(3, pageBean, 1);
                        }
                        return true;

                    }
                });
                if (Constants1.sp.contains("bookmark_" + pageBean.getID()))
                    p.getMenu().removeItem(R.id.addbookmark);
                else p.getMenu().removeItem(R.id.removeaddbookmark);

                if(pageBean.getTAFSEER()!=null && pageBean.getTAFSEER().trim().length()==0)
                    p.getMenu().removeItem(R.id.sharearabictranslationtafseer);

                p.show();
            }
            return true;
        }

    }
    public void openSubMenu()
    {

    }
    public void actionOnText(int actionOn,PageBean pageBean,int actionType)
    {
        String translation="",tafseer="";
        if(Constants1.LANGUAGE.equalsIgnoreCase(Constants1.GUJARATI)) {
            translation= pageBean.getTRANSALATION().replaceAll("0", "૦").replaceAll("1", "૧")
                    .replaceAll("2", "૨").replaceAll("3", "૩").replaceAll("4", "૪")
                    .replaceAll("5", "૫").replaceAll("6", "૬").replaceAll("7", "૭").replaceAll("8", "૮").replaceAll("9", "૯");


            if (pageBean.getTAFSEER() != null && pageBean.getTAFSEER().trim().length() > 0 &&
                    pageBean.getTAFSEER().trim().equalsIgnoreCase("null") == false) {
                tafseer = "તફસીર: "+pageBean.getTAFSEER().replaceAll("0", "૦").replaceAll("1", "૧")
                        .replaceAll("2", "૨").replaceAll("3", "૩").replaceAll("4", "૪")
                        .replaceAll("5", "૫").replaceAll("6", "૬").replaceAll("7", "૭").replaceAll("8", "૮").replaceAll("9", "૯");;
            }
        }
        else if(Constants1.LANGUAGE.equalsIgnoreCase(Constants1.URDU))
        {
            translation=pageBean.getTRANSALATION().replaceAll("0","۰").replaceAll("1","۱")
                .replaceAll("2","۲").replaceAll("3","۳").replaceAll("4","۴")
                .replaceAll("5","۵").replaceAll("6","۶").replaceAll("7","۷").replaceAll("8","۸").replaceAll("9","۹");


            if (pageBean.getTAFSEER() != null && pageBean.getTAFSEER().trim().length() > 0 &&
                    pageBean.getTAFSEER().trim().equalsIgnoreCase("null") == false) {
                tafseer ="تفسیر: "+ pageBean.getTAFSEER().replaceAll("0","۰").replaceAll("1","۱")
                        .replaceAll("2","۲").replaceAll("3","۳").replaceAll("4","۴")
                        .replaceAll("5","۵").replaceAll("6","۶").replaceAll("7","۷").replaceAll("8","۸").replaceAll("9","۹");
            }
        }

        String selectedText="";
        ClipboardManager clipboard = (ClipboardManager)getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        if(actionOn==0)selectedText=pageBean.getQURAN_AYAT();
        else if(actionOn==1)selectedText=translation;
        else if(actionOn==2)selectedText=pageBean.getQURAN_AYAT()+"\n\n"+translation;
        else if(actionOn==3)selectedText=pageBean.getQURAN_AYAT()+"\n\n"+translation+"\n\n"+tafseer;

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
            intent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody+"\n\n"+Constants1.getShareText());
            /*Fire!*/
            startActivity(Intent.createChooser(intent, "Share Via"));


        }
    }

}
