package com.indapp.fonts;
import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * @author Haris
 */
public class HelviticaBold extends TextView
{
	static Typeface mTypeFace = null;


	public HelviticaBold(Context context, AttributeSet attrs, int defStyleAttr)
	{
		// Return Super
		super(context, attrs, defStyleAttr);

		// Load and Set Font
		initTypeFace();
	}

	public HelviticaBold(Context context, AttributeSet attrs)
	{
		// Return Super
		super(context, attrs);

		// Load and Set Font
		initTypeFace();
	}

	public HelviticaBold(Context context)
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
			mTypeFace = Typeface.createFromAsset(getContext().getAssets(), "Helvetica.otf");
		}

		if(!isInEditMode())
		{
			setTypeface(mTypeFace, Typeface.BOLD);
		}
	}

}
