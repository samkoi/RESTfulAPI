package de.uniba.dsg.jaxrs.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import de.uniba.dsg.jaxrs.db.DB;
import de.uniba.dsg.jaxrs.model.logic.Beverage;
import de.uniba.dsg.jaxrs.model.logic.Bottle;
import de.uniba.dsg.jaxrs.model.logic.Crate;
import de.uniba.dsg.jaxrs.model.logic.Order;
import de.uniba.dsg.jaxrs.model.logic.OrderItem;
import de.uniba.dsg.jaxrs.model.logic.OrderStatus;
import de.uniba.dsg.jaxrs.resources.FilterFunction;

/*
 * delegates requests to the database 
 */
public class BeverageService {

	public static final BeverageService instance = new BeverageService();

	private final DB database;

	public BeverageService() {
		this.database = new DB();
	}

	/*
	 * Get
	 */

	public Bottle getBottleById(final int bottleId) {
		return database.getBottleById(bottleId);
	}

	public Crate getCrateById(final int crateId) {
		return database.getCrateById(crateId);
	}

	public List<Order> getOrders(String searchTerm, FilterFunction filterFunction) {
		List<Order> orders = database.getOrders();

		if (searchTerm != null && !searchTerm.isEmpty()) {
			orders = orders.stream()
					.filter(beverage -> beverage.toString().toUpperCase().contains(searchTerm.toUpperCase()))
					.collect(Collectors.toList());
		}

		if (filterFunction == null) {
			return orders;
		}

		switch (filterFunction) {
		case MaxPrice:
			return orderFilterPrice(orders, true);
		case MinPrice:
			return orderFilterPrice(orders, false);
		case OrderProcessed:
			return ordersFilterProcessed(orders);
		case OrderSubmitted:
			return ordersFilterSubmitted(orders);
		default:
			return orders;
		}
	}

	public Order getOrderById(final int orderId) {
		return database.getOrderById(orderId);
	}

	public Order updateOrder(int id) {
		return database.updateOrder(id);
	}

	public void addOrder(Order order) {
		database.addOrder(order);
	}

	public boolean deleteOrder(int id) {
		return database.deleteOrder(id);
	}

	public OrderItem getOrderItemById(int orderId, int id) {
		return database.getOrderItemById(orderId, id);
	}

	public boolean deleteOrderItem(int orderId, int id) {
		return database.deleteOrderItem(orderId, id);
	}

	public List<Beverage> getBeverages(String searchTerm, FilterFunction filterFunction) {
		List<Beverage> beverages = database.getBeverages();
		if (searchTerm != null && !searchTerm.isEmpty()) {
			beverages = beverages.stream()
					.filter(beverage -> beverage.toString().toUpperCase().contains(searchTerm.toUpperCase()))
					.collect(Collectors.toList());
		}
		if (filterFunction == null) {
			return beverages;
		}

		switch (filterFunction) {
		case Alcoholic:
			return beverageFilterAlcoholic(beverages);
		case MaxPrice:
			return beverageFilterPrice(beverages, true);
		case MinPrice:
			return beverageFilterPrice(beverages, false);
		case VolumePercent:
			return beverageFilterVolume(beverages);
		default:
			return beverages;
		}

	}

	public void addBottle(Bottle bottle) {
		database.addBottle(bottle);
	}

	public void addCrate(Crate crate) {
		database.addCrate(crate);

	}

	public Bottle updateBottle(int id, Beverage bottle) {
		return database.updateBottle(id, (Bottle) bottle);
	}

	public Crate updateCrate(int id, Beverage crate) {
		return database.updateCrate(id, (Crate) crate);
	}

	public boolean deleteBottle(int id) {
		return database.deleteBottle(id);
	}

	public boolean deleteCrate(int id) {
		return database.deleteCrate(id);
	}

	public List<OrderItem> getOrderItems(int orderId) {
		return database.getOrderItems(orderId);
	}

	public List<String> getbevTypes() {
		List<String> list = new ArrayList<String>();
		list.add("Bottle");
		list.add("Crate");
		return list;
	}

	public int getAndIncrementBottleID() {
		return database.getAndIncrementBottleID();
	}

	public int getAndIncrementCrateID() {
		return database.getAndIncrementCrateID();
	}

	public int getAndIncrementOrdersID() {
		return database.getAndIncrementOrdersID();
	}

	public boolean isOrderAlreadyProcessed(int orderId) {
		return database.isOrderAlreadyProcessed(orderId);
	}

	public int getAvailableBeverageStock(Beverage beverage) {
		return database.getAvailableBeverageStock(beverage);
	}

	public OrderItem updateStockAndOrderPrice(int orderId, int orderItemId, int newQuantity) {
		return database.updateStockAndOrderPrice(orderId, orderItemId, newQuantity);
	}

	public OrderItem addBottleOrderItem(int orderId, int bottleId, int amount) {
		return database.addBottleOrderItem(orderId, bottleId, amount);
	}

	public OrderItem addCrateOrderItem(int orderId, int crateId, int amount) {
		return database.addCrateOrderItem(orderId, crateId, amount);
	}

	private List<Beverage> beverageFilterVolume(List<Beverage> beverages) {
		beverages.sort(Comparator.comparingDouble(Beverage::getVolumePercent).reversed());
		return beverages;
	}

	private List<Beverage> beverageFilterPrice(List<Beverage> beverages, boolean max) {
		if (max) {
			beverages.sort(Comparator.comparingDouble(Beverage::getPrice).reversed());
			return beverages;
		} else {
			beverages.sort(Comparator.comparingDouble(Beverage::getPrice));
			return beverages;
		}
	}

	private List<Beverage> beverageFilterAlcoholic(List<Beverage> beverages) {
		beverages = beverages.stream().filter(Beverage::isAlcoholic).collect(Collectors.toList());
		return beverages;
	}

	private List<Order> ordersFilterSubmitted(List<Order> orders) {
		orders = orders.stream().filter(order -> order.getStatus() == OrderStatus.SUBMITTED)
				.collect(Collectors.toList());
		return orders;
	}

	private List<Order> ordersFilterProcessed(List<Order> orders) {
		orders = orders.stream().filter(order -> order.getStatus() == OrderStatus.PROCESSED)
				.collect(Collectors.toList());
		return orders;
	}

	private List<Order> orderFilterPrice(List<Order> orders, boolean max) {
		if (max) {
			orders.sort(Comparator.comparingDouble(Order::getPrice).reversed());
			return orders;
		} else {
			orders.sort(Comparator.comparingDouble(Order::getPrice));
			return orders;
		}
	}

    public List<Beverage> getAllBottles() {
		return database.getBottles();
    }

	public List<Beverage> getAllCrates() {
		return database.getCrates();
	}
}
