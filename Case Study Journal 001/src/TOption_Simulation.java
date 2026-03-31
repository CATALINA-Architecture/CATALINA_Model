import java.util.ArrayList;
import java.util.EnumMap;

import com.Catalina_Model.Catalina_V_0_3.TAction;
import com.Catalina_Model.Catalina_V_0_3.TOption;
import com.Catalina_Model.Catalina_V_0_3.TPredicate;

public class TOption_Simulation extends TOption
{
	
//	private ArrayList<TAction> Plan_Actions;
	public TPlan_Simulation Path;
	private TPredicate Precondition;
	private Double Cost;
	private EnumMap<TType_Quality_Desire, Double> Quality_List;
//	private int Action_To_Do_ID; 
	
	public void Clear()
	{
		super.Clear();
		this.Precondition.Clear();
		this.Quality_List.clear();
	}
	
	public TOption_Simulation(ArrayList<TAction> plans, 
			TPredicate precondition, Double cost, EnumMap<TType_Quality_Desire, Double> quality_List)
	{
		super();
		if (plans != null)
		{
			this.Get_Plan_Actions().addAll( plans );
		}
		
		this.Precondition = precondition;
		this.Cost = cost;
		this.Quality_List = quality_List;
		this.Path = new TPlan_Simulation();
	}
	
	

	public ArrayList<TAction> Get_Plan_Actions() {
		return super.Get_Plan_Actions();
	}




	public void set_Plan_Actions(ArrayList<TAction> plan_actions)
	{
		this.Clear();
		this.Get_Plan_Actions().addAll( plan_actions );
	}




	public TPredicate get_Precondition() {
		return Precondition;
	}



	public void set_Precondition(TPredicate precondition) {
		Precondition = precondition;
	}



	public Double get_Cost() {
		return Cost;
	}



	public void set_Cost(Double cost) {
		Cost = cost;
	}

	public EnumMap<TType_Quality_Desire, Double> get_Quality_List() {
		return Quality_List;
	}

	public void set_Quality_List(EnumMap<TType_Quality_Desire, Double> quality_List) {
		Quality_List = quality_List;
	}
	
	public int Get_Action_To_Do_ID()
	{
		return this.Get_ID_Current_Action();
	}
	
	public void Inc_Action_To_Do_ID()
	{
		this.Next_ID_Current_Action();
	}

}
