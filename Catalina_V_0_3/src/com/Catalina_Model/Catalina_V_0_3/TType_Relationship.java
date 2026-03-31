package com.Catalina_Model.Catalina_V_0_3;

public enum TType_Relationship
{
	is,    			//means: "is"
	minor, 			//to comparing a < b
	major,			//to comparing a > b
	equal,			//to comparing a == b
	minor_equal	,	//to comparing a <= b
	major_equal,	//to comparing a >= b
	is_not,			//to comparing a != b
	is_in,			//means: in/at a place/set
	is_out,			//means: not in place/set
	
	//To develop
	is_between,		//means: is in and is nod in place/set/between two numbers...
	when,			//means: the time in which something happens
}
