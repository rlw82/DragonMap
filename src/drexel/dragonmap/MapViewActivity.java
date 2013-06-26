package drexel.dragonmap;


/*
 * This is the main Map Activity of the DragonMap app. It permits
 * the user to scroll around and zoom and stuff. It extends the
 * MapView class (which in turn extends ImageView).
 * 
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MapViewActivity extends Activity {

	
	private long currentPOI = -1L;
	
	/**
	 * @param savedInstanceState This can contain an intent with the String name of a
	 * POI to show on the screen. This intent would be passed by another Activity in 
	 * which the user clicks "Show on Map" from a search/the directory.
	 */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        
    	super.onCreate(savedInstanceState);
        
        //these are references, so we can repeatedly call this and it 
        //shouldn't chew up too much memory!
        Bitmap mapImage = ResourceManager.getMap();
        Bitmap pinImage = ResourceManager.getPin();
        
        resMap(mapImage);
        
        long POI_ID = getIntent().getLongExtra("POI", -1L);
        //POI_ID is null if no intent was passed (ie. just show the map)
        if (POI_ID != -1L)
        {
        	currentPOI = POI_ID;
        	//pressed "view on map" thang, adjust accordingly
        	POI myPOI = ResourceManager.getPOIs().getPOIByID(POI_ID);
            double targetX = myPOI.getX() + ( myPOI.getWidth() / 2 );
            double targetY = myPOI.getY() + ( myPOI.getHeight() / 2 );
            
            /* 
             * NOTE -- targetX and targetY have been cast to floats because 
             * drawBitmap (used in getBitmapOverlay) requires floats, and it's
             * a hassle to change them later.  Not sure if this will cause problems
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
    	Intent myIntent;
        switch (item.getItemId()) {
            case R.id.menuSearchButton:
            	//manually load the Search bar/activity
            	onSearchRequested();
                return true;
                
            case R.id.menuListingButton:
            	//start the BrowseActivity class
            	myIntent = new Intent(MapViewActivity.this, BrowseActivity.class);
            	MapViewActivity.this.startActivity(myIntent);
                return true;
                
            case R.id.mainMenuButton:
            	//start the BrowseActivity class
            	myIntent = new Intent(MapViewActivity.this, MenuActivity.class);
            	MapViewActivity.this.startActivity(myIntent);
                return true;
                
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
    // The hack to end all hacks. Don't even try to understand it. It's impossible.
    
    /* Motivation for this hack:
     * 	 We got a lot of OutOfMemory Errors because every time we opened a MapViewActivity
     *   with an intent, we left the old ones floating around in memory.
     * 
     * How it works:
     * 	 When the user reaches a DetailedViewActivity screen (either by pressing the POI
     *   on the map, searching or through the directory) they are presented with an option
     *   to view the POI on the map. When the user presses this button, all previous Activies
     *   (with the exception of the MenuActivity) are closed. This is desirable because it
     *   cleans up any activities that are floating around in memory. It is undesirable because
     *   it also deletes the DetailedViewActivity. This is bad, because pressing BACK from the
     *   map would now take the user to the main menu instead of to the last screen.
     *   
     * What this hack does:
     * 	 So the issue is that the user needs to go to the last-visited POI DetailedViewActivity
     *   if they press the back button on the map. That's exactly the process that is defined 
     *   below.
     *   
     * Room for improvement:
     * 	 Work out some way for the user to go back to the main menu. That's actually pretty
     *   important. TODO!
     */
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackPressed();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    
    @Override
    public void onBackPressed()
    {
    	if (currentPOI != -1L)
        {
            Intent myIntent = new Intent(MapViewActivity.this, DetailedViewActivity.class);
            myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            myIntent.putExtra("POI", currentPOI);
        	MapViewActivity.this.startActivity(myIntent);
        }
    	else
    	{
    		// will this work?
    		finish();
    	}
    }
    
    // end hackish, terrible code
    
    /*
     * Added 5/23/12
     * By Mark
     * Resolve map functionality
     * Drop pin functionality
     * BitmapOverlay functionality
     */
    // Drop a pinImg on the mapImg at point (left, top) and set it to the contentview
    public void resMap (Bitmap mapImg)
    {
    	// Initialize a MapView object from our map bitmap
    	MapView img = new MapView(this);
    	
    	// Make it the current view
        img.setImageBitmap(mapImg);
        img.setMaxZoom(4f);
        setContentView(img);
    }
    
    // Drop a pinImg on the mapImg at point (left, top) and set it to the contentview
    public void dropPin (Bitmap mapImg, Bitmap pinImg, float left, float top)
    {
    	// Convert the "left" and "top" values from percentages to points
        // and adjust the pin so the tip is on the given point
    	left = (left * mapImg.getWidth()) - (pinImg.getWidth()/2);
        top = (top * mapImg.getHeight()) - (pinImg.getHeight());
        
    	// Initialize a MapView and merge the map and pin
    	MapView img = new MapView(this);    	
    	Bitmap mapPinImage = getBitmapOverlay(mapImg,pinImg,left,top);
    	
    	// Update the current view with the new bitmap
        img.setImageBitmap(mapPinImage);
        img.setMaxZoom(4f);
        // TODO: Call the centerPoint function on the pin position.
        setContentView(img);
    }
    
    // Take two bitmaps and overlay bmp2 at (left,top) on bmp1
    public static Bitmap getBitmapOverlay(Bitmap bmp1, Bitmap bmp2, float left, float top)
    {
        // Create a NEW bitmap and make a canvas out of it
    	Bitmap bmOverlay = Bitmap.createBitmap(bmp1.getWidth(), bmp1.getHeight(),  bmp1.getConfig());
        Canvas canvas = new Canvas(bmOverlay);    
        // Draw bmp1 (map) onto the canvas
        canvas.drawBitmap(bmp1, 0, 0, null);
        // Draw bmp 2 (pin) onto the canvas at (left,top) and return
        canvas.drawBitmap(bmp2, left, top, null);
        return bmOverlay;
    }
}
