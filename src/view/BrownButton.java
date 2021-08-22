package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

public class BrownButton extends JButton implements MouseListener {

	public BrownButton(String name, int fontSize) {
		// TODO Auto-generated constructor stub
		super(name);
		setBorderPainted(false);
		setFocusable(false);
		setBackground(Color.decode("#A0522D"));
		setForeground(Color.decode("#FFEBCD"));
		setFont(new Font(Font.SANS_SERIF, Font.BOLD, fontSize));
		addMouseListener(this);
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
		setForeground(Color.decode("#A0522D"));
		setBackground(Color.decode("#FFEBCD"));
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		setBackground(Color.decode("#A0522D"));
		setForeground(Color.decode("#FFEBCD"));
	}

}
