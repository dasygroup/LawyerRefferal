package com.videodasy.OLHS_backend.Domain;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;



@MappedSuperclass
public class Profile {
	
private String names;
	
	private String email;
	
	private String phone;
	
	@Enumerated(EnumType.STRING)
	private Gender gender;
	
	@Enumerated(EnumType.STRING)
	private Martalstatus martalStatus;
	@Column(unique = true)
    private String identity;
	public String getNames() {
		return names;
	}
	public void setNames(String names) {
		this.names = names;
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
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	public Martalstatus getMartalStatus() {
		return martalStatus;
	}
	public void setMartalStatus(Martalstatus martalStatus) {
		this.martalStatus = martalStatus;
	}
	public String getIdentity() {
		return identity;
	}
	public void setIdentity(String identity) {
		this.identity = identity;
	}

	
}
