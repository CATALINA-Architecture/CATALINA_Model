package com.Catalina_Model.Catalina_V_0_3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class TLong_Term_Memory_Maintenance 
{
	private TExecutive_Memory_Maintenance_Function Parent;
	private TLong_Memory Long_Memory;
	
	public TLong_Term_Memory_Maintenance( TExecutive_Memory_Maintenance_Function owner,
			TLong_Memory long_memory)
	{
		this.Parent = owner;
		this.Long_Memory = long_memory;
	}
	
	public ArrayList<TAttentional_Desire> Get_Attentional_Desires()
	{
		return this.Long_Memory.Get_All_Attentional_Desires();
		
	}
	
	public ArrayList<TAttentional_Desire> Get_All_Not_Satisfied_Attentional_Desires()
	{
		return this.Long_Memory.Get_All_Not_Satisfied_Attentional_Desires();
	}
	
	public ArrayList<TAttentional_Desire> Get_Inhibited_Attentional_Desires()
	{
		return this.Long_Memory.Get_Inhibited_Attentional_Desires();
	}
	
	public ArrayList<TBelief> Get_All_Beliefs()
	{
		return this.Long_Memory.Get_All_Beliefs();
	}
	
	public HashMap<String, TBelief> Get_All_Map_Beliefs()
	{
		return this.Long_Memory.Get_All_Map_Beliefs();
	}
	
	public ArrayList<TBelief> Get_Inhibited_Beliefs()
	{
		return this.Long_Memory.Get_Inhibited_Beliefs();
	}
	
	public void Set_Beliefs(ArrayList<TBelief> beliefs)
	{
		this.Long_Memory.Clear_Beliefs();
		this.Long_Memory.Add_Beliefs(beliefs);
	}
	
	public void Set_Inhibited_Beliefs(ArrayList<TBelief> inhibited_beliefs)
	{
//		this.Long_Memory.Set_Inhibited_Beliefs(inhibited_beliefs);
	}
	
	public void Set_Inhibited_Data(ArrayList<TAttentional_Desire> inhibited_desires,
			ArrayList<TBelief> inhibited_beliefs,
			ArrayList<TRegion> inhibited_regions)
	{
		this.Long_Memory.Set_Inhibited_Data( inhibited_desires, inhibited_beliefs, inhibited_regions);
	}
	
	public void Add_Attentional_Desires(ArrayList<TAttentional_Desire> Desires)
	{
		this.Long_Memory.Add_Attentional_Desires( Desires );
	}
	
	public void Add_Inhibited_Practical_Desires(ArrayList<TPractical_Desire_Data> Desires_Data)
	{
		this.Long_Memory.Add_Inhibited_Practical_Desires(Desires_Data);
	}
	
	public void Add_Satisfied_Attentional_Desires(ArrayList<TAttentional_Desire> Desires)
	{
		this.Long_Memory.Add_Satisfied_Attentional_Desires( Desires );
	}
	
	public void Remove_Attentional_Desires(ArrayList<TAttentional_Desire> Desires)
	{
		this.Long_Memory.Remove_Attentional_Desires( Desires );
	}
	
	public ArrayList<TRegion> Get_Regions()
	{
		return this.Long_Memory.Get_All_Regions();
	}
	
	public ArrayList<TRegion> Get_Inhibited_Regions()
	{
		return this.Long_Memory.Get_Inhibited_Regions();
	}
	
	public void Remove_Inhibited_Data(ArrayList<TAttentional_Desire> inhibited_desires,
			ArrayList<TBelief> inhibited_beliefs,
			ArrayList<TRegion> inhibited_regions)
	{
		this.Long_Memory.Remove_Inhibited_Data( inhibited_desires, inhibited_beliefs, inhibited_regions);
	}
	
	public void Add_Inhibited_Desire(ArrayList<TAttentional_Desire> inhibited_desires)
	{
		this.Long_Memory.Add_Inhibited_Desire(inhibited_desires);
	}
	
	public ArrayList<TGreen_Desire> Get_All_Green_Desires()
	{
		return this.Long_Memory.Get_All_Green_Desires();
	}
	
	public ArrayList<TGreen_Desire> Get_Green_Desires_by_Names(ArrayList<String> Green_Names)
	{
		return this.Long_Memory.Get_Green_Desires_by_Names( Green_Names );
	}
	
	public ArrayList<TQuality_Desire> Get_All_Quality_Desires()
	{
		return this.Long_Memory.Get_All_Quality_Desires();	
	}
	
	public ArrayList<TQuality_Desire> Get_Quality_Desires_by_Names(ArrayList<String> Quality_Names)
	{
		return this.Long_Memory.Get_Quality_Desires_by_Names( Quality_Names );	
	}
	
	public ArrayList<TAttentional_Desire> Get_Satisfied_Attentional_Desires()
	{
		return this.Long_Memory.Get_All_Satisfied_Attentional_Desires();
	}
	
	public HashMap<String, TBelief> Get_Selected_Beliefs(HashSet<String> Beliefs_Names)
	{
		return this.Long_Memory.Get_Selected_Beliefs( Beliefs_Names );
	}
	
	public HashMap<String, TRegion> Get_Selected_Regions(HashSet<String> Regions_Names)
	{
		return this.Long_Memory.Get_Selected_Regions( Regions_Names );
	}
	
	public HashMap<String, TRegion>  Get_All_Map_Regions()
	{
		return this.Long_Memory.Get_All_Map_Regions();
	}
}
