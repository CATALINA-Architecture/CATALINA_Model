package Cara_Simulation;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class TFunctional_Goal extends TAttentional_Goal{
	
	private transient TBelief_Base Final_State;
	private String Final_State_Name;
	private transient TPredicate Trigger_Condition;
	private String Trigger_Condition_Name;
	private LocalDateTime Finally_Start;
	private LocalDateTime Finally_End;
	private LocalDateTime Global_Start;
	private LocalDateTime Global_End;
	private LocalDateTime Untill_Start;
	private LocalDateTime Until_End;

	
	
	
	public TFunctional_Goal(String name, TBelief_Base final_state, TPredicate trigger_Condition, Double saliency, Double reward, Double relax_Preference,
			ArrayList<TGreen_Goal> list_Green_Goal, ArrayList<TQuality_Goal> list_Quality_Goal,
			LocalDateTime finally_Start, LocalDateTime finally_End, LocalDateTime global_Start, LocalDateTime global_End, 
			LocalDateTime untill_Start, LocalDateTime until_End )
	{
		super(name, saliency, reward, relax_Preference, list_Green_Goal, list_Quality_Goal);
		this.Final_State = final_state;
		this.Trigger_Condition = trigger_Condition;
		this.Final_State_Name = final_state.get_Name();
		this.Trigger_Condition_Name = trigger_Condition.get_Name();
		this.Finally_Start = finally_Start;
		this.Finally_End = finally_End;
		this.Global_Start = global_Start;
		this.Global_End = global_End;
		this.Untill_Start = global_End;
		this.Until_End = until_End;
	}
	
	public TFunctional_Goal(String name, String final_state_Name, String trigger_Condition_Name, Double saliency, Double reward, Double relax_Preference,
			ArrayList<String> list_Green_Goal_Name, ArrayList<String> list_Quality_Goal_Name,
			LocalDateTime finally_Start, LocalDateTime finally_End, LocalDateTime global_Start, LocalDateTime global_End, 
			LocalDateTime untill_Start, LocalDateTime until_End )
	{
		//super(saliency, reward, relax_Preference, null, null);
		super(name, list_Green_Goal_Name, list_Quality_Goal_Name, saliency, reward, relax_Preference );
		this.Final_State = null;
		this.Trigger_Condition = null;
		this.Final_State_Name = final_state_Name;
		this.Trigger_Condition_Name = trigger_Condition_Name;
		this.Finally_Start = finally_Start;
		this.Finally_End = finally_End;
		this.Global_Start = global_Start;
		this.Global_End = global_End;
		this.Untill_Start = global_End;
		this.Until_End = until_End;
	}



	public TPredicate get_Trigger_Condition() {
		return Trigger_Condition;
	}



	public void set_Trigger_Condition(TPredicate trigger_Condition) {
		this.Trigger_Condition = trigger_Condition;
		if (trigger_Condition == null)
			this.Trigger_Condition_Name = "";
		else
		this.Trigger_Condition_Name = trigger_Condition.get_Name(); 
	}



	public TBelief_Base get_Final_State() {
		return Final_State;
	}
	
	public void set_Final_State(TBelief_Base final_state) {
		 this.Final_State = final_state;
		 this.Final_State_Name = final_state.get_Name();
	}
	
	public String get_Final_State_Name() {
		return this.Final_State_Name;
	}
	
	public String get_Trigger_Condition_Name() {
		return this.Trigger_Condition_Name;
	}
	
	public LocalDateTime Get_Finally_Start()
	{
		return this.Finally_Start;
	}
	
	public LocalDateTime Get_Finally_End()
	{
		return this.Finally_End;
	}
	public LocalDateTime Get_Global_Start()
	{
		return this.Global_Start;
	}
	
	public LocalDateTime Get_Global_End()
	{
		return this.Global_End;
	}
	
	public LocalDateTime Get_Until_End()
	{
		return this.Until_End;
	}
	
	

}
