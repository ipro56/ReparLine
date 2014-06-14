package com.droidstore.reparline.models;

public class Incidence {
	// Atributos
	private String incidence_id, title, description, username, date, isPublic;

	// Constructores
	public Incidence() {
	}

	public Incidence(String incidenceId, String title, String description,
			String username, String date) {
		this.incidence_id = incidenceId;
		this.title = title;
		this.description = description;
		this.username = username;
		this.date = date;
	}

	public Incidence(String title, String description, String username,
			String isPublic) {

		this.title = title;
		this.description = description;
		this.username = username;
		this.isPublic = isPublic;
	}

	// Getters y Setters

	public String getTitle() {
		return title;
	}

	public String getIncidence_id() {
		return incidence_id;
	}

	public void setIncidence_id(String incidence_id) {
		this.incidence_id = incidence_id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getIsPublic() {
		return isPublic;
	}

	public void setIsPublic(String isPublic) {
		this.isPublic = isPublic;
	}

	@Override
	public String toString() {
		return "Incidence [incidence_id=" + incidence_id + ", title=" + title
				+ ", description=" + description + ", username=" + username
				+ ", date=" + date + ", isPublic=" + isPublic + "]";
	}

}
