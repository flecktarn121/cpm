package ui;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.FlowLayout;

public class LoginDialog extends JDialog {
	private JPanel pnButtons;
	private JButton btnCancel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginDialog dialog = new LoginDialog();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	public LoginDialog() {
		setBounds(100, 100, 450, 300);
		getContentPane().add(getPnButtons(), BorderLayout.SOUTH);

	}

	private JPanel getPnButtons() {
		if (pnButtons == null) {
			pnButtons = new JPanel();
			FlowLayout flowLayout = (FlowLayout) pnButtons.getLayout();
			flowLayout.setAlignment(FlowLayout.RIGHT);
			pnButtons.add(getBtnCancel());
		}
		return pnButtons;
	}
	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton("Cancel");
		}
		return btnCancel;
	}
}
