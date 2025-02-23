package com.aj.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tbl_qs_contact_details")
public class ContactDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String name;
	private String email;
	private String message;
	private String visitTimestamp;

	public ContactDetails() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getVisitTimestamp() {
		return visitTimestamp;
	}

	public void setVisitTimestamp(String visitTimestamp) {
		this.visitTimestamp = visitTimestamp;
	}

	@Override
	public String toString() {
		return "ContactDetails [id=" + id + ", name=" + name + ", email=" + email + ", message=" + message
				+ ", visitTimestamp=" + visitTimestamp + "]";
	}

}