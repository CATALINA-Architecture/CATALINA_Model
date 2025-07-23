package Catalina_Simulation;

import java.io.Serializable;
import java.util.ArrayList;

public class TStanding_Desire implements Serializable{
	//New 2024.10.23
	
	
	protected double Saliency;
	private Boolean Checked = false;
	private Double Reward;
	private Double Relax_Preference;
	private String Name;
	
	public void Clear()
	{
		
	}
	
	public TStanding_Desire( String name, Double saliency, Double reward, Double relax_Preference )
	{
		this.Name = name;
		this.Saliency = saliency;
		this.Reward = reward;
		this.Relax_Preference = relax_Preference;
		this.Checked = false;
	}
	
	//SectionMethods Implementetion
	
	// Saliency
	public Double Get_Saliency() {
	return this.Saliency;
	}
	
	/**
	* modo per inserire un commento all'interno del code insight
	*/
	public void set_Saliency(double saliency) {
	this.Saliency = saliency;
	}
	
	// Reward
	public Double Get_Reward() {
		return Reward;
	}

	public void set_Reward(double reward) {
		Reward = reward;
	}
	
	// Relax_Preference
	public Double Get_Relax_Preference() {
		return Relax_Preference;
	}

	public void set_Relax_Preference(double relax_Preference) {
		Relax_Preference = relax_Preference;
	}

	// Checked
	public Boolean Check_Satisfation()
	{
		return Checked;
		
	}
	
	public String Get_Name() {
		return Name;
	}

	public void set_Name(String name) {
		Name = name;
	}
}