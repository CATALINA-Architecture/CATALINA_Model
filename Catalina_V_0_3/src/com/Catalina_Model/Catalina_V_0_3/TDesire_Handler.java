package com.Catalina_Model.Catalina_V_0_3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.function.Function;

import com.Catalina_Model.Catalina_V_0_3.TEndogenous_Desire_Promotion.Get_Unhinibited_Regions_for_Desire;
import com.Catalina_Model.Catalina_V_0_3.TExogenous_Attentional_Desires_Function_Handler.Exogenous_Attentional_Desires_Promotion_Function;
import com.Catalina_Model.Catalina_V_0_3.TGlobal_Workspace.TIRecall_Beliefs;
import com.Catalina_Model.Catalina_V_0_3.TMap_Exogenous_Functions_Handler.Stimulus_To_Epistemic_Function;


public class TDesire_Handler extends TAgent_Base_Thread
{
//	@FunctionalInterface
//    public interface Get_Unhinibited_Regions_for_Desire
//    {
//        ArrayList<TRegion> Execute(TAttentional_Desire Attentional_Desire);
//    }
	
	/**
	 * this is an useful function to assign to "Compute_Unhinibited_Regions_for_Desire".
	 * For each Inhibited Desire, this function has to compute each regions useful to use in
	 * Executive Reasoner 
	 */
//	public Get_Unhinibited_Regions_for_Desire Compute_Unhinibited_Regions_for_Desire;
	
	private TDemotion_Desires Demotion_Desires;
	private TIntention_Deletion Intention_Deletion;
	private TEndogenous_Desire_Promotion Endogenous_Desire_Promotion;
	private TExogenous_Desire_Promotion Exogenous_Desire_Promotion;
	
	private TGlobal_Workspace Global_Workspace;

//	Boolean Updated_Thresholds;
	private Double Saliency_Threshold;
	private Double Attention_Threshold;
	private Boolean Updated_Uninhibited_Data;
	
	public TDesire_Handler(TAgent agent) 
	{
	
//		super(agent, "Executive Desire Promotion Function");
		super(agent, "Executive Desire Handler Function");
		
		this.Global_Workspace = this.Agent.Get_Global_WorkSpace();
		this.Exogenous_Desire_Promotion = new TExogenous_Desire_Promotion( this );
		this.Endogenous_Desire_Promotion = new TEndogenous_Desire_Promotion( this );
		this.Intention_Deletion = new TIntention_Deletion( this );
		this.Demotion_Desires = new TDemotion_Desires( this );
		
		this.Saliency_Threshold = 0.0;
		this.Attention_Threshold = 0.0;
		this.Updated_Uninhibited_Data = false;
	}
	
	@Override
	public void Insert_in_List_Update_Contract()
	{
		//I must to check
		//Message to handle for TIntention_Deletion
		this.Agent.Get_Global_WorkSpace().Insert_for_Update_Contract(TType_Update_Contract.Internal_Signals_Delete_Intention, this);
		this.Agent.Get_Global_WorkSpace().Insert_for_Update_Contract(TType_Update_Contract.Internal_Signals_Satisfied_Intention, this);
		
		//Message to handle for TDowngrading_Desires
		this.Agent.Get_Global_WorkSpace().Insert_for_Update_Contract(TType_Update_Contract.Updated_Uninhibited_Data, this);
		this.Agent.Get_Global_WorkSpace().Insert_for_Update_Contract(TType_Update_Contract.Updated_Thresholds, this);
		
		//Message to handle for TTEndogenous_Desire_Promotion
		this.Agent.Get_Global_WorkSpace().Insert_for_Update_Contract(TType_Update_Contract.Updated_Standing_Desires, this);
		this.Agent.Get_Global_WorkSpace().Insert_for_Update_Contract(TType_Update_Contract.Updated_Beliefs, this);
		this.Agent.Get_Global_WorkSpace().Insert_for_Update_Contract(TType_Update_Contract.Updated_Preconditions, this);
		//Below, these 2 signals are in common with TDowngrading_Desires, I have not to re-insert again
//		this.Agent.Get_Global_WorkSpace().Insert_for_Update_Contract(TType_Update_Contract.Uninhibited_Data, this);
//		this.Agent.Get_Global_WorkSpace().Insert_for_Update_Contract(TType_Update_Contract.Thresholds, this);
		
		//Message to handle for TTEndogenous_Desire_Promotion
		this.Agent.Get_Global_WorkSpace().Insert_for_Update_Contract(TType_Update_Contract.Updated_Stimuli, this);
	}
	
	private void Load_Map_Stimulus_To_Epistemic_Desire_From_File()
	{
		/***
		 * TODO Develop Load_Map_Stimulus_To_Epistemic_Desire_From_File
		 * This function is useful to acquire 
		 */
	}
	
	
	@Override
	public void Execute()
	{
		/**
		 * TO DEFINE:
		 * for each loop, is it better activate exogenous or endogenous desires, or
		 * activate both desire (it they exists)?
		 */
		ArrayList<TAttentional_Desire> Uninhibited_Desires_To_Promote = new ArrayList<TAttentional_Desire>();
		ArrayList<TAttentional_Desire> Inhibited_Desires_To_Promote = new ArrayList<TAttentional_Desire>();
//		TQuadruple_Object Inhibited_Desires_To_Promote = null;
//		while (this.Read_Continue_Thread() )
		{
			Boolean Updated_Thresholds = this.Message_Handler.Read_Value_And_Clear_Updated_Thresholds();
			Updated_Uninhibited_Data = this.Message_Handler.Read_Value_And_Clear_Updated_Unhinibited_Data();
			
			if ( Updated_Thresholds)
			{
				TDouble_Object Thresholds = this.Global_Workspace.Get_Saliency_and_Attention_Thresholds();
				
				this.Saliency_Threshold = (Double) Thresholds.Get_Object_First();
				this.Attention_Threshold = (Double) Thresholds.Get_Object_Second();
			}
			
			/**
			 * INTENTION DELETION
			 * (Capture Two Internal Signals: Delete_Intention and Satisfied_Intention
			 * 
			 * It remove each intention with errata option (errata preconditions and postconditions)
			 * and each intention with satisfied desire 
			 */
			this.Intention_Deletion.Execute();
			
			/**
			 * DEMOTION DERIRES
			 * 
			 * It demotes each desire from Active to Standing. It also removes all related intentions to
			 * demoted desires
			 * 
			 */
			this.Demotion_Desires.Execute();
			
			/**
			 * EXOGENOUS DESIRE PROMOTION
			 * 
			 * It promotes each STIMULUS to Active Desire.
			 * It use a TMap_Stimulus_To_Epistemic_Desires to generate an Epistemic Desire from
			 * a Stimulus.
			 * It is necessary to create for each type of stimulus a function that generates an 
			 * Epistemic Desire by calling Register_Epistemic_Function and UnRegister_Epistemic_Function
			 * of the TExogenous_Desire_Promotion class
			 */
			this.Exogenous_Desire_Promotion.Execute();
			
			/**
			 * ENDOGENOUS DESIRE PROMOTION
			 * 
			 * It promotes each uninhibited standing practical desire that passes Saliency Threshold or 
			 * each inhibited standing practical desire that passes Attention Threshold.
			 * All these desire change its status from Standing to Active.
			 * For each inhibited desires, the class computes each precondition, belief and region to
			 * not inhibit and insert this data in uninhibited data ( preconditions, beliefs, regions and
			 * attentional desires) in Global Workspace.
			 * To compute the regions related to inhibited desires, it is necessary to assign 
			 * to "Compute_Unhinibited_Regions_for_Desire" by using the public function 
			 * "Set_Compute_Unhinibited_Regions_for_Desire_Function". 
			 * "Compute_Unhinibited_Regions_for_Desire" get an attentional desire and compute its regions
			 */
			this.Endogenous_Desire_Promotion.Execute();
			
			//Now I get each Exogenous and Endogenous desires  and I send them to GlobalWorkspace
			Uninhibited_Desires_To_Promote.clear();
			Inhibited_Desires_To_Promote.clear();
			
			//I get the EXOGENOUS Desires, if they exist
			Inhibited_Desires_To_Promote.addAll( 
					this.Exogenous_Desire_Promotion.Get_Exogenous_Epistemic_Desire() );
			
			//I get the Uninhibited ENDOGENOUS Desires, if they exist
			Uninhibited_Desires_To_Promote.addAll( 
					this.Endogenous_Desire_Promotion.Get_Uninhibited_Endogenous_Desires() );

			//I get the Inhibited ENDOGENOUS Desires, if they exist
//			Inhibited_Desires_To_Promote = 
//					this.Endogenous_Desire_Promotion.Get_Inhibited_Endogenous_Desires();
//			
//			this.Global_Workspace.Mark_as_Active_Desires( Uninhibited_Desires_To_Promote, 
//					Inhibited_Desires_To_Promote);
			
			Inhibited_Desires_To_Promote.addAll(
					this.Endogenous_Desire_Promotion.Get_Inhibited_Endogenous_Desires() );
			
			this.Global_Workspace.Mark_as_Active_Desires(
					Uninhibited_Desires_To_Promote, Inhibited_Desires_To_Promote);
		}
		
		
	}
	
	protected TGlobal_Workspace Get_Global_Workspace()
	{
		return this.Global_Workspace;
	}
	
	public void Register_Epistemic_Function(String Stimulus_Type, 
			Stimulus_To_Epistemic_Function func) 
	{
		this.Exogenous_Desire_Promotion.Register_Epistemic_Function( Stimulus_Type, func );
	}
	
	public void Register_Practical_Function(String Stimulus_Type, 
			Exogenous_Attentional_Desires_Promotion_Function func) 
	{
		this.Exogenous_Desire_Promotion.Register_Practical_Function(Stimulus_Type, func);
	}
	
	public void UnRegister_Epistemic_Function(String Stimulus_Type) 
	{
		this.Exogenous_Desire_Promotion.UnRegister_Epistemic_Function( Stimulus_Type ); 
    }
	
	public void UnRegister_Practical_Function(String Stimulus_Type) 
	{
		this.Exogenous_Desire_Promotion.UnRegister_Practical_Function(Stimulus_Type); 
    }
	
	protected Double Get_Saliency_Threshold()
	{
		return this.Saliency_Threshold;
	}
	
	protected Double Get_Attention_Threshold()
	{
		return this.Attention_Threshold;
	}
	
	protected Boolean Get_Updated_Uninhibited_Data()
	{
		return this.Updated_Uninhibited_Data;
	}
	
	private void Set_Compute_Unhinibited_Regions_for_Desire_Function(Get_Unhinibited_Regions_for_Desire Func)
	{
		this.Endogenous_Desire_Promotion.Set_Compute_Unhinibited_Regions_for_Desire_Function(Func);
	}

}
