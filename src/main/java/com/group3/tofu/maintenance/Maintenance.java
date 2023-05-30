package com.group3.tofu.maintenance;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;


@Entity
@Table(name="Maintenance")
public class Maintenance {
	
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name="mid")
private Integer mid;
@Column(name="f_customer_id")
private Integer cid;
@Column(name="f_employee_id")
private Integer eid;
@Column(name="f_product_id")
private Integer pid;
@Column(name="mcategory")
private String mcategory;
@Column(name="notes")
private String notes;
@Temporal(TemporalType.TIMESTAMP)
@DateTimeFormat(pattern="yyyy-MM-dd")
@Column(name="appointment")
private Date appointment;
private String appointmenttime;
private String keycode ;
private String inspection;
private String electric;

@Column(name="status")
private String status;

public Maintenance() {
	
}

public String getInspection() {
	return inspection;
}

public void setInspection(String inspection) {
	this.inspection = inspection;
}

public String getElectric() {
	return electric;
}

public void setElectric(String electric) {
	this.electric = electric;
}

public String getKeycode() {
	return keycode;
}

public void setKeycode(String keycode) {
	this.keycode = keycode;
}

public String getAppointmenttime() {
	return appointmenttime;
}

public void setAppointmenttime(String appointmenttime) {
	this.appointmenttime = appointmenttime;
}

public Integer getMid() {
	return mid;
}

public void setMid(Integer mid) {
	this.mid = mid;
}

public Integer getCid() {
	return cid;
}

public void setCid(Integer cid) {
	this.cid = cid;
}

public Integer getEid() {
	return eid;
}

public void setEid(Integer eid) {
	this.eid = eid;
}

public Integer getPid() {
	return pid;
}

public void setPid(Integer pid) {
	this.pid = pid;
}

public String getMcategory() {
	return mcategory;
}

public void setMcategory(String mcategory) {
	this.mcategory = mcategory;
}



public String getNotes() {
	return notes;
}

public void setNotes(String notes) {
	this.notes = notes;
}

public Date getAppointment() {
	return appointment;
}

public void setAppointment(Date appointment) {
	this.appointment = appointment;
}

public String getStatus() {
	return status;
}

public void setStatus(String status) {
	this.status = status;
}

}
