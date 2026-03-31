package com.Catalina_Model.Catalina_V_0_3;

import java.util.ArrayList;
import java.util.HashSet;

public class TDesire_Data 
{
	private HashSet<TBelief> Preconditions;
	private HashSet<TBelief> Beliefs;
	private HashSet<TRegion> Regions;

	public TDesire_Data()
	{
		this.Preconditions = new HashSet<TBelief>();
		this.Beliefs = new HashSet<TBelief>();
		this.Regions = new HashSet<TRegion>();
	}
	
	public void Clear()
	{
		this.Preconditions.clear();
		this.Beliefs.clear();
		this.Regions.clear();
	}
	
	public ArrayList<TBelief> Get_Preconditions()
	{
		ArrayList<TBelief> result = new ArrayList<TBelief>();
		result.addAll(this.Preconditions);
		this.Preconditions.clear();
		
		return result;
	}
	
	public ArrayList<TBelief> Get_Beliefs()
	{
		ArrayList<TBelief> result = new ArrayList<TBelief>();
		result.addAll(this.Beliefs);
		this.Beliefs.clear();
		
		return result;
	}
	
	public ArrayList<TRegion> Get_Regions()
	{
		ArrayList<TRegion> result = new ArrayList<TRegion>();
		result.addAll(this.Regions);
		this.Regions.clear();
		
		return result;
	}
	
	public void Set_Preconditions(HashSet<TBelief> values)
	{
		this.Preconditions.clear();
		this.Preconditions.addAll( values );
	}
	
	public void Set_Preconditions(ArrayList<TBelief> values)
	{
		this.Preconditions.clear();
		this.Preconditions.addAll( values );
	}
	
	public void Set_Beliefs(HashSet<TBelief> values)
	{
		this.Beliefs.clear();
		this.Beliefs.addAll( values );
	}
	
	public void Set_Regions(HashSet<TRegion> values)
	{
		this.Regions.clear();
		this.Regions.addAll( values );
	}
	
}
 