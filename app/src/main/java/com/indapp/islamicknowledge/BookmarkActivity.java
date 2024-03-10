package com.indapp.islamicknowledge;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;

import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.PopupMenu;

import com.indapp.beans.PageBean;
import com.indapp.fonts.ArabicTextView;
import com.indapp.fonts.GujaratiTextView;
import com.indapp.fonts.UrduTextView;
import com.indapp.utils.Constants1;
import com.indapp.utils.DatabaseHandler;

import java.util.ArrayList;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

public class BookmarkActivity extends Activity {
    LinearLayout contentLayout;
    ImageView imgListGridIcon;

    boolean isMuntaKhib=false;
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


        if(getIntent().getExtras()!=null)
        {
            if(getIntent().getExtras().containsKey("isMuntaKhib"))
            {
                isMuntaKhib=true;
            }
        }
        Constants1.initSharedPref(this);
        Constants1.LANGUAGE=Constants1.sp.getString("language",Constants1.GUJARATI);
        setContentView(R.layout.activity_bookmark);
        if(Constants1.LANGUAGE.equalsIgnoreCase(Constants1.GUJARATI))
        {
            ((GujaratiTextView)findViewById(R.id.txtMainTitleGujarati)).setVisibility(View.VISIBLE);
        }
        else if(Constants1.LANGUAGE.equalsIgnoreCase(Constants1.URDU))
        {
            ((UrduTextView)findViewById(R.id.txtMainTitleUrdu)).setVisibility(View.VISIBLE);
        }

        if(isMuntaKhib)
        {
            if(Constants1.LANGUAGE.equalsIgnoreCase(Constants1.GUJARATI))
            {
                ((GujaratiTextView)findViewById(R.id.txtMainTitleGujarati)).setVisibility(View.VISIBLE);
                ((GujaratiTextView)findViewById(R.id.txtMainTitleGujarati)).setText("ખાસ આયત");
            }
            else if(Constants1.LANGUAGE.equalsIgnoreCase(Constants1.URDU))
            {
                ((UrduTextView)findViewById(R.id.txtMainTitleUrdu)).setVisibility(View.VISIBLE);
                ((UrduTextView)findViewById(R.id.txtMainTitleUrdu)).setText("خصوصی آیات");
            }

        }
        mScaleDetector = new ScaleGestureDetector(this, new ScaleManager());
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
        contentLayout=(LinearLayout) findViewById(R.id.contentLayout);
        resetBookmarkList();

        findViewById(R.id.imgBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        imgListGridIcon=(ImageView)findViewById(R.id.imgListGridIcon);
        if(Constants1.sp.getString("format","grid").equalsIgnoreCase("grid"))
        {
            imgListGridIcon.setImageResource(R.drawable.icon_grid);
        }
        else
        {
            imgListGridIcon.setImageResource(R.drawable.icon_list);
        }
        imgListGridIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(Constants1.sp.getString("format","grid").equalsIgnoreCase("grid"))
                {

                    Constants1.editor.putString("format","list");
                    imgListGridIcon.setImageResource(R.drawable.icon_list);
                }
                else
                {
                    Constants1.editor.putString("format","grid");
                    imgListGridIcon.setImageResource(R.drawable.icon_grid);
                }
                Constants1.editor.commit();


            }
        });
    }
    int TOTAL_ROW;
    final TextView txtTranslation[]=new TextView[TOTAL_ROW];
    final ArabicTextView txtArabicTextView[]=new ArabicTextView[TOTAL_ROW];
    final View view_row[]=new View[TOTAL_ROW];
    final View viewLineHorizontal[]=new View[TOTAL_ROW];
    final View viewLineVertical[]=new View[TOTAL_ROW];

    String temp_translation="";
    String finalTasfeeer="";
    Vector<String> muntakhibUniqeName=new Vector<>();
    String tafseerLineSeperator="\n\n";

    public void resetBookmarkList() {
        muntakhibUniqeName.clear();
        Constants1.initSharedPref(this);
        Map<String, ?> allEntries = Constants1.sp.getAll();
        String IDs = "";


        if(isMuntaKhib==false) {
            for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
                if (entry.getKey().startsWith("bookmark_")) {
                    IDs = IDs + "" + (Integer.parseInt(Constants1.sp.getString(entry.getKey().toString(), ""))) + ",";
                }
            }
        }
        else {
            for (int i = 0; i < Constants1.SPECAIL_INDEX.length; i++) {
                IDs = IDs + "" + Constants1.SPECAIL_INDEX[i] + ",";

            }
        }

        if(IDs.trim().length()>0) {
            IDs = IDs.substring(0, IDs.lastIndexOf(","));

            Cursor cursor = Constants1.databaseHandler.getData("SELECT QA.PARA_NO,QA.SURA_NO,QA.QURAN_AYAT_NO,QA.SURA_AYAT_NO, QA.AYAT,\n" +
                    "QP.PARA_NAME,\n" +
                    "QS.SURA_NAME,\n" +
                    "QT.TRANSLATION_" + Constants1.LANGUAGE + ",\n" +
                    "QT.TAFSEER_" + Constants1.LANGUAGE + ",\n" +
                    "QT.ID" + ",\n" +
                    "QA.GROUP_NO" + "\n" +
                    "from QURAN_ARABIC QA, QURAN_PARA QP, QURAN_SURA QS, QURAN_TRANSLATION QT\n" +
                    "where QA.PARA_NO =  QP.ID and QT.ID in (" + IDs + ") and QA.SURA_NO = QS.ID\n" +
                    "and QA.ID=QT.ID order by QT.ID", Constants1.sqLiteDatabase);
            String PARA_NO, SURA_NO, QURAN_AYAT_NO, SURA_AYAT_NO, QURAN_AYAT, PARA_NAME, SURA_NAME, TRANSALATION, TAFSEER = "", ID = "", GROUP_NO = "";
            ArrayList<PageBean> pageBeanArrayList = new ArrayList<>();
            PageBean tempPageBean;
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    Log.v(Constants1.TAG, "***********ID - " + "*****************");
                    tempPageBean = new PageBean();
                    PARA_NO = "" + cursor.getInt(0);
                    SURA_NO = "" + cursor.getInt(1);
                    QURAN_AYAT_NO = "" + cursor.getInt(2);
                    SURA_AYAT_NO = "" + cursor.getInt(3);
                    QURAN_AYAT = "" + cursor.getString(4);
                    PARA_NAME = "" + cursor.getString(5);
                    SURA_NAME = "" + cursor.getString(6);
                    TRANSALATION = "" + cursor.getString(7);
                    TAFSEER = "" + cursor.getString(8);
                    ID = "" + cursor.getString(9);
                    GROUP_NO = "" + cursor.getString(10);

                    Log.v(Constants1.TAG, "PARA_NO: " + PARA_NO + "\nSURA_NO: " + SURA_NO + "\nPARA_NAME: " + PARA_NAME + "\nSURA_NAME:  " + SURA_NAME + "\nPARA_NAME:  " + PARA_NAME + "\nQURA_AYAT: " + QURAN_AYAT + "\nTRANSALATION: " + TRANSALATION);

                    tempPageBean.setPARA_NO(PARA_NO);
                    tempPageBean.setSURA_NO(SURA_NO);
                    tempPageBean.setQURAN_AYAT_NO(QURAN_AYAT_NO);
                    tempPageBean.setSURA_AYAT_NO(SURA_AYAT_NO);
                    tempPageBean.setQURA_AYAT(QURAN_AYAT);
                    tempPageBean.setPARA_NAME(PARA_NAME);
                    tempPageBean.setSURA_NAME(SURA_NAME);
                    tempPageBean.setTRANSALATION(TRANSALATION);
                    tempPageBean.setID(ID);
                    tempPageBean.setGROUP_NO(GROUP_NO);
//                if(TAFSEER!=null && TAFSEER.trim().length()>0)
                    tempPageBean.setTAFSEER(TAFSEER);
//                else tempPageBean.setTAFSEER("");

                    pageBeanArrayList.add(tempPageBean);
                }
                ScrollView scrollViewNested = (ScrollView) findViewById(R.id.scrollViewNested);


                int TOTAL_ROW = pageBeanArrayList.size();
                final TextView txtTranslation[] = new TextView[TOTAL_ROW];
                final ArabicTextView txtArabicTextView[] = new ArabicTextView[TOTAL_ROW];
                final View view_row[] = new View[TOTAL_ROW];
                final View viewLineHorizontal[] = new View[TOTAL_ROW];
                final View viewLineVertical[] = new View[TOTAL_ROW];
                final RelativeLayout layout_bookmarkContent[] = new RelativeLayout[TOTAL_ROW];
                final ArabicTextView txtBookmarkSurahName[] = new ArabicTextView[TOTAL_ROW];
                final ArabicTextView txtBookmarkParaName[] = new ArabicTextView[TOTAL_ROW];
                final LinearLayout layout_grid[] = new LinearLayout[TOTAL_ROW];
                final LinearLayout layout_list[] = new LinearLayout[TOTAL_ROW];
                final TextView txtTranslationList[] = new TextView[TOTAL_ROW];
                final ArabicTextView txtArabicList[] = new ArabicTextView[TOTAL_ROW];
                final TextView txtTafseer[] = new TextView[TOTAL_ROW];

                String temp_translation = "";
                String finalTasfeeer = "";
                for (int i = 0; i < TOTAL_ROW; i++) {
                    view_row[i] = View.inflate(this, R.layout.inflate_quran_page, null);

                    layout_grid[i] = (LinearLayout) view_row[i].findViewById(R.id.layout_grid);
                    layout_list[i] = (LinearLayout) view_row[i].findViewById(R.id.layout_list);

                    viewLineHorizontal[i] = view_row[i].findViewById(R.id.viewLineHorizontal);
                    viewLineVertical[i] = view_row[i].findViewById(R.id.viewLineVertical);

                    layout_bookmarkContent[i] = view_row[i].findViewById(R.id.layout_bookmarkContent);

                    txtBookmarkSurahName[i] = view_row[i].findViewById(R.id.txtBookmarkSurahName);
                    txtBookmarkParaName[i] = view_row[i].findViewById(R.id.txtBookmarkParaName);

                    if(muntakhibUniqeName.contains(pageBeanArrayList.get(i).getSURA_NAME())==false)
                    {muntakhibUniqeName.add(pageBeanArrayList.get(i).getSURA_NAME());
                        layout_bookmarkContent[i].setVisibility(View.VISIBLE);

                    }

                    if (Constants1.LANGUAGE.equalsIgnoreCase(Constants1.URDU)) {
                        txtTranslation[i] = (TextView) view_row[i].findViewById(R.id.txtUrdu);
                        txtTranslationList[i] = (TextView) view_row[i].findViewById(R.id.txtUrduList);
                        txtTafseer[i] = (TextView) view_row[i].findViewById(R.id.txtTafseerUrdu);


                    } else if (Constants1.LANGUAGE.equalsIgnoreCase(Constants1.GUJARATI)) {
                        txtTranslation[i] = (TextView) view_row[i].findViewById(R.id.txtGujarati);
                        txtTranslationList[i] = (TextView) view_row[i].findViewById(R.id.txtGujaratiList);
                        txtTafseer[i] = (TextView) view_row[i].findViewById(R.id.txtTafseerGujarati);

                    }

                    if(isMuntaKhib)
                    txtTafseer[i].setVisibility(View.VISIBLE);

                    txtArabicTextView[i] = (ArabicTextView) view_row[i].findViewById(R.id.txtArabic);
                    txtArabicList[i] = (ArabicTextView) view_row[i].findViewById(R.id.txtArabicList);

                    temp_translation = pageBeanArrayList.get(i).getTRANSALATION();
                    finalTasfeeer = "";
                    if(isMuntaKhib) {
                        if (pageBeanArrayList.get(i).getTAFSEER() != null && pageBeanArrayList.get(i).getTAFSEER().trim().length() > 0 &&
                                pageBeanArrayList.get(i).getTAFSEER().trim().equalsIgnoreCase("null") == false) {
                            if (finalTasfeeer.length() > 0)
                                finalTasfeeer = finalTasfeeer + tafseerLineSeperator + pageBeanArrayList.get(i).getTAFSEER();
                            else finalTasfeeer = pageBeanArrayList.get(i).getTAFSEER();
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

                    txtTranslation[i].setText(temp_translation);
                    txtTranslationList[i].setText(temp_translation);

                    txtBookmarkParaName[i].setText(pageBeanArrayList.get(i).getPARA_NAME());
                    txtBookmarkSurahName[i].setText(pageBeanArrayList.get(i).getSURA_NAME());

                    txtArabicTextView[i].setText(pageBeanArrayList.get(i).getQURAN_AYAT());
                    txtArabicList[i].setText(pageBeanArrayList.get(i).getQURAN_AYAT());


                    view_row[i].setOnLongClickListener(new RowLongClick(i, pageBeanArrayList.get(i), txtArabicTextView[i]));
                    view_row[i].setOnClickListener(new RowClick(i, pageBeanArrayList.get(i)));

                    if (Constants1.sp.contains("bookmark_" + pageBeanArrayList.get(i).getID()))
                        view_row[i].setBackgroundColor(getResources().getColor(R.color.colorSurahParaBG));
                    contentLayout.addView(view_row[i]);
                    Log.v(Constants1.TAG, "----------" + i);
                }

//            if (finalTasfeeer.trim().length() > 0) {
//                if (Constants1.LANGUAGE.equalsIgnoreCase(Constants1.GUJARATI)) {
//                    finalTasfeeer = finalTasfeeer.replaceAll("0", "૦").replaceAll("1", "૧")
//                            .replaceAll("2", "૨").replaceAll("3", "૩").replaceAll("4", "૪")
//                            .replaceAll("5", "૫").replaceAll("6", "૬").replaceAll("7", "૭").replaceAll("8", "૮").replaceAll("9", "૯");
//                }
//                txtTafseer.setText(finalTasfeeer);
//            } else txtTafseer.setVisibility(View.GONE);

                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                for (int i = 0; i < TOTAL_ROW; i++) {
                                    txtTranslation[i].setTextSize(2, (float) Constants1.sp.getInt("perf_font_size", Constants1.DEFAULT_FONT));
                                    txtArabicTextView[i].setTextSize(2, (float) Constants1.sp.getInt("perf_font_size", Constants1.DEFAULT_FONT) * 1.3f);

                                    txtTranslationList[i].setTextSize(2, (float) Constants1.sp.getInt("perf_font_size", Constants1.DEFAULT_FONT));
                                    txtArabicList[i].setTextSize(2, (float) Constants1.sp.getInt("perf_font_size", Constants1.DEFAULT_FONT) * 1.3f);


                                    txtTranslation[i].setTextColor(Color.parseColor("#" + Constants1.sp.getString("perf_font_color_urdu", "000000")));
                                    txtArabicTextView[i].setTextColor(Color.parseColor("#" + Constants1.sp.getString("perf_font_color_arabic", "000000")));

                                    txtTranslationList[i].setTextColor(Color.parseColor("#" + Constants1.sp.getString("perf_font_color_urdu", "000000")));
                                    txtArabicList[i].setTextColor(Color.parseColor("#" + Constants1.sp.getString("perf_font_color_arabic", "000000")));


                                    txtBookmarkSurahName[i].setTextSize(2, (float) Constants1.sp.getInt("perf_font_size", Constants1.DEFAULT_FONT) * 1.3f);
                                    txtBookmarkParaName[i].setTextSize(2, (float) Constants1.sp.getInt("perf_font_size", Constants1.DEFAULT_FONT) * 1.3f);

                                    viewLineVertical[i].setBackgroundColor(Color.parseColor("#" + Constants1.sp.getString("perf_line_color", "000000")));
                                    viewLineHorizontal[i].setBackgroundColor(Color.parseColor("#" + Constants1.sp.getString("perf_line_color", "000000")));
                                    txtTafseer[i].setTextSize(2, (float) Constants1.sp.getInt("perf_font_size", Constants1.DEFAULT_FONT));


                                    if (Constants1.sp.getString("format", "list").equalsIgnoreCase("grid")) {
                                        layout_grid[i].setVisibility(View.VISIBLE);
                                        layout_list[i].setVisibility(View.GONE);
                                    } else {
                                        layout_grid[i].setVisibility(View.GONE);
                                        layout_list[i].setVisibility(View.VISIBLE);
                                    }

                                }
//                            txtTafseer.setTextSize(2, (float) Constants1.sp.getInt("perf_font_size", Constants1.DEFAULT_FONT));
                            }

                            ;
                        });
                    }
                }, 0, 100);
            }
        }
        else
        {
            Toast.makeText(this, "No bookmark available", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
    public class RowClick implements View.OnClickListener {
        int pos;
        PageBean pageBean;
        View clickView;

        public RowClick(int pos, PageBean pageBean) {
            this.pos = pos;
            this.pageBean = pageBean;
        }

        public void onClick(View view) {
            Intent b=new Intent();
            b.putExtra("group",pageBean.getGROUP_NO());
            setResult(200,b);
            finish();
        }
    }
            PopupMenu p;
        public class RowLongClick implements View.OnLongClickListener {
            int pos;
            PageBean pageBean;
            View clickView;
            public RowLongClick(int pos,PageBean pageBean,View clickView)
            {
                this.pos=pos;
                this.pageBean=pageBean;
                this.clickView=clickView;
            }

            public boolean onLongClick(View view) {
                Constants1.initSharedPref(BookmarkActivity.this);

                if(p==null) {

                    p = new PopupMenu(BookmarkActivity.this, clickView);
                    p.setOnDismissListener(new PopupMenu.OnDismissListener() {
                        @Override
                        public void onDismiss(PopupMenu menu) {
                            p = null;
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
                                Toast.makeText(BookmarkActivity.this, "Added to Bookmark", Toast.LENGTH_LONG).show();
                                view.setBackgroundColor(getResources().getColor(R.color.colorSurahParaBG));

                            } else if (item.getItemId() == R.id.removeaddbookmark) {
                                Constants1.editor.remove("bookmark_" + pageBean.getID());
                                Constants1.editor.commit();
                                Toast.makeText(BookmarkActivity.this, "Removed from Bookmark", Toast.LENGTH_LONG).show();
                                view.setVisibility(View.GONE);
                            }
//                            else if (item.getItemId() == R.id.copyarabic) {
//                                actionOnText(0, pageBean, 0);
//                            } else if (item.getItemId() == R.id.copytranslation) {
//                                actionOnText(1, pageBean, 0);
//                            } else if (item.getItemId() == R.id.copyarabictranslation) {
//                                actionOnText(2, pageBean, 0);
//                            } else if (item.getItemId() == R.id.sharearabic) {
//                                actionOnText(0, pageBean, 1);
//                            }
//                            else if (item.getItemId() == R.id.sharetranslation) {
//                                actionOnText(1, pageBean, 1);
//                            }
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
        public void openSubMenu ()
        {

        }
        public void actionOnText ( int actionOn, PageBean pageBean,int actionType)
        {
            String translation = "",tafseer="";
            if (Constants1.LANGUAGE.equalsIgnoreCase(Constants1.GUJARATI)) {
                translation = pageBean.getTRANSALATION().replaceAll("0", "૦").replaceAll("1", "૧")
                        .replaceAll("2", "૨").replaceAll("3", "૩").replaceAll("4", "૪")
                        .replaceAll("5", "૫").replaceAll("6", "૬").replaceAll("7", "૭").replaceAll("8", "૮").replaceAll("9", "૯");


                if (pageBean.getTAFSEER() != null && pageBean.getTAFSEER().trim().length() > 0 &&
                        pageBean.getTAFSEER().trim().equalsIgnoreCase("null") == false) {
                    tafseer = "તફસીર: "+pageBean.getTAFSEER().replaceAll("0", "૦").replaceAll("1", "૧")
                            .replaceAll("2", "૨").replaceAll("3", "૩").replaceAll("4", "૪")
                            .replaceAll("5", "૫").replaceAll("6", "૬").replaceAll("7", "૭").replaceAll("8", "૮").replaceAll("9", "૯");;
                }

            } else if (Constants1.LANGUAGE.equalsIgnoreCase(Constants1.URDU)) {
                translation = pageBean.getTRANSALATION().replaceAll("0", "۰").replaceAll("1", "۱")
                        .replaceAll("2", "۲").replaceAll("3", "۳").replaceAll("4", "۴")
                        .replaceAll("5", "۵").replaceAll("6", "۶").replaceAll("7", "۷").replaceAll("8", "۸").replaceAll("9", "۹");

                if (pageBean.getTAFSEER() != null && pageBean.getTAFSEER().trim().length() > 0 &&
                        pageBean.getTAFSEER().trim().equalsIgnoreCase("null") == false) {
                    tafseer ="تفسیر: "+ pageBean.getTAFSEER().replaceAll("0","۰").replaceAll("1","۱")
                            .replaceAll("2","۲").replaceAll("3","۳").replaceAll("4","۴")
                            .replaceAll("5","۵").replaceAll("6","۶").replaceAll("7","۷").replaceAll("8","۸").replaceAll("9","۹");
                }
            }

            String selectedText = "";
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            if (actionOn == 0) selectedText = pageBean.getQURAN_AYAT();
            else if (actionOn == 1) selectedText = translation;
            else if(actionOn==2)selectedText=pageBean.getQURAN_AYAT()+"\n\n"+translation;
            else if(actionOn==3)selectedText=pageBean.getQURAN_AYAT()+"\n\n"+translation+"\n\n"+tafseer;

            selectedText = selectedText + "\n\n[" + pageBean.getPARA_NAME() + ", " + pageBean.getSURA_NAME() + "]";


            if (actionType == 0) {
                ClipData clip = ClipData.newPlainText("label", selectedText);
                if (clipboard == null || clip == null) return;
                clipboard.setPrimaryClip(clip);
                Toast.makeText(BookmarkActivity.this, "Copied to Clipboard", Toast.LENGTH_LONG).show();
            } else {
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
            //if(layout_setting.getVisibility()!=View.VISIBLE)
            {
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
            //if(layout_setting.getVisibility()!=View.VISIBLE)
            {
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