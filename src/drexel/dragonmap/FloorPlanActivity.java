package drexel.dragonmap;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class FloorPlanActivity extends Activity
{
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.floorplan);

	    Gallery gallery = (Gallery) findViewById(R.id.gallery);
	    gallery.setAdapter(new ImageAdapter(this));
	    
	    String POIName = getIntent().getStringExtra("POI");
	    final POI myPOI = DBAccessor.getInstance().getData().getPOIByName(POIName);
	    
	    TextView buildingTitle = (TextView) findViewById(R.id.floor_title);
	    buildingTitle.setText( POIName );
	    
	    final TextView floorLabel = (TextView) findViewById(R.id.floor_label);
	    floorLabel.setText( "Floor 1 of " + myPOI.getFloors() );
	    
	    
	    final ImageView floorPic = (ImageView) findViewById(R.id.floor_view);
	    //do this programmatically, son
	    floorPic.setImageResource(R.drawable.dac1);
	    
	    
	    
	    
	    gallery.setOnItemClickListener(new OnItemClickListener() {
	        public void onItemClick(AdapterView parent, View v, int position, long id)
	        {
	        	floorLabel.setText( "Floor " + (position + 1) + " of " + myPOI.getFloors() );
	        	floorPic.setImageResource(R.drawable.dac2);
	        }
	    });
	}
}


class ImageAdapter extends BaseAdapter {
    int mGalleryItemBackground;
    private Context mContext;

    private Integer[] mImageIds = {
            R.drawable.dac1,
            R.drawable.dac2,
            R.drawable.dac3
    };

    public ImageAdapter(Context c) {
        mContext = c;
        TypedArray attr = mContext.obtainStyledAttributes(R.styleable.FloorPlan);
        mGalleryItemBackground = attr.getResourceId(
                R.styleable.FloorPlan_android_galleryItemBackground, 0);
        attr.recycle();
        
    }

    public int getCount() {
        return mImageIds.length;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(mContext);

        imageView.setImageResource(mImageIds[position]);
        imageView.setLayoutParams(new Gallery.LayoutParams(150, 100));
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setBackgroundResource(mGalleryItemBackground);

        return imageView;
    }
}