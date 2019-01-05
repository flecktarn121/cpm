package db;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import model.Product;
import model.ProductType;

public class ProductDatabase {

	private static final String PRODUCTS_FILE = "files/party.txt";
	private Map<String, Product> products = new HashMap<String, Product>();

	public ProductDatabase() throws FileNotFoundException, IOException {
		this.loadProducts();
	}

	private void loadProducts() throws FileNotFoundException, IOException {
		try (FileReader fl = new FileReader(PRODUCTS_FILE); BufferedReader file = new BufferedReader(fl)) {
			while (file.ready()) {
				String line = file.readLine();
				String[] data = line.split("@");
				String code = data[0];
				ProductType type = toType(data[1]);
				String name = data[2];
				String description = data[3];
				double unitPrice = Double.valueOf(data[4]);
				double groupPrice = Double.valueOf(data[5]);
				Product product = new Product(code, type, name, description, unitPrice, groupPrice);
				products.put(code, product);
			}
		}
	}

	public ProductType toType(String type) {
		ProductType productType = null;
		switch (type) {
		case "Drink":
			productType = ProductType.DRINK;
			break;
		case "Food":
			productType = ProductType.FOOD;
			break;
		case "Decoration":
			productType = ProductType.DECORATION;
			break;
		case "Place":
			productType = ProductType.SPACE;
			break;
		case "Otros":
			productType = ProductType.OTHERS;
			break;
		}
		return productType;
	}

	public Map<String, Product> getProducts() {
		return new HashMap<String, Product>(products);
	}

}
