package com.droidstore.reparline.models;

/**
 * POJO class to model a User
 * 
 * @author Jose_Antonio
 */
public class User {

	// Atributos
	private String name, surname, filename, address, userName, password, phone;

	public User() {
	}

	public User(String name, String surname, String address, String userName,
			String password, String phone, String filename) {
		this.name = name;
		this.surname = surname;
		this.filename = filename;
		this.address = address;
		this.userName = userName;
		this.password = password;
		this.phone = phone;
	}

	// Getters y Setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Override
	public String toString() {
		return "User [name=" + name + ", surname=" + surname + ", filename="
				+ filename + ", address=" + address + ", userName=" + userName
				+ ", password=" + password + ", phone=" + phone + "]";
	}

}
