/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package playingfield;


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
class ComboBoxRenderer extends JLabel implements ListCellRenderer<Object> {
    ComboBoxRenderer() {
        setOpaque(true);
    }
    
/*
     * This method finds the image and text corresponding
     * to the selected value and returns the label, set up
     * to display the text and image.
     */
    
    static Color[] colorTable = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW};
    
    static private int charToIndex(char input){
        if(input < 'a'){
            return (int)(input - '1');
        }
        return (int)(input - 'a') + 9;
    }
    
    static public Color charToColor(char input){
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
