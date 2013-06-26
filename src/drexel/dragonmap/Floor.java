package drexel.dragonmap;

import android.graphics.Bitmap;

/*
 * represents a single floor in a single building
 * 
 * each POI will have a FloorList field, which will be a list of Floors
 * 
 * -Floor label: can be a floor number (1, 4, etc) or text (basement, ground, etc)
 * -Image source
 * 
 * we're not going to store an image because allocating all of those pictures
 * would take up a lot of memory. We'll do it as necessary
 */

public class Floor
{
	//The label to display along with this floor
	private String floorLabel;
	//The path to this image
	private String imageSrc;
	
	public Floor(String floorLabel, String imageSrc)
	{
		this.floorLabel = floorLabel;
		this.imageSrc = imageSrc;
	}

	public Floor()
	{
		this.floorLabel = "";
		this.imageSrc = "";
	}
	
	public String getFloorLabel()
	{
		return floorLabel;
	}
	
	public void setFloorLabel(String s)
	{
		floorLabel = s;
	}
	
	public String getImageSrc()
	{
		return imageSrc;
	}
	
	public void setImageSrc(String s)
	{
		imageSrc = s;
	}
	
	//TODO: Figure out what I put this here for...
	public Bitmap getBitmap()
	{
		return null;
	}
}
