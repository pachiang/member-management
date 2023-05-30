package com.group3.tofu.product.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.group3.tofu.photo.model.Photo;

@Entity
@Table(name="Product")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="product_id")
	private Integer productId;

	@Column(name="brand", columnDefinition="nvarchar(255)")
	private String brand;

	@Column(name="product_model", columnDefinition="nvarchar(255)")
	private String productModel;

	@Column(name="category", columnDefinition="nvarchar(255)")
	private String category;

	@Column(name="engine_type", columnDefinition="nvarchar(255)")
	private String engineType;

	@Column(name="displacement")
	private Integer displacement;

	@Column(name="product_price")
	private Integer productPrice;

	@Column(name="appearance_design", columnDefinition="nvarchar(255)")
	private String appearanceDesign;

	@Column(name="car_interior", columnDefinition="nvarchar(255)")
	private String carInterior;

	@Column(name="power_performance", columnDefinition="nvarchar(255)")
	private String powerPerformance;

	@Column(name="color", columnDefinition="nvarchar(255)")
	private String color;

	@OneToMany(mappedBy="product", cascade=CascadeType.ALL)
	private List<Photo> photos;

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getProductModel() {
		return productModel;
	}

	public void setProductModel(String productModel) {
		this.productModel = productModel;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getEngineType() {
		return engineType;
	}

	public void setEngineType(String engineType) {
		this.engineType = engineType;
	}

	public Integer getDisplacement() {
		return displacement;
	}

	public void setDisplacement(Integer displacement) {
		this.displacement = displacement;
	}

	public Integer getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(Integer productPrice) {
		this.productPrice = productPrice;
	}

	public String getAppearanceDesign() {
		return appearanceDesign;
	}

	public void setAppearanceDesign(String appearanceDesign) {
		this.appearanceDesign = appearanceDesign;
	}

	public String getCarInterior() {
		return carInterior;
	}

	public void setCarInterior(String carInterior) {
		this.carInterior = carInterior;
	}

	public String getPowerPerformance() {
		return powerPerformance;
	}

	public void setPowerPerformance(String powerPerformance) {
		this.powerPerformance = powerPerformance;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public List<Photo> getPhotos() {
		return photos;
	}

	public void setPhotos(List<Photo> photos) {
		this.photos = photos;
	}

	public Product() {

	}
	
	@Override
	public String toString() {
		return "Product [productId=" + productId + ", brand=" + brand + ", productModel=" + productModel + ", category="
				+ category + ", engineType=" + engineType + ", displacement=" + displacement + ", productPrice="
				+ productPrice + ", appearanceDesign=" + appearanceDesign + ", carInterior=" + carInterior
				+ ", powerPerformance=" + powerPerformance + ", color=" + color + ", photos=" + photos + "]";
	}
	
}
