package com.Catalina_Model.Catalina_V_0_3;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

import javax.swing.text.StyledEditorKit.BoldAction;

import com.Catalina_Model.Catalina_V_0_3.BooleanExpressionSorter.SortedResult;

public class TGLITTER_Trie 
{
	private TTrieNode Root;
	
	public TGLITTER_Trie()
	{
		this.Root = new TTrieNode();
	}
	
	public ArrayList<TAction> Get_Plans(TAttentional_Desire Attentional_Desire,
			ArrayList<TPredicate> Preconditions 
			)
	{
		ArrayList<TAction> result = new ArrayList<TAction>();
		
		if( Attentional_Desire instanceof TEpistemic_Desire)
		{
			return result;
		}
		TPractical_Desire Desire = (TPractical_Desire) Attentional_Desire;
		
		String Final_State = Desire.Get_Final_State().Get_Formula();
		ArrayList<String> L_Goals = new ArrayList<String>();
		HashMap<String, Object> L_pre_cond = new HashMap<String, Object>();
		HashMap<String, Object> L_Quality = new HashMap<String, Object>();
		
		//Now, I start to search some plans in Plan Library
		SortedResult Final_Predicates = BooleanExpressionSorter.
				sortExpression(Final_State);
		
		/**
		 * I get Final State Predicates in Canonical Form (Sum of Products, SoP)
		 */
		for (int i = 0; i < Final_Predicates.getProductList().size(); i++)
		{
			L_Goals.add( Final_Predicates.getProductList().get(i) );
        }
		
		/**
		 * I get the Precoditions of the Plan.
		 * They are the preconditions for the first Action 
		 */
		
		for(TPredicate predicate: Preconditions)
		{
			L_pre_cond.put( predicate.Get_Name(), predicate.Get_Object_Complement());
		}
		
		/**
		 * I get each QUALITY AND GREEN desires with their values
		 */
		for(TQuality_Desire Quality: Desire.Get_List_Quality_Standing_Desire())
		{
			L_Quality.put( Quality.Get_Name(), 
									Quality.Get_Constraint().Get_Object_Complement());
		}
		
		for(TGreen_Desire Green: Desire.Get_List_Green_Standing_Desire())
		{
			L_Quality.put( Green.Get_Name(), 
									Green.Get_Constraint().Get_Object_Complement());
		}
		
		
		//Now, I start
		TTrieNode Node = this.Root;
		Integer Counter = 0;
		Boolean Continue = true;
		
		/**
		 * 1)
		 * 
		 * I create the path of goals in Trie.
		 * Before I search for a path, otherwise I create the path
		 */
		while(Continue && (Counter < L_Goals.size()) )
		{
			TTrieNode Temp_Node = null;
			String Index_Name = L_Goals.get( Counter );
			if(Node.Get_Layer_Goals() != null)
			{
				Temp_Node = Node.Get_Layer_Goals().get( Index_Name);
			}
			
			if(Temp_Node != null) 
			{
				Node = Temp_Node;
			}
			else
			{
				Continue = false;
				return result;
			}
			
			Counter++;
		}
		
		/**
		 * 2)
		 * 
		 * I create the path of Pre_conditions in Trie.
		 * Before I search for a path, otherwise I create the path
		 */
		Counter = 0;
		ArrayList<String> pre_cond_values = new ArrayList<String>();
		pre_cond_values.addAll( L_pre_cond.keySet());
		//I order the array in alphabetical order
		pre_cond_values.sort(Comparator.nullsFirst(Comparator.naturalOrder()));
		
		while(Counter < pre_cond_values.size())
		{
//			TTrieNode Temp_Node = null;
			HashMap<Object, TTrieNode> Pre_Cond_Values = null;
			String Index_Name = pre_cond_values.get( Counter );
			Object Index_value = L_pre_cond.get( Index_Name );
			if(Node.Get_Layer_Preconditions() != null)
			{
//				String Index_string = pre_cond_values.get( Counter );
				Pre_Cond_Values = Node.Get_Layer_Preconditions().
											get( Index_Name );
			}
			
			if( (Pre_Cond_Values != null) && (Pre_Cond_Values.get( Index_value) != null) )
			{
//				if(Pre_Cond_Values)
				Node = Pre_Cond_Values.get( Index_value);
			}
			else
			{
				Continue = false;
				return result;
			}
			
			Counter++;
		}
		
		/**
		 * 3)
		 * 
		 * I create the path of quality (quality and green desires) in Trie.
		 * Before I search for a path, otherwise I create the path
		 */
		
		Counter = 0;
		ArrayList<String> quality_values = new ArrayList<String>();
		quality_values.addAll( L_Quality.keySet());
		//I order the array in alphabetical order
//		quality_values.sort(null);
		quality_values.sort(Comparator.nullsFirst(Comparator.naturalOrder()));
		
		while(Counter < quality_values.size())
		{
			TTrieNode Temp_Node = null;
			String Index_Name = quality_values.get( Counter );
			Object Index_value = L_Quality.get( Index_Name );
			if(Node.Get_Layer_Quality() != null)
			{
				Temp_Node = Node.Get_Layer_Quality().get( Index_Name );
			}
			
//			if( (Temp_Node != null) && (Temp_Node.Data.equals( Index_value )))
			if( (Temp_Node != null) )
			{
				Node = Temp_Node;
			}
			else
			{
				Continue = false;
				return result;
			}
			
			Counter++;
		}
		
		for(TAction Action: Node.Get_Layer_Tasks())
		{
			result.add( Action );
		}
		
		return result;
	}
	
	
	public Boolean Create_Plan(TIntention Intention)
	{
		Boolean result = false;
		
		//By now it create plan for Practical Desires
		if(Intention.Get_Active_Desire() instanceof TEpistemic_Desire)
		{
			return result;
		}
		TPractical_Desire Desire = (TPractical_Desire) Intention.Get_Active_Desire();
		Integer Option_Id = Intention.Get_Selected_Option_Id();
		TOption Option = Desire.Get_List_Options().get( Option_Id);
		
		ArrayList<String> L_Goals = new ArrayList<String>();
		HashMap<String, Object> L_pre_cond = new HashMap<String, Object>();
		HashMap<String, Object> L_Quality = new HashMap<String, Object>();
		ArrayList<TAction> Tasks = new ArrayList<TAction>();
		
//		ArrayList<TDouble_Object> Temp_Goals = new ArrayList<TDouble_Object>();
//		ArrayList<TDouble_Object> Temp_pre_cond = new ArrayList<TDouble_Object>();
//		ArrayList<TDouble_Object> Temp_Quality = new ArrayList<TDouble_Object>();
		String Final_State =  Desire.Get_Final_State().Get_Formula();
		
		SortedResult Final_Predicates = BooleanExpressionSorter.
											sortExpression(Final_State);
		
		/**
		 * I get Final State Predicates in Canonical Form (Sum of Products, SoP)
		 */
		for (int i = 0; i < Final_Predicates.getProductList().size(); i++)
		{
			L_Goals.add( Final_Predicates.getProductList().get(i) );
        }
		
		/**
		 * I get the Precoditions of the Plan.
		 * They are the preconditions for the first Action 
		 */
		
		for(TPredicate predicate: Option.Get_Plan_Actions().getFirst().Get_Pre_conditions())
		{
			L_pre_cond.put( predicate.Get_Name(), predicate.Get_Object_Complement());
		}
		
		/**
		 * I get each tasks
		 */
		for (TAction Action: Option.Get_Plan_Actions())
		{
			Tasks.add( Action.Clone() );
		}
		
		/**
		 * I get each QUALITY AND GREEN desires with their values
		 */
		for(TQuality_Desire Quality: Desire.Get_List_Quality_Standing_Desire())
		{
			L_Quality.put( Quality.Get_Name(), 
									Quality.Get_Constraint().Get_Object_Complement());
		}
		
		for(TGreen_Desire Green: Desire.Get_List_Green_Standing_Desire())
		{
			L_Quality.put( Green.Get_Name(), 
									Green.Get_Constraint().Get_Object_Complement());
		}
		
		/**
		 * Now I Create the Plna by the Satisfied Intention
		 */
		TTrieNode Node = this.Root;
		Integer Counter = 0;
		
		/**
		 * 1)
		 * 
		 * I create the path of goals in Trie.
		 * Before I search for a path, otherwise I create the path
		 */
		while(Counter < L_Goals.size())
		{
			TTrieNode Temp_Node = null;
			String Index_Name = L_Goals.get( Counter );
			if(Node.Get_Layer_Goals() != null)
			{
				Temp_Node = Node.Get_Layer_Goals().get( Index_Name);
			}
			
			if(Temp_Node != null) 
			{
				Node = Temp_Node;
			}
			else
			{
				Node = Node.Add_Node_Goal(Index_Name);
			}
			
			Counter++;
		}
		
		/**
		 * 2)
		 * 
		 * I create the path of Pre_conditions in Trie.
		 * Before I search for a path, otherwise I create the path
		 */
		Counter = 0;
		ArrayList<String> pre_cond_values = new ArrayList<String>();
		pre_cond_values.addAll( L_pre_cond.keySet());
		//I order the array in alphabetical order
		pre_cond_values.sort(Comparator.nullsFirst(Comparator.naturalOrder()));
		
		while(Counter < pre_cond_values.size())
		{
//			TTrieNode Temp_Node = null;
			HashMap<Object, TTrieNode> Pre_Cond_Values = null;
			String Index_Name = pre_cond_values.get( Counter );
			Object Index_value = L_pre_cond.get( Index_Name );
			if(Node.Get_Layer_Preconditions() != null)
			{
//				String Index_string = pre_cond_values.get( Counter );
				Pre_Cond_Values = Node.Get_Layer_Preconditions().
											get( Index_Name );
			}
			
			if( (Pre_Cond_Values != null) && (Pre_Cond_Values.get( Index_value) != null) )
			{
//				if(Pre_Cond_Values)
				Node = Pre_Cond_Values.get( Index_value);
			}
			else
			{
				Node = Node.Add_Node_Pre_Condition(Index_Name, Index_value);
			}
			
			Counter++;
		}
		
		/**
		 * 3)
		 * 
		 * I create the path of quality (quality and green desires) in Trie.
		 * Before I search for a path, otherwise I create the path
		 */
		
		Counter = 0;
		ArrayList<String> quality_values = new ArrayList<String>();
		quality_values.addAll( L_Quality.keySet());
		//I order the array in alphabetical order
//		quality_values.sort(null);
		quality_values.sort(Comparator.nullsFirst(Comparator.naturalOrder()));
		
		while(Counter < quality_values.size())
		{
			TTrieNode Temp_Node = null;
			String Index_Name = quality_values.get( Counter );
			Object Index_value = L_Quality.get( Index_Name );
			if(Node.Get_Layer_Quality() != null)
			{
				Temp_Node = Node.Get_Layer_Quality().get( Index_Name );
			}
			
//			if( (Temp_Node != null) && (Temp_Node.Data.equals( Index_value )))
			if( (Temp_Node != null) )
			{
				Node = Temp_Node;
			}
			else
			{
				Node = Node.Add_Node_Quality_Solution(Index_Name, Index_value);
			}
			
			Counter++;
		}
		
		/**
		 * 4)
		 * 
		 * I create the path of quality (quality desires) in Trie.
		 * Before I search for a path, otherwise I create the path
		 */
		Node.Insert_Tasks( Tasks );
		/**
		 * 5)
		 * 
		 * I create the path of quality (quality desires) in Trie.
		 * Before I search for a path, otherwise I create the path
		 */
		Node.Set_Layer_Final_State( Final_State );
		
		return result;
	}
	

}
