package de.uniba.dsg.jaxrs.model.api;

import java.net.URI;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import de.uniba.dsg.jaxrs.model.dto.OrderItemDTO;

/*
 * Handles Pagination of orderItems
 */
@XmlRootElement
@XmlType(propOrder = {"pagination", "orderItems", "href"})
public class PaginatedOrderItems {

	private Pagination pagination;
    private List<OrderItemDTO> orderItems;

    private URI href;
	

	public PaginatedOrderItems() {

	}

	public PaginatedOrderItems(Pagination pagination, List<OrderItemDTO> orderItems, URI href) {
		this.pagination = pagination;
		this.orderItems = orderItems;
		this.href = href;
		
	}

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public List<OrderItemDTO> getOrderItems() {
		return orderItems;
	}

	public void setOrderItems(List<OrderItemDTO> orderItems) {
		this.orderItems = orderItems;
	}

	public URI getHref() {
		return href;
	}

	public void setHref(URI href) {
		this.href = href;
	}

	
	

}
