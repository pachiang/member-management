package com.group3.tofu.employee.model;

import java.util.Date;

import javax.persistence.CacheStoreMode;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "Checks")
public class Checks {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "check_id")
	private Integer cid;
	
	//目前可以work的寫法
//	@JoinColumn(name = "f_employee_id")
//	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
//	private Employee employee;
	
	@JoinColumn(name = "f_employee_id")
	@JsonBackReference(value = "empCheckedIn")
	@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
	private Employee employee;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "checkin_time")
	private Date checkInTime;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "checkout_time")
	private Date checkOutTime;

	// 沒有日期時，自動產生日期 new Date();
//	@PrePersist // 當物件要轉換成 Persistent 狀態時，發動該方法
//	public void onCreate() {
//		if (added == null) {
//			added = new Date();
//		}
//	}
	
	public Checks() {
		
	}
	
	public Checks(Employee employee, Date checkInTime) {
		this.employee = employee;
		this.checkInTime = checkInTime;
	}

	public Checks(Employee employee, Date checkInTime, Date checkOutTime) {
		this.employee = employee;
		this.checkInTime = checkInTime;
		this.checkOutTime = checkOutTime;
	}

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Date getCheckInTime() {
		return checkInTime;
	}

	public void setCheckInTime(Date checkInTime) {
		this.checkInTime = checkInTime;
	}

	public Date getCheckOutTime() {
		return checkOutTime;
	}

	public void setCheckOutTime(Date checkOutTime) {
		this.checkOutTime = checkOutTime;
	}

	@Override
	public String toString() {
		return "Check [cid=" + cid + ", employee=" + employee.getEid() + ", checkInTime=" + checkInTime + ", checkOutTime="
				+ checkOutTime + "]";
	}

	
	

}
