//Hey look something with my name on it -Russell
package drexel.dragonmap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
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

