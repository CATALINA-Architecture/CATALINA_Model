import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class TTrafficControlService 
{
	private Autonomous_Vehicle_Demo Demo;
	private TCommon_Functions Common_Functions;
	public ArrayList<Integer> Danger_Data;
	private Boolean New_Data_Acquired;
	public Boolean Response_Sended;
	public String String_Response_Sended;
	public Boolean Value_For_Sample_Execution = true;
	
	
	public TTrafficControlService(Autonomous_Vehicle_Demo demo)
	{
		this.Demo = demo;
		this.Common_Functions = demo.Common_Functions;
		
		this.Danger_Data = new ArrayList<Integer>();
		this.New_Data_Acquired = false;
		
		this.Response_Sended = false;
		this.String_Response_Sended = "";
	}
	
	public void Danger_Query_Simulation(ArrayList<Integer> Real_Positions)
	{
//		Boolean Response_Sended = false;
//		String String_Response_Sended = "";
		
		Integer City_position = Real_Positions.get(0);
		Integer Route_position = Real_Positions.get(1);
		Integer Step_position = Real_Positions.get(2);
		Integer Dangeroues_Route_position = Real_Positions.get(3);
		
		
		
		String Question = "- Why is the route closed?";
		this.Common_Functions.Print_Colored_Text(Question, 2);
		Question ="";
		int counter = 0;
		for(TType_Danger Type_Danger: TType_Danger.values() )
		{
			Question += counter + "- "+Type_Danger+"\n";
			counter++;
		}
		
		if (Question.length() > 0)
		{
		    Question = Question.substring(0, Question.length() - 1);
		}
		
		String Answer = ""; 
		Boolean Repeat = false;
		int index = -1;
		do
		{
//			Answer = this.Common_Functions.Get_Preset_Input(Question, "1", 2, true);
			this.Common_Functions.Print_Colored_Text(Question, 2);
			while( !this.Response_Sended)
			{
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			this.Response_Sended = false;
			Answer = this.String_Response_Sended;
			this.String_Response_Sended = "";
			
			this.Common_Functions.Print(" Your response was: "+Answer);
			if (Answer == null | Answer.isEmpty()) 
			{
				Repeat =  true;
		    }
			else
			{
				try 
				{
			        // 1. Prova a convertire la stringa in un intero
			        index = Integer.parseInt(Answer);

			        // 2. Ottieni il numero totale di elementi nell'enum
			        int totalDangers = TType_Danger.values().length;

			        // 3. Controlla se l'indice � nel range valido (da 0 a total-1)
			        Repeat =  !(index >= 0 && index < totalDangers);
			    } 
				catch (NumberFormatException e) 
				{
			        // Se la stringa non era un numero (es. "abc"), non � un indice.
					Repeat = true;
			    }
			}
			if ( Repeat )
			{
				this.Common_Functions.Print_Colored_Text(
						"The Answer was wrong! Repeat please.", 2);
			}
		}
		while( Repeat);
		
		TType_Danger Type_Danger = TType_Danger.values()[index];
		/*
		 * I Ask the duration of the Danger (for recompute other paths)
		 */
		
		/**
		 * this gives the same behaviour of the simulation for Catalina_V_0.2
		 * min = 4 and max = 9
		 */
		Integer min = 1;
		Integer max = 100;
		Integer Random_Number = ThreadLocalRandom.current().nextInt(4, max + 1);
		
		Question = "\n- How long will the route be closed?\n"
				+ "Enter the number of time slots as an integer between 1 and 100.(a time slot equals 15 minutes)";
//		+"Give me a number between "+min+" and "+max;
		do
		{
//			Answer = this.Common_Functions.Get_Preset_Input(
//						Question, Random_Number.toString(), 2, true);
			this.Common_Functions.Print_Colored_Text(Question, 2);
			while(!this.Response_Sended)
			{
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			this.Response_Sended = false;
			Answer = this.String_Response_Sended;
			this.String_Response_Sended = "";
			this.Common_Functions.Print(" Your response was: "+Answer);
			
			if (Answer == null | Answer.isEmpty()) 
			{
				Repeat =  true;
		    }
			else
			{
				try 
				{
			        // 1. Prova a convertire la stringa in un intero
			        index = Integer.parseInt(Answer);

			        // 2. Controlla se l'indice � nel range valido (da min a max)
			        Repeat =  !(index >= min && index <= max);
			    } 
				catch (NumberFormatException e) 
				{
			        // Se la stringa non era un numero (es. "abc"), non � un indice.
					Repeat = true;
			    }
			}
			if ( Repeat)
			{
				this.Common_Functions.Print_Colored_Text(
						"The Answer was wrong! Repeat please.", 2);
			}
		}
		while( Repeat);

		Integer Duration = index;
		
//		this.Common_Functions.Print_Colored_Text(
//				"The Answer was wrong! Repeat please.", 2);
		
		this.Danger_Data.clear();
		/**
		 * Danger_Data Index:
		 * 0 - City_position
		 * 1 - Route_position
		 * 2 - Step_position
		 * 3 - Dangeroues_Route_position
		 * 4 - Type_Danger.ordinal()
		 * 5 - Duration
		 */
		this.Danger_Data.add(City_position);
		this.Danger_Data.add(Route_position);
		this.Danger_Data.add(Step_position);
		this.Danger_Data.add(Dangeroues_Route_position);
		this.Danger_Data.add(Type_Danger.ordinal());
		this.Danger_Data.add(Duration);
		
		this.New_Data_Acquired = this.Value_For_Sample_Execution;
	}
	
	public ArrayList<Integer> Get_Danger_Data()
	{
		ArrayList<Integer> result= new ArrayList<Integer>();
		if ( this.New_Data_Acquired )
		{
			this.New_Data_Acquired = false;
			result.addAll( this.Danger_Data );
			this.Danger_Data.clear();
		}
		return result; 
	}

}
