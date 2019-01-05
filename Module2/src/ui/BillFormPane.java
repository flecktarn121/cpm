package ui;

import java.awt.BorderLayout;
import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.plaf.FileChooserUI;

import org.jdatepicker.JDatePicker;
import org.jdatepicker.UtilDateModel;

import bussiness.InvalidInputException;
import bussiness.Party;
import net.miginfocom.swing.MigLayout;
import util.Messages;

public class BillFormPane extends JPanel {
	public JPanel contentPane;
	public JPanel pnLogin;
	public JPanel pnTitle;
	public JLabel lblLetsGoParty;
	public JPanel pnLogo;
	public JPanel pnButtons;
	public JButton btnEnterAsGuest;
	public JButton btnLogIn;
	public JMenuBar menuBar;
	public JMenu mnParty;
	public JMenuItem mntmClear;
	public JSeparator separator;
	public JMenuItem mntmExit;
	public JMenu mnHelp;
	public JMenuItem mntmShowHelp;
	public JSeparator separator_1;
	public JMenuItem mntmAbout;
	public JMenu mnLanguage;
	public JRadioButtonMenuItem rdbtnmntmEnglish;
	public JRadioButtonMenuItem rdbtnmntmSpanich;
	public JSplitPane splitPane;
	public JPanel pnForm;
	public JLabel lblName;
	public JLabel lblSurname;
	public JLabel lblNif;
	public JTextField txtName;
	public JTextField txtSurname;
	public JTextField txtNIF;
	public JLabel lblAttendants;
	public JSpinner spnAttendants;
	public JLabel lblDate;
	public JLabel lblHour;
	public JPanel pnBillForm;
	public JPanel pnCatalog;
	public JPanel pnFilters;
	private JDatePicker datePicker;
	private JButton btnPrintBill;
	private JScrollPane spBill;
	private JTextArea taBill;
	private Party party;
	private JLabel lblComments;
	private JTextArea taComments;
	private JSpinner timeSpinner;
	private JPanel panel;
	private JButton btnSaveBill;
	private JLabel lblTelephone;
	private JTextField txtPhone;
	private MainWindow mw;

	public BillFormPane(Party party, MainWindow mw) {
		this.party = party;
		this.mw = mw;
		this.setLayout(new BorderLayout(0, 0));
		this.add(getSplitPane(), BorderLayout.CENTER);
	}

	private JPanel getPnForm() {
		if (pnForm == null) {
			pnForm = new JPanel();
			pnForm.setLayout(new MigLayout(Messages.getString("BillFormPane.0"), "[][grow]", "[][][][][][][][grow][]")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
			pnForm.add(getLblName(), "cell 0 0,alignx trailing"); //$NON-NLS-1$
			pnForm.add(getTxtName(), "cell 1 0,growx"); //$NON-NLS-1$
			pnForm.add(getLblSurname(), "cell 0 1,alignx trailing"); //$NON-NLS-1$
			pnForm.add(getTxtSurname(), "cell 1 1,growx"); //$NON-NLS-1$
			pnForm.add(getLblNif(), "cell 0 2,alignx trailing"); //$NON-NLS-1$
			pnForm.add(getTextField_1(), "cell 1 2,growx"); //$NON-NLS-1$
			pnForm.add(getLblTelephone(), "cell 0 3,alignx trailing"); //$NON-NLS-1$
			pnForm.add(getTxtPhone(), "cell 1 3,growx"); //$NON-NLS-1$
			pnForm.add(getLblAttendants(), "cell 0 4"); //$NON-NLS-1$
			pnForm.add(getSpnAttendants(), "cell 1 4,alignx left"); //$NON-NLS-1$
			pnForm.add(getLblDate(), "cell 0 5,alignx right"); //$NON-NLS-1$
			pnForm.add(getDatePicker(), "cell 1 5"); //$NON-NLS-1$
			pnForm.add(getLblHour(), "cell 0 6,alignx right"); //$NON-NLS-1$
			pnForm.add(getTimeSpinner(), "cell 1 6"); //$NON-NLS-1$
			pnForm.add(getLblComments(), "cell 0 7,aligny top"); //$NON-NLS-1$
			pnForm.add(getTaComments(), "cell 1 7,grow"); //$NON-NLS-1$
			pnForm.add(getPanel(), Messages.getString("BillFormPane.19")); //$NON-NLS-1$
		}
		return pnForm;
	}

	private JSpinner getTimeSpinner() {
		if (timeSpinner == null) {
			timeSpinner = new JSpinner(new SpinnerDateModel());
			timeSpinner.setToolTipText(Messages.getString("BillFormPane.20")); //$NON-NLS-1$
			JSpinner.DateEditor timeEditor = new JSpinner.DateEditor(timeSpinner, Messages.getString("BillFormPane.21")); //$NON-NLS-1$
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

	private JDatePicker getDatePicker() {
		if (datePicker == null) {
			Date date = party.getDateAndTime();
			UtilDateModel model = new UtilDateModel();
			if (date != null) {
				model = new UtilDateModel(date);
			}
			datePicker = new JDatePicker(model);
			datePicker.setToolTipText(Messages.getString("BillFormPane.22")); //$NON-NLS-1$
		}

		return datePicker;
	}

	private JLabel getLblName() {
		if (lblName == null) {
			lblName = new JLabel(Messages.getString("BillFormPane.23")); //$NON-NLS-1$
			lblName.setToolTipText(Messages.getString("BillFormPane.24")); //$NON-NLS-1$
			lblName.setDisplayedMnemonic('N');
			lblName.setLabelFor(getTxtName());
		}
		return lblName;
	}

	private JLabel getLblSurname() {
		if (lblSurname == null) {
			lblSurname = new JLabel(Messages.getString("BillFormPane.25")); //$NON-NLS-1$
			lblSurname.setToolTipText(Messages.getString("BillFormPane.26")); //$NON-NLS-1$
			lblSurname.setLabelFor(getTxtSurname());
			lblSurname.setDisplayedMnemonic('S');
		}
		return lblSurname;
	}

	private JLabel getLblNif() {
		if (lblNif == null) {
			lblNif = new JLabel(Messages.getString("BillFormPane.27")); //$NON-NLS-1$
			lblNif.setToolTipText(Messages.getString("BillFormPane.28")); //$NON-NLS-1$
			lblNif.setDisplayedMnemonic('F');
			lblNif.setLabelFor(getTextField_1());
		}
		return lblNif;
	}

	private JTextField getTxtName() {
		if (txtName == null) {
			txtName = new JTextField();
			txtName.setToolTipText(Messages.getString("BillFormPane.29")); //$NON-NLS-1$
			txtName.setColumns(10);
		}
		return txtName;
	}

	private JTextField getTxtSurname() {
		if (txtSurname == null) {
			txtSurname = new JTextField();
			txtSurname.setToolTipText(Messages.getString("BillFormPane.30")); //$NON-NLS-1$
			txtSurname.setColumns(10);
		}
		return txtSurname;
	}

	private JTextField getTextField_1() {
		if (txtNIF == null) {
			txtNIF = new JTextField();
			txtNIF.setToolTipText(Messages.getString("BillFormPane.31")); //$NON-NLS-1$
			txtNIF.setColumns(10);
		}
		return txtNIF;
	}

	private JLabel getLblAttendants() {
		if (lblAttendants == null) {
			lblAttendants = new JLabel(Messages.getString("BillFormPane.32")); //$NON-NLS-1$
			lblAttendants.setToolTipText(Messages.getString("BillFormPane.33")); //$NON-NLS-1$
			lblAttendants.setDisplayedMnemonic('A');
			lblAttendants.setLabelFor(getSpnAttendants());
		}
		return lblAttendants;
	}

	private JSpinner getSpnAttendants() {
		if (spnAttendants == null) {
			spnAttendants = new JSpinner();
			spnAttendants.setToolTipText(Messages.getString("BillFormPane.34")); //$NON-NLS-1$
			spnAttendants.setModel(new SpinnerNumberModel(new Integer(1), new Integer(1), null, new Integer(1)));
			spnAttendants.setValue(party.getNumberOfPeople());
		}
		return spnAttendants;
	}

	private JLabel getLblDate() {
		if (lblDate == null) {
			lblDate = new JLabel(Messages.getString("BillFormPane.35")); //$NON-NLS-1$
			lblDate.setDisplayedMnemonic('D');
			lblDate.setLabelFor(getDatePicker());
			lblDate.setToolTipText(Messages.getString("BillFormPane.36")); //$NON-NLS-1$
		}
		return lblDate;
	}

	private JLabel getLblHour() {
		if (lblHour == null) {
			lblHour = new JLabel(Messages.getString("BillFormPane.37")); //$NON-NLS-1$
			lblHour.setToolTipText(Messages.getString("BillFormPane.38")); //$NON-NLS-1$
			lblHour.setLabelFor(getTimeSpinner());
			lblHour.setDisplayedMnemonic('o');
		}
		return lblHour;
	}

	private JSplitPane getSplitPane() {
		if (splitPane == null) {
			splitPane = new JSplitPane();
			splitPane.setLeftComponent(getPnForm());
			splitPane.setRightComponent(getSpBill());
		}
		return splitPane;
	}

	private JButton getBtnPrintBill() {
		if (btnPrintBill == null) {
			btnPrintBill = new JButton(Messages.getString("BillFormPane.39")); //$NON-NLS-1$
			btnPrintBill.setToolTipText(Messages.getString("BillFormPane.40")); //$NON-NLS-1$
			btnPrintBill.setMnemonic('P');
			btnPrintBill.addActionListener(e -> {
				try {
					JOptionPane.showConfirmDialog(null, Messages.getString("BillFormPane.41"), Messages.getString("BillFormPane.42"), //$NON-NLS-1$ //$NON-NLS-2$
							JOptionPane.WARNING_MESSAGE);
					String name = getTxtName().getText();
					String surname = getTxtSurname().getText();
					String NIF = getTextField_1().getText();
					Date date = ((Date) getDatePicker().getModel().getValue());
					LocalTime time = getTime();
					party.setCustomerName(name);
					party.setCustomerSurname(surname);
					party.setCustomerNIF(NIF);
					party.setComment(getTaComments().getText());
					party.setDateAndTime(date);
					party.setTime(time);
					String bill = party.printRecipt();
					getTaBill().setText(bill);
					getBtnSaveBill().setEnabled(true);
					setEnableForm(false);
					mw.getBtnFinishPurchase().setEnabled(true);
				} catch (InvalidInputException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage(), Messages.getString("BillFormPane.43"), JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$
				}
			});
		}
		return btnPrintBill;
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

	private JScrollPane getSpBill() {
		if (spBill == null) {
			spBill = new JScrollPane();
			spBill.setViewportView(getTaBill());
		}
		return spBill;
	}

	private JTextArea getTaBill() {
		if (taBill == null) {
			taBill = new JTextArea();
			taBill.setToolTipText(Messages.getString("BillFormPane.44")); //$NON-NLS-1$
			taBill.setLineWrap(true);
			taBill.setEditable(false);
		}
		return taBill;
	}

	private JLabel getLblComments() {
		if (lblComments == null) {
			lblComments = new JLabel(Messages.getString("BillFormPane.45")); //$NON-NLS-1$
			lblComments.setToolTipText(Messages.getString("BillFormPane.46")); //$NON-NLS-1$
			lblComments.setLabelFor(getTaComments());
			lblComments.setDisplayedMnemonic('C');
		}
		return lblComments;
	}

	private JTextArea getTaComments() {
		if (taComments == null) {
			taComments = new JTextArea();
			taComments.setText(party.getComment());
		}
		return taComments;
	}

	void updateValues(Party party) {
		getSpnAttendants().setValue(party.getNumberOfPeople());
		LocalTime time = party.getTime();
		if (time == null) {
			time = LocalTime.of(0, 0, 0);
		}
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, time.getHour());
		calendar.set(Calendar.MINUTE, time.getMinute());
		calendar.set(Calendar.SECOND, time.getSecond());
		getTimeSpinner().setValue(calendar.getTime());
		getTaComments().setText(party.getComment());
	}

	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.add(getBtnSaveBill());
			panel.add(getBtnPrintBill());
		}
		return panel;
	}

	private JButton getBtnSaveBill() {
		if (btnSaveBill == null) {
			btnSaveBill = new JButton(Messages.getString("BillFormPane.47")); //$NON-NLS-1$
			btnSaveBill.setMnemonic('v');
			btnSaveBill.setToolTipText(Messages.getString("BillFormPane.48")); //$NON-NLS-1$
			btnSaveBill.setEnabled(false);
			btnSaveBill.addActionListener(e -> {
				enTodoElFilechuser();

			});
		}
		return btnSaveBill;
	}

	private void setEnableForm(boolean isEnabled) {
		getTaComments().setEnabled(isEnabled);
		getTxtName().setEnabled(isEnabled);
		getTxtSurname().setEnabled(isEnabled);
		getTextField_1().setEnabled(isEnabled);
		getTxtPhone().setEnabled(isEnabled);
		getSpnAttendants().setEnabled(isEnabled);
		getDatePicker().setEnabled(isEnabled);
		getTimeSpinner().setEnabled(isEnabled);
		getBtnPrintBill().setEnabled(isEnabled);
	}

	private void enTodoElFilechuser() {
		JFileChooser fc = new JFileChooser();
		fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnVal = fc.showSaveDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File f = fc.getSelectedFile();
			String name = f.getAbsolutePath();
			String billName = party.getCustomerNIF()
					+ party.getDateAndTime().toString().replaceAll("\\s+", "").replaceAll(":", "") + ".dat"; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$
			String file = (name + File.separatorChar + billName);
			party.saveBill(file, getTaBill().getText());
		}
	}

	private JLabel getLblTelephone() {
		if (lblTelephone == null) {
			lblTelephone = new JLabel(Messages.getString("BillFormPane.54")); //$NON-NLS-1$
			lblTelephone.setToolTipText(Messages.getString("BillFormPane.55")); //$NON-NLS-1$
			lblTelephone.setDisplayedMnemonic('T');
		}
		return lblTelephone;
	}

	private JTextField getTxtPhone() {
		if (txtPhone == null) {
			txtPhone = new JTextField();
			txtPhone.setToolTipText(Messages.getString("BillFormPane.56")); //$NON-NLS-1$
			txtPhone.setColumns(10);
		}
		return txtPhone;
	}

	public void restart() {
		setEnableForm(true);
		getTaBill().setText(Messages.getString("BillFormPane.4")); //$NON-NLS-1$
		getTxtName().setText(Messages.getString("BillFormPane.3")); //$NON-NLS-1$
		getTxtSurname().setText(Messages.getString("BillFormPane.1")); //$NON-NLS-1$
		getTextField_1().setText(Messages.getString("BillFormPane.2")); //$NON-NLS-1$
		getTxtPhone().setText(Messages.getString("BillFormPane.61")); //$NON-NLS-1$
		getTaComments().setText(Messages.getString("BillFormPane.62")); //$NON-NLS-1$
		getBtnSaveBill().setEnabled(false);
		getBtnPrintBill().setEnabled(true);
		mw.getBtnFinishPurchase().setEnabled(false);

	}

	public void setParty(Party party2) {
		if (party2 == null) {
			throw new IllegalArgumentException("The party cannot be null"); //$NON-NLS-1$
		}
		this.party = party2;

	}
}