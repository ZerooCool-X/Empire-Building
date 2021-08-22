package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.border.LineBorder;

import engine.City;
import engine.Game;
import engine.Player;
import units.Archer;
import units.Army;
import units.Cavalry;
import units.Infantry;
import units.Status;
import view.Frame;

public class mapview extends JPanelWithBackground {

	JPanel armiesPanel;
	Frame frame;
//	Clip clip;

	public mapview(int width, int height, Frame parent) {
		super(CityView.stretch("map.png", width, height));
		Game g = parent.g;
		if (g.isGameOver()) {
			if (g.getCurrentTurnCount() > g.getMaxTurnCount()) {
				JOptionPane.showMessageDialog(null, "You lost", "Game Over", JOptionPane.PLAIN_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "You are Victorious", "Victoryyyyy!!!!!",
						JOptionPane.PLAIN_MESSAGE);
			}
			System.exit(0);
		}
		Player p = g.getPlayer();
		frame = parent;
		setLayout(new BorderLayout());
		JPanel head = new JPanel();
		JLabel playerName, food, gold, turn;
		head.setLayout(new GridLayout(1, 4));
		head.setOpaque(false);
		playerName = new JLabel(p.getName());
		gold = new JLabel("  Gold : " + p.getTreasury());
		food = new JLabel("  Food : " + p.getFood());
		turn = new JLabel(" Turn Count : " + g.getCurrentTurnCount() + "/50");

		playerName.setForeground(Color.decode("#FFEBCD"));
		gold.setForeground(Color.decode("#FFEBCD"));
		food.setForeground(Color.decode("#FFEBCD"));
		turn.setForeground(Color.decode("#FFEBCD"));

		playerName.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
		gold.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
		food.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
		turn.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
		head.add(playerName);
		head.add(gold);
		head.add(food);
		head.add(turn);

		this.add(head, BorderLayout.NORTH);
		JPanel rest = new JPanel();
		rest.setOpaque(false);
		this.add(rest, BorderLayout.CENTER);
		rest.setVisible(true);
		rest.setLayout(new BorderLayout());

		// adding the map
//		ImageIcon img = new ImageIcon("Untitled-1.png");
//		JLabel background = new JLabel("",img,JLabel.CENTER);
//		rest.add(background);
//		background.setLayout(null);
//		background.setBounds(0,0,900,800);
		JPanel right = new JPanel();
		right.setOpaque(false);
		right.setLayout(new BorderLayout());
		JPanel map = new JPanel();
		map.setOpaque(false);
		map.setLayout(null);
//		map.setBackground(Color.blue);

		armiesPanel = new JPanel();
		armiesPanel.setOpaque(false);
		// armiesPanel.setBackground(Color.red);
		rest.add(map, BorderLayout.CENTER);
		rest.add(right, BorderLayout.EAST);
		// rest.add(info);
		JLabel armiesLabel = new JLabel("AVAILABLE ARMIES");
		armiesLabel.setForeground(Color.decode("#FFEBCD"));
		right.add(armiesLabel, BorderLayout.NORTH);
		right.add(armiesPanel, BorderLayout.CENTER);
		armiesPanel.setLayout(new GridLayout(20, 2));

		MapCityButton cairo = new MapCityButton("Cairo", "");
		CityView.setIcon(cairo, "Cairo", "city1.png", 200, 100);
		cairo.setBounds(350, 200, 200, 100);
		cairo.addActionListener(parent);
		map.add(cairo);
		cairo.name.setBounds(420, 150, 100, 50);
		map.add(cairo.name);

		cairo.setContentAreaFilled(false);
		cairo.setBorderPainted(false);
		MapCityButton rome = new MapCityButton("Rome", "");
		CityView.setIcon(rome, "Rome", "city2.png", 200, 100);
		rome.setBounds(width / 2 + 180, height / 2 - 60, 200, 100);
		rome.addActionListener(parent);
		map.add(rome);
		rome.name.setBounds(width / 2 + 180, height / 2 - 60, 100, 50);

		map.add(rome.name);

		rome.setContentAreaFilled(false);
		rome.setBorderPainted(false);
		MapCityButton sparta = new MapCityButton("Sparta", "THIS IS SPARTA.wav");
		CityView.setIcon(sparta, "Sparta", "city3.png", 200, 100);
		sparta.setBounds(500, 500, 200, 100);
		sparta.addActionListener(parent);
		map.add(sparta);
		sparta.name.setBounds(490, 500, 100, 50);

		map.add(sparta.name);

		sparta.setContentAreaFilled(false);
		sparta.setBorderPainted(false);
		JButton endTurn = new BrownButton("End Turn", 25);
		endTurn.setBorderPainted(true);
		endTurn.setBorder(new LineBorder(Color.decode("#FFEBCD"), 2));
		endTurn.setBounds(width / 2 + 400, height / 2 + 200, 200, 100);
		map.add(endTurn);
		endTurn.addActionListener(parent);
		rest.validate();
		rest.repaint();

//		//showing the armies on the map
		ArrayList<Army> armies = p.getControlledArmies();
//		int n = armies.size();
		for (int i = 0; i < armies.size(); i++) {

			Army xArmy = armies.get(i);
			for (int j = 0; j < xArmy.getUnits().size(); j++) {
				if (xArmy.getUnits().get(j).getCurrentSoldierCount() == 0) {
					xArmy.getUnits().remove(j);
					j--;
				}

			}
			if (xArmy.getUnits().size() == 0) {
				armies.remove(armies.get(i));
				i--;
			}
		}
		for (int i = 0; i < armies.size(); i++) {
			ArmyButton armyButton = new ArmyButton("army", armies.get(i), g, parent);
			endTurn.addActionListener(armyButton);
			armiesPanel.add(armyButton);
			armyButton.setPreferredSize(new Dimension(100, 50));
			armyButton.addMouseListener(armyButton);
			armiesPanel.add(armyButton);
			armyButton.armyInfo.setBounds(1200, i * 10, 200, 200);
			map.add(armyButton.armyInfo);
		}

		rest.validate();
		rest.repaint();
//		
//		this.setVisible(true);
		// this.setSize(width, height);

//		parent.getContentPane().add(new JPanelWithBackground("hxh mapa.png"));

		for (int i = 0; i < g.getAvailableCities().size(); i++) {
			City c = g.getAvailableCities().get(i);
			if (!p.getControlledCities().contains(c)) {
				switch (c.getName()) {
				case "Cairo":
					cairo.setEnabled(false);
					break;
				case "Rome":
					rome.setEnabled(false);
					break;
				case "Sparta":
					sparta.setEnabled(false);
					break;
				default:
					break;
				}
			}
		}

	}

}
