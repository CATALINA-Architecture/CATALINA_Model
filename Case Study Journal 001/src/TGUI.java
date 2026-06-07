import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;


public class TGUI extends JFrame 
{
	public TMap_Panel Map_Panel;
	public JButton btnSendSignal;
	public JButton btnSendLowFuel;
	
	public JTextArea Log_Memo;
	public Autonomous_Vehicle_Demo Demo;
	public TFloat_Window GlobaL_Workspace_View;

    public TGUI(String Map_PathPath, String Car_Path, List<Point> Positions,
    		Autonomous_Vehicle_Demo demo) {
    	
        setTitle("Autonomous Vehicle Agent Simulation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.Demo = demo;
        Map_Panel = new TMap_Panel(Map_PathPath, Car_Path, Positions, demo);
        add(Map_Panel, BorderLayout.CENTER);

        
        /**
         * 
         */
     // --- MODIFICA QUI ---
        
        // 1. Creiamo un pannello per contenere i bottoni
        JPanel buttonPanel = new JPanel(); 
        JPanel rightPanel = new JPanel();
        rightPanel.setPreferredSize(new Dimension(400, 50));
//        new Dimension(200, 50)
        
        // TRUCCO: Impostiamo un layout verticale per il pannello destro
        // Così i bottoni stanno uno sopra l'altro.
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        
        // 2. Creiamo e configuriamo il primo bottone
        JButton Move_Button = new JButton("Move Car");
        Move_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Map_Panel.Move_Car();
            }
        });
        
        JButton Move_Button2 = new JButton("Start AVA");
        Move_Button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
					Map_Panel.Start_AV();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        });
        
        JButton Move_Button3 = new JButton("Suspend AVA");
        Move_Button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
					Map_Panel.Suspend_AV();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        });
        
        JButton Move_Button4 = new JButton("Resume AVA");
        Move_Button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Map_Panel.Resume_AV();
            }
        });
        
        // 3. Creiamo e configuriamo il secondo bottone
        btnSendSignal = new JButton("Signal: Block the route!");
//        btnSaluta.addActionListener(e -> {
//            // Usiamo la tua funzione Show_Message
////            Show_Message("Saluto", "Ciao mondo", JOptionPane.INFORMATION_MESSAGE);
//        	
//        });
        
        btnSendSignal.setFont(new Font("Arial", Font.BOLD, 20));
        btnSendSignal.setPreferredSize(new Dimension(200, 50));
        
        btnSendLowFuel = new JButton("Signal: Low Fuel!");
        btnSendLowFuel.setFont(new Font("Arial", Font.BOLD, 20));
        btnSendLowFuel.setPreferredSize(new Dimension(200, 50));
        
        Log_Memo = new javax.swing.JTextArea();
        Log_Memo.setEditable(false); // Se vuoi che sia solo di lettura (come un log)
        Log_Memo.setLineWrap(true);  // A capo automatico
        Log_Memo.setWrapStyleWord(true);
        
        // Opzionale: Font tipo "Console"
        Log_Memo.setFont(new Font("Monospaced", Font.PLAIN, 20));
//        Log_Memo.setPreferredSize(new Dimension(400, 200));
        
     // --- 3. AGGIUNTA DELLO SCROLL (Fondamentale!) ---
        // In Java la scrollbar non è automatica, serve il JScrollPane
        javax.swing.JScrollPane scrollPane = new javax.swing.JScrollPane(Log_Memo);
        scrollPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        
        // Impostiamo una dimensione preferita per non farla collassare
//        scrollPane.setPreferredSize(new Dimension(200, 300));
        
        
        
        
        // 4. Aggiungiamo i bottoni al PANNELLO (non direttamente al frame)
//        buttonPanel.add(Move_Button);
//        buttonPanel.add(btnSaluta);
        rightPanel.add(Move_Button);
        rightPanel.add(Move_Button2);
        rightPanel.add(Move_Button3);
        rightPanel.add(Move_Button4);
//        rightPanel.add(Move_Button3);
        // Aggiungiamo un piccolo spazio vuoto tra i bottoni (opzionale)
        rightPanel.add(javax.swing.Box.createVerticalStrut(10)); 
        rightPanel.add(btnSendSignal);
        rightPanel.add(btnSendLowFuel);
        
        
        /**
         * Fuel Level Section
         */
//        JPanel Fuel_Level_Panel = new JPanel();
//        javax.swing.JTextField Fuel_Leve_lInput = new javax.swing.JTextField(20);
//        Fuel_Level_Panel.add(Fuel_Leve_lInput);
//        Fuel_Level_Panel.add(btnSendLowFuel);
//        rightPanel.add(Fuel_Level_Panel);
        
        
        
        
        
//        rightPanel.add(Move_Button3);
//        rightPanel.add(Move_Button4);
        
        
        rightPanel.add(javax.swing.Box.createVerticalStrut(20));

        // 5. Aggiungiamo il PANNELLO alla zona SUD della finestra
        add(buttonPanel, BorderLayout.SOUTH);
        
     // Aggiungiamo lo scrollPane (che contiene la Memo) al pannello
        rightPanel.add(scrollPane);
     // 4. ANCORIAMO IL PANNELLO A DESTRA
        add(rightPanel, BorderLayout.EAST);
        
        
        
        JPanel inputPanel = new JPanel(); // Usa il FlowLayout di default (allineati orizzontalmente)

        // 2. Creiamo il TEdit (JTextField)
        // "20" è la larghezza approssimativa in colonne di testo
        javax.swing.JTextField txtInput = new javax.swing.JTextField(20);

        // 3. Creiamo il Bottone
        JButton btnInvia = new JButton("Invia");

        // 4. Definiamo l'AZIONE (La funzione da chiamare)
        // La scriviamo una volta sola per usarla due volte
        ActionListener inviaAzione = e -> {
            // Prendi il testo
//            String testoDigitato = txtInput.getText();
            
            // Chiama la tua funzione
//            ElaboraStringa(testoDigitato);
//            this.Demo.Functions_for_Plan_Execution.String_Response_Sended =
//            		txtInput.getText();
//            this.Demo.Functions_for_Plan_Execution.Response_Sended = true;
            
            this.Demo.Perception_Processing_functions.Virtual_TCS.String_Response_Sended =
            		txtInput.getText();
            this.Demo.Perception_Processing_functions.Virtual_TCS.Response_Sended = true;
            
            // Opzionale: Pulisci il campo dopo l'invio
            txtInput.setText(""); 
        };

        // 5. Colleghiamo l'azione a ENTRAMBI i componenti
        btnInvia.addActionListener(inviaAzione); // Si attiva al Click
        txtInput.addActionListener(inviaAzione); // Si attiva premendo ENTER mentre si scrive!

        // 6. Assembliamo il pannello
        inputPanel.add(txtInput);
        inputPanel.add(btnInvia);

        // 7. Aggiungiamo il pannello alla finestra
        // Esempio: Lo mettiamo in cima (NORTH) o dove preferisci
        rightPanel.add(inputPanel, BorderLayout.NORTH);

        // --------------------
        
        /**
         * 
         * 
         */
//        JButton Move_Button = new JButton("Move Car");
//        Move_Button.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                Map_Panel.Move_Car();
//            }
//        });
//        add(Move_Button, BorderLayout.SOUTH);
//
//        // Fit window size to components
//        
//        JButton btnSaluta = new JButton("Cliccami per un saluto");
//
//        // 4. Aggiungi l'AZIONE al bottone (L'evento Click)
//        // Usiamo una "Lambda Expression" (il modo moderno di Java 8+)
//        btnSaluta.addActionListener(e -> {
//            
//            // Questo codice viene eseguito quando clicchi:
////            JOptionPane.showMessageDialog(frame, "Ciao mondo");
////        	sho
//            
//        });
        
        /**
         * 
         * 
         */
        
        pack(); 
        // Center the window on the screen
        setLocationRelativeTo(null); 
        setVisible(true);
        
        //GlobaL_Workspace_View
        this.GlobaL_Workspace_View = new TFloat_Window(this, "Monitor");
        this.GlobaL_Workspace_View.setVisible(true);
    }
    
    public void Show_Message(String Title, String Message, Integer Type_Message)
    {
    	if(Type_Message == null)
    	{
    		Type_Message = JOptionPane.INFORMATION_MESSAGE;
    	}
    	JLabel Message_Label = new JLabel(Message);

        // Set the font of the JLabel
        // "Arial" is the font name, 
    	// Font.PLAIN is the style (you can use Font.BOLD, Font.ITALIC),
    	// 12 is the text size
        Message_Label.setFont(new Font("Arial", Font.PLAIN, 12));
        
    	JOptionPane.showMessageDialog(this, Message_Label , Title, Type_Message);
    }

}
