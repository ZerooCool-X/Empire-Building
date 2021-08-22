package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class namePanel extends JPanelWithBackground {
	JTextField name;

	public namePanel(int width, int height, Frame parent) {
		super(CityView.stretch("start1.jpg", width, height));
		setLayout(null);
		this.setSize(width, height);
		JLabel title = new JLabel("Enter Player Name");
		JButton play = new BrownButton("PLAY", 20);
		play.addActionListener(parent);
		name = new JTextField(50);
		name.setBounds(width / 3, height / 3 + 100, 400, 50);
		title.setBounds(width / 3, height / 3, 400, 50);
		play.setBounds(width / 3, height / 3 + 200, 400, 50);
		title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
		title.setForeground(Color.decode("#654321"));
		name.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
		name.setForeground(Color.decode("#A0522D"));
		name.setBackground(Color.decode("#FFEBCD"));
		this.add(title);
		this.add(name);
		this.add(play);

	}

	public String getPlayerName() {
		return name.getText();
	}

}
