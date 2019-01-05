package ui.filter;

import model.Product;

public class FilterByGroupPrice extends FilterByPrice {

	public FilterByGroupPrice(Filter filter, double price) {
		super(filter, price);
	}

	@Override
	double getPrice(Product product) {
		return product.getGroupPrice();
	}

}
