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
import android.graphics.Canvas;
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
       
        // Initialize our map and pin images and decode them to bitmaps.  Once they are
        // bitmapped, they can be passed to either "resMap" or "dropPin"
        Bitmap mapImage = BitmapFactory.decodeResource(getResources(), R.drawable.campus_map_75);
        Bitmap pinImage = BitmapFactory.decodeResource(getResources(), R.drawable.map_pin);
        
        
        resMap(mapImage);
        
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
            
            /* 
             * NOTE -- targetX and targetY have been cast to floats because 
             * drawBitmap (used in getBitmapOverlay) requires floats, and it's
             * a bitch to change them later.  Not sure if this will cause problems
             * but I don't think it will.
             */
            dropPin(mapImage,pinImage,(float)targetX,(float)targetY);
            
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
    
    
    /*
     * Added 5/23/12
     * By Mark
     * Resolve map functionality
     * Drop pin functionality
     * BitmapOverlay functionality
     */
    // Drop a pinImg on the mapImg at point (left, top) and set it to the contentview
    public void resMap (Bitmap mapImg) {
    	// Initialize a MapView object from our map bitmap
    	MapView img = new MapView(this);
    	
    	// Make it the current view
        img.setImageBitmap(mapImg);
        img.setMaxZoom(4f);
        setContentView(img);
    }
    
    // Drop a pinImg on the mapImg at point (left, top) and set it to the contentview
    public void dropPin (Bitmap mapImg, Bitmap pinImg, float left, float top) {
    	// Initialize a MapView and merge the map and pin
    	MapView img = new MapView(this);    	
    	Bitmap mapPinImage = getBitmapOverlay(mapImg,pinImg,left,top);
    	
    	// Update the current view with the new bitmap
        img.setImageBitmap(mapPinImage);
        img.setMaxZoom(4f);
        setContentView(img);
    }
    
    // Take two bitmaps and overlay bmp2 at (left,top) on bmp1
    public static Bitmap getBitmapOverlay(Bitmap bmp1, Bitmap bmp2, float left, float top) {
        // Create a NEW bitmap and make a canvas out of it
    	Bitmap bmOverlay = Bitmap.createBitmap(bmp1.getWidth(), bmp1.getHeight(),  bmp1.getConfig());
        Canvas canvas = new Canvas(bmOverlay);    
        // Draw bmp1 (map) onto the canvas
        canvas.drawBitmap(bmp1, 0, 0, null);
        // Convert the "left" and "top" values from percentages to points
        // and adjust the pin so the tip is on the given point
        left = (left * bmp1.getWidth()) - (bmp2.getWidth()/2);
        top = (top * bmp1.getHeight()) - (bmp2.getHeight());
        // Draw bmp 2 (pin) onto the canvas at (left,top) and return
        canvas.drawBitmap(bmp2, left, top, null);
        return bmOverlay;
    }
}