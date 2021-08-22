package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.*;
import javax.swing.text.html.Option;

import engine.City;
import engine.Player;
import units.Unit;

public class InitiateFrame extends JFrame implements ActionListener {

	HashMap<JButton, Unit> hm = new HashMap<>();

	City city;
	Player player;
	Unit u;
	JButton b;

	public InitiateFrame(Player p, City c) {
		setTitle("Initiate Army");
		player = p;
		city = c;
		this.setVisible(true);
		this.setSize(500, 500);
		this.setResizable(false);
		JPanelWithBackground units = new JPanelWithBackground(CityView.stretch("back1.jpg", 500, 420));
		this.add(units, BorderLayout.CENTER);
		units.setOpaque(false);
		for (Unit u : c.getDefendingArmy().getUnits()) {
			JButton unit = new BrownButton(u.toString(), 12);
			units.add(unit);
			unit.setActionCommand("unit");
			unit.addActionListener(this);
			hm.put(unit, u);
		}
		JButton init = new BrownButton("Initiate", 20);
		init.addActionListener(this);
		init.setPreferredSize(new Dimension(500, 80));
		this.add(init, BorderLayout.SOUTH);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("unit")) {
			if (b != null) {
				b.setEnabled(true);
			}
			b = (JButton) e.getSource();
			u = hm.get(b);
			b.setEnabled(false);
		} else if (e.getActionCommand().equals("Initiate")) {
			if (u == null) {
				(new JOptionPane()).showMessageDialog(null, "Please choose a unit", "Choose Unit",
						JOptionPane.PLAIN_MESSAGE);
			} else {
				player.initiateArmy(city, u);
			}
			this.dispose();
		}
	}

}
