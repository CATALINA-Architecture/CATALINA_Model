package com.Catalina_Model.Catalina_V_0_3;

import java.util.ArrayList;
import java.util.HashSet;

public class TDesires_Inhibition 
{
	
	private TExecutive_Inhibition_Function Parent;
	private TGlobal_Workspace Global_Workspace;
	private HashSet<TAttentional_Desire> Inhibited_Desires;
	private HashSet<TAttentional_Desire> Uninhibited_Desires;
	
	public TDesires_Inhibition(TExecutive_Inhibition_Function Owner)
	{
		this.Parent = Owner;
		this.Global_Workspace = this.Parent.Get_Global_Workspace();
		
		this.Uninhibited_Desires = new HashSet<TAttentional_Desire>();
		this.Inhibited_Desires = new HashSet<TAttentional_Desire>();
	}
	
	public void Execute(ArrayList<TIntention> Intentions)
	{
		this.Uninhibited_Desires.clear();
		this.Inhibited_Desires.clear();
		
		HashSet<TAttentional_Desire> inhibited_Desires = new HashSet<TAttentional_Desire>();
		HashSet<TAttentional_Desire> uninhibited_Desires = new HashSet<TAttentional_Desire>();
		
		inhibited_Desires.addAll( this.Global_Workspace.Get_Attentional_Desires() );
//		
		for(TIntention Intention: Intentions)
		{
			uninhibited_Desires.add((TAttentional_Desire) Intention.Get_Active_Desire() );
		}

		inhibited_Desires.removeAll( uninhibited_Desires );
		
		this.Uninhibited_Desires.addAll( uninhibited_Desires );
		this.Inhibited_Desires.addAll( inhibited_Desires );
	}
	
	public ArrayList<TAttentional_Desire> Get_Uninhibited_Desires()
	{
		ArrayList<TAttentional_Desire> result = new ArrayList<TAttentional_Desire>();
		result.addAll( this.Uninhibited_Desires );
		this.Uninhibited_Desires.clear();
		
		return result;
	}
	
	public ArrayList<TAttentional_Desire> Get_Inhibited_Desires()
	{
		ArrayList<TAttentional_Desire> result = new ArrayList<TAttentional_Desire>();
		result.addAll( this.Inhibited_Desires );
		this.Inhibited_Desires.clear();
		
		return result;
	}
}
