/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package playingfield;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JComboBox;


/**
 *
 * @author laurin.agostini
 */
class ItemChangeListener implements ItemListener{
    PlayingField playingField;
    ItemChangeListener(PlayingField playingField){
        this.playingField = playingField;
    }
    @SuppressWarnings("rawtypes")
	@Override
    public void itemStateChanged(ItemEvent event) {
       if (event.getStateChange() == ItemEvent.SELECTED) {
          Object item = event.getItem();
          Object itemSelectable = event.getItemSelectable();
          ((JComboBox)itemSelectable).setBackground(ComboBoxRenderer.charToColor(item.toString().charAt(0)));
          playingField.codeChanged();
          // do something with object
       }
    }       
}
