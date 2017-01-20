package main;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



/**
 *
 * @author laurin.agostini
 */
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.synth.SynthLookAndFeel;
import network.client.Client;
import network.server.Server;

public class MainWindow extends javax.swing.JFrame {
    
    private MainWindow me = this;
    private String allColors = "0123456789abcdef";
    private String originalCode = "";
    /**
     * Creates new form MyJFrame
     */
    public MainWindow() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    //@SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        tabbedPane = new javax.swing.JTabbedPane();
        joinPanel = new javax.swing.JPanel();
        nameLabel = new javax.swing.JLabel();
        nameTextField = new javax.swing.JTextField();
        ipAddressTextField = new javax.swing.JTextField();
        joinPortTextField = new javax.swing.JTextField();
        colonLabel = new javax.swing.JLabel();
        ipAddressLabel = new javax.swing.JLabel();
        joinPortLabel = new javax.swing.JLabel();
        autoModeCheckbox = new javax.swing.JCheckBox();
        joinButton = new javax.swing.JButton();
        hostPanel = new javax.swing.JPanel();
        codeLengthLabel = new javax.swing.JLabel();
        codeLengthSpinner = new javax.swing.JSpinner();
        colorCountLabel = new javax.swing.JLabel();
        colorCountSpinner = new javax.swing.JSpinner();
        codeLengthRandomCheckbox = new javax.swing.JCheckBox();
        colorCountRandomCheckbox = new javax.swing.JCheckBox();
        hostPortTextField = new javax.swing.JTextField();
        hostPortLabel = new javax.swing.JLabel();
        tryCountLabel = new javax.swing.JLabel();
        attemptsCountSpinner = new javax.swing.JSpinner();
        seperator = new javax.swing.JSeparator();
        hostButton = new javax.swing.JToggleButton();
        menuBar = new javax.swing.JMenuBar();
        exitMenuButton = new javax.swing.JMenu();
        helpMenuButton = new javax.swing.JMenu();
        highscoreMenuButton = new javax.swing.JMenu();
		gameCountLabel = new javax.swing.JLabel();
		gameCountSpinner = new javax.swing.JSpinner();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Mastermind");

        tabbedPane.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        tabbedPane.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N

        nameLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        nameLabel.setText("Name");

        nameTextField.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        nameTextField.setText("Client04");

        ipAddressTextField.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N

        joinPortTextField.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        joinPortTextField.setText("50004");

        colonLabel.setText(":");

        ipAddressLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        ipAddressLabel.setText("IP-Address");

        joinPortLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        joinPortLabel.setText("Port");

        autoModeCheckbox.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        autoModeCheckbox.setText("Automatic Mode");

        joinButton.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        joinButton.setText("Join");
        joinButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                joinButtonActionPerformed(evt);
            }
        });


        javax.swing.GroupLayout joinPanelLayout = new javax.swing.GroupLayout(joinPanel);
        joinPanel.setLayout(joinPanelLayout);
        joinPanelLayout.setHorizontalGroup(
            joinPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(joinPanelLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(joinPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(joinPanelLayout.createSequentialGroup()
                        .addGroup(joinPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(joinButton)
                            .addComponent(autoModeCheckbox)
							.addComponent(gameCountLabel)
							.addComponent(gameCountSpinner)
                            .addComponent(nameLabel)
                            .addGroup(joinPanelLayout.createSequentialGroup()
                                .addGroup(joinPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(joinPanelLayout.createSequentialGroup()
                                        .addComponent(ipAddressTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(colonLabel))
                                    .addComponent(ipAddressLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(joinPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(joinPortLabel)
                                    .addComponent(joinPortTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(nameTextField))
                        .addContainerGap(98, Short.MAX_VALUE))
                    .addGroup(joinPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        joinPanelLayout.setVerticalGroup(
            joinPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(joinPanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(nameLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(joinPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ipAddressLabel)
                    .addComponent(joinPortLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(joinPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ipAddressTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(joinPortTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(colonLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(autoModeCheckbox)
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
				.addComponent(gameCountLabel)
				.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
				.addComponent(gameCountSpinner)
                .addGap(26, 26, 26)
                .addComponent(joinButton)
                .addContainerGap(126, Short.MAX_VALUE))
        );

        tabbedPane.addTab("Join", joinPanel);

        codeLengthLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        codeLengthLabel.setText("Code length");

        codeLengthSpinner.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        codeLengthSpinner.setModel(new javax.swing.SpinnerNumberModel(4, 2, 15, 1));
        codeLengthSpinner.setValue(4);

        colorCountLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        colorCountLabel.setText("Colors");

        colorCountSpinner.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        colorCountSpinner.setModel(new javax.swing.SpinnerNumberModel(4, 2, 15, 1));
		
		gameCountLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        gameCountLabel.setText("Number of games (0 = unlimited)");

        gameCountSpinner.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        gameCountSpinner.setModel(new javax.swing.SpinnerNumberModel(0, 0, 100, 1));
        gameCountSpinner.setValue(0);

        codeLengthRandomCheckbox.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        codeLengthRandomCheckbox.setText("Random");
        codeLengthRandomCheckbox.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                codeLengthRandomCheckboxStateChanged(evt);
            }
        });

        colorCountRandomCheckbox.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        colorCountRandomCheckbox.setText("Random");
        colorCountRandomCheckbox.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                colorCountRandomCheckboxStateChanged(evt);
            }
        });

        hostPortTextField.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        hostPortTextField.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        hostPortTextField.setText("50004");
        hostPortTextField.setMinimumSize(new java.awt.Dimension(41, 22));
        hostPortTextField.setPreferredSize(new java.awt.Dimension(41, 22));

        hostPortLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        hostPortLabel.setText("Port");

        tryCountLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        tryCountLabel.setText("Attempts (0 = unlimited)");

        attemptsCountSpinner.setFont(new java.awt.Font("Segoe UI", 0, 11)); // NOI18N
        attemptsCountSpinner.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));
        attemptsCountSpinner.setPreferredSize(new java.awt.Dimension(41, 22));

        hostButton.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        hostButton.setText("Host");
        hostButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                hostButtonMouseClicked(evt);
            }
        });


        javax.swing.GroupLayout hostPanelLayout = new javax.swing.GroupLayout(hostPanel);
        hostPanel.setLayout(hostPanelLayout);
        hostPanelLayout.setHorizontalGroup(
            hostPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(seperator)
            .addGroup(hostPanelLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(hostPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(hostButton)
                    .addComponent(hostPortLabel)
                    .addComponent(tryCountLabel)
                    .addComponent(colorCountLabel)
                    .addGroup(hostPanelLayout.createSequentialGroup()
                        .addGroup(hostPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(colorCountSpinner)
                            .addComponent(codeLengthLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(codeLengthSpinner, javax.swing.GroupLayout.Alignment.LEADING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(hostPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(colorCountRandomCheckbox)
                            .addComponent(codeLengthRandomCheckbox)))
                    .addComponent(attemptsCountSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(hostPortTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(130, Short.MAX_VALUE))
        );
        hostPanelLayout.setVerticalGroup(
            hostPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(hostPanelLayout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(codeLengthLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(hostPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(codeLengthSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(codeLengthRandomCheckbox))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(colorCountLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(hostPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(colorCountSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(colorCountRandomCheckbox))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tryCountLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(attemptsCountSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(seperator, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(hostPortLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(hostPortTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addComponent(hostButton)
                .addContainerGap(80, Short.MAX_VALUE))
        );

        colorCountSpinner.getAccessibleContext().setAccessibleName("");
        colorCountSpinner.getAccessibleContext().setAccessibleDescription("");

        tabbedPane.addTab("Host", hostPanel);

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 401, Short.MAX_VALUE)
            .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(tabbedPane, javax.swing.GroupLayout.Alignment.TRAILING))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 422, Short.MAX_VALUE)
            .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(tabbedPane))
        );

        menuBar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        exitMenuButton.setText("Exit");
        exitMenuButton.setToolTipText("Exit the game");
        exitMenuButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenuExitClicked(evt);
            }
        });
        menuBar.add(exitMenuButton);

        helpMenuButton.setText("Help");
        helpMenuButton.setToolTipText("Show a help page");
        helpMenuButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenuHelpClicked(evt);
            }
        });
        menuBar.add(helpMenuButton);

        highscoreMenuButton.setText("Highscore");
        highscoreMenuButton.setToolTipText("Show the local highscore");
        highscoreMenuButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                highscoreMenuButtonjMenuHelpClicked(evt);
            }
        });
        menuBar.add(highscoreMenuButton);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuExitClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuExitClicked
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING)); 
    }//GEN-LAST:event_jMenuExitClicked

    private void jMenuHelpClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenuHelpClicked
        java.awt.EventQueue.invokeLater(() -> {
            new help.HelpWindow().setVisible(true);
        });
    }//GEN-LAST:event_jMenuHelpClicked

    private void codeLengthRandomCheckboxStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_codeLengthRandomCheckboxStateChanged
        codeLengthSpinner.setEnabled(!codeLengthRandomCheckbox.isSelected());
    }//GEN-LAST:event_codeLengthRandomCheckboxStateChanged

    private void colorCountRandomCheckboxStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_colorCountRandomCheckboxStateChanged
        colorCountSpinner.setEnabled(!colorCountRandomCheckbox.isSelected());
    }//GEN-LAST:event_colorCountRandomCheckboxStateChanged

    private void hostButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hostButtonMouseClicked
        Server server = new Server(Integer.parseInt(hostPortTextField.getText())
                                    , (int)attemptsCountSpinner.getValue()
                                    , (int)colorCountSpinner.getValue()
                                    , (int)codeLengthSpinner.getValue());
        //start server
        server.start();
    }//GEN-LAST:event_hostButtonMouseClicked


    private void joinButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_joinButtonActionPerformed
        Client client = new Client(ipAddressTextField.getText(), Integer.parseInt(joinPortTextField.getText()), nameTextField.getText());
        client.start();
    }//GEN-LAST:event_joinButtonActionPerformed

    private void highscoreMenuButtonjMenuHelpClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_highscoreMenuButtonjMenuHelpClicked
        java.awt.EventQueue.invokeLater(() -> {
            new highscore.Highscore().setVisible(true);
        });
    }//GEN-LAST:event_highscoreMenuButtonjMenuHelpClicked
    
    
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        //initLookAndFeel();
        
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        try {
            // Set cross-platform Java L&F (also called "Metal")
        UIManager.setLookAndFeel(
            UIManager.getCrossPlatformLookAndFeelClassName());
        } 
        catch (UnsupportedLookAndFeelException e) {
           // handle exception
        }
        catch (ClassNotFoundException e) {
           // handle exception
        }
        catch (InstantiationException e) {
           // handle exception
        }
        catch (IllegalAccessException e) {
           // handle exception
        }
        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new MainWindow().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSpinner attemptsCountSpinner;
    private javax.swing.JCheckBox autoModeCheckbox;
    private javax.swing.JLabel codeLengthLabel;
    private javax.swing.JCheckBox codeLengthRandomCheckbox;
    private javax.swing.JSpinner codeLengthSpinner;
    private javax.swing.JLabel colonLabel;
    private javax.swing.JLabel colorCountLabel;
    private javax.swing.JCheckBox colorCountRandomCheckbox;
    private javax.swing.JSpinner colorCountSpinner;
    private javax.swing.JMenu exitMenuButton;
    private javax.swing.JMenu helpMenuButton;
    private javax.swing.JMenu highscoreMenuButton;
    private javax.swing.JToggleButton hostButton;
    private javax.swing.JPanel hostPanel;
    private javax.swing.JLabel hostPortLabel;
    private javax.swing.JTextField hostPortTextField;
    private javax.swing.JLabel ipAddressLabel;
    private javax.swing.JTextField ipAddressTextField;
    private javax.swing.JButton joinButton;
    private javax.swing.JPanel joinPanel;
    private javax.swing.JLabel joinPortLabel;
    private javax.swing.JTextField joinPortTextField;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JTextField nameTextField;
    private javax.swing.JSeparator seperator;
    private javax.swing.JTabbedPane tabbedPane;
    private javax.swing.JLabel tryCountLabel;
	private javax.swing.JLabel gameCountLabel;
	private javax.swing.JSpinner gameCountSpinner;
    // End of variables declaration//GEN-END:variables
}
