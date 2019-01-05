package bussiness;

import java.util.List;
import java.util.function.Consumer;

import model.ProductType;
import util.Messages;

public class ReceiptPrinter {

	private Party party;
	private Login login;

	public ReceiptPrinter(Party party, Login login) {
		this.party = party;
		this.login = login;
	}

	public String printRecipt() {
		StringBuilder recipt = new StringBuilder();
		printHeader(recipt);
		printBody(recipt);
		printFooter(recipt);
		return recipt.toString();
	}

	private void printFooter(StringBuilder recipt) {
		recipt.append(Messages.getString("ReceiptPrinter.0") + Messages.getString("ReceiptPrinter.1")); //$NON-NLS-1$ //$NON-NLS-2$
		recipt.append(Messages.getString("ReceiptPrinter.2") + Messages.getString("ReceiptPrinter.3")); //$NON-NLS-1$ //$NON-NLS-2$
		recipt.append(party.getComment() + Messages.getString("ReceiptPrinter.4")); //$NON-NLS-1$
		double totalPrice = party.getTotalPrice();
		double totalDiscount = party.getTotalDiscount();
		recipt.append(Messages.getString("ReceiptPrinter.5") + totalPrice + Messages.getString("ReceiptPrinter.6") + totalDiscount + Messages.getString("ReceiptPrinter.7") //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				+ (totalPrice - totalDiscount) + Messages.getString("ReceiptPrinter.8")); //$NON-NLS-1$

	}

	private void printBody(StringBuilder recipt) {
		List<Order> orders = party.getItems();
		recipt.append(Messages.getString("ReceiptPrinter.9")); //$NON-NLS-1$
		recipt.append(Messages.getString("ReceiptPrinter.10")); //$NON-NLS-1$

		Consumer<Order> printing = (order) -> {
			recipt.append(Messages.getString("ReceiptPrinter.11") + order.getName()); //$NON-NLS-1$
			recipt.append(Messages.getString("ReceiptPrinter.12") + order.getCode()); //$NON-NLS-1$
			recipt.append(Messages.getString("ReceiptPrinter.13") + order.getUnits()); //$NON-NLS-1$
			recipt.append(Messages.getString("ReceiptPrinter.14") + order.getTotalPrice()); //$NON-NLS-1$
			recipt.append(Messages.getString("ReceiptPrinter.15")); //$NON-NLS-1$
		};
		printDrinks(recipt, orders, printing);
		printFood(recipt, orders, printing);
		printDecorations(recipt, orders, printing);
		printSpaces(recipt, orders, printing);
		printOthers(recipt, orders, printing);
	}

	private void printOthers(StringBuilder recipt, List<Order> orders, Consumer<Order> printing) {
		recipt.append(Messages.getString("ReceiptPrinter.16") + Messages.getString("ReceiptPrinter.17")); //$NON-NLS-1$ //$NON-NLS-2$
		orders.stream().filter((order) -> {
			return order.getType().equals(ProductType.OTHERS);
		}).forEach((order) -> printing.accept(order));

	}

	private void printSpaces(StringBuilder recipt, List<Order> orders, Consumer<Order> printing) {
		recipt.append(Messages.getString("ReceiptPrinter.18") + Messages.getString("ReceiptPrinter.19")); //$NON-NLS-1$ //$NON-NLS-2$
		orders.stream().filter((order) -> {
			return order.getType().equals(ProductType.SPACE);
		}).forEach((order) -> printing.accept(order));

	}

	private void printDecorations(StringBuilder recipt, List<Order> orders, Consumer<Order> printing) {
		recipt.append(Messages.getString("ReceiptPrinter.20") + Messages.getString("ReceiptPrinter.21")); //$NON-NLS-1$ //$NON-NLS-2$
		orders.stream().filter((order) -> {
			return order.getType().equals(ProductType.DECORATION);
		}).forEach((order) -> printing.accept(order));

	}

	private void printFood(StringBuilder recipt, List<Order> orders, Consumer<Order> printing) {
		recipt.append(Messages.getString("ReceiptPrinter.22") + Messages.getString("ReceiptPrinter.23")); //$NON-NLS-1$ //$NON-NLS-2$
		orders.stream().filter((order) -> {
			return order.getType().equals(ProductType.FOOD);
		}).forEach((order) -> printing.accept(order));

	}

	private void printDrinks(StringBuilder recipt, List<Order> orders, Consumer<Order> printing) {
		recipt.append(Messages.getString("ReceiptPrinter.24") + Messages.getString("ReceiptPrinter.25")); //$NON-NLS-1$ //$NON-NLS-2$
		orders.stream().filter((order) -> {
			return order.getType().equals(ProductType.DRINK);
		}).forEach((order) -> printing.accept(order));
	}

	private void printHeader(StringBuilder recipt) {
		recipt.append(Messages.getString("ReceiptPrinter.26") + Messages.getString("ReceiptPrinter.27")); //$NON-NLS-1$ //$NON-NLS-2$
		recipt.append(Messages.getString("ReceiptPrinter.28") + Messages.getString("ReceiptPrinter.29")); //$NON-NLS-1$ //$NON-NLS-2$
		recipt.append(Messages.getString("ReceiptPrinter.30") + party.getCustomerName() + Messages.getString("ReceiptPrinter.31") + party.getCustomerSurname()); //$NON-NLS-1$ //$NON-NLS-2$
		if (!login.getCurrentUser().equals(Login.NO_USER)) {
			recipt.append(Messages.getString("ReceiptPrinter.32") + login.getCurrentUser()); //$NON-NLS-1$
		}
		recipt.append(Messages.getString("ReceiptPrinter.33")); //$NON-NLS-1$
		recipt.append(Messages.getString("ReceiptPrinter.34") + party.getCustomerNIF() + Messages.getString("ReceiptPrinter.35")); //$NON-NLS-1$ //$NON-NLS-2$
		recipt.append(Messages.getString("ReceiptPrinter.36") + party.getDateAndTime().toString() + Messages.getString("ReceiptPrinter.37")); //$NON-NLS-1$ //$NON-NLS-2$
		recipt.append(Messages.getString("ReceiptPrinter.38") + party.getNumberOfPeople() + Messages.getString("ReceiptPrinter.39")); //$NON-NLS-1$ //$NON-NLS-2$
	}

}
