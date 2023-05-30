package com.group3.tofu.employee.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

public class CheckSearchVO {
//	 private int page = 1;
//	 private int limit;
	
	private String name;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	@Column(name= "checkin_time")
	private LocalDate checkDate;
	
	@JsonFormat(pattern = "HH:mm:ss", timezone = "GMT+8")
	//@DateTimeFormat(pattern = "HH:mm:ss")
	@DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "checkin_time")
	private LocalTime checkInTime;

	@JsonFormat(pattern = "HH:mm:ss", timezone = "GMT+8")
	//@DateTimeFormat(pattern = "HH:mm:ss")
	@DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "checkout_time")
	private LocalTime checkOutTime;
	
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(LocalDate checkDate) {
		this.checkDate = checkDate;
	}

	public LocalTime getCheckInTime() {
		return checkInTime;
	}

	public void setCheckInTime(LocalTime checkInTime) {
		this.checkInTime = checkInTime;
	}

	public LocalTime getCheckOutTime() {
		return checkOutTime;
	}

	public void setCheckOutTime(LocalTime checkOutTime) {
		this.checkOutTime = checkOutTime;
	}
	
	

	public CheckSearchVO() {

	}
	
	public CheckSearchVO(String name, LocalDate checkDate, LocalTime checkInTime, LocalTime checkOutTime) {
		this.name = name;
		this.checkDate = checkDate;
		this.checkInTime = checkInTime;
		this.checkOutTime = checkOutTime;
	}

	@Override
	public String toString() {
		return "CheckSearchVO [name=" + name + ", checkDate=" + checkDate + ", checkInTime=" + checkInTime
				+ ", checkOutTime=" + checkOutTime + "]";
	}
	
	
	

	
	
	
	
	
}
