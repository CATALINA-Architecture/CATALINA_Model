package com.Catalina_Model.Catalina_V_0_3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

class TAgent_Focus_Intention_Compare implements Comparator<TIntention> 
{
    @Override
    public int compare(TIntention Intention0, TIntention Intention1) 
    {
    	Double w0 = Intention0.Get_Active_Desire().Get_Saliency();
    	Double w1 = Intention1.Get_Active_Desire().Get_Saliency();

        return (w0 > w1? -1 : (w0 == w1) ? 0 : 1);
    }
}

public class TAgent_Focus 
{
	private TAttention_Selection Parent;
	
	private Double Saliency_Threshold;
	private Double Attention_Threshold;
	
	public TAgent_Focus(TAttention_Selection Owner)
	{
		this.Parent = Owner;
		this.Saliency_Threshold = 0.0;
		this.Attention_Threshold = 0.0;
	}
	
	public void Execute(ArrayList<TIntention> Selected_Intentions)
	{
//		Collections.sort(Selected_Intentions, new TAgent_Focus_Intention_Compare());
		
		TIntention Intention = Selected_Intentions.getFirst();
		if(Intention != null)
		{
			if(Intention.Get_Active_Desire() != null)
			{
				this.Compute_Saliency_Threshold( Intention );
				this.Compute_Attention_Threshold( Intention );				
			}
			else
			{
				Selected_Intentions.size();
			}	
		}
		else
		{
			Selected_Intentions.size();
		}
		
	}
	
	private void Compute_Saliency_Threshold( TIntention First_Intention)
	{
		this.Saliency_Threshold = First_Intention.Get_Active_Desire().Get_Saliency();
	}
	
	private void Compute_Attention_Threshold( TIntention First_Intention)
	{
		this.Attention_Threshold = this.Saliency_Threshold + ( ( 1 - this.Saliency_Threshold) /2);
	}
	
	public Double Get_Saliency_Threshold()
	{
		return this.Saliency_Threshold;
	}
	
	public Double Get_Attention_Threshold()
	{
		return this.Attention_Threshold;
	}
}
