package com.group3.tofu.employee.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Leave")
public class Leave {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "leave_id")
	private Integer lid;
	
	@Column(name= "leave_category")
	private String leaveCategory;
	
	@Column(name= "leave_reason")
	private String leaveReason;
	
	@Column(name= "tenure_in_months")
	private Integer tenureInMonths;
	
	@Column(name= "max_leave_days")
	private Integer MaxLeaveDays;
	
	@Column(name= "proof_document")
	private String proofDocument;	      

	@Column(name= "full_attendance")
	private Integer fullAttendance;
	
	@Column(name= "salary")
	private Integer salary;
	
	public Leave() {
	}
	
	

	public Leave(String leaveCategory, String leaveReason, Integer tenureInMonths, Integer maxLeaveDays,
			String proofDocument, Integer fullAttendance, Integer salary) {
		this.leaveCategory = leaveCategory;
		this.leaveReason = leaveReason;
		this.tenureInMonths = tenureInMonths;
		MaxLeaveDays = maxLeaveDays;
		this.proofDocument = proofDocument;
		this.fullAttendance = fullAttendance;
		this.salary = salary;
	}

	public Integer getLid() {
		return lid;
	}

	public void setLid(Integer lid) {
		this.lid = lid;
	}

	public String getLeaveCategory() {
		return leaveCategory;
	}

	public void setLeaveCategory(String leaveCategory) {
		this.leaveCategory = leaveCategory;
	}

	public String getLeaveReason() {
		return leaveReason;
	}

	public void setLeaveReason(String leaveReason) {
		this.leaveReason = leaveReason;
	}

	public Integer getTenureInMonths() {
		return tenureInMonths;
	}

	public void setTenureInMonths(Integer tenureInMonths) {
		this.tenureInMonths = tenureInMonths;
	}

	public Integer getMaxLeaveDays() {
		return MaxLeaveDays;
	}

	public void setMaxLeaveDays(Integer maxLeaveDays) {
		MaxLeaveDays = maxLeaveDays;
	}

	public String getProofDocument() {
		return proofDocument;
	}

	public void setProofDocument(String proofDocument) {
		this.proofDocument = proofDocument;
	}

	public Integer getFullAttendance() {
		return fullAttendance;
	}

	public void setFullAttendance(Integer fullAttendance) {
		this.fullAttendance = fullAttendance;
	}

	public Integer getSalary() {
		return salary;
	}

	public void setSalary(Integer salary) {
		this.salary = salary;
	}

	@Override
	public String toString() {
		return "Leave [lid=" + lid + ", leaveCategory=" + leaveCategory + ", leaveReason=" + leaveReason
				+ ", tenureInMonths=" + tenureInMonths + ", MaxLeaveDays=" + MaxLeaveDays + ", proofDocument="
				+ proofDocument + ", fullAttendance=" + fullAttendance + ", salary=" + salary + "]";
	}
	
	
	

}
