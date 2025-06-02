package Cara_Simulation;

import java.util.ArrayList;

public class TActive_Desire {
	
	private TAttentional_Desire Attentional_Goal;
	private ArrayList<TOption> Option_List;
	private String Name;
	private TIntention Related_Intention;
	
	public String Get_Name()
	{
		return this.Name;
	}
	
	public void Clear()
	{
		Attentional_Goal = null;
		for(TOption Option: Option_List)
		{
			Option.Clear();
		}
		Option_List.clear();
	}
	

	public TActive_Desire(String Name, TAttentional_Desire attentional_Goal, ArrayList<TOption> option_List )
	{
		this.Attentional_Goal = attentional_Goal;
		this.Name = Name;
		this.Related_Intention = null;
		
		//if option_List is different to null 
		if (Option_List != null)
		{
			this.Option_List  = option_List;
		}
		//else I created the Option_List
		else
		{
			Option_List = new ArrayList<TOption>();
		}
	}
	
	public void Set_Realated_Intention(TIntention intention)
	{
		this.Related_Intention = intention;
	}
	
	public TIntention Get_Realated_Intention()
	{
		return this.Related_Intention;
	}


	public TAttentional_Desire get_Attentional_Goal() {
		return Attentional_Goal;
	}


	public void set_Attentional_Goal(TAttentional_Desire attentional_Goal) {
		Attentional_Goal = attentional_Goal;
	}


	public ArrayList<TOption> get_Option_List() {
		return Option_List;
	}


	public void set_Option_List(ArrayList<TOption> option_List) {
		this.Option_List.clear();
		Option_List.addAll(option_List);
	}
}