package com.group3.tofu.order.model.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@Entity
@Table(name = "OrdersDetail")
public class OrderDetail {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_did")
	private Integer id;
	
	@Column(name = "gid")
	private Integer gid;
	
	@Column(name = "qty")
	private Integer qty;
	
	@Column(name = "price")
	private Integer price;
	
	@Column(name = "name")
	private String name;
	
	@JsonIgnoreProperties({ "orderDetails" })
	@JoinColumn(name = "f_order_id", referencedColumnName = "order_id")
	@ManyToOne
	private Order order;
	

	public OrderDetail() {
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getGid() {
		return gid;
	}


	public void setGid(Integer gid) {
		this.gid = gid;
	}


	public Integer getQty() {
		return qty;
	}


	public void setQty(Integer qty) {
		this.qty = qty;
	}


	public Integer getPrice() {
		return price;
	}


	public void setPrice(Integer price) {
		this.price = price;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Order getOrder() {
		return order;
	}


	public void setOrder(Order order) {
		this.order = order;
	}


	


	
	
	

}
