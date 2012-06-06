package drexel.dragonmap;
 
import android.app.AlertDialog;
import android.app.ExpandableListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
 
public class BrowseActivity extends ExpandableListActivity
{
 
	MyExpandableListAdapter myAdapter;
	

    public void onCreate(Bundle savedInstanceState)
    {
        try
        {
             super.onCreate(savedInstanceState);
             setContentView(R.layout.browse);
             
             myAdapter = new MyExpandableListAdapter();
             setListAdapter(myAdapter);
             getExpandableListView().setOnChildClickListener(this);
 
 
        }
        	catch(Exception e){}
    }
    
    @Override
	public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id)
    {
        final String selected = (String) myAdapter.getChild(groupPosition, childPosition);
        AlertDialog alertDialog = new AlertDialog.Builder(v.getContext()).create();
        alertDialog.setTitle(selected);
        alertDialog.setIcon(R.drawable.pin);
		alertDialog.setMessage("What would you like to do?");
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "View Info", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
   				Intent myIntent = new Intent(BrowseActivity.this, DetailedViewActivity.class);
   				//send over the POI name
                myIntent.putExtra("POI", selected);
                BrowseActivity.this.startActivity(myIntent);
			}
		});
        alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "View on Map", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
   				Intent myIntent = new Intent(BrowseActivity.this, MapViewActivity.class);
   				//send over the POI name
                myIntent.putExtra("POI", selected);
                BrowseActivity.this.startActivity(myIntent);
			}
		});
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Close", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				//do nothing
				//i wonder if i can make the onClickListener null
				//NullPointerException? who knows
			}
		});
        alertDialog.show();	
        
        return true;
    }
 
    /* This function is called on expansion of the group */
    public void  onGroupExpand  (int groupPosition) {
        try{
             System.out.println("Group exapanding Listener => groupPosition = " + groupPosition);
        }catch(Exception e){
            System.out.println(" groupPosition Errrr +++ " + e.getMessage());
        }
    }


	public class MyExpandableListAdapter extends BaseExpandableListAdapter {
	    // Sample data set.  children[i] contains the children (String[]) for groups[i].
	    private String[] groups = null;
	    private String[][] children = null;
	    
	    public MyExpandableListAdapter()
	    {
	    	POIList POIs = DBAccessor.getInstance().getData();
	    	groups = POIs.getCategories();
	    	children = POIs.getChildren();
	    }
	    
	   
	    public Object getChild(int groupPosition, int childPosition) {
	        return children[groupPosition][childPosition];
	    }
	
	    public long getChildId(int groupPosition, int childPosition) {
	        return childPosition;
	    }
	
	    public int getChildrenCount(int groupPosition) {
	        return children[groupPosition].length;
	    }
	
	    public TextView getGenericView() {
	        // Layout parameters for the ExpandableListView
	        AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
	                ViewGroup.LayoutParams.MATCH_PARENT, 64);
	
	        TextView textView = new TextView(BrowseActivity.this);
	        textView.setLayoutParams(lp);
	        // Center the text vertically
	        textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
	        // Set the text starting position
	        textView.setPadding(36, 0, 0, 0);
	        return textView;
	    }
	
	    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
	            View convertView, ViewGroup parent) {
	        TextView textView = getGenericView();
	        textView.setText(getChild(groupPosition, childPosition).toString());
	        return textView;
	    }
	
	    public Object getGroup(int groupPosition) {
	        return groups[groupPosition];
	    }
	
	    public int getGroupCount() {
	        return groups.length;
	    }
	
	    public long getGroupId(int groupPosition) {
	        return groupPosition;
	    }
	
	    public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
	            ViewGroup parent) {
	        TextView textView = getGenericView();
	        textView.setText(getGroup(groupPosition).toString());
	        return textView;
	    }
	
	    public boolean isChildSelectable(int groupPosition, int childPosition) {
	        return true;
	    }
	
	    public boolean hasStableIds() {
	        return true;
	    }
	
	}
}
