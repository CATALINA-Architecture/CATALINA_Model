package Cara_Simulation;

import java.util.ArrayList;

public class TDesire {
	
	private TAttentional_Goal Attentional_Goal;
	private ArrayList<TOption> Option_List;
	private String Name;
	
	public String Get_Name()
	{
		return this.Name;
	}
	

	public TDesire(String Name, TAttentional_Goal attentional_Goal, ArrayList<TOption> option_List )
	{
		this.Attentional_Goal = attentional_Goal;
		this.Name = Name;
		
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


	public TAttentional_Goal get_Attentional_Goal() {
		return Attentional_Goal;
	}


	public void set_Attentional_Goal(TAttentional_Goal attentional_Goal) {
		Attentional_Goal = attentional_Goal;
	}


	public ArrayList<TOption> get_Option_List() {
		return Option_List;
	}


	public void set_Option_List(ArrayList<TOption> option_List) {
		Option_List = option_List;
	}



}
