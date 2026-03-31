package com.Catalina_Model.Catalina_V_0_3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class TTrieNode 
{
	private HashMap<String, TTrieNode> Layer_Goals;
//	private HashMap<String, TTrieNode> Layer_Pre_Conditions;
	private HashMap<String, HashMap<Object, TTrieNode>> Layer_Pre_Conditions;
	private HashMap<String, TTrieNode> Layer_Quality_of_Solution;
//	private HashMap<String, TTrieNode> Layer_Tasks;
	private ArrayList<TAction> Layer_Tasks;
	private Object Data;
	private String Layer_Final_State;
	private String Name;
	
//	public TTrieNode_old()
//	{
//		this.Layer_Goals = new HashMap<String, TTrieNode>();
//		this.Layer_Pre_Conditions = new HashMap<String, TTrieNode>();
//		this.Layer_Quality_of_Solution = new HashMap<Object, TTrieNode>();
////		this.Layer_Tasks = new LinkedHashMap<String, TAction>();
//		this.Layer_Final_State = "";
//		this.Data = null;
//		this.Action = null;
//		this.Name = "";
//	}
	
	public TTrieNode()
	{
		this.Layer_Goals = null;
		this.Layer_Pre_Conditions = null;
		this.Layer_Quality_of_Solution = null;
		this.Layer_Tasks = null;
		this.Layer_Final_State = null;
		this.Data = null;
//		this.Action = null;
		this.Name = null;
	}
	
	public void Clear()
	{
		this.Layer_Goals = null;
		this.Layer_Pre_Conditions = null;
		this.Layer_Quality_of_Solution = null;
		this.Layer_Tasks = null;
		this.Layer_Final_State = null;
		this.Data = null;
//		this.Action = null;
		this.Name = null;
	}
	
	public HashMap<String, TTrieNode> Get_Layer_Goals()
	{
		return this.Layer_Goals;
	}
	
	public HashMap<String, TTrieNode> Get_Layer_Quality()
	{
		return this.Layer_Quality_of_Solution;
	}
	
	public HashMap<String, HashMap<Object, TTrieNode>> Get_Layer_Preconditions()
	{
		return this.Layer_Pre_Conditions;
	}
	
	public TTrieNode Add_Node_Goal(String Name)
	{
		TTrieNode node = new TTrieNode();
		node.Data = Name;
		node.Name = Name;
		if( this.Layer_Goals == null)
		{
			this.Layer_Goals = new HashMap<String, TTrieNode>();
		}
		this.Layer_Goals.put(Name, node);
		
		return node;
	}
	
	public TTrieNode Add_Node_Pre_Condition(String Name, Object value)
	{
		TTrieNode node = new TTrieNode();
		node.Data = value;
		node.Name = Name;
		if( this.Layer_Pre_Conditions == null)
		{
			this.Layer_Pre_Conditions = new HashMap<String, HashMap<Object,TTrieNode>>();
		}
		HashMap<Object,TTrieNode> temp_Values = new HashMap<Object, TTrieNode>();
		temp_Values.put( value, node);
		this.Layer_Pre_Conditions.put(Name, temp_Values);
		
		return node;
	}
	
	public TTrieNode Add_Node_Quality_Solution(String Name, Object value)
	{
		TTrieNode node = new TTrieNode();
		node.Data = value;
		node.Name = Name;
		if( this.Layer_Quality_of_Solution == null)
		{
			this.Layer_Quality_of_Solution = new HashMap<String, TTrieNode>();
		}
		
		this.Layer_Quality_of_Solution.put(Name, node);
		
		return node;
	}
	
	public Object Get_Data()
	{
		return this.Data;
	}
	
	public String Get_Name()
	{
		return this.Name;
	}
	
	public ArrayList<TAction> Get_Layer_Tasks()
	{
		return this.Layer_Tasks;
	}
	
	public String Get_Layer_Final_State()
	{
		return this.Layer_Final_State;
	}
	
	public void Insert_Tasks(ArrayList<TAction> Actions)
	{
		if( this.Layer_Tasks == null)
		{
			this.Layer_Tasks = new ArrayList<TAction>();
		}
		this.Layer_Tasks.clear();
		for(TAction Action: Actions)
		{
			this.Layer_Tasks.add( Action );
		}
	}
	
	public void Set_Layer_Final_State(String value)
	{
		this.Layer_Final_State = value;
	}
	
//	public TTrieNode Add_Node_Task(String Name, TAction action)
//	{
//		TTrieNode node = new TTrieNode();
//		node.Name = Name;
//		if( this.Layer_Tasks == null)
//		{
//			this.Layer_Tasks = new HashMap<String, TTrieNode>();
//		}
//		node.Action = new TAction();
//		node.Action.Copy_Action( action );
//		
//		this.Layer_Quality_of_Solution.put(Name, node);
//		
//		return node;
//	}
	
	
	
//	Layer_Tasks
	
//	public TTrieNode Create_Goal(String Goal_Name)
//	{
//		TTrieNode result = this.Layer_Goals.get( Goal_Name );
//		if ( result == null)
//		{
//			result = new TTrieNode();
//			result.Name = Goal_Name;
//			this.Layer_Goals.put(Goal_Name, result);
//		}
//		return result;
//	}
//	
//	public TTrieNode Create_Pre_Condition(String Pre_Condition_Name)
//	{
//		TTrieNode result = this.Layer_Pre_Conditions.get( Pre_Condition_Name );
//		if ( result == null)
//		{
//			result = new TTrieNode();
//			result.Name = Pre_Condition_Name;
//			this.Layer_Pre_Conditions.put(Pre_Condition_Name, result);
//		}
//		return result;
//	}
//	
//	public void Add_Quality_Solution(String Quality_Solution_Name, 
//			Object Quality_Solution)
//	{
////		this.Layer_Quality_of_Solution.put( Quality_Solution_Name , Quality_Solution );
//	}
//	
//	public TTrieNode Search_Goal(String Goal_Name)
//	{
//		TTrieNode result = this.Layer_Goals.get( Goal_Name );
//		return result;
//	}
	
	
	
	
	

	
	
}
