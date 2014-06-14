package com.droidstore.reparline.models;

public class Post {

	// Atributos
	private String postId, description, date, username, incidenceId;

	// Constructores
	public Post() {
	}

	public Post(String postId, String description, String date, String username) {
		this.postId = postId;
		this.description = description;
		this.date = date;
		this.username = username;
	}

	public Post(String incidenceId, String description, String username) {
		this.incidenceId = incidenceId;
		this.description = description;
		this.username = username;
	}

	// Getters y Setters
	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getIncidenceId() {
		return incidenceId;
	}

	public void setIncidenceId(String incidenceId) {
		this.incidenceId = incidenceId;
	}

	@Override
	public String toString() {
		return "Post [postId=" + postId + ", description=" + description
				+ ", date=" + date + ", username=" + username
				+ ", incidenceId=" + incidenceId + "]";
	}

}
