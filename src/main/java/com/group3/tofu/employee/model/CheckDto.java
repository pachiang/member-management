package com.group3.tofu.employee.model;

public class CheckDto {
	
	private Integer cid;
	
	private Integer eid;
	
	private String firstName;

	private String lastName;

	private Checks checks;

	public CheckDto() {

	}

	public CheckDto(Integer cid, Integer eid, String firstName, String lastName, Checks checks) {
		this.cid = cid;
		this.eid = eid;
		this.firstName = firstName;
		this.lastName = lastName;
		this.checks = checks;
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

	public Checks getChecks() {
		return checks;
	}

	public void setChecks(Checks checks) {
		this.checks = checks;
	}

	@Override
	public String toString() {
		return "CheckDto [cid=" + cid + ", eid=" + eid + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", checks=" + checks + "]";
	}

	

	

}
