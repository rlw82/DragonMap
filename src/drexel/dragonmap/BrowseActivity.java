package drexel.dragonmap;
 
import android.app.AlertDialog;
import android.app.ExpandableListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
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

         super.onCreate(savedInstanceState);
         setContentView(R.layout.browse);
         
         myAdapter = new MyExpandableListAdapter();
         setListAdapter(myAdapter);
         getExpandableListView().setOnChildClickListener(this);
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
        alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "View Map", new DialogInterface.OnClickListener() {
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
                    ViewGroup.LayoutParams.FILL_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
 
            TextView textView = new TextView(BrowseActivity.this);
            textView.setLayoutParams(lp);
            
            textView.setPadding(dpToPx(50), dpToPx(20), 0, dpToPx(20));
            return textView;
        }
	    
	    public int dpToPx(float n)
	    {
	    	Resources r = getResources();
	    	float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, n, r.getDisplayMetrics());
	    	return (int) ( px + .5 );
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
