package com.Catalina_Model.Catalina_V_0_3;

import java.util.ArrayList;
import java.util.HashSet;

import com.Catalina_Model.Catalina_V_0_3.TGlobal_Workspace_Memory_Maintenance_Update_Belief_Function_Handler.Global_Workspace_Memory_Maintenance_Update_Belief_Function;
import com.Catalina_Model.Catalina_V_0_3.TInformation_Extraction_Function_Handler.Information_Extraction_Function;
import com.Catalina_Model.Catalina_V_0_3.TPerception_Processing_Sensor_Function_Handler.Memory_Maintenance_Sensor_Function;
import com.Catalina_Model.Catalina_V_0_3.TStimulus_Filtering_Manipulate_Stimulus_Function_Handler.Stimulus_Filtering_Manipulate_Stimulus_Function;

public class TExecutive_Memory_Maintenance_Function extends TAgent_Base_Thread
{

//	private TLong_Memory Long_Memory;
	
//	private TPerceptions_Processing_old Perceptions_Processing = null;;
//	private TInformation_Extraction_old Information_Extraction = null;;
//	private TStimulus_Filtering_old Stimulus_Filtering = null;
	
//	private HashSet<Object> Sensors; 
	
//	private ArrayList<TPerception> Perceptions;
//	private ArrayList<TStimulus> Stimuli;
	private ArrayList<TStimulus> Filtered_Stimuli;
//	private ArrayList<TBelief> Inhibited_Beliefs;
//	private ArrayList<TRegion> Inhibited_Regions;
//	private ArrayList<TBelief> Uninhibited_Beliefs;
//	private ArrayList<TRegion> Uninhibited_Regions;
	
	private Double Saliency_Threshold;
	private Double Attention_Threshold;
	
	private TGlobal_Workspace Global_Workspace; 
	private TGlobal_Workspace_Memory_Maintenance Global_Workspace_Memory_Maintenance;
	private TLong_Term_Memory_Maintenance Long_Term_Memory_Maintenance;
	
//	private TBoolean_Protected_Object Continue_Execution;
	
	
	public TExecutive_Memory_Maintenance_Function(TAgent agent, TLong_Memory Long_Memory) 
	{
	
		super(agent, "Executive Working Memory Maintenance Function");
//		this.Long_Memory = Long_Memory;
//		this.Continue_Execution = new TBoolean_Protected_Object(true);
		
		this.Global_Workspace = agent.Get_Global_WorkSpace();
		this.Global_Workspace_Memory_Maintenance = new TGlobal_Workspace_Memory_Maintenance(
				this, this.Agent.Get_Global_WorkSpace());
		this.Long_Term_Memory_Maintenance = new TLong_Term_Memory_Maintenance(this, 
				Long_Memory);
		
		this.Filtered_Stimuli = new ArrayList<TStimulus>();
//		this.Sensors = new HashSet<Object>();
//		
//		this.Perceptions_Processing = new TPerceptions_Processing_old( this );
//		this.Information_Extraction = new TInformation_Extraction_old( this );
//		this.Stimulus_Filtering = new TStimulus_Filtering_old( this );
//		
//		
//		this.Perceptions = new ArrayList<TPerception>();
//		this.Stimuli = new ArrayList<TStimulus>();
//		this.Filtered_Stimuli = new ArrayList<TStimulus>();
//		
//		this.Inhibited_Beliefs = new ArrayList<TBelief>();
//		this.Inhibited_Regions = new ArrayList<TRegion>();
//		
//		this.Uninhibited_Beliefs = new ArrayList<TBelief>();
//		this.Uninhibited_Regions = new ArrayList<TRegion>();
		
		this.Saliency_Threshold = 0.0;
		this.Attention_Threshold = 0.0;
		
		//I gets several data from Long Memory at the start of the Agent
		TDouble_Object Thresholds =this.Agent.Get_Global_WorkSpace().Get_Saliency_and_Attention_Thresholds();
		if( Thresholds != null)
		{
			this.Saliency_Threshold = (Double) Thresholds.Get_Object_First();
			this.Attention_Threshold = (Double) Thresholds.Get_Object_Second();
		}
		
//		this.Uninhibited_Beliefs.addAll( this.Long_Term_Memory_Maintenance.Get_All_Beliefs() );
//		
//		
//		this.Uninhibited_Regions.addAll( this.Long_Term_Memory_Maintenance.Get_Regions() );
		
	}

	@Override
	public void Insert_in_List_Update_Contract()
	{
		this.Agent.Get_Global_WorkSpace().Insert_for_Update_Contract(TType_Update_Contract.Updated_Filtered_Stimuli, this);
		this.Agent.Get_Global_WorkSpace().Insert_for_Update_Contract(TType_Update_Contract.Updated_Thresholds, this);
	}
	
//	public void Set_Perception_Processing(TPerceptions_Processing_old perceptions_processing)
//	{
//		this.Perceptions_Processing = perceptions_processing;
//	}
//	
//	public void Set_Information_Extraction(TInformation_Extraction_old information_extraction)
//	{
//		this.Information_Extraction = information_extraction;
//	}
//	
//	public void Set_Stimulus_Filtering(TStimulus_Filtering_old stimulus_filtering)
//	{
//		this.Stimulus_Filtering = stimulus_filtering;
//	}
	
//	@Override
//	public void Execute_old()
//	{
//		/**
//		 * Thresholds is a double object:
//		 * 1- Saliency Threshold: Double
//		 * 2- Attention Threshold: Double
//		 */
//		TDouble_Object Thresholds = null;
//		
//		Boolean Updated_Thresholds;
//		
////		if ( this.Continue_Execution.Read() )
////		while( this.Read_Continue_Thread() )
//		{
//			Updated_Thresholds = this.Message_Handler.Read_Value_And_Clear_Updated_Thresholds();
//			
//			if(Updated_Thresholds)
//			{
//				Thresholds = this.Agent.Get_Global_WorkSpace().Get_Saliency_and_Attention_Thresholds();
//				this.Saliency_Threshold = (Double) Thresholds.Get_Object_First();
//				this.Attention_Threshold = (Double) Thresholds.Get_Object_Second();
//				
//				// If Updated_Thresholds is true, it means that a change in
//				// inhibited and uninhibited conscious data occurs
//			}
//			
//			if (this.Message_Handler.Read_Value_And_Clear_Updated_Unhinibited_Data() )
//			{
//				this.Inhibited_Beliefs.clear();
//				this.Inhibited_Beliefs.addAll( this.Long_Term_Memory_Maintenance.Get_Inhibited_Beliefs());
//				
//				this.Inhibited_Regions.clear();
//				this.Inhibited_Regions.addAll( this.Long_Term_Memory_Maintenance.Get_Inhibited_Regions());
//				
//				this.Uninhibited_Beliefs.addAll( this.Long_Term_Memory_Maintenance.Get_All_Beliefs() );
//				this.Uninhibited_Beliefs.removeAll( this.Inhibited_Beliefs );
//				
//				this.Uninhibited_Regions.addAll( this.Long_Term_Memory_Maintenance.Get_Regions() );
//				this.Uninhibited_Regions.removeAll( this.Inhibited_Regions );
//			}
//			
//			this.Perceptions.clear();
//			this.Stimuli.clear();
//			this.Survived_Stimuli.clear();
//			
//				this.Perceptions_Processing.Execute();
//				this.Perceptions.addAll( this.Perceptions_Processing.Get_Last_Acquired_Perceptions() );
//				
//				this.Information_Extraction.Execute( this.Perceptions, this.Uninhibited_Beliefs, 
//														this.Uninhibited_Regions );
//				this.Stimuli.addAll( this.Information_Extraction.Get_Last_Stimuli() );
//
//				this.Stimulus_Filtering.Execute
//				(
//						this.Stimuli, 
//						this.Saliency_Threshold, this.Attention_Threshold, 
//						this.Inhibited_Regions, this.Inhibited_Beliefs 
//				);
//				
//				this.Survived_Stimuli.addAll( this.Stimulus_Filtering.Get_Survived_Stimuli() );
//
//			if( this.Survived_Stimuli.size() > 0)
//			{
//				this.Global_Workspace_Memory_Maintenance.Set_Stimuli_in_GW( this.Survived_Stimuli );
//			}
//		}
//	}
	
	@Override
	public void Execute()
	{
		Boolean Updated_Filtered_Stimuli = this.Message_Handler.Read_Value_And_Clear_Updated_Filtered_Stimuli();
		
//		if ( this.Continue_Execution.Read() )
//		while( this.Read_Continue_Thread() )
		if (Updated_Filtered_Stimuli)
		{
			this.Filtered_Stimuli.clear();
			this.Filtered_Stimuli.addAll( this.Global_Workspace.Get_Filtered_Stimuli() );
			/**
			 * Thresholds is a double object:
			 * 1- Saliency Threshold: Double
			 * 2- Attention Threshold: Double
			 */
			TDouble_Object Thresholds = null;
			
			Boolean Updated_Thresholds;
			Updated_Thresholds = this.Message_Handler.Read_Value_And_Clear_Updated_Thresholds();
			
			if(Updated_Thresholds)
			{
				Thresholds = this.Agent.Get_Global_WorkSpace().Get_Saliency_and_Attention_Thresholds();
				this.Saliency_Threshold = (Double) Thresholds.Get_Object_First();
				this.Attention_Threshold = (Double) Thresholds.Get_Object_Second();
				
				// If Updated_Thresholds is true, it means that a change in
				// inhibited and uninhibited conscious data occurs
			}
			
//			if (this.Message_Handler.Read_Value_And_Clear_Updated_Unhinibited_Data() )
//			{
//				this.Inhibited_Beliefs.clear();
//				this.Inhibited_Beliefs.addAll( this.Long_Term_Memory_Maintenance.Get_Inhibited_Beliefs());
//				
//				this.Inhibited_Regions.clear();
//				this.Inhibited_Regions.addAll( this.Long_Term_Memory_Maintenance.Get_Inhibited_Regions());
//				
//				this.Uninhibited_Beliefs.addAll( this.Long_Term_Memory_Maintenance.Get_All_Beliefs() );
//				this.Uninhibited_Beliefs.removeAll( this.Inhibited_Beliefs );
//				
//				this.Uninhibited_Regions.addAll( this.Long_Term_Memory_Maintenance.Get_Regions() );
//				this.Uninhibited_Regions.removeAll( this.Inhibited_Regions );
//			}
			
//			this.Perceptions.clear();
//			this.Stimuli.clear();
//			this.Survived_Stimuli.clear();
//			
//				this.Perceptions_Processing.Execute();
//				this.Perceptions.addAll( this.Perceptions_Processing.Get_Last_Acquired_Perceptions() );
//				
//				this.Information_Extraction.Execute( this.Perceptions, this.Uninhibited_Beliefs, 
//														this.Uninhibited_Regions );
//				this.Stimuli.addAll( this.Information_Extraction.Get_Last_Stimuli() );
//
//				this.Stimulus_Filtering.Execute
//				(
//						this.Stimuli, 
//						this.Saliency_Threshold, this.Attention_Threshold, 
//						this.Inhibited_Regions, this.Inhibited_Beliefs 
//				);
//				
//				this.Survived_Stimuli.addAll( this.Stimulus_Filtering.Get_Survived_Stimuli() );

			if( this.Filtered_Stimuli.size() > 0)
			{
				this.Global_Workspace_Memory_Maintenance.Set_Stimuli_in_GW( this.Filtered_Stimuli );
			}
		}
	}
	
	public TGlobal_Workspace_Memory_Maintenance Get_GW_Memory_Maintenance()
	{
		return this.Global_Workspace_Memory_Maintenance;
	}
	
	public TLong_Term_Memory_Maintenance Get_LT_Memory_Maintenance()
	{
		return this.Long_Term_Memory_Maintenance;
	}
	
//	public void Register_Perception_Function(String Sensor, Memory_Maintenance_Sensor_Function func) 
//    {
//		this.Perceptions_Processing.Register_Perception_Function(Sensor, func);
//    }
//	
//	public boolean Unregister_Perception_Function(String Sensor) 
//    {
//        return this.Perceptions_Processing.Unregister_Perception_Function( Sensor );
//        		
//    }
	
//	public void Add_Sensor(String sensor)
//	{
//		this.Sensors.add( sensor );
//		this.Perceptions_Processing.Add_Sensor( sensor );
//	}
//	
//	public void Add_Sensors(ArrayList<String> sensors)
//	{
//		this.Sensors.addAll(sensors);
//		this.Perceptions_Processing.Add_Sensors( sensors );
//	}
	
//	public void Remove_Sensor_by_Object(String Sensor)
//	{
//		this.Sensors.remove(Sensor);
//		this.Perceptions_Processing.Remove_Sensor_by_Object( Sensor );
//		if (Sensor != null)
//		{
//			this.Unregister_Information_Extraction_Function( Sensor );
//		}
//	}
//	
//	
//	public void Register_Information_Extraction_Function(String Sensor, Information_Extraction_Function func) 
//    {
//       this.Information_Extraction.
//       			Register_Information_Extraction_Function(Sensor, func);
//    }
//    
//    public boolean Unregister_Information_Extraction_Function(String Sensor) 
//    {
//        return this.Information_Extraction.
//        		Unregister_Information_Extraction_Function(Sensor);
//    }
    
//    public ArrayList<Object> Get_Sensors()
//    {
//    	ArrayList<Object> List_sensors = new ArrayList<Object>();
//    	List_sensors.addAll( this.Sensors );
//    	return List_sensors;
//    	
//    }
//    
//    public void Load_Saliencies_for_Stimuli(String filename)
//	{
//    	this.Stimulus_Filtering.Load_Saliencies_for_Stimuli(filename);
//	}
//    
//    public void Register_Stimulus_Filtering_Function(String Stimulus_Type, Stimulus_Filtering_Manipulate_Stimulus_Function func) 
//    {
//        this.Stimulus_Filtering.Register_Function( Stimulus_Type ,  func );
//    }
//    
//    public boolean Unregister_Stimulus_Filtering_Function(String Stimulus_Type) 
//    {
//    	return this.Stimulus_Filtering.Unregister_Function( Stimulus_Type );
//    }
    
    public void Register_Update_Beliefs_Function(String Stimulus_Type, 
			Global_Workspace_Memory_Maintenance_Update_Belief_Function func) 
	{
		this.Global_Workspace_Memory_Maintenance.
								Register_Update_Beliefs_Function(Stimulus_Type, func);
	}
	
	public boolean Unregister_Update_Beliefs_Function(String Stimulus_Type) 
	{
		return this.Global_Workspace_Memory_Maintenance.
							Unregister_Update_Beliefs_Function( Stimulus_Type );
	}
	
	public void Add_Uninhibited_Preconditions(HashSet<TBelief> Pre_Conditions)
    {
		this.Global_Workspace_Memory_Maintenance.Add_Uninhibited_Preconditions( Pre_Conditions );
    }
	
	public void Associate_Functions_to_Inhibited_Desires(
    		ArrayList<TPractical_Desire_Data> Desires_Data)
    {
		this.Global_Workspace_Memory_Maintenance.Associate_Functions_to_Inhibited_Desires( Desires_Data );
    }

}
