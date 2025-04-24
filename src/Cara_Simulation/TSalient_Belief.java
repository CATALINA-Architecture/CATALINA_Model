package Cara_Simulation;

import java.time.LocalDateTime;

public class TSalient_Belief extends TBelief {
	
	private Double Saliency;
	
	
	public Double Get_Saliency() {
		return Saliency;
	}
	public void Update_Saliency(Double saliency) {
		Saliency = saliency;
	}


	public TSalient_Belief(String name, TPredicate predicate, Double saliency, Boolean truth, Object information_Source,
			LocalDateTime time_stamp, TType_Beliefs type_Belief)
	{
		super(name, predicate, truth, information_Source, time_stamp, type_Belief);
		this.Saliency = saliency;
	}
	
	public TSalient_Belief(String name, String predicate_Name, Double saliency, Boolean truth, Object information_Source,
			LocalDateTime time_stamp, TType_Beliefs type_Belief)
	{
		super(name, predicate_Name, truth, information_Source, time_stamp, type_Belief);
		this.Saliency = saliency;
		
	}

}
