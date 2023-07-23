package com.indapp.fragements;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
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
    public static final String EXTRA_TYPE = "";
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
        if(type.equalsIgnoreCase(Constants1.TYPE_DUA))
        {
            cursor=Constants1.databaseHandler.getData("select HEADING_"+Constants1.LANGUAGE+", TRANSLATION_"+Constants1.LANGUAGE+", TAFSEER_"+Constants1.LANGUAGE+", SURAHNO, AYATNO" +", HAWALA_TRANSLATION_"+Constants1.LANGUAGE+", HAWALA_TAFSEER_"+Constants1.LANGUAGE+" from dua where ID ="+getArguments().getString(EXTRA_ID)+"",Constants1.sqLiteDatabase);
            while (cursor.moveToNext()) {
                Log.v(Constants1.TAG, "SUBTITLE----->" + cursor.getString(1));
                dataBean = new DataBean();
                dataBean.setSR("");
                dataBean.setTitle(cursor.getString(0));
                //dataBean.setSubtitle(cursor.getString(1));
                if (TITLE.length() == 0) TITLE = cursor.getString(0);

                SURAHNO =cursor.getInt(3);
                 AYATNO=cursor.getInt(4);
                dataBean.setReferenceTrans(cursor.getString(5));
                dataBean.setReferenceFazilat(cursor.getString(6));

                Log.v(Constants1.TAG,"Reference-->"+cursor.getString(5)+">>>"+cursor.getString(6));
                cursor1=Constants1.databaseHandler.getData("select AYAT from QURAN_ARABIC where SURA_NO ='"+SURAHNO+"' and SURA_AYAT_NO='"+AYATNO+"'",Constants1.sqLiteDatabase);
                Log.v(Constants1.TAG,"select AYAT from QURAN_ARABIC where SURA_NO ="+SURAHNO+" and SURA_AYAT_NO="+AYATNO);
                if(cursor1!=null) {
                    while (cursor1.moveToNext()) {
                        dataBean.setArabicdua(cursor1.getString(0));//cursor.getString(5));
                    }
                }
                dataBean.setTranslation(cursor.getString(1));
                dataBean.setFazilat(cursor.getString(2));
                dataBeanArrayList.add(dataBean);
            }
        }
        else if(type.equalsIgnoreCase(Constants1.TYPE_DAROOD))
        {
            cursor=Constants1.databaseHandler.getData("select HEADING_"+Constants1.LANGUAGE+", TRANSLATION_"+Constants1.LANGUAGE+", TAFSEER_"+Constants1.LANGUAGE+", ARABIC," +" HAWALA_"+Constants1.LANGUAGE+" from darood where ID ="+getArguments().getString(EXTRA_ID)+"",Constants1.sqLiteDatabase);
            while (cursor.moveToNext()) {
                Log.v(Constants1.TAG, "SUBTITLE----->" + cursor.getString(1));
                dataBean = new DataBean();
                dataBean.setSR("");
                dataBean.setTitle(cursor.getString(0));
                //dataBean.setSubtitle(cursor.getString(1));
                if (TITLE.length() == 0) TITLE = cursor.getString(0);


                dataBean.setReferenceTrans("");
                dataBean.setReferenceFazilat(cursor.getString(4));

//                Log.v(Constants1.TAG,"Reference-->"+cursor.getString(5)+">>>"+cursor.getString(6));
                dataBean.setArabicdua(cursor.getString(3));
                dataBean.setTranslation(cursor.getString(1));
                dataBean.setFazilat(cursor.getString(2));
                dataBeanArrayList.add(dataBean);
            }
        }


        Constants1.setFontTypeFaceLanguage(((TextView)view1.findViewById(R.id.txtTitle)),getActivity(),true);

        Constants1.initSharedPref(getActivity());
        Log.v(Constants1.TAG,"*********************>>>>>>>"+type+"_"+(Integer.parseInt(currentPageNo)));
        if(Constants1.sp.contains(type+"_"+(Integer.parseInt(currentPageNo)))==true)
        {
            imgFav.setImageResource(R.drawable.favorite_dua_white_sel);
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
        TextView txtFazilatReference[]=new TextView[TOTAL_ROW];
        TextView txtTransReference[]=new TextView[TOTAL_ROW];
        TextView txtIndex[]=new TextView[TOTAL_ROW];
        TextView txtTitleDarood[]=new TextView[TOTAL_ROW];

       // NoriNastalicUrduFont txtTrans[]=new NoriNastalicUrduFont[TOTAL_ROW];
        imgFav.setOnClickListener(new FavClickListener(Integer.parseInt(currentPageNo),imgFav));
        for(int i=0;i<TOTAL_ROW;i++)
        {
            if(Constants1.LANGUAGE.equalsIgnoreCase(Constants1.GUJARATI))
            {
                view[i]=View.inflate(getActivity(),R.layout.inflate_content_gujarati,null);
            }
            else if(Constants1.LANGUAGE.equalsIgnoreCase(Constants1.URDU))
            {
                view[i]=View.inflate(getActivity(),R.layout.inflate_content_urdu,null);
            }

           contentLayout1[i]=(LinearLayout)view[i].findViewById(R.id.contentLayout);
            btnShare[i]=(ImageView)view[i].findViewById(R.id.btnShare);
            view_line1[i]=(View)view[i].findViewById(R.id.view_line1);
            view_line2[i]=(View)view[i].findViewById(R.id.view_line2);
            view_line3[i]=(View)view[i].findViewById(R.id.view_line3);


            txtArabicTextView[i]=(TextView)view[i].findViewById(R.id.txtArabic);
            txtTranslation[i]=(TextView)view[i].findViewById(R.id.txtTrans);
            txtTafseer[i]=(TextView)view[i].findViewById(R.id.txtFazilat) ;
            txtIndex[i]=(TextView)view[i].findViewById(R.id.txtIndex);
            txtTitleDarood[i]=(TextView)view[i].findViewById(R.id.txtTitleDarood);

            txtTransReference[i]=(TextView)view[i].findViewById(R.id.txtTransReference);
            txtFazilatReference[i]=(TextView)view[i].findViewById(R.id.txtFazilatReference);

            btnShare[i].setOnClickListener(new ShareClickListener(contentLayout1[i],TITLE,btnShare[i],    txtTitleDarood[i]));
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
                txtTransReference[i].setVisibility(View.GONE);

            }
            else
            {
                txtTranslation[i].setText(Constants1.replaceNumbers(dataBeanArrayList.get(i).getTranslation().trim()));


                if(dataBeanArrayList.get(i).getReferenceTrans()!=null && dataBeanArrayList.get(i).getReferenceTrans().trim().length()>0)
                txtTransReference[i].setText(Constants1.replaceNumbers(dataBeanArrayList.get(i).getReferenceTrans()));
                else txtTransReference[i].setVisibility(View.GONE);

            }

            if(dataBeanArrayList.get(i).getFazilat()==null || dataBeanArrayList.get(i).getFazilat().trim().length()==0)
            {
                view_line3[i].setVisibility(View.GONE);
                txtTafseer[i].setVisibility(View.GONE);
                txtFazilatReference[i].setVisibility(View.GONE);
            }
            else
            {
                txtTafseer[i].setText((dataBeanArrayList.get(i).getFazilat()));

                if(dataBeanArrayList.get(i).getReferenceFazilat()!=null && dataBeanArrayList.get(i).getReferenceFazilat().trim().length()>0)
                txtFazilatReference[i].setText(Constants1.replaceNumbers(dataBeanArrayList.get(i).getReferenceFazilat()));
                else txtFazilatReference[i].setVisibility(View.GONE);

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
            if(type.equalsIgnoreCase(Constants1.TYPE_DAROOD))
            {
                txtIndex[i].setVisibility(View.GONE);
                txtTitleDarood[i].setVisibility(View.VISIBLE);
//                view_line4[i].setVisibility(View.VISIBLE);
                txtTitleDarood[i].setText((TITLE));
                ((TextView)view1.findViewById(R.id.txtTitle)).setText("");

            }
            else {
                ((TextView)view1.findViewById(R.id.txtTitle)).setText(""+TITLE);
                txtTitleDarood[i].setText((TITLE));
            }

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
                                        txtTitleDarood[i].setTextSize(2, (float) Constants1.sp.getInt("perf_font_size", Constants1.DEFAULT_FONT) * 1f);


                                        txtIndex[i].setTextSize(2, (float) Constants1.sp.getInt("perf_font_size", Constants1.DEFAULT_FONT));
                                        txtTafseer[i].setTextSize(2, (float) Constants1.sp.getInt("perf_font_size", Constants1.DEFAULT_FONT));

                                        txtFazilatReference[i].setTextSize(2, (float) Constants1.sp.getInt("perf_font_size", Constants1.DEFAULT_FONT)*0.8f);
                                        txtTransReference[i].setTextSize(2, (float) Constants1.sp.getInt("perf_font_size", Constants1.DEFAULT_FONT)*0.8f);



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

                imgFav.setImageResource(R.drawable.favorite_dua_white_sel);
                Toast.makeText(getActivity(),"Added to Favourite",Toast.LENGTH_SHORT).show();
            }
            else
            {
                Constants1.editor.remove(getArguments().getString(EXTRA_TYPE)+"_"+pageno);
                Constants1.editor.commit();

                imgFav.setImageResource(R.drawable.favorite_dua_white_unsel);
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
        TextView txtDaroodTitle;
        public ShareClickListener(View view,String TITLE,ImageView btnShare, TextView txtDaroodTitle)
        {
            this.view1=view;
            this.TITLE=TITLE;
            this.btnShare=btnShare;
            this.txtDaroodTitle=txtDaroodTitle;

        }
        public Bitmap getBitmapFromView(View view)
        {
//            view.findViewById(R.id.txtTitleDarood).setVisibility(View.VISIBLE);
            Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_4444);
            Canvas canvas = new Canvas(bitmap);
            view.draw(canvas);
//            view.findViewById(R.id.txtTitleDarood).setVisibility(View.GONE);

            return bitmap;
        }
        public void onClick(View view)
        {
            try
            {
                btnShare.setVisibility(View.INVISIBLE);
//                txtDaroodTitle.setVisibility(View.VISIBLE);
//                for(int i=0;i<100000;i++)
//                {}
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
                Log.v(Constants1.TAG,"444444444444444444444444444");
                btnShare.setVisibility(View.VISIBLE);
                stream.close();
                stream.flush();


//                StrictMode.ThreadPolicy policy =
//                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
//            StrictMode.setThreadPolicy(policy);

                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());


                 String AUTHORITY="com.indapp.islamicknowledge.fileprovider";

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
//                txtDaroodTitle.setVisibility(View.GONE);
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
