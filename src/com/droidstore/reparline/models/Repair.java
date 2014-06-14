package com.droidstore.reparline.models;

import java.util.Date;

public class Repair {

	// Atributos
	private String repairId, address;
	private Date date;
	private User user;
	private Technician technician;

	// Construsctores
	public Repair() {
	}

	public Repair(String repairId, String address, Date date, User user,
			Technician technician) {
		this.repairId = repairId;
		this.address = address;
		this.date = date;
		this.user = user;
		this.technician = technician;
	}

	// Getters y Setters

	public String getRepairId() {
		return repairId;
	}

	public void setRepairId(String repairId) {
		this.repairId = repairId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Technician getTechnician() {
		return technician;
	}

	public void setTechnician(Technician technician) {
		this.technician = technician;
	}

	@Override
	public String toString() {
		return "Repair [repairId=" + repairId + ", address=" + address
				+ ", date=" + date + ", user=" + user + ", technician="
				+ technician + "]";
	}

}
