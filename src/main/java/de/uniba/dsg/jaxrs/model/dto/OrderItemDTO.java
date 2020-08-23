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

import de.uniba.dsg.jaxrs.model.logic.Beverage;
import de.uniba.dsg.jaxrs.model.logic.Bottle;
import de.uniba.dsg.jaxrs.model.logic.Crate;
import de.uniba.dsg.jaxrs.model.logic.OrderItem;
import de.uniba.dsg.jaxrs.resources.OrderItemResource;

/*
 * this class is used to send back OrderItem Objects to client 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "orderItem")
@XmlType(propOrder = { "number", "beverage", "quantity", "href" })
public class OrderItemDTO {
	
	@XmlElement(required = true)
	private int number;
	@XmlElement(required = true)
    private BeverageDTO beverage;
	@XmlElement(required = true)
    private int quantity;
	
	@XmlElement
	private URI href;
	
	public OrderItemDTO() {
		
	}
	
	public OrderItemDTO(int orderId, final OrderItem orderItem, final URI baseUri) {
		this.number = orderItem.getNumber();
		if(orderItem.getBeverage() instanceof Bottle) {
			this.beverage = new BeverageDTO((Bottle) orderItem.getBeverage(), baseUri);
		}else {
			this.beverage = new BeverageDTO((Crate) orderItem.getBeverage(), baseUri);
		}
		this.quantity = orderItem.getQuantity();
		this.href = UriBuilder.fromUri(baseUri).path(OrderItemResource.class).path(OrderItemResource.class, "getOrderItem").build(orderId, this.number);
	}
	
	

	public static List<OrderItemDTO> marshall(int orderId, List<OrderItem> positions, URI baseUri) {
		final ArrayList<OrderItemDTO> orderItems = new ArrayList<>();
        for (final OrderItem orderItem : positions) {
        	orderItems.add(new OrderItemDTO(orderId, orderItem, baseUri));
        }
        return orderItems;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public URI getHref() {
		return href;
	}

	public void setHref(URI href) {
		this.href = href;
	}

	public OrderItem unmarshall() {
		return new OrderItem(number, beverage.unmarshall(), quantity);
	}
	
	

	public BeverageDTO getBeverage() {
		return beverage;
	}

	public void setBeverage(BeverageDTO beverage) {
		this.beverage = beverage;
	}

	@Override
	public String toString() {
		return "OrderItemDTO [number=" + number + ", beverage=" + beverage + ", quantity=" + quantity + ", href=" + href
				+ "]";
	}
	
	

}
