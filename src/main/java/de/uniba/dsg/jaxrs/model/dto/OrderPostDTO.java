package de.uniba.dsg.jaxrs.model.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import de.uniba.dsg.jaxrs.model.logic.Order;
import de.uniba.dsg.jaxrs.model.logic.OrderItem;
import de.uniba.dsg.jaxrs.model.logic.OrderStatus;

/*
 *Post Object with infos about order
 *no variables necessary only if worker wants to add a non-client created order
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "order")
@XmlType(propOrder = {"orderId", "positions", "price", "status"})
public class OrderPostDTO {
	
	@XmlElement
	private int orderId;
	@XmlElement
    private List<OrderItemDTO> positions;
	@XmlElement
    private double price;
	@XmlElement
    private OrderStatus status;
    
    
	public OrderPostDTO() {

	}



	public OrderPostDTO(int orderId, List<OrderItemDTO> positions, double price, OrderStatus status) {
		super();
		this.orderId = orderId;
		this.positions = positions;
		this.price = price;
		this.status = status;
	}
	
	
	
	public int getOrderId() {
		return orderId;
	}



	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}



	public List<OrderItemDTO> getPositions() {
		return positions;
	}



	public void setPositions(List<OrderItemDTO> positions) {
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



	public Order unmarshall() {
		final List<OrderItem> orderItems = new ArrayList<>();
		for (final OrderItemDTO orderItemDTO : this.positions) {
			orderItems.add(orderItemDTO.unmarshall());
		}
		return new Order(this.orderId, orderItems, this.price, this.status);
	}
	

}
