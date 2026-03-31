package com.Catalina_Model.Catalina_V_0_3;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class TMap_Belief_To_Salience_Stimuli
{
	private HashMap<String, Double> List_Mapped_Salience_Stimuli;
	private String XML_File_Path;// = "epistemic_desires.xml"; // Percorso del file XML
	private String Default_File_Name;
	
	public TMap_Belief_To_Salience_Stimuli()
	{
		this.List_Mapped_Salience_Stimuli = new HashMap<String, Double>();
		this.XML_File_Path = "";
		this.Default_File_Name = "Map_Belief_To_Salience_Stimuli.xml";
	}
	
	public void Add_Type_Belief_To_Salience_Stimuli(String Stimulus_Type, Double Saliency)
	{
		this.List_Mapped_Salience_Stimuli.put(Stimulus_Type, Saliency);
	}
	
	public void Set_File_Path(String Value)
	{
		this.XML_File_Path = Value;
	}

	public void Load_Mapped_Epistemic_Desires(String Dir_Path)
	{
		String Filename = Dir_Path +"\\"+this.Default_File_Name ;
		if ( Filename == null || Filename.equals(""))
	    {
			System.out.print("TMap_Belief_To_Salience_Stimuli - error in Filename: "+Filename);
	    	return;
	    }
		this.Set_File_Path( Filename );
		
	    if (this.XML_File_Path == null || this.XML_File_Path.equals(""))
	    {
	    	System.out.print("TMap_Belief_To_Salience_Stimuli - error in XML_File_Path: "+this.XML_File_Path);
	    	return;
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
            File xmlFile = new File(this.XML_File_Path);
            if (!xmlFile.exists()) {
                System.err.println("Error: The XML file '" + this.XML_File_Path + "' doesn't exist!");
                return;
            }
            
            this.List_Mapped_Salience_Stimuli.clear();
            
            Document doc = builder.parse(xmlFile);

            // 4. Normalizza il Document (utile per rimuovere nodi di testo vuoti)
            doc.getDocumentElement().normalize();

            // 5. Ottieni tutti gli elementi "MappedDesire"
            NodeList Mapped_Stimuli = doc.getElementsByTagName("Map_Belief_To_Salience_Stimulus");
            // 6. Itera su ogni "MappedDesire" e popola la mappa
            for (int i = 0; i < Mapped_Stimuli.getLength(); i++) {
                Node node = Mapped_Stimuli.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element element = (Element) node;

                    // Estrai i valori dai tag figli
                    String typeStimulus = this.Get_Element_Text_Content(element, "Belief_Type");
                    Double saliency = Double.parseDouble(this.Get_Element_Text_Content(element, "Value").replace(",", "."));

                    // Aggiungi l'oggetto alla HashMap usando Type_Stimulus come chiave
                    this.List_Mapped_Salience_Stimuli.put(typeStimulus, saliency);
                }
            }
        } 
		catch (ParserConfigurationException | SAXException | IOException | NumberFormatException e) 
		{
//            System.err.println("Errore durante il parsing o la lettura del file XML: " + e.getMessage());
            e.printStackTrace(); // Stampa lo stack trace per debug
        }
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

    // Metodo per ottenere la mappa (per test o utilizzo esterno)
    public HashMap<String, Double> Get_Mapped_Stimuli() 
    {
        return this.List_Mapped_Salience_Stimuli;
    }
    
    private void Create_Example_File()
    {
    	this.XML_File_Path = "Belief_2_Salience_Stimuli.mxl";
    	// --- Crea il file XML di esempio per il test ---
        String xmlContent = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
                            "<Stimuli>\n" +
                            "    <Stimulus>\n" +
                            "        <Type_Belief>Urgent_1</Type_Belief>\n" +
                            "        <Saliency>0.3</Saliency>\n" +
                            "    </Stimulus>\n" +
                            "    <Stimulus>\n" +
                            "        <Type_Belief>Urgent_2</Type_Belief>\n" +
                            "        <Saliency>0.5</Saliency>\n" +
                            "    </Stimulus>\n" +
                            "    <Stimulus>\n" +
                            "        <Type_Belief>Hola</Type_Belief>\n" +
                            "        <Saliency>0.99</Saliency>\n" +
                            "    </Stimulus>\n" +
                            "</Stimuli>";

        try {
            java.nio.file.Files.write(java.nio.file.Paths.get(this.XML_File_Path), xmlContent.getBytes());
//            System.out.println("File '" + XML_FILE_PATH + "' creato per il test.");
        } 
        catch (IOException e) 
        {
//            System.err.println("Errore nella creazione del file XML: " + e.getMessage());
            e.printStackTrace();
            return;
        }
    }
}
