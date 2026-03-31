package com.Catalina_Model.Catalina_V_0_3;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class TBase_Message_Handler {
	
	// Update Messages
	
	//sent when Predicates change
	private TBase_Message Update_Predicates_Message;
	//sent when Beliefs change (the content of the Beliefs)
	private TBase_Message Update_Beliefs_Message;
	//sent when the List of Uninhibited Beliefs change (after the inhibition execution)
	private TBase_Message Update_Unhinibited_Data_Message;
	private TBase_Message Update_Standing_Desires_Message;
	private TBase_Message Update_Active_Desires_Message;
	private TBase_Message Update_Active_Desires_With_Options_Message;
	private TBase_Message Update_Stimuli_Message;
	private TBase_Message Update_Selected_Intentions_Message;
	private TBase_Message Update_Thresholds_Message;
	private TBase_Message Update_Internal_Signals_Message;
//	private TBase_Message Update_Internal_Signals_Abort_Option_Message;
//	private TBase_Message Update_Internal_Signals_Delete_Intention_Message;
//	private TBase_Message Update_Internal_Signals_Satisfied_Intention_Message;
	
	private TGeneric_Protected_List<TBase_Message> Update_Internal_Signals_Abort_Option_Messages;
	private TGeneric_Protected_List<TBase_Message> Update_Internal_Signals_Delete_Intention_Messages;
	private TGeneric_Protected_List<TBase_Message> Update_Internal_Signals_Satisfied_Intention_Messages;
	private TBase_Message Update_Perceptions_Message;
	private TBase_Message Update_Satisfied_Desires_Message;
	private TBase_Message Update_Important_Beliefs_Message;
	private TBase_Message Update_Preconditions_Message;
	private TBase_Message Update_Filtered_Stimuli_Message;
	private TBase_Message Update_Action_to_Execute_Message;
	private TBase_Message Update_Executed_Action_Message;
	
	

	public TBase_Message_Handler() 
	{
		// Update Messages
		this.Update_Predicates_Message = new TBase_Message();
		this.Update_Beliefs_Message = new TBase_Message();
		this.Update_Unhinibited_Data_Message = new TBase_Message();
		this.Update_Standing_Desires_Message = new TBase_Message();
		this.Update_Active_Desires_Message = new TBase_Message();
		this.Update_Active_Desires_With_Options_Message = new TBase_Message();
		this.Update_Stimuli_Message = new TBase_Message();
		this.Update_Selected_Intentions_Message = new TBase_Message();
		this.Update_Thresholds_Message = new TBase_Message();
		this.Update_Internal_Signals_Message = new TBase_Message();
		this.Update_Perceptions_Message = new TBase_Message();
		this.Update_Satisfied_Desires_Message = new TBase_Message();
		this.Update_Important_Beliefs_Message = new TBase_Message();
		this.Update_Preconditions_Message = new TBase_Message();
		this.Update_Filtered_Stimuli_Message = new TBase_Message();
		this.Update_Action_to_Execute_Message = new TBase_Message();
		this.Update_Executed_Action_Message = new TBase_Message();
		
		
//		this.Update_Internal_Signals_Abort_Option_Message = new TBase_Message();
//		this.Update_Internal_Signals_Delete_Intention_Message = new TBase_Message();
//		this.Update_Internal_Signals_Satisfied_Intention_Message = new TBase_Message();
		
		this.Update_Internal_Signals_Abort_Option_Messages = new TGeneric_Protected_List<TBase_Message>();
		this.Update_Internal_Signals_Delete_Intention_Messages = new TGeneric_Protected_List<TBase_Message>();
		this.Update_Internal_Signals_Satisfied_Intention_Messages = new TGeneric_Protected_List<TBase_Message>();
		
	}
	
	/////////
	/////////
	// READ Update Messages Area
	/////////
	/////////
	
	
	
	public Boolean Read_Updated_Predicates() 
	{
		return this.Update_Predicates_Message.Read_Value();
	}
	
	public Boolean Read_Updated_Beliefs() 
	{
		return this.Update_Beliefs_Message.Read_Value();
	}
	
	public Boolean Read_Updated_Unhinibited_Beliefs() 
	{
		return this.Update_Unhinibited_Data_Message.Read_Value();
	}
	
	public Boolean Read_Updated_Standing_Desires() 
	{
		return this.Update_Standing_Desires_Message.Read_Value();
	}
	
	public Boolean Read_Value_Updated_Active_Desires() 
	{
		return this.Update_Active_Desires_Message.Read_Value();
	}
	
	public Boolean Read_Value_Updated_Active_Desires_With_Options() 
	{
		return this.Update_Active_Desires_With_Options_Message.Read_Value();
	}
	
	public Boolean Read_Value_Updated_Stimuli() 
	{
		return this.Update_Stimuli_Message.Read_Value();
	}
	
	public Boolean Read_Value_Updated_Selected_Intentions() 
	{
		return this.Update_Selected_Intentions_Message.Read_Value();
	}
	
	public Boolean Read_Value_Updated_Thresholds() 
	{
		return this.Update_Thresholds_Message.Read_Value();
	}
	
	public Boolean Read_Value_Updated_Internal_Signals()
	{
		return this.Update_Internal_Signals_Message.Read_Value();
	}
	
	public Boolean Read_Value_Updated_Perceptions()
	{
		return this.Update_Perceptions_Message.Read_Value();
	}

	public Boolean Read_Value_Updated_Satisfied_Desires()
	{
		return this.Update_Satisfied_Desires_Message.Read_Value();
	}
	
	public Boolean Read_Value_Updated_Important_Beliefs()
	{
		return this.Update_Important_Beliefs_Message.Read_Value();
	}
	
	public Boolean Read_Value_Updated_Preconditions()
	{
		return this.Update_Preconditions_Message.Read_Value();
	}
	
	
	public Boolean Read_Value_Update_Filtered_Stimuli()
	{
		return this.Update_Filtered_Stimuli_Message.Read_Value();
	}
	
	public Boolean Read_Value_Updated_Action_to_Execute() 
	{
		return this.Update_Action_to_Execute_Message.Read_Value();
	}
	
	
	public Boolean Read_Value_Update_Executed_Action() 
	{
		return this.Update_Executed_Action_Message.Read_Value();
	}
	
	
	
	public ArrayList<TBase_Message> Read_Updated_Internal_Signals_Abort_Option_Messages()
	{
		return this.Update_Internal_Signals_Abort_Option_Messages.Read();
	}
	
	public ArrayList<TBase_Message> Read_Updated_Internal_Signals_Delete_Intention_Messages()
	{
		return this.Update_Internal_Signals_Delete_Intention_Messages.Read();
	}
	
	public ArrayList<TBase_Message> Read_Updated_Internal_Signals_Satisfied_Intention_Messages()
	{
		return this.Update_Internal_Signals_Satisfied_Intention_Messages.Read();
	}
	

	
	
	//////////////////////
	//////////////////////
	//// Read_And_Clean (It means: Read and Write)
	//////////////////////
	//////////////////////
	
	
	
	
	public Boolean Read_Value_And_Clear_Updated_Predicates() 
	{
		return this.Update_Predicates_Message.Read_Value_and_Clear();
	}
	
	public Boolean Read_Value_And_Clear_Updated_Beliefs() 
	{
		return this.Update_Beliefs_Message.Read_Value_and_Clear();
	}
	
	public Boolean Read_Value_And_Clear_Updated_Unhinibited_Data() 
	{
		return this.Update_Unhinibited_Data_Message.Read_Value_and_Clear();
	}
	
	public Boolean Read_Value_And_Clear_Updated_Standing_Desires()
	{
		return this.Update_Standing_Desires_Message.Read_Value_and_Clear();
	}
	
	public Boolean Read_Value_And_Clear_Updated_Active_Desires() 
	{
		return this.Update_Active_Desires_Message.Read_Value_and_Clear();
	}
	
	public Boolean Read_Value_And_Clear_Updated_Active_Desires_With_Options() 
	{
		return this.Update_Active_Desires_With_Options_Message.Read_Value_and_Clear();
	}
	
	public Boolean Read_Value_And_Clear_Updated_Stimuli() 
	{
		return this.Update_Stimuli_Message.Read_Value_and_Clear();
	}
	
	public Boolean Read_Value_And_Clear_Updated_Selected_Intentions() 
	{
		return this.Update_Selected_Intentions_Message.Read_Value_and_Clear();
	}
	
	public Boolean Read_Value_And_Clear_Updated_Thresholds()
	{
		return this.Update_Thresholds_Message.Read_Value_and_Clear();
	}
	
	public Boolean Read_Value_And_Clear_Updated_Internal_Signals() 
	{
		return this.Update_Internal_Signals_Message.Read_Value_and_Clear();
	}
	
	public Boolean Read_Value_And_Clear_Updated_Perceptions() 
	{
		return this.Update_Perceptions_Message.Read_Value_and_Clear();
	}
	
	public Boolean Read_Value_And_Clear_Updated_Satisfied_Desires() 
	{
		return this.Update_Satisfied_Desires_Message.Read_Value_and_Clear();
	}
	
	public Boolean Read_Value_And_Clear_Updated_Important_Beliefs() 
	{
		return this.Update_Important_Beliefs_Message.Read_Value_and_Clear();
	}
	
	public Boolean Read_Value_And_Clear_Updated_Preconditions() 
	{
		return this.Update_Preconditions_Message.Read_Value_and_Clear();
	}
	
	public Boolean Read_Value_And_Clear_Updated_Filtered_Stimuli() 
	{
		return this.Update_Filtered_Stimuli_Message.Read_Value_and_Clear();
	}
	
	public Boolean Read_Value_And_Clear_Updated_Action_to_Execute() 
	{
		return this.Update_Action_to_Execute_Message.Read_Value_and_Clear();
	}
	
	
	public Boolean Read_Value_And_Clear_Updated_Executed_Action() 
	{
		return this.Update_Executed_Action_Message.Read_Value_and_Clear();
	}
	
	
	
	public ArrayList<TBase_Message> Read_And_Clear_Updated_Internal_Signals_Abort_Option_Message()
	{
		return this.Update_Internal_Signals_Abort_Option_Messages.Read_And_Clear();
	}
	
	public ArrayList<TBase_Message> Read_And_Clear_Updated_Internal_Signals_Delete_Intention_Messages()
	{
		return this.Update_Internal_Signals_Delete_Intention_Messages.Read_And_Clear();
	}
	
	public ArrayList<TBase_Message> Read_And_Clear_Updated_Internal_Signals_Satisfied_Intention_Messages()
	{
		return this.Update_Internal_Signals_Satisfied_Intention_Messages.Read_And_Clear();
	}
	
	
	/////////
	/////////
	// Write Update Messages Area
	/////////
	/////////
	
	
	
	public void Write_Value_Updated_Predicates() 
	{
		this.Update_Predicates_Message.Write_Value();
	}
	
	public void Write_Value_Updated_Beliefs() 
	{
		this.Update_Beliefs_Message.Write_Value();
	}
	
	public void Write_Value_Updated_Unhinibited_Beliefs() 
	{
		this.Update_Unhinibited_Data_Message.Write_Value();
	}
	
	public void Write_Value_Updated_Standing_Desires() 
	{
		this.Update_Standing_Desires_Message.Write_Value();
	}
	
	public void Write_Value_Updated_Active_Desires() 
	{
		this.Update_Active_Desires_Message.Write_Value();
	}
	
	public void Write_Value_Updated_Active_Desires_With_Options() 
	{
		this.Update_Active_Desires_With_Options_Message.Write_Value();
	}
	
	public void Write_Value_Updated_Stimuli() 
	{
		this.Update_Stimuli_Message.Write_Value();
	}
	
	public void Write_Value_Updated_Selected_Intentions() 
	{
		this.Update_Selected_Intentions_Message.Write_Value();
	}
	
	public void Write_Value_Updated_Thresholds() 
	{
		this.Update_Thresholds_Message.Write_Value();
	}
	
	public void Write_Value_Updated_Internal_Signals() 
	{
		this.Update_Internal_Signals_Message.Write_Value();
	}
	
	public void Write_Value_Updated_Perceptions() 
	{
		this.Update_Perceptions_Message.Write_Value();
	}
	
	public void Write_Value_Updated_Satisfied_Desires() 
	{
		this.Update_Satisfied_Desires_Message.Write_Value();
	}
	
	public void Write_Value_Updated_Important_Beliefs() 
	{
		this.Update_Important_Beliefs_Message.Write_Value();
	}
	
	public void Write_Value_Updated_Preconditions() 
	{
		this.Update_Preconditions_Message.Write_Value();
	}
	
	public void Write_Value_Updated_Filtered_Stimuli() 
	{
		this.Update_Filtered_Stimuli_Message.Write_Value();
	}
	
	public void Write_Value_Updated_Action_to_Execute() 
	{
		this.Update_Action_to_Execute_Message.Write_Value();
	}
	
	
	public void Write_Value_Updated_Executed_Action() 
	{
		this.Update_Executed_Action_Message.Write_Value();
	}
	
	
//	public void Write_Value_Updated_Internal_Signals_Abort_Option_Message()
//	{
//		this.Update_Internal_Signals_Abort_Option_Messages.Write_Value();
//	}
//	
//	public void Write_Value_Updated_Internal_Signals_Delete_Intention_Messages()
//	{
//		this.Update_Internal_Signals_Delete_Intention_Messages.Write_Value();
//	}
//	
//	public void Write_Value_Updated_Internal_Signals_Satisfied_Intention_Messages()
//	{
//		this.Update_Internal_Signals_Satisfied_Intention_Messages.Write_Value();
//	}
	
	
	
	public void Add_Value_Updated_Internal_Signals_Abort_Option_Message(String Sender_Name,
			ArrayList<Object> Data)
	{
		TBase_Message Internal_Signal = new TBase_Message();
		Internal_Signal.Write_Internal_Signal(Sender_Name, Sender_Name, Data);
		
		this.Update_Internal_Signals_Abort_Option_Messages.Add( Internal_Signal );
	}
	
	public void Add_Value_Updated_Internal_Signals_Delete_Intention_Message(String Sender_Name,
			ArrayList<Object> Data)
	{
		TBase_Message Internal_Signal = new TBase_Message();
		Internal_Signal.Write_Internal_Signal(Sender_Name, Sender_Name, Data);
		
		this.Update_Internal_Signals_Delete_Intention_Messages.Add( Internal_Signal );
	}
	
	public void Add_Value_Updated_Internal_Signals_Satisfied_Intention_Message(String Sender_Name,
			ArrayList<Object> Data)
	{
		TBase_Message Internal_Signal = new TBase_Message();
		Internal_Signal.Write_Internal_Signal(Sender_Name, Sender_Name, Data);
		
		this.Update_Internal_Signals_Satisfied_Intention_Messages.Add( Internal_Signal );
	}
}
