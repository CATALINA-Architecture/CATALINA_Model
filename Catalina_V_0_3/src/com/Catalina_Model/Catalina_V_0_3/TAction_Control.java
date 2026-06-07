package com.Catalina_Model.Catalina_V_0_3;

import java.util.ArrayList;
import java.util.HashMap;

import com.Catalina_Model.Catalina_V_0_3.TAction_Execution_Function_Handler.Plan_Executive_Function;

public class TAction_Control extends TAgent_Base_Thread
{

	protected TGlobal_Workspace Global_Workspace;
	private TAgent Owner;
	private Boolean Result;
	private HashMap<String, TBelief> Map_Beliefs;
	private HashMap<String, TRegion> Map_Regions;
	private TAction_Execution Action_Execution; 
	
//	public TAction_Control(TAgent agent)
//	{
////		Action_Control
//		String Executive_Function_Name = "Action Control";
//		super(agent, Executive_Function_Name);
//		// TODO Auto-generated constructor stub
//		this.Owner = agent;
//		this.Global_Workspace = this.Owner.Get_Global_WorkSpace();
//		this.Result = false;
//		this.Map_Beliefs = new HashMap<String, TBelief>();
//		this.Map_Regions = new HashMap<String, TRegion>();
//		
//	}
	public TAction_Control(TAgent agent) 
	{
		
		super(agent, "Action Control");
		
		this.Owner = agent;
		this.Global_Workspace = this.Owner.Get_Global_WorkSpace();
		this.Result = false;
		this.Map_Beliefs = new HashMap<String, TBelief>();
		this.Map_Regions = new HashMap<String, TRegion>();
		
		this.Action_Execution = new TAction_Execution( this );
	}

	@Override
	public void Execute() 
	{
		// TODO Auto-generated method stub
		Boolean Updated_Action_to_Execute = this.Message_Handler.Read_Value_And_Clear_Updated_Action_to_Execute();
		
		if( Updated_Action_to_Execute)
		{
			
			Boolean Updated_Uninhibited_Data = this.Message_Handler.Read_Value_And_Clear_Updated_Unhinibited_Data();
			Boolean Updated_Beliefs = this.Message_Handler.Read_Value_And_Clear_Updated_Beliefs();
			
			if(Updated_Uninhibited_Data || Updated_Beliefs ) 
			{
				this.Map_Beliefs.clear();
				this.Map_Beliefs.putAll( this.Global_Workspace.Get_Map_Uninhibited_Beliefs() );
				
				this.Map_Regions.clear();
				this.Map_Regions.putAll( this.Global_Workspace.Get_Map_Uninhibited_Regions() );
			}
			
		}
		
	}

	@Override
	public void Insert_in_List_Update_Contract()
	{
		// TODO Auto-generated method stub
		this.Agent.Get_Global_WorkSpace().Insert_for_Update_Contract(TType_Update_Contract.Updated_Action_to_Execute, this);
		this.Agent.Get_Global_WorkSpace().Insert_for_Update_Contract(TType_Update_Contract.Updated_Uninhibited_Data, this);
		this.Agent.Get_Global_WorkSpace().Insert_for_Update_Contract(TType_Update_Contract.Updated_Beliefs, this);
		
	}
	
	public void Register_Plan_Execution_Funtion_to_Execute(String Action_Name, Plan_Executive_Function Func )
	{
		
		this.Action_Execution.Register_Plan_Execution_Funtion_to_Execute(Action_Name, Func);
	}
	
	public void Register_Plan_Executioon_Funtion_to_Execute(TAction Action, Plan_Executive_Function Func )
	{
		this.Action_Execution.Register_Plan_Executioon_Funtion_to_Execute( Action, Func);
	}
	
	public boolean Unregister_Plan_Execution_Function_to_Execute(String Action_Name)
	{
		return this.Action_Execution.Unregister_Plan_Execution_Function_to_Execute( Action_Name );
	}
	
	public TAction_Execution Get_Action_Execution()
	{
		return this.Action_Execution;
	}

}
