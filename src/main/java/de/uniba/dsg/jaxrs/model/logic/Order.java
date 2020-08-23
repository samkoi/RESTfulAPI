package de.uniba.dsg.jaxrs.model.logic;

import java.util.List;

import javax.ws.rs.Path;

/*
 * Normal Order Object
 */
public class Order {

    private int orderId;
    private List<OrderItem> positions;
    private double price;
    private OrderStatus status;
    
    

    public Order() {

    }



	public Order(int orderId, List<OrderItem> positions, double price, OrderStatus status) {
        this.orderId = orderId;
        this.positions = positions;
        this.price = price;
        this.status = status;
    }
    
    

    public int getOrderId() {
        return orderId;
    }

    public List<OrderItem> getPositions() {
        return positions;
    }

    public void setPositions(List<OrderItem> positions) {
        this.positions = positions;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", positions=" + positions +
                ", price=" + price +
                ", status=" + status +
                '}';
    }
}
