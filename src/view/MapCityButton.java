package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.*;
import javax.swing.JButton;
import javax.swing.JLabel;

public class MapCityButton extends JButton implements MouseListener {
	JLabel name;
	File sound = null;
	static boolean played = false;

	public MapCityButton(String n, String Sname) {
		this.name = new JLabel(n);
		name.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
		this.addMouseListener(this);
		this.name.setVisible(false);
		name.setForeground(Color.decode("#FFEBCD"));
		if (!Sname.equals("")) {
			sound = new File(Sname);
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
		this.name.setVisible(true);
		if (sound != null && !played) {
			try {
				played = true;
//				AudioInputStream audioStream = AudioSystem.getAudioInputStream(sound);
//				Clip clip = AudioSystem.getClip();
//				clip.open(audioStream);
//				clip.start();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		this.name.setVisible(false);

	}

}
