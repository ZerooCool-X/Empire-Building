package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.beancontext.BeanContext;
import java.io.IOException;

import javax.imageio.IIOException;
import javax.swing.*;
import javax.swing.border.LineBorder;

import buildings.*;
import engine.City;
import engine.Game;
import engine.Player;
import exceptions.NotEnoughGoldException;

public class CityView extends JPanelWithBackground implements ActionListener {

	Game g;
	String name;
	CityButton barracks, archeryrange, stable, farm, market;
	JLabel head;
	JPanel mid, rest;
	Player p;
	JPanel[][] panelHolder;
	BuildingPanel ba, ar, st, ma, fa;

	public CityView(int width, int height, Frame parent, String name) throws IOException {
		super(stretch("terrain.png", width, height));
		setOpaque(false);
//		this.setVisible(true);
		this.setSize(width, height);
		g = parent.g;
		this.name = name;
		p = g.getPlayer();
		setLayout(new BorderLayout());
		head = new JLabel(p.getName() + "                                          Gold : " + p.getTreasury()
				+ "                                                    Food : " + p.getFood()
				+ "                                                    Turn Count : " + g.getCurrentTurnCount() + "/"
				+ g.getMaxTurnCount());
		head.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
		head.setForeground(Color.decode("#654321"));
		this.add(head, BorderLayout.NORTH);
		rest = new JPanel();
		rest.setOpaque(false);
		this.add(rest, BorderLayout.CENTER);
		rest.setVisible(true);
		rest.setLayout(new GridLayout(2, 3));
		JPanelWithBackground buttons = new JPanelWithBackground(stretch("back1.jpg", width / 3, height / 2));
		buttons.setLayout(new BorderLayout());
		buttons.setBorder(new LineBorder(Color.decode("#A0522D"), 2));
		JPanel top = new JPanel();
		JPanel bottom = new JPanel();
		buttons.setOpaque(false);
		top.setOpaque(false);
		bottom.setOpaque(false);

		mid = new JPanel();
		mid.setOpaque(false);
		buttons.add(top, BorderLayout.NORTH);
		buttons.add(mid, BorderLayout.CENTER);
		buttons.add(bottom, BorderLayout.SOUTH);
		barracks = new CityButton("", new Barracks(), "build", this, top);
		archeryrange = new CityButton("", new ArcheryRange(), "build", this, top);
		stable = new CityButton("", new Stable(), "build", this, top);
		market = new CityButton("", new Market(), "build", this, top);
		farm = new CityButton("", new Farm(), "build", this, top);
		setIcon(barracks, "barracks", "barracks_ico.png", 60, 60);
		setIcon(archeryrange, "archeryrange", "archeryrange_ico.png", 60, 60);
		setIcon(stable, "stable", "stable_ico.png", 60, 60);
		setIcon(market, "market", "market_ico.png", 60, 60);
		setIcon(farm, "farm", "farm_ico.png", 60, 60);
		mid.add(barracks.info);
		mid.add(archeryrange.info);
		mid.add(stable.info);
		mid.add(market.info);
		mid.add(farm.info);
		JButton armies = new BrownButton("Armies", 12);
		armies.addActionListener(this);
		bottom.add(armies);
		JButton init = new BrownButton("Initiate Army", 12);
		init.addActionListener(this);
		bottom.add(init);
		JButton relocate = new BrownButton("Relocate Unit", 12);
		relocate.addActionListener(this);
		bottom.add(relocate);
		JButton worldMap = new BrownButton("World Map", 12);
		worldMap.addActionListener(parent);
		bottom.add(worldMap);

		panelHolder = new JPanel[2][3];
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 3; j++) {
				panelHolder[i][j] = new JPanel();
				panelHolder[i][j].setLayout(new GridLayout(1, 1));
				panelHolder[i][j].setOpaque(false);
				rest.add(panelHolder[i][j]);
			}
		}
		panelHolder[1][2].add(buttons);
	}

	public static void setIcon(JButton button, String action, String fileName, int width, int height) {
		button.setPreferredSize(new Dimension(width, height));
//		button.setContentAreaFilled(false);
//		button.setBorderPainted(false);
		button.setActionCommand(action);
		button.setIcon(stretch(fileName, width, height));
	}

	public static ImageIcon stretch(String fileName, int width, int height) {
		ImageIcon ico = new ImageIcon(fileName);
		Image image = ico.getImage(); // transform it
		Image newimg = image.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
		return new ImageIcon(newimg);
	}

	public void actionPerformed(ActionEvent e) {
		try {
			City c = null;
			for (City i : p.getControlledCities()) {
				if (i.getName().equals(name)) {
					c = i;
					break;
				}
			}
			if (e.getActionCommand().equals("barracks")) {
				g.getPlayer().build("Barracks", name);
				for (Building b : c.getMilitaryBuildings()) {
					if (b instanceof Barracks) {
						panelHolder[0][0].add(ba = new BuildingPanel(g, name, b, this));
						barracks.building = b;
						break;
					}
				}
				barracks.setEnabled(false);
			} else if (e.getActionCommand().equals("archeryrange")) {
				g.getPlayer().build("ArcheryRange", name);
				for (Building b : c.getMilitaryBuildings()) {
					if (b instanceof ArcheryRange) {
						panelHolder[0][1].add(ar = new BuildingPanel(g, name, b, this));
						archeryrange.building = b;
						break;
					}
				}
				archeryrange.setEnabled(false);
			} else if (e.getActionCommand().equals("stable")) {
				g.getPlayer().build("Stable", name);
				for (Building b : c.getMilitaryBuildings()) {
					if (b instanceof Stable) {
						panelHolder[0][2].add(st = new BuildingPanel(g, name, b, this));
						stable.building = b;
						break;
					}
				}
				stable.setEnabled(false);
			} else if (e.getActionCommand().equals("market")) {
				g.getPlayer().build("Market", name);
				for (Building b : c.getEconomicalBuildings()) {
					if (b instanceof Market) {
						panelHolder[1][1].add(ma = new BuildingPanel(g, name, b, this));
						market.building = b;
						break;
					}
				}
				market.setEnabled(false);
			} else if (e.getActionCommand().equals("farm")) {
				g.getPlayer().build("Farm", name);
				for (Building b : c.getEconomicalBuildings()) {
					if (b instanceof Farm) {
						panelHolder[1][0].add(fa = new BuildingPanel(g, name, b, this));
						farm.building = b;
						break;
					}
				}
				farm.setEnabled(false);
			}
			head.setText(p.getName() + "                                          Gold : " + p.getTreasury()
					+ "                                                    Food : " + p.getFood()
					+ "                                                    Turn Count : " + g.getCurrentTurnCount()
					+ "/" + g.getMaxTurnCount());
		} catch (NotEnoughGoldException e1) {
			JOptionPane.showMessageDialog(null, "You don't have enough gold", "Not Enough Gold",
					JOptionPane.PLAIN_MESSAGE);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (e.getActionCommand().equals("Initiate Army")) {
			for (City x : p.getControlledCities()) {
				if (x.getName().equals(name)) {
					new InitiateFrame(p, x);
					break;
				}
			}
		}
		if (e.getActionCommand().equals("Armies")) {
			for (City x : p.getControlledCities()) {
				if (x.getName().equals(name)) {
					new ArmiesFrame(p, x);
					break;
				}
			}
		}
		if (e.getActionCommand().equals("Relocate Unit")) {
			for (City x : p.getControlledCities()) {
				if (x.getName().equals(name)) {
					new RelocateFrame(p, x);
					break;
				}
			}
		}
	}

}
