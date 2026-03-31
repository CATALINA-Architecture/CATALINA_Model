package com.Catalina_Model.Catalina_V_0_3;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class TLong_Memory {
	
	private TAgent Agent;
	
	private long Inc_Attentional_Desires_Number = 0;
//	private long Inc_Practical_Desires_Number = 0;
//	private long Inc_Epistemic_Desires_Number = 0;
	private Integer Inc_Beliefs_Number = 0;
	private Integer Inc_Predicates_Number = 0;
	private Integer Inc_Green_Desires_Number = 0;
	private Integer Inc_Quality_Desires_Number = 0;
	
	private TGeneric_Protected_Object<Long> Inc_Practical_Desires_Number;
	private TGeneric_Protected_Object<Long> Inc_Epistemic_Desires_Number;
	private TConscious_Data  Inhibited_Conscious_Data;
	
	
	//Desires Properties
//	private ArrayList<TAttentional_Desire> Attentional_Desires; 
	private TGeneric_Auto_Named_and_Protected_List<TAttentional_Desire> Attentional_Desires;
	private TGeneric_Auto_Named_and_Protected_List<TAttentional_Desire> Satisfied_Attentional_Desires;
//	private ArrayList<TAttentional_Desire> Inhibited_Attentional_Desires; 
	private TGeneric_Protected_List<TAttentional_Desire> Inhibited_Attentional_Desires;
	
	// Green_Desires Properties
	private TGeneric_Auto_Named_and_Protected_List<TGreen_Desire> Green_Desires;
	private HashMap<String, TGreen_Desire> Map_Green_Desires;
	
	// Quality_Desires Properties
	private TGeneric_Auto_Named_and_Protected_List<TQuality_Desire> Quality_Desires;
	private HashMap<String, TQuality_Desire> Map_Quality_Desires;
	
	// Epistemic_Desires Properties
//	private TGeneric_Auto_Named_and_Protected_List<TEpistemic_Desire> Epistemic_Desires;
		
	
	
	//Beliefs Properties
	private TGeneric_Auto_Named_and_Protected_List<TBelief> Beliefs;
	private HashMap<String, TBelief> Map_Beliefs;
//	private ConcurrentHashMap<String, TBelief> Map_Beliefs;
	
	private TGeneric_Protected_List<TBelief> Inhibited_Beliefs;
	private TGeneric_Protected_List<TBelief> Important_Beliefs;
	
	//Regions Properties
	private TGeneric_Protected_List<TRegion> Regions;
	private HashMap<String, TRegion> Map_Regions;
	private TGeneric_Protected_List<TRegion> Inhibited_Regions;
	
	//Predicates
	private HashMap<String, TPredicate> Map_Predicates;
	private TGeneric_Auto_Named_and_Protected_List<TPredicate> Predicates;
	
	//Locks Properties
	private final ReentrantReadWriteLock Lock_Attentional_Desires = new ReentrantReadWriteLock();
	private final ReentrantReadWriteLock Lock_Inhibited_Data = new ReentrantReadWriteLock();
	
	//Read Locks Properties
	private final ReentrantReadWriteLock.ReadLock Read_Lock_Attentional_Desires = Lock_Attentional_Desires.readLock();
	private final ReentrantReadWriteLock.ReadLock Read_Lock_Inhibited_Data = Lock_Inhibited_Data.readLock();
	//Write Locks Properties
	private final ReentrantReadWriteLock.WriteLock Write_Lock_Attentional_Desires = Lock_Attentional_Desires.writeLock();
	private final ReentrantReadWriteLock.WriteLock Write_Lock_Inhibited_Data = Lock_Inhibited_Data.writeLock();
	private final HashSet<String> Handled_Types;
	private TExecutive_Memory_Maintenance_Function Executive_Memory_Maintenance_Function;
	
	public TLong_Memory(TAgent agent) 
	{
		Inc_Practical_Desires_Number = new TGeneric_Protected_Object<Long>(0L);
		Inc_Epistemic_Desires_Number = new TGeneric_Protected_Object<Long>(0L);
		
		this.Agent = agent;
		//this.Executive_Memory_Maintenance_Function = Memory_Maintenance_Function;
		
		this.Attentional_Desires = new TGeneric_Auto_Named_and_Protected_List<TAttentional_Desire>();
		this.Satisfied_Attentional_Desires = new TGeneric_Auto_Named_and_Protected_List<TAttentional_Desire>();
		this.Green_Desires = new TGeneric_Auto_Named_and_Protected_List<TGreen_Desire>();
		this.Map_Green_Desires = new HashMap<String, TGreen_Desire>();
		this.Quality_Desires = new TGeneric_Auto_Named_and_Protected_List<TQuality_Desire>();
		this.Map_Quality_Desires = new HashMap<String, TQuality_Desire>();
		
		this.Inhibited_Attentional_Desires = new TGeneric_Protected_List<TAttentional_Desire>();
		this.Beliefs = new TGeneric_Auto_Named_and_Protected_List<TBelief>();
		this.Map_Beliefs = new HashMap<String, TBelief>();
//		this.Map_Beliefs = new ConcurrentHashMap<String, TBelief>();
		this.Inhibited_Beliefs = new TGeneric_Protected_List<TBelief>();
		this.Important_Beliefs = new TGeneric_Protected_List<TBelief>();
		
		this.Regions = new TGeneric_Protected_List<TRegion>();
		this.Map_Regions = new HashMap<String, TRegion>();
		this.Inhibited_Regions = new TGeneric_Protected_List<TRegion>();
		
		this.Inhibited_Conscious_Data = new TConscious_Data();
		
		//Data to read from a related file
		//The list of handled types when I load values data for Predicate, Beliefs, ecc...
		this.Handled_Types = new HashSet<String>();
		Handled_Types.add("Boolean");
		Handled_Types.add("Byte");
		Handled_Types.add("Char");
		Handled_Types.add("Double");
		Handled_Types.add("Float");
		Handled_Types.add("Integer");
		Handled_Types.add("Long");
		Handled_Types.add("Short");
		Handled_Types.add("String");
		
		this.Predicates = new TGeneric_Auto_Named_and_Protected_List<TPredicate>();
		this.Map_Predicates = new HashMap<String, TPredicate>();
		
	}
	
	public ArrayList<TAttentional_Desire> Get_All_Attentional_Desires()
	{
		return this.Attentional_Desires.Read();
	}
	
	public ArrayList<TAttentional_Desire> Get_All_Not_Satisfied_Attentional_Desires()
	{
		ArrayList<TAttentional_Desire> result = new ArrayList<TAttentional_Desire>();
		for(TAttentional_Desire Desire: this.Attentional_Desires.Read())
		{
			if(Desire.Get_Status_Desire() != TType_Status_Desire.Satisfied)
			{
				result.add( Desire );
			}
		}
		return result;
	}
	
	public ArrayList<TAttentional_Desire> Get_All_Satisfied_Attentional_Desires()
	{
		return this.Satisfied_Attentional_Desires.Read();
	}
	
	public void Add_Satisfied_Attentional_Desires(ArrayList<TAttentional_Desire> Desires)
	{
		this.Satisfied_Attentional_Desires.Add_All( Desires );
	}
	
	public void Add_Attentional_Desires(ArrayList<TAttentional_Desire> Desires)
	{
		this.Attentional_Desires.modifyList(list -> 
		{ // 'list' è la lista interna protetta
			if (Desires != null && !Desires.isEmpty()) 
			{
				long Temp_Long;
				for (TAttentional_Desire desire : Desires) 
				{
					Inc_Attentional_Desires_Number = this.Attentional_Desires.Inc_Number.incrementAndGet();
					
					switch(desire)
					{
						case TPractical_Desire Practical_Desire ->
							{
								//Inc_Practical_Desires_Number++;
								Temp_Long = this.Inc_Practical_Desires_Number.Read();
								Temp_Long++;
								this.Inc_Practical_Desires_Number.Write(Temp_Long);
								if (desire.Get_Name() == null || desire.Get_Name().isEmpty() ||
										desire.Get_Name().equals("")) 
								{
									desire.Set_Name("Practical Desire "+ Temp_Long);
								}
							}
						case TEpistemic_Desire Epistemic_Desire ->
							{
								Temp_Long = this.Inc_Epistemic_Desires_Number.Read();
								Temp_Long++;
								this.Inc_Epistemic_Desires_Number.Write(Temp_Long);
								if (desire.Get_Name() == null || desire.Get_Name().isEmpty() ||
										desire.Get_Name().equals("")) 
								{
									desire.Set_Name("Epistemic Desire "+ Temp_Long);
								}
							}
					
						default -> throw new IllegalArgumentException("Unexpected value on Add_Attentional_Desire: " + desire);
						
					}
					list.add(desire);
					
				}
			}
		});
	}
	
	public void Add_Inhibited_Practical_Desires(ArrayList<TPractical_Desire_Data> Desires_Data)
	{
		this.Attentional_Desires.modifyList(list -> 
		{ // 'list' è la lista interna protetta
			if (Desires_Data != null && !Desires_Data.isEmpty()) 
			{
				ArrayList<TAttentional_Desire> Desires = new ArrayList<TAttentional_Desire>();
				long Temp_Long;
				HashSet<TBelief> Preconditions = new HashSet<TBelief>();
				for (TPractical_Desire_Data Practical_Desire_Data : Desires_Data) 
				{
					Desires.add( Practical_Desire_Data.Get_Practical_Desire() );
					TPractical_Desire Practical_Desire = Practical_Desire_Data.Get_Practical_Desire();
					Inc_Attentional_Desires_Number = this.Attentional_Desires.Inc_Number.incrementAndGet();
					//Inc_Practical_Desires_Number++;
					Temp_Long = this.Inc_Practical_Desires_Number.Read();
					Temp_Long++;
					this.Inc_Practical_Desires_Number.Write(Temp_Long);
					if (Practical_Desire.Get_Name() == null || Practical_Desire.Get_Name().isEmpty() ||
							Practical_Desire.Get_Name().equals("")) 
					{
						Practical_Desire.Set_Name("Practical Desire "+ Temp_Long);
					}
					//Add Preconditions to Global_Work_Space
					
//					Map_Beliefs
					if (Practical_Desire.Get_Trigger_Condition() != null)
					{
						for(String Belief_Name: Practical_Desire.Get_Trigger_Condition().Get_Beliefs_Names())
						{
							Preconditions.add( this.Map_Beliefs.get( Belief_Name ) );
						}
					}
				}
				/**
				 * Preliminary Actions
				 * 1) I have to associate functions:
				 * 		- Means-End Reasoner function for the Active Desires
				 * 		- A Inhibition Function to compute Inhibited Beliefs
				 * 		- A Inhibition Function to compute Inhibited Regions
				 * 2) I update Preconditions in Global Workspace for Inhibited Desires
				 * 
				 */
				
				/*
				 * Associate Functions
				 */
				this.Executive_Memory_Maintenance_Function.
					Associate_Functions_to_Inhibited_Desires( Desires_Data );
				
				/*
				 * Associate Precondition
				 */
				this.Executive_Memory_Maintenance_Function.
						Add_Uninhibited_Preconditions( Preconditions ) ;
				
				list.addAll( Desires );
				Desires.clear();
			}
		});
	}
	
	public void Remove_Attentional_Desires(ArrayList<TAttentional_Desire> Desires)
	{
		this.Attentional_Desires.Remove_All( Desires );
	}
	
	private void Add_Attentional_Desire(TAttentional_Desire desire)
	{
		this.Attentional_Desires.modifyList(list -> 
		{ // 'list' è la lista interna protetta
					Inc_Attentional_Desires_Number = this.Attentional_Desires.Inc_Number.incrementAndGet();
					
					switch(desire)
					{
						case TPractical_Desire Practical_Desire ->
							{
								long Temp_Long = this.Inc_Practical_Desires_Number.Read();
								Temp_Long++;
								this.Inc_Practical_Desires_Number.Write(Temp_Long);
								if (desire.Get_Name() == null || desire.Get_Name().isEmpty() ||
										desire.Get_Name().equals("")) 
								{
									desire.Set_Name("Practical Desire "+ Temp_Long);
								}
							}
						case TEpistemic_Desire Epistemic_Desire ->
							{
								long Temp_Long = this.Inc_Epistemic_Desires_Number.Read();
								Temp_Long++;
								this.Inc_Epistemic_Desires_Number.Write(Temp_Long);
								if (desire.Get_Name() == null || desire.Get_Name().isEmpty() ||
										desire.Get_Name().equals("")) 
								{
									desire.Set_Name("Epistemic Desire "+ Temp_Long);
								}
							}
					
						default -> throw new IllegalArgumentException("Unexpected value on Add_Attentional_Desire: " + desire);
						
					}
					list.add(desire);
		});
	}
	
	public ArrayList<TAttentional_Desire> Get_Inhibited_Attentional_Desires()
	{
		this.Read_Lock_Inhibited_Data.lock();
		try 
		{
			return this.Inhibited_Attentional_Desires.Read();
		} 
		finally 
		{
			this.Read_Lock_Inhibited_Data.unlock();
		}
	}
	

	public ArrayList<TBelief> Get_All_Beliefs()
	{
		return this.Beliefs.Read();
	}
	
	public HashMap<String, TBelief> Get_All_Map_Beliefs()
	{
		return new HashMap<>(this.Map_Beliefs);
	}
	
	
	public void Add_Beliefs(ArrayList<TBelief> beliefs)
	{
		this.Beliefs.modifyList(list -> { // 'list' è la lista interna protetta
            if (beliefs != null && !beliefs.isEmpty()) 
            {
                for (TBelief belief : beliefs) 
                {
                    long newId = this.Beliefs.Inc_Number.incrementAndGet();

                    belief.Set_Belief_ID(newId);

                    if (belief.Get_Name() == null || belief.Get_Name().isEmpty() ||
                    		belief.Get_Name().equals("")) 
                    {
                        belief.Set_Name("B_" + newId);
                    }

                    list.add(belief);
                    this.Map_Beliefs.put( belief.Get_Name(), belief);
                }
            }
        });
	}
	
	public void Add_Belief(TBelief belief)
	{
		this.Beliefs.modifyList(list -> { // 'list' è la lista interna protetta
            {
                    long newId = this.Beliefs.Inc_Number.incrementAndGet();

                    belief.Set_Belief_ID(newId);

                    if (belief.Get_Name() == null || belief.Get_Name().isEmpty() ||
                    		belief.Get_Name().equals("")) 
                    {
                        belief.Set_Name("B_" + newId);
                    }

                    list.add(belief);
                    this.Map_Beliefs.put( belief.Get_Name(), belief);
            }
        });
	}
	
	public void Add_Predicate(TPredicate predicate)
	{
		this.Predicates.modifyList(list -> { // 'list' è la lista interna protetta
            {
                    long newId = this.Predicates.Inc_Number.incrementAndGet();
                    predicate.set_Predicate_ID( newId );
                    list.add(predicate);
                    this.Map_Predicates.put( predicate.Get_Name(), predicate);
            }
        });
		
	}
	
	public void Add_PredicGates(ArrayList<TPredicate> predicates)
	{
		this.Predicates.modifyList(list -> { // 'list' è la lista interna protetta
            if (predicates != null && !predicates.isEmpty()) 
            {
                for (TPredicate Predicate : predicates) 
                {
                    long newId = this.Predicates.Inc_Number.incrementAndGet();
                    Predicate.set_Predicate_ID( newId );

                    list.add(Predicate);
                    this.Map_Predicates.put( Predicate.Get_Name(), Predicate);
                }
            }
        });
	}
	
	public ArrayList<TBelief> Get_Inhibited_Beliefs()
	{
		this.Read_Lock_Inhibited_Data.lock();
		try 
		{
			return this.Inhibited_Beliefs.Read();
		} 
		finally 
		{
			this.Read_Lock_Inhibited_Data.unlock();
		}
	}
	
	public ArrayList<TRegion> Get_All_Regions()
	{
		return this.Regions.Read();
	}
	
	public HashMap<String, TRegion>  Get_All_Map_Regions()
	{
		return this.Map_Regions;
	}
	
	public ArrayList<TRegion> Get_Inhibited_Regions()
	{
		this.Read_Lock_Inhibited_Data.lock();
		try 
		{
			return this.Inhibited_Regions.Read();
		} 
		finally 
		{
			this.Read_Lock_Inhibited_Data.unlock();
		}
	}
	
	public void Add_Region(TRegion region)
	{
		this.Regions.Add(region);
		this.Map_Regions.put(region.Get_Name(), region);
	}
	
	public void Add_Regions(ArrayList<TRegion> regions)
	{
		this.Regions.Add_All(regions);
		for(TRegion region: regions)
		{
			this.Map_Regions.put(region.Get_Name(), region);
		}
	}
	
//	public void Add_Inhibited_Region(TRegion inhibited_region)
//	{
//		this.Inhibited_Regions.Add(inhibited_region);
//	}
	
//	public void Add_Inhibited_Regions(ArrayList<TRegion> inhibited_regions)
//	{
//		this.Inhibited_Regions.Add_All(inhibited_regions);
//	}
	
	public void Clear_Beliefs()
	{
		this.Beliefs.Clear();
	}
	
	public void Clear_Attentional_Desires()
	{
		this.Attentional_Desires.Clear();
	}
	
	public void Clear_Regions()
	{
		this.Regions.Clear();
		this.Map_Regions.clear();
	}
	
	public void Set_Inhibited_Data(ArrayList<TAttentional_Desire> inhibited_desires,
			ArrayList<TBelief> inhibited_beliefs,
			ArrayList<TRegion> inhibited_regions)
	{
		this.Write_Lock_Inhibited_Data.lock();
		try 
		{
			this.Inhibited_Attentional_Desires.Clear();
			this.Inhibited_Beliefs.Clear();
			this.Inhibited_Regions.Clear();
			
			this.Inhibited_Attentional_Desires.Set_List( inhibited_desires );
			this.Inhibited_Beliefs.Set_List( inhibited_beliefs );			
			this.Inhibited_Regions.Set_List( inhibited_regions );
			this.Inhibited_Conscious_Data.Write(null, inhibited_beliefs, inhibited_desires, inhibited_regions);
			
		} 
		finally 
		{
			Write_Lock_Inhibited_Data.unlock();
		}
		
	}
	
	public void Add_Inhibited_Data(ArrayList<TAttentional_Desire> inhibited_desires,
			ArrayList<TBelief> inhibited_beliefs,
			ArrayList<TRegion> inhibited_regions)
	{
		this.Write_Lock_Inhibited_Data.lock();
		try 
		{
			
			this.Inhibited_Attentional_Desires.Add_All( inhibited_desires );
			this.Inhibited_Beliefs.Add_All( inhibited_beliefs );
			this.Inhibited_Regions.Add_All( inhibited_regions );
			
			this.Inhibited_Conscious_Data.Write(null, this.Inhibited_Beliefs.Read(), 
					this.Inhibited_Attentional_Desires.Read(), this.Inhibited_Regions.Read());
			
			
		} 
		finally 
		{
			Write_Lock_Inhibited_Data.unlock();
		}
	}
	
	public void Add_Inhibited_Desire(ArrayList<TAttentional_Desire> inhibited_desires)
	{
		this.Write_Lock_Inhibited_Data.lock();
		try 
		{
			
			this.Inhibited_Attentional_Desires.Add_All( inhibited_desires );
			this.Inhibited_Conscious_Data.Write(null, this.Inhibited_Beliefs.Read(), 
					this.Inhibited_Attentional_Desires.Read(), this.Inhibited_Regions.Read());
		} 
		finally 
		{
			Write_Lock_Inhibited_Data.unlock();
		}
	}
	
	public void Remove_Inhibited_Data(ArrayList<TAttentional_Desire> inhibited_desires,
			ArrayList<TBelief> inhibited_beliefs,
			ArrayList<TRegion> inhibited_regions)
	{
		this.Write_Lock_Inhibited_Data.lock();
		try 
		{
			
			this.Inhibited_Attentional_Desires.Remove_All( inhibited_desires );
			this.Inhibited_Beliefs.Remove_All( inhibited_beliefs );
			this.Inhibited_Regions.Remove_All( inhibited_regions );
			
			this.Inhibited_Conscious_Data.Write(null, this.Inhibited_Beliefs.Read(), 
					this.Inhibited_Attentional_Desires.Read(), this.Inhibited_Regions.Read());
			
			
		} 
		finally 
		{
			Write_Lock_Inhibited_Data.unlock();
		}
	}
	
	private Document Get_Document_Xml(String Filename)
	{
		Document doc = null;
		if ( Filename == null || Filename.equals(""))
	    {
			System.out.print("TLong_Memory - error in Filename: "+Filename);
	    	return doc;
	    }
		
		try 
		{
            // 1. Ottieni un DocumentBuilderFactory
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            // Optional: Rendere il parser più sicuro per prevenire attacchi XXE
            factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
            factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            factory.setXIncludeAware(false);
            factory.setExpandEntityReferences(false);


            // 2. Crea un DocumentBuilder
            DocumentBuilder builder = factory.newDocumentBuilder();

            // 3. Parsifica il file XML nel Document
            File xmlFile = new File( Filename);
            if (!xmlFile.exists()) 
            {
                System.err.println("Error: The Filename '" + Filename + "' doesn't exist!");
                return doc;
            }
            
            doc = builder.parse(Filename);
   
            // 4. Normalizza il Document (utile per rimuovere nodi di testo vuoti)
            doc.getDocumentElement().normalize();
            
		}
		catch (ParserConfigurationException | SAXException | IOException | NumberFormatException e) 
		{
//            System.err.println("Errore durante il parsing o la lettura del file XML: " + e.getMessage());
            e.printStackTrace(); // Stampa lo stack trace per debug
        }
		return doc;
            
	}
	
	private Object Get_Object_by_Type_and_Value(String Type, String Value)
	{
		Object result = null;
//		System.out.println(Type +"  "+Value);
		switch(Type)
        {
        case "Boolean":
        	result = Boolean.parseBoolean( Value );
        	break;
        case "Byte":
        	result = Byte.parseByte( Value );
        	break;
        case "Char":
        	result = Value.charAt( 0 );
        	break;
        case "Double":
        	Value = Value.replace(",", ".");
        	result = Double.parseDouble( Value );
        	break;
        case "Float":
        	Value = Value.replace(",", ".");
        	result = Float.parseFloat( Value );
        	break;
        case "Integer", "int":
        	result = Integer.parseInt( Value );
        	break;
        case "Long":
        	result = Long.parseLong( Value );
        	break;
        case "Short":
        	result = Short.parseShort( Value );
        	break;
        case "String":
        	result = Value;
        	break;
        case "Object":
        	result = null;
        	break;
        case "Time":
        	result = LocalDateTime.parse(Value);
        	break;
        /**
         * For all other types: Predicate, Belief, ecc
         */
        default:
        	TDouble_Object Double_Object = new TDouble_Object();
        	Double_Object.Set_Object_First(Type);
        	Double_Object.Set_Object_Second(Value);
        	result = Double_Object;
        }
		return result;
	}
	
	private String Get_Element_Text_Content(Element parentElement, String tagName) 
	{
        NodeList nodeList = parentElement.getElementsByTagName(tagName);
        if (nodeList != null && nodeList.getLength() > 0) 
        {
            Node node = nodeList.item(0);
            if (node.getNodeType() == Node.ELEMENT_NODE) 
            {
                return node.getTextContent();
            }
        }
        return ""; // Ritorna stringa vuota se il tag non esiste o è vuoto
    }
	
	private void Load_Data( String Dir_Path)
	{
		if ( Dir_Path == null || Dir_Path.equals(""))
	    {
			System.out.print("TLong_Memory - error in Dir Path: "+Dir_Path);
	    	return;
	    }
		/***
		 * Order to read data:
		 * 1- Predicates
		 * 2- Beliefs
		 * 3- Green Desires
		 * 4- Quality Desires
		 * 5- Functional Desires
		 * 6- Epistemic Desires
		 */
		//01 - Predicates
		this.Load_Predicates( Dir_Path );
		
		//02 - Beliefs
		this.Load_Beliefs( Dir_Path );
		
		//03 - Green Desires
		this.Load_Green_Desires(Dir_Path);
		
		//04 - Quality Desires
		this.Load_Quality_Desires(Dir_Path);
		
		
		//05 - Epistemic Desires
		this.Load_Epistemic_Desires(Dir_Path);

		//System.out.println("----------------------------------");
		
		//06 - Practical Desires
		this.Load_Practical_Desires( Dir_Path );
//		for(TAttentional_Desire Attentional_Desire: this.Attentional_Desires.Read())
//		{
//			if(Attentional_Desire instanceof TPractical_Desire)
//			{
//				this.Print_Practical_Desire( (TPractical_Desire) Attentional_Desire );
//				System.out.println("----------------------------------");
//				
//			}
//		}
	}
	
	public void Load_Predicates(String Dir_Path)
	{
		
		String File_to_read = Dir_Path + "Predicates.xml";
		Document doc = this.Get_Document_Xml( File_to_read );
		if (doc == null)
		{
			
			return;
		}
		// 5. Ottieni tutti gli elementi "MappedDesire"
        NodeList Node_List = doc.getElementsByTagName("Predicate");
        String Predicate_Name = "";
        String Subject_Type_Text = "";
        String Subject_Value_Text = "";
        String Relationship_Type = "";
        String Object_Complement_Type_Text = "";
        String Object_Complement_Value_Text = "";
        
        ArrayList<String> To_Do_Later = new ArrayList<String>();
        
        /**
         * I'm not interested in the description. 
         * This field is only useful for the agent and case study definition tool.
         */
//        String Description = "";
        
        // 6. Itera su ogni "MappedDesire" e popola la mappa
        for (int i = 0; i < Node_List.getLength(); i++) {
            Node node = Node_List.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE)
            {
                Element element = (Element) node;

                // Estrai i valori dai tag figli
                Predicate_Name = this.Get_Element_Text_Content(element, "Predicate_Name");
                Subject_Type_Text = this.Get_Element_Text_Content(element, "Subject_Type");
                Subject_Value_Text = this.Get_Element_Text_Content(element, "Subject_Value");
                Relationship_Type = this.Get_Element_Text_Content(element, "Relationship_Type");
                Object_Complement_Type_Text = this.Get_Element_Text_Content(element, "Object_Complement_Type");
                Object_Complement_Value_Text = this.Get_Element_Text_Content(element, "Object_Complement_Value");
                
//                if (this.Handled_Types.contains(Subject_Type_Text) & 
//                		this.Handled_Types.contains(Object_Complement_Type_Text)	)
                {
                	
                	Object Subject = Get_Object_by_Type_and_Value(Subject_Type_Text, Subject_Value_Text);
                    TType_Relationship Relationship = TType_Relationship.valueOf(Relationship_Type);
                    Object Object_Complement = Get_Object_by_Type_and_Value(Object_Complement_Type_Text,
                    										Object_Complement_Value_Text);
                    
                	TPredicate Predicate = new TPredicate(Predicate_Name, Subject, 
							Relationship, Object_Complement);
                	
//                	this.Predicates.Add( Predicate );
                	this.Add_Predicate(Predicate);
                	
//                	this.Map_Predicates.put( Predicate_Name, Predicate );
                	
                }
//                else
//                {
//                	To_Do_Later.clear();
//                	To_Do_Later.add(Predicate_Name);
//                	To_Do_Later.add(Subject_Type_Text);
//                	To_Do_Later.add(Subject_Value_Text);
//                	To_Do_Later.add(Relationship_Type);
//                	To_Do_Later.add(Object_Complement_Type_Text);
//                	To_Do_Later.add(Object_Complement_Value_Text);
//                	
//                	this.Predicates_To_Parse_Later.add( To_Do_Later );
//                }

            }
        }
	}
	
	public void Load_Beliefs(String Dir_Path)
	{
		String File_to_read = Dir_Path + "Beliefs.xml";
		Document doc = this.Get_Document_Xml( File_to_read );
		if (doc == null)
		{
			
			return;
		}
		// 5. Ottieni tutti gli elementi
		ArrayList<TBelief> Loaded_Beliefs = new ArrayList<TBelief>();
		
        NodeList Node_List = doc.getElementsByTagName("Belief");
        String Belief_Name = "";
        String Belief_Type = "";
        String Associated_Predicate = "";
        String Information_Source = "";
        String Time_Stamp_Text = "";
        LocalDateTime Time_Stamp = null;
        Boolean Truth = false;
        //String Description = "";
        
        ArrayList<String> To_Do_Later = new ArrayList<String>();
        
        /**
         * I'm not interested in the description. 
         * This field is only useful for the agent and case study definition tool.
         */
//        String Description = "";
        
        // 6. Itera su ogni "MappedDesire" e popola la mappa
        for (int i = 0; i < Node_List.getLength(); i++) 
        {
            Node node = Node_List.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE)
            {
                Element element = (Element) node;

                // Estrai i valori dai tag figli
                Belief_Name = this.Get_Element_Text_Content(element, "Belief_Name");
                Belief_Type = this.Get_Element_Text_Content(element, "Belief_Type");
                Associated_Predicate = this.Get_Element_Text_Content(element, "Associated_Predicate");
                Information_Source = this.Get_Element_Text_Content(element, "Information_Source");
                
                Time_Stamp_Text = this.Get_Element_Text_Content(element, "Time_Stamp" );
                if ((Time_Stamp_Text == null) || (Time_Stamp_Text.equals("")) 
                			|| (Time_Stamp_Text.equals("null")) )
                {
                	Time_Stamp = null;
                }
                else
                {
                	Time_Stamp = LocalDateTime.parse( Time_Stamp_Text );
                }
                
                Truth = Boolean.parseBoolean( this.Get_Element_Text_Content(element, "Truth" ));
                
                if(Belief_Type.startsWith("BLTS_"))
                {
                	TStimulus Stimulus = new TStimulus(Belief_Name, Associated_Predicate, 0.0, Truth, Information_Source, 
							Time_Stamp, Belief_Type );
                	Loaded_Beliefs.add( Stimulus );
                	
                }
                else
                {
                	TBelief Belief = new TBelief(Belief_Name, Associated_Predicate, Truth, Information_Source, 
							Time_Stamp, Belief_Type );
                	Loaded_Beliefs.add( Belief );
                }
                
                
                
            }
        }
//        this.Beliefs.Add_All(Loaded_Beliefs);
        this.Add_Beliefs( Loaded_Beliefs );
        Loaded_Beliefs.clear(); 
        
	}
	
	/**
	 * this function normalizes all Predicate that have the subject and/or the Complement_Object are an
	 * instance of TDouble_Object. This means the the Subject and/or Complement_Object has to be
	 * normalized. This function does this. 
	 * @param Type_To_Normalize
	 */
	private void Normalize_Predicates()
	{
		
		String Name = "";
		String Type = "";
		HashMap<String, TBelief> Temp_Beliefs = new HashMap<>(this.Map_Beliefs); //new HashMap<String, TBelief>();
		HashMap<String, TPredicate> Temp_Predicates = this.Map_Predicates; //new HashMap<String, TBelief>();
				
//		for (TBelief Belief : this.Beliefs.Read()) 
//		{
//			Temp_Beliefs.put(Belief.Get_Name(), Belief);
//      }
		int Index = 0;
		Object Object_to_Check = null;
		String Error_String = "";
		for(TPredicate Predicate: this.Predicates.Read() )
		{
			//I handle the Subject case
			for(Index=0; Index<2;Index++)
			{
				if(Index == 0)
				{
					Object_to_Check = Predicate.Get_Subject();
					//This String is not ever used, but only when an error occors
					Error_String = "The Agent cannot to normalize the Subject of Object type: ";
				}
				else
				{
					Object_to_Check = Predicate.Get_Object_Complement();
					Error_String = "The Agent cannot to normalize the Subject of Object type: ";
				}
				if( Object_to_Check instanceof TDouble_Object)
				{
					TDouble_Object double_Object = (TDouble_Object) Object_to_Check;
					switch( (String) double_Object.Get_Object_First())
					{
						case "Predicate":
							TPredicate Predicate_to_link = Temp_Predicates.get( (String) double_Object.Get_Object_Second() );
							if( Predicate_to_link != null)
							{
								if (Index == 0)
									{ Predicate.Set_Subject( Predicate_to_link ); }
								else
									{ Predicate.set_Object_Complement( Predicate_to_link ); }
								
							}
							else
							{
								System.out.println("No Predicate to normalize for: "+ double_Object.Get_Object_Second());
							}
							break;
						case "Belief":
							TBelief Belief_to_link = Temp_Beliefs.get( (String) double_Object.Get_Object_Second() );
							if( Belief_to_link != null)
							{
								if (Index == 0)
									{ Predicate.Set_Subject( Belief_to_link ); }
								else
									{ Predicate.set_Object_Complement( Belief_to_link ); }
							}
							else
							{
								System.out.println("No Belief to normalize for: "+ double_Object.Get_Object_Second());
							}
							break;

						default:
							System.out.println(Error_String + (String) double_Object.Get_Object_First());
					}
					
					
				}
			}

			
//			//I handle the Object_Complement case
//			if(Predicate.Get_Object_Complement() instanceof TDouble_Object)
//			{
//				TDouble_Object Double_Object = (TDouble_Object) Predicate.Get_Object_Complement();
//				switch( (String) Double_Object.Get_Object_First())
//				{
//				case "Belief":
//					TBelief Belief = Temp_Beliefs.get( (String) Double_Object.Get_Object_Second() );
//					if( Belief != null)
//					{
//						Predicate.set_Object_Complement(Temp_Beliefs);
//					}
//					else
//					{
//						System.out.println("the agent failed to normalize the Object_Complement of Predicate: "+Predicate.Get_Name());
//					}
//					break;
//					default:
//						System.out.println("The Agent cannot to normalize the Object_Complement of Object type: "+(String) Double_Object.Get_Object_First());
//						break;
//				}
//				
//			}
		}
		
	}
	
	private void Normalize_Beliefs()
	{
		HashMap<String, TPredicate> Temp_Predicates = 
				new HashMap<String, TPredicate>(this.Map_Predicates);
		for(TBelief Belief: this.Beliefs.Read() )
		{
			String Predicate_Name = Belief.Get_Predicate_name();
			if(Belief.Get_Predicate() == null &&
					(!(Predicate_Name == null) && (!Predicate_Name.trim().isEmpty()) ))
			{
				TPredicate Predicate = Temp_Predicates.get( Predicate_Name );
				Belief.Set_Predicate( Predicate );
			}
			else
			{
				System.out.println("the agent failed to normalize the Predicate "+Predicate_Name+" of Belief: "+Belief.Get_Name());
			}
		}
		
	}
	
	private void Normalize_Green_Desires()
	{
		HashMap<String, TPredicate> Temp_Predicates = 
				new HashMap<String, TPredicate>(this.Map_Predicates);
		for(TGreen_Desire Green_Desire: this.Green_Desires.Read())
		{
			String Predicate_Name = Green_Desire.Get_Constraint_Name();
			if(Green_Desire.Get_Constraint() == null &&
					(!(Predicate_Name == null) && (!Predicate_Name.trim().isEmpty()) ))
			{
				TPredicate Predicate = Temp_Predicates.get( Predicate_Name );
				Green_Desire.set_Constraint( Predicate );
				//System.out.println(Green_Desire.Get_Constraint().Get_Name());
			}
			else
			{
				System.out.println("the agent failed to normalize the Predicate "+Predicate_Name+" of Green Desire: "+Green_Desire.Get_Name());
			}
		}
		
	}
	
	private void Normalize_Quality_Desires()
	{
		HashMap<String, TPredicate> Temp_Predicates = 
				new HashMap<String, TPredicate>(this.Map_Predicates);
		for(TQuality_Desire Quality_Desire: this.Quality_Desires.Read())
		{
			String Predicate_Name = Quality_Desire.Get_Constraint_Name();
			if(Quality_Desire.Get_Constraint() == null &&
					(!(Predicate_Name == null) && (!Predicate_Name.trim().isEmpty()) ))
			{
				TPredicate Predicate = Temp_Predicates.get( Predicate_Name );
				Quality_Desire.set_Constraint( Predicate );
				//System.out.println(Quality_Desire.Get_Constraint().Get_Name());
			}
			else
			{
				System.out.println("the agent failed to normalize the Predicate "+Predicate_Name+" of Quality Desire: "+Quality_Desire.Get_Name());
			}
		}
		
	}
	
	private void Normalize_Epistemic_Desires()
	{
		HashMap<String, TBelief> Temp_Beliefs = 
				new HashMap<String, TBelief>(this.Map_Beliefs);
		HashMap<String, TGreen_Desire> Temp_Greens = new HashMap<String, TGreen_Desire>(); 
		HashMap<String, TQuality_Desire> Temp_Qualities = new HashMap<String, TQuality_Desire>();
		
		for (TGreen_Desire Green_Desire : this.Green_Desires.Read()) 
		{
			Temp_Greens.put(Green_Desire.Get_Name(), Green_Desire);
	    }
		
		for (TQuality_Desire Quality_Desire : this.Quality_Desires.Read()) 
		{
			Temp_Qualities.put(Quality_Desire.Get_Name(), Quality_Desire);
	    }
		
		String Object_Name = "";
		for(TAttentional_Desire Attentional_Desire: this.Attentional_Desires.Read())
		{
			if(Attentional_Desire instanceof TEpistemic_Desire)
			{
				TEpistemic_Desire Epistemic_Desire = (TEpistemic_Desire) Attentional_Desire;
				
				//Stimulus Normalization
				Object_Name = Epistemic_Desire.Get_Belief_Name();
				TBelief Belief = Temp_Beliefs.get( Object_Name );
				if( Belief != null)
				{
					Epistemic_Desire.Set_Belief( Belief );
				}
				else
				{
					System.out.println("No Belief "+Object_Name+" to normalize for Epistemic Desire: "+ Epistemic_Desire.Get_Name());
				}
				
				//Green Desires Normalization
				ArrayList<TGreen_Desire> List_Green_Desires = new ArrayList<TGreen_Desire>();
				for(String Green_Name: Epistemic_Desire.Get_List_Green_Standing_Desire_Name())
				{
					TGreen_Desire Green_Desire = Temp_Greens.get( Green_Name );
					if( Green_Desire != null)
					{
						List_Green_Desires.add( Green_Desire );
					}
					else
					{
						System.out.println("No Green Desire "+Green_Name+" to normalize for Epistemic Desire: "+ Epistemic_Desire.Get_Name());
					}
				}
				Epistemic_Desire.Set_List_Green_Standing_Desire( List_Green_Desires );
				
				//Quality Desires Normalization
				ArrayList<TQuality_Desire> List_Quality_Desires = new ArrayList<TQuality_Desire>();
				for(String Quality_Name: Epistemic_Desire.Get_List_Quality_Standing_Desire_Name())
				{
					TQuality_Desire Quality_Desire = Temp_Qualities.get( Quality_Name );
					if( Quality_Desire != null)
					{
						List_Quality_Desires.add( Quality_Desire );
					}
					else
					{
						System.out.println("No Quality Desire "+Quality_Desire+" to normalize for Epistemic Desire: "+ Epistemic_Desire.Get_Name());
					}
				}
				Epistemic_Desire.Set_List_Quality_Standing_Desire( List_Quality_Desires );
			}
		}
	}
	
	private void Normalize_Practical_Desires()
	{
		HashMap<String, TBelief> Temp_Beliefs = 
				new HashMap<String, TBelief>(this.Map_Beliefs);
		HashMap<String, TGreen_Desire> Temp_Greens = new HashMap<String, TGreen_Desire>(); 
		HashMap<String, TQuality_Desire> Temp_Qualities = new HashMap<String, TQuality_Desire>();
		
		for (TGreen_Desire Green_Desire : this.Green_Desires.Read()) 
		{
			Temp_Greens.put(Green_Desire.Get_Name(), Green_Desire);
	    }
		
		for (TQuality_Desire Quality_Desire : this.Quality_Desires.Read()) 
		{
			Temp_Qualities.put(Quality_Desire.Get_Name(), Quality_Desire);
	    }
		
		String Object_Name = "";
		for(TAttentional_Desire Attentional_Desire: this.Attentional_Desires.Read())
		{
			if(Attentional_Desire instanceof TPractical_Desire)
			{
				TPractical_Desire Practical_Desire = (TPractical_Desire) Attentional_Desire;
				
				//Green Desires Normalization
				ArrayList<TGreen_Desire> List_Green_Desires = new ArrayList<TGreen_Desire>();
				for(String Green_Name: Practical_Desire.Get_List_Green_Standing_Desire_Name())
				{
					TGreen_Desire Green_Desire = Temp_Greens.get( Green_Name );
					if( Green_Desire != null)
					{
						List_Green_Desires.add( Green_Desire );
					}
					else
					{
						System.out.println("No Green Desire "+Green_Name+" to normalize for Practical Desire: "+ Practical_Desire.Get_Name());
					}
				}
				Practical_Desire.Set_List_Green_Standing_Desire( List_Green_Desires );
				
				//Quality Desires Normalization
				ArrayList<TQuality_Desire> List_Quality_Desires = new ArrayList<TQuality_Desire>();
				for(String Quality_Name: Practical_Desire.Get_List_Quality_Standing_Desire_Name())
				{
					TQuality_Desire Quality_Desire = Temp_Qualities.get( Quality_Name );
					if( Quality_Desire != null)
					{
						List_Quality_Desires.add( Quality_Desire );
					}
					else
					{
						System.out.println("No Quality Desire "+Quality_Desire+" to normalize for Practical Desire: "+ Practical_Desire.Get_Name());
					}
				}
				Practical_Desire.Set_List_Quality_Standing_Desire( List_Quality_Desires );
				
				//Trigger_Condition
				ArrayList<TBelief> List_Trigger_Condition = new ArrayList<TBelief>();
				for(String Belief_Name: Practical_Desire.Get_Trigger_Condition_Names())
				{
					TBelief Belief = Temp_Beliefs.get( Belief_Name );
					if( Belief != null)
					{
						List_Trigger_Condition.add( Belief );
					}
					else
					{
						System.out.println("No Trigger Condition  "+Belief_Name+" to normalize for Practical Desire: "+ Practical_Desire.Get_Name());
					}
				}
				TPropositional_Formula Propositional_Formula = new TPropositional_Formula();
				Propositional_Formula.Set_Beliefs( List_Trigger_Condition );
				Propositional_Formula.Set_Formula( Practical_Desire.Get_Trigger_Condition_Formula() );
				Practical_Desire.Set_Trigger_Condition( Propositional_Formula );
				
				//Final_States
				ArrayList<TBelief> List_Final_States = new ArrayList<TBelief>();
				for(String Belief_Name: Practical_Desire.Get_Final_State_Names())
				{
					TBelief Belief = Temp_Beliefs.get( Belief_Name );
					if( Belief != null)
					{
						List_Final_States.add( Belief );
					}
					else
					{
						System.out.println("No Final State  "+Belief_Name+" to normalize for Practical Desire: "+ Practical_Desire.Get_Name());
					}
				}
				
				TTemporal_Operator Temporal_Operator = new TTemporal_Operator();
				Temporal_Operator.Set_Type_Temporal_Operator( TType_Temporal_Operator.Finally );
				Temporal_Operator.Set_Start_Time( Practical_Desire.Get_Finally_Start() );
				Temporal_Operator.Set_End_Time( Practical_Desire.Get_Finally_End() );
				
				TTemporal_Propositional_Formula Temporal_Propositional_Formula = 
						new TTemporal_Propositional_Formula();
				Temporal_Propositional_Formula.Set_Temporal_Operator( Temporal_Operator );
				Temporal_Propositional_Formula.Set_Beliefs( List_Final_States );
				Temporal_Propositional_Formula.Set_Formula( Practical_Desire.Get_Final_State_Formula() );
				Temporal_Propositional_Formula.Set_List_Temporal_Propositional_Formula_Names(null);
				Practical_Desire.Set_Final_State( Temporal_Propositional_Formula );
				
			}
		}
	}
	
	public void Load_Green_Desires(String Dir_Path)
	{
		String File_to_read = Dir_Path + "Green_Desires.xml";
		Document doc = this.Get_Document_Xml( File_to_read );
		if (doc == null)
		{
			
			return;
		}
		// 5. Ottieni tutti gli elementi
		ArrayList<TGreen_Desire> Loaded_Green_Desires = new ArrayList<TGreen_Desire>();
		
        NodeList Node_List = doc.getElementsByTagName("Green_Desire");
        String Green_Desire_Name = "";
        String Green_Desire_Type = "";
        String Constraint = "";
        String Saliency_Text = "";
        String Reward_Text = "";
        String Relax_Preference_Text = "";
        String Fee_Text = "";
        Double Saliency = 0.0;
        Double Reward = 0.0;
        Double Relax_Preference = 0.0;
        Double Fee = 0.0;
        Boolean Satisfied = false;
        //String Description = "";
        
        ArrayList<String> To_Do_Later = new ArrayList<String>();
        
        /**
         * I'm not interested in the description. 
         * This field is only useful for the agent and case study definition tool.
         */
//        String Description = "";
        
        // 6. Itera su ogni "MappedDesire" e popola la mappa
        for (int i = 0; i < Node_List.getLength(); i++) 
        {
            Node node = Node_List.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE)
            {
                Element element = (Element) node;

                // Estrai i valori dai tag figli
                Green_Desire_Name = this.Get_Element_Text_Content(element, "Green_Desire_Name");
                Green_Desire_Type = this.Get_Element_Text_Content(element, "Green_Desire_Type");
                Constraint = this.Get_Element_Text_Content(element, "Constraint");
                
                Saliency_Text = this.Get_Element_Text_Content(element, "Saliency");
                Saliency = Double.parseDouble( Saliency_Text.replace(",", ".") );
                
                Reward_Text = this.Get_Element_Text_Content(element, "Reward");
                Reward = Double.parseDouble( Reward_Text.replace(",", ".") );
                
                Relax_Preference_Text = this.Get_Element_Text_Content(element, "Relax_Preference");
                Relax_Preference = Double.parseDouble( Relax_Preference_Text.replace(",", ".") );
                
                Fee_Text = this.Get_Element_Text_Content(element, "Fee");
                Fee = Double.parseDouble( Fee_Text.replace(",", ".") );
                
                //Time_Stamp = LocalDateTime.parse( this.Get_Element_Text_Content(element, "Time_Stamp" ));
                Satisfied = Boolean.parseBoolean( this.Get_Element_Text_Content(element, "Satisfied" ));
                
//                TBelief Belief = new TBelief(Belief_Name, Associated_Predicate, Truth, Information_Source, 
//                										Time_Stamp, Information_Source );
                TGreen_Desire Green_Desire = new TGreen_Desire(Green_Desire_Name, 
                		Constraint, Green_Desire_Type, Saliency, Reward, Relax_Preference, Fee);
                
                Loaded_Green_Desires.add( Green_Desire );
                
                
            }
        }
        this.Green_Desires.Add_All( Loaded_Green_Desires );
        Loaded_Green_Desires.clear(); 
        
	}
	
	public void Load_Quality_Desires(String Dir_Path)
	{
		String File_to_read = Dir_Path + "Quality_Desires.xml";
		Document doc = this.Get_Document_Xml( File_to_read );
		if (doc == null)
		{
			
			return;
		}
		// 5. Ottieni tutti gli elementi
		ArrayList<TQuality_Desire> Loaded_Quality_Desires = new ArrayList<TQuality_Desire>();
		
        NodeList Node_List = doc.getElementsByTagName("Quality_Desire");
        String Quality_Desire_Name = "";
        String Quality_Desire_Type = "";
        String Constraint = "";
        String Saliency_Text = "";
        String Reward_Text = "";
        String Relax_Preference_Text = "";
        Double Saliency = 0.0;
        Double Reward = 0.0;
        Double Relax_Preference = 0.0;
        Boolean Satisfied = false;
        //String Description = "";
        
        ArrayList<String> To_Do_Later = new ArrayList<String>();
        
        /**
         * I'm not interested in the description. 
         * This field is only useful for the agent and case study definition tool.
         */
//        String Description = "";
        
        // 6. Itera su ogni "MappedDesire" e popola la mappa
        for (int i = 0; i < Node_List.getLength(); i++) 
        {
            Node node = Node_List.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE)
            {
                Element element = (Element) node;

                // Estrai i valori dai tag figli
                Quality_Desire_Name = this.Get_Element_Text_Content(element, "Quality_Desire_Name");
                Quality_Desire_Type = this.Get_Element_Text_Content(element, "Quality_Desire_Type");
                Constraint = this.Get_Element_Text_Content(element, "Constraint");
                
                Saliency_Text = this.Get_Element_Text_Content(element, "Saliency");
                Saliency = Double.parseDouble( Saliency_Text.replace(",", ".") );
                
                Reward_Text = this.Get_Element_Text_Content(element, "Reward");
                Reward = Double.parseDouble( Reward_Text.replace(",", ".") );
                
                Relax_Preference_Text = this.Get_Element_Text_Content(element, "Relax_Preference");
                Relax_Preference = Double.parseDouble( Relax_Preference_Text.replace(",", ".") );
                
                //Time_Stamp = LocalDateTime.parse( this.Get_Element_Text_Content(element, "Time_Stamp" ));
                Satisfied = Boolean.parseBoolean( this.Get_Element_Text_Content(element, "Satisfied" ));
                
//                TBelief Belief = new TBelief(Belief_Name, Associated_Predicate, Truth, Information_Source, 
//                										Time_Stamp, Information_Source );
                TQuality_Desire Quality_Desire = new TQuality_Desire(Quality_Desire_Name, 
                		Constraint, Quality_Desire_Type, Saliency, Reward, Relax_Preference);
                
                Loaded_Quality_Desires.add( Quality_Desire );
                
                
            }
        }
        this.Quality_Desires.Add_All( Loaded_Quality_Desires );
        Loaded_Quality_Desires.clear(); 
        
	}
	
	public void Load_Epistemic_Desires(String Dir_Path)
	{
		String File_to_read = Dir_Path + "Epistemic_Desires.xml";
		Document doc = this.Get_Document_Xml( File_to_read );
		if (doc == null)
		{
			
			return;
		}
		// 5. Ottieni tutti gli elementi
		ArrayList<TAttentional_Desire> Loaded_Epistemic_Desires = new ArrayList<TAttentional_Desire>();
		
        NodeList Node_List = doc.getElementsByTagName("Epistemic_Desire");
        String Epistemic_Name = "";
        String Stimulus_Name = "";
        String Saliency_Text = "";
        String Trigger_condotion_formula = "";
        String Reward_Text = "";
        String Relax_Preference_Text = "";
        Double Saliency = 0.0;
        Double Reward = 0.0;
        Double Relax_Preference = 0.0;
        Boolean Satisfied = false;
        ArrayList<String> Trigger_Condition_Names = new ArrayList<String>();
        ArrayList<String> List_Green_Desires = new ArrayList<String>();
        ArrayList<String> List_Quality_Desires = new ArrayList<String>();
        ArrayList<String> List_Beliefs_for_Reasoner = new ArrayList<String>();
        //String Description = "";
        
        ArrayList<String> To_Do_Later = new ArrayList<String>();
        
        /**
         * I'm not interested in the description. 
         * This field is only useful for the agent and case study definition tool.
         */
//        String Description = "";
        
        // 6. Itera su ogni "MappedDesire" e popola la mappa
        for (int i = 0; i < Node_List.getLength(); i++) 
        {
            Node node = Node_List.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE)
            {
                Element element = (Element) node;

                // Estrai i valori dai tag figli
                
                Epistemic_Name = this.Get_Element_Text_Content(element, "Epistemic_Name");
                Stimulus_Name = this.Get_Element_Text_Content(element, "Stimulus_Name");
                
                Saliency_Text = this.Get_Element_Text_Content(element, "Saliency");
                Saliency = Double.parseDouble( Saliency_Text.replace(",", ".") );
                
                Trigger_condotion_formula = this.Get_Element_Text_Content(element, "Trigger_condotion_formula");
                
                Reward_Text = this.Get_Element_Text_Content(element, "Reward");
                Reward = Double.parseDouble( Reward_Text.replace(",", ".") );
                
                Relax_Preference_Text = this.Get_Element_Text_Content(element, "Relax_Preference");
                Relax_Preference = Double.parseDouble( Relax_Preference_Text.replace(",", ".") );
                
                //Time_Stamp = LocalDateTime.parse( this.Get_Element_Text_Content(element, "Time_Stamp" ));
                Satisfied = Boolean.parseBoolean( this.Get_Element_Text_Content(element, "Satisfied" ));
                
                NodeList Trigger_Condition_Name_Nodes = element.getElementsByTagName("Trigger_Condition_Name_Node");
                for (int j = 0; j < Trigger_Condition_Name_Nodes.getLength(); j++) 
                {
                    Node Trigger_Condition_Node = Trigger_Condition_Name_Nodes.item(j);
                    // Aggiungi il testo del nodo alla lista
                    Trigger_Condition_Names.add(Trigger_Condition_Node.getTextContent());
                }
                
                //I get all Green Desires into the Epistemic Desire
                NodeList greenDesireNodes = element.getElementsByTagName("Green_Desire");
                for (int j = 0; j < greenDesireNodes.getLength(); j++) 
                {
                    Node greenNode = greenDesireNodes.item(j);
                    // Aggiungi il testo del nodo alla lista
                    List_Green_Desires.add(greenNode.getTextContent());
                }
                
                NodeList qualityDesireNodes = element.getElementsByTagName("Quality_Desire");
                for (int k = 0; k < qualityDesireNodes.getLength(); k++) 
                {
                    Node qualityNode = qualityDesireNodes.item(k);
                    // Aggiungi il testo del nodo alla lista
                    List_Quality_Desires.add(qualityNode.getTextContent());
                }
                
                NodeList BeliefsReasonerDesireNodes = element.getElementsByTagName("Beliefs_for_Reasoner");
                for (int k = 0; k < BeliefsReasonerDesireNodes.getLength(); k++) 
                {
                    Node qualityNode = BeliefsReasonerDesireNodes.item(k);
                    // Aggiungi il testo del nodo alla lista
                    List_Beliefs_for_Reasoner.add(qualityNode.getTextContent());
                }
                
                
                TEpistemic_Desire Epistemic_Desire = new TEpistemic_Desire(Epistemic_Name, 
                		Stimulus_Name, Trigger_condotion_formula, Trigger_Condition_Names, 
                		Saliency, Reward, Relax_Preference, 
                		List_Green_Desires, List_Quality_Desires,
                		List_Beliefs_for_Reasoner, null);
                
                Loaded_Epistemic_Desires.add( Epistemic_Desire );
                
                
            }
        }
//        this.epLoaded_Epistemic_Desires.Add_All( Loaded_Epistemic_Desires );
        this.Attentional_Desires.Add_All(Loaded_Epistemic_Desires);
        this.Inhibited_Attentional_Desires.Add_All( Loaded_Epistemic_Desires );
        Loaded_Epistemic_Desires.clear(); 
        
	}
	
	public void Load_Practical_Desires(String Dir_Path)
	{
		String File_to_read = Dir_Path + "Practical_Desires.xml";
		Document doc = this.Get_Document_Xml( File_to_read );
		if (doc == null)
		{
			
			return;
		}
		// 5. Ottieni tutti gli elementi
		ArrayList<TAttentional_Desire> Loaded_Practical_Desires = new ArrayList<TAttentional_Desire>();
		
        NodeList Node_List = doc.getElementsByTagName("Practical_Desire");
        String Practical_Name = "";
        String Saliency_Text = "";
        String Reward_Text = "";
        String Relax_Preference_Text = "";
        Double Saliency = 0.0;
        Double Reward = 0.0;
        Double Relax_Preference = 0.0;
        Boolean Satisfied = false;
        
        ArrayList<String> List_Beliefs_Trigger_Condition_Formula = new ArrayList<String>();
        ArrayList<String> List_Beliefs_Temporal_Propositional_Formula = new ArrayList<String>();
        
        ArrayList<String> List_Beliefs_for_Reason = new ArrayList<String>();
        
        String Trigger_Condition_Formula = "";
        String Trigger_Condition_Formula_Description = "";
        String Temporal_Propositional_Formula ="";
        String Temporal_Propositional_Formula_Description = "";
        String Time_Stamp_Text = "";
        LocalDateTime Finally_Start_Time = null;
        LocalDateTime Finally_End_Time = null;
        //String Description = "";
        
        ArrayList<String> To_Do_Later = new ArrayList<String>();
        
        /**
         * I'm not interested in the description. 
         * This field is only useful for the agent and case study definition tool.
         */
//        String Description = "";
        
        // 6. Itera su ogni "MappedDesire" e popola la mappa
        for (int i = 0; i < Node_List.getLength(); i++) 
        {
        	ArrayList<String> List_Green_Desires = new ArrayList<String>();
            ArrayList<String> List_Quality_Desires = new ArrayList<String>();
            Node node = Node_List.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE)
            {
                Element element = (Element) node;

                // Estrai i valori dai tag figli
                
                Practical_Name = this.Get_Element_Text_Content(element, "Practical_Name");
                
                Saliency_Text = this.Get_Element_Text_Content(element, "Saliency");
                Saliency = Double.parseDouble( Saliency_Text.replace(",", ".") );
                
                Reward_Text = this.Get_Element_Text_Content(element, "Reward");
                Reward = Double.parseDouble( Reward_Text.replace(",", ".") );
                
                Relax_Preference_Text = this.Get_Element_Text_Content(element, "Relax_Preference");
                Relax_Preference = Double.parseDouble( Relax_Preference_Text.replace(",", ".") );
                
                //Time_Stamp = LocalDateTime.parse( this.Get_Element_Text_Content(element, "Time_Stamp" ));
                Satisfied = Boolean.parseBoolean( this.Get_Element_Text_Content(element, "Satisfied" ));
                
                //I get all Green Desires into the Practical Desire
                NodeList greenDesireNodes = element.getElementsByTagName("Green_Desire");
                List_Green_Desires.clear();;
                for (int j = 0; j < greenDesireNodes.getLength(); j++) 
                {
                    Node greenNode = greenDesireNodes.item(j);
                    // Aggiungi il testo del nodo alla lista
                    List_Green_Desires.add(greenNode.getTextContent());
                }
                
                //I get all Quality Desires into the Practical Desire
                NodeList qualityDesireNodes = element.getElementsByTagName("Quality_Desire");
                List_Quality_Desires.clear();
                for (int k = 0; k < qualityDesireNodes.getLength(); k++) 
                {
                    Node qualityNode = qualityDesireNodes.item(k);
                    // Aggiungi il testo del nodo alla lista
                    List_Quality_Desires.add(qualityNode.getTextContent());
                }
                
                //I get all Beliefs_Trigger_Condition_Formula into the Practical Desire
                NodeList BeliefsNodes = element.getElementsByTagName("Belief_Trigger_Condition_Formula");
                List_Beliefs_Trigger_Condition_Formula.clear();
                for (int k = 0; k < BeliefsNodes.getLength(); k++) 
                {
                    Node qualityNode = BeliefsNodes.item(k);
                    // Aggiungi il testo del nodo alla lista
                    List_Beliefs_Trigger_Condition_Formula.add(qualityNode.getTextContent());
                }
//                System.out.println("List_Beliefs_Trigger_Condition_Formula: "+List_Beliefs_Trigger_Condition_Formula);
                
                //I get all Beliefs_Temporal_Propositional_Formula into the Practical Desire
                NodeList TemporalBeliefsNodes = element.getElementsByTagName("Belief_Temporal_Propositional_Formula");
                List_Beliefs_Temporal_Propositional_Formula.clear();
                for (int k = 0; k < TemporalBeliefsNodes.getLength(); k++) 
                {
                    Node qualityNode = TemporalBeliefsNodes.item(k);
                    // Aggiungi il testo del nodo alla lista
                    List_Beliefs_Temporal_Propositional_Formula.add(qualityNode.getTextContent());
                }
//                System.out.println("List_Beliefs_Temporal_Propositional_Formula: "+List_Beliefs_Temporal_Propositional_Formula);
//                System.out.println("-------------------------");
                
                Trigger_Condition_Formula = this.Get_Element_Text_Content(element, "Trigger_Condition_Formula");
                Trigger_Condition_Formula_Description = this.Get_Element_Text_Content(element, "Trigger_Condition_Formula_Description");
                Temporal_Propositional_Formula = this.Get_Element_Text_Content(element, "Temporal_Propositional_Formula");
                Temporal_Propositional_Formula_Description = this.Get_Element_Text_Content(element, "Temporal_Propositional_Formula_Description");
                
                Time_Stamp_Text = this.Get_Element_Text_Content(element, "Finally_Start_Time" );
                if ((Time_Stamp_Text == null) || (Time_Stamp_Text.equals("")) 
                			|| (Time_Stamp_Text.equals("null")) )
                {
                	Finally_Start_Time = null;
                }
                else
                {
                	Finally_Start_Time = LocalDateTime.parse( Time_Stamp_Text );
                }
                Time_Stamp_Text = this.Get_Element_Text_Content(element, "Finally_End_Time" );
                if ((Time_Stamp_Text == null) || (Time_Stamp_Text.equals("")) 
                			|| (Time_Stamp_Text.equals("null")) )
                {
                	Finally_End_Time = null;
                }
                else
                {
                	Finally_End_Time = LocalDateTime.parse( Time_Stamp_Text );
                }
                
                //Beliefs for Reason
                NodeList ReasonBeliefsNodes = element.getElementsByTagName("Belief_for_Reasoner");
                List_Beliefs_for_Reason.clear();
                for (int k = 0; k < ReasonBeliefsNodes.getLength(); k++) 
                {
                    Node qualityNode = ReasonBeliefsNodes.item(k);
                    // Aggiungi il testo del nodo alla lista
                    List_Beliefs_for_Reason.add(qualityNode.getTextContent());
                }
                
                
                /**
                 * 
                 *  TPropositional_Formula Trigger_Condition = new TPropositional_Formula();
                	Trigger_Condition.Set_Beliefs_Names(List_Beliefs_Trigger_Condition_Formula);
                  	Trigger_Condition.Set_Formula(Trigger_Condition_Formula_Text);
                	// Non implemented now
                	//Propositional_Formula.Set_Name("");
                
                	TTemporal_Operator Temporal_Operator = new TTemporal_Operator();
                	Temporal_Operator.Set_Type_Temporal_Operator( TType_Temporal_Operator.Finally );
                	Temporal_Operator.Set_Start_Time( Finally_Start_Time );
                	Temporal_Operator.Set_End_Time( Finally_End_Time );
                
                	TTemporal_Propositional_Formula Temporal_Propositional_Formula = new 
                		TTemporal_Propositional_Formula();
                	Temporal_Propositional_Formula.Set_Temporal_Operator( Temporal_Operator );
                	Temporal_Propositional_Formula.Set_Formula( Temporal_Propositional_Formula_Text );
                	Temporal_Propositional_Formula.Set_Beliefs_By_Name( List_Beliefs_Temporal_Propositional_Formula, null);
                	//Not used now
                	//Temporal_Propositional_Formula.Set_Name(Practical_Name);
                 */
                
                
                
                
                TPractical_Desire Practical_Desire = new TPractical_Desire(
                		Practical_Name, 
                		List_Beliefs_Temporal_Propositional_Formula, 
                		List_Beliefs_Trigger_Condition_Formula, 
                		Temporal_Propositional_Formula,
                		Trigger_Condition_Formula,
                		Saliency,
                		Reward, 
                		Relax_Preference,
                		List_Green_Desires,
                		List_Quality_Desires,
                		Finally_Start_Time,
                		Finally_End_Time,
                		null,
                		null,
                		null,
                		null,
                		List_Beliefs_for_Reason,
                		null);

                
                Loaded_Practical_Desires.add( Practical_Desire );
                
                
            }
        }
//        this.epLoaded_Epistemic_Desires.Add_All( Loaded_Epistemic_Desires );
        this.Attentional_Desires.Add_All(Loaded_Practical_Desires);
        this.Inhibited_Attentional_Desires.Add_All( Loaded_Practical_Desires );
        Loaded_Practical_Desires.clear(); 
	}
	
	public void Print_Predicate(String Predicate_Name)
	{
		TPredicate Predicate = this.Map_Predicates.get( Predicate_Name );
		if( Predicate == null )
		{
			System.out.println("No Predicate for Predicate Name: "+Predicate_Name);
		}
		else
		{
			this.Print_Predicate( Predicate );			
		}
		
	}
	
	public void Print_Predicate(TPredicate Predicate)
	{
		if( Predicate == null )
		{
			System.out.println("No Predicate provided");
		}
		else
		{
			String Text = "Predicate: " +Predicate.Get_Name() +"\n"
					+ "Predicate PredicateID: "+Predicate.Get_Predicate_ID()+"\n"
					+ "Predicate Subject: "+Predicate.Get_Subject()+"\n"
					+ "Predicate Relationship: "+Predicate.Get_Relationship()+"\n"
					+ "Predicate Object Complement: "+Predicate.Get_Object_Complement()+"\n"
					+ "Predicate Linked Belief Name: "+Predicate.Get_Linked_Belief_Name();
			
			System.out.println(Text);		
		}
	}
	
	public void Print_Belief(String Belief_Name)
	{
		Boolean found = false;
		
		for(TBelief Belief: this.Beliefs.Read())
		{
			if( Belief.Get_Name() == Belief_Name)
			{
				this.Print_Belief( Belief );
			}
		}
		
		if( !found )
		{
			System.out.println("No Belief for Belief Name: "+ Belief_Name);
		}
	
	}
	
	public void Print_Belief(TBelief Belief)
	{
		if( Belief == null )
		{
			System.out.println("No Belief provided");
		}
		else
		{
			String Text = "Belief: " +Belief.Get_Name() +"\n"
					+ "Belief Belief ID: "+Belief.Get_Belief_ID()+"\n"
					+ "Belief Type Belief: "+Belief.Get_Type_Belief()+"\n"
					+ "Belief Information_Source: "+Belief.Get_Information_Source()+"\n"
					+ "Belief Time Stamp: "+Belief.Get_Time_stamp()+"\n"
					+ "Belief Predicates Name: "+Belief.Get_Predicate_name()+"\n"
					+ "Belief Truth: "+Belief.Get_Truth();
			
			System.out.println(Text);		
		}
	}
	
	public void Print_Green_Desire(String Green_Desire_Name)
	{
		Boolean found = false;
		
		for(TGreen_Desire Green_Desire: this.Green_Desires.Read())
		{
			if( Green_Desire.Get_Name() == Green_Desire_Name)
			{
				this.Print_Green_Desire( Green_Desire );
			}
		}
		
		if( !found )
		{
			System.out.println("No Belief for Belief Name: "+ Green_Desire_Name);
		}
	
	}
	
	public void Print_Green_Desire(TGreen_Desire Green_Desire)
	{
		if( Green_Desire == null )
		{
			System.out.println("No Green_Desire provided");
		}
		else
		{
			String Text = "Green_Desire: " +Green_Desire.Get_Name() +"\n"
					+ "Belief Type Green Desire: "+Green_Desire.Get_Type_Green_Standing_Desire()+"\n"
					+ "Belief Get_Constraint Name: "+Green_Desire.Get_Constraint_Name()+"\n"
					+ "Belief Saliency: "+Green_Desire.Get_Saliency()+"\n"
					+ "Belief Fee: "+Green_Desire.Get_Fee()+"\n"
					+ "Belief Reward: "+Green_Desire.Get_Reward()+"\n"
					+ "Belief Relax_Preference: "+Green_Desire.Get_Relax_Preference()+"\n"
					+ "Belief Check_Satisfation: "+Green_Desire.Check_Satisfation();
			
			System.out.println(Text);		
		}
	}
	
	public void Print_Quality_Desire(String Quality_Desire_Name)
	{
		Boolean found = false;
		
		for(TQuality_Desire Quality_Desire: this.Quality_Desires.Read())
		{
			if( Quality_Desire.Get_Name() == Quality_Desire_Name)
			{
				this.Print_Quality_Desire( Quality_Desire_Name );
			}
		}
		
		if( !found )
		{
			System.out.println("No Belief for Quality Desire: "+ Quality_Desire_Name);
		}
	
	}
	
	public void Print_Quality_Desire(TQuality_Desire Quality_Desire)
	{
		if( Quality_Desire == null )
		{
			System.out.println("No Green_Desire provided");
		}
		else
		{
			String Text = "Quality: " +Quality_Desire.Get_Name() +"\n"
					+ "Belief Type Quality Desire: "+Quality_Desire.Get_Type_Quality_Goal()+"\n"
					+ "Belief Get_Constraint Name: "+Quality_Desire.Get_Constraint_Name()+"\n"
					+ "Belief Saliency: "+Quality_Desire.Get_Saliency()+"\n"
					+ "Belief Reward: "+Quality_Desire.Get_Reward()+"\n"
					+ "Belief Relax_Preference: "+Quality_Desire.Get_Relax_Preference()+"\n"
					+ "Belief Check Satisfation: "+Quality_Desire.Check_Satisfation();
			
			System.out.println(Text);		
		}
	}
	
	public void Print_Epistemic_Desire(String Epistemic_Desire_Name)
	{
		Boolean found = false;
		
		for(TAttentional_Desire Attentional_Desire: this.Attentional_Desires.Read())
		{
			if( Attentional_Desire.Get_Name() == Epistemic_Desire_Name)
			{
				if(Attentional_Desire instanceof TEpistemic_Desire)
				{
					this.Print_Epistemic_Desire( (TEpistemic_Desire) Attentional_Desire );
				}
				else
				{
					System.out.println(Epistemic_Desire_Name+" is not an Epistemic_Desire. Impossibile to print it.");
				}
				
			}
		}
		
		if( !found )
		{
			System.out.println("No Belief for Quality Desire: "+ Epistemic_Desire_Name);
		}
	
	}
	
	public void Print_Epistemic_Desire(TEpistemic_Desire Epistemic_Desire)
	{
		if( Epistemic_Desire == null )
		{
			System.out.println("No Green Desire provided");
		}
		else
		{
			String Text = "Epistemic_Desire: " +Epistemic_Desire.Get_Name() +"\n"
					+ "Belief Name Epistemic_Desire Desire: "+Epistemic_Desire.Get_Belief_Name()+"\n"
					+ "Belief Saliency: "+Epistemic_Desire.Get_Saliency()+"\n"
					+ "Belief Reward: "+Epistemic_Desire.Get_Reward()+"\n"
					+ "Belief Relax_Preference: "+Epistemic_Desire.Get_Relax_Preference()+"\n"
					+ "Belief Check Satisfation: "+Epistemic_Desire.Check_Satisfation();
			int i = 0;
			for(String Green_Name: Epistemic_Desire.Get_List_Green_Standing_Desire_Name())
			{
				i++;
				Text += "\nGreen Desire Desire"+i+": "+Green_Name;
			}
			i = 0;
			for(String Quality_Name: Epistemic_Desire.Get_List_Quality_Standing_Desire_Name())
			{
				i++;
				Text += "\nBelief Name Green Desire Desire"+i+": "+Quality_Name;
			}
			System.out.println(Text);		
		}
	}
	
	public void Print_Practical_Desire(TPractical_Desire Practical_Desire)
	{
		if( Practical_Desire == null )
		{
			System.out.println("No Practical Desire provided");
		}
		else
		{
			String Text = "Practical_Desire: " +Practical_Desire.Get_Name() +"\n"
					+ "Belief Saliency: "+Practical_Desire.Get_Saliency()+"\n"
					+ "Belief Reward: "+Practical_Desire.Get_Reward()+"\n"
					+ "Belief Relax_Preference: "+Practical_Desire.Get_Relax_Preference()+"\n"
					+ "Belief Check Satisfation: "+Practical_Desire.Check_Satisfation();
			int i = 0;
			for(String Green_Name: Practical_Desire.Get_List_Green_Standing_Desire_Name())
			{
				i++;
				Text += "\nGreen Desire "+i+": "+Green_Name;
			};
			i = 0;
			for(String Quality_Name: Practical_Desire.Get_List_Quality_Standing_Desire_Name())
			{
				i++;
				Text += "\nQuality Desire "+i+": "+Quality_Name;
			};
			Text += "\nTrigger Formula "+i+": "+Practical_Desire.Get_Trigger_Condition_Formula();
			i = 0;
			for(String Trigger_Name: Practical_Desire.Get_Trigger_Condition_Names())
			{
				i++;
				Text += "\nTrigger Name "+i+": "+Trigger_Name;
			};
			Text += "\nFinal State Formula "+Practical_Desire.Get_Final_State_Formula();
			i = 0;
			for(String Final_State_Name: Practical_Desire.Get_Final_State_Names())
			{
				i++;
				Text += "\nFinal State Name "+i+": "+Final_State_Name;
			};
			Text += "\nFinally Start Time "+i+": "+Practical_Desire.Get_Finally_Start();
			Text += "\nFinally End Time "+i+": "+Practical_Desire.Get_Finally_End();
			System.out.println(Text);		
		}
	}
	
	private Object Normalize_Object(TDouble_Object Double_Object, ArrayList<?> List_Object)
	{
		Object result = null;
		String Type = (String) Double_Object.Get_Object_First();
		String Value = (String) Double_Object.Get_Object_Second();
		switch (Type) 
		{
		/**
		 * 	Belief: handled
			Epistemic_Desire
			Green_Desire
			Practical_Desire
			Predicate
			Quality_Desire
		 */
			case "Belief": 
				if(List_Object.getFirst() instanceof TBelief)
				{
					// Cast no controllato: veloce ma potenzialmente rischioso
			        // È necessario un doppio cast per "convincere" il compilatore
					@SuppressWarnings("unchecked")
					List<TBelief> tempList = (List<TBelief>) (List<?>) List_Object;
	
					// 2. Usa il costruttore di ArrayList per creare una copia
					ArrayList<TBelief> List_Belief = new ArrayList<>(tempList);
					for(TBelief Belief: List_Belief)
					{
						if( Value.equals(Belief.Get_Name()) )
						{
							result = Belief;
							
						}
					}
				}
				break;
				
			default:
			{
				result = Double_Object;
				throw new IllegalArgumentException("Conversion not handled for Object Type: " + Type);
			}
				
		}
		
		return result;
	}
	
	/**
	 * 
	 */
	private void Normalize_Data()
	{
		/***
		 * Order to read data:
		 * 1- Predicates
		 * 2- Beliefs
		 * 3- Green Desires
		 * 4- Quality Desires
		 * 5- Functional Desires
		 * 6- Epistemic Desires
		 */
		//01- Predicates
		this.Normalize_Predicates();
		
		//02- Beliefs
		this.Normalize_Beliefs();
		
		//03- Green_Desires
		this.Normalize_Green_Desires();
		
		//04- Quality_Desires
		this.Normalize_Quality_Desires();
		
		//05- Functional Desires
		this.Normalize_Practical_Desires();
		
		//06- Epistemic Desires
		this.Normalize_Epistemic_Desires();
	}
	
	/**
	 * Now, It associate Subject or Object_Complement to:
	 * - A Belief (TBelief)
	 * - Coming soon other types..
	 */
	private void Normalize_Predicat()
	{
		Object Normaliezed_Object = null;
		for(TPredicate Predicate: this.Predicates.Read())
		{
			if(Predicate.Get_Subject() instanceof TDouble_Object)
			{
				Normaliezed_Object = this.Normalize_Object( 
						(TDouble_Object) Predicate.Get_Subject(), this.Beliefs.Read());
				if(Normaliezed_Object != null)
				{
					TBelief Belief = (TBelief) Normaliezed_Object;
					Predicate.Set_Subject( Belief );
					
					System.out.println(( (TBelief) Predicate.Get_Subject()).Get_Name()+ " "+Predicate.Get_Subject());
				}
			}
			if(Predicate.Get_Object_Complement() instanceof TDouble_Object)
			{
				Normaliezed_Object = this.Normalize_Object( 
						(TDouble_Object) Predicate.Get_Subject(), this.Beliefs.Read());
				if(Normaliezed_Object != null)
				{
					TBelief Belief = (TBelief) Normaliezed_Object;
					Predicate.Set_Subject( Belief );
					System.out.println(Belief.Get_Name()+ " "+Belief);
				}
				
				
			}
		}
	}
	
	private void Load_Other_Data( String Dir_Path )
	{
		this.Load_Critical_Beliefs(Dir_Path);
		
	}
	
	private void Load_Critical_Beliefs( String Dir_Path )
	{
		String File_to_read = Dir_Path + "Critical_Beliefs.xml";
		Document doc = this.Get_Document_Xml( File_to_read );
		if (doc == null)
		{
			
			return;
		}
		// 5. Ottieni tutti gli elementi
		ArrayList<TBelief> Loaded_Beliefs = new ArrayList<TBelief>();
		
        NodeList Node_List = doc.getElementsByTagName("Critical_Belief");
        String Belief_Name = "";
        
        /**
         * I'm not interested in the description. 
         * This field is only useful for the agent and case study definition tool.
         */
//        String Description = "";
        
        // 6. Itera su ogni "MappedDesire" e popola la mappa
        for (int i = 0; i < Node_List.getLength(); i++) 
        {
            Node node = Node_List.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE)
            {
                Element element = (Element) node;

                // Estrai i valori dai tag figli
                Belief_Name = this.Get_Element_Text_Content(element, "Critical_Belief_Name");
                TBelief Belief = this.Map_Beliefs.get(Belief_Name);
                if(Belief != null)
                {
                	Loaded_Beliefs.add( Belief );
                }
                else
                {
                	System.out.println("No Important Belief to load for Critical Belief: "+Belief_Name);
                }
                
            }
        }
        this.Important_Beliefs.Add_All( Loaded_Beliefs );
        Loaded_Beliefs.clear();
	}
	
	public void Initialize(String Dir_Path)
	{
		/**
		 * Long Memory load each information for the Agent
		 */
		this.Load_Data( Dir_Path );
		/**
		 * Now, the agent has to normalize (associate) any data that is a reference to an object
		 * e.g.: A Name of a Belief to a Belief
		 */
		this.Normalize_Data();
		/**
		 * Load Other information: e.g. Important beliefs, etc.
		 */
		this.Load_Other_Data(Dir_Path);
	}
	
	public ArrayList<TGreen_Desire> Get_All_Green_Desires()
	{
		return this.Green_Desires.Read();
	}
	
	public ArrayList<TGreen_Desire> Get_Green_Desires_by_Names(ArrayList<String> Green_Names)
	{
		ArrayList<TGreen_Desire> result = new ArrayList<TGreen_Desire>();
		for(String Name: Green_Names)
		{
			TGreen_Desire Green_Desire = this.Map_Green_Desires.get( Name ); 
			if( Green_Desire != null)
			{
				result.add( Green_Desire );				
			}
		}
		return result;
	}
	
	public ArrayList<TQuality_Desire> Get_All_Quality_Desires()
	{
		return this.Quality_Desires.Read();
	}
	
	public ArrayList<TQuality_Desire> Get_Quality_Desires_by_Names(ArrayList<String> Quality_Names)
	{
		ArrayList<TQuality_Desire> result = new ArrayList<TQuality_Desire>();
		for(String Name: Quality_Names)
		{
			TQuality_Desire Quality_Desire = this.Map_Quality_Desires.get( Name ); 
			if( Quality_Desire != null)
			{
				result.add( Quality_Desire );				
			}
			
		}
		return result;
	}
	
	public ArrayList<TAttentional_Desire> Get_Satisfied_Attentional_Desires()
	{
		return this.Satisfied_Attentional_Desires.Read();
	}
	
	public HashMap<String, TBelief> Get_Selected_Beliefs(HashSet<String> Beliefs_Names)
	{
		HashMap<String, TBelief> result = new HashMap<String, TBelief>();
//		synchronized (Map_Beliefs) {
	        for (String name : Beliefs_Names) 
	        {
	            TBelief belief = this.Map_Beliefs.get(name);
	            if (belief != null) 
	            {
	            	result.put(name, belief);
	            }
	        }
//	    }
		return result;
	}
	
	public HashMap<String, TRegion> Get_Selected_Regions(HashSet<String> Regions_Names)
	{
		HashMap<String, TRegion> result = new HashMap<String, TRegion>();
		HashMap<String, TRegion> temp_regions = new HashMap<String, TRegion>();
		temp_regions.putAll( this.Map_Regions);
//		synchronized (Map_Beliefs) {
	        for (String name : Regions_Names) 
	        {
	        	TRegion region = temp_regions.get( name );
	            if (region != null) 
	            {
	            	result.put(name, region);
	            }
	        }
//	    }
	    temp_regions.clear();
		return result;
	}
	
	public void Acquire_Agent_Components()
	{
		this.Executive_Memory_Maintenance_Function = this.Agent.Get_Executive_Memory_Maintenance_Function();
	}
	
}
