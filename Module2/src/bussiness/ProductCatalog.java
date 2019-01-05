package bussiness;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import db.ProductDatabase;
import model.Product;
import util.Messages;

public class ProductCatalog {
	private ProductDatabase db;

	public ProductCatalog() throws BusinessException {
		try {
			db = new ProductDatabase();
		} catch (IOException e) {
			throw new BusinessException(Messages.getString("ProductCatalog.0")); //$NON-NLS-1$
		}
	}

	public List<Product> getProducts(){
		List<Product> products = new ArrayList<Product>(db.getProducts().values());
		return products;
	}

}
