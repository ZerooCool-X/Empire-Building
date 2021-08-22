package view;

import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class errorFrame extends JFrame{

	public errorFrame(String title,String msg) {
		setTitle(title);
		setVisible(true);
		JLabel errorl = new JLabel(msg);
		add(errorl);
		setSize(300, 100);
		setResizable(false);
		setLayout(new FlowLayout());
	}

}
