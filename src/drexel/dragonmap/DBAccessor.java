package drexel.dragonmap;


public class DBAccessor
{
	public static DBAccessor instance = null;
	public static POIList data = null;
	
	protected DBAccessor()
	{
		
	}
	
	public static DBAccessor getInstance()
	{
		if (instance == null)
			instance = new DBAccessor();
		return instance;
	}
	
	public void setData(POIList POIs)
	{
		data = POIs;
	}
	
	public POIList getData()
	{
		return data;
	}
}