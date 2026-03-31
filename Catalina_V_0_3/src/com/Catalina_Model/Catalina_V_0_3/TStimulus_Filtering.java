package com.Catalina_Model.Catalina_V_0_3;

import java.util.ArrayList;
import java.util.HashMap;

import com.Catalina_Model.Catalina_V_0_3.TStimulus_Filtering_Manipulate_Stimulus_Function_Handler.Stimulus_Filtering_Manipulate_Stimulus_Function;

public class TStimulus_Filtering
{
	private TExecutive_Perception_Function Parent;
	protected ArrayList<TBelief> Inhibited_Beliefs;
	protected ArrayList<TRegion> Inhibited_Regions;
	
//	protected TMap_Stimulus_To_Functions Map_Stimulus_to_Functions;
	protected TMap_Belief_To_Salience_Stimuli Map_Stimuli_To_Salience;
	private String Map_Stimuli_To_Salience_Path;
//	private HashMap< String, Double> Saliencies_for_Stimuli;
	
	private ArrayList<TStimulus> Survived_Stimuli;
	
	private TStimulus_Filtering_Manipulate_Stimulus_Function_Handler
	 				Manipulate_Stimulus_Function_Handler;
	
	public TStimulus_Filtering(TExecutive_Perception_Function Owner)
	{
		this.Parent = Owner;
		this.Inhibited_Beliefs = new ArrayList<TBelief>();
		this.Inhibited_Regions = new ArrayList<TRegion>();
		this.Survived_Stimuli = new ArrayList<TStimulus>();
		
//		this.Map_Stimulus_to_Functions = new TMap_Stimulus_To_Functions();
		
		this.Map_Stimuli_To_Salience = new TMap_Belief_To_Salience_Stimuli();
		this.Map_Stimuli_To_Salience_Path = "";
//		this.Map_Belief_To_Salience_Stimuli.Set_File_Path( this.Belief_To_Salience_Stimuli_Path );
//		this.Map_Belief_To_Salience_Stimuli.Load_Mapped_Epistemic_Desires();
		
		this.Manipulate_Stimulus_Function_Handler = 
				new TStimulus_Filtering_Manipulate_Stimulus_Function_Handler();
	}
	
	public void Execute(ArrayList<TStimulus> New_Stimuli, 
			Double Saliency_Threshold, Double Attention_Threshold, 
			ArrayList<TRegion> Inhibited_Regions, ArrayList<TBelief> Inhibited_Beliefs)
	{
		
		ArrayList<TStimulus> Temp_Survived_Stimuli = new ArrayList<TStimulus>();
		ArrayList<TStimulus> Relevant_Stimuli = new ArrayList<TStimulus>();
		Temp_Survived_Stimuli.clear();
		Relevant_Stimuli.clear();
		
		this.Survived_Stimuli.clear();
		
		//For each New Stimulus, I get a related Relevant Stimulus (One to One way)
		Relevant_Stimuli.addAll( this.Apply_Filter(New_Stimuli, Inhibited_Regions ) );
		
		int index = 0;
		for(TStimulus Relevant_Stimulus: Relevant_Stimuli)
		{
			
//			if ((Inhibited_Regions != null) && (Relevant_Stimulus != null))
			if ((Inhibited_Regions.size() > 0 ) && (Relevant_Stimulus != null))
			{
				if ( Relevant_Stimulus.Get_Saliency() > Saliency_Threshold )
				{
					Temp_Survived_Stimuli.add( Relevant_Stimulus );
				}
			}
			else
			{
				/**
				 * In this case, the considered Stimulus is in New_Stimuli,
				 * because Relevant_Stimulus can be null.
				 * New_Stimuli and Relevant_Stimuli has the same size, because
				 * for each new stimuli, Relevant_Stimuli has a computer stimulus,
				 * so we can get the correct Stimulus in New_Stimuli
				 */
//				int index = Relevant_Stimuli.indexOf( Relevant_Stimulus );
				TStimulus Stimulus = New_Stimuli.get( index );
				
				if ( Stimulus.Get_Saliency() > Attention_Threshold )
				{
					Temp_Survived_Stimuli.add( Relevant_Stimulus );
				}
			}
			index++;
		}
		
//		this.Survived_Stimuli.clear();
		if( Temp_Survived_Stimuli.size() > 0)
		{
			this.Survived_Stimuli.addAll( Temp_Survived_Stimuli );
		}
		
	}
	
	/**
	 * This function get New stimuli to analyze and inhibition regions from Long
	 * Memory.
	 * For each stimulus, it assigns a saliency to this stimulus if the map
	 * "Mapped_Stimuli_To_Salience_Value" contains a type stimulus equal to
	 * the type of the stimulus to analyze.
	 * 
	 * Further, for each stimulus the map "Manipulate_Stimulus_Function_Handler" 
	 * can executes a function associated to the type of stimulus.
	 * 
	 * In this way, at the same time, a stimulus can get the saliency value in 
	 * two way:
	 * 1- Using "Mapped_Stimuli_To_Salience_Value" to assign immediately the saliency
	 * value (because the saliency is associated with the map to the type of
	 * stimulus)
	 * 2- Executing a function associated to the type of the stimulus by using
	 * "Manipulate_Stimulus_Function_Handler". 
	 * In this case the stimulus can change its values (saliency, and other 
	 * properties of the stimulus) dynamically, according to the function.
	 * 
	 * For each stimulus, both ways can be done at the same time.
	 * 
	 * For example: a stimulus can acquire the corresponding saliency value and
	 * later can be computed dynamically for to give value to the context 
	 * the agent is experiencing or based on its previous experiences
	 * 
	 * 
	 * 
	 * @param New_Stimuli
	 * @param Inhi_regions
	 * @return
	 */
	public ArrayList<TStimulus> Apply_Filter(ArrayList<TStimulus> New_Stimuli, 
			ArrayList<TRegion> Inhi_regions )
	{
		ArrayList<TStimulus> Survived_Stimuli = new ArrayList<TStimulus>();
		
		HashMap<String, Double> Mapped_Stimuli_To_Salience_Value = this.Map_Stimuli_To_Salience.Get_Mapped_Stimuli();

		
//		HashMap<String, Function<TStimulus,TEpistemic_Desire>> Mapped_Functions = 
//				this.Map_Stimulus_to_Functions.Get_Mapped_Functions();
		
		
//		this.Map_Stimulus_to_Functions.Get_Mapped_Functions();
		
		String Type_Stimulus = "";
		for(TStimulus Stimulus: New_Stimuli)
		{
			TStimulus Relevant_Stimulus = null;
			
			Type_Stimulus = Stimulus.Get_Type_Belief();
			if (Mapped_Stimuli_To_Salience_Value.containsKey( Type_Stimulus ) )
			{
				Relevant_Stimulus = Stimulus;
				Relevant_Stimulus.Update_Saliency( Mapped_Stimuli_To_Salience_Value.get( Type_Stimulus ));
				
			}
//			if (this.Manipulate_Stimulus_Function_Handler )
			{
				if (Relevant_Stimulus == null)
				{
					Relevant_Stimulus = Stimulus;
				}
				Relevant_Stimulus = this.Manipulate_Stimulus_Function_Handler.
									Execute_Function_For_Stimulus( 
											Relevant_Stimulus, Inhi_regions );
			}

			Survived_Stimuli.add( Relevant_Stimulus );
		}
//		return New_Stimuli;
		return Survived_Stimuli;
		
	}
	
	public void Load_Saliencies_for_Stimuli(String filename)
	{
		this.Map_Stimuli_To_Salience_Path = filename;
		this.Map_Stimuli_To_Salience.Load_Mapped_Epistemic_Desires( 
				this.Map_Stimuli_To_Salience_Path );
	}
	
	public void Register_Function(String Stimulus_Type, Stimulus_Filtering_Manipulate_Stimulus_Function func) 
    {
        this.Manipulate_Stimulus_Function_Handler.Register_Function(
        		Stimulus_Type, func);
    }
    
    public boolean Unregister_Function(String Stimulus_Type) 
    {
    	return this.Manipulate_Stimulus_Function_Handler.
    							Unregister_Function(Stimulus_Type);
    }
    
    public void Add_Saliency_to_Map_Stimulus_to_Saliency(String Stimulus_Type, 
    		Double Saliency)
    {
    	this.Map_Stimuli_To_Salience.Add_Type_Belief_To_Salience_Stimuli( 
    			Stimulus_Type, Saliency );
    }
    
    public ArrayList<TStimulus> Get_Survived_Stimuli()
    {
    	ArrayList<TStimulus> result = new ArrayList<TStimulus>();
    	result.addAll( this.Survived_Stimuli );
    	this.Survived_Stimuli.clear();
    	return result;
    }
}