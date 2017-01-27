
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
public class Highscore{
    private String[] data = new String[100];
    private int size = 0;
    private final String[] headers = {"SCORE", "NAME", "ATTEMPTS", "CODELENGTH", "COLORCOUNT", "DATE", "TIME"};
    
    private static Highscore highscore = null;
    
    private Highscore(){
        //read();
    }
    
    public static Highscore get(){
        if(highscore == null){
            highscore = new Highscore();
        }
        return highscore;
    }
    
    /**
     * draw method to create the table
     */
    
    public void draw () {
        read();
        JFrame frame = new JFrame();

        DefaultTableModel model = new DefaultTableModel(size, headers.length);
        JTable table = new JTable(model);

        for(int i = 0; i < headers.length; ++i){
            table.getColumnModel().getColumn(i).setHeaderValue(headers[i]);
        }

        for (int i = 0; i < size; i++) {
            String [] item = data[i].split("\\$", -1);
            for(int u = 0; u < headers.length; ++u){
                model.setValueAt(item[u], i, u);
            }
        }

        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);
        frame.setSize(500, 500);
        frame.setVisible(true);
    }

    public void read () {
        try (BufferedReader br = new BufferedReader(new FileReader("highscore.txt"))) {
            String line;
            int size = 0;
            while ((line = br.readLine()) != null) {
                data[size++] = line;
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void add (String name, int codeLength, int colorCount, int attempts) {
        read();
        int score = (codeLength * colorCount * 1000 / attempts);
        String day = new SimpleDateFormat("dd.MM.yyyy").format(new Date());
        String time = new SimpleDateFormat("HH:mm").format(new Date());
        Boolean done = false;

        try{
            PrintWriter writer = new PrintWriter("highscore.txt", "UTF-8");
            
            for (int i = 0; i < size; i++) {
                int d = Integer.parseInt(data[i].split("\\$", -1)[0]);
                if (score > d && done == false) {
                    writer.println(score + "$" + name + "$" + attempts + "$" + codeLength + "$" + colorCount + "$" + day + "$" + time);
                    done = true;
                }
                writer.println(data[i]);
            }

            if (done == false) {
                writer.println(score + "$" + name + "$" + attempts + "$" + codeLength + "$" + colorCount + "$" + day + "$" + time);
            }
                
            writer.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void show () {
        //read();
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