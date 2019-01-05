package ui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import bussiness.BusinessException;
import bussiness.Login;
import net.miginfocom.swing.MigLayout;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class LoginDialog extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5873790838679534268L;
	private JPanel pnButtons;
	private JButton btnCancel;
	private JButton btnRegister;
	private JButton btnLogIn;
	private JPanel pnCenter;
	private JLabel lblUsername;
	private JTextField txtUser;
	private JLabel lblPassword;
	private JPasswordField pfUserPassword;
	private Login login;

	/**
	 * Create the dialog.
	 */
	public LoginDialog(Login login) {
		setModal(true);
		setTitle("User manager");
		this.login = login;
		setResizable(false);
		setBounds(100, 100, 451, 120);
		getContentPane().add(getPnButtons(), BorderLayout.SOUTH);
		getContentPane().add(getPnCenter(), BorderLayout.CENTER);
		this.setVisible(true);

	}

	private JPanel getPnButtons() {
		if (pnButtons == null) {
			pnButtons = new JPanel();
			FlowLayout flowLayout = (FlowLayout) pnButtons.getLayout();
			flowLayout.setAlignment(FlowLayout.RIGHT);
			pnButtons.add(getBtnLogIn());
			pnButtons.add(getBtnRegister());
			pnButtons.add(getBtnCancel());
		}
		return pnButtons;
	}

	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton("Cancel");
			btnCancel.addActionListener(e -> this.dispose());
			btnCancel.setToolTipText("Cancer the user´s menu.");
		}
		return btnCancel;
	}

	private JButton getBtnRegister() {
		if (btnRegister == null) {
			btnRegister = new JButton("Register");
			btnRegister.setEnabled(false);
			btnRegister.setToolTipText("Register the specified user with the specified password.");
			btnRegister.setMnemonic('R');
			btnRegister.addActionListener(e -> register());
		}
		return btnRegister;
	}

	private JButton getBtnLogIn() {
		if (btnLogIn == null) {
			btnLogIn = new JButton("Log In");
			btnLogIn.setEnabled(false);
			btnLogIn.setToolTipText("Log in with the specified user and password.");
			btnLogIn.setMnemonic('L');
			btnLogIn.addActionListener(e -> logIn());
		}
		return btnLogIn;
	}

	private JPanel getPnCenter() {
		if (pnCenter == null) {
			pnCenter = new JPanel(new MigLayout("", "[][grow]", "[][]"));
			pnCenter.add(getLblUsername(), "cell 0 0,alignx trailing");
			pnCenter.add(getTxtUser(), "cell 1 0,growx");
			pnCenter.add(getLblPassword(), "cell 0 1,alignx trailing");
			pnCenter.add(getPfUserPassword(), "cell 1 1,growx");
		}
		return pnCenter;
	}

	private JLabel getLblUsername() {
		if (lblUsername == null) {
			lblUsername = new JLabel("Username:");
			lblUsername.setLabelFor(getTxtUser());
			lblUsername.setDisplayedMnemonic('s');
		}
		return lblUsername;
	}

	private JTextField getTxtUser() {
		if (txtUser == null) {
			txtUser = new JTextField();
			txtUser.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent arg0) {
					if (!txtUser.getText().isEmpty()) {
						getBtnLogIn().setEnabled(true);
						getBtnRegister().setEnabled(true);
					}
				}
			});
			txtUser.setColumns(10);
		}
		return txtUser;
	}

	private JLabel getLblPassword() {
		if (lblPassword == null) {
			lblPassword = new JLabel("Password:");
			lblPassword.setDisplayedMnemonic('P');
		}
		return lblPassword;
	}

	private JPasswordField getPfUserPassword() {
		if (pfUserPassword == null) {
			pfUserPassword = new JPasswordField();
			pfUserPassword.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent arg0) {
					if (pfUserPassword.getPassword().length > 0) {
						getBtnLogIn().setEnabled(true);
						getBtnRegister().setEnabled(true);
					}
				}
			});
		}
		return pfUserPassword;
	}

	private void logIn() {
		String username = this.getTxtUser().getText();
		String password = new String(this.getPfUserPassword().getPassword());
		if (username.isEmpty() || password.isEmpty()) {
			JOptionPane.showMessageDialog(this, "You must fill both the user and the password.", "Error",
					JOptionPane.ERROR_MESSAGE);
		} else {
			try {
				this.login.logIn(username, password);
				this.dispose();
			} catch (BusinessException e) {
				JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void register() {
		String username = this.getTxtUser().getText();
		String password = new String(this.getPfUserPassword().getPassword());
		if (username.isEmpty() || password.isEmpty()) {
			JOptionPane.showMessageDialog(this, "You must fill both the user and the password.", "Error",
					JOptionPane.ERROR_MESSAGE);
		} else {
			try {
				this.login.registerUser(username, password);
				this.dispose();
			} catch (BusinessException e) {
				JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
