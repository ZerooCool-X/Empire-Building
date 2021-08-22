package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

import units.Status;

public class startingPanel extends JPanelWithBackground {
	int width, height;
	Frame parent;

	public startingPanel(int w, int t, Frame f) {
		super(CityView.stretch("start1.jpg", w, t));
		width = w;
		height = t;
		parent = f;
		this.setVisible(true);
		setLayout(null);
		this.setSize(width, height);
		JLabel title = new JLabel("Empire Building", JLabel.CENTER);
		title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
		title.setBounds(width / 3, height / 3, 400, 50);
		title.setForeground(Color.decode("#654321"));
		JButton start = new BrownButton("START", 20);
		start.setBounds(width / 3, height / 3 + 100, 400, 50);
		JButton exit = new BrownButton("EXIT", 20);
		exit.setBounds(width - 300, height - 200, 100, 50);
		this.add(start);
		this.add(title);
		this.add(exit);
		start.addActionListener(parent);
		exit.addActionListener(parent);
	}

}
