package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.*;

import engine.City;
import engine.Player;
import units.Army;
import units.Infantry;
import units.Unit;

public class ArmiesFrame extends JFrame implements ActionListener {

	HashMap<JButton, Army> hm = new HashMap<>();
	HashMap<JButton, JPanel> hm2 = new HashMap<>();
	Player player;
	City city;
	JPanel inf;

	public ArmiesFrame(Player p, City c) {
		setTitle("Armies");
		player = p;
		city = c;
		this.setVisible(true);
		this.setSize(500, 500);
		this.setResizable(false);
		JPanelWithBackground back = new JPanelWithBackground(CityView.stretch("back1.jpg", 500, 500));
		this.add(back);
		back.setLayout(new BorderLayout());
		JPanel armies = new JPanel();
		armies.setOpaque(false);
		back.add(armies, BorderLayout.NORTH);
		armies.setPreferredSize(new Dimension(500, 100));
		JPanel rest = new JPanel();
		rest.setOpaque(false);
		back.add(rest, BorderLayout.CENTER);
		JButton def = new BrownButton("Defending Army", 12);
		def.addActionListener(this);
		armies.add(def);
		inf = new JPanel();
		inf.setLayout(new GridLayout(7, 3));
		for (Unit u : city.getDefendingArmy().getUnits()) {
			JLabel unit = new JLabel(u.toString());
			unit.setPreferredSize(new Dimension(150, 50));
//			unit.setForeground(Color.decode("#654321"));
			inf.add(unit);
		}
		inf.setVisible(false);
		inf.setOpaque(false);
		rest.add(inf);
		for (Army a : player.getControlledArmies()) {
			if (a.getCurrentLocation().equals(city.getName())) {
				JButton army = new BrownButton("Army", 12);
				army.addActionListener(this);
				armies.add(army);
				hm.put(army, a);
				JPanel info = new JPanel();
				info.setLayout(new GridLayout(100, 3));
				info.setVisible(false);
				info.setOpaque(false);
				for (Unit u : a.getUnits()) {
					JLabel unit = new JLabel(u.toString());
					unit.setPreferredSize(new Dimension(150, 50));
//					unit.setForeground(Color.decode("#654321"));
					info.add(unit);
				}
				hm2.put(army, info);
				rest.add(info);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		inf.setVisible(false);
		for (JPanel l : hm2.values()) {
			l.setVisible(false);
		}
		if (e.getActionCommand().equals("Defending Army")) {
			inf.setVisible(true);
		} else if (e.getActionCommand().equals("Army")) {
			hm2.get((JButton) e.getSource()).setVisible(true);
		}
	}

}
