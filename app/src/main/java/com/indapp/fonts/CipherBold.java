package com.indapp.fonts;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * @author Haris
 */
public class CipherBold extends TextView
{
	static Typeface mTypeFace = null;


	public CipherBold(Context context, AttributeSet attrs, int defStyleAttr)
	{
		// Return Super
		super(context, attrs, defStyleAttr);

		// Load and Set Font
		initTypeFace();
	}

	public CipherBold(Context context, AttributeSet attrs)
	{
		// Return Super
		super(context, attrs);

		// Load and Set Font
		initTypeFace();
	}

	public CipherBold(Context context)
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
			mTypeFace = Typeface.createFromAsset(getContext().getAssets(), "Poppins-SemiBold.ttf");
		}

		if(!isInEditMode())
		{
			setTypeface(mTypeFace);//, Typeface.BOLD);
		}
	}

}
