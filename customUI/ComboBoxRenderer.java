/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customUI;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 *
 * @author laurin.agostini
 */
@SuppressWarnings("serial")
public class ComboBoxRenderer extends JLabel implements ListCellRenderer<Object> {

    private ComboBoxRenderer() {
        setOpaque(true);
		loadColors();
    }
	private static ComboBoxRenderer me = null;
	
	public static ComboBoxRenderer get(){
		if(ComboBoxRenderer.me == null){
			ComboBoxRenderer.me = new ComboBoxRenderer();
		}
		return ComboBoxRenderer.me;
	}
    
    private Color[] colorTable ={hexToRGB("#00a300"), hexToRGB("#ee1111"), hexToRGB("#ffc40d"),
                                hexToRGB("#2d89ef"), hexToRGB("#603cba"), hexToRGB("#1e7145"),
                                hexToRGB("#da532c"), hexToRGB("#2b5797"), hexToRGB("#9f00a7"), 
                                hexToRGB("#00aba9"), hexToRGB("#e3a21a"), hexToRGB("#eff4ff"),
                                hexToRGB("#99b433"), hexToRGB("#795548"), hexToRGB("#7e3878")};
    
	
	private Color hexToRGB(String hex){
            return new Color(
                Integer.valueOf( hex.substring( 1, 3 ), 16 ),
                Integer.valueOf( hex.substring( 3, 5 ), 16 ),
                Integer.valueOf( hex.substring( 5, 7 ), 16 ) 
            );
	}
	
	private void loadColors(){
		System.out.println("Load colors");
		try (BufferedReader br = new BufferedReader(new FileReader("colors.cfg"))) {
            String line;
            int size = 0;
            while ((line = br.readLine()) != null && size < 15) {
                colorTable[size++] = hexToRGB(line);
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
	}
	
    private int charToIndex(char input){
        if(input < 'a'){
            return (int)(input - '1');
        }
        return (int)(input - 'a') + 9;
    }
    
    public Color charToColor(char input){
		System.out.println(input + " -> " + colorTable[charToIndex(input)]);
        return colorTable[charToIndex(input)];
    }
       
        
    
    @SuppressWarnings("rawtypes")
	public Component getListCellRendererComponent(
                                       JList list,
                                       Object value,
                                       int index,
                                       boolean isSelected,
                                       boolean cellHasFocus) {
        setText((String)value);
        
        setBackground(charToColor(((String)value).charAt(0)));

        return this;
    }
}
