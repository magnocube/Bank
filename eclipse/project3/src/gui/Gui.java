package gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class Gui {
	public JPanel newPanel = new JPanel();

	public JPanel panel = new JPanel();
    public JFrame frame = new JFrame("bankolicious");
    public JLabel label;
    JButton a = new JButton("test");
	public Gui()
	{
	    
		JFrame frame = new JFrame("example");
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setVisible(true);
	    frame.setSize(100, 100);
	    label= new JLabel("input");
		frame.add(label);	    
		
		frame.add(a);
	}
	   
	public void give(String input) {
	a.setText(input);	
	 a.doClick(300);
	}
	

	

}
