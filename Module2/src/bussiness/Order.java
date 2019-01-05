package bussiness;

import model.Product;
import model.ProductType;
import util.Messages;

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
	 * @param units
	 *            only taken into account when the total price of the order is
	 *            determined by units.
	 * @throws BusinessException 
	 */
	public Order(Product product, Party party, int units) throws BusinessException {
		if (units < 0) {
			throw new BusinessException(Messages.getString("Order.0")); //$NON-NLS-1$
		}
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

	public ProductType getType() {
		return product.getType();
	}

	public String getName() {
		return product.getName();
	}

	public String getCode() {
		return product.getCode();
	}

	public int getUnits() {
		return units;
	}

	public void setUnits(int units) {
		if (units < 0) {
			throw new IllegalArgumentException(Messages.getString("Order.1")); //$NON-NLS-1$
		}
		this.units = units;
	}

	public double getUnitPrice() {
		return product.getUnitPrice();
	}

	public double getGroupPrice() {
		return product.getGroupPrice();
	}
}
