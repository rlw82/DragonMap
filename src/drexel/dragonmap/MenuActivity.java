//Hey look something with my name on it -Russell #LOL #PUNK #GETAREALJOB
package drexel.dragonmap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

public class MenuActivity extends Activity
{

	
	/** Called when the activity is first created.
	 *  Creates all the buttons and assigns them listeners
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	
		setContentView(R.layout.menu);
		Button about = (Button) findViewById(R.id.about);
		Button search = (Button) findViewById(R.id.search);
		Button directory = (Button) findViewById(R.id.directory);
		Button view_map = (Button) findViewById(R.id.view_map);
		about.setOnClickListener(aboutButtonListener);
		search.setOnClickListener(searchButtonListener);
		directory.setOnClickListener(directoryButtonListener);
		view_map.setOnClickListener(mapButtonListener);
		
		initialize();
	}
	
	/* This method will only be called once when the program is created. It may eventually
	 * be migrated to a splash screen if that's the route we end up taking. It loads the 
	 * points of interest from JSON and the bitmaps and puts them into the DB singleton.
	 * This way, the maps and POIs will only be initialized once and then can be 
	 * universally accessed. The syntax is DBAccessor.getInstance().get[Data|Map|Pin]()
	 */
	public void initialize()
	{
		// Initialize our map and pin images and decode them to bitmaps.  Once they are
        // bitmapped, they can be passed to either "resMap" or "dropPin"
        Bitmap mapImage = BitmapFactory.decodeResource(getResources(), R.drawable.campus_map_75);
        Bitmap pinImage = BitmapFactory.decodeResource(getResources(), R.drawable.map_pin);
        
       
        
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
        dba.setMap(mapImage);
        dba.setPin(pinImage);
	}

	/**  Might have to be changed to a different type if multiple lines isn't supported
	 *   but will display a short message with the option to close out when selected on the menu
	 */
	private OnClickListener aboutButtonListener = new OnClickListener()
	{
		public void onClick(View v){
			AlertDialog alertDialog = new AlertDialog.Builder(MenuActivity.this).create();
		    alertDialog.setTitle("About Us");  
		    alertDialog.setMessage("Welcome to the DragonMap App!");  
		    alertDialog.setButton("Close", new DialogInterface.OnClickListener() {  
		      public void onClick(DialogInterface dialog, int which) {  
		        return;  
		      } });  
			alertDialog.show();
	}
	};
	
	/**  Default search is the same as in MapViewActivity, so the search bar will act the same when brought up
	 *   could also maybe have search button used?
	 */
	private OnClickListener searchButtonListener = new OnClickListener() {
	
		public void onClick(View v) {
			onSearchRequested();
		}
	};
	
	/**  Changes activities to the directory listing activity */
	private OnClickListener directoryButtonListener = new OnClickListener() {
	
		public void onClick(View v) {
        	Intent myIntent = new Intent(MenuActivity.this, BrowseActivity.class);
        	MenuActivity.this.startActivity(myIntent);
		}
	};
	
	/** Changes activities to the MapViewActivity, may lag on emulator but on the actual phone it runs smoothly	 */
	private OnClickListener mapButtonListener = new OnClickListener() {
		public void onClick(View v) {
			Intent myIntent = new Intent(MenuActivity.this, MapViewActivity.class);
			MenuActivity.this.startActivity(myIntent);
		}
	};
}

