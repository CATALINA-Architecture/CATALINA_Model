package Catalina_Simulation;

/**
 * It represents the status of an agent
 */
public enum TAgent_Status {
	
	Not_Active,		// The agent is not active
	Idle,			// The agent is in idle
	Initializing,	// The agent is initializing
	Active,			// The agent is active
	Busy,			// The agent is busy (e.g. it can't answer)
	Suspended		// The agent is suspended 
}