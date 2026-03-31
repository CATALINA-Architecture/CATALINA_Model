package com.Catalina_Model.Catalina_V_0_3;

public enum TType_Update_Contract 
{
	All_Updates,
	Updated_Predicates,
	Updated_Standing_Desires,
	// Used to send a belief update message (change the content of the belief)
	Updated_Beliefs,
	/**
	 *  Used to send a list of unhinibited belief update message (change the whole list of unhinibited beliefs
	 *  This is used each time the agent computes new Uninhibited Pre_conditions, Beliefs, Desires and Regions
	 *  and new Inhibited Pre_conditions, Beliefs, Desires and Regions, because the message is sent by 
	 *  The Executive Inhibition Function. Some times it can be sent by Endogenous Desire Prmotion (by
	 *  the Desire Handler) when an inhibited desire pass the attention threshold and it become a new
	 *  Uninhibited active desire.
	 */
	Updated_Uninhibited_Data,
	Updated_Active_Desires,
	Updated_Active_Desires_with_Options,
	Updated_Selected_Intentions,
	Updated_Stimuli,
	Updated_Thresholds,
	Internal_Signals,
	// Used when the agent must to abort an option(s) of an intention(s)
	Internal_Signals_Abort_Option,
	// Used when the agent must to delete an intention(s)
	Internal_Signals_Delete_Intention,
	// Used when the agent must to mark as satisfied an intention(s)
	Internal_Signals_Satisfied_Intention,
	//Never used, because Perceptions changes are in same component: TExecutive_Memory_Managment_Function
	Updated_Perceptions,
	//
	Updated_Satisfied_Desires,
	Updated_Critical_Beliefs,
	Updated_Preconditions,
	Updated_Filtered_Stimuli,
	Updated_Action_to_Execute,
	Updated_Executed_Action;
}
