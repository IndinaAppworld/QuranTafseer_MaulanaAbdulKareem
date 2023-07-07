package com.indapp.fragements;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.indapp.beans.DataBean;
import com.indapp.fonts.GujaratiBoldTextView;
import com.indapp.fonts.GujaratiTextView;
import com.indapp.islamicknowledge.R;
import com.indapp.utils.Constants1;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class DuaFragement extends Fragment {


    public static final String EXTRA_ID = "ID";
    public static final String EXTRA_TYPE = "TYPE";
    public static final DuaFragement newInstance(String ID,String type)
    {
        DuaFragement f = new DuaFragement();
        Bundle bdl = new Bundle(2);
        bdl.putString(EXTRA_ID, ID);
        bdl.putString(EXTRA_TYPE,type);
        f.setArguments(bdl);
        return f;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view1 = inflater.inflate(R.layout.fragment_dua, container, false);
        LinearLayout  contentLayout=(LinearLayout)view1.findViewById(R.id.contentLayout);
        ImageView imgFav=(ImageView)view1.findViewById(R.id.imgFav);
        Log.v(Constants1.TAG,"PAGE NO----------->"+getArguments().getString(EXTRA_ID));
        ArrayList<DataBean> dataBeanArrayList=new ArrayList<>();
        String type=""+getArguments().getString(EXTRA_TYPE);
        String currentPageNo=""+getArguments().getString(EXTRA_ID);
        Log.v(Constants1.TAG,"-------------->"+currentPageNo);
        DataBean dataBean;
        Cursor cursor;

        String TITLE = "";
        int SURAHNO,AYATNO;
        Cursor cursor1;
//        if(getArguments().getInt(EXTRA_TYPE)==Constants1.TYPE_FORTRESS)
        {
            cursor=Constants1.databaseHandler.getData("select HEADING_"+Constants1.LANGUAGE+", TRANSLATION_"+Constants1.LANGUAGE+", TAFSEER_"+Constants1.LANGUAGE+", SURAHNO, AYATNO from dua where ID ="+getArguments().getString(EXTRA_ID)+"",Constants1.sqLiteDatabase);
            while (cursor.moveToNext()) {
                Log.v(Constants1.TAG, "SUBTITLE----->" + cursor.getString(1));
                dataBean = new DataBean();
                dataBean.setSR("");
                dataBean.setTitle(cursor.getString(0));
                //dataBean.setSubtitle(cursor.getString(1));
                if (TITLE.length() == 0) TITLE = cursor.getString(0);

                SURAHNO =cursor.getInt(3);
                 AYATNO=cursor.getInt(4);
                //dataBean.setTitleid(cursor.getString(2));
                //dataBean.setPageno(cursor.getString(3));
                //dataBean.setDuano(cursor.getString(4));

                cursor1=Constants1.databaseHandler.getData("select AYAT from QURAN_ARABIC where SURA_NO ='"+SURAHNO+"' and SURA_AYAT_NO='"+AYATNO+"'",Constants1.sqLiteDatabase);
                Log.v(Constants1.TAG,"select AYAT from QURAN_ARABIC where SURA_NO ="+SURAHNO+" and SURA_AYAT_NO="+AYATNO);
                if(cursor1!=null) {
                    while (cursor1.moveToNext()) {
                        dataBean.setArabicdua(cursor1.getString(0));//cursor.getString(5));

                    }
                }
                dataBean.setTranslation(cursor.getString(1));

                //dataBean.setReferance(cursor.getString(7));
                dataBean.setFazilat(cursor.getString(2));
                //dataBean.setFav(cursor.getString(9));

                dataBeanArrayList.add(dataBean);
            }
        }



        if(Constants1.LANGUAGE.equalsIgnoreCase(Constants1.GUJARATI) )//|| Constants1.LANGUAGE.equalsIgnoreCase(Constants1.ROMAN))
        {
            ((GujaratiBoldTextView)view1.findViewById(R.id.txtTitle)).setText(""+TITLE);
//            ((GujaratiTextView)view1.findViewById(R.id.txtTitle_urdu)).setVisibility(View.GONE);
//            ((GujaratiTextView)view1.findViewById(R.id.txtTitle_Hindi)).setVisibility(View.GONE);
        }
//        else if(Constants1.LANGUAGE.equalsIgnoreCase(Constants1.URDU))
//        {
//            ((SwissBoldFont)view1.findViewById(R.id.txtTitle)).setVisibility(View.GONE);
//            ((NoriNastalicUrduFont)view1.findViewById(R.id.txtTitle_urdu)).setVisibility(View.VISIBLE);
//            ((NoriNastalicUrduFont)view1.findViewById(R.id.txtTitle_urdu)).setText(Html.fromHtml("<b>"+TITLE+"</b>"));
//            ((HindiFont)view1.findViewById(R.id.txtTitle_Hindi)).setVisibility(View.GONE);
//        }
//        else if(Constants1.LANGUAGE.equalsIgnoreCase(Constants1.HINDI))
//        {
//            ((SwissBoldFont)view1.findViewById(R.id.txtTitle)).setVisibility(View.GONE);
//            ((NoriNastalicUrduFont)view1.findViewById(R.id.txtTitle_urdu)).setVisibility(View.GONE);
//            ((HindiFont)view1.findViewById(R.id.txtTitle_Hindi)).setVisibility(View.VISIBLE);
//            ((HindiFont)view1.findViewById(R.id.txtTitle_Hindi)).setText(Html.fromHtml("<b>"+TITLE+"</b>"));
//
//        }
        Constants1.initSharedPref(getActivity());
        Log.v(Constants1.TAG,"*********************>>>>>>>"+type+"_"+(Integer.parseInt(currentPageNo)));
        if(Constants1.sp.contains(type+"_"+(Integer.parseInt(currentPageNo)))==true)
        {
            imgFav.setImageResource(R.drawable.favorite_dua_sel);
        }

        int TOTAL_ROW=dataBeanArrayList.size();
        View view[]=new View[TOTAL_ROW];
        View view_line1[]=new View[TOTAL_ROW];
        View view_line2[]=new View[TOTAL_ROW];
        View view_line3[]=new View[TOTAL_ROW];

        ImageView btnShare[]=new ImageView[TOTAL_ROW];
        LinearLayout contentLayout1[]=new LinearLayout[TOTAL_ROW];
        
        TextView txtArabicTextView[]=new TextView[TOTAL_ROW];
        TextView txtTranslation[]=new TextView[TOTAL_ROW];
        TextView txtTafseer[]=new TextView[TOTAL_ROW];
        TextView txtIndex[]=new TextView[TOTAL_ROW];
       // NoriNastalicUrduFont txtTrans[]=new NoriNastalicUrduFont[TOTAL_ROW];
        imgFav.setOnClickListener(new FavClickListener(Integer.parseInt(currentPageNo),imgFav));
        for(int i=0;i<TOTAL_ROW;i++)
        {
            if(true)//Constants1.LANGUAGE.equalsIgnoreCase(Constants1.GUJARATI))
            {
                view[i]=View.inflate(getActivity(),R.layout.inflate_content_gujarati,null);
            }
//            else if(Constants1.LANGUAGE.equalsIgnoreCase(Constants1.ENGLISH) || Constants1.LANGUAGE.equalsIgnoreCase(Constants1.ROMAN))
//            view[i]=View.inflate(getActivity(),R.layout.inflate_content,null);
//            else if(Constants1.LANGUAGE.equalsIgnoreCase(Constants1.HINDI))
//                view[i]=View.inflate(getActivity(),R.layout.inflate_content_hindi,null);

            contentLayout1[i]=(LinearLayout)view[i].findViewById(R.id.contentLayout);
            btnShare[i]=(ImageView)view[i].findViewById(R.id.btnShare);
            view_line1[i]=(View)view[i].findViewById(R.id.view_line1);
            view_line2[i]=(View)view[i].findViewById(R.id.view_line2);
            view_line3[i]=(View)view[i].findViewById(R.id.view_line3);

            txtArabicTextView[i]=(TextView)view[i].findViewById(R.id.txtArabic);
            txtTranslation[i]=(TextView)view[i].findViewById(R.id.txtTrans);
            txtTafseer[i]=(TextView)view[i].findViewById(R.id.txtFazilat) ;
            txtIndex[i]=(TextView)view[i].findViewById(R.id.txtIndex);
            btnShare[i].setOnClickListener(new ShareClickListener(contentLayout1[i],TITLE,btnShare[i]));
            if(dataBeanArrayList.get(i).getArabicdua()==null || dataBeanArrayList.get(i).getArabicdua().trim().length()==0)
            {
                view_line1[i].setVisibility(View.GONE);
                 txtArabicTextView[i].setVisibility(View.GONE);
            }
            else
            {
                ( (TextView)view[i].findViewById(R.id.txtArabic)).setText(dataBeanArrayList.get(i).getArabicdua().trim());
            }

            if(dataBeanArrayList.get(i).getTranslation()==null || dataBeanArrayList.get(i).getTranslation().trim().length()==0)
            {
                view_line2[i].setVisibility(View.GONE);
                txtTranslation[i].setVisibility(View.GONE);
            }
            else
            {
                txtTranslation[i].setText(Constants1.replaceNumbers(dataBeanArrayList.get(i).getTranslation().trim()));
            }

            if(dataBeanArrayList.get(i).getFazilat()==null || dataBeanArrayList.get(i).getFazilat().trim().length()==0)
            {
                view_line3[i].setVisibility(View.GONE);
                txtTafseer[i].setVisibility(View.GONE);
            }
            else
            {
//                if(Constants1.LANGUAGE.equalsIgnoreCase(Constants1.URDU))
//                {
//                    txtTafseer[i].setText((""+dataBeanArrayList.get(i).getFazilat().trim()));
//                }
//                else
                txtTafseer[i].setText((dataBeanArrayList.get(i).getFazilat().trim()));
            }

            if(dataBeanArrayList.get(i).getReferance()!=null && dataBeanArrayList.get(i).getReferance().equalsIgnoreCase("NA")==false)
            {
                ((TextView)view[i].findViewById(R.id.txtReferance)).setText(""+Constants1.replaceNumbers(dataBeanArrayList.get(i).getReferance().trim()));
            }
            else
            {
                ((TextView)view[i].findViewById(R.id.txtReferance)).setVisibility(View.INVISIBLE);
            }
            txtIndex[i].setText(Constants1.replaceNumbers(""+(i+1))+"/"+Constants1.replaceNumbers(""+TOTAL_ROW));
            contentLayout.addView(view[i]);
        }



        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {

                try {

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {


                                if (true)//Constants1.sp.getBoolean("arabic", false) == false)
                                {
                                    for (int i = 0; i < TOTAL_ROW; i++) {
                                        txtTranslation[i].setTextSize(2, (float) Constants1.sp.getInt("perf_font_size", Constants1.DEFAULT_FONT));
                                        txtArabicTextView[i].setTextSize(2, (float) Constants1.sp.getInt("perf_font_size", Constants1.DEFAULT_FONT) * 1.3f);

//                                        txtTranslationList[i].setTextSize(2, (float) Constants1.sp.getInt("perf_font_size", Constants1.DEFAULT_FONT));
//                                        txtArabicList[i].setTextSize(2, (float) Constants1.sp.getInt("perf_font_size", Constants1.DEFAULT_FONT) * 1.3f);
//
//                                        if (pageBeanArrayList.get(i).getQURAN_AYAT_NO() != null && pageBeanArrayList.get(i).getQURAN_AYAT_NO().equalsIgnoreCase("-1") == false
//                                                && pageBeanArrayList.get(i).getQURAN_AYAT_NO() != null && pageBeanArrayList.get(i).getSURA_AYAT_NO().equalsIgnoreCase("0") == false) {
//                                            txtTranslation[i].setTextColor(Color.parseColor("#" + Constants1.sp.getString("perf_font_color_urdu", "000000")));
//                                            txtArabicTextView[i].setTextColor(Color.parseColor("#" + Constants1.sp.getString("perf_font_color_arabic", "000000")));
//
//                                            txtTranslationList[i].setTextColor(Color.parseColor("#" + Constants1.sp.getString("perf_font_color_urdu", "000000")));
//                                            txtArabicList[i].setTextColor(Color.parseColor("#" + Constants1.sp.getString("perf_font_color_arabic", "000000")));
//                                        }

//                                        viewLineHorizontal[i].setBackgroundColor(Color.parseColor("#" + Constants1.sp.getString("perf_line_color", "000000")));
//                                        viewLineVertical[i].setBackgroundColor(Color.parseColor("#" + Constants1.sp.getString("perf_line_color", "000000")));

                                        txtIndex[i].setTextSize(2, (float) Constants1.sp.getInt("perf_font_size", Constants1.DEFAULT_FONT));
                                        txtTafseer[i].setTextSize(2, (float) Constants1.sp.getInt("perf_font_size", Constants1.DEFAULT_FONT));


                                    }
                                }
//                                else {
//
//                                    for (int i = 0; i < TOTAL_ROW; i++) {
//                                        txtArabicFullText[i].setTextSize(2, (float) Constants1.sp.getInt("perf_font_size", Constants1.DEFAULT_FONT) * 1.3f);
//                                        if (pageBeanArrayList.get(i).getQURAN_AYAT_NO().equalsIgnoreCase("-1") == false
//                                                && pageBeanArrayList.get(i).getSURA_AYAT_NO().equalsIgnoreCase("0") == false) {
//                                            txtArabicFullText[i].setTextColor(Color.parseColor("#" + Constants1.sp.getString("perf_font_color_arabic", "000000")));
//                                        }
//                                    }
//                                }
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
            Constants1.initSharedPref(getActivity());

            Log.v(Constants1.TAG,"On Favorite Click--->"+getArguments().getString(EXTRA_TYPE)+"_"+pageno);
            if(Constants1.sp.contains(getArguments().getString(EXTRA_TYPE)+"_"+pageno)==false)
            {
                Constants1.editor.putString(getArguments().getString(EXTRA_TYPE)+"_"+pageno,""+pageno);
                Constants1.editor.commit();

                imgFav.setImageResource(R.drawable.favorite_dua_sel);
                Toast.makeText(getActivity(),"Added to Favourite",Toast.LENGTH_SHORT).show();
            }
            else
            {
                Constants1.editor.remove(getArguments().getString(EXTRA_TYPE)+"_"+pageno);
                Constants1.editor.commit();

                imgFav.setImageResource(R.drawable.favorite_dua_unesl);
                Toast.makeText(getActivity(),"Removed from Favourite",Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class ShareClickListener implements View.OnClickListener
    {
        int pos;
        View view1;
        String TITLE;
        ImageView btnShare;
        public ShareClickListener(View view,String TITLE,ImageView btnShare)
        {
            this.view1=view;
            this.TITLE=TITLE;
            this.btnShare=btnShare;

        }
        public Bitmap getBitmapFromView(View view)
        {
            Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_4444);
            Canvas canvas = new Canvas(bitmap);
            view.draw(canvas);
            return bitmap;
        }
        public void onClick(View view)
        {
            try
            {
                btnShare.setVisibility(View.INVISIBLE);
//                view1.setDrawingCacheEnabled(true);
                Log.v(Constants1.TAG,"11111111111111111111111111");
                Bitmap bitmap = getBitmapFromView(view1);//Bitmap.createBitmap(view1.getDrawingCache());
                Log.v(Constants1.TAG,"22222222222222222222222222");

                File mainDire = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES );

                File subDire = new File(mainDire.getAbsolutePath(), "share.png");
                subDire.createNewFile();
                FileOutputStream stream = new FileOutputStream(subDire); // overwrites this image every time
                Log.v(Constants1.TAG,"333333333333333333333333333");
                bitmap.compress(Bitmap.CompressFormat.PNG, 70, stream);
                Log.v(Constants1.TAG,"4444444444444444444444444444");
                btnShare.setVisibility(View.VISIBLE);
                stream.close();
                stream.flush();


//                StrictMode.ThreadPolicy policy =
//                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
//            StrictMode.setThreadPolicy(policy);

                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());


                 String AUTHORITY="com.indapp.supplications.fileprovider";

                Log.v(Constants1.TAG,"FILE PATH-------->"+getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/share.png");
                Intent intent = new Intent();
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setAction(Intent.ACTION_SEND);
                Uri uri = Uri.fromFile(new File(getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/share.png"));

                File file=new File(getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/share.png");

                Uri contentUri = FileProvider.getUriForFile(getActivity(), AUTHORITY, file);

                intent.setDataAndType(uri, "image/*");
                intent.putExtra(Intent.EXTRA_TEXT,""+TITLE+"\n\n"+Constants1.share_data);
                intent.putExtra(Intent.EXTRA_STREAM,contentUri);
                startActivity(intent);
            }
            catch (Exception e)
            {
                Toast.makeText(getContext(),""+e,Toast.LENGTH_SHORT).show();;
                Log.v(Constants1.TAG,"Error in Capturing Screen-->"+e);
            }
        }
    }

    private void changeButtonColor(ImageView imageView,String COLOR) {
        try {
            int color = Color.parseColor(COLOR);//getResources().getColor(R.color.));
            imageView.setColorFilter(color);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
