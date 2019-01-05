package ui.filter;

import model.Product;

public class FilterByUnitPrice extends FilterByPrice {

	public FilterByUnitPrice(Filter filter, double price) {
		super(filter, price);
	}

	@Override
	double getPrice(Product product) {
		return product.getUnitPrice();
	}

}
