package gui;

import java.awt.Color;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = -2278436951424873713L;

	private JPanel myPanel;
	private JButton myButton;
	public MainWindow() {
		this.setSize(400, 300);
		this.setTitle("Stalin did nothing wrong");
		myPanel = new JPanel();
		myPanel.setBackground(Color.BLACK);
		this.setContentPane(myPanel);
		myPanel.setLayout(null);
		myButton = new JButton("ok");
		myButton.setBounds(150, 220, 70, 30);
		myButton.setVisible(true);
		myPanel.add(myButton);
		
	}

	public static void main(String[] args) {
		MainWindow mw = new MainWindow();
		mw.setLocationRelativeTo(null);
		mw.setVisible(true);

	}

}
