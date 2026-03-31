import com.Catalina_Model.Catalina_V_0_3.TRegion;
import java.util.ArrayList;

public class TRegion_Simulation2 extends TRegion
{
	public ArrayList<TCity> Destinations;
	public ArrayList<TRoute> Routes;
	public ArrayList<Integer> Integer_Routes;

	public TRegion_Simulation2()
	{
		super();
		this.Destinations = new ArrayList<TCity>();
		this.Routes = new ArrayList<TRoute>();
		this.Integer_Routes = new ArrayList<Integer>();
	}
	
	public boolean Is_Empty()
	{
		boolean result = false;
		
		if(this.Destinations.isEmpty() && this.Routes.isEmpty() && 
				this.Integer_Routes.isEmpty())
		{
			result = true;
		}
		return result;
	}
	
	public void Clear()
	{
		this.Destinations.clear();
		this.Routes.clear();
		this.Integer_Routes.clear();
	}
}
