package Cara_Simulation;

public class TIntention {
	
	private TDesire Desire;
	private Integer Seleted_Option_Id;
	
	public TIntention(TDesire desire, Integer seleted_Option_Id )
	{
		this.Desire = desire;
		this.Seleted_Option_Id = seleted_Option_Id;
	}

	public TDesire get_Desire() {
		return Desire;
	}

	public void set_Desire(TDesire desire) {
		Desire = desire;
	}

	public Integer get_Seleted_Option_Id() {
		return Seleted_Option_Id;
	}

	public void set_Seleted_Option_Id(Integer seleted_Option_Id) {
		Seleted_Option_Id = seleted_Option_Id;
	}

	
}
