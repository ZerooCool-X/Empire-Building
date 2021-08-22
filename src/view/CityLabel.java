package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
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

public class CityLabel extends JLabel implements MouseListener {

	JLabel info;
	Building building;
	String action;

	public CityLabel(ImageIcon img, Building b, String action) {
		super(img);
//		this.info = info;
		this.building = b;
		this.action = action;
		addMouseListener(this);
		info = new JLabel();
		info.setVisible(false);
		info.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
		
		String s;
		if (building instanceof ArcheryRange) {
			s = "Archer";
		} else if (building instanceof Barracks) {
			s = "Infantry";
		} else {
			s = "Cavalry";
		}
		if (action.equals("unit")) {
			Unit u = ((MilitaryBuilding) building).create();
			info.setText(
					"<html>" + s + " level : " + u.getLevel() + "<br>" + "Soldier Count : " + u.getCurrentSoldierCount()
							+ "<br>" + "Max Soldier Count : " + u.getMaxSoldierCount() + "</html>");
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
		String r;
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
		if (action.equals("building")) {
			info.setText("<html>" + r + " level : " + building.getLevel() + "<br>"
					+ (building.isCoolDown() ? "In Cooldown" : "Available") + "</html>");
		}
		info.setVisible(true);
		validate();
		repaint();
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
//		info = new JPanel();
		info.setVisible(false);
		validate();
		repaint();
	}
}
