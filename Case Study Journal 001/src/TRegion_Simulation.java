import com.Catalina_Model.Catalina_V_0_3.TRegion;

public class TRegion_Simulation extends TRegion
{
	private TRoute Route;
	
	public TRegion_Simulation(TRoute route)
	{
		super();
		this.Route = route;
		this.Set_Name("Route_"+route.Get_Numered_Route());
		int o=0;
	}
	
	public TRoute Get_Route()
	{
		return this.Route;
	}
}
