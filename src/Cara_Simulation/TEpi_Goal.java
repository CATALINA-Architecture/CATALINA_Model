package Cara_Simulation;

enum TTipi_Goals{
	TTG_Functional_Goal,
	TTG_Epistemic_Goal,
	TTG_Quality_Goal,
	TTG_Green_Goal
}

public enum TEpi_Goal {
	TEG_Busy_Route,   //Occupata da un utente
	TEG_Danger_Route, //Problema generico sulla tratta che potrebbe rallentare o bloccare il percorso 
	TEG_Monitor_Route,//Monitorare se ci sono 
	TEG_Investigate_Opponent_Strategy
}

//Tipo per il Green Goal e il Quality Goal

enum TAI_Goal{
	TAll_Goal,
	TInibited_Goal
}