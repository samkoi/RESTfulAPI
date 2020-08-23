package de.uniba.dsg.jaxrs.db;

import de.uniba.dsg.jaxrs.model.logic.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/*
 * Handles data storing and operations for the beverage Service (Insert/Delete/Update)
 */
public class DB {
	private static final Logger logger = Logger.getLogger("DB.class");
    private final List<Bottle> bottles;
    private final List<Crate> crates;
    private final List<Order> orders;
	private final List<Customer> customers;
	private final List<Employee> employees;
    
    private final AtomicInteger bottleID = new AtomicInteger(1);
    private final AtomicInteger crateID = new AtomicInteger(1);
    private final AtomicInteger ordersID = new AtomicInteger(1);
	private final AtomicInteger customerID = new AtomicInteger(1);
	private final AtomicInteger employeeID = new AtomicInteger(1);

    public DB() {
        this.bottles = initBottles();
        this.crates = initCases();
        this.orders = initOrder();
		this.customers = initCustomer();
		this.employees = initEmployee();
    }

    private List<Bottle> initBottles() {
        return new ArrayList<>(Arrays.asList(
                new Bottle(bottleID.getAndIncrement(), "Pils", 0.5, true, 4.8, 0.79, "Keesmann", 34),
                new Bottle(bottleID.getAndIncrement(), "Helles", 0.5, true, 4.9, 0.89, "Mahr", 17),
                new Bottle(bottleID.getAndIncrement(), "Boxbeutel", 0.75, true, 12.5, 5.79, "Divino", 11),
                new Bottle(bottleID.getAndIncrement(), "Tequila", 0.7, true, 40.0, 13.79, "Tequila Inc.", 5),
                new Bottle(bottleID.getAndIncrement(), "Gin", 0.5, true, 42.00, 11.79, "Hopfengarten", 3),
                new Bottle(bottleID.getAndIncrement(), "Export Edel", 0.5, true, 4.8, 0.59, "Oettinger", 66),
                new Bottle(bottleID.getAndIncrement(), "Premium Tafelwasser", 0.7, false, 0.0, 4.29, "Franken Brunnen", 12),
                new Bottle(bottleID.getAndIncrement(), "Wasser", 0.5, false, 0.0, 0.29, "Franken Brunnen", 57),
                new Bottle(bottleID.getAndIncrement(), "Spezi", 0.7, false, 0.0, 0.69, "Franken Brunnen", 42),
                new Bottle(bottleID.getAndIncrement(), "Grape Mix", 0.5, false, 0.0, 0.59, "Franken Brunnen", 12),
                new Bottle(bottleID.getAndIncrement(), "Still", 1.0, false, 0.0, 0.66, "Franken Brunnen", 34),
                new Bottle(bottleID.getAndIncrement(), "Cola", 1.5, false, 0.0, 1.79, "CCC", 69),
                new Bottle(bottleID.getAndIncrement(), "Cola Zero", 2.0, false, 0.0, 2.19, "CCC", 12),
                new Bottle(bottleID.getAndIncrement(), "Apple", 0.5, false, 0.0, 1.99, "Juice Factory", 25),
                new Bottle(bottleID.getAndIncrement(), "Orange", 0.5, false, 0.0, 1.99, "Juice Factory", 55),
                new Bottle(bottleID.getAndIncrement(), "Lime", 0.5, false, 0.0, 2.99, "Juice Factory", 8)
        ));
    }

    private List<Crate> initCases() {
        return new ArrayList<>(Arrays.asList(
                new Crate(crateID.getAndIncrement(), this.bottles.get(0), 20, 14.99, 3),
                new Crate(crateID.getAndIncrement(), this.bottles.get(1), 20, 15.99, 5),
                new Crate(crateID.getAndIncrement(), this.bottles.get(2), 6, 30.00, 7),
                new Crate(crateID.getAndIncrement(), this.bottles.get(7), 12, 1.99, 11),
                new Crate(crateID.getAndIncrement(), this.bottles.get(8), 20, 11.99, 13),
                new Crate(crateID.getAndIncrement(), this.bottles.get(11), 6, 10.99, 4),
                new Crate(crateID.getAndIncrement(), this.bottles.get(12), 6, 11.99, 5),
                new Crate(crateID.getAndIncrement(), this.bottles.get(13), 20, 35.00, 7),
                new Crate(crateID.getAndIncrement(), this.bottles.get(14), 12, 20.00, 9)
        ));
    }

    private List<Order> initOrder() {
        return new ArrayList<>(Arrays.asList(
                new Order(ordersID.getAndIncrement(), new ArrayList<>(Arrays.asList(
                        new OrderItem(10, this.bottles.get(3), 2),
                        new OrderItem(20, this.crates.get(3), 1),
                        new OrderItem(30, this.bottles.get(15), 1)
                )), 32.56, OrderStatus.SUBMITTED),
                new Order(ordersID.getAndIncrement(), new ArrayList<>(Arrays.asList(
                        new OrderItem(10, this.bottles.get(13), 2),
                        new OrderItem(20, this.bottles.get(14), 2),
                        new OrderItem(30, this.crates.get(0), 1)
                )), 22.95, OrderStatus.PROCESSED),
                new Order(ordersID.getAndIncrement(), new ArrayList<>(Arrays.asList(
                        new OrderItem(10, this.bottles.get(2), 1)
                )), 5.79, OrderStatus.SUBMITTED)
        ));
    }

	private List<Customer> initCustomer() {
		return new ArrayList<>(Arrays.asList(
				new Customer(customerID.getAndIncrement(), "Namrata", "Jain", "Namrata Jain")
		));
	}

	private List<Employee> initEmployee() {
		return new ArrayList<>(Arrays.asList(
				new Employee(employeeID.getAndIncrement(), "Namrata", "Jain", "Namrata Jain")
		));
	}

	
	public Bottle getBottleById(int id) {
		return bottles.stream().filter(bottle -> bottle.getId() == id).findFirst().orElse(null);
	}

	public Crate getCrateById(int id) {
		return crates.stream().filter(crates -> crates.getId() == id).findFirst().orElse(null);
	}
	
	public List<Order> getOrders() {
		return orders;
	}
	
	public Order getOrderById(int id) {
		return orders.stream().filter(order -> order.getOrderId() == id).findFirst().orElse(null);
	}

	public Order updateOrder(int id) {
		Order orderToUpdate = orders.stream().filter(o -> o.getOrderId() == id).findFirst().get();
		orderToUpdate.setStatus(OrderStatus.PROCESSED);
		return orderToUpdate;
	}

	public void addOrder(Order order) {
		orders.add(order);
		
	}

	public boolean deleteOrder(int id) {
		Optional <Order> order = orders.stream().filter(o -> o.getOrderId() == id).findFirst();
		if(order.isPresent()) {
			return orders.remove(order.get());
		}else {
			return false;
		}
	}


	public OrderItem getOrderItemById(int orderId, int id) {
		Order order = orders.get(orders.indexOf(orders.stream().filter(o -> o.getOrderId() == orderId).findFirst().get()));
		OrderItem orderItem = order.getPositions().stream().filter(oi -> oi.getNumber() == id).findFirst().orElse(null);
		return orderItem;
	}


	
	/*
	 * Deletes order item / updates order price and increments stock of Beverage
	 */
	public boolean deleteOrderItem(int orderId, int id) {
		
		Order order = orders.get(orders.indexOf(orders.stream().filter(o -> o.getOrderId() == orderId).findFirst().get()));
		Optional <OrderItem> orderItem = order.getPositions().stream().filter(oi -> oi.getNumber() == id).findFirst();
		if (!orderItem.isPresent()) {
			return false;
		}
		updateOrderPriceafterDelete(order, orderItem.get());
		return order.getPositions().remove(orderItem.get());
	}

	
	/*
	 * Also updates amount of beverages and the Price of the order
	 */
	private void updateOrderPriceafterDelete(Order order, OrderItem orderItem) {
		if (orderItem.getBeverage() instanceof Bottle) {
			Bottle bottle = (Bottle) orderItem.getBeverage();
			this.getBottleById(bottle.getId()).setInStock(this.getBottleById(bottle.getId()).getInStock() + orderItem.getQuantity());
			double newPrice = Math.round((order.getPrice() - orderItem.getQuantity() * bottle.getPrice()) * 100);
			newPrice = newPrice / 100;
			order.setPrice(newPrice);
		}else {
			Crate crate = (Crate) orderItem.getBeverage();
			this.getCrateById(crate.getId()).setInStock(this.getCrateById(crate.getId()).getInStock() + orderItem.getQuantity());
			double newPrice = Math.round((order.getPrice() - orderItem.getQuantity() * crate.getPrice()) * 100);
			newPrice = newPrice / 100;
			order.setPrice(newPrice);
		}
	}

	/*
	 * returns sorted list of Beverages. First Bottle, then Crates
	 */
	public List<Beverage> getBeverages() {
		List<Beverage> beverages = new ArrayList<Beverage>(bottles);
		beverages.addAll(crates);
		beverages.sort(new Comparator<Beverage>() {

			@Override
			public int compare(Beverage o1, Beverage o2) {
				if(o1.getClass() == o2.getClass()) {
					return 0;
				}else if (o1 instanceof Bottle) {
					return 1;
				}else {
					return -1;
				}
			}
			
		});
		
		return beverages;
	}

	public void addBottle(Bottle bottle) {
		bottles.add(bottle);
		
	}

	public void addCrate(Crate crate) {
		crates.add(crate);
	}


	public Bottle updateBottle(int id, Bottle bottle) {
		Bottle bottleToReplace = bottles.stream().filter(b -> b.getId() == id ).findFirst().get();
		
		bottles.set(bottles.indexOf(bottleToReplace), bottle);
		return bottle;
	}

	public Crate updateCrate(int id, Crate crate) {
		Crate crateToReplace = crates.stream().filter(c -> c.getId() == id ).findFirst().get();
		crates.set(crates.indexOf(crateToReplace), crate);
		return crate;
	}

	public boolean deleteBottle(int id) {
		Optional<Bottle> bottleToDelete = bottles.stream().filter(bottle -> bottle.getId() == id).findFirst();
		if(bottleToDelete.isPresent()) {
			return bottles.remove(bottleToDelete.get());
		}else {
			return false;
		}
	}

	public boolean deleteCrate(int id) {
		Optional<Crate> crateToDelete = crates.stream().filter(crate -> crate.getId() == id).findFirst();
		if(crateToDelete.isPresent()) {
			return crates.remove(crateToDelete.get());
		}else {
			return false;
		}
	}

	public List<OrderItem> getOrderItems(int orderId) {
		return orders.stream().filter(order -> order.getOrderId() == orderId).findFirst().get().getPositions();
	}

	public int getAndIncrementBottleID() {
		return bottleID.getAndIncrement();
	}

	public int getAndIncrementCrateID() {
		return crateID.getAndIncrement();
	}

	public int getAndIncrementOrdersID() {
		return ordersID.getAndIncrement();
	}

	public boolean isOrderAlreadyProcessed(int orderId) {
		return this.getOrderById(orderId).getStatus() == OrderStatus.PROCESSED;
	}

	public int getAvailableBeverageStock(Beverage beverage) {
		if (beverage instanceof Bottle) {
			beverage = (Bottle) beverage;
			return this.getBottleById(((Bottle) beverage).getId()).getInStock();
		}else {
			beverage = (Crate) beverage;
			return this.getCrateById(((Crate) beverage ).getId()).getInStock();
		}
	}

	
	/*
	 * Helperfunction
	 */
	public OrderItem updateStockAndOrderPrice(int orderId, int orderItemId, int newQuantity) {
		if (this.getOrderItemById(orderId, orderItemId).getBeverage() instanceof Bottle) {
			return this.updateBottleStockAndOrderPrice(orderId, orderItemId, newQuantity);
		}else {
			return this.updateCrateStockAndOrderPrice(orderId, orderItemId, newQuantity);
		}
	}

	/*
	 * Order Item was updated --> increment/decrement Crate Stock and increment/decrement Order Price
	 * depends on if more beverages or less were requested
	 */
	private OrderItem updateCrateStockAndOrderPrice(int orderId, int orderItemId, int newQuantity) {
		OrderItem orderItemToUpdate = this.getOrderItemById(orderId, orderItemId);
		Order orderToUpdate = this.getOrderById(orderId);
		Crate crateToUpdate = (Crate) orderItemToUpdate.getBeverage();
		int difference = newQuantity - orderItemToUpdate.getQuantity();
		orderItemToUpdate.setQuantity(newQuantity);
		crateToUpdate.setInStock(crateToUpdate.getInStock() - difference);
		double newPrice = orderToUpdate.getPrice() + (difference * crateToUpdate.getPrice());
		newPrice = Math.round(newPrice*100);
		newPrice =  newPrice / 100;
		orderToUpdate.setPrice(newPrice);
		return orderItemToUpdate;
	}

	/*
	 * Order Item was updated --> increment/decrement Bottle Stock and increment/decrement Order Price
	 * depends on if more beverages or less were requested
	 */
	private OrderItem updateBottleStockAndOrderPrice(int orderId, int orderItemId, int newQuantity) {
		OrderItem orderItemToUpdate = this.getOrderItemById(orderId, orderItemId);
		Order orderToUpdate = this.getOrderById(orderId);
		Bottle bottleToUpdate = (Bottle) orderItemToUpdate.getBeverage();
		int difference = newQuantity - orderItemToUpdate.getQuantity();
		orderItemToUpdate.setQuantity(newQuantity);
		bottleToUpdate.setInStock(bottleToUpdate.getInStock() - difference);
		
		double newPrice = orderToUpdate.getPrice() + (difference * bottleToUpdate.getPrice());
		newPrice = Math.round(newPrice*100);
		newPrice =  newPrice / 100;
		orderToUpdate.setPrice(newPrice);
		return orderItemToUpdate;
	}

	/*
	 * Order Item was inserted --> increment Order Price and decrement Bottle stock
	 */
	public OrderItem addBottleOrderItem(int orderId, int bottleId, int amount) {
		Order order = this.getOrderById(orderId);
		Bottle bottle = this.getBottleById(bottleId);
		int newOrderNumber = getNewOrderNumber(order);
		
		double newPrice = (order.getPrice() + bottle.getPrice() * amount) * 100;
		newPrice = Math.round(newPrice);
		newPrice = newPrice /100;
		order.setPrice(newPrice);
		bottle.setInStock(bottle.getInStock() - amount);
		OrderItem orderItem = new OrderItem(newOrderNumber, bottle, amount);
		order.getPositions().add(orderItem);
		return orderItem;
	}

	
	/*
	 * Order Item was inserted --> increment Order Price and decrement crate stock
	 */
	public OrderItem addCrateOrderItem(int orderId, int crateId, int amount) {
		Order order = this.getOrderById(orderId);
		Crate crate = this.getCrateById(crateId);
		int newOrderNumber = getNewOrderNumber(order);
		double newPrice = (order.getPrice() + crate.getPrice() * amount) * 100;
		newPrice = Math.round(newPrice);
		newPrice = newPrice /100;
		order.setPrice(newPrice);
		crate.setInStock(crate.getInStock() - amount);
		OrderItem orderItem = new OrderItem(newOrderNumber, crate, amount);
		order.getPositions().add(orderItem);
		return orderItem;
	}
	

	private int getNewOrderNumber(Order order) {
		int newNumber = order.getPositions().stream().mapToInt(orderitem -> orderitem.getNumber()).max().orElse(0);
		return newNumber +10;
	}

	public void addCustomer(Customer customer) {
		customers.add(customer);
		System.out.println(customers);

	}
	public int getAndIncrementCustomerID() {
		return customerID.getAndIncrement();
	}

	public void addNewEmployee(Employee employee) {
		employees.add(employee);
		System.out.println(employees);

	}
	public int getAndIncrementEmployeeID() {
		return employeeID.getAndIncrement();
	}

	public boolean deleteCustomer(int id) {
		return customers.remove(customers.stream().filter(customer -> customer.getId() == id).findFirst().get());
	}

	public boolean deleteEmployee(int id) {
		return employees.remove(employees.stream().filter(employee -> employee.getId() == id).findFirst().get());
	}

	public List<Customer> getAllCustomer() {
		List<Customer> list = customers;
		return list;

	}
	public List<Employee> getAllEmployees() {
		List<Employee> list = employees;
		return list;

	}

	public Customer getCustomer(int id) {
		return customers.stream().filter(customer ->  customer.getId() == id).findAny().orElse(null);
	}

	public Employee getEmployee(int id) {
		return employees.stream().filter(employee -> employee.getId() == id).findAny().orElse(null);
	}

    public List<Beverage> getBottles() {
		return new ArrayList<>(bottles);
    }

	public List<Beverage> getCrates() {
		return new ArrayList<>(crates);
	}
}
