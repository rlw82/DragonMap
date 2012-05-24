/*
 * POI.java
 * 4/20/12
 * Version 1.0
 * 
 * For use in the Dragon Map Android Application
 * 
 */

package drexel.dragonmap;

import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;


/*
 * points of interest class
   -Name
   -Description/keywords (searchable)
   -GPS or image coordinate location
   -size
   -floors
   -address
   
   Originally, the Building class was going to extend the POI class, but there is really no
   point to have two different classes if they both do exactly the same thing. It is more
   work for us, and more potential for quirky inheritance stuff to get in the way of actual
   map work. 0 floors will indicate that something is not a building. Non-building items can
   have addresses too.
   
   Note: this is subject to change in the event that we throw floor plans into the mix.
 
   Important:
   	Position is given as the X and Y coordinate (integers) of the bounding box
   	that surrounds a Point of interest. The X and Y coordinates are taken from
   	the TOP-LEFT corner of the bounding box. It is assumed that the TOP-LEFT
   	corner of the window is (0, 0)
   	
 */
public class POI
{
	private String name_;
	private String description_;
	private ArrayList<String> keywords_ = new ArrayList<String>();
	private double width_;
	private double height_;
	private int floors_;
	private String address_;
	private double x_;
	private double y_;
	private String category_;
	
	/*
	 * Constructor with all arguments given.
	 */
	public POI(String name, String description, ArrayList<String> keywords, String category,
			   int floors, String address, double x, double y, double w, double h)
	{
		this.name_ = name;
		this.description_ = description;
		this.keywords_ = keywords;
		this.floors_  = floors;
		this.address_ = address;
		this.category_ = category;
		this.x_ = x;
		this.y_ = y;
		this.width_ = w;
		this.height_ = h;
	}
	
	/*
	 * default constuctor, used to load from JSON or from mutators.
	 */
	public POI()
	{
		this.name_ = "";
		this.description_ = "";
		this.keywords_ = null;
		this.floors_  = 0;
		this.address_ = "";
		this.x_ = 0;
		this.y_ = 0;
		this.width_ = 0;
		this.height_ = 0;
	}

	
//-----Setters---------------------------------------------------------
	public void setName(String name)
	{
		this.name_ = name;
	}
	
	public void setDescription(String description)
	{
		this.description_ = description;
	}
	
	public void setKeywords(ArrayList<String> keywords)
	{
		this.keywords_ = keywords;
	}
	
	public void setPosition(double x, double y)
	{
		this.x_ = x;
		this.y_ = y;
	}
	
	/*
	 * Mutate the bounding box of the POI.
	 * @param w The width of the bbox
	 * @param h The height of the bbox
	 */
	public void setSize(double w, double h)
	{
		this.width_ = w;
		this.height_ = h;
	}
	
	public void setFloors(int floors)
	{
		this.floors_ = floors;
	}

	
	public void setAddress(String address)
	{
		this.address_ = address;
	}
	
	public void getCategory(String category)
	{
		this.category_ = category;
	}

//-----Getters---------------------------------------------------------
	public String getName()
	{
		return this.name_;
	}
	
	public String getDescription()
	{
		return this.description_;
	}
	
	public ArrayList<String> getKeywords()
	{
		return this.keywords_;
	}
	
	public double getX()
	{
		return this.x_;
	}
	
	public double getY()
	{
		return this.y_;
	}
	
	public double getWidth()
	{
		return this.width_;
	}
	
	public double getHeight()
	{
		return this.height_;
	}
	
	public int getFloors()
	{
		return this.floors_;
	}
	
	public String getAddress()
	{
		return this.address_;
	}
	
	public String getCategory()
	{
		return category_;
	}


//-----Facilitators---------------------------------------------------------
	/*
	 * Checks if a given point is contained within the bounding box of the POI.
	 * @param x The x coordinate of the point to check.
	 * @param y The y coordinate of the point to check.
	 * @return True if the point is inside the bounding box, otherwise False.
	 */
	public boolean isContained(float x, float y)
	{
		return (
				(x >= this.x_) && (x <= this.x_ + this.width_) &&
		        (y >= this.y_) && (y <= this.y_ + this.height_)
		       );
	}
	
	//for debugging purposes, maybe
	public String toLongString()
	{
		return "Name: " + this.name_ + "\n" +
				"    Description: " + this.description_ + "\n" +
				"    Keywords: " + this.keywords_.toString() + "\n" +
				"    Floors: " + this.floors_ + "\n" +
				"    Address: " + this.address_ + "\n" +
				"    Position: (" + this.x_ + ", " + this.y_ + ")\n" +
				"    Size: [" + this.width_ + ", " + this.height_ + "]\n" + 
				"    Category: " + this.category_ + "\n";
	}
	
	@Override
	public String toString()
	{
		return this.name_;
	}
	
	public String getKeywordsAsString()
	{
		String keys = "Keywords: ";
		for (String str: keywords_)
			keys += str + " ";
		return keys;
	}
	
	@SuppressWarnings("unchecked")
	public String toJSONString()
	{
		JSONObject obj = new JSONObject();
		obj.put("name", name_);
		obj.put("description", description_);
		JSONArray keys = new JSONArray();
		for (String key : keywords_)
			keys.add(key);
		obj.put("keywords", keys);
		obj.put("floors", this.floors_);
		obj.put("address", this.address_);
		obj.put("x", this.x_);
		obj.put("y", this.y_);
		obj.put("width", this.width_);
		obj.put("height", this.height_);
		obj.put("category", this.category_);
		return obj.toString();
	}
	
	@SuppressWarnings("unchecked")
	public void fromJSON(String JSON)
	{
		JSONObject obj = (JSONObject)JSONValue.parse(JSON);
		this.name_ = (String)obj.get("name");
		JSONArray keywords = (JSONArray)obj.get("keywords");
		this.keywords_ = (ArrayList<String>)keywords;
		this.description_ = (String)obj.get("description");
		this.address_ = (String)obj.get("address");
		this.category_ = (String)obj.get("category");
		this.floors_ = ((Number)obj.get("floors")).intValue();
		this.x_ = ((Double)obj.get("x")).doubleValue();
		this.y_ = ((Double)obj.get("y")).doubleValue();
		this.width_ = ((Double)obj.get("width")).doubleValue();
		this.height_ = ((Double)obj.get("height")).doubleValue();
	}
	
	public boolean isMatch(String find)
	{
		boolean found;
		find = find.toLowerCase();
		if (name_.toLowerCase().contains(find))
			found = true;
		else if (description_.toLowerCase().contains(find))
			found = true;
		else if (keywords_.contains(find))
			found = true;
		else if (address_.toLowerCase().contains(find))
			found = true;
		else
			found = false;
		
		return found;
	}
	
}





