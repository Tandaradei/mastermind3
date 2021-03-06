package playingfield;

import java.util.Random;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import javax.swing.JScrollPane;

import network.*;
import customUI.*;

/**
 * Displays current game from client/server view
 */
public class PlayingField extends JPanel {

    JPanel history;
    int historyCount = 0;
    int codeLength = 4;
    String lastCode = "";
    String colors;
    Boolean isServer = false;
    JComboBox[] comboBoxes;
    NetworkParticipant netParticipant;
    /**
     * Creates new PlayingField and initializes components
     * @param isServer display playingField as Server or Client?
     * @param colors substring of all colors to play with
     * @param codeLength length of code to guess
     */
    public PlayingField(boolean isServer, String colors, int codeLength) {
    	this.isServer = isServer;
        this.colors = colors;
        this.codeLength = codeLength;
        comboBoxes = new JComboBox[codeLength];
        initComponents();
        initActivePane();
        initHistory();
        randomButton.setBackground(Color.WHITE);
        addButton.setBackground(Color.WHITE);
        if(isServer){
                randomButton.setVisible(true);
                addButton.setText("Set as Code");
        }
		
    }
    
    public void setNetParticipant(NetworkParticipant netParticipant){
    	this.netParticipant = netParticipant;
    }

    /**
     * Auto generated code
     */
    private void initComponents() {
	
		statusLabel = new javax.swing.JTextField("Initiated");
        activePane = new javax.swing.JPanel();
        addButton = new javax.swing.JButton();
		randomButton = new javax.swing.JButton();
        historyPane = new javax.swing.JPanel();

        statusLabel.setEditable(false);

        activePane.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        activePane.setPreferredSize(new java.awt.Dimension(426, 67));
		
		randomButton.setVisible(false);
		randomButton.setFont(new java.awt.Font("Segoe UI", 0, 14));
        randomButton.setText("Randomize");
        randomButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                randomButtonMouseClicked(evt);
            }
        });
		
        randomButton.setFont(new java.awt.Font("Segoe UI", 0, 14));
        addButton.setText("Send");
        addButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addButtonMouseClicked(evt);
            }
        });
		
		activePane.add(randomButton);
		activePane.add(addButton);

        historyPane.setBackground(new java.awt.Color(204, 204, 204));

        javax.swing.GroupLayout historyPaneLayout = new javax.swing.GroupLayout(historyPane);
        historyPane.setLayout(historyPaneLayout);
        historyPaneLayout.setHorizontalGroup(
            historyPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        historyPaneLayout.setVerticalGroup(
            historyPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
			.addComponent(statusLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
            .addComponent(activePane, javax.swing.GroupLayout.DEFAULT_SIZE, 435, Short.MAX_VALUE)
            .addComponent(historyPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
				.addGap(10, 10, 10)
                .addComponent(statusLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(activePane, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addComponent(historyPane, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

	
	private void randomButtonMouseClicked(java.awt.event.MouseEvent evt) {
		String[] array = colors.split("(?!^)");
		Random rand = new Random();
		for(int i = 0; i < codeLength; ++i){
			String randomItem = array[rand.nextInt(array.length)];
			comboBoxes[i].setSelectedItem(randomItem);
		}
	}
	
	
    /**
     * Performs action when 'Send'/'Set as code' button was clicked
     * @param evt
     */
    private void addButtonMouseClicked(java.awt.event.MouseEvent evt) {
    	String code = "";
        
        for(int i = 0; i < codeLength; ++i){
            code += comboBoxes[i].getSelectedItem();
        }
        lastCode = code;
        
        netParticipant.sendCode(code);
        if(isServer){
        	for(int i = 0; i < codeLength; ++i){
                comboBoxes[i].setEnabled(false);
            }
        	addButton.setVisible(false);
			randomButton.setVisible(false);
        }
        addButton.setBackground(Color.RED);
    }//GEN-LAST:event_addButtonMouseClicked

    /**
     * returns Color.BLACK for 'B' or Color.WHITE
     * @param input
     * @return
     */
    private Color charToColorResponse(char input){
        if(input == 'B'){
            return Color.black;
        }
        return Color.white;
        
    }
    
    /**
     * Returns new JPanel with number, and graphical representation of code and response
     * @param code code to render
     * @param response response to render
     * @param index number in history
     * @return
     */
    private JPanel getListEntryRendered(String code, String response, int index){
		Color background = historyCount % 2 == 0 ? new Color(191, 191, 191) : new Color(223, 223, 223);
	
        JPanel entry = new JPanel();
		entry.setBackground(background);
		GridBagConstraints gbc = new GridBagConstraints();
		entry.setLayout(new GridBagLayout());
		gbc.gridx = 0;
		gbc.gridy = 0;
        entry.add(new JLabel(((Integer)(index)).toString()), gbc);
		JPanel codePanel = new JPanel();
		codePanel.setBackground(background);
		gbc.fill = GridBagConstraints.VERTICAL;
		gbc.gridx = 1;
		gbc.gridy = 0;
		entry.add(codePanel, gbc);
		
		JPanel responsePanel = new JPanel();
		responsePanel.setBackground(background);
		gbc.fill = GridBagConstraints.VERTICAL;
		gbc.gridx = 1;
		gbc.gridy = 1;
		entry.add(responsePanel, gbc);
        
        for(int i = 0; i < code.length(); ++i){
            GradientJButton button = new GradientJButton(" ");
            button.setBackground(ComboBoxRenderer.get().charToColor(code.charAt(i)));
            button.setEnabled(false);
            button.setSize(20, 20);
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.gridx = i;
			gbc.gridy = 0;
            codePanel.add(button, gbc);
        }
		if(response.charAt(0) != '0'){
			for(int i = 0; i < response.length(); ++i){
				GradientJButton button = new GradientJButton(" ");
				button.setBackground(charToColorResponse(response.charAt(i)));
				button.setEnabled(false);
				button.setSize(20, 20);
				gbc.fill = GridBagConstraints.HORIZONTAL;
				gbc.gridx = i;
				gbc.gridy = 0;
				responsePanel.add(button, gbc);
			}
		}
		else{
			responsePanel.setBackground(background);
			JButton button = new JButton(" ");
			button.setOpaque(false);
			button.setContentAreaFilled(false);
			button.setBorderPainted(false);
			button.setEnabled(false);
			button.setSize(20, 20);
			gbc.fill = GridBagConstraints.HORIZONTAL;
			gbc.gridx = 0;
			gbc.gridy = 0;
			responsePanel.add(button, gbc);
		}
		
        return entry;
    }
	
    public void openAgainWindow(){
        PlayingField me = this;
        java.awt.EventQueue.invokeLater(() -> {
            AgainWindow againWindow = new AgainWindow(me);
            againWindow.setVisible(true);
        });
    }

    public void again(boolean again){
        if(again){
                netParticipant.restart();
        }
        else{
                netParticipant.stop(STOPTYPE.QUIT);
        }
    }
    
    /**
     * Adds a code and the response to the history view
     * @param code 
     * @param response
     */
    public void addToHistory(String code, String response){
        historyCount++;
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        history.add(getListEntryRendered(code, response, historyCount), gbc, 0);
        revalidate();
    }
	
	/**
     * Resets the history view and resets the selected colors
     */
	public void reset(){
		String[] array = colors.split("(?!^)");
        for(int i = 0; i < codeLength; ++i){
			comboBoxes[i].setBackground(ComboBoxRenderer.get().charToColor(colors.charAt(0)));
		}
		historyCount = 0;
		history.removeAll();
		history.revalidate();
        revalidate();
		if(isServer){
			randomButton.setEnabled(true);
			addButton.setEnabled(true);
		}
	}
    
	/**
	* Activates the 'Send' button
	*/
    public void activateSendButton(){
    	if(!isServer){
    		addButton.setEnabled(true);
    	}
    }
	
	/**
	* Updates the status text
	* @param text The text to display
	*/
	public void setStatusText(String text){
		statusLabel.setText(text);
	}
    
    /**
     * Initializes the history
     */
    private void initHistory(){
        historyPane.setLayout(new BorderLayout());
        historyPane.setPreferredSize(new java.awt.Dimension(400, 400));

        history = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 1;
        gbc.weighty = 1;
        //history.add(new JPanel(), gbc);
        historyPane.add(new JScrollPane(history));
        revalidate();
    }
    
    /**
     * Initializes the activePane with JComboBoxes
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	private void initActivePane(){
        String[] array = colors.split("(?!^)");
        for(int i = 0; i < codeLength; ++i){
            GradientJComboBox comboBox = new GradientJComboBox(array);
            comboBox.setBackground(ComboBoxRenderer.get().charToColor(colors.charAt(0)));
            comboBox.setSize(60, 80);
            comboBox.setLocation(i*50, 0);
            comboBox.setRenderer(ComboBoxRenderer.get());
            comboBox.addItemListener(new ItemChangeListener(this));
            activePane.add(comboBox);
            comboBoxes[i] = comboBox;
        }
        revalidate();
    }
    
    
    /**
     * Called if one of the combo boxes was changed
     */
    public void codeChanged(){
        addButton.setBackground(Color.WHITE);
        String code = "";
        for(int i = 0; i < codeLength; ++i){
            code += comboBoxes[i].getSelectedItem();
        }
        if(code.equals(lastCode)){
            addButton.setBackground(Color.RED);
        }
    }

	private javax.swing.JTextField statusLabel;
    private javax.swing.JPanel activePane;
	private javax.swing.JButton randomButton;
    private javax.swing.JButton addButton;
    private javax.swing.JPanel historyPane;
}
