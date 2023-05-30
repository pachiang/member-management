package com.group3.tofu.employee.model;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "LeaveApplication")
public class LeaveApplication {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "app_id")
	private Integer aid;

	@JoinColumn(name = "f_employee_id")
	// @JsonBackReference(value = "empCheckedIn")
	@ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
			CascadeType.REFRESH })
	private Employee employee;

	@JoinColumn(name = "f_leave_id")
	// @JsonBackReference(value = "empCheckedIn")
	@ManyToOne(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
			CascadeType.REFRESH })
	private Leave leave;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	@Column(name = "begin_date")
	private LocalDate beginDate;

	@JsonFormat(pattern = "yyyy-MM-dd")
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	@Column(name = "end_date")
	private LocalDate endDate;

	@Column(name = "leave_days")
	private Integer leaveDays;
	
	@Column(name = "leave_reason")
	private String leaveReason;
	
	//@Lob好像預設會加0x
	@Lob
	@Column(name = "file_attached", nullable = true)
	private byte[] fileAttached;

	@Column(name = "manager_approved")
	private Integer managerApproved;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date")
	private Date createdDate;

	// 沒有日期時，自動產生日期 new Date();
	@PrePersist // 當物件要轉換成 Persistent 狀態時，發動該方法
	public void onCreate() {
		if (createdDate == null) {
			createdDate = new Date();
		}
	}

	public LeaveApplication() {
	}

	public LeaveApplication(Employee employee, Leave leave, LocalDate beginDate, LocalDate endDate, Integer leaveDays,
			String leaveReason, byte[] fileAttached, Integer managerApproved, Date createdDate) {
		this.employee = employee;
		this.leave = leave;
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.leaveDays = leaveDays;
		this.leaveReason = leaveReason;
		this.fileAttached = fileAttached;
		this.managerApproved = managerApproved;
		this.createdDate = createdDate;
	}

	public Integer getAid() {
		return aid;
	}

	public void setAid(Integer aid) {
		this.aid = aid;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Leave getLeave() {
		return leave;
	}

	public void setLeave(Leave leave) {
		this.leave = leave;
	}

	public LocalDate getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(LocalDate beginDate) {
		this.beginDate = beginDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public Integer getLeaveDays() {
		return leaveDays;
	}

	public void setLeaveDays(Integer leaveDays) {
		this.leaveDays = leaveDays;
	}
	
	public String getLeaveReason() {
		return leaveReason;
	}

	public void setLeaveReason(String leaveReason) {
		this.leaveReason = leaveReason;
	}
	
	public byte[] getFileAttached() {
		return fileAttached;
	}

	public void setFileAttached(byte[] fileAttached) {
		this.fileAttached = fileAttached;
	}

	public Integer getManagerApproved() {
		return managerApproved;
	}

	public void setManagerApproved(Integer managerApproved) {
		this.managerApproved = managerApproved;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Override
	public String toString() {
		return "LeaveApplication [aid=" + aid + ", employee=" + employee + ", leave=" + leave + ", beginDate="
				+ beginDate + ", endDate=" + endDate + ", leaveDays=" + leaveDays + ", leaveReason=" + leaveReason
				+ ", fileAttached=" + Arrays.toString(fileAttached) + ", managerApproved=" + managerApproved
				+ ", createdDate=" + createdDate + "]";
	}
	
	
	
	

}
