package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;

import bussiness.BusinessException;
import bussiness.Order;
import bussiness.Party;
import model.Product;
import net.miginfocom.swing.MigLayout;
import ui.filter.BaseFilter;
import ui.filter.Filter;
import ui.filter.FilterByPrice;
import ui.filter.FilterByType;
import util.TypeConversor;
import javax.swing.JCheckBox;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Catalog extends JPanel {
	private JPanel pnFilters;
	private JLabel lblSearch;
	private JTextField txtSearch;
	private JLabel lblType;
	private JComboBox<String> cbTypes;
	private JScrollPane spCatalog;
	private JTable catalog;
	private List<Product> products;
	private List<Product> originalProducts;
	private JPanel pnLeft;
	private JLabel lblImage;
	private JPanel pnUnits;
	private JLabel lblUnits;
	private JSpinner spnUnits;
	private JLabel lblPrice;
	private JTextField txtPrice;
	private JScrollPane spDescription;
	private JTextArea taDescription;
	private Party party;
	private JButton btnBuy;
	private JSlider slider;
	private JSpinner spnPrice;
	private JPanel pnFilterButtons;
	private JButton btnApplyFilters;
	private JButton btnResetfilters;
	private JLabel lblPrice_1;
	private JPanel pnAux;
	private JCheckBox chckbxNewCheckBox;

	/**
	 * Create the panel.
	 */
	public Catalog(List<Product> products, Party party) {
		this.products = products;
		this.originalProducts = new ArrayList<Product>(products);
		this.party = party;
		setLayout(new BorderLayout(0, 0));
		add(getPnFilters(), BorderLayout.NORTH);
		add(getSpCatalog(), BorderLayout.CENTER);
		add(getPnLeft(), BorderLayout.WEST);
		add(getSpDescription(), BorderLayout.SOUTH);
		fillCatalog(this.products);

	}

	void setParty(Party party) {
		if (party == null) {
			throw new IllegalArgumentException("The party cannot be null");
		}
		this.party = party;
	}

	private JPanel getPnFilters() {
		if (pnFilters == null) {
			pnFilters = new JPanel();
			pnFilters.setLayout(new MigLayout("", "[][][grow 35][][][][grow][grow]", "[grow]"));
			pnFilters.add(getLblSearch(), "cell 0 0,alignx trailing,aligny top");
			pnFilters.add(getTxtSearch(), "cell 1 0,alignx left,aligny top");
			pnFilters.add(getLblPrice_1(), "cell 2 0,aligny top");
			pnFilters.add(getSlider(), "cell 3 0");
			pnFilters.add(getLblType(), "cell 5 0,alignx trailing,aligny top");
			pnFilters.add(getCbTypes(), "cell 6 0,aligny top");
			pnFilters.add(getPnFilterButtons(), "cell 7 0,alignx center,growy");
			pnFilters.add(getPnAux(), "cell 4 0,aligny top");
		}
		return pnFilters;
	}

	private JLabel getLblSearch() {
		if (lblSearch == null) {
			lblSearch = new JLabel("Search:");
			lblSearch.setDisplayedMnemonic('a');
			lblSearch.setToolTipText("Write the name and press enter to search for a product.");
			lblSearch.setLabelFor(getTxtSearch());
		}
		return lblSearch;
	}

	private JTextField getTxtSearch() {
		if (txtSearch == null) {
			txtSearch = new JTextField();
			txtSearch.setToolTipText("Write the name and press enter to search for a product.");
			txtSearch.addActionListener(e -> {
				String searchedText = txtSearch.getText().toLowerCase().replace(" ", "");
				if (searchedText.isEmpty()) {
					products = originalProducts;
					reDrawCatalog();
				} else {
					products = products.parallelStream().filter(product -> {
						String name = product.getName();
						boolean expression = name.toLowerCase().replace(" ", "").equals(searchedText);
						return expression;
					}).collect(Collectors.toList());
					reDrawCatalog();
				}
			});
			txtSearch.setColumns(10);
		}
		return txtSearch;
	}

	private JLabel getLblType() {
		if (lblType == null) {
			lblType = new JLabel("Type:");
			lblType.setToolTipText("Select the type of the product.");
			lblType.setLabelFor(getCbTypes());
			lblType.setDisplayedMnemonic('T');
		}
		return lblType;
	}

	private JComboBox<String> getCbTypes() {
		if (cbTypes == null) {
			cbTypes = new JComboBox<String>();
			cbTypes.setModel(new DefaultComboBoxModel<String>(
					new String[] { "All", "Drinks", "Food", "Place", "Decoration", "Others" }));
			cbTypes.setSelectedIndex(0);
		}
		return cbTypes;
	}

	private JScrollPane getSpCatalog() {
		if (spCatalog == null) {
			spCatalog = new JScrollPane();
			spCatalog.setViewportView(getCatalog());
		}
		return spCatalog;
	}

	private JTable getCatalog() {
		if (catalog == null) {
			catalog = new JTable() {
				/**
				 * 
				 */
				private static final long serialVersionUID = 1479120713583837208L;

				@Override
				public boolean isCellEditable(int row, int column) {
					return false;
				}
			};
			catalog.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			catalog.getSelectionModel().addListSelectionListener(e -> {
				setSelectedProduct(catalog.getSelectedRow());
			});
			DefaultTableModel model = new DefaultTableModel(new String[] { "Name", "Type", "Price", "Price Type" }, 0);
			catalog.setModel(model);
		}
		return catalog;
	}

	private void fillCatalog(List<Product> products) {
		for (Product product : products) {
			String name = product.getName();
			String type = TypeConversor.convert(product.getType());
			String price = "";
			String priceType = "";
			if (product.getUnitPrice() > 0) {
				price = String.valueOf(product.getUnitPrice());
				priceType = "per unit";
			} else {
				price = String.valueOf(product.getGroupPrice());
				priceType = "per person";
			}
			((DefaultTableModel) this.getCatalog().getModel()).addRow(new String[] { name, type, price, priceType, });
		}
		getCatalog().getSelectionModel().setSelectionInterval(0, 0);

	}

	private JPanel getPnLeft() {
		if (pnLeft == null) {
			pnLeft = new JPanel();
			pnLeft.setLayout(new MigLayout("", "[150px,grow][10px]", "[150px][grow]"));
			pnLeft.add(getLblImage(), "cell 0 0,alignx left,aligny top");
			pnLeft.add(getPnUnits(), "cell 0 1,grow");
		}
		return pnLeft;
	}

	private JLabel getLblImage() {
		if (lblImage == null) {
			lblImage = new JLabel("");
			lblImage.setPreferredSize(new Dimension(150, 150));
		}
		return lblImage;
	}

	private JPanel getPnUnits() {
		if (pnUnits == null) {
			pnUnits = new JPanel();
			pnUnits.setLayout(new MigLayout("", "[][grow]", "[][][]"));
			pnUnits.add(getLblUnits(), "cell 0 0");
			pnUnits.add(getSpnUnits(), "cell 1 0");
			pnUnits.add(getLblPrice(), "cell 0 1,alignx trailing");
			pnUnits.add(getTxtPrice(), "cell 1 1,growx,aligny top");
			pnUnits.add(getBtnBuy(), "cell 0 2 2097051 1,alignx center");
		}
		return pnUnits;
	}

	private JLabel getLblUnits() {
		if (lblUnits == null) {
			lblUnits = new JLabel("Units:");
			lblUnits.setDisplayedMnemonic('U');
			lblUnits.setLabelFor(getSpnUnits());
			lblUnits.setToolTipText("Specify the units of the products that require it.");
		}
		return lblUnits;
	}

	private JSpinner getSpnUnits() {
		if (spnUnits == null) {
			spnUnits = new JSpinner();
			spnUnits.setToolTipText("Specify the units of the products that require it.");
			spnUnits.setModel(new SpinnerNumberModel(1, 1, null, 1));
			spnUnits.getModel().addChangeListener(e -> {
				int units = (int) spnUnits.getValue();
				double price = products.get(getCatalog().getSelectedRow()).getUnitPrice();
				this.getTxtPrice().setText(String.valueOf(price * units));
			});
		}
		return spnUnits;
	}

	private JLabel getLblPrice() {
		if (lblPrice == null) {
			lblPrice = new JLabel("Price:");
		}
		return lblPrice;
	}

	private JTextField getTxtPrice() {
		if (txtPrice == null) {
			txtPrice = new JTextField();
			txtPrice.setToolTipText("The price of the product, considering either units or number of people in the party.");
			txtPrice.setEditable(false);
			txtPrice.setColumns(10);
		}
		return txtPrice;
	}

	private void setSelectedProduct(int index) {
		if (index >= 0 && !products.isEmpty()) {
			Product product = this.products.get(index);
			if (product.getUnitPrice() > 0) {
				this.getSpnUnits().setEnabled(true);
				this.getTxtPrice().setText("0");
			} else {
				this.getSpnUnits().setEnabled(false);
				this.updateGroupPrice(party.getNumberOfPeople(), product.getGroupPrice());
			}

			this.getTaDescription().setText(product.getDescription());
			setAdaptedImage(getLblImage(), "/img/" + product.getCode());
			getSpnUnits().setValue(1);
		}

	}

	private JScrollPane getSpDescription() {
		if (spDescription == null) {
			spDescription = new JScrollPane();
			spDescription.setPreferredSize(new Dimension(200, 100));
			spDescription.setViewportView(getTaDescription());
		}
		return spDescription;
	}

	private JTextArea getTaDescription() {
		if (taDescription == null) {
			taDescription = new JTextArea();
			taDescription.setEditable(false);
		}
		return taDescription;
	}

	private void updateGroupPrice(int attendants, double groupPrice) {
		this.getSpnUnits().setEnabled(false);
		this.getTxtPrice().setText(String.valueOf(groupPrice * attendants));
	}

	private void setAdaptedImage(JLabel label, String imageRoute) {
		URL url = this.getClass().getResource(imageRoute + ".jpg");
		if (url == null) {
			url = this.getClass().getResource(imageRoute + ".JPG");
		}
		Image imgOriginal = new ImageIcon(url).getImage();
		Image imgEscalada = imgOriginal.getScaledInstance((int) (label.getPreferredSize().getWidth()),
				(int) (label.getPreferredSize().getHeight()), Image.SCALE_FAST);
		label.setIcon(new ImageIcon(imgEscalada));
		label.setVisible(true);
	}

	private JButton getBtnBuy() {
		if (btnBuy == null) {
			btnBuy = new JButton("Buy");
			btnBuy.setToolTipText("Add the specified product to the cart.");
			btnBuy.setMnemonic('B');
			btnBuy.addActionListener(e -> {
				int units = (int) getSpnUnits().getValue();
				Product product = products.get(getCatalog().getSelectedRow());
				try {
					Order order = new Order(product, party, units);
					party.addOrder(order);
				} catch (BusinessException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			});
		}
		return btnBuy;
	}

	private JSlider getSlider() {
		if (slider == null) {
			slider = new JSlider();
			slider.setToolTipText("Select the maximun price of the products.");
			slider.setEnabled(false);
			slider.setPaintTicks(true);
			slider.setPaintLabels(true);
			slider.setMaximum(1000);
			slider.setMinorTickSpacing(250);
			slider.setMajorTickSpacing(500);
			slider.addChangeListener(e -> {
				getSpnPrice().setValue(slider.getValue());
			});
		}
		return slider;
	}

	private JSpinner getSpnPrice() {
		if (spnPrice == null) {
			spnPrice = new JSpinner();
			spnPrice.setToolTipText("Select the maximun price of the products.");
			spnPrice.setEnabled(false);
			spnPrice.setModel(
					new SpinnerNumberModel(new Integer(1), new Integer(1), new Integer(1500), new Integer(10)));
			spnPrice.addChangeListener(e -> {
				getSlider().setValue(Integer.parseInt(spnPrice.getValue().toString()));
			});
		}
		return spnPrice;
	}

	private JPanel getPnFilterButtons() {
		if (pnFilterButtons == null) {
			pnFilterButtons = new JPanel();
			pnFilterButtons.setLayout(new MigLayout("", "[]", "[][]"));
			pnFilterButtons.add(getBtnApplyFilters(), "cell 0 0");
			pnFilterButtons.add(getBtnResetfilters(), "cell 0 1");
		}
		return pnFilterButtons;
	}

	private JButton getBtnApplyFilters() {
		if (btnApplyFilters == null) {
			btnApplyFilters = new JButton("Apply Filters");
			btnApplyFilters.setToolTipText("Apply the specified filters");
			btnApplyFilters.setMnemonic('F');
			btnApplyFilters.addActionListener(e -> appllyFilters());
		}
		return btnApplyFilters;
	}

	private void appllyFilters() {
		Filter baseFilter = new BaseFilter(products);
		baseFilter = getTypeFilter(baseFilter);
		baseFilter = getPriceFilter(baseFilter);
		products = baseFilter.getProducts();
		if (products.isEmpty()) {
			getBtnBuy().setEnabled(false);
			getSpnUnits().setEnabled(false);
		}
		reDrawCatalog();
	}

	private void reDrawCatalog() {
		DefaultTableModel model = new DefaultTableModel(new String[] { "Name", "Type", "Price", "Price Type" }, 0);
		catalog.setModel(model);
		fillCatalog(products);

	}

	private Filter getPriceFilter(Filter baseFilter) {
		if (getChckbxNewCheckBox().isSelected()) {
			double price = Double.parseDouble(getSpnPrice().getValue().toString());
			baseFilter = new FilterByPrice(baseFilter, price);
		}
		return baseFilter;
	}

	private Filter getTypeFilter(Filter baseFilter) {
		String type = getCbTypes().getSelectedItem().toString();
		baseFilter = new FilterByType(baseFilter, TypeConversor.convert(type));
		return baseFilter;
	}

	private JButton getBtnResetfilters() {
		if (btnResetfilters == null) {
			btnResetfilters = new JButton("ResetFilters");
			btnResetfilters.setToolTipText("Reset the filters and show all the products.");
			btnResetfilters.setMnemonic('e');
			btnResetfilters.addActionListener(e -> {
				getBtnBuy().setEnabled(true);
				getSpnUnits().setEnabled(true);
				products = new ArrayList<Product>(originalProducts);
				reDrawCatalog();
			});
		}
		return btnResetfilters;
	}

	private JLabel getLblPrice_1() {
		if (lblPrice_1 == null) {
			lblPrice_1 = new JLabel("Price:");
			lblPrice_1.setToolTipText("Select the maximun price of the products.");
			lblPrice_1.setLabelFor(getSlider());
			lblPrice_1.setDisplayedMnemonic('i');
		}
		return lblPrice_1;
	}

	private JPanel getPnAux() {
		if (pnAux == null) {
			pnAux = new JPanel();
			pnAux.setLayout(new MigLayout("", "[63px]", "[20px][]"));
			pnAux.add(getSpnPrice(), "cell 0 0,growx,aligny top");
			pnAux.add(getChckbxNewCheckBox(), "cell 0 1");
		}
		return pnAux;
	}

	private JCheckBox getChckbxNewCheckBox() {
		if (chckbxNewCheckBox == null) {
			chckbxNewCheckBox = new JCheckBox("Check price");
			chckbxNewCheckBox.setToolTipText("Check it to use the price filter.");
			chckbxNewCheckBox.setMnemonic('k');
			chckbxNewCheckBox.addChangeListener(e -> {
				if (chckbxNewCheckBox.isSelected()) {
					getSpnPrice().setEnabled(true);
					getSlider().setEnabled(true);
				} else {

					getSpnPrice().setEnabled(false);
					getSlider().setEnabled(false);
				}
			});
		}
		return chckbxNewCheckBox;
	}

	public void restart() {
		products = new ArrayList<Product>(originalProducts);
		reDrawCatalog();
		setSelectedProduct(0);

	}
}
