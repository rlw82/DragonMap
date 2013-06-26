package drexel.dragonmap;

import java.util.ArrayList;
import java.util.Collections;

/* This is an ArrayList of floors with some helper methods
 * 
 * Every POI has an attached FloorList which contains
 * a list of floors to associate with the POI.
 */

public class FloorList
{
	private ArrayList<Floor> floors;
	
	public FloorList()
	{
		floors = new ArrayList<Floor>();
	}
	
	public void addFloor(Floor f)
	{
		floors.add(f);
	}
	
	public Floor getFloor(int i)
	{
		return floors.get(i);
	}
	
	public int size()
	{
		return floors.size();
	}
	
	//ImageAdapters require an array of image SRCs, so this facilitates that process
	public String[] getImageSrcs()
	{
		ArrayList<String> imageSrcs = new ArrayList<String>();
		for (Floor f: floors)
		{
			imageSrcs.add(f.getImageSrc());
		}
		Collections.sort(imageSrcs);
		
		String[] arr = imageSrcs.toArray(new String[imageSrcs.size()]);
		return arr;
	}

}
