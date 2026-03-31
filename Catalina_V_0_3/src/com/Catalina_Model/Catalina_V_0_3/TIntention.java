package com.Catalina_Model.Catalina_V_0_3;

public class TIntention {

	private volatile TDesire Desire;
	private volatile Integer Seleted_Option_Id;
	private volatile String Name;
	
	public TIntention(String name, TDesire desire, Integer seleted_Option_Id )
	{
		this.Name = name;
		this.Desire = desire;
		if( seleted_Option_Id == null)
		{
			this.Seleted_Option_Id = -1;			
		}
		else
		{
			this.Seleted_Option_Id = seleted_Option_Id;
		}
		
	}

	public TDesire Get_Active_Desire() 
	{
		return this.Desire;
	}

	public void Set_Desire(TDesire desire)
	{
		this.Desire = desire;
	}

	public Integer Get_Selected_Option_Id() {
		return this.Seleted_Option_Id;
	}

	public void Set_Seleted_Option_Id(Integer seleted_Option_Id) {
		this.Seleted_Option_Id = seleted_Option_Id;
	}
	
	public void Clear()
	{
		this.Desire = null;
		this.Seleted_Option_Id = -1;
	}
	
	public void Set_Name(String name)
	{
		this.Name = name;
	}
	
	public String Get_Name()
	{
		return this.Name;
	}
	
	public TOption Get_Selected_Option()
	{
		TAttentional_Desire Attentional_Desire = (TAttentional_Desire) Desire;
		return Attentional_Desire.Get_List_Options().get( this.Seleted_Option_Id );
	}
	
	@Override
	public String toString() {
		return "TIntention[" +
			   "Name='" + Name + '\'' +
			   ", Desire=" + Desire +
			   ", Seleted_Option_Id=" + Seleted_Option_Id +
			   ']';
	}
}