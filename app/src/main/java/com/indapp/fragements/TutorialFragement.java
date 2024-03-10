package com.indapp.fragements;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.indapp.islamicknowledge.R;
import com.indapp.utils.Constants1;

public class TutorialFragement extends Fragment {


    public static final String PAGENO_ID = "PAGENO_ID";
    public static final String EXTRA_TYPE = "TYPE";

    public static final TutorialFragement newInstance(int PAGENO) {
        TutorialFragement f = new TutorialFragement();
        Bundle bdl = new Bundle(2);
        bdl.putInt(PAGENO_ID, PAGENO);
        f.setArguments(bdl);
        return f;
    }
    RelativeLayout last_layout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view1 = inflater.inflate(R.layout.fragement_tutorial, container, false);
        int GROUP_NO = getArguments().getInt(PAGENO_ID);
        last_layout = (RelativeLayout) view1.findViewById(R.id.last_layout);
        ImageView imgTutorial = (ImageView) view1.findViewById(R.id.imgTutorial);

        if(GROUP_NO<Constants1.TUTORIAL_IMAGES_GUJ.length) {
            if (Constants1.LANGUAGE.equalsIgnoreCase(Constants1.URDU))
                imgTutorial.setImageResource(Constants1.TUTORIAL_IMAGES[GROUP_NO]);
            else if(Constants1.LANGUAGE.equalsIgnoreCase(Constants1.GUJARATI) )
            imgTutorial.setImageResource(Constants1.TUTORIAL_IMAGES_GUJ[GROUP_NO]);
            else if(Constants1.LANGUAGE.equalsIgnoreCase(Constants1.ENGLISH) )
                imgTutorial.setImageResource(Constants1.TUTORIAL_IMAGES_ENG[GROUP_NO]);
            last_layout.setVisibility(View.GONE);
        }
        else {
            imgTutorial.setVisibility(View.GONE);
            last_layout.setVisibility(View.VISIBLE);

            TextView txtTutorialEndText=(TextView) view1.findViewById(R.id.txtTutorialEndText);
            TextView txtBottomText=(TextView) view1.findViewById(R.id.txtBottomText);
            Typeface face;

            if (Constants1.LANGUAGE.equalsIgnoreCase(Constants1.GUJARATI))
            {
                txtTutorialEndText.setText("કુર્આન મજીદને વુઝૂ સાથે પઢવું જોઇયે");
                txtBottomText.setText("શરૂઆત કરવા માટે અહીં ક્લિક કરો");
                face = Typeface.createFromAsset(getActivity().getAssets(),
                        "fonts/BHUJ UNICODE.ttf");
            }
            else if (Constants1.LANGUAGE.equalsIgnoreCase(Constants1.ENGLISH))
            {
                txtTutorialEndText.setText("The Quran should be recited while being in a state of \"Wudhu\" (Ablution).");
                txtBottomText.setText("Click here to start.");
                face = Typeface.createFromAsset(getActivity().getAssets(),
                        "fonts/BLKCHCRY.ttf");
            }
            else //if (Constants1.LANGUAGE.equalsIgnoreCase(Constants1.URDU))
            {
                txtTutorialEndText.setText("قرآن مجید کو وضو کے ساتھ پڑھنا چاہیے");
                txtBottomText.setText("شروع کرنے کے لیے یہاں کلک کریں");
                face= Typeface.createFromAsset(getActivity().getAssets(),
                        "fonts/jameelnoorinastaleeq.ttf");
            }
            txtTutorialEndText.setTypeface(face);
            txtBottomText.setTypeface(face);
            txtBottomText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getActivity().finish();
                }
            });

        }

        return view1;
    }

}