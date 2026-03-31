import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import java.awt.*;

public class TFloat_Window extends JDialog {

	private JTextArea TextArea;
	private JTextPane textPane;
	private StyledDocument doc;
	private SimpleAttributeSet stileGrassetto;
	private SimpleAttributeSet stileNormale;
	private SimpleAttributeSet Custom_Style;

    // Costruttore
//    public TFloat_Window_old(JFrame parentFrame, String Title) {
//        // Il 'false' finale è FONDAMENTALE: rende la finestra "Non Modale".
//        // Significa che puoi cliccare sulla finestra principale anche se questa è aperta.
//        super(parentFrame, Title, false);
//
//        // Impostazioni grafiche
//        setSize(400, 300);
//        setLocationRelativeTo(parentFrame); // Appare vicino alla finestra madre
//        setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE); // Se la chiudi, si nasconde solo
//
//        // Creazione dell'area di testo
//        TextArea = new JTextArea();
//        TextArea.setEditable(false); // Metti 'true' se vuoi poterci scrivere tu a mano
//        TextArea.setFont(new Font("Monospaced", Font.PLAIN, 12)); // Font tipo console
//
//        // Fondamentale: JScrollPane per permettere lo scorrimento
//        JScrollPane scrollPane = new JScrollPane(TextArea);
//        
//        // Aggiungiamo lo scroll pane alla finestra
//        add(scrollPane, BorderLayout.CENTER);
//    }
    
    public TFloat_Window(JFrame parentFrame, String Title) {
        // Il 'false' finale è FONDAMENTALE: rende la finestra "Non Modale".
        // Significa che puoi cliccare sulla finestra principale anche se questa è aperta.
        super(parentFrame, Title, false);

        // Impostazioni grafiche
        setSize(400, 300);
        setLocationRelativeTo(parentFrame); // Appare vicino alla finestra madre
        setDefaultCloseOperation(JDialog.HIDE_ON_CLOSE); // Se la chiudi, si nasconde solo

        // Creazione dell'area di testo
//        TextArea = new JTextArea();
//        TextArea.setEditable(false); // Metti 'true' se vuoi poterci scrivere tu a mano
//        TextArea.setFont(new Font("Monospaced", Font.PLAIN, 12)); // Font tipo console
        textPane = new JTextPane();
        
        doc = textPane.getStyledDocument();
        stileGrassetto = new SimpleAttributeSet();
        StyleConstants.setBold(stileGrassetto, true);
        stileNormale = new SimpleAttributeSet();
        Custom_Style = new SimpleAttributeSet();
        StyleConstants.setBold(stileNormale, false);

        // Fondamentale: JScrollPane per permettere lo scorrimento
//        JScrollPane scrollPane = new JScrollPane(TextArea);
        JScrollPane scrollPane = new JScrollPane(textPane);
        
        // Aggiungiamo lo scroll pane alla finestra
        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Metodo per aggiungere testo dall'esterno
     */
//    public void Add_Data_to_TextArea(String text) {
//        // Aggiunge il testo in coda
//        TextArea.append(text + "\n");
//        
//        // Fa scorrere automaticamente la visuale verso il basso (Auto-scroll)
//        TextArea.setCaretPosition(TextArea.getDocument().getLength());
//    }
    
    public void Add_Simple_Text(String text) 
    {
        // Aggiunge il testo in coda
//    	textPane.append(text + "\n");
        
        // Fa scorrere automaticamente la visuale verso il basso (Auto-scroll)
//    	textPane.setCaretPosition(TextArea.getDocument().getLength());
    	try 
    	{
			doc.insertString(doc.getLength(), text + "\n", stileNormale);
			textPane.setCaretPosition(textPane.getDocument().getLength());
		} 
    	catch (BadLocationException e) 
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//    	doc.insertString(doc.getLength(), "Attenzione: ", stileGrassetto);
    }
    
    public void Add_Bold_Text(String text) 
    {
    	try 
    	{
			doc.insertString(doc.getLength(), text + "\n", stileGrassetto);
			textPane.setCaretPosition(textPane.getDocument().getLength());
		} 
    	catch (BadLocationException e) 
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void Add_Text(String text, Boolean Bold_or_Not, Color colore) 
    {
    	try 
    	{
    		if (colore == null)
    		{
    			colore = Color.BLACK;
    		}
    		StyleConstants.setForeground(Custom_Style, colore);
    		if (Bold_or_Not == null)
    		{
    			Bold_or_Not = false;
    		}
    		StyleConstants.setBold(Custom_Style, Bold_or_Not);
    		
//    		doc.insertString(doc.getLength(), text + "\n", Custom_Style);
    		doc.insertString(doc.getLength(), text , Custom_Style);
			textPane.setCaretPosition(textPane.getDocument().getLength());
		} 
    	catch (BadLocationException e) 
    	{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    /**
     * Metodo per pulire tutto
     */
    public void Pulisci() {
        TextArea.setText("");
    }
}