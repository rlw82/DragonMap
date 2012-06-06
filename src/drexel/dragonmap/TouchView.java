package drexel.dragonmap;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;


public class TouchView extends MapView
{

	public TouchView(Context context)
	{
		super(context);
	}
	
    public TouchView(Context context, AttributeSet attrs)
    {
    	super(context, attrs);
    }
	
	public void performClick(float x, float y)
	{
		return;
	}

}
