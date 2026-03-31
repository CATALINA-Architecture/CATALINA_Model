package com.Catalina_Model.Catalina_V_0_3;

public enum TType_Status_Desire
{
	/**
	 * Unknown status
	 */
	None,
	/**
	 * The desire has verified preconditions and its saliency is >= than 
	 * Saliency or Attention Threshold of the Agent
	 */
	Active,  
	/**
	 * The desire has not verified preconditions or its saliency is < than 
	 * Saliency or Attention Threshold of the Agent
	 */
	Standing,
	/**
	 * The desire was satisfied. The actions of the option of its related 
	 * intention was executed.
	 */
	Satisfied
}
