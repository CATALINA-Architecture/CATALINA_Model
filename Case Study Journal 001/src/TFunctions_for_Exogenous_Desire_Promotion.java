import java.util.ArrayList;
import java.util.HashSet;

import com.Catalina_Model.Catalina_V_0_3.TBelief;
import com.Catalina_Model.Catalina_V_0_3.TDesire_Handler;
import com.Catalina_Model.Catalina_V_0_3.TEpistemic_Desire;
import com.Catalina_Model.Catalina_V_0_3.TExecutive_Memory_Maintenance_Function;
import com.Catalina_Model.Catalina_V_0_3.TGlobal_Workspace;
import com.Catalina_Model.Catalina_V_0_3.TGreen_Desire;
import com.Catalina_Model.Catalina_V_0_3.TPropositional_Formula;
import com.Catalina_Model.Catalina_V_0_3.TQuality_Desire;
import com.Catalina_Model.Catalina_V_0_3.TStimulus;

public class TFunctions_for_Exogenous_Desire_Promotion
{
	private Autonomous_Vehicle_Demo Demo;
	private TCommon_Functions Common_Functions;
	
	public TFunctions_for_Exogenous_Desire_Promotion(Autonomous_Vehicle_Demo demo)
	{
		this.Demo = demo;
		this.Common_Functions = demo.Common_Functions;
	}
	
	public void Add_Functions_To_Exogenous_Desire_Promotion(TDesire_Handler Desire_Handler )
	{
		Desire_Handler.Register_Epistemic_Function("BLTS_Stimulus_Danger_on_the_Route",
				this::Epistemic_Desire_from_BLTS_Stimulus_Danger_on_the_Route);

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
