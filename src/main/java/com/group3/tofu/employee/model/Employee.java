package com.group3.tofu.employee.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "Employee")
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "employee_id")
	private Integer eid;

	@Column(name= "first_name")
	private String firstName;
	
	@Column(name= "last_name")
	private String lastName;
	
	@Column(name= "account")
	private String account;
	
	@Column(name= "password")
	private String password;
	
	@Column(name= "gender")
	private String gender;
	
	//後台到前台的時間格式
	@JsonFormat(pattern = "yyyy-MM-dd")
//	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	//前台傳數據到後台的格式
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
//	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name= "birthday")
	private LocalDate birthday;
	
//	@MetaProperty(related={"profileDob"})
//	public int getAge() {
//	    LocalDate birthDate = profileDob.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//	    LocalDate now = LocalDate.now();
//	    return Period.between(birthDate, now).getYears();
//	}
	
//	public Integer getAge() {
//        LocalDate now = LocalDate.now();
//        return Period.between(this.birthday, now).getYears();
//    }
	
	@Column(name= "email")
	private String email;
	
	@Column(name= "phone")
	private String phone;
	
	@Column(name= "department")
	private String department;
	
	@Column(name= "position")
	private String position;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	@Column(name= "hire_date")
	private LocalDate hireDate;
	
	@Column(name= "salary")
	private Integer salary;
	
	@Column(name= "enabled")
	private Integer enabled;
	
	@Column(name= "authority")
	private String authority;
	
	//在hibernate裡面，要儲存大型資料時，要記得加上@Lob的標註
	@Lob
	@Column(name= "photo")
	private byte[] photo;
	
//	@JsonManagedReference(value = "checkedInEmp")
//	@OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
//	// 意思是當刪除employee時，相關的check也會被刪除
	// mappedBy的意思是要去找Check裡面的employee屬性，並使用joincolumn連結資訊
	//目前可以work的寫法
//	@JsonIgnore
//	@OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
//	private List<Checks> checks;
	
	@JsonManagedReference(value = "empCheckedIn")
	@OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
	private List<Checks> checks;
	
//	@JsonManagedReference(value = "empCheckedIn")
//	@JsonIgnore
//	@OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
//	private List<LeaveApplication> apps;
	
	// add convenience methods for bi-directional relationship
	public void addCheck(Checks tempCheck) {
		
		// 如果沒有任何打卡紀錄，就new一個新的打卡紀錄List
		if(checks == null) {
			checks = new ArrayList<>();
		}
		// 加入新的一筆打卡紀錄
		checks.add(tempCheck);
		// 把check中外鍵的員工資料關聯到現在這個員工
		tempCheck.setEmployee(this);
	}
	
	public Employee() {
	}

	public Integer getEid() {
		return eid;
	}

	public void setEid(Integer eid) {
		this.eid = eid;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
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

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public LocalDate getHireDate() {
		return hireDate;
	}

	public void setHireDate(LocalDate hireDate) {
		this.hireDate = hireDate;
	}

	public Integer getSalary() {
		return salary;
	}

	public void setSalary(Integer salary) {
		this.salary = salary;
	}

	public Integer getEnabled() {
		return enabled;
	}

	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}
	
	public List<Checks> getChecks() {
		return checks;
	}

	public void setChecks(List<Checks> checks) {
		this.checks = checks;
	}

	public Employee(Integer eid, String firstName, String lastName, String account, String password, String gender,
			LocalDate birthday, String email, String phone, String department, String position, LocalDate hireDate,
			Integer salary, Integer enabled, String authority, byte[] photo) {
		this.eid = eid;
		this.firstName = firstName;
		this.lastName = lastName;
		this.account = account;
		this.password = password;
		this.gender = gender;
		this.birthday = birthday;
		this.email = email;
		this.phone = phone;
		this.department = department;
		this.position = position;
		this.hireDate = hireDate;
		this.salary = salary;
		this.enabled = enabled;
		this.authority = authority;
		this.photo = photo;
	}

	@Override
	public String toString() {
		return "Employee [eid=" + eid + ", firstName=" + firstName + ", lastName=" + lastName + ", account=" + account
				+ ", password=" + password + ", gender=" + gender + ", birthday=" + birthday + ", email=" + email
				+ ", phone=" + phone + ", department=" + department + ", position=" + position + ", hireDate="
				+ hireDate + ", salary=" + salary + ", enabled=" + enabled + ", authority=" + authority + ", photo="
				+ Arrays.toString(photo) + ", checks=" + checks + "]";
	}

//	@Override
//	public String toString() {
//		return "Employee [eid=" + eid + ", firstName=" + firstName + ", lastName=" + lastName + ", account=" + account
//				+ ", password=" + password + ", gender=" + gender + ", birthday=" + birthday + ", email=" + email
//				+ ", phone=" + phone + ", department=" + department + ", position=" + position + ", hireDate="
//				+ hireDate + ", salary=" + salary + ", enabled=" + enabled + ", authority=" + authority + ", photo="
//				+ Arrays.toString(photo) + "]";
//	}

	

	

	
	
	
	
	
	
	
	
}
