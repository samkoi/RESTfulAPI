package de.uniba.dsg.jaxrs.model.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import de.uniba.dsg.jaxrs.model.logic.Bottle;


/*
 * this class is used to get values from swagger which are used to update/ Create a bottle
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "bottle")
@XmlType(propOrder = { "id", "price", "inStock", "name", "volume", "isAlcoholic", "volumePercent", "supplier"})
public class BottlePostDTO {

		// Common Attributes --> Required
		@XmlElement
		private int id;
		@XmlElement(required = true)
		private double price;
		@XmlElement(required = true)
		private int inStock;

		// Bottle Attributes
		@XmlElement(required = true)
		private String name;
		@XmlElement(required = true)
		private double volume;
		@XmlElement
		private boolean isAlcoholic;
		@XmlElement
		private double volumePercent;
		@XmlElement
		private String supplier;
		
		
		public BottlePostDTO() {

		}


		public BottlePostDTO(int id, double price, int inStock, String name, double volume, boolean isAlcoholic,
				double volumePercent, String supplier) {
			this.id = id;
			this.price = price;
			this.inStock = inStock;
			this.name = name;
			this.volume = volume;
			this.isAlcoholic = isAlcoholic;
			this.volumePercent = volumePercent;
			this.supplier = supplier;
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


		public String getName() {
			return name;
		}


		public void setName(String name) {
			this.name = name;
		}


		public double getVolume() {
			return volume;
		}


		public void setVolume(double volume) {
			this.volume = volume;
		}


		public boolean isAlcoholic() {
			return isAlcoholic;
		}


		public void setAlcoholic(boolean isAlcoholic) {
			this.isAlcoholic = isAlcoholic;
		}


		public double getVolumePercent() {
			return volumePercent;
		}


		public void setVolumePercent(double volumePercent) {
			this.volumePercent = volumePercent;
		}


		public String getSupplier() {
			return supplier;
		}


		public void setSupplier(String supplier) {
			this.supplier = supplier;
		}


		@Override
		public String toString() {
			return "BottlePostDTO [id=" + id + ", price=" + price + ", inStock=" + inStock + ", name=" + name
					+ ", volume=" + volume + ", isAlcoholic=" + isAlcoholic + ", volumePercent=" + volumePercent
					+ ", supplier=" + supplier + "]";
		}


		public Bottle unmarshall() {
			return new Bottle(id, name, volume, isAlcoholic, volumePercent, price, supplier, inStock);
		}
		
		
		
		
}
