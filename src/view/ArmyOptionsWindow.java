package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import engine.City;
import engine.Game;
import engine.Player;
import exceptions.FriendlyCityException;
import exceptions.TargetNotReachedException;
import units.Archer;
import units.Army;
import units.Cavalry;
import units.Infantry;
import units.Status;
import units.Unit;

public class ArmyOptionsWindow extends JFrame implements ActionListener {

	JButton attack, laySiege, cancel, cairo, rome, sparta;
	JPanel unitPanel, rest;
	JPanel move, AS;
	JLabel moveLabel;
	Army a;
	Game g;
	Player p;
	Frame frame;
	ArmyButton armyButton;
	boolean open = false;

	public ArmyOptionsWindow(Army a, Frame frame, ArmyButton armyButton) {
		super("Army Options");
		if (open) {
			return;
		}
		setAlwaysOnTop(true);
		setResizable(false);
		open = true;
		this.a = a;
		g = frame.g;
		this.frame = frame;
		this.armyButton = armyButton;
		// armyButton.setEnabled(false);
		JPanelWithBackground back = new JPanelWithBackground(CityView.stretch("back1.jpg", 500, 500));
		this.add(back);
		back.setLayout(new BorderLayout());
		p = g.getPlayer();

		unitPanel = new JPanel();
		unitPanel.setOpaque(false);
		attack = new BrownButton("ATTACK!", 18);
		laySiege = new BrownButton("Lay Siege!", 18);
		move = new JPanel();
		cancel = new BrownButton("Cancel", 18);
		move.setLayout(new GridLayout(1, 4));
		moveLabel = new JLabel("Target ->", JLabel.CENTER);
		moveLabel.setForeground(Color.decode("#A0522D"));
		moveLabel.setBackground(Color.decode("#FFEBCD"));
		moveLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
		moveLabel.setOpaque(true);
		cairo = new BrownButton("Cairo", 18);
		rome = new BrownButton("Rome", 18);
		sparta = new BrownButton("Sparta", 18);
		cairo.setBorderPainted(true);
		sparta.setBorderPainted(true);
		rome.setBorderPainted(true);
		attack.setBorderPainted(true);
		laySiege.setBorderPainted(true);
		cancel.setBorderPainted(true);
		cairo.setBorder(new LineBorder(Color.decode("#FFEBCD")));
		sparta.setBorder(new LineBorder(Color.decode("#FFEBCD")));
		rome.setBorder(new LineBorder(Color.decode("#FFEBCD")));
		attack.setBorder(new LineBorder(Color.decode("#FFEBCD")));
		laySiege.setBorder(new LineBorder(Color.decode("#FFEBCD")));
		cancel.setBorder(new LineBorder(Color.decode("#FFEBCD")));
		rest = new JPanel();

		back.add(rest, BorderLayout.CENTER);
		AS = new JPanel();
		AS.setLayout(new GridLayout(1, 2));
		AS.add(attack);
		AS.add(laySiege);

		move.add(moveLabel);
		move.add(cairo);
		move.add(rome);
		move.add(sparta);

//		this.info = info;
		setSize(500, 500);

		unitPanel.setLayout(new GridLayout(3, 4));
		rest.setLayout(new GridLayout(3, 1));
		back.add(unitPanel, BorderLayout.NORTH);
		rest.add(AS);
		rest.add(move);
		rest.add(cancel);
		revalidate();
		repaint();
		attack.addActionListener(this);
		laySiege.addActionListener(this);
		laySiege.addActionListener(frame);
		cairo.addActionListener(this);
		rome.addActionListener(this);
		sparta.addActionListener(this);
		cairo.addActionListener(this);
		rome.addActionListener(armyButton);
		sparta.addActionListener(armyButton);
		cancel.addActionListener(this);
		setVisible(true);
		deactivateCurrentCity();
		deactivateLaySiege(a.getCurrentStatus(), a.getCurrentLocation());
		deactivateAttack();
		for (int i = 0; i < a.getUnits().size(); i++) {
			Unit u = a.getUnits().get(i);
			JLabel unitInfo = new JLabel();
			// unitInfo.setLayout(new BorderLayout());
//			unitInfo.setForeground(Color.decode("#654321"));
			unitInfo.setSize(100, 100);
			String infoToAdd;
			if (u instanceof Archer) {
				// example of multible lines in HTML //"<html>This is my first line.<br/>This is
				// second line</html>"
				infoToAdd = "<html>Archer" + "<br/>Level:" + u.getLevel() + "<br/>number of soldiers: "
						+ u.getCurrentSoldierCount() + "<br/>Max number of soldiers: " + u.getMaxSoldierCount()
						+ "</html>";
			} else if (u instanceof Cavalry) {
				infoToAdd = "<html>Cavalry" + "<br/>Level:" + u.getLevel() + "<br/>number of soldiers: "
						+ u.getCurrentSoldierCount() + "<br/>Max number of soldiers: " + u.getMaxSoldierCount()
						+ "</html>";
			} else {
				infoToAdd = "<html>Infantry" + "<br/>Level:" + u.getLevel() + "<br/>number of soldiers: "
						+ u.getCurrentSoldierCount() + "<br/>Max number of soldiers: " + u.getMaxSoldierCount()
						+ "</html>";
			}
			unitInfo.setText(infoToAdd);
			unitPanel.add(unitInfo);

		}

	}

	private void deactivateCurrentCity() {
		String currentLocation = a.getCurrentLocation();
		if (a.getCurrentStatus() == Status.MARCHING) {
			cairo.setEnabled(false);
			sparta.setEnabled(false);
			rome.setEnabled(false);
		}
		if (currentLocation.equals("Cairo")) {
			cairo.setEnabled(false);
		}
		if (currentLocation.equals("Sparta")) {
			sparta.setEnabled(false);
		}
		if (currentLocation.equals("Rome")) {
			rome.setEnabled(false);
		}
		revalidate();
		repaint();
	}

	private void deactivateLaySiege(Status s, String city) {
		if (!s.equals(Status.IDLE)) {
			laySiege.setEnabled(false);
		} else {
			boolean flag = false;
			for (int i = 0; i < p.getControlledCities().size(); i++) {
				City c = p.getControlledCities().get(i);
				if (c.getName().equals(city)) {
					flag = true;
					break;
				}
			}

			if (flag) {
				laySiege.setEnabled(false);
			}
		}
	}

	public void deactivateAttack() {
		if (a.getCurrentLocation().equals("On Road")) {
			attack.setEnabled(false);
			return;
		}
		for (City c : p.getControlledCities()) {
			if (a.getCurrentLocation().equals(c.getName())) {
				attack.setEnabled(false);
				return;
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		open = false;
		if (action.equals("ATTACK!")) {
			for (City c : g.getAvailableCities()) {
				if (c.getName().equals(a.getCurrentLocation())) {
					try {
//						frame.removeAll();
						frame.getClip().stop();
						frame.mapview.setVisible(false);
						frame.add(new BattleView(a, c.getDefendingArmy(), g, frame));
						this.dispose();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					if (a.getCurrentStatus() == Status.BESIEGING) {
						a.setCurrentStatus(Status.IDLE);
						c.setUnderSiege(false);
						c.setTurnsUnderSiege(0);
					}
				}
			}
		} else if (action.equals("Lay Siege!")) {
			// if the army at an opponent city, call laySiege
			// if at a friendly city or not at any city, send an error message
			City c = new City("");
			for (int i = 0; i < g.getAvailableCities().size(); i++) {
				c = g.getAvailableCities().get(i);
				if (a.getCurrentLocation().equals(c.getName())) {
					break;
				}
			}
			if (c.isUnderSiege()) {
				JOptionPane alreadyUnder = new JOptionPane();
				alreadyUnder.showMessageDialog(null, "The city is already under Siege", "City Under Siege",
						JOptionPane.PLAIN_MESSAGE);
				return;
			}
			try {
				p.laySiege(a, c);
				armyButton.createArmyInfo();
				this.dispose();
			} catch (TargetNotReachedException e1) {
				(new JOptionPane()).showMessageDialog(null, "You haven't reached your target", "Target Not Reached",
						JOptionPane.PLAIN_MESSAGE);
			} catch (FriendlyCityException e1) {
				(new JOptionPane()).showMessageDialog(null, "This city is friendly", "Friendly City",
						JOptionPane.PLAIN_MESSAGE);
			}
		} else if (action.equals("Cancel")) {
			this.dispose();
		} else if (action.equals("Cairo")) {
			g.targetCity(a, "Cairo");
			this.dispose();
			armyButton.createArmyInfo();
		} else if (action.equals("Rome")) {
			g.targetCity(a, "Rome");
			armyButton.createArmyInfo();
			this.dispose();

		} else if (action.equals("Sparta")) {
			try {
				File sound = new File("THIS IS SPARTA.wav");
				AudioInputStream audioStream = AudioSystem.getAudioInputStream(sound);
				Clip clip = AudioSystem.getClip();
				clip.open(audioStream);
				clip.start();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			g.targetCity(a, "Sparta");
			armyButton.createArmyInfo();
			this.dispose();

		}

	}

}
