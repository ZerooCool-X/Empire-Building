package view;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;

import buildings.*;
import engine.City;
import engine.Game;
import engine.Player;
import exceptions.BuildingInCoolDownException;
import exceptions.MaxLevelException;
import exceptions.MaxRecruitedException;
import exceptions.NotEnoughGoldException;

public class BuildingPanel extends JPanelWithBackground implements ActionListener {

	Building building;
	Game g;
	String cityName;
	Player p;
	CityView city;
	JPanel bottom;

	public BuildingPanel(Game g, String cityName, Building building, CityView city) throws IOException {
		super("Empty.png");
		this.g = g;
		this.building = building;
		this.cityName = cityName;
		this.city = city;
		p = g.getPlayer();
		ImageIcon img;
		setLayout(new BorderLayout());
		setSize(500, 500);
		if (building instanceof Barracks) {
			img = stretch("barracks.png", 300, 300);
		} else if (building instanceof ArcheryRange) {
			img = stretch("archeryrange.png", 300, 300);
		} else if (building instanceof Stable) {
			img = stretch("stable.png", 300, 300);
		} else if (building instanceof Market) {
			img = stretch("market.png", 300, 300);
		} else {
			img = stretch("mill.png", 300, 300);
		}
		bottom = new JPanel();
		initializeBottom();
		CityLabel center = new CityLabel(img, building, "building");
		add(center, BorderLayout.CENTER);
		add(bottom, BorderLayout.SOUTH);
		city.mid.add(center.info);
		this.setOpaque(false);
		center.setOpaque(false);
	}

	public void initializeBottom() {
		bottom.removeAll();
		if (building instanceof MilitaryBuilding) {
			CityButton recruit = new CityButton("recruit", building, "recruit", this, bottom);
			recruit.setPreferredSize(new Dimension(100, 50));
			recruit.setBorderPainted(false);
			recruit.setFocusable(false);
			recruit.setBackground(Color.decode("#A0522D"));
			recruit.setForeground(Color.decode("#FFEBCD"));
			recruit.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
			city.mid.add(recruit.info);
		}
		CityButton upgrade = new CityButton("upgrade", building, "upgrade", this, bottom);
		upgrade.setPreferredSize(new Dimension(100, 50));
		upgrade.setPreferredSize(new Dimension(100, 50));
		upgrade.setBorderPainted(false);
		upgrade.setFocusable(false);
		upgrade.setBackground(Color.decode("#A0522D"));
		upgrade.setForeground(Color.decode("#FFEBCD"));
		upgrade.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
		city.mid.add(upgrade.info);
		bottom.setOpaque(false);
	}

	public static ImageIcon stretch(String fileName, int width, int height) {
		ImageIcon ico = new ImageIcon(fileName);
		Image image = ico.getImage(); // transform it
		Image newimg = image.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
		return new ImageIcon(newimg);
	}

	public void actionPerformed(ActionEvent e) {
		JOptionPane op = new JOptionPane();
		if (e.getActionCommand().equals("upgrade")) {
			try {
				g.getPlayer().upgradeBuilding(building);
				city.head.setText(p.getName() + "                                          Gold : " + p.getTreasury()
						+ "                                                    Food : " + p.getFood()
						+ "                                                    Turn Count : " + g.getCurrentTurnCount()
						+ "/" + g.getMaxTurnCount());
			} catch (BuildingInCoolDownException e1) {
				op.showMessageDialog(null, "This building is cooling down", "Cooldown", JOptionPane.PLAIN_MESSAGE);
			} catch (MaxLevelException e1) {
				op.showMessageDialog(null, "This building reached max level", "Max Level Reached",
						JOptionPane.PLAIN_MESSAGE);
			} catch (NotEnoughGoldException e1) {
				op.showMessageDialog(null, "You don't have enough gold", "Not Enough Gold", JOptionPane.PLAIN_MESSAGE);
			}
		}
		if (e.getActionCommand().equals("recruit")) {
//			System.out.println(e.getSource());
			try {
				if (building instanceof ArcheryRange) {
					g.getPlayer().recruitUnit("Archer", cityName);
					CityLabel unit = new CityLabel(stretch("Archer.png", 50, 50), building, "unit");
					bottom.add(unit);
					city.mid.add(unit.info);
				} else if (building instanceof Barracks) {
					g.getPlayer().recruitUnit("Infantry", cityName);
					CityLabel unit = new CityLabel(stretch("Infantry.png", 50, 50), building, "unit");
					bottom.add(unit);
					city.mid.add(unit.info);
				} else if (building instanceof Stable) {
					g.getPlayer().recruitUnit("Cavalry", cityName);
					CityLabel unit = new CityLabel(stretch("Cavalry.png", 50, 50), building, "unit");
					bottom.add(unit);
					city.mid.add(unit.info);
				}
				city.head.setText(p.getName() + "                                          Gold : " + p.getTreasury()
						+ "                                                    Food : " + p.getFood()
						+ "                                                    Turn Count : " + g.getCurrentTurnCount()
						+ "/" + g.getMaxTurnCount());
			} catch (BuildingInCoolDownException e1) {
				op.showMessageDialog(null, "This building is cooling down", "Cooldown", JOptionPane.PLAIN_MESSAGE);
			} catch (MaxRecruitedException e1) {
				op.showMessageDialog(null, "This building reached max recruited units", "Max Recruited Reached",
						JOptionPane.PLAIN_MESSAGE);
			} catch (NotEnoughGoldException e1) {
				op.showMessageDialog(null, "You don't have enough gold", "Not Enough Gold", JOptionPane.PLAIN_MESSAGE);
			}
		}
	}

}
