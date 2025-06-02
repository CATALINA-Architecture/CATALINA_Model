package Cara_Simulation;

import java.util.ArrayList;

public class TInitial_SoW {

	private ArrayList<TRoute_Status> Map_Status;
	private ArrayList<TTrain> Initial_Train_pos;
	
	public TInitial_SoW()
	{
		Map_Status = new ArrayList<TRoute_Status>();
		Initial_Train_pos = new ArrayList<TTrain>();
	}

	public ArrayList<TRoute_Status> get_All_Map_Status() {
		return Map_Status;
	}

	public void set_Map_Status(ArrayList<TRoute_Status> map_Status) {
		Map_Status = map_Status;
	}

	public ArrayList<TTrain> get_All_Initial_Train_pos() {
		return Initial_Train_pos;
	}

	public void set_Initial_Train_pos(ArrayList<TTrain> initial_Train_pos) {
		Initial_Train_pos = initial_Train_pos;
	}
}