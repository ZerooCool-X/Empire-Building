package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import buildings.ArcheryRange;
import buildings.Barracks;
import buildings.Building;
import buildings.Market;
import buildings.MilitaryBuilding;
import buildings.Stable;
import units.Unit;

public class CityButton extends JButton implements MouseListener {

	JLabel info;
	Building building;
	String action;
	String s, r;

	public CityButton(String text, Building b, String action, ActionListener al, JPanel parentPanel) {
		super(text);
//		this.info = info;
		this.building = b;
		this.action = action;
		this.addActionListener(al);
		addMouseListener(this);
		parentPanel.add(this);
		info = new JLabel();
		info.setVisible(false);
		info.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
//		info.setForeground(Color.blue);
		if (building instanceof ArcheryRange) {
			r = "ArcheryRange";
		} else if (building instanceof Barracks) {
			r = "Barracks";
		} else if (building instanceof Stable) {
			r = "Stable";
		} else if (building instanceof Market) {
			r = "Market";
		} else {
			r = "Farm";
		}
		if (building instanceof ArcheryRange) {
			s = "Archer";
		} else if (building instanceof Barracks) {
			s = "Infantry";
		} else {
			s = "Cavalry";
		}
		if (action.equals("recruit")) {
			Unit u = ((MilitaryBuilding) building).create();
			info.setText("<html>Recruit " + s + "<br>" + "Cost : " + ((MilitaryBuilding) building).getRecruitmentCost()
					+ "<br>" + "Soldier Count : " + u.getCurrentSoldierCount() + "<br>" + "Max Soldier Count : "
					+ u.getMaxSoldierCount() + "</html>");
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		if (action.equals("upgrade")) {
			info.setText("<html>upgrade " + r + "<br>" + "Cost : " + building.getUpgradeCost() + "</html>");
		}
		if (action.equals("build")) {
			if (this.isEnabled()) {
				info.setText("<html>Build " + r + "<br>" + "Cost : " + building.getCost() + "</html>");
			} else
				info.setText("<html>You can only have one building<br>of each type in each city</html>");
		}
		info.setVisible(true);
		setForeground(Color.decode("#A0522D"));
		setBackground(Color.decode("#FFEBCD"));
		validate();
		repaint();
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
//		info = new JPanel();
		info.setVisible(false);
		setBackground(Color.decode("#A0522D"));
		setForeground(Color.decode("#FFEBCD"));
		validate();
		repaint();
	}
}
