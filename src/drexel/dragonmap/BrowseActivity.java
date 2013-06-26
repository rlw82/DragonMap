
/*
 * 
 * BrowseActivity.java
 * 
 * Purpose: This Activity is displayed when the user wants to trawl
 * through all of the loaded POIs by category until they find the
 * one that they're looking for.
 * 
 * NOTE: Updated to pass POI ID instead of POI Name!
 * 
 */

package drexel.dragonmap;
 
import android.app.AlertDialog;
import android.app.ExpandableListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
 
public class BrowseActivity extends ExpandableListActivity
{
 
	//Make it available to the onChildClick method below
	MyExpandableListAdapter myAdapter;
	

    public void onCreate( Bundle savedInstanceState )
    {

         super.onCreate( savedInstanceState );
         setContentView( R.layout.browse );
         
         //Manage all of our POIs
         myAdapter = new MyExpandableListAdapter();
         setListAdapter( myAdapter );
         //Click callbacks
         getExpandableListView().setOnChildClickListener( this );
    }
    
    @Override
	public boolean onChildClick( ExpandableListView parent, View v, int groupPosition, int childPosition, long id )
    {
    	//needs to be final so it can be referenced inside of the onClick methods below
        final long POI_ID = myAdapter.getChildId( groupPosition, childPosition );
        String POI_Name = myAdapter.getChild( groupPosition, childPosition );
        
        //Alert Dialog code
        AlertDialog alertDialog = new AlertDialog.Builder( v.getContext() ).create();
        alertDialog.setTitle( POI_Name );
        alertDialog.setIcon( R.drawable.pin );
		alertDialog.setMessage( "What would you like to do?" );
		
		//-----Alert buttons-----
		
		//View Info (LEFT) - Will open up the DetailedViewActivity for this POI
        alertDialog.setButton( DialogInterface.BUTTON_NEGATIVE, "View Info", new DialogInterface.OnClickListener() {
			public void onClick( DialogInterface dialog, int which ) {
   				Intent myIntent = new Intent( BrowseActivity.this, DetailedViewActivity.class );
   				//send over the POI name
                myIntent.putExtra( "POI", POI_ID );
                BrowseActivity.this.startActivity( myIntent );
			}
		});
        
        //View Map (CENTER) - Will open up the MapViewActivity centered on this POI
        alertDialog.setButton( DialogInterface.BUTTON_NEUTRAL, "View Map", new DialogInterface.OnClickListener() {
			public void onClick( DialogInterface dialog, int which ) {
   				Intent myIntent = new Intent(BrowseActivity.this, MapViewActivity.class);
   				//send over the POI name5
                myIntent.putExtra("POI", POI_ID);
                BrowseActivity.this.startActivity(myIntent);
			}
		});
        
        //Close (RIGHT)
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Close", new DialogInterface.OnClickListener() {
			public void onClick( DialogInterface dialog, int which ) {
				return; //just close, don't do anything
			}
		});
        alertDialog.show();	
        
        return true;
    }
 

    //Nested Classes!

    /*
     * Used by the BrowseActivity Class to manage the parent/children combinations
     * displayed in the ExpandableListView. 
     */
	public class MyExpandableListAdapter extends BaseExpandableListAdapter
	{
		//na-na-na-na-na-na-na-na-na-na CATMAN!
		CategoryManager catMan;
	    
	    public MyExpandableListAdapter()
	    {
	    	// load the category manager so methods below can access it
	    	catMan = ResourceManager.getPOIs().getCategoryManager();
	    }
	    
	    
	    /* A quick note on this groupPosition/childPosition business:
	     * 
		 * groupPosition and childPosition are indexes of where the given POI
		 * Name and Category information can be found in the CategoryManager. 
		 * The actual numbers given by groupPosition and childPosition are dictated
		 * by alphabetical sorting. All of this is managed by the CategoryManager;
		 * all the methods below need to do is give the ExpandableList's ListAdapater
		 * some methods to plug into.
	     */
	    
	    //Return the given child's name
	    public String getChild( int groupPosition, int childPosition )
	    {
	        return catMan.getChild( groupPosition, childPosition );
	    }
	
	    //Return the given child's ID
	    public long getChildId(int groupPosition, int childPosition)
	    {
	        return catMan.getChildId( groupPosition, childPosition);
	    }
	
	    //return the number of children in a group
	    public int getChildrenCount( int groupPosition )
	    {
	        return catMan.childSize( groupPosition );
	    }
	
	    //I have no idea what this does. I think it's a container for something?
	    public TextView getGenericView()
	    {
            // Layout parameters for the ExpandableListView
            AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
                    ViewGroup.LayoutParams.FILL_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
 
            TextView textView = new TextView( BrowseActivity.this );
            textView.setLayoutParams( lp );
            
            textView.setPadding( dpToPx( 50 ), dpToPx( 20 ), 0, dpToPx( 20 ) );
            return textView;
        }
	    
	    //helper method for getGenericView; turns dp units to pixels.
	    public int dpToPx( float n )
	    {
	    	Resources r = getResources();
	    	float px = TypedValue.applyDimension( TypedValue.COMPLEX_UNIT_DIP, n, r.getDisplayMetrics() );
	    	return (int) ( px + .5 );
	    }

	    //layout code for children entries
	    public View getChildView( int groupPosition, int childPosition, boolean isLastChild,
	            View convertView, ViewGroup parent )
	    {
	        TextView textView = getGenericView();
	        textView.setText( getChild( groupPosition, childPosition ).toString() );
	        return textView;
	    }
	
	    //get the name of a given category
	    public Object getGroup( int groupPosition )
	    {
	        return catMan.getCategory( groupPosition );
	    }
	
	    //get the number of categories
	    public int getGroupCount()
	    {
	        return catMan.groupSize();
	    }
	
	    //always return -1 for the group ids. They don't have one presently
	    public long getGroupId( int groupPosition )
	    {
	        return -1L;
	    }
	
	    //layout for categories
	    public View getGroupView( int groupPosition, boolean isExpanded,
	    		                  View convertView, ViewGroup parent )
	    {
	        TextView textView = getGenericView();
	        textView.setText( getGroup( groupPosition ).toString() );
	        return textView;
	    }
	
	    //all children can be selected
	    public boolean isChildSelectable( int groupPosition, int childPosition )
	    {
	        return true;
	    }
	    
	    //no groups can be selected
	    public boolean isGroupSelectable( int groupPosition )
	    {
	    	return false;
	    }
	
	    //IDs are constant throughout the life of the program
	    public boolean hasStableIds()
	    {
	        return true;
	    }
	
	}
}
