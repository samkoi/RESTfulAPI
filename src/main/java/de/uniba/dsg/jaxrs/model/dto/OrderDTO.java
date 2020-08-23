package de.uniba.dsg.jaxrs.model.dto;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.UriBuilder;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import de.uniba.dsg.jaxrs.model.logic.Order;
import de.uniba.dsg.jaxrs.model.logic.OrderStatus;
import de.uniba.dsg.jaxrs.resources.OrderResource;

/*
 * this class is used to send back Order Objects to client
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "order")
@XmlType(propOrder = { "orderId", "positions", "price", "status", "href" })
public class OrderDTO {


	@XmlElement
	private int orderId;
	@XmlElement
	private List<OrderItemDTO> positions;
	@XmlElement(required = true)
	private double price;
	@XmlElement(required = true)
	private OrderStatus status;

	@XmlElement
	private URI href;

	public OrderDTO() {

	}
	
	public OrderDTO(final Order order, final URI baseUri) {
		this.orderId = order.getOrderId();
		this.positions = new ArrayList<>();
		this.positions = OrderItemDTO.marshall(this.orderId, order.getPositions(), baseUri);
		this.price = order.getPrice();
		this.status = order.getStatus();
		this.href = UriBuilder.fromUri(baseUri).path(OrderResource.class).path(OrderResource.class, "getOrder").build(this.orderId);
	}
	
	 public static List<OrderDTO> marshall(final List<Order> orderList, final URI baseUri) {
	        final ArrayList<OrderDTO> orders = new ArrayList<>();
	        for (final Order order : orderList) {
	            orders.add(new OrderDTO(order, baseUri));
	        }
	        return orders;
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

	public URI getHref() {
		return href;
	}

	public void setHref(URI href) {
		this.href = href;
	}

	@Override
	public String toString() {
		return "OrderDTO{" +
				"orderId=" + this.orderId +
				", positions=" + this.positions +
				", price=" + this.price +
				", status=" + this.status +
				'}';
	}
	
	

}
