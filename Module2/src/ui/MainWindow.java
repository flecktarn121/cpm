package ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.net.URL;

import javax.help.HelpBroker;
import javax.help.HelpSet;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSeparator;
import javax.swing.border.EmptyBorder;

import org.jvnet.substance.SubstanceLookAndFeel;

import bussiness.BusinessException;
import bussiness.Login;
import bussiness.Party;
import bussiness.ProductCatalog;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainWindow extends JFrame {

	private static final String PRESENTATION = "name_11063346997627";
	private static final String BILL = "name_16374799304763";
	private static final String CATALOG = "name_6337481550517";
	/**
	 * 
	 */
	private static final long serialVersionUID = 57894284793740621L;
	private JPanel contentPane;
	private JPanel pnLogin;
	private JPanel pnTitle;
	private JLabel lblLetsGoParty;
	private JPanel pnLogo;
	private JPanel pnButtons;
	private JButton btnEnterAsGuest;
	private JButton btnLogIn;
	private JMenuBar menuBar;
	private JMenu mnParty;
	private JMenuItem mntmClear;
	private JSeparator separator;
	private JMenuItem mntmExit;
	private JMenu mnHelp;
	private JMenuItem mntmShowHelp;
	private JSeparator separator_1;
	private JMenuItem mntmAbout;
	private JMenu mnLanguage;
	private JRadioButtonMenuItem rdbtnmntmEnglish;
	private JRadioButtonMenuItem rdbtnmntmSpanich;
	private JPanel pnCatalog;
	private JPanel panel;
	private JButton btnProceed;
	private JButton btnCancel;
	private Party party;
	private Login login;
	private Catalog catalog;
	private ProductCatalog prodCatalog;
	private JMenuItem mntmMyParty;
	private JMenu mnUser;
	private JMenuItem mntmChangeUser;
	private JMenuItem mntmContinueAsGuest;
	private BillFormPane billFormPane;
	private JPanel panel_1;
	private JButton btnFinishPurchase;
	private JButton btnPrevious;
	private HelpBroker hb;
	private HelpSet hs;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JFrame.setDefaultLookAndFeelDecorated(true);
					JDialog.setDefaultLookAndFeelDecorated(true);
					SubstanceLookAndFeel.setSkin("org.jvnet.substance.skin.ModerateSkin");
					MainWindow frame = new MainWindow();
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
	public MainWindow() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent arg0) {
				exit();
			}
		});
		try {
			prodCatalog = new ProductCatalog();
			login = new Login();
			this.party = new Party(login);
		} catch (BusinessException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			System.exit(-1);
		}
		setTitle("Let´s go party!");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 847, 576);
		setJMenuBar(getMenuBar_1());
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new CardLayout(0, 0));
		contentPane.add(getPnLogin(), PRESENTATION);
		contentPane.add(getBillFormPane(), BILL);
		contentPane.add(getPnCatalog(), CATALOG);
		loadHelp();
	}
	
	private void loadHelp() {

		URL hsURL;
		HelpSet hs;

		try {
			File fichero = new File("help/Ayuda.hs");
			hsURL = fichero.toURI().toURL();
			hs = new HelpSet(null, hsURL);
		}

		catch (Exception e) {
			System.out.println("Ayuda no encontrada");
			return;
		}

		HelpBroker hb = hs.createHelpBroker();
		this.hb = hb;
		this.hs = hs;

		hb.enableHelpKey(getRootPane(), "welcome", hs);
		hb.enableHelpOnButton(getMntmShowHelp(), "welcome", hs);
		hb.enableHelp(getMntmShowHelp(), "welcome", hs);
	}

	private BillFormPane getBillFormPane() {
		if (billFormPane == null) {
			billFormPane = new BillFormPane(party, this);
			billFormPane.add(getPanel_1(), BorderLayout.SOUTH);
		}
		return billFormPane;
	}

	private JPanel getPnLogin() {
		if (pnLogin == null) {
			pnLogin = new JPanel();
			pnLogin.setLayout(new BorderLayout(0, 0));
			pnLogin.add(getPnTitle(), BorderLayout.NORTH);
			pnLogin.add(getPnLogo(), BorderLayout.CENTER);
			pnLogin.add(getPnButtons(), BorderLayout.SOUTH);
		}
		return pnLogin;
	}

	private JPanel getPnTitle() {
		if (pnTitle == null) {
			pnTitle = new JPanel();
			pnTitle.add(getLblLetsGoParty());
		}
		return pnTitle;
	}

	private JLabel getLblLetsGoParty() {
		if (lblLetsGoParty == null) {
			lblLetsGoParty = new JLabel("Let´s go Party!");
		}
		return lblLetsGoParty;
	}

	private JPanel getPnLogo() {
		if (pnLogo == null) {
			pnLogo = new JPanel();
		}
		return pnLogo;
	}

	private JPanel getPnButtons() {
		if (pnButtons == null) {
			pnButtons = new JPanel();
			pnButtons.add(getBtnEnterAsGuest());
			pnButtons.add(getBtnLogIn());
		}
		return pnButtons;
	}

	private JButton getBtnEnterAsGuest() {
		if (btnEnterAsGuest == null) {
			btnEnterAsGuest = new JButton("Enter as guest");
			btnEnterAsGuest.addActionListener(e -> {
				login.asGuest();
				((CardLayout) contentPane.getLayout()).show(contentPane, CATALOG);
				enableMenus(true);
			});
			btnEnterAsGuest.setToolTipText("Proceed as a guest user.");
			btnEnterAsGuest.setMnemonic('t');
		}
		return btnEnterAsGuest;
	}

	private JButton getBtnLogIn() {
		if (btnLogIn == null) {
			btnLogIn = new JButton("Enter as user");
			btnLogIn.addActionListener(e -> {
				new LoginDialog(login);
				((CardLayout) contentPane.getLayout()).show(contentPane, CATALOG);
				enableMenus(true);
			});
			btnLogIn.setMnemonic('r');
			btnLogIn.setToolTipText("Log in with your account");
		}
		return btnLogIn;
	}

	private JMenuBar getMenuBar_1() {
		if (menuBar == null) {
			menuBar = new JMenuBar();
			menuBar.add(getMnParty());
			menuBar.add(getMnUser());
			menuBar.add(getMnLanguage());
			menuBar.add(getMnHelp());
		}
		return menuBar;
	}

	private JMenu getMnParty() {
		if (mnParty == null) {
			mnParty = new JMenu("Party");
			mnParty.setMnemonic('P');
			mnParty.add(getMntmMyParty());
			mnParty.add(getMntmClear());
			mnParty.add(getSeparator());
			mnParty.add(getMntmExit());
		}
		return mnParty;
	}

	private JMenuItem getMntmClear() {
		if (mntmClear == null) {
			mntmClear = new JMenuItem("Clear");
			mntmClear.addActionListener(e -> {
				int response = JOptionPane.showConfirmDialog(null,
						"Are you sure?\nAll the content of the current party will be lost.", "Be careful",
						JOptionPane.WARNING_MESSAGE);
				if(response == JOptionPane.OK_OPTION) {
					party = new Party(login);
					getCatalog().setParty(party);
					getBillFormPane().setParty(party);
				}
			});
			mntmClear.setEnabled(false);
			mntmClear.setToolTipText("Empties the shopping cart and any other information of the current party");
			mntmClear.setMnemonic('C');
		}
		return mntmClear;
	}

	private JSeparator getSeparator() {
		if (separator == null) {
			separator = new JSeparator();
		}
		return separator;
	}

	private JMenuItem getMntmExit() {
		if (mntmExit == null) {
			mntmExit = new JMenuItem("Exit");
			mntmExit.addActionListener(e -> {
				int exit = JOptionPane.showConfirmDialog(this, "Are you really sure you want to exit the application?",
						"Warning", JOptionPane.OK_CANCEL_OPTION);
				if (exit == JOptionPane.OK_OPTION) {
					this.exit();
				}
			});
			mntmExit.setToolTipText("Exit the application");
			mntmExit.setMnemonic('x');
		}
		return mntmExit;
	}

	private JMenu getMnHelp() {
		if (mnHelp == null) {
			mnHelp = new JMenu("Help");
			mnHelp.setToolTipText("Help support options.");
			mnHelp.setMnemonic('H');
			mnHelp.add(getMntmShowHelp());
			mnHelp.add(getSeparator_1());
			mnHelp.add(getMntmAbout());
		}
		return mnHelp;
	}

	private JMenuItem getMntmShowHelp() {
		if (mntmShowHelp == null) {
			mntmShowHelp = new JMenuItem("Show help");
			mntmShowHelp.setToolTipText("Displays the help support");
			mntmShowHelp.setMnemonic('w');
		}
		return mntmShowHelp;
	}

	private JSeparator getSeparator_1() {
		if (separator_1 == null) {
			separator_1 = new JSeparator();
		}
		return separator_1;
	}

	private JMenuItem getMntmAbout() {
		if (mntmAbout == null) {
			mntmAbout = new JMenuItem("About");
			mntmAbout.setToolTipText("About this program.");
			mntmAbout.addActionListener(e -> {
				String message = "Application developed as a project for the course of Human Computer Interaction.\n";
				message += "Author: Ángel García Menéndez\n";
				message += "Contact: UO258654@uniovi.es";
				JOptionPane.showMessageDialog(null, message, "About", JOptionPane.INFORMATION_MESSAGE);
			});
			mntmAbout.setMnemonic('b');
		}
		return mntmAbout;
	}

	private JMenu getMnLanguage() {
		if (mnLanguage == null) {
			mnLanguage = new JMenu("Language");
			mnLanguage.setToolTipText("Select the language of the application.");
			mnLanguage.setMnemonic('L');
			mnLanguage.add(getRdbtnmntmEnglish());
			mnLanguage.add(getRdbtnmntmSpanich());
		}
		return mnLanguage;
	}

	private JRadioButtonMenuItem getRdbtnmntmEnglish() {
		if (rdbtnmntmEnglish == null) {
			rdbtnmntmEnglish = new JRadioButtonMenuItem("English");
			rdbtnmntmEnglish.setToolTipText("Select English as the language of the application.");
			rdbtnmntmEnglish.setMnemonic('g');
			rdbtnmntmEnglish.setSelected(true);
			rdbtnmntmEnglish.addActionListener(e->{
				getRdbtnmntmSpanich().setSelected(false);
			});
		}
		return rdbtnmntmEnglish;
	}

	private JRadioButtonMenuItem getRdbtnmntmSpanich() {
		if (rdbtnmntmSpanich == null) {
			rdbtnmntmSpanich = new JRadioButtonMenuItem("Spanish");
			rdbtnmntmSpanich.setToolTipText("Select spanish as the language of the application.");
			rdbtnmntmSpanich.setMnemonic('S');
			rdbtnmntmSpanich.addActionListener(e->{
				getRdbtnmntmEnglish().setSelected(false);
			});
		}
		return rdbtnmntmSpanich;
	}

	private JPanel getPnCatalog() {
		if (pnCatalog == null) {
			pnCatalog = new JPanel();
			pnCatalog.setLayout(new BorderLayout(0, 0));
			pnCatalog.add(getCatalog(), BorderLayout.CENTER);
			pnCatalog.add(getPanel(), BorderLayout.SOUTH);
		}
		return pnCatalog;
	}

	private Catalog getCatalog() {
		if (catalog == null) {
			catalog = new Catalog(prodCatalog.getProducts(), party);
		}

		return catalog;
	}

	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			FlowLayout flowLayout = (FlowLayout) panel.getLayout();
			flowLayout.setAlignment(FlowLayout.RIGHT);
			panel.add(getBtnCancel());
			panel.add(getBtnProceed());
		}
		return panel;
	}

	private JButton getBtnProceed() {
		if (btnProceed == null) {
			btnProceed = new JButton("Proceed");
			btnProceed.setToolTipText("Proceed with the current purchase.");
			btnProceed.setMnemonic('d');
			btnProceed.addActionListener(e -> {
				getBillFormPane().updateValues(party);
				((CardLayout) contentPane.getLayout()).show(contentPane, BILL);
				enableMenus(false);
			});
		}
		return btnProceed;
	}

	private JButton getBtnCancel() {
		if (btnCancel == null) {
			btnCancel = new JButton("Cancel");
			btnCancel.setToolTipText("Cancel the current party.");
			btnCancel.setMnemonic('l');
			btnCancel.addActionListener(e -> {
				int option = JOptionPane.showConfirmDialog(null,
						"Are you really sure you want to cancel?\nThe current party will be deleted.", "Warning",
						JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
				if (option == JOptionPane.OK_OPTION) {
					party = new Party(login);
					login.asGuest();
					enableMenus(false);
					((CardLayout) contentPane.getLayout()).show(contentPane, PRESENTATION);
				}
			});
		}
		return btnCancel;
	}

	private JMenuItem getMntmMyParty() {
		if (mntmMyParty == null) {
			mntmMyParty = new JMenuItem("My party");
			mntmMyParty.setToolTipText("Display the information of your current party.");
			mntmMyParty.setMnemonic('M');
			mntmMyParty.addActionListener(e -> {
				new PartyDialog(party).setVisible(true);
			});
			mntmMyParty.setEnabled(false);
		}
		return mntmMyParty;
	}

	private void exit() {
		login.close();
		System.exit(0);
	}

	private JMenu getMnUser() {
		if (mnUser == null) {
			mnUser = new JMenu("User");
			mnUser.setMnemonic('s');
			mnUser.add(getMntmChangeUser());
			mnUser.add(getMntmContinueAsGuest());
		}
		return mnUser;
	}

	private JMenuItem getMntmChangeUser() {
		if (mntmChangeUser == null) {
			mntmChangeUser = new JMenuItem("Change user...");
			mntmChangeUser.setMnemonic('g');
			mntmChangeUser.setToolTipText("Change the current user.");
			mntmChangeUser.setEnabled(false);
			mntmChangeUser.addActionListener(e -> {
				new LoginDialog(login);
			});
		}
		return mntmChangeUser;
	}

	private JMenuItem getMntmContinueAsGuest() {
		if (mntmContinueAsGuest == null) {
			mntmContinueAsGuest = new JMenuItem("Continue as guest");
			mntmContinueAsGuest.setMnemonic('n');
			mntmContinueAsGuest.setToolTipText("Change the user to guest.");
			mntmContinueAsGuest.setEnabled(false);
		}
		return mntmContinueAsGuest;
	}

	private void enableMenus(boolean areEnabled) {
		getMntmChangeUser().setEnabled(areEnabled);
		getMntmContinueAsGuest().setEnabled(areEnabled);
		getMntmMyParty().setEnabled(areEnabled);
		getMntmClear().setEnabled(areEnabled);
	}

	private JPanel getPanel_1() {
		if (panel_1 == null) {
			panel_1 = new JPanel();
			FlowLayout flowLayout = (FlowLayout) panel_1.getLayout();
			flowLayout.setAlignment(FlowLayout.RIGHT);
			panel_1.add(getBtnPrevious());
			panel_1.add(getBtnFinishPurchase());
		}
		return panel_1;
	}

	JButton getBtnFinishPurchase() {
		if (btnFinishPurchase == null) {
			btnFinishPurchase = new JButton("Finish purchase");
			btnFinishPurchase.setEnabled(false);
			btnFinishPurchase.setMnemonic('F');
			btnFinishPurchase.setToolTipText("Finalize the process. Your party will be ready.");
			btnFinishPurchase.addActionListener(e -> {
				getBillFormPane().restart();
				getCatalog().restart();
				this.restart();
				party = new Party(login);
			});
		}
		return btnFinishPurchase;
	}

	private void restart() {
		getBtnFinishPurchase().setEnabled(false);
		enableMenus(false);
		((CardLayout) contentPane.getLayout()).show(contentPane, PRESENTATION);

	}

	private JButton getBtnPrevious() {
		if (btnPrevious == null) {
			btnPrevious = new JButton("Previous");
			btnPrevious.setToolTipText("Go back to the cart.");
			btnPrevious.setMnemonic('e');
			btnPrevious.addActionListener(e -> {
				((CardLayout) contentPane.getLayout()).show(contentPane, CATALOG);
			});
		}
		return btnPrevious;
	}
}
