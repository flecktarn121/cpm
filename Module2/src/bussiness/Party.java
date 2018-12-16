package bussiness;

import java.util.ArrayList;
import java.util.List;

public class Party {

	private int numberOfPeople;
	private List<Order> items = new ArrayList<Order>();
	private double totalPrice = 0.0;

	public int getNumberOfPeople() {
		return numberOfPeople;
	}

	public void setNumberOfPeople(int numberOfPeople) throws InvalidInputException {
		if (numberOfPeople < 0) {
			throw new InvalidInputException("There must be at least one attendant to the party.");
		}
		this.numberOfPeople = numberOfPeople;
		updateTotalPrice();
	}

	private void updateTotalPrice() {
		totalPrice = items.parallelStream().map((order) -> order.getTotalPrice()).reduce(0.0, (a, b) -> a + b);

	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void addOrder(Order order) {
		this.items.add(order);
		this.totalPrice += order.getTotalPrice();
	}

	public Order getOrder(int index) {
		// this method will be used assuming the order in the displayed chart id the
		// same as in the list of orders.
		if (index < 0) {
			throw new IllegalArgumentException();
		}
		Order order = this.items.get(index);
		return order;
	}

	public void removeOrder(Order order) {
		this.items.remove(order);
		this.totalPrice -= order.getTotalPrice();

	}
}
