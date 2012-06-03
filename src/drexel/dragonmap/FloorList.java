package drexel.dragonmap;

import java.util.ArrayList;
import java.util.Collections;

import android.util.Log;

/* An ArrayList of floors with some helper methods*/

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
	
	public ArrayList<Floor> getFloors()
	{
		return floors;
	}
	
	public int size()
	{
		return floors.size();
	}
	
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
