package com.Catalina_Model.Catalina_V_0_3;

import java.util.HashMap;

import com.Catalina_Model.Catalina_V_0_3.TAction_Execution_Function_Handler.Plan_Executive_Function;
/**
 * This classe Executes an action.
 * If the execution was correct, it sets the Next_ID_Current_Action to next action in the plan of
 * action to do.
 * This class uses a "TPlan_Executive_Function_Handler", an handler to execute a function in accordance
 * to the name of "Action_Name".
 * To add a function related to Action_Name, you have to call the function
 * "Add_Funtion_to_Action_Name", where a functio is a TPlan_Executive_Function type 
 */
public class TOption_Execution
{
	private TExecutive_Switching_Function Parent;
	private TAction_Execution_Function_Handler Plan_Executive_Function_Handler;
	private TAction_Execution Action_Execution;
	private TGlobal_Workspace Global_Workspace; 
	
	public TOption_Execution(TExecutive_Switching_Function own)
	{
		this.Parent = own;
//		this.Global_Workspace = this. 
		
		this.Plan_Executive_Function_Handler = new TAction_Execution_Function_Handler();
//		this.Parent
	}
	
	public TAction_Execution_Result Execute(TIntention Selected_Intention, HashMap<String, TBelief> Beliefs) 
	{
		
		TAttentional_Desire Attentional_Desire = (TAttentional_Desire) Selected_Intention.Get_Active_Desire();
		
		int Selected_Option_ID = Selected_Intention.Get_Selected_Option_Id();
		TOption Selected_Option = Attentional_Desire.Get_List_Options().get( Selected_Option_ID );
		
		int Active_Action_ID = Selected_Option.Get_ID_Current_Action();
		TAction Active_Action = Selected_Option.Get_Plan_Actions().get( Active_Action_ID );
		
//		TAction_Execution_Result result = this.Plan_Executive_Function_Handler.Execute_Function_For_Desire(
//												Active_Action, Beliefs);
		TAction_Execution_Result result = this.Action_Execution.Execute(Active_Action, Beliefs);
		
		if ( result.Get_Result() )
		{
			Selected_Option.Next_ID_Current_Action();
		}
		else
		{
			System.out.println("An error was encountered while executing the Action Function Name: "+Active_Action.Get_Action_Name());		
		}
		
		return result;		
		
	}
	
	public void Set_Data( TAgent value)
	{
		this.Global_Workspace = value.Get_Global_WorkSpace();
		//this.Action_Execution = this.Global_Workspace.Get_Action_Control().Get_Action_Execution();
		this.Action_Execution = value.Get_Action_Control().Get_Action_Execution();
	}
	
	protected TGlobal_Workspace Get_Global_Workspace()
	{
		return this.Global_Workspace;
	}
	
	public void Register_Plan_Execution_Funtion_to_Execute(String Action_Name, Plan_Executive_Function Func )
	{
		this.Plan_Executive_Function_Handler.Register_Function(Action_Name, Func);
	}
	
	public void Register_Plan_Executioon_Funtion_to_Execute(TAction Action, Plan_Executive_Function Func )
	{
		this.Register_Plan_Execution_Funtion_to_Execute( Action.Get_Action_Name(), Func);
	}
	
	public boolean Unregister_Plan_Execution_Function_to_Execute(String Action_Name)
	{
		return this.Plan_Executive_Function_Handler.Unregister_Function( Action_Name );
	}

}
