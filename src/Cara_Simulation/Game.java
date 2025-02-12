package Cara_Simulation;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
//import java.util.Scanner;
import java.util.List;
import java.util.concurrent.TimeUnit;

//import java.util.Arrays;

//import Gioco.Plan;
//import Gioco.Goal;


public class Game {
	
//	private Plan Piano;
	
	public static Environment Mappa;
	public static Integer Scenario_Number;
	
	
	
	public static void main(String[] args) 
	{
//		TReader_XML readerx = new TReader_XML();
//		readerx.Read_File();
//		
//		Tprova prova = new Tprova();
//		prova.Esegui_Predicato();
		Scenario_Number = 0;
		TFile_Manager mana = new TFile_Manager();
		mana.Create_Predicates();
		mana.Create_Beliefs();
		int Init_Creation_Path_Travelled_for_Functional_Goal = mana.Init_Creation_Path_Travelled_for_Functional_Goal;
		mana.Create_Green_Goals();
		mana.Create_Quality_Goals();
		mana.Create_Functional_Goals();
		mana.Create_Epistemic_Goals();
		
		Instant start = Instant.now();
		Mappa = new Environment();
		
		System.out.println("Creating Map");		
		Create_Map();
		Instant end = Instant.now();
		Duration timeElapsed = Duration.between(start, end);
		System.out.println("I created the map in Time: "+ timeElapsed.toMillis() +" milliseconds");
		
		TTCS TCS = new TTCS(Mappa);

		TAgent Agent = new TAgent();
		//Game.Print("Init_Creation_Path_Travelled_for_Functional_Goal: "+Init_Creation_Path_Travelled_for_Functional_Goal);
		Agent.Initialization(TCS, Mappa, Init_Creation_Path_Travelled_for_Functional_Goal);

		
		Agent.Start();
		End_Game();
		
		
		
		
		

		End_Game();
//		Game Gioco = new Game();
//		Desire des1;
//		Desire des2 = new Desire("dsds",Station.Amsterdam) ;//"fd","dffd");
//		System.out.println("des2 " + des2.getName() + des2.getDestination());
////		des2.setDestination("ddd");
////		des2.setName("ddd22222");
//		des1 = des2;
//		System.out.println("des1 " + des1.getName() + des1.getDestination());
//		
//		Collezioni Collezione = new Collezioni();
//		System.out.println("ciao !" );
		//Links	
		
		//Funziona
		//Instant start = Instant.now();
		System.out.println("Inizio creazione mappa");
		Mappa = new Environment();
		Create_Map();
		//Instant end = Instant.now();
		//Duration timeElapsed = Duration.between(start, end);
		System.out.println("Ho creato la mappa in "+"Time taken: "+ timeElapsed.toMillis() +" milliseconds");
		End_Game();
//		Mappa.aggiungiArco(Station.Lisboa, Station.Madrid, Color.Red, 1);
//		Mappa.aggiungiArco(Station.Lisboa, Station.Madrid, Color.Black, 1);
//		Mappa.aggiungiArco(Station.Lisboa, Station.Madrid, Color.Violet, 1);
//		Mappa.aggiungiArco(Station.Madrid, Station.Cadiz, Color.Green, 1);
//		Mappa.aggiungiArco(Station.Cadiz, Station.Venezia, Color.Red, 1);
//		Mappa.aggiungiArco(Station.Venezia, Station.Palermo, Color.Red, 1);
//		Mappa.aggiungiArco(Station.Palermo, Station.Cadiz, Color.Red, 1);
		//System.out.println("+++++++++:  "+Mappa.Tutte_Le_Rotte.size()+"  -  "+Mappa.Tutte_Le_Rotte);
	
		
		
		List<Plan> piani = Mappa.trovaTuttiIPercorsi(Station.Lisboa, Station.Venezia);
		System.out.println("Tratte trovate: "+piani.size());
		for (Plan piano : piani)
		{
//			System.out.println(piano.Destinazioni);
//			System.out.println(piano.Lista_Tratte_numerate);
//			System.out.println(piano.Totale_Pesi);
		}
		Plan piano = piani.get(0);
		System.out.println(piano.Destinations+ " "+ piano.Destinations.size());
		System.out.println(piano.Routes+ " "+ piano.Routes.size());
		System.out.println(piano.Total_Weights);
		
//		List<Plan> piani2 = Mappa.trovaTuttiIPercorsi_interi(Station.Lisboa, Station.Madrid);
//		System.out.println("------------");
//		System.out.println("------------");
//		System.out.println("------------");
//		System.out.println("Tratte trovate con percorsi interi: "+piani2.size());
//		for (Plan piano3 : piani2)
//		{
//			System.out.println(piano3.Destinazioni);
//			System.out.println(piano3.Lista_Tratte_numerate);
//			System.out.println(piano3.Totale_Pesi);
//		}

//		System.out.println("Destinazioni: "+piani.get(1).Destinazioni);
//		System.out.println(piani.get(1).Totale_Pesi);
		//System.out.println(piani.get(0).Totale_Peso_Locomotiva);**
		//System.out.println(piani.get(0).Totale_Peso_Panorama);**
		//System.out.println(piani.get(0).Totale_Peso_Velocita);**
//		System.out.println();+++
//		List<Plan> pian2i = Mappa.trovaTuttiIPercorsi(Station.Cadiz, Station.Lisboa);
//		System.out.println(pian2i.get(0).Destinazioni);
//		System.out.println(pian2i.get(0).Totale_Pesi);
//		System.out.println("------------");+++
		Route t = Mappa.get_Route(Station.Lisboa,Station.Madrid);
		//Functional_Goal scopo = new Functional_Goal("nome", "nome", 0, Station.Palermo);**
		if (t!= null)
		{
//			System.out.println(t.getDeparture() + " "+ t.getDestination());
		}
		
		List<Station> y = Mappa.get_Lista_Città_Vicine(Station.Madrid);
		
		//System.out.println(y);
//		Agent agente = new Agent();
//		
//		agente.Initialization(Mappa);
//		agente.Insert_Functional_Goal("1", "prova", 0.7, Station.Lisboa, Station.Roma);
//		agente.Start();
//		System.out.println("Game Over");
	}
		
	
	public static void Create_Map()
	{
		//Creo tutti i percorsi RED
		Mappa.Add_Arch(Station.Pamplona, Station.Marseille, Color.Red, 4);
		Mappa.Add_Arch(Station.Paris, Station.Bruxelles, Color.Red, 2);
		Mappa.Add_Arch(Station.Frankfurt, Station.Berlin, Color.Red, 3);
		Mappa.Add_Arch(Station.Wien, Station.Budapest, Color.Red, 1);
		Mappa.Add_Arch(Station.Zacrab, Station.Sarajevo, Color.Red, 3);
		Mappa.Add_Arch(Station.Warszawa, Station.Wilno, Color.Red, 3);
		Mappa.Add_Arch(Station.Kyiv, Station.Smolensk, Color.Red, 3);
		Mappa.Add_Arch(Station.Erzurum, Station.Sochi, Color.Red, 3);
		
		//Creo tutti i percorsi GREEN
		Mappa.Add_Arch(Station.Pamplona, Station.Paris, Color.Green, 4);
		Mappa.Add_Arch(Station.Dieppe, Station.Bruxelles, Color.Green, 2);
		Mappa.Add_Arch(Station.Frankfurt, Station.Essen, Color.Green, 2);
		Mappa.Add_Arch(Station.Zurich, Station.Venezia, Color.Green, 2);
		Mappa.Add_Arch(Station.Berlin, Station.Wien, Color.Green, 3);
		Mappa.Add_Arch(Station.Sarajevo, Station.Athina, Color.Green, 4);
		Mappa.Add_Arch(Station.Rica, Station.Wilno, Color.Green, 4);
		Mappa.Add_Arch(Station.Kharkov, Station.Rostow, Color.Green, 2);
		
		//Creo tutti i percorsi YELLOW
		Mappa.Add_Arch(Station.Madrid, Station.Barcelona, Color.Yellow, 2);
		Mappa.Add_Arch(Station.Paris, Station.Bruxelles, Color.Yellow, 2);
		Mappa.Add_Arch(Station.Amsterdam, Station.Essen, Color.Yellow, 3);
		Mappa.Add_Arch(Station.Zurich, Station.Munchen, Color.Yellow, 2);
		Mappa.Add_Arch(Station.Kodenilavn, Station.Stockholm, Color.Yellow, 3);
		Mappa.Add_Arch(Station.Berlin, Station.Warszawa, Color.Yellow, 4);
		Mappa.Add_Arch(Station.Bucaresti, Station.Costantinople, Color.Yellow, 3);
		Mappa.Add_Arch(Station.Wilno, Station.Smolensk, Color.Yellow, 3);
		
		//Creo tutti i percorsi ORANGE
		Mappa.Add_Arch(Station.Madrid, Station.Cadiz, Color.Orange, 3);
		Mappa.Add_Arch(Station.Brest, Station.Dieppe, Color.Orange, 2);
		Mappa.Add_Arch(Station.Edimburgh, Station.London, Color.Orange, 4);
		Mappa.Add_Arch(Station.Paris, Station.Frankfurt, Color.Orange, 3);
		Mappa.Add_Arch(Station.Munchen, Station.Wien, Color.Orange, 3);
		Mappa.Add_Arch(Station.Zacrab, Station.Budapest, Color.Orange, 2);
		Mappa.Add_Arch(Station.Smyrna, Station.Ancora, Color.Orange, 3);
		Mappa.Add_Arch(Station.Smolensk, Station.Moskva, Color.Orange, 2);
		
		//Creo tutti i percorsi BLACK
		Mappa.Add_Arch(Station.Madrid, Station.Pamplona, Color.Black, 3);
		Mappa.Add_Arch(Station.Brest, Station.Paris, Color.Black, 3);
		Mappa.Add_Arch(Station.Edimburgh, Station.London, Color.Black, 4);
		Mappa.Add_Arch(Station.Bruxelles, Station.Amsterdam, Color.Black, 1);
		Mappa.Add_Arch(Station.Frankfurt, Station.Berlin, Color.Black, 3);
		Mappa.Add_Arch(Station.Venezia, Station.Roma, Color.Black, 2);
		Mappa.Add_Arch(Station.Danzic, Station.Rica, Color.Black, 3);
		Mappa.Add_Arch(Station.Ancora, Station.Erzurum, Color.Black, 3);
		
		//Creo tutti i percorsi BLU
		Mappa.Add_Arch(Station.Lisboa, Station.Cadiz, Color.Blue, 2);
		Mappa.Add_Arch(Station.Pamplona, Station.Paris, Color.Blue, 4);		
		Mappa.Add_Arch(Station.Bruxelles, Station.Frankfurt, Color.Blue, 2);
		Mappa.Add_Arch(Station.Essen, Station.Berlin, Color.Blue, 2);
		Mappa.Add_Arch(Station.Munchen, Station.Venezia, Color.Blue, 2);		
		Mappa.Add_Arch(Station.Wien, Station.Warszawa, Color.Blue, 4);
		Mappa.Add_Arch(Station.Sofia, Station.Costantinople, Color.Blue, 3);
		Mappa.Add_Arch(Station.Wilno, Station.Petrocrad, Color.Blue, 4);
		
		//Creo tutti i percorsi VIOLET
		Mappa.Add_Arch(Station.Lisboa, Station.Madrid, Color.Violet, 3);
		Mappa.Add_Arch(Station.Pamplona, Station.Brest, Color.Violet, 4);		
		Mappa.Add_Arch(Station.Dieppe, Station.Paris, Color.Violet, 1);
		Mappa.Add_Arch(Station.Marseille, Station.Zurich, Color.Violet, 2);
		Mappa.Add_Arch(Station.Frankfurt, Station.Munchen, Color.Violet, 2);		
		Mappa.Add_Arch(Station.Berlin, Station.Warszawa, Color.Violet, 4);
		Mappa.Add_Arch(Station.Budapest, Station.Sarajevo, Color.Violet, 3);
		Mappa.Add_Arch(Station.Sofia, Station.Athina, Color.Violet, 3);
		
		//Creo tutti i percorsi WHITE
		Mappa.Add_Arch(Station.Madrid, Station.Pamplona, Color.White, 3);////aggiornare sulla mappa
		
		//Creo tutti i percorsi GRIGI
		// -----BLU
		Mappa.Add_Arch(Station.Marseille, Station.Barcelona, Color.Blue, 4);
		Mappa.Add_Arch(Station.Dieppe, Station.London, Color.Blue, 2);

		
		
		// -----GREEN
		Mappa.Add_Arch(Station.Pamplona, Station.Barcelona, Color.Green, 2);
		Mappa.Add_Arch(Station.Marseille, Station.Roma, Color.Green, 4);
		
		// -----RED
		Mappa.Add_Arch(Station.Paris, Station.Zurich, Color.Red, 3);
		
		
		// -----YELLOW
		Mappa.Add_Arch(Station.Marseille, Station.Paris, Color.Yellow, 4);
		Mappa.Add_Arch(Station.Dieppe, Station.London, Color.Yellow, 2);
		
		
		//-----VIOLET
		Mappa.Add_Arch(Station.London, Station.Amsterdam, Color.Violet, 2);
		
		//-----ORANGE
		Mappa.Add_Arch(Station.Roma, Station.Palermo, Color.Orange, 4);
		
	}
	
	
	public static void End_Game()
	{
		Game.Print("End_Game");
		System.exit(0);
	}
	
	public static void Print(Object Text)
	{
		System.out.println(Text);
	}
	
	public static void PrintLn(Object Text)
	{
		System.out.println(Text+"\n");
	}
	
	public static void PrintLn()
	{
		System.out.println();
	}
	
	public static void Sleep_Seconds(long seconds)
	{
		try {
			TimeUnit.SECONDS.sleep(seconds);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void Print_Scenario()
	{
		Game.Print("******* Scenario_Number:    --"+Scenario_Number+"-- *******");
	}
	
//	public void Print_Belief_Type(ArrayList<TBelief_Base> Beliefs)
//	{
//		ArrayList<TType_Beliefs> Beliefs_Type_list = new ArrayList<TType_Beliefs>();
//		for(TBelief_Base belief: Beliefs)
//		{
////			TPredicate Predicato = null;
//			Predicato = Map_Predicates.get(belief.get_Predicate_name());	
//			if (!Beliefs_Type_list.contains(belief.get_Type_Belief()))
//			{
//				Beliefs_Type_list.add(belief.get_Type_Belief());
//				//Game.Print("--- Belief Name: "+belief.get_Name()+" - related Predicate Name: "+Predicato.get_Name()+" - Belief Type: "+belief.get_Type_Belief());
//				Game.Print("--- Belief Type: "+belief.get_Type_Belief());
//			}
//		}
//	}
	
}
	