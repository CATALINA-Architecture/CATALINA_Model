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

public class TMap_Stimulus_To_Epistemic_Desires
{
	private HashMap<String, TMapped_Epistemic_Desire> List_Mapped_Epicstemic_Desires;
	private String XML_File_Path;// = "epistemic_desires.xml"; // Percorso del file XML
	
	public TMap_Stimulus_To_Epistemic_Desires()
	{
		this.List_Mapped_Epicstemic_Desires = new HashMap<String, TMapped_Epistemic_Desire>();
		this.XML_File_Path = "";
	}
	
	public void Add_Stimulus_Type__to_Epistemic_Desire(String Stimulus_Type, TMapped_Epistemic_Desire Epistemic_Desire)
	{
		this.List_Mapped_Epicstemic_Desires.put(Stimulus_Type, Epistemic_Desire);
	}
	
	public void Set_File_Path(String Value)
	{
		this.XML_File_Path = Value;
	}

	public void Load_Mapped_Epistemic_Desires()
	{
	    
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
            File xmlFile = new File(XML_File_Path);
            if (!xmlFile.exists()) 
            {
                System.err.println("Error: The XML file '" + XML_File_Path + "' doesn't exist!");
                return;
            }
            
            this.List_Mapped_Epicstemic_Desires.clear();
            
            Document doc = builder.parse(xmlFile);

            // 4. Normalizza il Document (utile per rimuovere nodi di testo vuoti)
            doc.getDocumentElement().normalize();

            // 5. Ottieni tutti gli elementi "MappedDesire"
            NodeList mappedDesireNodes = doc.getElementsByTagName("MappedDesire");

            // 6. Itera su ogni "MappedDesire" e popola la mappa
            for (int i = 0; i < mappedDesireNodes.getLength(); i++) {
                Node node = mappedDesireNodes.item(i);

                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    // Estrai i valori dai tag figli
                    String typeStimulus = this.Get_Element_Text_Content(element, "StimulusType");
                    Double saliency = Double.parseDouble(this.Get_Element_Text_Content(element, "Saliency"));
                    Double reward = Double.parseDouble(this.Get_Element_Text_Content(element, "Reward"));
                    Double relaxPreference = Double.parseDouble(this.Get_Element_Text_Content(element, "RelaxPreference"));
                    String TriggerConditionFormula = this.Get_Element_Text_Content(element, "TriggerConditionFormula");

                    ArrayList<String> TriggerConditionNames = this.Get_Desire_Names(element, "TriggerConditionNames");
                    ArrayList<String> greenDesires = this.Get_Desire_Names(element, "GreenDesires");
                    ArrayList<String> qualityDesires = this.Get_Desire_Names(element, "QualityDesires");
                    ArrayList<String> BeleafsReasonerDesires = this.Get_Desire_Names(element, "Beliefs_for_Reasoner");

                    // Crea l'oggetto TMapped_Epistemic_Desire
                    TMapped_Epistemic_Desire desire = new TMapped_Epistemic_Desire(
                            typeStimulus, saliency, TriggerConditionFormula,
                            TriggerConditionNames, reward, relaxPreference,
                            greenDesires, qualityDesires, BeleafsReasonerDesires, null
                    );

                    // Aggiungi l'oggetto alla HashMap usando Type_Stimulus come chiave
                    this.List_Mapped_Epicstemic_Desires.put(typeStimulus, desire);
//                    System.out.println("Caricato desiderio per StimulusType: " + typeStimulus);
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
	
	private ArrayList<String> Get_Desire_Names(Element parentElement, String parentTagName) 
	{
        ArrayList<String> names = new ArrayList<String>();
        NodeList parentList = parentElement.getElementsByTagName(parentTagName);
        if (parentList != null && parentList.getLength() > 0) 
        {
            Element parentEl = (Element) parentList.item(0);
            NodeList desireNameNodes = parentEl.getElementsByTagName("DesireName");
            for (int i = 0; i < desireNameNodes.getLength(); i++) 
            {
                Node node = desireNameNodes.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE)
                {
                    names.add(node.getTextContent());
                }
            }
        }
        return names;
    }

    // Metodo per ottenere la mappa (per test o utilizzo esterno)
    public HashMap<String, TMapped_Epistemic_Desire> Get_Mapped_Epicstemic_Desires() 
    {
        return this.List_Mapped_Epicstemic_Desires;
    }
    
    private void Create_Example_File()
    {
    	this.XML_File_Path = "Stimulus_2_Epistemic_Desires.mxl";
    	// --- Crea il file XML di esempio per il test ---
        String xmlContent = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" +
                            "<EpistemicDesires>\n" +
                            "    <MappedDesire>\n" +
                            "        <StimulusType>UrgentCall</StimulusType>\n" +
                            "        <Saliency>0.9</Saliency>\n" +
                            "        <Reward>0.8</Reward>\n" +
                            "        <RelaxPreference>0.1</RelaxPreference>\n" +
                            "        <GreenDesires>\n" +
                            "            <DesireName>RespondQuickly</DesireName>\n" +
                            "            <DesireName>Prioritize</DesireName>\n" +
                            "        </GreenDesires>\n" +
                            "        <QualityDesires>\n" +
                            "            <DesireName>EnsureAccuracy</DesireName>\n" +
                            "        </QualityDesires>\n" +
                            "    </MappedDesire>\n" +
                            "    <MappedDesire>\n" +
                            "        <StimulusType>NewEmail</StimulusType>\n" +
                            "        <Saliency>0.5</Saliency>\n" +
                            "        <Reward>0.3</Reward>\n" +
                            "        <RelaxPreference>0.7</RelaxPreference>\n" +
                            "        <GreenDesires>\n" +
                            "            <DesireName>CheckInbox</DesireName>\n" +
                            "            <DesireName>provaaa</DesireName>\n" +
                            "        </GreenDesires>\n" +
                            "        <QualityDesires>\n" +
                            "            <DesireName>CategorizeEmail</DesireName>\n" +
                            "            <DesireName>ReplyPolitely</DesireName>\n" +
                            "        </QualityDesires>\n" +
                            "    </MappedDesire>\n" +
                            "</EpistemicDesires>";

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
