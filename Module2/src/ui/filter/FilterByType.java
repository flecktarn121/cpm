package ui.filter;

import java.util.List;
import java.util.stream.Collectors;

import model.Product;
import model.ProductType;

public class FilterByType implements Filter {
	private Filter filter;
	private List<Product> products;
	private ProductType type;

	public FilterByType(Filter filter, ProductType type) {
		if (type == null) {
			throw new IllegalArgumentException("There is no type of products to filter");
		}
		this.filter = filter;
		this.type = type;
	}

	@Override
	public void filter() {
		products = filter.getProducts();
		if (products == null) {
			throw new IllegalStateException("There is no list of products to filter");
		}
		products = products.parallelStream().filter((product) -> {
			return product.getType().equals(this.type) || type.equals(ProductType.ALL);
		}).collect(Collectors.toList());
	}

	@Override
	public List<Product> getProducts() {
		this.filter();
		return this.products;
	}

}
