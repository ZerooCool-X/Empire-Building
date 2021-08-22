package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

import buildings.ArcheryRange;
import engine.City;
import engine.Game;
import engine.Player;
import units.Army;

public class Frame extends JFrame implements ActionListener {
	JPanel startingPanel, namePanel, choseCityPanel, mapview, RomeCity, CairoCity, SpartaCity;
	static String playerName;
	int width, height;
	Game g;
	Player p;
	Clip clip;

	public Frame() throws IOException {
		this.setVisible(true);
		this.setTitle("Empire Building");
		width = (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth());
		height = (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight());
		this.setSize(width, height - 50);
		this.setResizable(false);
		this.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		startingPanel = new startingPanel(width, height, this);
		this.add(startingPanel);
		File sound = new File("Skalitz 1403.wav");
		try {
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(sound);
			clip = AudioSystem.getClip();
			clip.open(audioStream);
			clip.loop(Clip.LOOP_CONTINUOUSLY);
			clip.start();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	public Clip getClip() {
		return clip;
	}

	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action.equals("START")) {
			startingPanel.setVisible(false);
			namePanel = new namePanel(width, height, this);
			this.add(namePanel);
		} else if (action.equals("EXIT")) {
			System.exit(0);
		} else if (action.equals("PLAY")) {
			if (((namePanel) namePanel).getPlayerName().equals("")) {
				JOptionPane.showMessageDialog(null, "Enter Your Name", "Missing Name", JOptionPane.PLAIN_MESSAGE);
//			} else if (((namePanel) namePanel).getPlayerName().toLowerCase().equals("zondl")
//					|| ((namePanel) namePanel).getPlayerName().toLowerCase().equals("zerocool")) {
//				JOptionPane.showMessageDialog(null, "You are Victorious", "Victoryyyyy!!!!!",
//						JOptionPane.PLAIN_MESSAGE);
//				System.exit(0);
			} else {
				namePanel.setVisible(false);
				try {
					choseCityPanel = new choseCityPanel(width, height, this);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				this.add(choseCityPanel);
				playerName = ((namePanel) namePanel).getPlayerName();
			}

		} else if (action.equals("Cairo1") || action.equals("Rome1") || action.equals("Sparta1")) {
			try {
				String s = ((JButton) e.getSource()).getActionCommand();
				g = new Game(playerName, s.substring(0, s.length() - 1));
				p = g.getPlayer();
				mapview = new mapview(width, height, this);
				choseCityPanel.setVisible(false);
				RomeCity = new CityView(width, height, this, "Rome");
				CairoCity = new CityView(width, height, this, "Cairo");
				SpartaCity = new CityView(width, height, this, "Sparta");
				this.add(mapview);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} else if (action.equals("Rome")) {
			mapview.setVisible(false);
			RomeCity.setVisible(true);
			this.add(RomeCity);
		} else if (action.equals("Cairo")) {
			mapview.setVisible(false);
			CairoCity.setVisible(true);
			this.add(CairoCity);
		} else if (action.equals("Sparta")) {
			mapview.setVisible(false);
			SpartaCity.setVisible(true);
			this.add(SpartaCity);
		} else if (action.equals("World Map")) {
			CairoCity.setVisible(false);
			SpartaCity.setVisible(false);
			RomeCity.setVisible(false);
			mapview = new mapview(width, height, this);
			this.add(mapview);
		} else if (action.equals("End Turn")) {
			if (seigeMoreThan3()) {
				JOptionPane.showMessageDialog(null, "You have an army beseiging a city for more than 3 turns",
						"Max number of sieging turns reached", JOptionPane.PLAIN_MESSAGE);
				return;
			}
			g.endTurn();
			this.remove(mapview);
			this.add(mapview = new mapview(width, height, this));
			cityViewEndTurn();
			validate();
			repaint();
		}
	}

	public boolean seigeMoreThan3() {
		for (City c : g.getAvailableCities()) {
			if (c.getTurnsUnderSiege() == 3)
				return true;
		}
		return false;
	}

	public void cityViewEndTurn() {
		((CityView) RomeCity).head.setText(p.getName() + "                                          Gold : "
				+ p.getTreasury() + "                                                    Food : " + p.getFood()
				+ "                                                    Turn Count : " + g.getCurrentTurnCount() + "/"
				+ g.getMaxTurnCount());
		((CityView) CairoCity).head.setText(p.getName() + "                                          Gold : "
				+ p.getTreasury() + "                                                    Food : " + p.getFood()
				+ "                                                    Turn Count : " + g.getCurrentTurnCount() + "/"
				+ g.getMaxTurnCount());
		((CityView) SpartaCity).head.setText(p.getName() + "                                          Gold : "
				+ p.getTreasury() + "                                                    Food : " + p.getFood()
				+ "                                                    Turn Count : " + g.getCurrentTurnCount() + "/"
				+ g.getMaxTurnCount());
		for (City c : p.getControlledCities()) {
			if (c.getName().equals("Rome")) {
				if (((CityView) RomeCity).ba != null)
					((CityView) RomeCity).ba.initializeBottom();
				if (((CityView) RomeCity).ar != null)
					((CityView) RomeCity).ar.initializeBottom();
				if (((CityView) RomeCity).st != null)
					((CityView) RomeCity).st.initializeBottom();
			} else if (c.getName().equals("Cairo")) {
				if (((CityView) CairoCity).ba != null)
					((CityView) CairoCity).ba.initializeBottom();
				if (((CityView) CairoCity).ar != null)
					((CityView) CairoCity).ar.initializeBottom();
				if (((CityView) CairoCity).st != null)
					((CityView) CairoCity).st.initializeBottom();
			} else if (c.getName().equals("Sparta")) {
				if (((CityView) SpartaCity).ba != null)
					((CityView) SpartaCity).ba.initializeBottom();
				if (((CityView) SpartaCity).ar != null)
					((CityView) SpartaCity).ar.initializeBottom();
				if (((CityView) SpartaCity).st != null)
					((CityView) SpartaCity).st.initializeBottom();
			}
		}
	}

}
