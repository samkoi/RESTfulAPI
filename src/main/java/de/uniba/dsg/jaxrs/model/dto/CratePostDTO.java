package de.uniba.dsg.jaxrs.model.dto;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import de.uniba.dsg.jaxrs.model.logic.Bottle;
import de.uniba.dsg.jaxrs.model.logic.Crate;


/*
 * this class is used to get values from swagger which are used to update/ Create a crate
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "crate")
@XmlType(propOrder = {"price", "inStock", "bottleId", "noOfBottles" })
public class CratePostDTO {

		@XmlElement(required = true)
		private double price;
		@XmlElement(required = true)
		private int inStock;
		// Crate Attribute
		@XmlElement(required = true)
		private int bottleId;
		@XmlElement
		private int noOfBottles;
		
		public CratePostDTO() {
		
		}
		
		
		public CratePostDTO(double price, int inStock, int bottleId, int noOfBottles) {
			super();
			this.price = price;
			this.inStock = inStock;
			this.bottleId = bottleId;
			this.noOfBottles = noOfBottles;
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
		public int getBottleId() {
			return bottleId;
		}
		public void setBottleId(int bottleId) {
			this.bottleId = bottleId;
		}
		public int getNoOfBottles() {
			return noOfBottles;
		}
		public void setNoOfBottles(int noOfBottles) {
			this.noOfBottles = noOfBottles;
		}
		public Crate unmarshall(int id, Bottle bottleById) {
			return new Crate(id, bottleById, noOfBottles, price, inStock);
		}
		@Override
		public String toString() {
			return "CratePostDTO [price=" + price + ", inStock=" + inStock + ", bottleId=" + bottleId + ", noOfBottles="
					+ noOfBottles + "]";
		}
		
		




		

		
		
}
