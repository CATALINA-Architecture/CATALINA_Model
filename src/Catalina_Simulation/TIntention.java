package Catalina_Simulation;

public class TIntention {
	
	private TActive_Desire Desire;
	private Integer Seleted_Option_Id;
	private String Name;
	
	public TIntention(String name, TActive_Desire desire, Integer seleted_Option_Id )
	{
		this.Name = name;
		this.Desire = desire;
		this.Seleted_Option_Id = seleted_Option_Id;
	}

	public TActive_Desire Get_Active_Desire() {
		return Desire;
	}

	public void set_Desire(TActive_Desire desire) {
		Desire = desire;
	}

	public Integer Get_Seleted_Option_Id() {
		return Seleted_Option_Id;
	}

	public void set_Seleted_Option_Id(Integer seleted_Option_Id) {
		Seleted_Option_Id = seleted_Option_Id;
	}
	
	public void Clear()
	{
		Desire = null;
	}
	
	public void Set_Name(String name)
	{
		this.Name = name;
	}
	
	public String Get_Name()
	{
		return this.Name;
	}
}