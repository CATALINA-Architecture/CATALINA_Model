package Catalina_Simulation;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;

public class TOption{
	
	private ArrayList<TAction> Plan_Actions;
	public Plan Path;
	private TPredicate Precondition;
	private Double Cost;
	private EnumMap<TType_Quality_Desire, Double> Quality_List;
	private int Action_To_Do_ID; 
	
	public void Clear()
	{
		this.Plan_Actions.clear();
		this.Precondition.Clear();
		this.Quality_List.clear();
	}
	
	public TOption(ArrayList<TAction> plans, TPredicate precondition, Double cost, EnumMap<TType_Quality_Desire, Double> quality_List)
	{
		this.Plan_Actions = plans;
		this.Precondition = precondition;
		this.Cost = cost;
		this.Quality_List = quality_List;
		this.Path = new Plan();
		this.Action_To_Do_ID = 0;
	}
	
	

	public ArrayList<TAction> Get_Plan_Actions() {
		return Plan_Actions;
	}




	public void set_Plan_Actions(ArrayList<TAction> plan_actions) {
		Plan_Actions = plan_actions;
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
		return this.Action_To_Do_ID;
	}
	
	public void Inc_Action_To_Do_ID()
	{
		this.Action_To_Do_ID++;
	}
}