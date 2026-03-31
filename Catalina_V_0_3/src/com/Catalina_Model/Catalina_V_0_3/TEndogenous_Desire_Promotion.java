package com.Catalina_Model.Catalina_V_0_3;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * It promote each Standing Practical Desire to Active Practical Desire.
 * For each desire (un/inhibited desires), it cecks if its preconditions are true, and
 * - For each uninhibited standing desire that passes the saliency threshold of the agent, it promotes this desire
 * to active desire.
 * 
 * - For each inhibited standing desire to promote to uninhibitedactive desire (the saliency of this type
 * of desire pass the Attention Threshold of the agent):
 * -- it uses the function "Compute_Unhinibited_Regions_for_Desire" to compute regions related to
 * the Active desire. It needs to be set by using "Set_Compute_Unhinibited_Regions_for_Desire_Function".
 * -- it compute uninhibited beliefs and preconditions, but not
 * 
 * 
 */

public class TEndogenous_Desire_Promotion 
{
	
	@FunctionalInterface
    public interface Get_Unhinibited_Regions_for_Desire
    {
        ArrayList<TRegion> Execute(TAttentional_Desire Attentional_Desire);
    }
	
	private TDesire_Handler Parent;
	private TGlobal_Workspace Global_Workspace;
	
	private TBoolean_Expression_Evaluetor Expression_Evaluetor;
	private ArrayList<TBelief> Pre_Conditions;
	private ArrayList<TIntention> Selected_Intentions;
	
//	private TQuadruple_Object Inhibited_Endogenous_Desires;
	private HashSet<TAttentional_Desire> Inhibited_Endogenous_Desires;
	private HashSet<TAttentional_Desire> Uninhibited_Endogenous_Desires;
	private Boolean Computed_New_Exogenous_Desire;
	
	private Get_Unhinibited_Regions_for_Desire Compute_Unhinibited_Regions_for_Desire;
	
	private Boolean Executed;
	
	public TEndogenous_Desire_Promotion(TDesire_Handler Owner)
	{
		this.Parent = Owner;
		this.Global_Workspace = this.Parent.Get_Global_Workspace();
		
		this.Expression_Evaluetor = new TBoolean_Expression_Evaluetor();
		this.Pre_Conditions = new ArrayList<TBelief>();
		this.Selected_Intentions = new ArrayList<TIntention>();
		
//		this.Inhibited_Endogenous_Desires = new TQuadruple_Object(); 
		this.Uninhibited_Endogenous_Desires = new HashSet<TAttentional_Desire>();
		this.Inhibited_Endogenous_Desires = new HashSet<TAttentional_Desire>();
		this.Computed_New_Exogenous_Desire = false;
		
	}
	
//	public void Execute_old()
//	{
//		this.Executed = false;
////		try 
////		{
//			Boolean Updated_Standing_Desires = this.Parent.Message_Handler.Read_Value_And_Clear_Updated_Standing_Desires();
//			Boolean Updated_Beliefs = this.Parent.Message_Handler.Read_Value_And_Clear_Updated_Beliefs();
//			Boolean Updated_Preconditions = this.Parent.Message_Handler.Read_Value_And_Clear_Updated_Preconditions();
//			
//			/**
//			 * When Updated_Unhinibited_Beliefs is true, it means that not only the 
//			 * Unhinibited Beliefs are changed but also (unhinibited): 
//			 * Preconditions, Regions and Desires 
//			 */
////			Boolean Updated_Unhinibited_Data= this.Parent.Message_Handler.Read_Value_And_Clear_Updated_Unhinibited_Data();
//			Boolean Updated_Unhinibited_Data = this.Parent.Get_Updated_Uninhibited_Data();
//			
//			
////			System.out.println("dddd - "+LocalDateTime.now());
////			if ( (  Updated_Standing_Desires == true) || (  Updated_Beliefs == true) || 
//////				 ( Updated_Unhinibited_Data == true)
////			   )
////			if ( (  Updated_Standing_Desires == true) || (  Updated_Beliefs == true) || 
////					Updated_Unhinibited_Data || Updated_Preconditions)
//			{
//				
//				this.Inhibited_Endogenous_Desires = null;
//				this.Uninhibited_Endogenous_Desires.clear();
//				
//				
////				TGlobal_Workspace Global_Workspace = this.Agent.Get_Global_WorkSpace();
//				
////				Boolean Updated_Thresholds = this.Parent.Message_Handler.Read_Value_And_Clear_Updated_Thresholds();
//				
////				TDouble_Object Thresholds = Global_Workspace.Get_Saliency_and_Attention_Thresholds();
//				
////				if ( Updated_Thresholds)
////				{
////					TDouble_Object Thresholds = this.Global_Workspace.Get_Saliency_and_Attention_Thresholds();
////					
////					this.Saliency_Threshold = (Double) Thresholds.Get_Object_First();
////					this.Attention_Threshold = (Double) Thresholds.Get_Object_Second();
////				}
//				
//				Double Saliency_Threshold = this.Parent.Get_Saliency_Threshold();
//				Double Attention_Threshold = this.Parent.Get_Attention_Threshold();
//				
//				if(Updated_Unhinibited_Data || Updated_Preconditions)
//				{
//					this.Pre_Conditions.clear();
//					this.Pre_Conditions = this.Global_Workspace.Get_Preconditions();
//					this.Update_Expression_Evaluator(this.Pre_Conditions);
//				}
//				
//				ArrayList<TAttentional_Desire> Desires_To_Promote = new ArrayList<TAttentional_Desire>();
//		
//				Desires_To_Promote.clear();
//		
//				//We said that these are only TPractical Desires
//				ArrayList<TPractical_Desire> Practical_Desires = this.Global_Workspace.Get_All_Practical_Desires();
//
//				ArrayList<TPractical_Desire> Inhibited_Practical_Desires = this.Global_Workspace.Get_Inhibited_Practical_Desires_from_LT();
//				
//				//A goal is inhibited when its conditions are not met, or when its saliency is
//				//lower than the Saliency or Attention Threshold of the Agent(see figure 3 of the journal)
//				ArrayList<TPractical_Desire> Uninhibited_Practical_Desires = new ArrayList<TPractical_Desire>();
//				Uninhibited_Practical_Desires.addAll(Practical_Desires);
//				
//				//Compute Practical_Desires - Inhibited_Practical_Desires
//				Uninhibited_Practical_Desires.removeAll(Inhibited_Practical_Desires);
//				
//				for (TPractical_Desire Practical_Desire : Uninhibited_Practical_Desires) 
//				{
////					if( Practical_Desire.Get_Status_Desire() != TType_Status_Desire.Active )/
//					if( Practical_Desire.Get_Status_Desire() == TType_Status_Desire.Standing )
//					{
//						if(this.Verify_Precondition(Practical_Desire) )
//						{
//							if (Practical_Desire.Get_Saliency() >= Saliency_Threshold)
//							{
//								// I promote g as a desire
//								Desires_To_Promote.add(Practical_Desire);
//							}
//							else
//							{
////								System.out.println("Not Promoted Goal Name: "+Practical_Desire.Get_Name()+ ", because the Saliency Functional Goal is minor then the my Saliency threshold .");
//							}
//						}
//						else
//						{
////							System.out.println("I do Not Promote Goal Name: "+Practical_Desire.Get_Name()+ ", because the Functional Goal's precondition is not true.");
//						}
//					}
//					else
//					{
////						System.out.println("I do Not Promote Goal Name: "+Practical_Desire.Get_Name()+ ", because it is already a Desire");
//					}
////					System.out.println();
//				}
//				
//				ArrayList<TAttentional_Desire> Inhibited_Desires_To_Promote = new ArrayList<TAttentional_Desire>();
//				if(Inhibited_Practical_Desires.size() > 0)
//				{
//					
//				}
//				
//				for (TPractical_Desire Practical_Desire : Inhibited_Practical_Desires) 
//				{
////					if( Practical_Desire.Get_Status_Desire() != TType_Status_Desire.Active )
//					if( Practical_Desire.Get_Status_Desire() == TType_Status_Desire.Standing )
//					{
//						if(this.Verify_Precondition( Practical_Desire ))
//						{
//							if (Practical_Desire.Get_Saliency() >= Attention_Threshold)
//							{
//								Inhibited_Desires_To_Promote.add( Practical_Desire );
//							}
//						}
//					}
//					else
//					{
////						System.out.println("I do Not Promote Goal Name: "+goal.Get_Name()+ ", because it is already a Desire");
////						System.out.println();
//					}
////					System.out.println();
//				}
//				
////				TQuadruple_Object Inhibited_Data_of_Inhibited_Desires = null;
//				this.Inhibited_Endogenous_Desires = null;
//				/**
//				 * If we have some inhibited desire to promote to uninhibited active desire,
//				 * we have to compute all uninhibited data related to these desires for inserting
//				 * this data in uninhibited data in Global Workspace
//				 */
//				if ( Inhibited_Desires_To_Promote.size() > 0)
//				{
//					TDouble_Object Inhibited_Beliefs_of_Inhibited_Desires = null;
//					
//					Inhibited_Beliefs_of_Inhibited_Desires = 
//							this.Recall_Inhibited_Data_for_Activated_Inhibited_Desires( 
//											Inhibited_Desires_To_Promote );
//					
////					ArrayList<TRegion> Inhibited_Regions = 
////							this.Get_Uninhibited_Regions_from_Inhibited_Desires( 
////									Inhibited_Desires_To_Promote );
//					ArrayList<TRegion> Inhibited_Regions = new ArrayList<TRegion>();
//					
////					Inhibited_Data_of_Inhibited_Desires = new TQuadruple_Object();
//					this.Inhibited_Endogenous_Desires = new TQuadruple_Object();
//
//					//1 - I insert the First Object: The Preconditions of the Inhibited Desires
////					Inhibited_Data_of_Inhibited_Desires.Set_Object_First( 
////							Inhibited_Beliefs_of_Inhibited_Desires.Get_Object_First());
//					this.Inhibited_Endogenous_Desires.Set_Object_First( 
//							Inhibited_Beliefs_of_Inhibited_Desires.Get_Object_First());
//					
//					//2 - I insert the Second Object: The Beliefs of the Inhibited Desires
////					Inhibited_Data_of_Inhibited_Desires.Set_Object_Second(
////							Inhibited_Beliefs_of_Inhibited_Desires.Get_Object_Second());
//					this.Inhibited_Endogenous_Desires.Set_Object_Second(
//							Inhibited_Beliefs_of_Inhibited_Desires.Get_Object_Second());
//					
//					//3 - I insert the Third Object: the Inhibited Desires
////					Inhibited_Data_of_Inhibited_Desires.Set_Object_Third(
////							Inhibited_Desires_To_Promote);
//					this.Inhibited_Endogenous_Desires.Set_Object_Third(
//							Inhibited_Desires_To_Promote);
//
//					//4 - I insert the Fourth Object: the Inhibited Desires
////					Inhibited_Data_of_Inhibited_Desires.Set_Object_Fourth(
////							Inhibited_Regions);
//					this.Inhibited_Endogenous_Desires.Set_Object_Fourth(
//							Inhibited_Regions);
//				}
//				
//				this.Uninhibited_Endogenous_Desires.addAll( Desires_To_Promote );
//						
////				this.Global_Workspace.Mark_as_Active_Desires( Desires_To_Promote, 
////																Inhibited_Data_of_Inhibited_Desires);
////				
//				this.Computed_New_Exogenous_Desire = 
//						(this.Uninhibited_Endogenous_Desires.size() > 0 || 
//								Inhibited_Desires_To_Promote.size() > 0);
////								this.Inhibited_Endogenous_Desires != null);
////				if (Desires_To_Promote.size() > 0 || Inhibited_Desires_To_Promote.size() > 0 )
////				{
////					this.Executed = true;
////				}
//				
//			}
//	}
	
	public void Execute()
	{
		this.Executed = false;
//		try 
//		{
			Boolean Updated_Standing_Desires = this.Parent.Message_Handler.Read_Value_And_Clear_Updated_Standing_Desires();
			Boolean Updated_Beliefs = this.Parent.Message_Handler.Read_Value_And_Clear_Updated_Beliefs();
			Boolean Updated_Preconditions = this.Parent.Message_Handler.Read_Value_And_Clear_Updated_Preconditions();
			
			/**
			 * When Updated_Unhinibited_Beliefs is true, it means that not only the 
			 * Unhinibited Beliefs are changed but also (unhinibited): 
			 * Preconditions, Regions and Desires 
			 */
//			Boolean Updated_Unhinibited_Data= this.Parent.Message_Handler.Read_Value_And_Clear_Updated_Unhinibited_Data();
			Boolean Updated_Unhinibited_Data = this.Parent.Get_Updated_Uninhibited_Data();
			
			
//			System.out.println("dddd - "+LocalDateTime.now());
//			if ( (  Updated_Standing_Desires == true) || (  Updated_Beliefs == true) || 
////				 ( Updated_Unhinibited_Data == true)
//			   )
			if ( (  Updated_Standing_Desires ) || (  Updated_Beliefs ) || 
					Updated_Unhinibited_Data || Updated_Preconditions)
			{
				this.Inhibited_Endogenous_Desires.clear();
				this.Uninhibited_Endogenous_Desires.clear();
				
				
//				TGlobal_Workspace Global_Workspace = this.Agent.Get_Global_WorkSpace();
				
//				Boolean Updated_Thresholds = this.Parent.Message_Handler.Read_Value_And_Clear_Updated_Thresholds();
				
//				TDouble_Object Thresholds = Global_Workspace.Get_Saliency_and_Attention_Thresholds();
				
//				if ( Updated_Thresholds)
//				{
//					TDouble_Object Thresholds = this.Global_Workspace.Get_Saliency_and_Attention_Thresholds();
//					
//					this.Saliency_Threshold = (Double) Thresholds.Get_Object_First();
//					this.Attention_Threshold = (Double) Thresholds.Get_Object_Second();
//				}
				
				Double Saliency_Threshold = this.Parent.Get_Saliency_Threshold();
				Double Attention_Threshold = this.Parent.Get_Attention_Threshold();
				
				if(Updated_Unhinibited_Data || Updated_Preconditions)
				{
					this.Pre_Conditions.clear();
					this.Pre_Conditions = this.Global_Workspace.Get_Preconditions();
					this.Update_Expression_Evaluator(this.Pre_Conditions);
				}
				
				ArrayList<TAttentional_Desire> Desires_To_Promote = new ArrayList<TAttentional_Desire>();
		
				Desires_To_Promote.clear();
		
				//We said that these are only TPractical Desires
				ArrayList<TPractical_Desire> Practical_Desires = this.Global_Workspace.Get_All_Practical_Desires();

				ArrayList<TPractical_Desire> Inhibited_Practical_Desires = this.Global_Workspace.Get_Inhibited_Practical_Desires_from_LT();
				
				//A goal is inhibited when its conditions are not met, or when its saliency is
				//lower than the Saliency or Attention Threshold of the Agent(see figure 3 of the journal)
				ArrayList<TPractical_Desire> Uninhibited_Practical_Desires = new ArrayList<TPractical_Desire>();
				Uninhibited_Practical_Desires.addAll(Practical_Desires);
				
				//Compute Practical_Desires - Inhibited_Practical_Desires
				Uninhibited_Practical_Desires.removeAll(Inhibited_Practical_Desires);
				
				for (TPractical_Desire Practical_Desire : Uninhibited_Practical_Desires) 
				{
//					if( Practical_Desire.Get_Status_Desire() != TType_Status_Desire.Active )/
					if( Practical_Desire.Get_Status_Desire() == TType_Status_Desire.Standing )
					{
						if(this.Verify_Precondition(Practical_Desire) )
						{
							if (Practical_Desire.Get_Saliency() >= Saliency_Threshold)
							{
								// I promote g as a desire
								Desires_To_Promote.add(Practical_Desire);
							}
							else
							{
//								System.out.println("Not Promoted Goal Name: "+Practical_Desire.Get_Name()+ ", because the Saliency Functional Goal is minor then the my Saliency threshold .");
							}
						}
						else
						{
//							System.out.println("I do Not Promote Goal Name: "+Practical_Desire.Get_Name()+ ", because the Functional Goal's precondition is not true.");
						}
					}
					else
					{
//						System.out.println("I do Not Promote Goal Name: "+Practical_Desire.Get_Name()+ ", because it is already a Desire");
					}
//					System.out.println();
				}
				
				ArrayList<TAttentional_Desire> Inhibited_Desires_To_Promote = new ArrayList<TAttentional_Desire>();
				if(Inhibited_Practical_Desires.size() > 0)
				{
					
				}
				
				for (TPractical_Desire Practical_Desire : Inhibited_Practical_Desires) 
				{
//					if( Practical_Desire.Get_Status_Desire() != TType_Status_Desire.Active )
//					if( Practical_Desire.Get_Status_Desire() == TType_Status_Desire.Standing )
					if( Practical_Desire.Get_Status_Desire().equals(TType_Status_Desire.Standing) )
					{
						if(this.Verify_Precondition( Practical_Desire ))
						{
							if (Practical_Desire.Get_Saliency() >= Attention_Threshold)
							{
								Inhibited_Desires_To_Promote.add( Practical_Desire );
							}
						}
					}
					else
					{
//						System.out.println("I do Not Promote Goal Name: "+goal.Get_Name()+ ", because it is already a Desire");
//						System.out.println();
					}
//					System.out.println();
				}
				
//				this.Inhibited_Endogenous_Desires = null;
				/**
				 * If we have some inhibited desire to promote to uninhibited active desire,
				 * we have to compute all uninhibited data related to these desires for inserting
				 * this data in uninhibited data in Global Workspace
				 */
				
				this.Uninhibited_Endogenous_Desires.addAll( Desires_To_Promote );
				this.Inhibited_Endogenous_Desires.addAll( Inhibited_Desires_To_Promote );
						
//				this.Global_Workspace.Mark_as_Active_Desires( Desires_To_Promote, 
//																Inhibited_Data_of_Inhibited_Desires);
//				
				this.Computed_New_Exogenous_Desire = 
						(this.Uninhibited_Endogenous_Desires.size() > 0 || 
								this.Inhibited_Endogenous_Desires.size() > 0);
//				if (Desires_To_Promote.size() > 0 || Inhibited_Desires_To_Promote.size() > 0 )
//				{
//					this.Executed = true;
//				}
				
			}
	}
	
	private void Update_Expression_Evaluator(ArrayList<TBelief> Pre_Conditions)
	{
		this.Expression_Evaluetor.Clear();
		for (TBelief Belief: Pre_Conditions)
		{
			Expression_Evaluetor.Set_Variable(Belief.Get_Name(), Belief.Is_Truth());
		}
	}
	
	private Boolean Verify_Precondition(TPractical_Desire Practical_Desire)
	{	
		Boolean Result = true;
		
		//String Formula = Practical_Desire.Get_Trigger_Condition().Get_Formula();
		String Formula = Practical_Desire.Get_Trigger_Condition_Formula();
		if (Practical_Desire != null)
		{
			if (Formula != "")
			{
				Expression_Evaluetor.Clear();
				for(TBelief Belief: Practical_Desire.Get_Trigger_Condition().Get_Beliefs())
				{
					Expression_Evaluetor.Set_Variable(Belief.Get_Name(), Belief.Is_Truth());
//					if(Belief.Get_Name().equals("BL_City_Visited_Paris"))
//					{
//						//Qui
//						System.out.println("Valuto BL_City_Visited_Paris");
//						System.out.println(Belief);
//						System.out.println(Belief.Is_Truth());
//						System.out.println("------------");
//					}
				}
				Result = Expression_Evaluetor.Evaluate(Formula);
			}
		}
//		if (Practical_Desire.Get_Name().equals("PD_Visit_Rome"))
//		{
//			Result=Result;
//		}
		return Result;
	}
	
	/**
	 * Prendere soltanto le precondition e le belief correlate ai desires
	 * @param Inhibited_Desires_To_Promote
	 * @return
	 */
	private TDouble_Object Recall_Inhibited_Data_for_Activated_Inhibited_Desires(ArrayList<TAttentional_Desire> Inhibited_Desires_To_Promote)
	{
		
		TDouble_Object Uninhibited_Data = new TDouble_Object();
		
		ArrayList<TBelief> All_Beliefs = new ArrayList<TBelief>(); 
		All_Beliefs.addAll( this.Global_Workspace.Get_All_Beliefs_from_MM() );
				
		HashSet<TBelief> Uninhibited_Beliefs = new HashSet<TBelief>();
		HashSet<TBelief> Pre_conditions= new HashSet<TBelief>();

		for (TAttentional_Desire Attentional_Desire: Inhibited_Desires_To_Promote)
		{
			switch(Attentional_Desire)
			{
				// PRACTICAL DESIRES
				case TPractical_Desire Practical_Desire ->
				{
					//I insert each belief of the Trigger Condition of the Practical Desire
					Uninhibited_Beliefs.addAll( Practical_Desire.Get_Trigger_Condition().Get_Beliefs() );
					Pre_conditions.addAll( Practical_Desire.Get_Trigger_Condition().Get_Beliefs() );
					
					//I insert each belief of the Final State of the Practical Desire
					Uninhibited_Beliefs.addAll( Practical_Desire.Get_Final_State().Get_Beliefs());
					Uninhibited_Beliefs.addAll( All_Beliefs );
				}
				
				//EPISTEMIC DESIRES
				case TEpistemic_Desire Epistemic_Desire ->
				{
					Uninhibited_Beliefs.add( Epistemic_Desire.Get_Belief() );
					
				}
				default -> throw new IllegalArgumentException("Unexpected value: " + Attentional_Desire);
			}

			//I insert the Constraint of each Green Desires of the Practical Desire
			for(TGreen_Desire Green_Desire: Attentional_Desire.Get_List_Green_Standing_Desire())
			{
				Uninhibited_Beliefs.add( Green_Desire.Get_Constraint().Get_Linked_Belief());
			}
			
			//I insert the Constraint of each Quality Desires of the Practical Desire
			for(TQuality_Desire Quality_Desire: Attentional_Desire.Get_List_Quality_Standing_Desire())
			{
				Uninhibited_Beliefs.add( Quality_Desire.Get_Constraint().Get_Linked_Belief());
			}
			
		}
		
		Uninhibited_Data.Set_Object_First( Pre_conditions);
		Uninhibited_Data.Set_Object_Second( Uninhibited_Beliefs);
		
		return Uninhibited_Data;
	}
	
	private ArrayList<TRegion> Get_Uninhibited_Regions_from_Inhibited_Desires(ArrayList<TAttentional_Desire> Desires)
	{
		HashSet<TRegion> Uninhibited_Regions = new HashSet<TRegion>();

		ArrayList<TRegion> Regions_for_Desire = null;
		for(TAttentional_Desire Desire: Desires)
		{
			if (this.Compute_Unhinibited_Regions_for_Desire != null )
			{
				Regions_for_Desire = this.Compute_Unhinibited_Regions_for_Desire.Execute(Desire);
				if (Regions_for_Desire != null)
				{
					Uninhibited_Regions.addAll( Regions_for_Desire );
				} 
				Regions_for_Desire = null;
			}
			else
			{
				System.out.println("No Functions to compute Unhinibited Regions for all Desire(s)");
			}
		}
		ArrayList<TRegion> Result = new ArrayList<TRegion>( Uninhibited_Regions );
		
		return Result;
	}
	
	protected  void Set_Compute_Unhinibited_Regions_for_Desire_Function(Get_Unhinibited_Regions_for_Desire Func)
	{
		this.Compute_Unhinibited_Regions_for_Desire = Func;
	}
	
	public Boolean Has_New_Exogenous_Desire()
	{
		return this.Computed_New_Exogenous_Desire;
	}
	
	public ArrayList<TAttentional_Desire> Get_Uninhibited_Endogenous_Desires()
	{
		ArrayList<TAttentional_Desire> result = new ArrayList<TAttentional_Desire>();
		result.addAll( this.Uninhibited_Endogenous_Desires );
		this.Uninhibited_Endogenous_Desires .clear();
		return result;
	}
	
//	public TQuadruple_Object Get_Inhibited_Endogenous_Desires()
//	{
//		TQuadruple_Object result = null;
//			
//		if  (this.Inhibited_Endogenous_Desires != null)
//				
//				if(this.Inhibited_Endogenous_Desires.Get_Object_Third() != null) 
//		{
//			result = new TQuadruple_Object();
//	//		Inhibited_Pre_conditions
//			result.Set_Object_First( this.Inhibited_Endogenous_Desires.Get_Object_First() );
//	//		Inhibited_Beliefs
//			result.Set_Object_Second( this.Inhibited_Endogenous_Desires.Get_Object_Second() );
//	//		Inhibited_Desires_To_Promote
//			result.Set_Object_Third( this.Inhibited_Endogenous_Desires.Get_Object_Third() );
//	//		Inhibited_Regions
//			result.Set_Object_Fourth( this.Inhibited_Endogenous_Desires.Get_Object_Fourth() );
//			this.Inhibited_Endogenous_Desires.Clear();
//		}
//		return result; 
//	}
	
	public HashSet<TAttentional_Desire> Get_Inhibited_Endogenous_Desires()
	{
		HashSet<TAttentional_Desire> result = new HashSet<TAttentional_Desire>();
		result.addAll( this.Inhibited_Endogenous_Desires );
		this.Inhibited_Endogenous_Desires.clear();
		
		return result; 
	}
	
	
	
	
	
}
