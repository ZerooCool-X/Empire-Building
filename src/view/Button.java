package view;

import javax.swing.JButton;

public class Button extends JButton{
	
	public Button(String s,Frame f, int width, int height) {
		super(s);
		addActionListener(f);
		setSize(width, height);
		
	}

}
