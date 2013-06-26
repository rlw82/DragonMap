package drexel.dragonmap;


import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.HashMap;

import android.content.res.AssetManager;
import android.util.Log;


public class POIList
{

	//list_ contains the list of POIS
	//categoryMao_ is a map of categories onto POI names for the browse feature
	//categories_ is for efficiency's sake; use when propagating the Browse activity
	private ArrayList<POI> list_;
	private CategoryManager catMan; //SUPERHERO KITTENS! MEEEOOOW!
	private POI NOT_FOUND_POI; //For if a POI can't be found
	
	public POIList()
	{
		list_ = new ArrayList<POI>();
		catMan = null;
		//Show some apologetic remarks
		NOT_FOUND_POI = new POI();
		NOT_FOUND_POI.setName("Not found");
		NOT_FOUND_POI.setDescription("Oops! Something went wrong!");
		NOT_FOUND_POI.setID( -1L );
	}
	
	public POIList(String fname, AssetManager assets)
	{
		this();
		load(fname, assets);
		genFloorPlans(assets);
		catMan = new CategoryManager( list_ );
	}
	
	public ArrayList<POI> getList()
	{
		return list_;
	}
	
	
	public void load( String fname, AssetManager assets )
	{
		Scanner scanner = null;
		try
		{
			scanner = new Scanner( assets.open( fname ) );
		}
		catch ( IOException e )
		{
			//file couldn't be opened? I guess we handle this by not loading POIs.
			return;
		}
		
		while ( scanner.hasNextLine() )
		{
			  String line = scanner.nextLine();
			  POI newObj = new POI();
			  newObj.fromJSON( line );
			  list_.add( newObj );
		}
	}

	
	public void genFloorPlans(AssetManager assets)
	{
		String[] buildings = null;
		try
		{
			buildings = assets.list("floor_plans");
		}
		catch( IOException e )
		{
			return; //No floor plans? No worries! ( I suck at coding )
		}
		
		for (String building: buildings)
		{
			String dir = "floor_plans/" + building;
			Scanner scanner = null;
			try
			{
				scanner = new Scanner( assets.open( dir + "/ref.txt" ) );
			}
			catch ( IOException e )
			{
				continue;
			}
			
			//save first line - contains relevant POIs
			String relevantStr = scanner.nextLine().split( ":" )[1];
			FloorList myFloorList = new FloorList();
			while ( scanner.hasNextLine() )
			{
				String[] fileParts = scanner.nextLine().split( ":" );
				String fileSrc = fileParts[0];
				String fileDescription = fileParts[1];							
				Floor newFloor = new Floor( fileDescription, dir + "/" + fileSrc );
				myFloorList.addFloor( newFloor );
			}
			for ( String POI_ID: relevantStr.split( "," ) )
			{
				getPOIByID( Integer.parseInt( POI_ID) ).setFloorList( myFloorList );
				//TODO: What happens if two different FloorLists pertain to 1 POI????
			}
		}
	}
	
	public CategoryManager getCategoryManager()
	{
		return catMan;
	}

	
	public POI getPOIByID( long ID )
	{
		for ( POI poi: list_ )
		{
			if ( poi.getID() == ID )
				return poi;
		}
		return NOT_FOUND_POI;
	}
	
	public ArrayList<POI> find(String match)
	{
		ArrayList<POI> matched = new ArrayList<POI>();
		for (POI item: list_)
		{
			if (item.isMatch(match))
			{
				matched.add(item);
			}
		}
		return matched;
	}
	
	public POI getFirstContained(float x, float y)
	{
		for (POI p: list_)
		{
			if (p.isContained(x, y))
				return p;
		}
		return null;
	}
}


