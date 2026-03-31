package com.Catalina_Model.Catalina_V_0_3;

import java.util.HashMap;

import com.Catalina_Model.Catalina_V_0_3.TAction_Execution_Function_Handler.Plan_Executive_Function;

public class TAction_Execution
{
	public TAction_Control Parent;
	private TAction_Execution_Function_Handler Plan_Executive_Function_Handler;
	
	public TAction_Execution(TAction_Control own)
	{
		this.Parent = own;
		
		this.Plan_Executive_Function_Handler = new TAction_Execution_Function_Handler();
	}
	
//	public TAction_Execution_Result Execute(TIntention Selected_Intention, HashMap<String, TBelief> Beliefs)
	public TAction_Execution_Result Execute(TAction Active_Action, HashMap<String, TBelief> Beliefs)
	{
		
//		TAttentional_Desire Attentional_Desire = (TAttentional_Desire) Selected_Intention.Get_Active_Desire();
//		
//		int Selected_Option_ID = Selected_Intention.Get_Selected_Option_Id();
//		TOption Selected_Option = Attentional_Desire.Get_List_Options().get( Selected_Option_ID );
//		
//		int Active_Action_ID = Selected_Option.Get_ID_Current_Action();
//		TAction Active_Action = Selected_Option.Get_Plan_Actions().get( Active_Action_ID );
		
		TAction_Execution_Result result = this.Plan_Executive_Function_Handler.Execute_Function_For_Desire(
												Active_Action, Beliefs);
		
//		if ( result.Get_Result() )
//		{
//			Selected_Option.Next_ID_Current_Action();
//		}
//		else
//		{
//			System.out.println("An error was encountered while executing the Action Function Name: "+Active_Action.Get_Action_Name());		
//		}
		
		return result;		
		
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
