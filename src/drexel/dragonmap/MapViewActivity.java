package drexel.dragonmap;

/*
 * This is the main Map Activity of the DragonMap app. It permits
 * the user to scroll around and zoom and stuff. It extends the
 * MapView class (which in turn extends ImageView).
 * 
 */

import drexel.dragonmap.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MapViewActivity extends Activity {

	
	/**
	 * @param savedInstanceState This can contain an intent with the String name of a
	 * POI to show on the screen. This intent would be passed by another Activity in 
	 * which the user clicks "Show on Map" from a search/the directory.
	 */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       
        //load up our custom MapView
        MapView img = new MapView(this);
        Bitmap mapImage = BitmapFactory.decodeResource(getResources(), R.drawable.campus_map);
        img.setImageBitmap(mapImage);
        img.setMaxZoom(4f);
        
        
        
        setContentView(img);
        
        /*this is called a singleton. It only works if everything is operating
        in the same thread, I think. Android guarantees this, so no worries.
        It's cool because it lets us set the data here and access it statically from
        any other class/activity in the project! This is useful because now we only
        have to parse the out.txt file once, instead of every time the user searches.
        
        The syntax is DBAccessor.getInstance().getData() to get the POIList
        and DBAccessor.getInstance().setData(data) to set the POIList
        */
        
        DBAccessor dba = DBAccessor.getInstance();
        //create our database
        //NOTE: This initialization should be done in the MAIN activity. Currently
        //this is MapViewActivity, but when Russel gets the mainmenu working, we
        //should migrate it over there!
        dba.setData(new POIList("out.txt", getAssets()));
        
        String POIName = getIntent().getStringExtra("POI");
        //POIName is null if no intent was passed (ie. just show the map)
        if (POIName != null)
        {
        	//pressed "view on map" thang, adjust accordingly
        	POI myPOI = DBAccessor.getInstance().getData().getPOIByName(POIName);
            double targetX = myPOI.getX() + ( myPOI.getWidth() / 2 );
            double targetY = myPOI.getY() + ( myPOI.getHeight() / 2 );
            //doSomething(myPOI, targetX, targetY)
            
        }
    }
    
    /*
     * Added 5/9/12
     * By Drew
     * Part of the search functionality.
     * This pops open the menu when the user presses the physical menu button. 
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu); //bring up the menu
        return true; //return true means process the click
    }
    
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menuSearchButton:
            	//manually load the Search bar/activity
            	onSearchRequested();
                return true;
                
            case R.id.menuListingButton:
            	//start the BrowseActivity class
            	Intent myIntent = new Intent(MapViewActivity.this, BrowseActivity.class);
            	MapViewActivity.this.startActivity(myIntent);
                return true;
                
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}