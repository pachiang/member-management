package com.group3.tofu.customer.model;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name="customer")
public class Customer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="customer_id")
	private Integer customer_id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="account")
	private String account;
	
	@Column(name="password")
	private String password;
	
	@Column(name="age")
	private Integer age;
	
	@Column(name="gender")
	private String gender;
	
	
	/*
	 *   @JsonFormat主要是后台到前台的时间格式的转换
	 *   
	 *   @DataFormAT主要是前后到后台的时间格式的转换
	 */
	
//	@JsonFormat(pattern = "yyyy-MM-dd" , timezone = "GMT+8")
//	@Temporal(TemporalType.TIMESTAMP)
//	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
//	@DateTimeFormat(pattern = "yyyy-MM-dd")
//	@JsonFormat(pattern = "yyyy-MM-dd")
//	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
//	@Column(name= "birthday" , columnDefinition = "DATE")
//	private LocalDate birthday;
	
	@Column(name= "birthday")
	private String birthday;
	
	@Column(name="email")
	private String email;
	
	@Column(name="phone")
	private String phone;
	
	@Column(name="address")
	private String address;
	
	@Column(name="enabled")
	private boolean enabled;
	
	@Column(name="isAdmin")
	private String isAdmin;
	
	
//	@Lob
//	@Column(name="photo")
//	private byte[] photo;
	
	@Lob
	@Column(name="photo")
	private byte[] photo;

	@Column(name="verification")
	private String verification;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+8")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name="register_date")
	private Date register_date;
	
	
	@PrePersist //當物件要轉換成 Persistent 狀態時，發動該方法
	public void onCreate() {
		if(register_date == null) { //如果日期是空值，我就新增日期進去
			register_date = new Date();
		}
	}
	

	public Customer() {
		
	}

	public Integer getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(Integer customer_id) {
		this.customer_id = customer_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public byte[] getPhoto() {
		return photo;
	}


	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}


	public String getVerification() {
		return verification;
	}

	public void setVerification(String verification) {
		this.verification = verification;
	}

	public Date getRegister_date() {
		return register_date;
	}

	public void setRegister_date(Date register_date) {
		this.register_date = register_date;
	}


	public String getIsAdmin() {
		return isAdmin;
	}


	public void setIsAdmin(String isAdmin) {
		this.isAdmin = isAdmin;
	}


	public String getBirthday() {
		return birthday;
	}


	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}


	@Override
	public String toString() {
		return "Customer [customer_id=" + customer_id + ", name=" + name + ", account=" + account + ", password="
				+ password + ", age=" + age + ", gender=" + gender + ", birthday=" + birthday + ", email=" + email
				+ ", phone=" + phone + ", address=" + address + ", enabled=" + enabled + ", isAdmin=" + isAdmin
				+ ", photo=" + Arrays.toString(photo) + ", verification=" + verification + ", register_date="
				+ register_date + "]";
	}

	
	

	
}


