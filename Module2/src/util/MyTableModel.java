package util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import javax.swing.table.AbstractTableModel;

import model.Product;

public class MyTableModel extends AbstractTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3093683189490896991L;

	private Map<String, Function<Product, Object>> fields = new HashMap<String, Function<Product, Object>>();

	private List<Product> products;

	private String[] names = new String[] { "Name", "Description", "Type", "Price" };

	public MyTableModel() {
		generateFields();
	}

	private void generateFields() {
		fields.put(names[0], (product) -> product.getName());
		fields.put(names[1], (product) -> product.getDescription());
		fields.put(names[2], (product) -> {
			if (product.getGroupPrice() > 0) {
				return product.getGroupPrice();
			} else {
				return product.getUnitPrice();
			}
		});
		fields.put(names[3], (product) -> product.getType());

	}

	@Override
	public int getColumnCount() {
		return fields.size();
	}

	@Override
	public int getRowCount() {
		return products.size();
	}

	@Override
	public Object getValueAt(int arg0, int arg1) {
		if (arg1 >= names.length || arg0 >= products.size()) {
			throw new IndexOutOfBoundsException();
		}
		return fields.get(names[arg1]).apply(products.get(arg0));
	}
	
	public void add(Product arg0) {
		if(arg0 == null) {
			throw new NullPointerException("The argument connot be null.");
		}
		products.add(arg0);
		fireTableDataChanged();
		fireTableRowsInserted(0, products.size()-1);
	}

}
