package com.Catalina_Model.Catalina_V_0_3;

import java.awt.image.TileObserver;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.EventObject;
import java.util.HashMap;
import java.util.HashSet;

import com.Catalina_Model.Catalina_V_0_3.TAction_Execution_Function_Handler.Plan_Executive_Function;

class TIntention_Compare implements Comparator<TIntention> 
{
    @Override
    public int compare(TIntention Intention0, TIntention Intention1) 
    {
    	Double w0 = Intention0.Get_Active_Desire().Get_Saliency();
    	Double w1 = Intention1.Get_Active_Desire().Get_Saliency();

        return (w0 > w1? -1 : (w0 == w1) ? 0 : 1);
    }
}

/**
 * The Switching Function executes plan actions of selected option of a selected intention.
 * It considers only a specific number of selected intention to execute at time.
 * Now, this number is: 1
 * 
 * It execute an action at time for each selected intention.
 * For each action to execute, it checks if precondition and postconditions are consistent with
 * beliefs of the agent. If yes, it execute the action computed by the Reasoner.
 * 
 * It uses 2 modules: Plan_Advancement_Evaluation and TPlan_Execution.
 * 
 * ............................
 * Plan_Advancement_Evaluation
 * ............................
 * This module is general purpose. The pre and post conditions are predicates in which the Subject is
 * a Belief (with its sublect, relationship and object_complement) and the object_complement is a
 * value or a set or other.
 * This module evaluates if preconditions and postconditions (for an action to execute) are true and 
 * consistent with the beliefs and the reality. It can has 4 different evaluation result:
 * Not_to_Execute, To_Execute, To_Delete, Satisfied.
 * Not_to_Execute: when other selected intentions are executed and the current selected intention is 
 * over the number of the number of selected intention that the agent can executed.
 * To_Execute: the pre and postconditions are correct and the action can be executed
 * To_Delete: the intention must to be delete. In this case, a "Delete Intention" Signal is broadcasted.
 * Satisfied: the intention was satisfied. In this case, a "Satisfied Intention" Signal is broadcasted.
 * 
 * ............................
 * Plan_Execution
 * ............................
 * The Plan Execution module execute the next action to execute in the plan action of the option of the
 * selected intention to pursue.
 * It use an handler "TPlan_Executive_Function_Handler" to execute a function associated to the
 * Action Name. For each Action Name, this module execute one only function (in future, it can execute
 * more function also).
 * To associate a function to an action name, it needs to use "Add_Funtion_to_Execute" and to remove
 * an associated function to an action name, it needs to use "Remove_Function_to_Execute".
 * 
 * 
 */
public class TExecutive_Switching_Function extends TAgent_Base_Thread
{
//	private TAgent Agent; 
	private TGlobal_Workspace Global_Workspace;
	private TOption_Advancement_Evaluation Option_Advancement_Evaluation;
	private TOption_Execution Option_Execution;
	
	private ArrayList<TIntention> Selected_Intentions;
//	private ArrayList<TBelief> Uninhibited_Beliefs;
	private HashMap<String, TBelief> Uninhibited_Beliefs;
	
	private ArrayList<TAttentional_Desire> Satisfied_Attentional_Desires;
	private ArrayList<TIntention> Intentions_to_Delete;
	
	public TExecutive_Switching_Function(TAgent agent) 
	{
	
		super(agent, "Executive Switching Function");
		this.Global_Workspace = agent.Get_Global_WorkSpace();
		
		this.Option_Advancement_Evaluation = new TOption_Advancement_Evaluation( this );
		this.Option_Execution = new TOption_Execution( this );
		this.Option_Execution.Set_Data( this.Agent);
		
		this.Selected_Intentions = new ArrayList<TIntention>();
//		this.Uninhibited_Beliefs = new ArrayList<TBelief>();
		this.Uninhibited_Beliefs = new HashMap<String, TBelief>();
		this.Satisfied_Attentional_Desires = new ArrayList<TAttentional_Desire>();
		this.Intentions_to_Delete = new ArrayList<TIntention>();
	}
	

	@Override
	public void Insert_in_List_Update_Contract()
	{
		//The Siwtching Function executes always its actions.
		//It has not the 
		this.Agent.Get_Global_WorkSpace().Insert_for_Update_Contract(TType_Update_Contract.Internal_Signals, this);
		this.Agent.Get_Global_WorkSpace().Insert_for_Update_Contract(TType_Update_Contract.Updated_Selected_Intentions, this);
		this.Agent.Get_Global_WorkSpace().Insert_for_Update_Contract(TType_Update_Contract.Internal_Signals_Abort_Option, this);
		this.Agent.Get_Global_WorkSpace().Insert_for_Update_Contract(TType_Update_Contract.Updated_Thresholds, this);
		this.Agent.Get_Global_WorkSpace().Insert_for_Update_Contract(TType_Update_Contract.Updated_Uninhibited_Data, this);
		this.Agent.Get_Global_WorkSpace().Insert_for_Update_Contract(TType_Update_Contract.Updated_Beliefs, this);
		this.Agent.Get_Global_WorkSpace().Insert_for_Update_Contract(TType_Update_Contract.Updated_Executed_Action, this);
	}
	
	@Override
	public void Execute()
	{
//		if( this.Message_Handler.Read_Value_And_Clear_Updated_Thresholds() )
		{
//			TDouble_Object Thresholds = this.Global_Workspace.Get_Saliency_and_Attention_Thresholds();
//			if(Thresholds.Get_Object_First().equals(Thresholds.Get_Object_Second()))
//			{
//				System.out.println("Entrambi i dati sono uquali");
//			}
//			else
//			{
//				System.out.println("Entrambi i dati NON sono uquali");
//			}
//			System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++");

			TDouble_Object Thresholds = this.Global_Workspace.Get_Saliency_and_Attention_Thresholds();
			while(!Thresholds.Get_Object_First().equals(Thresholds.Get_Object_Second()))
			{
				Thresholds = this.Global_Workspace.Get_Saliency_and_Attention_Thresholds();
				ArrayList<TBase_Message> Internal_Abort_Option_Signals = new ArrayList<TBase_Message>();
				HashSet<TIntention> Intentions_To_Abort = new HashSet<TIntention>();
				
				Boolean Updated_Beliefs = this.Message_Handler.Read_Updated_Beliefs();
				Boolean Updated_Unhinibited_Beliefs = this.Message_Handler.Read_Updated_Unhinibited_Beliefs();
//				if(this.Message_Handler.Read_Updated_Beliefs() ||
//						this.Message_Handler.Read_Updated_Unhinibited_Beliefs()
//						)
//				if( Updated_Beliefs || Unhinibited_Beliefs)
//				{
//					
//				}
//				while(this.Message_Handler.Read_Updated_Beliefs()||this.Message_Handler.Read_Updated_Unhinibited_Beliefs())
					
		//		while( this.Read_Continue_Thread() )
//				if(this.Message_Handler.Read_Value_And_Clear_Updated_Beliefs() || 
//						//				this.Message_Handler.Read_Updated_Beliefs())
//										this.Message_Handler.Read_Value_And_Clear_Updated_Unhinibited_Data())
				{
					try 
					{
						this.Is_In_Pause();
					} 
					catch (InterruptedException e) 
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					// Reset
//					this.Message_Handler.Read_Value_And_Clear_Updated_Beliefs();
//					this.Message_Handler.Read_Value_And_Clear_Updated_Unhinibited_Data();
					
					//
					if( this.Message_Handler.Read_Value_And_Clear_Updated_Selected_Intentions() )
					{
						this.Selected_Intentions.clear();
						this.Selected_Intentions.addAll( this.Global_Workspace.Get_Selected_Intentions() );
					
						this.Selected_Intentions.size();					
					}
					
					if(this.Message_Handler.Read_Value_And_Clear_Updated_Beliefs() || 
//		//				this.Message_Handler.Read_Updated_Beliefs())
						this.Message_Handler.Read_Value_And_Clear_Updated_Unhinibited_Data())
					{        
						this.Uninhibited_Beliefs.clear();
//						for( TBelief Belief: this.Global_Workspace.Get_UnInhibited_Beliefs())
//						{
////							TBelief Belief_Clone =  Belief.Clone();
//							this.Uninhibited_Beliefs.put( Belief.Get_Name(), Belief );
//						}
						try
						{
							this.Uninhibited_Beliefs.putAll(this.Global_Workspace.Map_Uninhibited_Beliefs);
						}
						catch (Exception e) {
							this.Uninhibited_Beliefs.size();
						}
//						this.Uninhibited_Beliefs.values()
//						System.out.println("Belief Reloaded");
//						this.Global_Workspace.Map_Uninhibited_Beliefs;
//						this.Uninhibited_Beliefs = this.Global_Workspace.Map_Uninhibited_Beliefs;
		//				this.Uninhibited_Beliefs.addAll( this.Global_Workspace.Get_UnInhibited_Beliefs() );
						
//					}//sono dubbioso se chiudere qua questa parentesi o dopo alla riga 352
					
					/**
					 * I capture internal Abort Intention Signal(s).
					 * This is an array of TBase_Message. Each TBase_Message has an array of data (objects) that
					 * are a couple of data: 0- Selected Intention, and 1- Option ID
					 */
					Internal_Abort_Option_Signals.clear();
					Internal_Abort_Option_Signals.addAll( this.Message_Handler.Read_And_Clear_Updated_Internal_Signals_Abort_Option_Message() );
				
					Intentions_To_Abort.clear();
					if( Internal_Abort_Option_Signals.size() > 0)
					{
						for(TBase_Message Message: Internal_Abort_Option_Signals)
						{
							for( Object obj: Message.Read_Data_and_Clean())
							{
								if(obj instanceof TIntention)
								{
									Intentions_To_Abort.add( (TIntention) obj);
								}
							}
						}
						
					}
					
					TType_Intention_Evaluated Execute_Action = null;
					TType_Intention_Evaluated Evaluate_Executed_Action = null;
					this.Satisfied_Attentional_Desires.clear();
					this.Intentions_to_Delete.clear();
					
					this.Option_Advancement_Evaluation.Initialize_Active_Intentions();
					
					TAttentional_Desire Attentional_Desire2 = null;
//					for(TIntention Selected_Intention: Selected_Intentions)
					if( Selected_Intentions.size()>0)
					{
						TIntention Selected_Intention = Selected_Intentions.getFirst();
						Attentional_Desire2 = (TAttentional_Desire ) Selected_Intention.Get_Active_Desire();
						
//						//Prova di funzionamento del Plan Library
//						this.Global_Workspace.Plan_Library.Create_Plan( Selected_Intention );
//						TOption Opzione_Selezionata = Selected_Intention.Get_Selected_Option();
//						ArrayList<TPredicate> Predicati = new ArrayList<TPredicate>();
//						TAction Azione = Opzione_Selezionata.Get_Plan_Actions().getFirst();
//						for(TPredicate Predicato: Azione.Get_Pre_conditions())
//						{
//							Predicati.add(Predicato);
//						}
//						ArrayList<TAction> azioni = new ArrayList<TAction>();
//						azioni.addAll(this.Global_Workspace.Plan_Library.Get_Plans(Attentional_Desire2, Predicati));
//						//Fine prova di funzionamento del Plan Library
						
//						Attentional_Desire2 = (TAttentional_Desire ) Selected_Intention.Get_Active_Desire();
						if (Attentional_Desire2!=null && Attentional_Desire2.Get_List_Options().size() > 0)
						{
							/**
							 * If the Intention is not to abort
							 */
							if(!Intentions_To_Abort.contains( Selected_Intention ))
							{
								/**
								 * If The Selected Intention has an id of the option to pursue
								 */
								TAttentional_Desire Attentional_Desire = (TAttentional_Desire) Selected_Intention.Get_Active_Desire();
//								if ((TAttentional_Desire)Selected_Intention.Get_Active_Desire()).siz > -1)
								if(  Attentional_Desire !=null)
								{
//									Attentional_Desire2 = Attentional_Desire;
									if( Attentional_Desire.Get_List_Options().size() > 0)
									{
										/**
										 * If the plans of the option as almost one action 
										 */
//										if( ((TAttentional_Desire)Selected_Intention.Get_Active_Desire())
										TOption Option = Attentional_Desire. Get_List_Options().
															get(Selected_Intention.Get_Selected_Option_Id());
										if( Option.Get_Plan_Actions().size() > 0 )
										{
											this.Option_Advancement_Evaluation.Evaluate_Pre_Conditions( Selected_Intention, 
													this.Uninhibited_Beliefs );
											Execute_Action = this.Option_Advancement_Evaluation.Get_Evaluation_Pre_Conditions_Result();
										}
										else
										{ 
											Execute_Action = TType_Intention_Evaluated.Not_to_Execute;
										}
									}
									else
									{
										Execute_Action = TType_Intention_Evaluated.Not_to_Execute;
									}
								}
								else
								{
									Execute_Action = TType_Intention_Evaluated.To_Delete;
								}
								
							}
							else
							{
								Execute_Action = TType_Intention_Evaluated.To_Delete;
							}
							
							
							switch( Execute_Action )
							{
								case TType_Intention_Evaluated.To_Execute:
								{
									
									TAttentional_Desire Attentional_Desire = (TAttentional_Desire) Selected_Intention.Get_Active_Desire();
									int Selected_Option_ID = Selected_Intention.Get_Selected_Option_Id();
									TOption Selected_Option = Attentional_Desire.Get_List_Options().get( Selected_Option_ID );
									/**
									 * First: 	Correct Execution: can be True or False, depending by if the
									 * execution of the function was ok
									 * Second: 	List of Beliefs to change
									 * Third: 	Double Object: 
									 * 			First:  List of Practical Desires to insert
									 * 			Second: List of Preconditions to insert
									 * Fourth: 
									 */
									TAction_Execution_Result result = 
											this.Option_Execution.Execute(Selected_Intention, this.Uninhibited_Beliefs);
									
									
										
//										ArrayList<Object> Beliefs_to_Change = new ArrayList<Object>();
//										Beliefs_to_Change.addAll( result.Get_Beliefs_to_Change() );
//										if (Beliefs_to_Change != null)
										if( result.Get_Beliefs_to_Change().size() > 0)
										{
											HashMap<String, Object> Beliefs_To_Change = new HashMap<String, Object>();
											Beliefs_To_Change.putAll( result.Get_Beliefs_to_Change() );
											this.Global_Workspace.Update_Uninhibited_Conscious_Beliefs( 
														Beliefs_To_Change);
										}
										
//										TDouble_Object Practical_Desires_Data = (TDouble_Object) result.Get_Object_Third();
										ArrayList<TPractical_Desire_Data> Other_Practical_Desires_Data = new ArrayList<TPractical_Desire_Data>();
										Other_Practical_Desires_Data.addAll( result.Get_Practical_Desires() );
										
										
										if( Other_Practical_Desires_Data.size() > 0)
										{
//											ArrayList<TAttentional_Desire> Practical_Desires = new ArrayList<TAttentional_Desire>();
//											Practical_Desires.addAll( (ArrayList<TPractical_Desire>) Practical_Desires_Data.Get_Object_First() );
//											
////													;
//											ArrayList<TBelief> Preconditions = new ArrayList<TBelief>();
//											Preconditions.addAll( (ArrayList<TBelief>) Practical_Desires_Data.Get_Object_Second() );
											//Qui
											this.Global_Workspace.Add_Inhibited_Practical_Attentional_Desires(
													Other_Practical_Desires_Data);
										}
										
										ArrayList<TStimulus> Other_Stimuli = new ArrayList<TStimulus>();
										Other_Stimuli.addAll( result.Get_Stimuli() );
										if( Other_Stimuli.size() > 0)
										{
											this.Global_Workspace.Set_Stimuli( Other_Stimuli );
										}
										/*
										 * Now I would handle the case where the last executed action was
										 * the last action to execute?
										 */
//										TAttentional_Desire Attentional_Desire = (TAttentional_Desire) Selected_Intention.Get_Active_Desire();
//										int Selected_Option_ID = Selected_Intention.Get_Selected_Option_Id();
//										TOption Selected_Option = Attentional_Desire.Get_List_Options().get( Selected_Option_ID );
//										if( Selected_Option.is_Satisfied() )
//										{
//											Execute_Action = TType_Intention_Evaluated.Satisfied;
//											Selected_Option.Get_Plan_Actions().size();
//										}
									if ( result.Get_Result() )
									{
										
									}
									//
									else
									{
										
										System.out.println("Result false! Error while executing an action!\nI delete my intention");
										Execute_Action = TType_Intention_Evaluated.To_Delete;
									}
									
									break;
								}
								case TType_Intention_Evaluated.Satisfied:
								{
									this.Satisfied_Attentional_Desires.add( (TAttentional_Desire) 
																		Selected_Intention.Get_Active_Desire() );
									break;
								}
								case TType_Intention_Evaluated.To_Delete:
								{
									System.out.println("Some precondition for an action is bad!\nI delete my intention");
									System.out.println("Intention: "+Selected_Intention.Get_Name()+ " - Attentional Dresire: "+Attentional_Desire2.Get_Name()); 
									this.Intentions_to_Delete.add( Selected_Intention );
									break;
								}
								case TType_Intention_Evaluated.Not_to_Execute:
								{
									break;
								}
							}
							
							if( Execute_Action == TType_Intention_Evaluated.To_Execute)
							{
								HashMap<String, TBelief> Temp_Uninhibited_Beliefs = new HashMap<String, TBelief>();
								while(!this.Message_Handler.Read_Updated_Beliefs() && 
														!this.Message_Handler.Read_Updated_Unhinibited_Beliefs())
								{
//									Temp_Uninhibited_Beliefs.clear();
//									Temp_Uninhibited_Beliefs.putAll(this.Global_Workspace.Map_Uninhibited_Beliefs);
								}
								Temp_Uninhibited_Beliefs.clear();
								Temp_Uninhibited_Beliefs.putAll(this.Global_Workspace.Map_Uninhibited_Beliefs);
								
								this.Option_Advancement_Evaluation.Evaluate_Post_Conditions(Selected_Intention, Temp_Uninhibited_Beliefs);
								Execute_Action = this.Option_Advancement_Evaluation.Get_Evaluation_Post_Conditions_Result();
//								System.out.println("Post_Condition evaluation: "+Execute_Action);
								int oo =3;
							}
							
							
							ArrayList<Object> Data_Signal = new ArrayList<Object>();
							if( this.Satisfied_Attentional_Desires.size() > 0)
							{
								Data_Signal.clear();
				//				this.Global_Workspace.Mark_as_Satisfied_Desires( Satisfied_Attentional_Desires );
								Data_Signal.clear();
								Data_Signal.addAll( this.Selected_Intentions);
				//				
								this.Global_Workspace.Broadcast_Internal_Signals_Messages(
										TType_Update_Contract.Internal_Signals_Satisfied_Intention, 
										getName(), Data_Signal);
							}
//							
							if( this.Intentions_to_Delete.size() > 0)
							{
								Data_Signal.clear();
								Data_Signal.addAll( this.Selected_Intentions);
//								this.Global_Workspace.Broadcast_Internal_Signals_Messages(
//										TType_Update_Contract.Internal_Signals_Abort_Option, 
//										getName(), Data_Signal);
								this.Global_Workspace.Broadcast_Internal_Signals_Messages(
										TType_Update_Contract.Internal_Signals_Delete_Intention, 
										getName(), Data_Signal);
							}
							
						}
					}
					
					
					
				  }//dovrebbe chiudere la riga 181
				}
			}
		}
	}
	
//	protected TAgent Get_Agent()
//	{
//		return this.Agent;
//	}
	
	public void Register_Plan_Execution_Funtion_to_Execute(String Action_Name, Plan_Executive_Function Func )
	{
		this.Option_Execution.Register_Plan_Execution_Funtion_to_Execute( Action_Name, Func);
	}
	
	public boolean Unregister_Plan_Execution_Function_to_Execute(String Action_Name)
	{
		return this.Option_Execution.Unregister_Plan_Execution_Function_to_Execute( Action_Name );
	}
	
}
