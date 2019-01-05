package ui.filter;

import java.util.List;

import model.Product;

public class BaseFilter implements Filter {
	private List<Product> products;

	public BaseFilter( List<Product> products) {
		this.products = products;
	}

	@Override
	public void filter() {

	}

	@Override
	public List<Product> getProducts() {
		return this.products;
	}

}
