/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customUI;


import java.awt.Color;
import java.awt.Component;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

/**
 *
 * @author laurin.agostini
 */
@SuppressWarnings("serial")
public class GradientJButton extends JButton{
	public GradientJButton() {
		super();
        setOpaque(true);
    }
	
    public GradientJButton(String text) {
		super(text);
        setOpaque(true);
    }
    
	@Override
	protected void paintComponent(Graphics grphcs) {
		super.paintComponent(grphcs);
		Graphics2D g2d = (Graphics2D) grphcs;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		GradientPaint gp = new GradientPaint(0, 0,
				getBackground().brighter().brighter(), 0, getHeight(),
				getBackground().darker().darker());
		g2d.setPaint(gp);
		g2d.fillRect(0, 0, getWidth(), getHeight()); 

	}
}