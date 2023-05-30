package com.group3.tofu.photo.model;

import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.group3.tofu.product.model.Product;

@Entity
@Table(name="Photo")
public class Photo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="photo_id")
	private Integer photoId ;
	
	@JsonIgnoreProperties({ "photos" })
	@JoinColumn(name = "f_product_id", referencedColumnName = "product_id")
	@ManyToOne
	private Product product ;
	
	@Column(name="product_name",columnDefinition = "nvarchar(255)")
	private String productName ;
	
	@Column(name="color",columnDefinition = "nvarchar(255)")
	private String color ;
	
	@Column(name="photo",columnDefinition = "varbinary(MAX)")
	private byte[] photo ;
	
	public Integer getPhotoId() {
		return photoId;
	}

	public void setPhotoId(Integer photoId) {
		this.photoId = photoId;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}
	
	public Photo() {

	}
	
	@Override
	public String toString() {
		return "Photo [photoId=" + photoId + ", product=" + product + ", productName=" + productName + ", color="
				+ color + ", photo=" + Arrays.toString(photo) + "]";
	}
	
}
