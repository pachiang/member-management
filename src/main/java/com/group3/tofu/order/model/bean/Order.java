package com.group3.tofu.order.model.bean;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity
@Table(name = "Orders")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_id")
	private Integer id;
	
	@Column(name = "order_number",columnDefinition = "nvarchar(50)")
	private String order_number;
	
	@Column(name = "f_product_id")
	private Integer f_product_id;
	
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	private List<OrderDetail> orderDetails;
	
	@Column(name = "f_customer_id")
	private Integer f_customer_id;
	
	@Column(name = "f_employee_id")
	private Integer f_employee_id;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "order_date")
	private Date order_date;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "shipped_date")
	private Date shipped_date;
	
	@Column(name = "ship_address")
	private String ship_address;
	
	@Column(name = "payment")
	private String payment;
	
	@Column(name = "ship_status")
	private String ship_status;
	
	@Column(name = "order_status")
	private String order_status;
	
	public Order() {
	}
	
	@PrePersist//當物件要轉換成persistent狀態時發動該方法
	public void onCreateTime() {
		if(order_date == null) {
			order_date = new Date();
			shipped_date = order_date;
			
			Calendar c = Calendar.getInstance(); 
			c.setTime(shipped_date); 
			c.add(Calendar.DATE, 7);
			shipped_date = c.getTime();
			
			payment="未付款";
			ship_status="未出貨";
			order_status="正在處理";
		}
	}
	
	

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getOrder_number() {
		return order_number;
	}
	public void setOrder_number(String order_number) {
		this.order_number = order_number;
	}
	public Integer getF_product_id() {
		return f_product_id;
	}
	public void setF_product_id(Integer f_product_id) {
		this.f_product_id = f_product_id;
	}
	public List<OrderDetail> getOrderDetails() {
		return orderDetails;
	}
	public void setOrderDetails(List<OrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}
	public Integer getF_customer_id() {
		return f_customer_id;
	}
	public void setF_customer_id(Integer f_customer_id) {
		this.f_customer_id = f_customer_id;
	}
	public Integer getF_employee_id() {
		return f_employee_id;
	}
	public void setF_employee_id(Integer f_employee_id) {
		this.f_employee_id = f_employee_id;
	}
	public Date getOrder_date() {
		return order_date;
	}
	public void setOrder_date(Date order_date) {
		this.order_date = order_date;
	}
	public Date getShipped_date() {
		return shipped_date;
	}
	public void setShipped_date(Date shipped_date) {
		this.shipped_date = shipped_date;
	}
	public String getShip_address() {
		return ship_address;
	}
	public void setShip_address(String ship_address) {
		this.ship_address = ship_address;
	}
	public String getPayment() {
		return payment;
	}
	public void setPayment(String payment) {
		this.payment = payment;
	}
	public String getShip_status() {
		return ship_status;
	}
	public void setShip_status(String ship_status) {
		this.ship_status = ship_status;
	}
	public String getOrder_status() {
		return order_status;
	}
	public void setOrder_status(String order_status) {
		this.order_status = order_status;
	}
	@Override
	public String toString() {
		return "Order [id=" + id + ", order_number=" + order_number + ", f_product_id=" + f_product_id
				+ ", f_customer_id=" + f_customer_id + ", f_employee_id=" + f_employee_id + ", order_date=" + order_date
				+ ", shipped_date=" + shipped_date + ", ship_address=" + ship_address + ", payment=" + payment
				+ ", ship_status=" + ship_status + "]";
	}

	

	






	

	
	
	

}
