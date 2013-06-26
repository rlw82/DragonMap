package drexel.dragonmap;


import drexel.dragonmap.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DetailedViewActivity extends Activity
{
	
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.detailedview );
        //needs to be final for onClick below. -1 denotes default
        final long POI_ID = getIntent().getLongExtra( "POI", -1L ); 
        
        POI myPOI = ResourceManager.getPOIs().getPOIByID( POI_ID );
        
        //------Set data for our POI------
        ( (TextView)findViewById( R.id.POITitle ) ).setText( myPOI.getName() );
        ( (TextView)findViewById( R.id.POIAddress ) ).setText( myPOI.getAddress() );
        ( (TextView)findViewById( R.id.POIDescription ) ).setText( myPOI.getDescription() );
        ( (TextView)findViewById( R.id.POIKeywords ) ).setText( myPOI.getKeywordsAsString() );
        
        
        //generate an onClick function on the fly
        Button viewOnMap = (Button)findViewById( R.id.viewOnMapButton );
        viewOnMap.setOnClickListener( new View.OnClickListener(){
            public void onClick( View v )
            {
            	Intent myIntent = new Intent( DetailedViewActivity.this, MapViewActivity.class );
            	myIntent.putExtra( "POI", POI_ID );
            	//delete past Activities from queue - consider revising this
            	myIntent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
                DetailedViewActivity.this.startActivity( myIntent );
            }
        });
        
        
        Button viewFloors = (Button)findViewById( R.id.viewFloorsButton );
        
        if ( myPOI.getFloorList().size() == 0 )
        {
        	viewFloors.setVisibility( View.GONE ); //if there are no floors, don't let them press
        }
        else
        {
	        viewFloors.setOnClickListener(new View.OnClickListener() {
	            public void onClick( View v )
	            {
	            	Intent myIntent = new Intent( DetailedViewActivity.this, FloorPlanActivity.class );
	            	myIntent.putExtra( "POI", POI_ID );
	                DetailedViewActivity.this.startActivity( myIntent );
	            }
	        });
        }
    } //onCreate
    
    
    //Called when the physical menu button is pressed
    //  brings up the menu list at the bottom (Search, Browse, Main Menu)
    @Override
    public boolean onCreateOptionsMenu( Menu menu )
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate( R.menu.mainmenu, menu ); //bring up the menu
        return true; //return true means process the click
    }
    
    //Called when a menu button at the bottom of the screen is pressed (search, browse, menu)
    @Override
    public boolean onOptionsItemSelected( MenuItem item )
    {
        // Handle item selection
    	Intent myIntent;
        switch (item.getItemId()) {
            case R.id.menuSearchButton:
            	//manually load the Search bar/activity
            	onSearchRequested();
                return true;
                
            case R.id.menuListingButton:
            	//start the BrowseActivity class
            	myIntent = new Intent(DetailedViewActivity.this, BrowseActivity.class);
            	DetailedViewActivity.this.startActivity(myIntent);
                return true;
                
            case R.id.mainMenuButton:
            	//start the BrowseActivity class
            	myIntent = new Intent(DetailedViewActivity.this, MenuActivity.class);
            	DetailedViewActivity.this.startActivity(myIntent);
                return true;
                
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}