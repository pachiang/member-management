package com.group3.tofu.book.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "Book")
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "book_id")
	private Integer book_id;
	
	@Column(name = "f_customer_id")
	private Integer f_customer_id;
	
	@Column(name = "f_employee_id")
	private Integer f_employee_id;
	
	@Column(name = "f_product_id")
	private Integer f_product_id;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "book_date")
	private Date book_date;
	
	@PrePersist //當物件要轉換成 Persistent 狀態時，發動該方法
	public void onCreate() {
		if(book_date == null) { //如果日期是空值，我就新增日期進去
			book_date = new Date();
		}
	}
	
	@Column(name = "status")
	private String status;
	
	public Book() {
	}

	public Integer getBook_id() {
		return book_id;
	}

	public void setBook_id(Integer book_id) {
		this.book_id = book_id;
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

	public Integer getF_product_id() {
		return f_product_id;
	}

	public void setF_product_id(Integer f_product_id) {
		this.f_product_id = f_product_id;
	}

	public Date getBook_date() {
		return book_date;
	}

	public void setBook_date(Date book_date) {
		this.book_date = book_date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Book [book_id=" + book_id + ", f_customer_id=" + f_customer_id + ", f_employee_id=" + f_employee_id
				+ ", f_product_id=" + f_product_id + ", book_date=" + book_date + ", status=" + status + "]";
	}
	
	
	
}
