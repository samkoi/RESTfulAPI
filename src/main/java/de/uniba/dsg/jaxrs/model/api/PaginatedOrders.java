package de.uniba.dsg.jaxrs.model.api;

import java.net.URI;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import de.uniba.dsg.jaxrs.model.dto.OrderDTO;

/*
 * Handles Pagination of order
 */
@XmlRootElement
@XmlType(propOrder = {"pagination", "orders", "href"})
public class PaginatedOrders {
	
	private Pagination pagination;
	private List<OrderDTO> orders;
	
	private URI href;

	public PaginatedOrders(Pagination pagination, List<OrderDTO> orders, URI href) {
		this.pagination = pagination;
		this.orders = orders;
		this.href = href;
	}
	
	

	public PaginatedOrders() {

	}



	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public List<OrderDTO> getOrders() {
		return orders;
	}

	public void setOrders(List<OrderDTO> orders) {
		this.orders = orders;
	}

	public URI getHref() {
		return href;
	}

	public void setHref(URI href) {
		this.href = href;
	}
	
	


	


}
