package Catalina_Simulation;

public enum TType_Relationship
{
	is,    			//means: "is"
	minor, 			//to comparing a < b
	major,			//to comparing a > b
	equal,			//to comparing a == b
	minor_equal	,	//to comparing a <= b
	major_equal,	//to comparing a >= b
	is_not,			//to comparing a != b
	is_in,			//means: in/at a place
	when,			//means: the time in which something happens
	goes,			//means: a player want to go in a destination
	has_traveled,
	visited_by,
	not_visited,
	is_Busy,
	is_Closed,
	is_Temporary_Closed,
	is_Too_Close_To_The_Train,
	is_Crowded,
	has_a_Danger
}