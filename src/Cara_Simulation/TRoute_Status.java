package Cara_Simulation;

public class TRoute_Status {
	
	private Integer Name;
	private TType_Route_Status Status;
	private String Used_By;
	
	public TRoute_Status(Integer name, TType_Route_Status status, String used_By)
	{
		this.Name = name;
		this.Status = status;
		this.Used_By = used_By;
	}
	
	
	
	public Integer get_Name() {
		return Name;
	}
	public void set_Name(Integer name) {
		Name = name;
	}
	public TType_Route_Status get_Status() {
		return Status;
	}
	public void set_Status(TType_Route_Status status) {
		Status = status;
	}
	public String get_Used_By() {
		return Used_By;
	}
	public void set_Used_By(String used_By) {
		Used_By = used_By;
	}
	
	
	
	
	

}
