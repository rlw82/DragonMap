
package drexel.dragonmap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class TouchImageViewActivity extends Activity
{
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       
        TouchView screen = new TouchView(this);
    	
    	// This is so wrong. Oh my God. Please forgive me Java gods for I know not what I do.
        screen.setImageBitmap(FloorPlanActivity.selected);
        screen.setMaxZoom(4f);
        setContentView(screen);
    }
	
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
            	myIntent = new Intent(TouchImageViewActivity.this, BrowseActivity.class);
            	TouchImageViewActivity.this.startActivity(myIntent);
                return true;
                
            case R.id.mainMenuButton:
            	//start the BrowseActivity class
            	myIntent = new Intent(TouchImageViewActivity.this, MenuActivity.class);
            	TouchImageViewActivity.this.startActivity(myIntent);
                return true;
                
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
}