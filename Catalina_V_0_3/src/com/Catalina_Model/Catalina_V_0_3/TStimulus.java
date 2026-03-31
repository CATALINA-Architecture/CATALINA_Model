package com.Catalina_Model.Catalina_V_0_3;

import java.time.LocalDateTime;

public class TStimulus extends TBelief {
	
	private volatile Double Saliency;
	
	public Double Get_Saliency() {
		return this.Saliency;
	}
	public void Update_Saliency(Double saliency) {
		this.Saliency = saliency;
	}

	public TStimulus(String name, TPredicate predicate, Double saliency, Boolean truth, Object information_Source,
			LocalDateTime time_stamp, String type_Belief)
	{
		super(name, predicate, truth, information_Source, time_stamp, type_Belief);
		this.Saliency = saliency;
	}
	
	public TStimulus(String name, String predicate_Name, Double saliency, Boolean truth, Object information_Source,
			LocalDateTime time_stamp, String type_Belief)
	{
		super(name, predicate_Name, truth, information_Source, time_stamp, type_Belief);
		this.Saliency = saliency;
	}
	
	@Override
    public String toString() {
        // Chiama la toString() del genitore (TBelief) e aggiunge
        // i campi specifici di questa classe (TStimulus).
        return "TStimulus[" +
               "Saliency=" + Saliency +
               "] " + super.toString();
    }
}