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
import de.uniba.dsg.jaxrs.resources.BeverageResource;

/*
 * this class is used to send back Beverage Objects to client
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "beverage")
@XmlType(propOrder = { "id", "price", "inStock", "bottle", "noOfBottles", "href" })
public class BeverageDTO {

	// Common Attributes --> Required
	@XmlElement
	private Integer id;
	@XmlElement
	private Double price;
	@XmlElement
	private Integer inStock;
	// Crate Attribute
	@XmlElement(required = true)
	private Bottle bottle;
	@XmlElement
	private Integer noOfBottles; // wont be shown if bottle is called Integer can be null, int cant

	@XmlElement
	private URI href;

	public BeverageDTO() {

	}

	public BeverageDTO(final Bottle bottle, final URI baseUri) {
		this.bottle = bottle;
		this.id = bottle.getId();
		this.href = UriBuilder.fromUri(baseUri).path(BeverageResource.class).path(BeverageResource.class, "getBottleById")
				.build(bottle.getId());
	}

	public BeverageDTO(final Crate crate, final URI baseUri) {
		this.id = crate.getId();
		this.price = crate.getPrice();
		this.inStock = crate.getInStock();
		this.bottle = crate.getBottle();
		this.noOfBottles = crate.getNoOfBottles();
		this.href = UriBuilder.fromUri(baseUri).path(BeverageResource.class).path(BeverageResource.class, "getCrateById")
				.build(this.id);
	}
	

	public static List<BeverageDTO> marshall(final List<Beverage> beverageList, final URI baseUri) {
		final ArrayList<BeverageDTO> beverages = new ArrayList<>();
		for (final Beverage beverage : beverageList) {
			if (beverage.getClass().getName().equals(Bottle.class.getName())) {
				beverages.add(new BeverageDTO((Bottle) beverage, baseUri));
			}else {
				beverages.add(new BeverageDTO((Crate) beverage, baseUri));
			}
			
		}
		return beverages;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getInStock() {
		return inStock;
	}

	public void setInStock(int inStock) {
		this.inStock = inStock;
	}

	public Bottle getBottle() {
		return bottle;
	}

	public void setBottle(Bottle bottle) {
		this.bottle = bottle;
	}

	public int getNoOfBottles() {
		return noOfBottles;
	}

	public void setNoOfBottles(int noOfBottles) {
		this.noOfBottles = noOfBottles;
	}

	public URI getHref() {
		return href;
	}

	public void setHref(URI href) {
		this.href = href;
	}

	public Beverage unmarshall() {
		if(id == null) {
			return new Crate(id, bottle, noOfBottles, price, inStock);
		}else {
			return bottle;
		}
	}

	@Override
	public String toString() {
		return "BeverageDTO [id=" + id + ", price=" + price + ", inStock=" + inStock + ", bottle=" + bottle
				+ ", noOfBottles=" + noOfBottles + ", href=" + href + "]";
	}

	


	
	
	
}
