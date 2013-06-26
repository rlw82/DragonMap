//Hey look something with my name on it -Russell #LOL #PUNK #GETAREALJOB
package drexel.dragonmap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MenuActivity extends Activity
{
	

	/* A quick note about Android Activity lifecycles:
	 * 	The onCreate method is only called one when the activity is actually
	 * 	created by the Android OS. Since this is our main activity, and we have
	 * 	no explicit intents that start the MenuActivity, initializations in the
	 * 	MenuActivity's onCreate are only run once when the app (and subsequently
	 * 	this the MenuActivity) is started. 
	 * 
	 * 	Hitting the back-button to return to the MenuActivity does not call the
	 *  onCreate method.
	 */


	/** Called when the activity is first created.
	 *  Creates all the buttons and assigns them listeners
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		initialize();
		
		setContentView(R.layout.menu);
		Button about = (Button) findViewById(R.id.about);
		Button search = (Button) findViewById(R.id.search);
		Button directory = (Button) findViewById(R.id.directory);
		Button view_map = (Button) findViewById(R.id.view_map);
		about.setOnClickListener(aboutButtonListener);
		search.setOnClickListener(searchButtonListener);
		directory.setOnClickListener(directoryButtonListener);
		view_map.setOnClickListener(mapButtonListener);
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
        
		Bitmap mapImage = BitmapFactory.decodeResource(getResources(), R.drawable.campus_small);
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
        POIList POIs = new POIList("db.dat", getAssets());
        
        dba.setData(POIs);

        dba.setMap(mapImage);
        dba.setPin(pinImage);
        
        
	}
	
	
	
	public static int calculateInSampleSize( BitmapFactory.Options options, int reqWidth, int reqHeight )
	{
	    // Raw height and width of image
	    final int height = options.outHeight;
	    final int width = options.outWidth;
	    int inSampleSize = 1;
	
	    if (height > reqHeight || width > reqWidth)
	    {
	        if (width > height)
	        {
	            inSampleSize = Math.round((float)height / (float)reqHeight);
	        }
	        else
	        {
	            inSampleSize = Math.round((float)width / (float)reqWidth);
	        }
	    }
	    return inSampleSize;
	}
	
	
	public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth, int reqHeight)
	{

	    // First decode with inJustDecodeBounds=true to check dimensions
	    final BitmapFactory.Options options = new BitmapFactory.Options();
	    options.inJustDecodeBounds = true;
	    BitmapFactory.decodeResource(res, resId, options);

	    // Calculate inSampleSize
	    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

	    // Decode bitmap with inSampleSize set
	    options.inJustDecodeBounds = false;
	    return BitmapFactory.decodeResource(res, resId, options);
	}
	
	

	/**  Might have to be changed to a different type if multiple lines isn't supported
	 *   but will display a short message with the option to close out when selected on the menu
	 */
	private OnClickListener aboutButtonListener = new OnClickListener()
	{
		public void onClick(View v){
			AlertDialog alertDialog = HyperlinkAlertDialog.create(MenuActivity.this);
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

