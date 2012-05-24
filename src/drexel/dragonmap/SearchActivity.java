package drexel.dragonmap;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.SearchManager;
import android.content.DialogInterface;
import android.content.Intent;
//import android.content.pm.ActivityInfo; //forces landscape/portrait
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import drexel.dragonmap.R;

import drexel.dragonmap.POI;
import drexel.dragonmap.POIList;

public class SearchActivity extends ListActivity
{
	
	private ArrayList<POI> matched_ = new ArrayList<POI>();
	
	@Override
    public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);       
		setContentView(R.layout.search);
		//setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //can be used to force portrait. undesirable? 
		
		Intent intent = getIntent();
	    if (Intent.ACTION_SEARCH.equals(intent.getAction()))
	    {
	      String query = intent.getStringExtra(SearchManager.QUERY);
	      ArrayAdapter<POI> searchAdapter = search(query);
	      
	      setListAdapter(searchAdapter);
	    }
	}
	
	public ArrayAdapter<POI> search(String query)
	{
		POIList POIs = DBAccessor.getInstance().getData();
		matched_ = POIs.find(query);
		ArrayAdapter<POI> adapter = new ArrayAdapter<POI>(this, android.R.layout.simple_list_item_1, matched_);
        return adapter;
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id)
	{
		final String name = matched_.get(position).getName();
		AlertDialog alertDialog = new AlertDialog.Builder(SearchActivity.this).create();
		alertDialog.setTitle(name);
		alertDialog.setIcon(R.drawable.icon);
		//this is hilarious and subject to change
		alertDialog.setMessage("Whatchu wanna do?");
		alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "View Info", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which)
			{
				Intent myIntent = new Intent(SearchActivity.this, DetailedViewActivity.class);
				//send over the POI name
				myIntent.putExtra("POI", name);
		        SearchActivity.this.startActivity(myIntent);
			}
		});
		alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "Show on Map", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which)
			{
				Intent myIntent = new Intent(SearchActivity.this, MapViewActivity.class);
				//send over the POI name
				myIntent.putExtra("POI", name);
				SearchActivity.this.startActivity(myIntent);
			}
		});
		alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Close", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which)
			{
			//do nothing
			//i wonder if i can make the onClickListener null
			//NullPointerException? who knows
			}
		});
	    alertDialog.show();	
	}
	
	
}
