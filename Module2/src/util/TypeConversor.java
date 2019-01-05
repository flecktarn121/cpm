package util;

import model.ProductType;

public class TypeConversor {

	public static String convert(ProductType type) {

		String typeString = "";
		switch (type) {
		case DRINK:
			typeString = "Drinks";
			break;
		case FOOD:
			typeString = "Food";
			break;
		case DECORATION:
			typeString = "Decoration";
			break;
		case SPACE:
			typeString = "Place";
			break;
		case OTHERS:
			typeString = "Others";
			break;
		case ALL:
			typeString = "All";
			break;
		}
		return typeString;
	}

	public static ProductType convert(String type) {
		ProductType productType = null;
		switch (type) {
		case "Drinks":
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
		case "Others":
			productType = ProductType.OTHERS;
			break;
		case "All":
			productType = productType.ALL;
			break;
		}
		return productType;
	}

}
