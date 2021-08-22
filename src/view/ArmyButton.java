package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;
import javax.swing.border.LineBorder;

import engine.City;
import engine.Game;
import units.Army;
import units.Status;

public class ArmyButton extends BrownButton implements MouseListener, ActionListener {

	JPanel armyInfo;
	JLabel status, currentLocation, targetCity, turnsToReach, besieging, turnsUnderSiege;
	Frame frame;
	Army a;
	Game g;
	JFrame window;

	public ArmyButton(String string, Army a, Game g, Frame frame) {
		super(string, 15);
		setBorderPainted(true);
		setBorder(new LineBorder(Color.decode("#FFEBCD"), 1));
		setText("Army");
		this.frame = frame;
		this.a = a;
		this.g = g;
		armyInfo = new JPanel();
		armyInfo.setLayout(new FlowLayout());
		armyInfo.setBackground(Color.decode("#FFEBCD"));
		armyInfo.setPreferredSize(new Dimension(200, 100));
		createArmyInfo();
		armyInfo.setVisible(false);
		this.setFocusable(false);
		window = new JFrame();
		// army is idle

		// army is marching

		// army is laying siege

	}

	public void createArmyInfo() {
		armyInfo.removeAll();
		status = new JLabel("Status : " + a.getCurrentStatus());
		status.setForeground(Color.decode("#A0522D"));
		currentLocation = new JLabel("Current Location : " + a.getCurrentLocation());
		currentLocation.setForeground(Color.decode("#A0522D"));
		armyInfo.add(status);
		armyInfo.add(currentLocation);
		if (a.getCurrentStatus().equals(Status.MARCHING)) {
			targetCity = new JLabel("Target City : " + a.getTarget());
			targetCity.setForeground(Color.decode("#A0522D"));
			turnsToReach = new JLabel("Turns To Reach Target : " + a.getDistancetoTarget());
			turnsToReach.setForeground(Color.decode("#A0522D"));
			armyInfo.add(targetCity);
			armyInfo.add(turnsToReach);
		}
		if (a.getCurrentStatus().equals(Status.BESIEGING)) {
			besieging = new JLabel("Currently Besieging : " + a.getCurrentLocation());
			besieging.setForeground(Color.decode("#A0522D"));
			City city = null;
			for (City c : g.getAvailableCities()) {
				if (c.getName().equals(a.getCurrentLocation())) {
					city = c;
					break;
				}
			}
			turnsUnderSiege = new JLabel("Turns Besieging :" + city.getTurnsUnderSiege());
			turnsUnderSiege.setForeground(Color.decode("#A0522D"));
			armyInfo.add(besieging);
			armyInfo.add(turnsUnderSiege);
		}
		armyInfo.validate();
		armyInfo.repaint();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		this.window.dispose();
		this.window = new ArmyOptionsWindow(a, frame, this);
		validate();
		repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		armyInfo.setVisible(true);
		setForeground(Color.decode("#A0522D"));
		setBackground(Color.decode("#FFEBCD"));
//		System.out.println(a.getDistancetoTarget());
		validate();
		repaint();

	}

	@Override
	public void mouseExited(MouseEvent e) {
		armyInfo.setVisible(false);
		setBackground(Color.decode("#A0522D"));
		setForeground(Color.decode("#FFEBCD"));
		validate();
		repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getActionCommand().equals("End Turn")) {
			createArmyInfo();
		}
	}

}