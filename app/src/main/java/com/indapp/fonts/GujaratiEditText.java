package com.indapp.fonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.TextView;

public class GujaratiEditText extends EditText
{
    static Typeface mTypeFace = null;


    public GujaratiEditText(Context context, AttributeSet attrs, int defStyleAttr)
    {
        // Return Super
        super(context, attrs, defStyleAttr);

        // Load and Set Font
        initTypeFace();
    }

    public GujaratiEditText(Context context, AttributeSet attrs)
    {
        // Return Super
        super(context, attrs);

        // Load and Set Font
        initTypeFace();
    }

    public GujaratiEditText(Context context)
    {
        // Return Super
        super(context);

        // Load and Set Font
        initTypeFace();
    }

    private void initTypeFace()
    {
        if(!isInEditMode() && mTypeFace == null)
        {
            mTypeFace = Typeface.createFromAsset(getContext().getAssets(), "fonts/BHUJ UNICODE.ttf");
        }

        if(!isInEditMode())
        {
            setTypeface(mTypeFace);
        }
    }

}

