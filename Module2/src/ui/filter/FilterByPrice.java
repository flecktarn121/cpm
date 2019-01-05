package ui.filter;

import java.util.List;
import java.util.stream.Collectors;

import model.Product;

public class FilterByPrice implements Filter {
	private Filter filter;
	private List<Product> products;
	private double price;

	public FilterByPrice(Filter filter, double price) {
		this.filter = filter;
		this.price = price;
	}

	@Override
	public void filter() {
		products = filter.getProducts();
		if (products == null) {
			throw new IllegalStateException("There is no list of products to filter");
		}
		products = products.parallelStream().filter((product) -> {
			double productPrice = getPrice(product);
			return productPrice <= price;
		}).collect(Collectors.toList());

	}

	double getPrice(Product product) {
		if(product.getUnitPrice() > 0) {
			return product.getUnitPrice();
		}else {
			return product.getGroupPrice();
		}
	}

	@Override
	public List<Product> getProducts() {
		this.filter();
		return this.products;
	}

}
