package Cara_Simulation;

import java.io.Serializable;
import java.util.ArrayList;

public class TGoal implements Serializable{
	//New 2024.10.23
	
	
	protected double Saliency;
	private Boolean Checked = false;
	private Double Reward;
	private Double Relax_Preference;
	private String Name;
	
	public void Clear()
	{
		
	}
	
	public TGoal( String name, Double saliency, Double reward, Double relax_Preference )
	{
		this.Name = name;
		this.Saliency = saliency;
		this.Reward = reward;
		this.Relax_Preference = relax_Preference;
		this.Checked = false;
	}
	
	//SectionMethods Implementetion
	
	// Saliency
	public Double get_Saliency() {
	return this.Saliency;
	}
	
	/**
	* modo per inserire un commento all'interno del code insight
	*/
	public void set_Saliency(double saliency) {
	this.Saliency = saliency;
	}
	
	// Reward
	public Double get_Reward() {
		return Reward;
	}

	public void set_Reward(double reward) {
		Reward = reward;
	}
	
	// Relax_Preference
	public Double get_Relax_Preference() {
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
	
	public String get_Name() {
		return Name;
	}

	public void set_Name(String name) {
		Name = name;
	}
}
