import java.util.HashMap;
import java.util.HashSet;

import com.Catalina_Model.Catalina_V_0_3.TAttentional_Desire;
import com.Catalina_Model.Catalina_V_0_3.TBelief;
import com.Catalina_Model.Catalina_V_0_3.TPractical_Desire;

public class TDesires_Precondictions_Functions
{
	private HashMap<String, HashSet<String>> Lists_PReconditions_for_Desires;

	public TDesires_Precondictions_Functions()
	{
		this.Lists_PReconditions_for_Desires = new HashMap<String, HashSet<String>>();
	}
	
	public HashSet<String> Sample_Compute_Preconditions(
    		TAttentional_Desire Attentional_Desire)
	{
		TPractical_Desire Practical_Desire = (TPractical_Desire) Attentional_Desire;
		HashSet<String> Pre_conditions = new HashSet<String>();
		for (TBelief Belief: Practical_Desire.Get_Trigger_Condition().Get_Beliefs())
		{
			Pre_conditions.add(Belief.Get_Name());
			/**
			 * The Predicate of the Belief can be a TBelief, so
			 * I insert this in Pre_conditions and in Unihibited_Beliefs
			 */
			if(Belief.Get_Predicate().Get_Subject() instanceof TBelief)
			{
				Pre_conditions.add( ((TBelief) Belief.Get_Predicate().Get_Subject()).Get_Name() );
			}
		}
		Pre_conditions.remove( null );
		this.Lists_PReconditions_for_Desires.put( Attentional_Desire.Get_Name() , Pre_conditions);
		return Pre_conditions;
	}
	
	public HashSet<String> Get_Destination_City_Preconditions( 
    		TAttentional_Desire Attentional_Desire, HashMap<String, TBelief> Map_Beliefs)
	{
		HashSet<String> result = new HashSet<String>();
		HashSet<String> temp_List = this.Lists_PReconditions_for_Desires.get( Attentional_Desire.Get_Name() );
		if( temp_List == null )
		{
			temp_List.addAll( this.Sample_Compute_Preconditions(Attentional_Desire) );
		}
		result.addAll( temp_List );
		temp_List = null;
		return result;
	}
}
