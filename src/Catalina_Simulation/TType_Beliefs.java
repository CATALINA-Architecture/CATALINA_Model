package Catalina_Simulation;

public enum TType_Beliefs {
	
	Belief_Current_Route,				//[ME, is_in, Integer route]
	Belief_Current_City,				//[ME, is_in, station]
	Belief_Current_Step,				//[ME, is_in, Integer step]
	Belief_Destination_City,			//[Station, visited_by, Me] //For Functional Goal
	Belief_Destination_Step,
	Belief_Destination_Route,
	Belief_Next_Route,
	Belief_Next_City,
	Belief_Next_Step,
	Belief_Prev_Route,
	Belief_Prev_City,
	Belief_Prev_Step,
	Belief_Current_Time,				//[Time, is, datetime[yyyy-mm-dd hh-mm-ss]]
	Belief_Temporary_Closed_Route,		//[HashMap<int Route, int duration>, is_Temporary_Closed, null));
	Belief_Closed_Route,				//[ArrayList<int Route>, is_Closed, null));
	Belief_Busy_Route,					//[HashMap<int Route, int duration>, is_Busy, null));
	Belief_Route_Status,				//this is a belief of status of a Route [int Route, is, TType_Route_Status]
	Belief_Visited_City,				//[Station, visited_by, TType_Object_Complement]
	
	Belief_Map,
	Belief_Path_Taken_For_Belief,     	//[String Functional_Goal_name, has_traveled, ArrayList<int Route>]:
										//In Next versione it will be:
										//[String Functional_Goal_name, has_traveled, [ArrayList<TDouble>, ArrayList<TimeStamp>]:
										//TDouble as 2 object: 1-a Route; 2-a LocalDateTime
	Belief_Danger_on_the_Route,			// [int route, has_a_Danger, TType_Danger]
	Belief_Come_Back_to_City,			//[Me, go/goes, int city]
	
	Belief_Number_Players, 			    //[String "Number Players", is, integer all player number, Agent included]
	
	//Types for Stimulus
	Stimulus_Ok_Correct_Movement,		// The done move is correct, saliency is low inthis case
	Stimulus_Too_Close_To_The_Train, 	// ahead train is too close to the agent: [Me, is_Too_Close_To_The_Train, (to) integer step]		
	Stimulus_Closed_Route,				// Permanently Closed Route: [int route, is_Closed, null]
	Stimulus_Busy_Route,				// Busy Route: [int route, is_Busy, ...]
	Stimulus_Temporary_Closed_Route, 	// A route damaged: [int route, is_Temporary_Closed, integer round]
	Stimulus_Crowded_Route,				// A crowded route by many trains [Integer Route, is_Crowded, (integer) number train)
	Stimulus_Route_Status,				// this is an epistemic (but endogenous) goal [Route, null, null]
	Stimulus_Danger_on_the_Route,		// A danger has been perceived on the route, the officer must investigate
										//[TTriple_Object[ TPosition_Train_Coords Precondition_Position, 
										//				   TPosition_Train_Coords Postcondition_Position, null], 
										//->			,has_a_Danger, TType_Danger]
	Stimulus_Irrelevant					//
}