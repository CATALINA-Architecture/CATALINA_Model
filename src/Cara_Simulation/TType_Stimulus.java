package Cara_Simulation;

public enum TType_Stimulus {
	
	Ok_Correct_Movement,		// The done move is correct, saliency is low inthis case
	Too_Close_To_The_Train, 	// ahead train is too close to the agent		
	Closed_Route,				// Permanently Closed Route
	Busy_Route,					// Busy Route
	Temporary_Closed_Route 		// A route damaged
}