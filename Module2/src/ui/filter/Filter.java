package ui.filter;

import java.util.List;

import model.Product;

public interface Filter {
	public void filter();

	public List<Product> getProducts();
}
