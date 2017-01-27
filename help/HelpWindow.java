/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package help;

import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.IOException;

import java.nio.charset.Charset;

/**
* Displays help for user
*/
public class HelpWindow extends javax.swing.JFrame {

    /**
     * Creates new HelpWindow and initializes components
     */
    public HelpWindow() {
        initComponents();
        readFile();
    }
    
	/**
	* Reads file and updates text pane with the content
	*/
    private void readFile(){
		Charset charset = Charset.forName("UTF-8");
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("help.html"), charset))) {
            String result = "";
            String line;
            while((line = br.readLine()) != null){
				//System.out.println(line);
                result += line;
            }
			jTextPane1.setContentType("text/html");
            jTextPane1.setText(result);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Auto generated code
     */
    @SuppressWarnings("unchecked")
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Help - Mastermind");

        jTextPane1.setEditable(false);
        jTextPane1.setContentType("text/html");
        jTextPane1.setFont(new java.awt.Font("Segoe UI", 0, 11));
        jTextPane1.setText( "Loading..." );
        jScrollPane1.setViewportView(jTextPane1);

        jMenu1.setText("Exit");
        jMenu1.setToolTipText("Exit the help page");
        jMenu1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenuExitClicked(evt);
            }
        });
        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 433, Short.MAX_VALUE)
        );

        pack();
    }

	/**
	* Closes the HelpWindow
	*/
    private void jMenuExitClicked(java.awt.event.MouseEvent evt) {
       this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
   }


    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextPane jTextPane1;
}
