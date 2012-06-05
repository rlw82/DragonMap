package drexel.dragonmap;

import android.graphics.Bitmap;

/*
 * represents a single floor in a single building
 * 
 * each POI will have a FloorList field, which will be a list of Floors
 * 
 * -Floor number (starts at 1 for first floor)
 * -Image source
 * 
 * we're not going to store an image because allocating all of those pictures
 * would take up a lot of memory. We'll do it as necessary
 */

public class Floor
{
	private int floorNum;
	private String imageSrc;
	
	public Floor(int floorNum, String imageSrc)
	{
		this.floorNum = floorNum;
		this.imageSrc = imageSrc;
	}
	
	
	public Floor()
	{
		this.floorNum = 0;
		this.imageSrc = "";
	}
	
	public int getFloorNum()
	{
		return floorNum;
	}
	
	public void setFloorNum(int n)
	{
		floorNum = n;
	}
	
	public String getImageSrc()
	{
		return imageSrc;
	}
	
	public void setImageSrc(String s)
	{
		imageSrc = s;
	}
	
	public Bitmap getBitmap()
	{
		return null;
	}



}
