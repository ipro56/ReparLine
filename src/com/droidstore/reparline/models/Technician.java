package com.droidstore.reparline.models;

public class Technician {

	// Atributos
	private String technicianId, name, surnames, address;

	// Constructores

	public Technician() {
	}

	public Technician(String technicianId, String name, String surnames,
			String address) {
		super();
		this.technicianId = technicianId;
		this.name = name;
		this.surnames = surnames;
		this.address = address;
	}

	// Getters y Setters

	public String getTechnicianId() {
		return technicianId;
	}

	public void setTechnicianId(String technicianId) {
		this.technicianId = technicianId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurnames() {
		return surnames;
	}

	public void setSurnames(String surnames) {
		this.surnames = surnames;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "Technician [technicianId=" + technicianId + ", name=" + name
				+ ", surnames=" + surnames + ", address=" + address + "]";
	}

}
