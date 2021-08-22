package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.*;
import javax.swing.border.LineBorder;

import engine.City;
import engine.Player;
import exceptions.MaxCapacityException;
import units.Army;
import units.Infantry;
import units.Unit;

public class RelocateFrame extends JFrame implements ActionListener {

	HashMap<JButton, Unit> hmUnits = new HashMap<>();
	HashMap<JButton, JPanel> hm2 = new HashMap<>();
	HashMap<JButton, Army> hm3 = new HashMap<>();
	HashMap<JButton, JPanel> hm4 = new HashMap<>();
	HashMap<Army, JButton> hmArmies = new HashMap<>();
	Player player;
	City city;
	JPanel units2, units1, armies1, armies2;
	Unit unitx;
	Army armyx;
	JButton relocate;

	public RelocateFrame(Player p, City c) {
		setTitle("Armies");
		player = p;
		city = c;
		this.setVisible(true);
		this.setSize(500, 500);
		this.setResizable(false);
		JPanelWithBackground back = new JPanelWithBackground(CityView.stretch("back1.jpg", 500, 500));
		this.add(back);
		back.setLayout(new GridLayout(1, 5));
		armies1 = new JPanel();
		units1 = new JPanel();
		armies2 = new JPanel();
		units2 = new JPanel();
		relocate = new JButton();
		JPanel mid = new JPanel();
		mid.setOpaque(false);
		mid.setLayout(null);
		mid.add(relocate);
		CityView.setIcon(relocate, "Relocate", "arrow.png", 100, 100);
		relocate.setBounds(0, 180, 100, 100);
		relocate.setBorderPainted(false);
		relocate.setContentAreaFilled(false);
//		relocate.setSize(100, 100);
		relocate.addActionListener(this);
		armies1.setOpaque(false);
		armies2.setOpaque(false);
		units1.setOpaque(false);
		units2.setOpaque(false);
		back.add(armies1);
		back.add(units1);
		back.add(mid);
		back.add(armies2);
		back.add(units2);
		initalize();
	}

	public void initalize() {
		armies1.removeAll();
		units1.removeAll();
		armies2.removeAll();
		units2.removeAll();
		ArrayList<Army> armies = new ArrayList<>();
		armies.add(city.getDefendingArmy());
		int n = player.getControlledArmies().size();
		for (int i = 0; i < n; i++) {
			if (player.getControlledArmies().get(i).getUnits().size() == 0) {
				player.getControlledArmies().remove(player.getControlledArmies().get(i));
				n--;
				i--;
			}
		}
		for (int i = 0; i < player.getControlledArmies().size(); i++) {
			if (player.getControlledArmies().get(i).getCurrentLocation().equals(city.getName())) {
				armies.add(player.getControlledArmies().get(i));
			}
		}
		for (Army a : armies) {
			JButton army = new BrownButton("Army", 12);
			JButton army2 = new BrownButton("Army", 12);
			if (a.equals(city.getDefendingArmy())) {
				army.setText("Defending Army");
				army.setActionCommand("Army");
				army2.setText("Defending Army");
			}
			army2.setActionCommand("army2");
			army.addActionListener(this);
			army2.addActionListener(this);
			armies1.add(army);
			armies2.add(army2);
			hm3.put(army2, a);
			hmArmies.put(a, army2);
			JPanel info = new JPanel();
			info.setOpaque(false);
			info.setLayout(new GridLayout(40,1));
			JPanel info2 = new JPanel();
			info2.setOpaque(false);
			info2.setLayout(new GridLayout(40,1));
			info.setVisible(false);
			info2.setVisible(false);
			for (Unit u : a.getUnits()) {
				JButton unit = new BrownButton(u.toString(), 12);
				unit.setActionCommand("unit");
				JLabel unit2 = new JLabel(u.toString());
				unit2.setForeground(Color.decode("#A0522D"));
				unit2.setBackground(Color.decode("#FFEBCD"));
				unit2.setOpaque(true);
				unit.addActionListener(this);
				hmUnits.put(unit, u);
				info.add(unit);
				info2.add(unit2);
			}
			hm2.put(army, info);
			hm4.put(army2, info2);
			units1.add(info);
			units2.add(info2);

		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Army")) {
			for (JButton b : hm2.keySet()) {
				if (!b.isEnabled())
					b.setEnabled(true);
			}
			for (JPanel l : hm2.values()) {
				l.setVisible(false);
			}
			((JButton) e.getSource()).setEnabled(false);
			hm2.get((JButton) e.getSource()).setVisible(true);
		}
		if (e.getActionCommand().equals("army2")) {
			for (JButton b : hm3.keySet()) {
				if (!b.isEnabled())
					b.setEnabled(true);
			}
			for (JPanel l : hm4.values()) {
				l.setVisible(false);
			}
			armyx = hm3.get((JButton) e.getSource());
			((JButton) e.getSource()).setEnabled(false);
			hm4.get((JButton) e.getSource()).setVisible(true);
		}
		if (e.getActionCommand().equals("unit")) {
			for (JButton b : hmUnits.keySet()) {
				if (!b.isEnabled())
					b.setEnabled(true);
			}
			((JButton) e.getSource()).setEnabled(false);
			unitx = hmUnits.get((JButton) e.getSource());
		}
		if (e.getActionCommand().equals("Relocate")) {
			if (unitx == null || armyx == null) {
				JOptionPane.showMessageDialog(null, "Please choose a unit and an army", "Choose Unit and Army",
						JOptionPane.PLAIN_MESSAGE);
			} else {
				try {
					armyx.relocateUnit(unitx);
					unitx = null;
					initalize();
					hmArmies.get(armyx).setEnabled(false);
					hm4.get(hmArmies.get(armyx)).setVisible(true);
					revalidate();
					repaint();
				} catch (MaxCapacityException e1) {
					JOptionPane.showMessageDialog(null, "Max Capacity Reached", "This army have reached max capacity",
							JOptionPane.PLAIN_MESSAGE);
				}
			}
		}
	}

}
