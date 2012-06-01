package drexel.dragonmap;

import android.graphics.Bitmap;


public class DBAccessor
{
	public static DBAccessor instance = null;
	public static POIList data = null;
	public static Bitmap map = null;
	public static Bitmap pin = null;
	
	protected DBAccessor()
	{
		
	}
	
	public static DBAccessor getInstance()
	{
		if (instance == null)
			instance = new DBAccessor();
		return instance;
	}
	
	public void setData(POIList POIs)
	{
		data = POIs;
	}
	
	
	public void setMap(Bitmap m)
	{
		map = m;
	}
	
	public void setPin(Bitmap p)
	{
		pin = p;
	}
	
	public POIList getData()
	{
		return data;
	}
	
	public Bitmap getMap()
	{
		return map;
	}
	
	public Bitmap getPin()
	{
		return pin;
	}
}