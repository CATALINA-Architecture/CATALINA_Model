package Catalina_Simulation;

import java.util.ArrayList;

/**
 * It represents the base class of Goals
 */
public class TAttentional_Standing_Desire extends TStanding_Desire{
	
	//Green Standing_Desire List
	private transient ArrayList<TGreen_Standing_Desire> Green_Standing_Desires;
	//Quality Standing_Desire List
	private transient ArrayList<TQuality_Standing_Desire> Quality_Standing_Desires;
	
	private ArrayList<String> List_Green_Standing_Desire_Names;
	private ArrayList<String> List_Quality_Standing_Desire_Names;
	//not used
	protected double Sum_for_Attention_Treshold = 0.15;

	/**
	 * Cleans any data
	 */
	public void Clear()
	{
		this.Green_Standing_Desires.clear();
		this.Quality_Standing_Desires.clear();
		super.Clear();
	}
	
	
	
	/**
	 * Constructor
	 * @param name
	 * @param saliency
	 * @param reward
	 * @param relax_Preference
	 * @param list_Green_Standing_Desire
	 * @param list_Quality_Standing_Desire
	 */
	public TAttentional_Standing_Desire(String name, Double saliency, Double reward, Double relax_Preference, ArrayList<TGreen_Standing_Desire> list_Green_Standing_Desire,
			ArrayList<TQuality_Standing_Desire> list_Quality_Standing_Desire )
	{
		super(name, saliency, reward, relax_Preference);
		this.Green_Standing_Desires = list_Green_Standing_Desire;
		this.Quality_Standing_Desires = list_Quality_Standing_Desire;
		
		List_Green_Standing_Desire_Names = new ArrayList<String>();
		for (TGreen_Standing_Desire Green: Green_Standing_Desires)
		{
			List_Green_Standing_Desire_Names.add(Green.Get_Name());			
		}
		
		List_Quality_Standing_Desire_Names = new ArrayList<String>();
		for (TQuality_Standing_Desire Quality: Quality_Standing_Desires)
		{
			List_Quality_Standing_Desire_Names.add(Quality.Get_Name());			
		}
	
	}
	
	public TAttentional_Standing_Desire(String name, ArrayList<String> list_Green_Standing_Desire_name, ArrayList<String> list_Quality_Standing_Desire_name, 
			Double saliency, Double reward, Double relax_Preference )
	{
		super(name, saliency, reward, relax_Preference);
		this.Green_Standing_Desires = new ArrayList<TGreen_Standing_Desire>();
		this.Quality_Standing_Desires = new ArrayList<TQuality_Standing_Desire>();
		
		this.List_Green_Standing_Desire_Names = list_Green_Standing_Desire_name;
		this.List_Quality_Standing_Desire_Names = list_Quality_Standing_Desire_name;
	}
	

	public Boolean Check_Precondition()
	{
		return true;
	}
	
	
	public ArrayList<TGreen_Standing_Desire> Get_List_Green_Standing_Desire() {
		return this.Green_Standing_Desires;
	}
	
	public ArrayList<String> Get_List_Green_Standing_Desire_Name() {
		return this.List_Green_Standing_Desire_Names;
	}


	public void Set_List_Green_Standing_Desire(ArrayList<TGreen_Standing_Desire> list_Green_Standing_Desire) {
		Green_Standing_Desires = list_Green_Standing_Desire;
	}

	public ArrayList<TQuality_Standing_Desire> Get_List_Quality_Standing_Desire() {
		return Quality_Standing_Desires;
	}
	
	public ArrayList<String> Get_List_Quality_Standing_Desire_Name() {
		return this.List_Quality_Standing_Desire_Names;
	}

	public void Set_List_Quality_Standing_Desire(ArrayList<TQuality_Standing_Desire> list_Quality_Standing_Desire) {
		Quality_Standing_Desires = list_Quality_Standing_Desire;
	}

	public boolean Verify_Precondition()
	{
		boolean result = this.Check_Precondition();
		return result;
	}
	
	public double Get_Sum_for_Attention_Treshold() {
		return this.Sum_for_Attention_Treshold;
	}
}