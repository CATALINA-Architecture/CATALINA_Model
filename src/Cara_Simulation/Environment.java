package Cara_Simulation;

import java.util.*;


public class Environment {
	//the even number route is the inserted ruote and odd number route is the back route. 
	private int Routes_Number;
	protected My_Collections My_Collection;
	
    public Map<Station, List<Route>> Neighbors;
    public ArrayList<Route> All_Routes; 

    public Environment() {
    	this.Routes_Number = -1;//So, the even number route is the inserted ruote and odd number route is the back route. 
    	My_Collection = new My_Collections();
    	
        Neighbors = new HashMap<>();
        All_Routes = new ArrayList<>();
    }
    
    public Environment clone()
    {
    	Environment Map2 = new Environment();
    	Map2.Routes_Number = this.Routes_Number;
    	Map2.All_Routes.addAll(this.All_Routes);
    	Map2.Neighbors.putAll(this.Neighbors);
    	return Map2;
    	
    }

    public void Add_Arch(Station Departure, Station Destination, Color Assigned_Color, 
    		int Steps) 
    {

    	Color_Route Color_Route = My_Collection.Color_Ruotes.get(Assigned_Color);

    	
        for(int i=0; i < Color_Route.Locomotives.size(); i++)
    	{
        	this.Routes_Number++;// increase the number of outbound routes
	    	Route Rotta = new Route(Departure, Destination, Assigned_Color, Steps, Color_Route.Locomotives.get(i),
					Color_Route.The_Panorama, Color_Route.The_Speed, this.Routes_Number);

	    	this.Routes_Number++;// increase the number of return trips
	    	Route Reverse_Route = new Route(Destination, Departure, Assigned_Color, Steps, Color_Route.Locomotives.get(i),
					Color_Route.The_Panorama, Color_Route.The_Speed, this.Routes_Number);
	    	Neighbors.computeIfAbsent(Departure, k -> new ArrayList<>()).add(Rotta);   	
	    	Neighbors.computeIfAbsent(Destination, k -> new ArrayList<>()).add(Reverse_Route); // Non orientato
	    	All_Routes.add(Rotta);
	    	All_Routes.add(Reverse_Route);
    	} 
    }

    public ArrayList<Plan> trovaTuttiIPercorsi(Station partenza, Station destinazione) {
        Set<Station> visitati = new HashSet<>();
        List<Station> percorsoCorrente = new ArrayList<>();
        List<List<Station>> tuttiPercorsi = new ArrayList<>();
        List<Integer> percorsoCorrente_tratte = new ArrayList<>();
        List<List<Integer>> tuttiPercorsi_tratte = new ArrayList<>();

        //dfs(partenza, destinazione, visitati, percorsoCorrente, tuttiPercorsi);
        
        dfs2(partenza, destinazione, visitati, percorsoCorrente, tuttiPercorsi, percorsoCorrente_tratte,
        		tuttiPercorsi_tratte,-1);
        
        
        ArrayList<Plan> Plans = new ArrayList<>();
        int i=0;
        /*for (List<Station> percorso : tuttiPercorsi) {
        	i++;
//        	System.out.println("Percorso: " +i);
//        	System.out.println("Percorso: " +i +" - "+ percorso);
            //double[] sommaPesi = calcolaSommaPesi(percorso);
        	EnumMap<TGQ_Goal, Double> Totale_Pesi = calcolaSommaPesi(percorso);
            Plans.add(new Plan());
            Plans.getLast().Inserisci_Percorso(percorso, Totale_Pesi);
        }*/

        //for (List<Integer> percorso_tratte : tuttiPercorsi_tratte) {
        for (i=0;i<tuttiPercorsi_tratte.size();i++) {
        	List<Station> percorso = tuttiPercorsi.get(i);
        	List<Integer> percorso_tratte = tuttiPercorsi_tratte.get(i);

//        	System.out.println("Percorso: " +i);
//        	System.out.println("Percorso: " +i +" - "+ percorso);
            //double[] sommaPesi = calcolaSommaPesi(percorso);
        	EnumMap<TType_Quality_Goal, Double> Totale_Pesi = calcolaSommaPesi_tratte(percorso_tratte);
        	Double Path_Time = Compute_Path_Time_Routes(percorso_tratte);
            Plans.add(new Plan());
            Plans.getLast().Inserisci_Percorso(percorso, percorso_tratte, Totale_Pesi, Path_Time);
        }
//        int visitati1=3;
        
//        i=0;
//        for (List<Integer> percorso2 : tuttiPercorsi_tratte) {        	
//            //double[] sommaPesi = calcolaSommaPesi(percorso);
//        	i++;
////            System.out.println("Percorso: " +i +" - "+ percorso2);
//        }
        return Plans;
    }
    
    /*public List<Plan> trovaTuttiIPercorsi_interi(Station partenza, Station destinazione) {
        Set<Integer> visitati = new HashSet<>();
        List<Integer> percorsoCorrente = new ArrayList<>();
        List<List<Integer>> tuttiPercorsi = new ArrayList<>();

        dfs_interi(partenza.ordinal(), destinazione.ordinal(), visitati, percorsoCorrente, tuttiPercorsi);
//      dfs_interi(Integer ,           Integer ,               Set<Integer> , List<Integer> , List<List<Integer>> ) {
//            visitati.add(nodoAttuale);
        
        List<Plan> Plans = new ArrayList<>();
        System.out.println("Percorsi trovati:  "+tuttiPercorsi.size());
        for (List<Integer> percorso : tuttiPercorsi) { 
        	System.out.println("+++++++++:  "+percorso);
            //double[] sommaPesi = calcolaSommaPesi(percorso);
        	EnumMap<TGQ_Goal, Double> Totale_Pesi = calcolaSommaPesi_interi(percorso);
            Plans.add(new Plan());
            //Plans.getLast().Inserisci_Percorso(percorso, sommaPesi[0], sommaPesi[1], sommaPesi[2]);
            Plans.getLast().Inserisci_Percorso_interi(Tutte_Le_Rotte, percorsoCorrente, Totale_Pesi);
            //System.out.println("Percorso: " + percorso + ", Somma pesi: " + Totale_Pesi);
        }
        return Plans;
    }*/

    protected EnumMap<TType_Quality_Goal, Double> calcolaSommaPesi(List<Station> percorso) {
        // Assumendo che il peso sia associato all'arco e non al nodo
        //int somma = 0;
    	EnumMap<TType_Quality_Goal, Double> Totale_Pesi = new EnumMap<>(TType_Quality_Goal.class); 
        //double[] Totale_Pesi = new double[3];
        double Peso_Locomotiva = 0;
        double Peso_Panorama = 0;
        double Peso_Velocita = 0;
        
        for (int i = 0; i < percorso.size() - 1; i++) {
            Station citta1 = percorso.get(i);
            Station citta2 = percorso.get(i + 1);
            for (Route arco : Neighbors.get(citta1)) {
                if (arco.getDestination() == citta2) {
                	;
                	Peso_Locomotiva += arco.get_Locomotive_Route();
                	Peso_Panorama += arco.get_Panorama_Route();
                	Peso_Velocita += arco.get_Speed_Route();
                    break;
                }
            }
        }
        Totale_Pesi.put(TType_Quality_Goal.TGQ_Locomotive, Peso_Locomotiva);
        Totale_Pesi.put(TType_Quality_Goal.TGQ_Panorama, Peso_Panorama);
        Totale_Pesi.put(TType_Quality_Goal.TGQ_Velocity, Peso_Velocita);
        return Totale_Pesi;
    }
    
    protected EnumMap<TType_Quality_Goal, Double> calcolaSommaPesi_tratte(List<Integer> percorso_tratte) {
        // Assumendo che il peso sia associato all'arco e non al nodo
        //int somma = 0;
    	EnumMap<TType_Quality_Goal, Double> Totale_Pesi = new EnumMap<>(TType_Quality_Goal.class); 
        //double[] Totale_Pesi = new double[3];
        double Peso_Locomotiva = 0;
        double Peso_Panorama = 0;
        double Peso_Velocita = 0;
        Double path_Time = 0.0;
        Route tratta;
        int indice;
        int Routes_Number = percorso_tratte.size();
//        int i=0;
//        for (int i = 0; i < Routes_Number - 1; i++)
        for (int i = 0; i < Routes_Number ; i++)
        {
            indice= percorso_tratte.get(i);
            tratta = this.All_Routes.get(indice);
        	Peso_Locomotiva += tratta.get_Locomotive_Route();
        	Peso_Panorama += tratta.get_Panorama_Route();
        	Peso_Velocita += tratta.get_Speed_Route();
        	path_Time += tratta.Get_Path_Time();
        }
//        Totale_Pesi.put(TType_Quality_Goal.TGQ_Locomotive, Peso_Locomotiva);
//        Totale_Pesi.put(TType_Quality_Goal.TGQ_Panorama, Peso_Panorama);
//        Totale_Pesi.put(TType_Quality_Goal.TGQ_Velocity, Peso_Velocita);
//        Totale_Pesi.put(TType_Quality_Goal.TGQ_Locomotive, Peso_Locomotiva/Routes_Number);
        Totale_Pesi.put(TType_Quality_Goal.TGQ_Locomotive, Peso_Locomotiva/Routes_Number);
        Totale_Pesi.put(TType_Quality_Goal.TGQ_Panorama, Peso_Panorama/Routes_Number);
        Totale_Pesi.put(TType_Quality_Goal.TGQ_Velocity, Peso_Velocita/Routes_Number);
        
        tratta = null;
        return Totale_Pesi;
    }
    
    protected Double Compute_Path_Time_Routes(List<Integer> percorso_tratte)
    {
        Double path_Time = 0.0;
        Route tratta;
//        int indice;
        //for (int i = 0; i < percorso_tratte.size() - 1; i++)
        for(int indice: percorso_tratte)
        {
//            indice= percorso_tratte.get(i);
            tratta = this.All_Routes.get(indice);
        	path_Time += tratta.Get_Path_Time();
        }
        tratta = null;
        return path_Time;
    }
    
    protected EnumMap<TType_Quality_Goal, Double> calcolaSommaPesi_interi(List<Integer> percorso) {
        // Assumendo che il peso sia associato all'arco e non al nodo
        //int somma = 0;
    	EnumMap<TType_Quality_Goal, Double> Totale_Pesi = new EnumMap<>(TType_Quality_Goal.class); 
    	Route arco;
        //double[] Totale_Pesi = new double[3];
        double Peso_Locomotiva = 0;
        double Peso_Panorama = 0;
        double Peso_Velocita = 0;
        
        for (int i = 0; i < percorso.size() - 1; i++) {
        	int indice_tratta_del_percorso = percorso.get(i);
        	arco = this.All_Routes.get( indice_tratta_del_percorso );
        	Peso_Locomotiva += arco.get_Locomotive_Route();
        	Peso_Panorama += arco.get_Panorama_Route();
        	Peso_Velocita += arco.get_Speed_Route();
        }
        Totale_Pesi.put(TType_Quality_Goal.TGQ_Locomotive, Peso_Locomotiva);
        Totale_Pesi.put(TType_Quality_Goal.TGQ_Panorama, Peso_Panorama);
        Totale_Pesi.put(TType_Quality_Goal.TGQ_Velocity, Peso_Velocita);
        return Totale_Pesi;
    }

    protected void dfs(Station nodoAttuale, Station destinazione, Set<Station> visitati, List<Station> percorsoCorrente, List<List<Station>> tuttiPercorsi) {
        visitati.add(nodoAttuale);
        percorsoCorrente.add(nodoAttuale);

        if (nodoAttuale == destinazione) {
            tuttiPercorsi.add(new ArrayList<>(percorsoCorrente));
        } else {
            for (Route arco : Neighbors.getOrDefault(nodoAttuale, Collections.emptyList())) {
                if (!visitati.contains(arco.getDestination())) {
                    dfs(arco.getDestination(), destinazione, visitati, percorsoCorrente, tuttiPercorsi);
                }
            }
        }

        percorsoCorrente.remove(percorsoCorrente.size() - 1);
        visitati.remove(nodoAttuale);
    }
    
    protected void dfs2(Station nodoAttuale, Station destinazione, Set<Station> visitati, List<Station> percorsoCorrente, List<List<Station>> tuttiPercorsi,
    		List<Integer> percorsoCorrente_tratte, List<List<Integer>> tuttiPercorsi_tratte, Integer Indice_Arco) {
        visitati.add(nodoAttuale);
        percorsoCorrente.add(nodoAttuale);
        if (Indice_Arco>-1)
        {
        	percorsoCorrente_tratte.add(Indice_Arco);
        }
        	
        

        if (nodoAttuale == destinazione) {
            tuttiPercorsi.add(new ArrayList<>(percorsoCorrente));
            tuttiPercorsi_tratte.add(new ArrayList<>(percorsoCorrente_tratte));
        } else {
            for (Route arco : Neighbors.getOrDefault(nodoAttuale, Collections.emptyList())) {
                if (!visitati.contains(arco.getDestination())) {
                	
                	Indice_Arco = arco.Route_Number;
                    dfs2(arco.getDestination(), destinazione, visitati, percorsoCorrente, tuttiPercorsi,
                    		percorsoCorrente_tratte, tuttiPercorsi_tratte, Indice_Arco);
                    if (Indice_Arco>-1)
                    {
                    	percorsoCorrente_tratte.remove(Indice_Arco);
                    }
                }
            }
        }

//        if (Indice_Arco>-1)
//        {
//        	percorsoCorrente_tratte.remove(Indice_Arco);
//        }
        percorsoCorrente.remove(percorsoCorrente.size() - 1);
        visitati.remove(nodoAttuale);
    }
    
//    protected void dfs_interi(Station nodoAttuale, Station destinazione, Set<Station> visitati, List<Station> percorsoCorrente, List<List<Station>> tuttiPercorsi) {
    	protected void dfs_interi(Integer nodoAttuale, Integer destinazione, Set<Integer> visitati, List<Integer> percorsoCorrente, List<List<Integer>> tuttiPercorsi) {
        visitati.add(nodoAttuale);
        
        percorsoCorrente.add(nodoAttuale);

        if (nodoAttuale == destinazione) {
            tuttiPercorsi.add(new ArrayList<>(percorsoCorrente));
        } else {
            //for (Route arco : Vicini.getOrDefault(nodoAttuale, Collections.emptyList())) {
        	for (Route arco : Neighbors.getOrDefault(Station.values()[nodoAttuale], Collections.emptyList())) {
                if (!visitati.contains(arco.getDestination().ordinal())) {
                	dfs_interi(arco.getDestination().ordinal(), destinazione, visitati, percorsoCorrente, tuttiPercorsi);
                }
            }
        }

        percorsoCorrente.remove(percorsoCorrente.size() - 1);
        visitati.remove(nodoAttuale);
    }
    
    public Route get_Route(Station nodoAttuale, Station destinazione)
    {
    	Route Tratta = null;
    	
    	if (nodoAttuale != destinazione)   		
    	{
    		
    		List<Route> Tutti_i_Vicini = Neighbors.getOrDefault(nodoAttuale, Collections.emptyList());
    		int i = 0;
    		while ( i < Tutti_i_Vicini.size() )
    		{
    			Route Tratta_appoggio = Tutti_i_Vicini.get(i);
    			if( Tratta_appoggio.getDestination() == destinazione)
    			{
    				Tratta = Tratta_appoggio;
    				break;
    			}
    			i++;
    		}
    		/*
    		List<Route> Tutti_i_Vicini = Vicini.getOrDefault(nodoAttuale, Collections.emptyList());
    		while (int i=0;i<Tutti_i_Vicini.)
        	
        	System.out.println( "Numero vicini: "+Tutti_i_Vicini.size());
        	System.out.println( Tutti_i_Vicini.getFirst().getDestination());*/
    	}
    	
    	
    	return Tratta;
    }
    
    public Route get_Route(Integer index)
    {
    	Route route = null;
    	if (index < this.All_Routes.size())
    	{
    		route = this.All_Routes.get(index);
    	}
    	
    	return route;
    }
    
    public List<Station> get_Lista_Città_Vicine(Station nodoAttuale)
    {
    	List<Station> I_Vicini = new ArrayList<>();

		List<Route> Tutti_i_Vicini = Neighbors.getOrDefault(nodoAttuale, Collections.emptyList());
		//while(Tutti_i_Vicini.hasNext()){
		int i=0;
		for (Route Tratta: Tutti_i_Vicini) {
			/*System.out.println(i);
			System.out.println( "getDestination: "+Tratta.getDestination());
			System.out.println( "getPeso_Locomotiva: "+Tratta.getPeso_Locomotiva());
			System.out.println( "getPeso_Panorama: "+Tratta.getPeso_Panorama());
			System.out.println( "getPeso_Velocita: "+Tratta.getPeso_Velocita());
			System.out.println( "getPeso_Velocita_per_rettangolo: "+Tratta.getPeso_Velocita_per_rettangolo());
			System.out.println("---------------");*/
			I_Vicini.add(Tratta.getDestination());
			i++;
    	}   	
    	//System.out.println( "Numero vicini: "+Tutti_i_Vicini.size());
    	//System.out.println( Tutti_i_Vicini.getFirst().getDestination());
    	return I_Vicini;
    }
    
    public List<Route> get_Tratte_Città_Vicine(Station nodoAttuale)
    {
		List<Route> Tutti_i_Vicini = Neighbors.getOrDefault(nodoAttuale, Collections.emptyList());
    	return Tutti_i_Vicini;
    }
    
    //this function return the routes number, in according to the index of route, and it is "minus 1" to size of all routes list
    //because Numero_Tratte starts by "-1"
    public Integer Get_Routes_Number()
    {
    	return Routes_Number;
    }
    
    /**
     * Get Back Route to a specific Route
     * @param A_Route
     * @return
     */
    public int Get_Specular_Route(int A_Route)
    {
    	int Specular_Integer_Route =0;
		if (A_Route % 2 == 0)
		{
			Specular_Integer_Route = A_Route + 1;
			
		}
		else
		{
			Specular_Integer_Route = A_Route - 1;
		}
    	return Specular_Integer_Route;
    }
    
}