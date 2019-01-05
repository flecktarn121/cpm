package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;

import org.jdatepicker.JDatePicker;
import org.jdatepicker.UtilDateModel;

import bussiness.InvalidInputException;
import bussiness.Login;
import bussiness.Order;
import bussiness.Party;
import net.miginfocom.swing.MigLayout;

public class PartyDialog extends JDialog {
	private JSplitPane splitPane;
	private JPanel pnButtons;
	private JButton btnClose;
	private JPanel pnSummary;
	private JLabel lblPartySummary;
	private JLabel lblUser;
	private JLabel lblAttendants;
	private JLabel lblDate;
	private JLabel lblTime;
	private JLabel lblComments;
	private JLabel lblTotalPrice;
	private JTextField txtUser;
	private JSpinner spnAttendants;
	private JTextArea taComments;
	private JTextField txtPrice;
	private JPanel pnCart;
	private JScrollPane spTable;
	private JPanel pnRemove;
	private JTable cart;
	private JButton btnRemove;
	private JButton btnClearCart;
	private Party party;
	private JButton btnSave;
	private JDatePicker datePicker;
	private JSpinner timeSpinner;
	private JLabel lblBasePrice;
	private JTextField txtBasePrice;
	private JLabel lblTotalDiscount;
	private JTextField txtDiscount;

	/**
	 * Create the dialog.
	 */
	public PartyDialog(Party party) {
		this.party = party;
		setBounds(100, 100, 705, 493);
		getContentPane().add(getSplitPane(), BorderLayout.CENTER);
		getContentPane().add(getPnButtons(), BorderLayout.SOUTH);

	}

	private JSplitPane getSplitPane() {
		if (splitPane == null) {
			splitPane = new JSplitPane();
			splitPane.setLeftComponent(getPnSummary());
			splitPane.setRightComponent(getPnCart());
		}
		return splitPane;
	}

	private JPanel getPnButtons() {
		if (pnButtons == null) {
			pnButtons = new JPanel();
			FlowLayout flowLayout = (FlowLayout) pnButtons.getLayout();
			flowLayout.setAlignment(FlowLayout.RIGHT);
			pnButtons.add(getBtnSave());
			pnButtons.add(getBtnClose());
		}
		return pnButtons;
	}

	private JButton getBtnClose() {
		if (btnClose == null) {
			btnClose = new JButton("Close");
			btnClose.addActionListener(e -> dispose());
		}
		return btnClose;
	}

	private JPanel getPnSummary() {
		if (pnSummary == null) {
			pnSummary = new JPanel();
			pnSummary.setLayout(new MigLayout("", "[][grow]", "[][][][][][][grow][][][]"));
			pnSummary.add(getLblPartySummary(), "cell 0 0");
			pnSummary.add(getLblUser(), "cell 0 1,alignx leading");
			pnSummary.add(getTxtUser(), "cell 1 1,growx");
			pnSummary.add(getLblAttendants(), "cell 0 2");
			pnSummary.add(getSpnAttendants(), "cell 1 2");
			pnSummary.add(getLblDate(), "cell 0 3");
			pnSummary.add(getDatePicker(), "cell 1 3");
			pnSummary.add(getLblTime(), "cell 0 4");
			pnSummary.add(getTimeSpinner(), "cell 1 4");
			pnSummary.add(getLblComments(), "cell 0 5 2 1,aligny top");
			pnSummary.add(getTaComments(), "cell 0 6 2 1,grow");
			pnSummary.add(getLblBasePrice(), "cell 0 7,alignx trailing");
			pnSummary.add(getTxtBasePrice(), "cell 1 7,growx");
			pnSummary.add(getLblTotalDiscount(), "cell 0 8,alignx trailing");
			pnSummary.add(getTxtDiscount(), "cell 1 8,alignx center");
			pnSummary.add(getLblTotalPrice(), "cell 0 9,alignx trailing");
			pnSummary.add(getTxtPrice(), "cell 1 9,growx");
		}
		return pnSummary;
	}

	private JDatePicker getDatePicker() {
		if (datePicker == null) {
			Date date = party.getDateAndTime();
			UtilDateModel model = new UtilDateModel();
			if (date != null) {
				model = new UtilDateModel(date);
			}

			datePicker = new JDatePicker(model);
		}

		return datePicker;
	}

	private JLabel getLblPartySummary() {
		if (lblPartySummary == null) {
			lblPartySummary = new JLabel("Party Summary:");
		}
		return lblPartySummary;
	}

	private JLabel getLblUser() {
		if (lblUser == null) {
			lblUser = new JLabel("User:");
		}
		return lblUser;
	}

	private JLabel getLblAttendants() {
		if (lblAttendants == null) {
			lblAttendants = new JLabel("Attendants:");
		}
		return lblAttendants;
	}

	private JLabel getLblDate() {
		if (lblDate == null) {
			lblDate = new JLabel("Date:");
		}
		return lblDate;
	}

	private JLabel getLblTime() {
		if (lblTime == null) {
			lblTime = new JLabel("Time:");
		}
		return lblTime;
	}

	private JLabel getLblComments() {
		if (lblComments == null) {
			lblComments = new JLabel("Comments:");
		}
		return lblComments;
	}

	private JLabel getLblTotalPrice() {
		if (lblTotalPrice == null) {
			lblTotalPrice = new JLabel("Total price:");
		}
		return lblTotalPrice;
	}

	private JTextField getTxtUser() {
		if (txtUser == null) {
			txtUser = new JTextField();
			txtUser.setEditable(false);
			txtUser.setColumns(10);
			String user = party.getLogin().getCurrentUser();
			if (user.equals(Login.NO_USER)) {
				txtUser.setText("Guest");
			} else {
				txtUser.setText(user);
			}
		}
		return txtUser;
	}

	private JSpinner getSpnAttendants() {
		if (spnAttendants == null) {
			spnAttendants = new JSpinner();
			spnAttendants.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
			spnAttendants.setValue(party.getNumberOfPeople());
		}
		return spnAttendants;
	}

	private JTextArea getTaComments() {
		if (taComments == null) {
			taComments = new JTextArea();
			taComments.setWrapStyleWord(true);
			taComments.setText(party.getComment());
		}
		return taComments;
	}

	private JTextField getTxtPrice() {
		if (txtPrice == null) {
			txtPrice = new JTextField();
			txtPrice.setEditable(false);
			txtPrice.setColumns(10);
			txtPrice.setText(String.valueOf(party.getTotalPrice() - party.getTotalDiscount()));
		}
		return txtPrice;
	}

	private JPanel getPnCart() {
		if (pnCart == null) {
			pnCart = new JPanel();
			pnCart.setLayout(new BorderLayout(0, 0));
			pnCart.add(getSpTable(), BorderLayout.CENTER);
			pnCart.add(getPnRemove(), BorderLayout.SOUTH);
		}
		return pnCart;
	}

	private JScrollPane getSpTable() {
		if (spTable == null) {
			spTable = new JScrollPane();
			spTable.setViewportView(getCart());
		}
		return spTable;
	}

	private JPanel getPnRemove() {
		if (pnRemove == null) {
			pnRemove = new JPanel();
			pnRemove.add(getBtnRemove());
			pnRemove.add(getBtnClearCart());
		}
		return pnRemove;
	}

	private JTable getCart() {
		if (cart == null) {
			cart = new JTable() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 7120152682454612770L;

				@Override
				public boolean isCellEditable(int arg0, int arg1) {
					return false;
				}
			};

			DefaultTableModel model = new DefaultTableModel(new String[] { "Name", "Units" }, 0);
			cart.setModel(model);
			cart.getSelectionModel().addListSelectionListener(e -> {
				if (!cart.getSelectionModel().isSelectionEmpty()) {
					getBtnRemove().setEnabled(true);
				}
			});
			for (Order order : party.getItems()) {
				model.addRow(new String[] { order.getName(), String.valueOf(order.getUnits()) });
			}
		}
		return cart;
	}

	private JButton getBtnRemove() {
		if (btnRemove == null) {
			btnRemove = new JButton("Remove");
			btnRemove.setEnabled(false);
			btnRemove.addActionListener(e -> {
				int index = getCart().getSelectedRow();
				if (index >= 0) {
					Order order = party.getOrder(index);
					party.removeOrder(order);
					((DefaultTableModel) getCart().getModel()).removeRow(index);
					updatePrices();
				}else {
					btnRemove.setEnabled(false);
				}
			});
		}
		return btnRemove;
	}

	private void updatePrices() {
		getTxtPrice().setText(String.valueOf(party.getTotalPrice() - party.getTotalDiscount()));
		getTxtBasePrice().setText(String.valueOf(party.getTotalPrice()));
		getTxtDiscount().setText(String.valueOf(party.getTotalDiscount()));

	}

	private JButton getBtnClearCart() {
		if (btnClearCart == null) {
			btnClearCart = new JButton("Clear cart");
			if (party.getItems().isEmpty()) {
				btnClearCart.setEnabled(false);
			}
			btnClearCart.addActionListener(e -> {
				party.clearOrders();
				DefaultTableModel model = new DefaultTableModel(new String[] { "Name", "Units" }, 0);
				getCart().setModel(model);
				updatePrices();
			});

		}
		return btnClearCart;
	}

	private JButton getBtnSave() {
		if (btnSave == null) {
			btnSave = new JButton("Save");
			btnSave.addActionListener(e -> save());
		}
		return btnSave;
	}

	private void save() {
		try {
			party.setNumberOfPeople(Integer.parseInt(getSpnAttendants().getValue().toString()));
			party.setComment(getTaComments().getText());
			Date date = (Date) getDatePicker().getModel().getValue();
			if (date != null) {
				party.setDateAndTime(date);
			}
			party.setTime(getTime());
			dispose();
		} catch (InvalidInputException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	private LocalTime getTime() {
		Date time = (Date) getTimeSpinner().getValue();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		int second = calendar.get(Calendar.SECOND);
		return LocalTime.of(hour, minute, second);
	}

	private JSpinner getTimeSpinner() {
		if (timeSpinner == null) {
			timeSpinner = new JSpinner(new SpinnerDateModel());
			JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, "HH:mm:ss");
			timeSpinner.setEditor(timeEditor);
			LocalTime time = party.getTime();
			if (time == null) {
				time = LocalTime.of(0, 0, 0);
			}
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.HOUR_OF_DAY, time.getHour());
			calendar.set(Calendar.MINUTE, time.getMinute());
			calendar.set(Calendar.SECOND, time.getSecond());
			timeSpinner.setValue(calendar.getTime());
		}
		return timeSpinner;
	}

	private JLabel getLblBasePrice() {
		if (lblBasePrice == null) {
			lblBasePrice = new JLabel("Base price:");
		}
		return lblBasePrice;
	}

	private JTextField getTxtBasePrice() {
		if (txtBasePrice == null) {
			txtBasePrice = new JTextField();
			txtBasePrice.setText(String.valueOf(party.getTotalPrice()));
			txtBasePrice.setEditable(false);
			txtBasePrice.setColumns(10);
		}
		return txtBasePrice;
	}

	private JLabel getLblTotalDiscount() {
		if (lblTotalDiscount == null) {
			lblTotalDiscount = new JLabel("Total discount:");
		}
		return lblTotalDiscount;
	}

	private JTextField getTxtDiscount() {
		if (txtDiscount == null) {
			txtDiscount = new JTextField();
			txtDiscount.setText(String.valueOf(party.getTotalDiscount()));
			txtDiscount.setEditable(false);
			txtDiscount.setColumns(10);
		}
		return txtDiscount;
	}
}
