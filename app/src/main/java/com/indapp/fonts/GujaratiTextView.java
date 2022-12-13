package com.indapp.fonts;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by admin on 7/26/2016.
 */
public class GujaratiTextView extends TextView
{
    static Typeface mTypeFace = null;


    public GujaratiTextView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        // Return Super
        super(context, attrs, defStyleAttr);

        // Load and Set Font
        initTypeFace();
    }

    public GujaratiTextView(Context context, AttributeSet attrs)
    {
        // Return Super
        super(context, attrs);

        // Load and Set Font
        initTypeFace();
    }

    public GujaratiTextView(Context context)
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
