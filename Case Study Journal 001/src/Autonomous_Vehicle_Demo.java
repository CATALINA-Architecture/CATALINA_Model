import java.awt.Point;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.Catalina_Model.Catalina_V_0_3.TAction_Control;
import com.Catalina_Model.Catalina_V_0_3.TAgent;
import com.Catalina_Model.Catalina_V_0_3.TAttention_Selection;
import com.Catalina_Model.Catalina_V_0_3.TBelief;
import com.Catalina_Model.Catalina_V_0_3.TBoolean_Expression_Evaluetor;
import com.Catalina_Model.Catalina_V_0_3.TDesire_Handler;
import com.Catalina_Model.Catalina_V_0_3.TExecutive_Inhibition_Function;
import com.Catalina_Model.Catalina_V_0_3.TExecutive_Memory_Maintenance_Function;
import com.Catalina_Model.Catalina_V_0_3.TExecutive_Perception_Function;
import com.Catalina_Model.Catalina_V_0_3.TExecutive_Reasoner_Function;
import com.Catalina_Model.Catalina_V_0_3.TExecutive_Resource_Allocation_Function;
import com.Catalina_Model.Catalina_V_0_3.TExecutive_Switching_Function;
import com.Catalina_Model.Catalina_V_0_3.TGlobal_Workspace;
import com.Catalina_Model.Catalina_V_0_3.TIntention;
import com.Catalina_Model.Catalina_V_0_3.TLong_Memory;
import com.Catalina_Model.Catalina_V_0_3.TMeans_End_Epistemic_Functions_Handler;
import com.Catalina_Model.Catalina_V_0_3.TOption;
import com.Catalina_Model.Catalina_V_0_3.TPredicate;
import com.Catalina_Model.Catalina_V_0_3.TType_Update_Contract;

public class Autonomous_Vehicle_Demo
{
	
//	public TFunctions_for_Information_Extraction Functions_for_Information_Extraction;
	public TFunctions_for_Inhibition_Function Functions_for_Belief_Inhibition_Function;
	public TFunctions_for_Region_Inhibition_Function Functions_for_Region_Inhibition_Function;
	public TFunctions_for_Option_Execution Functions_for_Plan_Execution;
//	TFunctions_for_Means_End_Reasoner Means_End_Reasoner_Function;
	public TFunctions_for_Perception_Processing Perception_Processing_functions;
	public TFunctions_for_Means_End_Reasoner Means_End_Reasoner_Function;
	
	public TEnvironment Map;
	public TGUI Gui_Map;
	public TAgent Agent;

	public String Data_Directory = "";
	public TCommon_Functions Common_Functions;
	
	TLong_Memory Long_Memory = null;
	
	public static void main(String[] args)
	{
		Autonomous_Vehicle_Demo Demo = new Autonomous_Vehicle_Demo();
		Demo.Start( Demo );
	}
	
	public void tentativi_Evaluetor ()
	{
		TBoolean_Expression_Evaluetor Evaluetor= new TBoolean_Expression_Evaluetor();
		Evaluetor.Set_Variable("A", false);
		Boolean valore = Evaluetor.Evaluate("A");
		System.out.print("Valore: " +valore);
		this.End_Simulation();
	}
	
	public void Initialize_GUI()
	{
		String Map_Path = "img/ticket to ride.jpeg";
        // String Car_Path = "img/m4.png";
        String Car_Path = "img/auto.png";
        List<Point> Positions = Arrays.asList(
                new Point(50, 50),
                new Point(200, 100),
                new Point(100, 250),
                new Point(300, 150)
        );
		 // Create and launch the GUI
        Gui_Map = new TGUI(Map_Path, Car_Path, Positions, this);
//		this.Map = new TEnvironment();
//		this.Create_Map();
////		End_Simulation();
	}
	
	public void Start(Autonomous_Vehicle_Demo Demo)
	{
//		this.tentativi();
		//System.out.println("Versione Java in uso: " + System.getProperty("java.version"));
		//System.out.println("Vendor: " + System.getProperty("java.vendor"));
        ////System.out.println("Versione Completa: " + System.getProperty("java.runtime.version"));
        //System.out.println("Home: " + System.getProperty("java.home"));
//        
		Demo.Common_Functions = new TCommon_Functions( this );
		this.Initialize_GUI();
		Demo.Common_Functions.Print_Colored_Text("Autonomous Vehicle Agent Simulation", 2);
		
		
//		this.Data_Directory ="D:\\Cartelle\\CNR\\Progetti\\Catalina\\CATALINA - Data Creation\\Data\\";
		this.Data_Directory ="D:/Cartelle/CNR/Progetti/Catalina/CATALINA - Data Creation/Data/";
		
		
		File cartella = new File(this.Data_Directory);
//		File mioFile = new File("Data\\");
		File mioFile = new File("/Data/");
//		System.out.println("cartella: "+cartella.getAbsolutePath());
//		System.out.println("mioFile: "+mioFile.getAbsolutePath());
//		System.exit(1);
//		if (Files.isDirectory(this.Data_Directory, null)) {
		if (!(cartella.exists() && cartella.isDirectory()))
//		{
////            System.out.println("La cartella esiste!");
//        } 
//		else
		{
//            System.out.println("La cartella NON esiste (o è un file).");
//            this.Data_Directory ="D:\\Cartelle\\CNR\\Progetti\\CATALINA - Data Creation\\Data\\";
			System.out.println(cartella.isDirectory()+" Non Esiste: "+this.Data_Directory);
			this.Data_Directory ="D:/Cartelle/CNR/Progetti/CATALINA - Data Creation/Data/";
        }
		cartella = new File(this.Data_Directory);
		if (!(cartella.exists() && cartella.isDirectory()))
//			{
////	            System.out.println("La cartella esiste!");
//	        } 
//			else
			{
//	            System.out.println("La cartella NON esiste (o è un file).");
				System.out.println(cartella.isDirectory()+" Non Esiste2: "+this.Data_Directory);
				String percorsoAvvio = System.getProperty("user.dir");
				System.out.println("percorsoAvvio: "+percorsoAvvio);
	            this.Data_Directory =percorsoAvvio+"/Data/";
	        }
		cartella = new File(this.Data_Directory);
		if (!(cartella.exists() && cartella.isDirectory()))
//			{
////	            System.out.println("La cartella esiste!");
//	        } 
//			else
			{
//	            System.out.println("La cartella NON esiste (o è un file).");
				System.out.println(cartella.isDirectory()+" Non Esiste3: "+this.Data_Directory);
				
	        }
		System.out.println(Data_Directory);
		Agent = new TAgent(Data_Directory);
		
//		this.Common_Functions.Print(
//		Agent.Get_Global_WorkSpace().Get_All_Map_Beliefs_from_MM().keySet());
		/*
		 * Attentional Layer
		 */
		TAttention_Selection Attention_Modulation = Agent.Get_Attention_Modulation_Component();
		TDesire_Handler Desire_Handler = Agent.Get_Desire_Handler_Component();
		
		/*
		 * Executive System Functions
		 */
		TExecutive_Memory_Maintenance_Function Memory_Maintenance_Function = Agent.Get_Executive_Memory_Maintenance_Function();
		TExecutive_Perception_Function Perception_Function = Agent.Get_Executive_PerceptionFunction();
		TExecutive_Inhibition_Function Inhibition_Function = Agent.Get_Executive_Inhibition_Function();
		TExecutive_Switching_Function Switching_Function = Agent.Get_Executive_Switching_Function();
		TAction_Control Action_Control = Agent.Get_Action_Control(); 
		
		/*
		 * This is not used in this simulation
		 */
		TExecutive_Resource_Allocation_Function Resource_Allocation_Function =
				Agent.Get_Executive_Resource_Allocation_Function();
				
		
		/*
		 * Practical Reasoner
		 */
		TExecutive_Reasoner_Function  Reasoner = Agent.Get_Executive_Reasoner_Function();
		
		TGlobal_Workspace Global_Workspace = Agent.Get_Global_WorkSpace();
		this.Long_Memory = Agent.Get_Long_Memory();
		
		
		/**
		 * Initialize Data Simulation
		 */
		this.Initialize_Data_Simulation();

		/**
		 * ****************************
		 * ****************************
		 * Perception Processing
		 * ****************************
		 * **************************** 
		 */
		
		/**
		 *  Add Sensors and Function to Perception Processing
		 */
//		TFunctions_for_Perception_Processing Perception_Processing_functions = 
		Perception_Processing_functions = 
						new TFunctions_for_Perception_Processing( Demo );
		
		/**
		 * Add Sensors to Memory_Maintenance_Function
		 */
//		Perception_Processing_functions.Add_Function_To_Perception_Processing( Memory_Maintenance_Function );
		Perception_Processing_functions.Add_Function_To_Perception_Processing( Perception_Function );
		
		
		this.Gui_Map.btnSendSignal.addActionListener(e -> {
	            // Usiamo la tua funzione Show_Message
//	            Show_Message("Saluto", "Ciao mondo", JOptionPane.INFORMATION_MESSAGE);
			Perception_Processing_functions.Send_A_Danger();
	        	
	        });
		
		this.Gui_Map.btnSendLowFuel.addActionListener(e -> {
            // Usiamo la tua funzione Show_Message
//            Show_Message("Saluto", "Ciao mondo", JOptionPane.INFORMATION_MESSAGE);
		Perception_Processing_functions.Send_Low_Fuel_Signal();
        	
        });
		
		
		
		
		/**
		 * ****************************
		 * ****************************
		 * Information Extraction
		 * ****************************
		 * **************************** 
		 */
		
		/**
		 * Add Sensors and Function to Information Extraction
		 */
		TFunctions_for_Information_Extraction Information_Extraction_functions = 
						new TFunctions_for_Information_Extraction( Demo );
		
		Information_Extraction_functions.Add_Function_To_Information_Extraction( Perception_Function );
		/**
		 * ****************************
		 * ****************************
		 * Stimulus Filtering
		 * ****************************
		 * **************************** 
		 */
		
		TFunctions_for_Stimulus_Filtering Stimulus_Filtering_functions = 
				new TFunctions_for_Stimulus_Filtering( Demo );
		Stimulus_Filtering_functions.Add_Function_To_Stimulus_Filtering( Perception_Function );
		
		/**
		 * ****************************
		 * ****************************
		 * Global_Workspace_Memory_Maintenance
		 * ****************************
		 * **************************** 
		 */
		
		TFunctions_for_GW_Memory_Maintenance_Update_Belief_Function GWMMF =
				new TFunctions_for_GW_Memory_Maintenance_Update_Belief_Function(Demo);
		GWMMF.Add_Function_To_GW_Memory_Maintenance_Function(Memory_Maintenance_Function);
		
		
		////////////////
		/**
		 * ****************************
		 * ****************************
		 * Belief Inhibition Function
		 * ****************************
		 * **************************** 
		 */
		Functions_for_Belief_Inhibition_Function = new TFunctions_for_Inhibition_Function( Demo );
		Functions_for_Belief_Inhibition_Function.Set_Inhibition_Function( Inhibition_Function );
		Functions_for_Belief_Inhibition_Function.Add_Inhibition_Functions();
		
		/**
		 * ****************************
		 * ****************************
		 * Region Inhibition Function
		 * ****************************
		 * **************************** 
		 */
		TFunctions_for_Region_Inhibition_Function Functions_for_Region_Inhibition_Function = new TFunctions_for_Region_Inhibition_Function( Demo );
		Functions_for_Region_Inhibition_Function.Set_Inhibition_Function( Inhibition_Function );
		Functions_for_Region_Inhibition_Function.Add_Inhibition_Functions();
		
		
		/**
		 * ****************************
		 * ****************************
		 * Map_Exogenous_Functions_Handler
		 * ****************************
		 * **************************** 
		 */
		
		TFunctions_for_Exogenous_Desire_Promotion EDP = new TFunctions_for_Exogenous_Desire_Promotion( Demo );
		EDP.Add_Functions_To_Exogenous_Desire_Promotion( Desire_Handler );
		
		/**
		 * ****************************
		 * ****************************
		 * TMeans_End_Epistemic_Functions_Handler  
		 * ****************************
		 * **************************** 
		 */
		
//		TFunctions_for_Means_End_Reasoner MER = new TFunctions_for_Means_End_Reasoner( Demo );
		this.Means_End_Reasoner_Function = new TFunctions_for_Means_End_Reasoner( Demo );
		this.Means_End_Reasoner_Function.Add_Functions_To_Means_End_Reasoner( Reasoner );
		this.Means_End_Reasoner_Function.Add_Beliefs_To_Means_End_Reasoner( Reasoner );
		
		
		/**
		 * ****************************
		 * ****************************
		 * Filtering_Process_Functions
		 * ****************************
		 * **************************** 
		 */
		
		TFunctions_for_Filtering_Desires_Function Filtering_Green_Desires = new
				TFunctions_for_Filtering_Desires_Function(Demo);
		Filtering_Green_Desires.Add_Function_To_Filter_Process( Reasoner );
		
		/**
		 * ****************************
		 * ****************************
		 * Deliberation_Process  
		 * ****************************
		 * **************************** 
		 */
		TFunctions_for_Deliberation_Process DPF = new TFunctions_for_Deliberation_Process( Demo );
		DPF.Add_Function_To_Means_End_Reasoner( Reasoner );
		
		
		/**
		 * ****************************
		 * ****************************
		 * Plan_Execution 
		 * ****************************
		 * **************************** 
		 */
		Functions_for_Plan_Execution = 
				new TFunctions_for_Option_Execution( Demo );
		Functions_for_Plan_Execution.Add_Function_To_Switching_Function( Action_Control );
//		
//		/**
//		 * 
//		 * Testing Memory_Maintenance_Function
//		 * 
//		 */
//		ArrayList<TIntention> selected_intentions = new ArrayList<TIntention>();
//		Global_Workspace.Set_Selected_Intentions(selected_intentions);
////		Global_Workspace.Broadcast_Message(TType_Update_Contract.Thresholds);
//		Global_Workspace.Update_Saliency_and_Attention_Thresholds(0.3, 0.3);
		
		Desire_Handler.Suspend();
		Attention_Modulation.Suspend();
		Inhibition_Function.Suspend();
		Perception_Function.Suspend();
		Memory_Maintenance_Function.Suspend();
		Reasoner.Suspend();
		Switching_Function.Suspend();
		
//		Desire_Handler.start();
//		Attention_Modulation.start();
//		Inhibition_Function.start();
//		Memory_Maintenance_Function.start();
//		Reasoner.start();
//		Switching_Function.start();
//		
//		Desire_Handler.Resume();
//		Attention_Modulation.Resume();
//		Inhibition_Function.Resume();
//		Memory_Maintenance_Function.Resume();
//		Reasoner.Resume();
//		Switching_Function.Resume();
//		
////		ArrayList<TIntention> selected_intentions = new ArrayList<TIntention>();
////		Global_Workspace.Set_Selected_Intentions(selected_intentions);
//		Global_Workspace.Broadcast_Message(null);
//		Global_Workspace.Update_Saliency_and_Attention_Thresholds(0.3, 0.3);
//		Global_Workspace.Broadcast_Message(TType_Update_Contract.Standing_Desires);
	}
	
	public void End_Simulation()
	{
		this.Common_Functions.Print("End Simulation.");
		System.exit(0);
	}
	
	public void Create_Map()
	{
		//Creating all paths RED
		this.Map.Add_Arch(TCity.Pamplona, TCity.Marseille, TColor.Red, 4);
		this.Map.Add_Arch(TCity.Paris, TCity.Bruxelles, TColor.Red, 2);
		this.Map.Add_Arch(TCity.Frankfurt, TCity.Berlin, TColor.Red, 3);
		this.Map.Add_Arch(TCity.Wien, TCity.Budapest, TColor.Red, 1);
		this.Map.Add_Arch(TCity.Zacrab, TCity.Sarajevo, TColor.Red, 3);
		this.Map.Add_Arch(TCity.Warszawa, TCity.Wilno, TColor.Red, 3);
		this.Map.Add_Arch(TCity.Kyiv, TCity.Smolensk, TColor.Red, 3);
		this.Map.Add_Arch(TCity.Erzurum, TCity.Sochi, TColor.Red, 3);
		
		//Creating all paths GREEN
		this.Map.Add_Arch(TCity.Pamplona, TCity.Paris, TColor.Green, 4);
		this.Map.Add_Arch(TCity.Dieppe, TCity.Bruxelles, TColor.Green, 2);
		this.Map.Add_Arch(TCity.Frankfurt, TCity.Essen, TColor.Green, 2);
		this.Map.Add_Arch(TCity.Zurich, TCity.Venice, TColor.Green, 2);
		this.Map.Add_Arch(TCity.Berlin, TCity.Wien, TColor.Green, 3);
		this.Map.Add_Arch(TCity.Sarajevo, TCity.Athina, TColor.Green, 4);
		this.Map.Add_Arch(TCity.Rica, TCity.Wilno, TColor.Green, 4);
		this.Map.Add_Arch(TCity.Kharkov, TCity.Rostow, TColor.Green, 2);
		
		//Creating all paths YELLOW
		this.Map.Add_Arch(TCity.Madrid, TCity.Barcelona, TColor.Yellow, 2);
		this.Map.Add_Arch(TCity.Paris, TCity.Bruxelles, TColor.Yellow, 2);
		this.Map.Add_Arch(TCity.Amsterdam, TCity.Essen, TColor.Yellow, 3);
		this.Map.Add_Arch(TCity.Zurich, TCity.Munchen, TColor.Yellow, 2);
		this.Map.Add_Arch(TCity.Kodenilavn, TCity.Stockholm, TColor.Yellow, 3);
		this.Map.Add_Arch(TCity.Berlin, TCity.Warszawa, TColor.Yellow, 4);
		this.Map.Add_Arch(TCity.Bucaresti, TCity.Costantinople, TColor.Yellow, 3);
		this.Map.Add_Arch(TCity.Wilno, TCity.Smolensk, TColor.Yellow, 3);
		
		//Creating all paths ORANGE
		this.Map.Add_Arch(TCity.Madrid, TCity.Cadiz, TColor.Orange, 3);
		this.Map.Add_Arch(TCity.Brest, TCity.Dieppe, TColor.Orange, 2);
		this.Map.Add_Arch(TCity.Edinburgh, TCity.London, TColor.Orange, 4);
		this.Map.Add_Arch(TCity.Paris, TCity.Frankfurt, TColor.Orange, 3);
		this.Map.Add_Arch(TCity.Munchen, TCity.Wien, TColor.Orange, 3);
		this.Map.Add_Arch(TCity.Zacrab, TCity.Budapest, TColor.Orange, 2);
		this.Map.Add_Arch(TCity.Smyrna, TCity.Ancora, TColor.Orange, 3);
		this.Map.Add_Arch(TCity.Smolensk, TCity.Moskva, TColor.Orange, 2);
		
		//Creating all paths BLACK
		this.Map.Add_Arch(TCity.Madrid, TCity.Pamplona, TColor.Black, 3);
		this.Map.Add_Arch(TCity.Brest, TCity.Paris, TColor.Black, 3);
		this.Map.Add_Arch(TCity.Edinburgh, TCity.London, TColor.Black, 4);
		this.Map.Add_Arch(TCity.Bruxelles, TCity.Amsterdam, TColor.Black, 1);
		this.Map.Add_Arch(TCity.Frankfurt, TCity.Berlin, TColor.Black, 3);
		this.Map.Add_Arch(TCity.Venice, TCity.Rome, TColor.Black, 2);
		this.Map.Add_Arch(TCity.Danzic, TCity.Rica, TColor.Black, 3);
		this.Map.Add_Arch(TCity.Ancora, TCity.Erzurum, TColor.Black, 3);
		
		//Creating all paths BLU
		this.Map.Add_Arch(TCity.Lisboa, TCity.Cadiz, TColor.Blue, 2);
		this.Map.Add_Arch(TCity.Pamplona, TCity.Paris, TColor.Blue, 4);		
		this.Map.Add_Arch(TCity.Bruxelles, TCity.Frankfurt, TColor.Blue, 2);
		this.Map.Add_Arch(TCity.Essen, TCity.Berlin, TColor.Blue, 2);
		this.Map.Add_Arch(TCity.Munchen, TCity.Venice, TColor.Blue, 2);		
		this.Map.Add_Arch(TCity.Wien, TCity.Warszawa, TColor.Blue, 4);
		this.Map.Add_Arch(TCity.Sofia, TCity.Costantinople, TColor.Blue, 3);
		this.Map.Add_Arch(TCity.Wilno, TCity.Petrocrad, TColor.Blue, 4);
		
		//Creating all paths VIOLET
		this.Map.Add_Arch(TCity.Lisboa, TCity.Madrid, TColor.Violet, 3);
		this.Map.Add_Arch(TCity.Pamplona, TCity.Brest, TColor.Violet, 4);		
		this.Map.Add_Arch(TCity.Dieppe, TCity.Paris, TColor.Violet, 1);
		this.Map.Add_Arch(TCity.Marseille, TCity.Zurich, TColor.Violet, 2);
		this.Map.Add_Arch(TCity.Frankfurt, TCity.Munchen, TColor.Violet, 2);		
		this.Map.Add_Arch(TCity.Berlin, TCity.Warszawa, TColor.Violet, 4);
		this.Map.Add_Arch(TCity.Budapest, TCity.Sarajevo, TColor.Violet, 3);
		this.Map.Add_Arch(TCity.Sofia, TCity.Athina, TColor.Violet, 3);
		
		//Creating all paths WHITE
		this.Map.Add_Arch(TCity.Madrid, TCity.Pamplona, TColor.White, 3);////aggiornare sulla this.Mappa
		
		//Creating all paths Grey
		// -----BLU
		this.Map.Add_Arch(TCity.Marseille, TCity.Barcelona, TColor.Blue, 4);
		this.Map.Add_Arch(TCity.Dieppe, TCity.London, TColor.Blue, 2);

		// -----GREEN
		this.Map.Add_Arch(TCity.Pamplona, TCity.Barcelona, TColor.Green, 2);
		this.Map.Add_Arch(TCity.Marseille, TCity.Rome, TColor.Green, 4);
		
		// -----RED
		this.Map.Add_Arch(TCity.Paris, TCity.Zurich, TColor.Red, 3);
		
		// -----YELLOW
		this.Map.Add_Arch(TCity.Marseille, TCity.Paris, TColor.Yellow, 4);
		this.Map.Add_Arch(TCity.Dieppe, TCity.London, TColor.Yellow, 2);
		
		//-----VIOLET
		this.Map.Add_Arch(TCity.London, TCity.Amsterdam, TColor.Violet, 2);
		
		//-----ORANGE
		this.Map.Add_Arch(TCity.Rome, TCity.Palermo, TColor.Orange, 4);
		
	}
	
	public void Initialize_Data_Simulation()
	{
		// I create the Map and its paths
		this.Map = new TEnvironment();
		this.Create_Map();
		this.Initialize_Region_Simulation();
		
		//I assign several Data to Agent
		HashMap<String, TBelief> Map_Belief = this.Long_Memory.Get_All_Map_Beliefs();
		TBelief BL_Map =  Map_Belief.get("BL_Map");
		TPredicate Predicate = BL_Map.Get_Predicate();
		Predicate.set_Object_Complement( this.Map );

		
//		this.Initialize_GUI();
	}
	
	public void Initialize_Region_Simulation()
	{
		ArrayList<TRegion_Simulation> All_Regions = new ArrayList<TRegion_Simulation>();
		for (TRoute Route: this.Map.All_Routes)
		{
			
			TRegion_Simulation Region_Simulation = new TRegion_Simulation( Route);
//			All_Regions.add(Region_Simulation);
			this.Long_Memory.Add_Region( Region_Simulation );
		}
	}
}
