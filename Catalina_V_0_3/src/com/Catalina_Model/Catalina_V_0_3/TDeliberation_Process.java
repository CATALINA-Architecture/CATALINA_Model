package com.Catalina_Model.Catalina_V_0_3;

import java.util.ArrayList;
import java.util.HashMap;

import com.Catalina_Model.Catalina_V_0_3.TExecutive_Reasoner_Function.TDeliberation_Process_Function;

public class TDeliberation_Process 
{
	
	private TExecutive_Reasoner_Function Owner;
	private TGlobal_Workspace Global_Workspace;
	
	private ArrayList<TIntention> Selected_Intentions = new ArrayList<TIntention>();
	
	private TDeliberation_Process_Function Deliberation_Process_Function;
	
	public TDeliberation_Process(TExecutive_Reasoner_Function owner)
	{
		this.Owner = owner;
		this.Global_Workspace = this.Owner.Get_Global_Workspace();
		
		this.Selected_Intentions = new ArrayList<TIntention>();
	}
	
	public void Execute( ArrayList<TAttentional_Desire> Active_Desires )
	{
		this.Selected_Intentions.clear();
//		ArrayList<TBelief> Beliefs = new ArrayList<TBelief>( this.Owner.Get_Beliefs() );
//		ArrayList<TRegion> Regions = new ArrayList<TRegion>( this.Owner.Get_Regions() );
		HashMap<String, TBelief> Beliefs = new HashMap<String, TBelief>(); 
		HashMap<String, TRegion> Regions = new HashMap<String, TRegion>();
		Beliefs.putAll( this.Owner.Get_Map_Beliefs());
		Regions.putAll( this.Owner.Get_Map_Regions());
		ArrayList<TIntention> Intentions = new ArrayList<TIntention>( this.Owner.Get_Intentions() );
		
		if ( this.Deliberation_Process_Function != null)
		{
			this.Selected_Intentions.addAll(
					this.Deliberation_Process_Function.Execute(
							Active_Desires, Beliefs, Regions, Intentions ) );
		}
	}
	
	public ArrayList<TIntention> Get_Selected_Intentions()
	{
		ArrayList<TIntention> result = new ArrayList<TIntention>();
		result.addAll( this.Selected_Intentions );
		this.Selected_Intentions.clear();
		
		return result;
	}
	
	public void  Set_Deliberation_Process_Function( TDeliberation_Process_Function Func)
	{
		this.Deliberation_Process_Function = Func;
	}

}
