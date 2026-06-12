import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import com.Catalina_Model.Catalina_V_0_3.TAction_Execution_Result;
import com.Catalina_Model.Catalina_V_0_3.TBelief;
import com.Catalina_Model.Catalina_V_0_3.TDesire_Handler;
import com.Catalina_Model.Catalina_V_0_3.TEpistemic_Desire;
import com.Catalina_Model.Catalina_V_0_3.TExecutive_Memory_Maintenance_Function;
import com.Catalina_Model.Catalina_V_0_3.TExogenous_Attentional_Desires_Promotion_Function_Result;
import com.Catalina_Model.Catalina_V_0_3.TGlobal_Workspace;
import com.Catalina_Model.Catalina_V_0_3.TGreen_Desire;
import com.Catalina_Model.Catalina_V_0_3.TPerception;
import com.Catalina_Model.Catalina_V_0_3.TPractical_Desire;
import com.Catalina_Model.Catalina_V_0_3.TPractical_Desire_Data;
import com.Catalina_Model.Catalina_V_0_3.TPredicate;
import com.Catalina_Model.Catalina_V_0_3.TPropositional_Formula;
import com.Catalina_Model.Catalina_V_0_3.TQuality_Desire;
import com.Catalina_Model.Catalina_V_0_3.TStimulus;
import com.Catalina_Model.Catalina_V_0_3.TTemporal_Operator;
import com.Catalina_Model.Catalina_V_0_3.TTemporal_Propositional_Formula;
import com.Catalina_Model.Catalina_V_0_3.TType_Temporal_Operator;
import com.Catalina_Model.Catalina_V_0_3.TGlobal_Workspace.TIRecall_Beliefs;

public class TFunctions_for_Exogenous_Desire_Promotion
{
	private Autonomous_Vehicle_Demo Demo;
	private TCommon_Functions Common_Functions;
	private TFunctions_for_Inhibition_Function Belief_Inhibition_Functions;
	
	
	public TFunctions_for_Exogenous_Desire_Promotion(Autonomous_Vehicle_Demo demo)
	{
		this.Demo = demo;
		this.Common_Functions = demo.Common_Functions;
		this.Belief_Inhibition_Functions = this.Demo.Functions_for_Belief_Inhibition_Function;
		int oo=2;
	}
	
	public void Add_Functions_To_Exogenous_Desire_Promotion(TDesire_Handler Desire_Handler )
	{
		Desire_Handler.Register_Epistemic_Function("BLTS_Stimulus_Danger_on_the_Route",
				this::Epistemic_Desire_from_BLTS_Stimulus_Danger_on_the_Route);
		Desire_Handler.Register_Practical_Function("BLTS_Stimulus_Low_Fuel",
				this::Practical_Desire_from_BLTS_Stimulus_Low_Fuel);
//		Desire_Handler.Register_Practical_Function("ST_Stimulus_Danger_Data_from_TCS",
//		Desire_Handler.Register_Practical_Function("BLTS_Stimulus_Danger_Data_from_TCS",
//				this::Practical_Desire_from_BLTS_Stimulus_Danger_Data_from_TCS);
		

	}
	
	public TExogenous_Attentional_Desires_Promotion_Function_Result 
				Practical_Desire_from_BLTS_Stimulus_Low_Fuel(
						TStimulus Stimulus, 
						HashMap<String, TBelief> Beliefs, TIRecall_Beliefs Recall_Belief
						)
	{
		/**
		 * This method generates a Practical Desire: "Refuel"
		 * Steps to generate a Practical Desire
		 */
		/**
		 *  1) Generate the Practical Desire and its data
		  	2) PRECONDITIONS - For Desire Handler
			3) BELIEFS FOR MEANS-END REASONER FUNCTION - For Means-End Reasoner
			4) REGIONS FOR MEANS-END REASONER FUNCTION - For Means-End Reasoner
			5) Function to Analyze the Practical Desire - For Means-End Reasoner 
			6) Function to generate List Beliefs - For Inhibition Function
			7) Function to generate List Regions - For Inhibition Function 
		 */
		
		TExogenous_Attentional_Desires_Promotion_Function_Result Result =
				new TExogenous_Attentional_Desires_Promotion_Function_Result();
		
////		Integer City_position = Positions.get( 0 );
////		Integer Route_position = Positions.get( 1 );
////		Integer Step_position = Positions.get( 2 );
//		
//		String Come_Back_Name = TCity.values()[City_position].toString();
		
//		TBelief BL_City_to_Refuel = Beliefs.get("BL_City_to_Refuel");
		
		/**
		 * 1) Generate the Practical Desire and its data
		 */
		TBelief BL_Refuel_to_City = Beliefs.get("BL_Refuel_to_City");
		
		ArrayList<TGreen_Desire> List_Green_Desires = new ArrayList<TGreen_Desire>();
		ArrayList<TQuality_Desire> List_Quality_Desires = new ArrayList<TQuality_Desire>();
		
		
		
		Double Saliency = Stimulus.Get_Saliency();//0.9;
		Double Reward = 0.4;
		Double Relax_Preference = 0.4;
		
		TBelief BL_Current_Time = Beliefs.get("BL_Current_Time");
		
		/**
		 * TTemporal_Propositional_Formula 
		 */
		
		LocalDateTime finally_Start = (LocalDateTime) BL_Current_Time.Get_Predicate().Get_Object_Complement();
		LocalDateTime finally_End = finally_Start.plusHours(10);
		
		
		ArrayList<TBelief> Final_State_Beliefs = new ArrayList<TBelief>();
		Final_State_Beliefs.add(BL_Refuel_to_City);
		
		/**
		 * I Create the Temporal_Operator (Finally temporal Operator)
		 */
		TTemporal_Operator Temporal_Operator = new TTemporal_Operator();
		Temporal_Operator.Set_Type_Temporal_Operator( TType_Temporal_Operator.Finally );
		Temporal_Operator.Set_Start_Time( finally_Start );
		Temporal_Operator.Set_End_Time( finally_End );
		
		/**
		 * I Create the TTemporal_Propositional_Formula (Final State)
		 */
		TTemporal_Propositional_Formula Temporal_Propositional_Formula = 
				new TTemporal_Propositional_Formula();
		Temporal_Propositional_Formula.Set_Temporal_Operator( Temporal_Operator );
		Temporal_Propositional_Formula.Set_Beliefs( Final_State_Beliefs );
		Temporal_Propositional_Formula.Set_Formula( BL_Refuel_to_City.Get_Name() );
		Temporal_Propositional_Formula.Set_List_Temporal_Propositional_Formula_Names(null);
		
		/**
		 * 2) PRECONDITIONS - For Desire Handler
		 */
		/**
		 * PRECONDITIONS
		 * For Desire Handler
		 * 
		 * The "Refuel" Practical Desire has one precondition.
		 * The precondition is one, so we can insert only the name as Formula:
		 * "BL_Low_Fuel_Level". The formula will be considered equal to
		 * "BL_Low_Fuel_Level". It can be true or false.
		 *  
		 */
		
		TBelief BL_Low_Fuel_Level = Beliefs.get("BL_Low_Fuel_Level");
		TPropositional_Formula Trigger_Condition = new TPropositional_Formula();
		
		ArrayList<TBelief> List_Trigger_Condition = new ArrayList<TBelief>();
		List_Trigger_Condition.add(BL_Low_Fuel_Level);
		
		Trigger_Condition.Set_Beliefs( List_Trigger_Condition );
		/**
		 * In this case the Formula is simple: just the name of the belief
		 */
		String Formula = BL_Low_Fuel_Level.Get_Name();
		Trigger_Condition.Set_Formula( Formula );
		
		
		/*
		 *  The Practical_Desire Come_Back_to_City has not preconditions.
		 *  The Preconditions for a Practical Desire are the beliefs in Trigger_Condition.
		 *  You can define that while you create the Practical_Desire.
		 */
		
		TPractical_Desire Refuel = 
//				new TPractical_Desire("PD_Come_Back_to_City_"+this.Practical_Desire_Number,
				new TPractical_Desire("Refuel",
						Temporal_Propositional_Formula, Trigger_Condition, 
						Saliency, Reward, 
						Relax_Preference, List_Green_Desires, 
						List_Quality_Desires, 
						finally_Start, finally_End, 
						null, null, null, null, null, null);

		/**
		 * 3) BELIEFS FOR MEANS-END REASONER FUNCTION - For Means-End Reasoner
		 */
		
		/**
		 * BELIEFS FOR MEANS-END REASONER FUNCTION
		 * For Reasoner
		 * 
		 * The "Come_Back" Practical Desire has Beliefs for Mean-End Reasoner. 
		 * These Beliefs are useful to compute all possible option to pursue for an intention.  
		 * So I register this Beliefs for Mean-End Reasoner in the Agent.
		 */
		
		/*
		 * We calculate beliefs for the Reasoner only once
		 */
		
		HashSet<String> Reasoner_Beliefs_Names = new HashSet<String>();
		Reasoner_Beliefs_Names.addAll( this.Demo.Functions_for_Belief_Inhibition_Function.
										Get_Desires_Beliefs_Functions().
											Reasoner_Beliefs_For_Refuel(Refuel));
		Refuel.Set_Beliefs_Name_for_Reasoner( Reasoner_Beliefs_Names );

		/**
		 * 4) REGIONS FOR MEANS-END REASONER FUNCTION - For Means-End Reasoner
		 */
		
		/**
		 * REGIONS FOR MEANS-END REASONER FUNCTION
		 * For Reasoner
		 * 
		 * * The "Come_Back" Practical Desire has Regions for Mean-End Reasoner. 
		 * These Regions are useful to compute all possible option to pursue for an intention.  
		 * So I register this Beliefs for Mean-End Reasoner in the Agent.
		 */
		/*
		 *  We calculate regions for the Reasoner only once
		 */
		HashSet<String> Reasoner_Regions_Names = new HashSet<String>();

		Reasoner_Regions_Names.addAll( this.Demo.Functions_for_Belief_Inhibition_Function.
				Get_Desires_Regions_Functions().Reasoner_Get_Regions_for_Refuel(Beliefs));
		Refuel.Set_Regions_Name_for_Reasoner( Reasoner_Regions_Names );
		
		/**
		 * Now, we have to associate the Function for:
		 * 5) Executive Means-End Reasoner Function to Analyze the Practical Desire
		 * 6) Beliefs for Executive Inhibition Function
		 * 7) Regions for Executive Inhibition Function 
		 */
		
//		/**
//		 *  We create the store the Practical Desire Refuel
//		 */

		
		
//		ArrayList<TPractical_Desire> Practical_Desires = new ArrayList<TPractical_Desire>();
//		Practical_Desires.add( Come_Back_to_City );
		TPractical_Desire_Data Practical_Desire_Data = new TPractical_Desire_Data();
		Practical_Desire_Data.Set_Practical_Desire( Refuel );

		
		/**
		 * 5) Function to Analyze the Practical Desire - For Means-End Reasoner 
		 */
		
		/**
		 * Function to invoke by MEANS-END REASONER FUNCTION
		 * 
		 * The "Come_Back" Function to invoke to compute the option for the intention
		 * to go back in previous city (the last visited city )
		 */
		
		//I insert Means End Reasoner Function
		Practical_Desire_Data.Set_Means_End_Reasoner_Function(
				this.Demo.Means_End_Reasoner_Function::Reasoner_Function_for_Refuel
//				this.Demo.Means_End_Reasoner_Function::Reasoner_Function_for_Refuel
//					Get_Desires_Executive_Funtions()::Means_End_Function_Come_Back
				);
		
		/**
		 * 6) Beliefs for Executive Inhibition Function
		 */
		
		/**
		 * Function to BELIEFS for Executive Inhibition Function 
		 */
		
		//I insert BELIEFS for Executive Inhibition Function 
		Practical_Desire_Data.Add_Beliefs_Inhibition_Functions(
				this.Belief_Inhibition_Functions.
					Get_Desires_Beliefs_Functions()::Inhibition_Beliefs_for_Refuel);
		
		/**
		 * 7) Regions for Executive Inhibition Function 
		 */
		
		/**
		 * Function to REGIONS for Executive Inhibition Function 
		 */
		//I insert REGIONS for Executive Inhibition Function
		Practical_Desire_Data.Add_Regions_Inhibition_Functions( 
				this.Belief_Inhibition_Functions.
					Get_Desires_Regions_Functions()::Inhibition_Regions_for_Refuel);
		
		ArrayList<TPractical_Desire_Data> Practical_Desires_Data = new ArrayList<TPractical_Desire_Data>();
		Practical_Desires_Data.add( Practical_Desire_Data );
		
		Result.Add_Practical_Desires_Data( Practical_Desires_Data );
		Result.Set_Result(true);
		
		
		return Result;
	}
	
	public TEpistemic_Desire Epistemic_Desire_from_BLTS_Stimulus_Danger_on_the_Route(
				TGlobal_Workspace Global_Workspace, TStimulus Stimulus)
	{
//		ArrayList<String> Trigger_Condition_Names = new ArrayList<String>();
		ArrayList<String> Green_Names = new ArrayList<String>();
		ArrayList<String> Quality_Names = new ArrayList<String>();
		String Type_Stimulus;
//		String Trigger_Condition_Formula = "";
		Double Saliency = Stimulus.Get_Saliency();
		Double Reward = 0.3;
		Double Relax_Preference = 0.3;
		TPropositional_Formula trigger_Condition = new TPropositional_Formula();
		
		ArrayList<TGreen_Desire> List_Green_Desire = new ArrayList<TGreen_Desire>();
		List_Green_Desire.addAll( Global_Workspace.Get_Green_Desires_by_Names_from_MM( Green_Names ));
		
		ArrayList<TQuality_Desire> List_Quality_Desire = new ArrayList<TQuality_Desire>();
		List_Quality_Desire.addAll( Global_Workspace.Get_Quality_Desires_by_Names_from_MM( Quality_Names ));
		
		
//		TEpistemic_Desire Epistemic_Desire = new TEpistemic_Desire("EPD_Stimulus_Danger_on_the_Route",
		TEpistemic_Desire Epistemic_Desire = new TEpistemic_Desire("Inquiry_Estimated_Delay",
				Stimulus, trigger_Condition, Saliency, Reward, Relax_Preference, 
				List_Green_Desire, List_Quality_Desire, null,null);
		
		/*
		 * I set the list of Beliefs for Means-End Reasoner Function
		 */
		HashSet<String> Reasoner_Belief_Names = new HashSet<String>();
		
		Reasoner_Belief_Names.addAll(
				this.Demo.Functions_for_Belief_Inhibition_Function.Get_Desires_Beliefs_Functions().
			Reasoner_Beliefs_For_Stimulus_Danger_on_the_Route(Epistemic_Desire) );
		
		Epistemic_Desire.Set_Beliefs_Name_for_Reasoner( Reasoner_Belief_Names );
		
		/*
		 * I set the list of Regions for Means-End Reasoner Function.
		 * In this case study, I have not regions for this stimulus.
		 * So I show how to insert this list but I don't execute this code
		 */
//		HashSet<String> Reasoner_Region_Names = new HashSet<String>();
//		Epistemic_Desire.Set_Regions_Name_for_Reasoner( Reasoner_Region_Names );
			
		/**
		 * The Functions for Means-End Reasoner, and computing beliefs and Regions by
		 * Executive Inhibition Functions are associated with the Stimulus Type only
		 * once during the agent's lifecycle, because they do not need to be 
		 * associated with the name but only with the type. 
		 * In this case study they are associate in "Add_Lists_for_Epistemic_Desires" method
		 * of the TFunctions_for_Inhibition_Function.
		 * 
		 */
		
		return Epistemic_Desire;
	}
	
}
