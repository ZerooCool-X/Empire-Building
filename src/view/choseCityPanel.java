package view;

import java.awt.*;
import java.io.IOException;

import javax.swing.*;

public class choseCityPanel extends JPanel {

	public choseCityPanel(int width, int height, Frame parent) throws IOException {
		setLayout(new GridLayout(1,3));
		
		JButton cairo = new JButton();
		CityView.setIcon(cairo,"Cairo1","cairo.jpg", width / 3, height);
		cairo.addActionListener(parent);
		JButton rome = new JButton();
		CityView.setIcon(rome,"Rome1","rome2.jpg", width / 3, height);
		rome.addActionListener(parent);
		JButton sparta = new JButton();
		CityView.setIcon(sparta,"Sparta1","sparta.jpg", width / 3, height);
		sparta.addActionListener(parent);
		this.add(rome);
		this.add(cairo);
		this.add(sparta);
		this.setVisible(true);
		this.setSize(width, height);


	}

}
