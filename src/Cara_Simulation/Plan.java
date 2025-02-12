package Cara_Simulation;

import java.util.ArrayList;
import java.util.EnumMap; 
import java.util.List;

public class Plan {
	
	 public ArrayList<Station> Destinations;
	 public EnumMap<TType_Quality_Goal, Double> Total_Weights = new EnumMap<>(TType_Quality_Goal.class); 
	 public ArrayList<Integer> Routes;
	 public Double Path_Time;
	 public TDouble_Object Next_Position;
//	 public double Totale_Peso_Locomotiva;
//	 public double Totale_Peso_Panorama;
//	 public double Totale_Peso_Velocita;

	 public Plan()
	 {
		 this.Destinations = new ArrayList<Station>();
		 this.Routes = new ArrayList<Integer>();
		 this.Path_Time = 0.0;
		 this.Next_Position = new TDouble_Object();
	 }
	 
	 //public void Inserisci_Percorso( List<Station> Destinazioni, double Totale_Peso_Locomotiva, double Totale_Peso_Panorama, 
	 		//double Totale_Peso_Velocita)
	 public void Inserisci_Percorso( List<Station> Destinazioni, List<Integer> Tratte, EnumMap<TType_Quality_Goal, Double> Pesi
			 , Double path_Time)

	 {
		 this.Destinations.addAll(Destinazioni);
		 //this.Totale_Peso_Locomotiva = Totale_Peso_Locomotiva;
		 //this.Totale_Peso_Panorama = Totale_Peso_Panorama;
		 //this.Totale_Peso_Velocita = Totale_Peso_Velocita;
		 this.Routes.addAll(Tratte);
		 this.Total_Weights.putAll(Pesi);
		 this.Path_Time = path_Time;
	 }
	 
	 public void Inserisci_Percorso_interi( List<Route> Tutte_Le_Rotte, List<Integer> Destinazioni_numerate, EnumMap<TType_Quality_Goal, Double> Pesi
			 , Double path_Time)

	 {
		 List<Station> Destinazioni = new ArrayList<Station>();
		 if (Destinazioni_numerate.size()>0)
		 {
			 int i=0;
			 Destinazioni.add(Tutte_Le_Rotte.get(0).getDeparture());
			 for (i=0;i<Destinazioni_numerate.size();i++)
			 {
				 Destinazioni.add(Tutte_Le_Rotte.get(i).getDestination());
			 }
		 }
		 
		 
		 this.Routes.addAll(Destinazioni_numerate);
		 this.Destinations.addAll(Destinazioni);
		 this.Total_Weights.putAll(Pesi);
		 this.Path_Time = path_Time;
	 }
	 
	 public void Copia_Plan(Plan Piano)
	 {
		 this.Routes.addAll(Piano.Routes);
		 this.Destinations.addAll(Piano.Destinations);
		 this.Total_Weights.putAll(Piano.Total_Weights);
		 this.Path_Time = Piano.Path_Time;
	 }
	 
	 public TDouble_Object Get_Next_Position()
	 {
		 return this.Next_Position;				 
	 }
	 
	 public void Set_Next_Position(TDouble_Object next_Position)
	 {
		 this.Next_Position.set_Object_First(next_Position.get_Object_First());
		 this.Next_Position.set_Object_Second(next_Position.get_Object_Second());
	 }
	 
	 public ArrayList<Integer> Get_Routes()
	 {
		 return this.Routes;
	 }
	 

}
