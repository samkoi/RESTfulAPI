package de.uniba.dsg.jaxrs.model.api;

import java.net.URI;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import de.uniba.dsg.jaxrs.model.dto.BeverageDTO;

/*
 * Handles Pagination of beverages
 */
@XmlRootElement
@XmlType(propOrder = {"pagination", "beverages", "href"})
public class PaginatedBeverages {
	
	private Pagination pagination;
    private List<BeverageDTO> beverages;

    private URI href;

    public PaginatedBeverages() {

    }

	public PaginatedBeverages(Pagination pagination, List<BeverageDTO> beverages, URI requestUri) {
		this.pagination = pagination;
		this.beverages = beverages;
		this.href =requestUri;
	}

	public Pagination getPagination() {
		return pagination;
	}

	public void setPagination(Pagination pagination) {
		this.pagination = pagination;
	}

	public List<BeverageDTO> getBeverages() {
		return beverages;
	}

	public void setBeverages(List<BeverageDTO> beverages) {
		this.beverages = beverages;
	}

	public URI getHref() {
		return href;
	}

	public void setHref(URI href) {
		this.href = href;
	}
	
}
