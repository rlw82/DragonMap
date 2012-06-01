package drexel.dragonmap;



import drexel.dragonmap.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DetailedViewActivity extends Activity {
    /** Called when the activity is first created. */
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailedview);
        final String POIName = getIntent().getStringExtra("POI"); //final for onClick below
        
        POI myPOI = DBAccessor.getInstance().getData().getPOIByName(POIName); //whew
        
        TextView title = (TextView) findViewById(R.id.POITitle);
        title.setText(POIName);
        
        TextView address = (TextView) findViewById(R.id.POIAddress);
        address.setText(myPOI.getAddress());
        
        TextView description = (TextView) findViewById(R.id.POIDescription);
        description.setText(myPOI.getDescription());
        
        TextView keywords = (TextView) findViewById(R.id.POIKeywords);
        keywords.setText(myPOI.getKeywordsAsString());
        
        Button viewOnMap = (Button) findViewById(R.id.viewOnMapButton);
        
        //final so they can be accessed inside of onClick below... java smh
        viewOnMap.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Intent myIntent = new Intent(DetailedViewActivity.this, MapViewActivity.class);
            	myIntent.putExtra("POI", POIName);
                DetailedViewActivity.this.startActivity(myIntent);
            }
        });
        
        Button viewFloors = (Button) findViewById(R.id.viewFloorsButton);
        viewFloors.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	Intent myIntent = new Intent(DetailedViewActivity.this, FloorPlanActivity.class);
            	myIntent.putExtra("POI", POIName);
                DetailedViewActivity.this.startActivity(myIntent);
            }
        });
    }
}