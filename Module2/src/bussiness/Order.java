package bussiness;

import model.Product;

public class Order {
	private Product product;
	private int units;
	private Party party;

	/**
	 * Constructor for the wrapper class to represent an order of a certain product.
	 * If the product has no units, the third parameter will be ignored
	 * 
	 * @param product
	 * @param party
	 * @param units   only taken into account when the total price of the order is
	 *                determined by units.
	 */
	public Order(Product product, Party party, int units) {
		this.product = product;
		this.party = party;
		this.units = units;
	}

	public double getTotalPrice() {
		double toalPrice = 0.0;
		if (product.getUnitPrice() > 0) {
			toalPrice = product.getUnitPrice() * units;
		} else {
			toalPrice = product.getGroupPrice() * this.party.getNumberOfPeople();
		}
		return toalPrice;
	}
}
