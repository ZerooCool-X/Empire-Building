package view;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import buildings.*;

import units.*;
import engine.Game;
import engine.Player;
import exceptions.FriendlyFireException;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.text.NumberFormat.*;
import java.util.*;

public class BattleView extends JPanelWithBackground implements ActionListener, MouseListener {

	Game game;
	Army me1, other1;
	Unit[] myArmy;
	JButton[] myArmybut;
	Unit[] otherArmy;
	JButton[] otherArmybut;
	JPanel up, down, east, west, center, centerUp, centerhelpup, centerhelpdown, centerhelpleft;
	JScrollPane centerDownPane;
	JTextPane centerDown;
	StyledDocument centerDown2;
	HashMap<JButton, Integer> mybut = new HashMap<JButton, Integer>();
	JLabel inf, inf2, arch, arch2, cav, cav2, left, right;
	Frame frame;
	JButton attacker, defender, attack, autoResolve, worldmap;
	Clip clip;
	javax.swing.text.Style styleRed, stylegreen;
	boolean ups, downs;

	public BattleView(Army me1, Army other1, Game game, Frame f) throws IOException {
		super(stretch("battle.jpg", f.width, f.height));
		this.frame = f;
		this.game = game;
		this.me1 = me1;
		this.other1 = other1;
		ArrayList<Unit> me = me1.getUnits();
		ArrayList<Unit> other = other1.getUnits();
		setLayout(new BorderLayout());

		// images

		inf = new JLabel(stretch("Infantry.png", 240, 240));
		inf2 = new JLabel(stretch("Infantry2.png", 240, 240));
		arch = new JLabel(new ImageIcon("Archer.png"));
		arch2 = new JLabel(new ImageIcon("Archer2.png"));
		cav = new JLabel(stretch("Cavalry.png", 240, 240));
		cav2 = new JLabel(stretch("Cavalry2.png", 240, 240));

//		setPreferredSize(new Dimension(200,200));
//		setBackground(new Color(1));
		up = new JPanel();
		up.setPreferredSize(new Dimension(200, 200));
//		up.setBorder(BorderFactory.createEmptyBorder(100,100,100,100));
//		up.setSize(200,200);
		down = new JPanel();
		down.setPreferredSize(new Dimension(200, 200));
//		down.setBorder(BorderFactory.createEmptyBorder(100,100,100,100));

		west = new JPanel();
		west.setPreferredSize(new Dimension(300, 300));
//		west.setBorder(BorderFactory.createEmptyBorder(100,100,100,100));

		east = new JPanel();
		east.setPreferredSize(new Dimension(300, 300));
		left = new JLabel();
		right = new JLabel();

//		east.setBorder(BorderFactory.createEmptyBorder(100,100,100,100));
		center = new JPanel();

		center.setPreferredSize(new Dimension(300, 300));
		centerUp = new JPanel();
//		centerUp.setPreferredSize(new Dimension(50, 50));
		centerDown = new JTextPane();
		JPanelWithBackground centerDownPanel = new JPanelWithBackground(
				stretch("scroll1.png", f.width - 600, f.height - 500));

		centerhelpdown = new JPanel();
		centerhelpup = new JPanel();
		centerhelpleft = new JPanel();
		centerhelpdown.setOpaque(false);
		centerhelpup.setOpaque(false);
		centerhelpleft.setOpaque(false);
		centerhelpdown.setPreferredSize(new Dimension(f.width - 600, 75));
		centerhelpup.setPreferredSize(new Dimension(f.width - 600, 75));
		centerhelpleft.setPreferredSize(new Dimension(75, f.height - 500));
		centerDownPanel.setLayout(new BorderLayout());
		centerDownPanel.setOpaque(false);
		centerDownPane = new JScrollPane(centerDown);
		centerDownPanel.add(BorderLayout.NORTH, centerhelpup);
		centerDownPanel.add(BorderLayout.WEST, centerhelpleft);
		centerDownPanel.add(BorderLayout.SOUTH, centerhelpdown);
		centerDownPanel.add(BorderLayout.CENTER, centerDownPane);
		centerDownPane.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
		centerDownPane.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 0));
		centerDown.setBorder(null);

		centerDown.setEditable(false);
		centerDown.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
		centerDown.setForeground(Color.decode("#A40000"));

		centerDown.setOpaque(false);
		centerDownPane.setOpaque(false);
		centerDownPane.getViewport().setOpaque(false);
		centerDownPane.setBorder(null);
		centerDownPane.getViewport().setBorder(null);
		centerDown2 = centerDown.getStyledDocument();
		stylegreen = centerDown.addStyle("lol", null);
		styleRed = centerDown.addStyle("lol", null);
		StyleConstants.setForeground(stylegreen, Color.decode("#006400"));
		StyleConstants.setForeground(styleRed, Color.decode("#A40000"));

//		centerUp.setBackground(new Color(0));
		west.setLayout(new GridLayout(2, 1));
		east.setLayout(new GridLayout(2, 1));

		center.setLayout(new BorderLayout());
		attack = new BrownButton("Attack", 16);
		autoResolve = new BrownButton("Auto-resolve", 16);
		worldmap = new JButton("World Map");
		attack.setPreferredSize(new Dimension(130, 70));
		autoResolve.setPreferredSize(new Dimension(130, 70));
		worldmap.setPreferredSize(new Dimension(130, 70));
		attack.setFocusable(false);
		autoResolve.setFocusable(false);
		worldmap.setFocusable(false);

		attack.setBorderPainted(true);
		autoResolve.setBorderPainted(true);

		autoResolve.addActionListener(this);
		attack.addActionListener(this);
		worldmap.addActionListener(this);
		centerUp.add(attack);
		centerUp.add(autoResolve);
//		centerUp.add(worldmap);

		centerUp.setOpaque(false);
		center.add(BorderLayout.NORTH, centerUp);
		center.add(BorderLayout.CENTER, centerDownPanel);

		left.setForeground(Color.decode("#006400"));
		right.setForeground(Color.decode("#A40000"));

//		up.setBackground(new Color(255,0,0));
//		down.setBackground(new Color(0,0,255));
//		west.setBackground(new Color(0, 255, 0));
//		east.setBackground(new Color(255, 255, 255));
		myArmy = new Unit[me.size()];
		myArmybut = new JButton[me.size()];
		otherArmy = new Unit[other.size()];
		otherArmybut = new JButton[other.size()];
		for (int i = 0; i < me.size(); i++) {
			JButton button = new JButton((me.get(i).getClass() + "").substring(12) + " level " + me.get(i).getLevel());
//			button.setPreferredSize(new Dimension(130, 70));
			button.setBackground(
					new Color((int) (255 - 255.0 * me.get(i).getCurrentSoldierCount() / me.get(i).getMaxSoldierCount()),
							(int) (255.0 * me.get(i).getCurrentSoldierCount() / me.get(i).getMaxSoldierCount()), 0));
			button.setForeground(Color.blue);
			button.setFocusable(false);
			button.addActionListener(this);
			button.addMouseListener(this);
			down.add(button);
			myArmy[i] = me.get(i);
			myArmybut[i] = button;
			mybut.put(button, (i + 1));
		}
		for (int i = 0; i < other.size(); i++) {
			JButton button = new JButton(
					(other.get(i).getClass() + "").substring(12) + " level " + other.get(i).getLevel());
//			button.setPreferredSize(new Dimension(130, 70));
			button.setBackground(new Color(
					(int) (255 - 255.0 * other.get(i).getCurrentSoldierCount() / other.get(i).getMaxSoldierCount()),
					(int) (255.0 * other.get(i).getCurrentSoldierCount() / other.get(i).getMaxSoldierCount()), 0));
			button.setForeground(Color.blue);
			button.setFocusable(false);
			button.addActionListener(this);

			button.addMouseListener(this);
			up.add(button);
			otherArmy[i] = other.get(i);
			otherArmybut[i] = button;
			mybut.put(button, -(i + 1));
		}
//		center.setOpaque(false);
//		center.setBackground(new Color(0,0,0,125));
		up.setOpaque(false);
		down.setOpaque(false);
		west.setOpaque(false);
		east.setOpaque(false);
		center.setOpaque(false);
		setOpaque(false);
		this.add(BorderLayout.NORTH, up);
		this.add(BorderLayout.SOUTH, down);
		this.add(BorderLayout.WEST, west);
		this.add(BorderLayout.EAST, east);
		this.add(BorderLayout.CENTER, center);

		File sound = new File("war.wav");
		try {
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(sound);
			clip = AudioSystem.getClip();
			clip.open(audioStream);
			clip.loop(Clip.LOOP_CONTINUOUSLY);
			clip.start();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		repaint();
		validate();
	}

	public void actionPerformed(ActionEvent e) {

		if (e.getActionCommand().equals("World Map")) {
			frame.remove(this);
			clip.stop();
			frame.getClip().start();
			frame.getClip().loop(Clip.LOOP_CONTINUOUSLY);
			frame.add(frame.mapview = new mapview(frame.width, frame.height, frame));
			frame.validate();
			frame.repaint();
			return;
		}
		if (e.getActionCommand().equals("Auto-resolve")) {
			try {
//				System.out.println(me1.getUnits());
//				System.out.println(other1.getUnits());
				game.autoResolve(me1, other1);
			} catch (FriendlyFireException e1) {
				JOptionPane op = new JOptionPane();
				op.showMessageDialog(null, "This city is friendly");
			}
			if (me1.getUnits().size() == 0) {
				JOptionPane op = new JOptionPane();
				op.showMessageDialog(null, "You Lost");
				frame.remove(this);
				clip.stop();
				frame.getClip().start();
				frame.getClip().loop(Clip.LOOP_CONTINUOUSLY);
				frame.add(frame.mapview = new mapview(frame.width, frame.height, frame));
				frame.validate();
				frame.repaint();
				return;
			} else {
				JOptionPane op = new JOptionPane();
				op.showMessageDialog(null, "You Won");
				frame.remove(this);
				clip.stop();
				frame.getClip().start();
				frame.getClip().loop(Clip.LOOP_CONTINUOUSLY);
				frame.add(frame.mapview = new mapview(frame.width, frame.height, frame));
				frame.validate();
				frame.repaint();
				return;
			}

		}
		if (e.getActionCommand().equals("Attack")) {
			autoResolve.setEnabled(false);
			worldmap.setEnabled(false);
			if (attacker == null) {
				JOptionPane op = new JOptionPane();
				op.showMessageDialog(null, "Select Attacker");
				return;
			}
			if (defender == null) {
				JOptionPane op = new JOptionPane();
				op.showMessageDialog(null, "Select Defender");
				return;
			}
			int numb_of_units = otherArmy[-mybut.get(defender) - 1].getCurrentSoldierCount();
			try {
				myArmy[mybut.get(attacker) - 1].attack(otherArmy[-mybut.get(defender) - 1]);
			} catch (FriendlyFireException e1) {
				JOptionPane op = new JOptionPane();
				op.showMessageDialog(null, "This city is friendly");
			}
			int numb_of_units_2 = otherArmy[-mybut.get(defender) - 1].getCurrentSoldierCount();

			try {
				centerDown2.insertString(centerDown2.getLength(),
						otherArmybut[-mybut.get(defender) - 1].getActionCommand() + " was attcked by "
								+ myArmybut[mybut.get(attacker) - 1].getActionCommand() + " and lost "
								+ (numb_of_units - numb_of_units_2) + " soliders \n",
						stylegreen);
			} catch (BadLocationException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}

			if (otherArmy[-mybut.get(defender) - 1].getCurrentSoldierCount() <= 0) {
				try {
					centerDown2.insertString(centerDown2.getLength(),
							otherArmybut[-mybut.get(defender) - 1].getActionCommand() + " died \n", stylegreen);
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				defender.setEnabled(true);
				defender.setVisible(false);
				defender = null;
				east.removeAll();
				repaint();
				revalidate();
			} else {
				right.setText("Level: " + otherArmy[-mybut.get(defender) - 1].getLevel() + "   Solider Count: "
						+ otherArmy[-mybut.get(defender) - 1].getCurrentSoldierCount());
				right.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
				right.setForeground(Color.decode("#A40000"));
				defender.setBackground(new Color(
						(int) (255 - 255.0 * otherArmy[-mybut.get(defender) - 1].getCurrentSoldierCount()
								/ otherArmy[-mybut.get(defender) - 1].getMaxSoldierCount()),
						(int) (255.0 * otherArmy[-mybut.get(defender) - 1].getCurrentSoldierCount()
								/ otherArmy[-mybut.get(defender) - 1].getMaxSoldierCount()),
						0));
				repaint();
				revalidate();
			}

			if (me1.getUnits().size() == 0) {
				JOptionPane op = new JOptionPane();
				op.showMessageDialog(null, "You Lost");
				frame.remove(this);
				clip.stop();
				frame.getClip().start();
				frame.getClip().loop(Clip.LOOP_CONTINUOUSLY);
				frame.add(frame.mapview = new mapview(frame.width, frame.height, frame));
				frame.validate();
				frame.repaint();
				return;
			} else if (other1.getUnits().size() == 0) {
				JOptionPane op = new JOptionPane();
				game.occupy(me1, me1.getCurrentLocation());
				op.showMessageDialog(null, "You Won");
				frame.remove(this);
				clip.stop();
				frame.getClip().start();
				frame.getClip().loop(Clip.LOOP_CONTINUOUSLY);
				frame.add(frame.mapview = new mapview(frame.width, frame.height, frame));
				frame.validate();
				frame.repaint();
				return;
			}

			int rand = (int) (Math.random() * (myArmy.length));
			Unit a = myArmy[rand];

			while (!myArmybut[rand].isVisible()) {
				rand = (int) (Math.random() * (myArmy.length));
				a = myArmy[rand];
			}
			int rand2 = (int) (Math.random() * (otherArmy.length));
			Unit b = otherArmy[rand2];
			while (!otherArmybut[rand2].isVisible()) {
				rand2 = (int) (Math.random() * (otherArmy.length));
				b = otherArmy[rand2];
			}

			numb_of_units = a.getCurrentSoldierCount();

			try {
				b.attack(a);
			} catch (FriendlyFireException e1) {
				JOptionPane op = new JOptionPane();
				op.showMessageDialog(null, "This city is friendly");
			}
			numb_of_units_2 = a.getCurrentSoldierCount();
			try {
				centerDown2.insertString(centerDown2.getLength(),
						myArmybut[rand].getActionCommand() + " was attcked by " + otherArmybut[rand2].getActionCommand()
								+ " and lost " + (numb_of_units - numb_of_units_2) + " soliders \n",
						styleRed);
			} catch (BadLocationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (a.getCurrentSoldierCount() <= 0) {
				myArmybut[rand].setEnabled(true);
				myArmybut[rand].setVisible(false);
				if (myArmybut[rand].equals(attacker)) {
					west.removeAll();
					attacker = null;
				}
				try {
					centerDown2.insertString(centerDown2.getLength(), myArmybut[rand].getActionCommand() + " died \n",
							styleRed);
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			} else {
				if (myArmybut[rand].equals(attacker)) {
					left.setText("Level: " + myArmy[rand].getLevel() + "   Solider Count: "
							+ myArmy[rand].getCurrentSoldierCount());
					left.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
					left.setForeground(Color.decode("#006400"));
				}
				myArmybut[rand].setBackground(new Color(
						(int) (255 - 255.0 * myArmy[rand].getCurrentSoldierCount() / myArmy[rand].getMaxSoldierCount()),
						(int) (255.0 * myArmy[rand].getCurrentSoldierCount() / myArmy[rand].getMaxSoldierCount()), 0));
				repaint();
				revalidate();
			}
			if (me1.getUnits().size() == 0) {
				JOptionPane op = new JOptionPane();
				op.showMessageDialog(null, "You Lost");
				frame.remove(this);
				clip.stop();
				frame.getClip().start();
				frame.getClip().loop(Clip.LOOP_CONTINUOUSLY);
				frame.add(frame.mapview = new mapview(frame.width, frame.height, frame));
				frame.validate();
				frame.repaint();
				return;
			} else if (other1.getUnits().size() == 0) {
				JOptionPane op = new JOptionPane();
				game.occupy(me1, me1.getCurrentLocation());
				op.showMessageDialog(null, "You Won");
				frame.remove(this);
				clip.stop();
				frame.getClip().start();
				frame.getClip().loop(Clip.LOOP_CONTINUOUSLY);
				frame.add(frame.mapview = new mapview(frame.width, frame.height, frame));
				frame.validate();
				frame.repaint();
				return;
			}

			return;
		}

		JButton a = (JButton) e.getSource();

		int x = mybut.get(a);
		a.setEnabled(false);
		if (x > 0) {
			west.removeAll();
			if (a.getText().charAt(0) == 'A') {
				west.add(arch);
			} else if (a.getText().charAt(0) == 'I') {
				west.add(inf);
			} else {
				west.add(cav);
			}
			Unit b = myArmy[x - 1];
			left.setText("Level: " + b.getLevel() + "   Solider Count: " + b.getCurrentSoldierCount());
			left.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
			left.setForeground(Color.green);
			west.add(left);
			attacker = a;
			downs = true;
			for (int i = 0; i < myArmybut.length; i++) {
				if (myArmybut[i] != a && myArmybut[i].isVisible()) {
					myArmybut[i].setEnabled(true);
				}
			}
			repaint();
			validate();
		} else {
			east.removeAll();
			if (a.getText().charAt(0) == 'A') {
				east.add(arch2);
			} else if (a.getText().charAt(0) == 'I') {
				east.add(inf2);
			} else {
				east.add(cav2);
			}
			Unit b = otherArmy[-x - 1];
			right.setText("Level: " + b.getLevel() + "   Solider Count: " + b.getCurrentSoldierCount());
			right.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
			right.setForeground(Color.red);
			east.add(right);
			defender = a;
			ups = true;
			for (int i = 0; i < otherArmybut.length; i++) {
				if (otherArmybut[i] != a && otherArmybut[i].isVisible()) {

					otherArmybut[i].setEnabled(true);
				}
			}
			repaint();
			validate();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

		JButton a = (JButton) e.getSource();

		int x = mybut.get(a);
		if (x > 0) {
			if (!downs) {
				west.removeAll();
				if (a.getText().charAt(0) == 'A') {
					west.add(arch);
				} else if (a.getText().charAt(0) == 'I') {
					west.add(inf);
				} else {
					west.add(cav);
				}

				Unit b = myArmy[x - 1];
				left.setText("Level: " + b.getLevel() + "   Solider Count: " + b.getCurrentSoldierCount());
				left.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
				left.setForeground(Color.green);
				west.add(left);
				repaint();
				validate();
			}
		} else {
			if (!ups) {
				east.removeAll();
				if (a.getText().charAt(0) == 'A') {
					east.add(arch2);
				} else if (a.getText().charAt(0) == 'I') {
					east.add(inf2);
				} else {
					east.add(cav2);
				}
				Unit b = otherArmy[-x - 1];
				right.setText("Level: " + b.getLevel() + "   Solider Count: " + b.getCurrentSoldierCount());
				right.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
				right.setForeground(Color.red);
				east.add(right);
				repaint();
				validate();
			}
		}

		validate();
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
//		west.setBackground(new Color(255));
		if (!ups) {
			east.removeAll();
			revalidate();
			repaint();
		}
		if (!downs) {
			west.removeAll();
			revalidate();
			repaint();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public static ImageIcon stretch(String fileName, int width, int height) {
		ImageIcon ico = new ImageIcon(fileName);
		Image image = ico.getImage(); // transform it
		Image newimg = image.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH); // scale it the smooth way
		return new ImageIcon(newimg);
	}

}
