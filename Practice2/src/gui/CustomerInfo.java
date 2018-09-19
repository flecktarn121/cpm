package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.JRadioButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CustomerInfo extends JFrame {

	private JPanel contentPane;
	private JTextField txtName;
	private JTextField txtSurname;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CustomerInfo frame = new CustomerInfo();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CustomerInfo() {
		setTitle("Customer information");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 828, 507);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setToolTipText("hi, i am ur tooltip");
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblName = new JLabel("Name:");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblName.setBounds(31, 72, 54, 19);
		contentPane.add(lblName);
		
		txtName = new JTextField();
		lblName.setLabelFor(txtName);
		txtName.setBounds(81, 73, 86, 20);
		contentPane.add(txtName);
		txtName.setColumns(10);
		
		JLabel lblSurname = new JLabel("Surname:");
		lblSurname.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblSurname.setBounds(284, 76, 69, 14);
		contentPane.add(lblSurname);
		
		txtSurname = new JTextField();
		lblSurname.setLabelFor(txtSurname);
		txtSurname.setBounds(363, 73, 86, 20);
		contentPane.add(txtSurname);
		txtSurname.setColumns(10);
		
		JPanel pnGender = new JPanel();
		pnGender.setBackground(Color.WHITE);
		pnGender.setFont(new Font("Tahoma", Font.PLAIN, 15));
		pnGender.setBorder(new TitledBorder(null, "Gender:", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		pnGender.setBounds(31, 154, 177, 62);
		contentPane.add(pnGender);
		
		JRadioButton rdbtnMale = new JRadioButton("Male");
		rdbtnMale.setFont(new Font("Tahoma", Font.PLAIN, 15));
		rdbtnMale.setBackground(Color.WHITE);
		pnGender.add(rdbtnMale);
		
		JRadioButton rdbtnFemale = new JRadioButton("Female");
		rdbtnFemale.setFont(new Font("Tahoma", Font.PLAIN, 15));
		rdbtnFemale.setBackground(Color.WHITE);
		pnGender.add(rdbtnFemale);
	}
}
