package bussiness;

import java.io.File;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import db.BillWriter;
import util.Messages;

public class Party {

	private static final double USERS_DISCOUNT = 0.15;
	private int numberOfPeople = 1;
	private List<Order> items = new ArrayList<Order>();
	private Set<String> codes = new HashSet<String>();
	private double totalPrice = 0.0;
	private double totalDiscount = 0.0;
	private String customerName;
	private String customerSurname;
	private String customerNIF;
	private String customersPhone;
	private Date date;
	private LocalTime time;
	private String comment;
	private Login login;

	public Party(Login login) {
		this.login = login;
	}

	public int getNumberOfPeople() {
		return numberOfPeople;
	}

	public void setNumberOfPeople(int numberOfPeople) throws InvalidInputException {
		if (numberOfPeople < 0) {
			throw new InvalidInputException(Messages.getString("Party.0")); //$NON-NLS-1$
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

	public void addOrder(Order order) throws BusinessException {
		if (codes.contains(order.getCode())) {
			if (order.getGroupPrice() > 0) {
				throw new BusinessException(Messages.getString("Party.1")); //$NON-NLS-1$
			}
		}
		this.items.add(order);
		this.codes.add(order.getCode());
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
		codes.remove(order.getCode());
		this.totalPrice -= order.getTotalPrice();

	}

	public List<Order> getItems() {
		return items;
	}

	public String getCustomerName() {
		return customerName;
	}

	public String getCustomerSurname() {
		return customerSurname;
	}

	public String getCustomerNIF() {
		return customerNIF;
	}

	public Date getDateAndTime() {
		return date;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public double getTotalDiscount() {
		if (!login.getCurrentUser().equals(Login.NO_USER)) {
			this.totalDiscount = this.getTotalPrice() * USERS_DISCOUNT;
		}

		return totalDiscount;
	}

	public String printRecipt() {
		return new ReceiptPrinter(this, login).printRecipt();
	}

	public void setCustomerName(String customerName) throws InvalidInputException {
		if(customerName.isEmpty()) {
			throw new InvalidInputException(Messages.getString("Party.2")); //$NON-NLS-1$
		}
		this.customerName = customerName;
	}

	public void setCustomerSurname(String customerSurname) throws InvalidInputException {
		if (customerSurname.isEmpty()) {
			throw new InvalidInputException(Messages.getString("Party.3")); //$NON-NLS-1$
		}
		this.customerSurname = customerSurname;
	}

	public void setCustomerNIF(String customerNIF) throws InvalidInputException {
		if (customerNIF.isEmpty()) {
			throw new InvalidInputException(Messages.getString("Party.4")); //$NON-NLS-1$
		}
		this.customerNIF = customerNIF;
	}

	public void setDateAndTime(Date dateAndTime) throws InvalidInputException {
		if (dateAndTime.before(new Date())) {
			throw new InvalidInputException(Messages.getString("Party.5")); //$NON-NLS-1$
		}
		this.date = dateAndTime;
	}

	public LocalTime getTime() {
		return time;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}

	public void clearOrders() {
		items.clear();
		codes.clear();
		updateTotalPrice();
	}

	public Login getLogin() {
		return login;
	}

	public void saveBill(String file, String bill) {
		new BillWriter(file, bill);

	}

	public String getCustomersPhone() {
		return customersPhone;
	}

	public void setCustomersPhone(String customersPhone) throws InvalidInputException {
		if (customersPhone.isEmpty()) {
			throw new InvalidInputException(Messages.getString("Party.6")); //$NON-NLS-1$
		}
		this.customersPhone = customersPhone;
	}
}
