package com.indapp.fragements;

import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.indapp.qurantafseer_maulanaabdulkareem.R;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view1 = inflater.inflate(R.layout.fragement_tutorial, container, false);
        int GROUP_NO = getArguments().getInt(PAGENO_ID);
        ImageView imgTutorial=(ImageView) view1.findViewById(R.id.imgTutorial);

        if(Constants1.LANGUAGE.equalsIgnoreCase(Constants1.URDU))
        imgTutorial.setImageResource(Constants1.TUTORIAL_IMAGES[GROUP_NO]);
        else imgTutorial.setImageResource(Constants1.TUTORIAL_IMAGES_GUJ[GROUP_NO]);

        return view1;
    }

}