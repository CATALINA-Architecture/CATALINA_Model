import java.util.ArrayList;

import com.Catalina_Model.Catalina_V_0_3.TAgent;

/**
 * To simulate the perception of the AV movement 
 * we use this class
 */
public class TVirtual_GPS 
{
	private TAgent Agent;
	private ArrayList<Integer> Positions;
	public TVirtual_GPS(TAgent agent)
	{
		this.Agent = agent;
		this.Positions = new ArrayList<Integer>();
	}
	
	public void Set_Current_Position(Integer city,
			Integer route, Integer step)
	{
		this.Positions.add(city);
		this.Positions.add(route);
		this.Positions.add(step);
	}
	
	public ArrayList<Integer> Get_Current_Position()
	{
		ArrayList<Integer> result = new ArrayList<Integer>();
		result.addAll( this.Positions );
		this.Positions.clear();
		return result;
	}
}
