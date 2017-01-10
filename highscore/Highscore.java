
package highscore;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.text.SimpleDateFormat;
import javax.swing.table.*;

/**
 * 
 * @author marius.claret
 *
 */
public class Highscore extends JFrame{
    private String[] data = new String[100];
    private int size;
    
    public Highscore(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        read();

        DefaultTableModel model = new DefaultTableModel(size, 6);
        JTable table = new JTable(model);

        table.getColumnModel().getColumn(0).setHeaderValue("PUNKTE");
        table.getColumnModel().getColumn(1).setHeaderValue("NAME");
        table.getColumnModel().getColumn(2).setHeaderValue("LAENGE");
        table.getColumnModel().getColumn(3).setHeaderValue("VERSUCHE");
        table.getColumnModel().getColumn(4).setHeaderValue("DATUM");
        table.getColumnModel().getColumn(5).setHeaderValue("UHRZEIT");

        for (int i = 0; i < size; i++) {
            String [] item = data[i].split("\\$", -1);
            model.setValueAt(item[0], i, 0);
            model.setValueAt(item[1], i, 1);
            model.setValueAt(item[2], i, 2);
            model.setValueAt(item[3], i, 3);
            model.setValueAt(item[4], i, 4);
            model.setValueAt(item[5], i, 5);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
        setSize(500, 500);
    }
    
    /**
     * draw method to create the table
     */
    /*
    public void draw () {
        read();
        JFrame frame = new JFrame();
        frame.

        DefaultTableModel model = new DefaultTableModel(size, 6);
        JTable table = new JTable(model);

        table.getColumnModel().getColumn(0).setHeaderValue("PUNKTE");
        table.getColumnModel().getColumn(1).setHeaderValue("NAME");
        table.getColumnModel().getColumn(2).setHeaderValue("LAENGE");
        table.getColumnModel().getColumn(3).setHeaderValue("VERSUCHE");
        table.getColumnModel().getColumn(4).setHeaderValue("DATUM");
        table.getColumnModel().getColumn(5).setHeaderValue("UHRZEIT");

        for (int i = 0; i < size; i++) {
            String [] item = data[i].split("\\$", -1);
            model.setValueAt(item[0], i, 0);
            model.setValueAt(item[1], i, 1);
            model.setValueAt(item[2], i, 2);
            model.setValueAt(item[3], i, 3);
            model.setValueAt(item[4], i, 4);
            model.setValueAt(item[5], i, 5);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setSize(500, 500);

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
        frame.setVisible(true);
    }
    */

    public void read () {
        try (BufferedReader br = new BufferedReader(new FileReader("highscore.txt"))) {
            String line;
            size = 0;
            while ((line = br.readLine()) != null) {
                data[size++] = line;
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void add (String name, int length, int trials) {
        read();
        int score = (length * 1000 / trials);
        String day = new SimpleDateFormat("dd.MM.yyyy").format(new Date());
        String time = new SimpleDateFormat("HH:mm").format(new Date());
        Boolean done = false;

        try{
            PrintWriter writer = new PrintWriter("highscore.txt", "UTF-8");
            
            for (int i = 0; i < size; i++) {
                int d = Integer.parseInt(data[i].split("\\$", -1)[0]);
                if (score > d && done == false) {
                    writer.println(score + "$" + name + "$" + length + "$" + trials + "$" + day + "$" + time);
                    done = true;
                }
                writer.println(data[i]);
            }

            if (done == false) {
                writer.println(score + "$" + name + "$" + length + "$" + trials + "$" + day + "$" + time);
            }
                
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void show () {
        read();
        try{
            Path filePath = new File("highscore.txt").toPath();
            Charset charset = Charset.defaultCharset();        
            List<String> stringList = Files.readAllLines(filePath, charset);
            String[] stringArray = stringList.toArray(new String[]{});

            for (int i = 0; i < stringArray.length; i++) {
                data = stringArray[i].split("\\$", -1);
            }
        } catch (IOException e) {
           System.out.println(e.getMessage());
        }
    }

}