package Cara_Simulation;

import java.util.ArrayList;

/**
 * It represents the base class of Goals
 */
public class TAttentional_Goal extends TGoal{
	
	//Green Goal List
	private transient ArrayList<TGreen_Goal> Green_Goals;
	//Quality Goal List
	private transient ArrayList<TQuality_Goal> Quality_Goals;
	
	private ArrayList<String> List_Green_Goal_Names;
	private ArrayList<String> List_Quality_Goal_Names;
	//not used
	protected double Sum_for_Attention_Treshold = 0.15;

	/**
	 * Cleans any data
	 */
	public void Clear()
	{
		this.Green_Goals.clear();
		this.Quality_Goals.clear();
		super.Clear();
	}
	
	
	
	/**
	 * Constructor
	 * @param name
	 * @param saliency
	 * @param reward
	 * @param relax_Preference
	 * @param list_Green_Goal
	 * @param list_Quality_Goal
	 */
	public TAttentional_Goal(String name, Double saliency, Double reward, Double relax_Preference, ArrayList<TGreen_Goal> list_Green_Goal,
			ArrayList<TQuality_Goal> list_Quality_Goal )
	{
		super(name, saliency, reward, relax_Preference);
		this.Green_Goals = list_Green_Goal;
		this.Quality_Goals = list_Quality_Goal;
		
		List_Green_Goal_Names = new ArrayList<String>();
		for (TGreen_Goal Green: Green_Goals)
		{
			List_Green_Goal_Names.add(Green.get_Name());			
		}
		
		List_Quality_Goal_Names = new ArrayList<String>();
		for (TQuality_Goal Quality: Quality_Goals)
		{
			List_Quality_Goal_Names.add(Quality.get_Name());			
		}
	
	}
	
	public TAttentional_Goal(String name, ArrayList<String> list_Green_Goal_name, ArrayList<String> list_Quality_Goal_name, 
			Double saliency, Double reward, Double relax_Preference )
	{
		super(name, saliency, reward, relax_Preference);
		this.Green_Goals = new ArrayList<TGreen_Goal>();
		this.Quality_Goals = new ArrayList<TQuality_Goal>();
		
		this.List_Green_Goal_Names = list_Green_Goal_name;
		this.List_Quality_Goal_Names = list_Quality_Goal_name;
//		List_Green_Goal_Names = new ArrayList<String>();
//		for (TGreen_Goal Green: List_Green_Goal)
//		{
//			List_Green_Goal_Names.add(Green.get_Name());			
//		}
		
//		List_Quality_Goal_Names = new ArrayList<String>();
//		for (TQuality_Goal Quality: List_Quality_Goal)
//		{
//			List_Quality_Goal_Names.add(Quality.get_Name());			
//		}
	
	}
	
	/**
	 * 
	 * This is TODO TO DEVELOP, --TAttentional_Goal.Check_Precondition-- togheter the Precondition
	 * now, it is true
	 * @return booleano: risultato di tutte le precondizioni
	 */
	public Boolean Check_Precondition()
	{
	
		
		return true;
	}
	
	
	public ArrayList<TGreen_Goal> get_List_Green_Goal() {
		return this.Green_Goals;
	}
	
	public ArrayList<String> get_List_Green_Goal_Name() {
		return this.List_Green_Goal_Names;
	}


	public void set_List_Green_Goal(ArrayList<TGreen_Goal> list_Green_Goal) {
		Green_Goals = list_Green_Goal;
	}

	public ArrayList<TQuality_Goal> get_List_Quality_Goal() {
		return Quality_Goals;
	}
	
	public ArrayList<String> get_List_Quality_Goal_Name() {
		return this.List_Quality_Goal_Names;
	}

	public void set_List_Quality_Goal(ArrayList<TQuality_Goal> list_Quality_Goal) {
		Quality_Goals = list_Quality_Goal;
	}
	
	/***
	 * TODO TO Development, --TAttentional_Goal.Verify_Precondition--
	 */
	public boolean Verify_Precondition()
	{
		boolean result = this.Check_Precondition();
		return result;
	}
	
	public double get_Sum_for_Attention_Treshold() {
		return this.Sum_for_Attention_Treshold;
	}

}
